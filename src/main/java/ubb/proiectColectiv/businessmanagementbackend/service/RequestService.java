package ubb.proiectColectiv.businessmanagementbackend.service;

import org.springframework.stereotype.Service;
import ubb.proiectColectiv.businessmanagementbackend.utils.FirebaseUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

@Service
public class RequestService {

    public String[] createProfileRequest(String token, HashMap<?, ?> request) {
        try {
            String email = TokenService.getKeyByToken(token);
            String hashedEmail = "\"" + Objects.hash(email) + "\"";
            String numberOfChildren = FirebaseUtils.getUpstreamData(Arrays.asList("UserEdits", hashedEmail));
            FirebaseUtils.setValue(Arrays.asList("UserEdits", hashedEmail, ), request);
            return new String[]{"REQUEST ADDED", email};
        } catch (NullPointerException e) {
            return new String[]{"INVALID TOKEN"};
        }
    }
}
