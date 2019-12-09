package ubb.proiectColectiv.businessmanagementbackend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import ubb.proiectColectiv.businessmanagementbackend.utils.FirebaseUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class RequestService {

    private ObjectMapper mapper = new ObjectMapper();

    public String[] createProfileRequest(String token, String content) throws JsonProcessingException {
        try {
            HashMap<?, ?> request = mapper.readValue(content, HashMap.class);
            String email = TokenService.getKeyByToken(token);
            String hashedEmail = String.valueOf(Objects.hash(email));
            long unixTime = System.currentTimeMillis() / 1000L;
            FirebaseUtils.setValue(Arrays.asList("User", hashedEmail, "edits", String.valueOf(unixTime)), request);
            return new String[]{"REQUEST ADDED", email, String.valueOf(unixTime)};
        } catch (NullPointerException e) {
            return new String[]{"INVALID TOKEN"};
        }
    }

    public String[] approveProfileRequest(String token, String requestId) throws JsonProcessingException {
        try {
            String email = TokenService.getKeyByToken(token);
            String hashedEmail = String.valueOf(Objects.hash(email));
            //get request
            Object requestInDatabase = FirebaseUtils.getUpstreamData(Arrays.asList("User", hashedEmail, "edits", requestId));
            HashMap<String, String> request = mapper.convertValue(requestInDatabase, HashMap.class);
            //set request
            for (Map.Entry<String, String> entry : request.entrySet()) {
                System.out.println(entry.getKey() + " | " + entry.getValue());
                FirebaseUtils.setValue(Arrays.asList("User", hashedEmail, entry.getKey()), entry.getValue());
            }
            //delete request
            FirebaseUtils.removeValue(Arrays.asList("User", hashedEmail, "edits", requestId));
            return new String[]{"REQUEST APPROVED", email};
        } catch (NullPointerException e) {
            return new String[]{"INVALID TOKEN"};
        }
    }
}
