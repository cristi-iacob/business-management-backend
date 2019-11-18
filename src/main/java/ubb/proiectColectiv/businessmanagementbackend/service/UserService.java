package ubb.proiectColectiv.businessmanagementbackend.service;

import org.springframework.stereotype.Service;
import ubb.proiectColectiv.businessmanagementbackend.utils.FirebaseUtils;

import java.util.Arrays;

@Service
public class UserService {

    public String login(String email, String password, Integer approved_status) {
        try {
            Object userPassword = FirebaseUtils.getUpstreamData(Arrays.asList("User", email, "password"));
            if (password.equals(userPassword)) {
                Object user_approved_status = FirebaseUtils.getUpstreamData(Arrays.asList("User", email, "approved_status"));
                if (user_approved_status.equals(true))
                    return "APPROVED";
                else
                    return "UNAPPROVED";
            }
            return "WRONG";
        } catch (NullPointerException e) {
            return "WRONG";
        }
    }
}
