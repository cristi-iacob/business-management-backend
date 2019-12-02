package ubb.proiectColectiv.businessmanagementbackend.service;

import org.springframework.stereotype.Service;
import ubb.proiectColectiv.businessmanagementbackend.utils.FirebaseUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

@Service
public class RequestService {

    public String createProfileRequest(String token, String email, HashMap request) {
        if (TokenService.containsToken(email, token)) {
            FirebaseUtils.setValue(Arrays.asList("UserEdits", String.valueOf(Objects.hash(email))), request);
            return "REQUEST ADDED";
        }
        return "INVALID TOKEN";
    }
}
