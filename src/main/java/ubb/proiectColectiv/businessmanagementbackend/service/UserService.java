package ubb.proiectColectiv.businessmanagementbackend.service;

import org.springframework.stereotype.Service;
import ubb.proiectColectiv.businessmanagementbackend.model.User;
import ubb.proiectColectiv.businessmanagementbackend.utils.FirebaseUtils;

import java.util.Arrays;
import java.util.Objects;

@Service
public class UserService {

    public String login(String email, String password) {
        try {
            Object userPassword = FirebaseUtils.getUpstreamData(Arrays.asList("User", String.valueOf(Objects.hash(email)), "password"));
            if (password.equals(userPassword)) {
                Object user_approved_status = FirebaseUtils.getUpstreamData(Arrays.asList("User", String.valueOf(Objects.hash(email)), "approved_status"));
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

    public String register(String email, String password) {
        //check if user already exists
        Object userInDataBase = FirebaseUtils.getUpstreamData(Arrays.asList("User", String.valueOf(Objects.hash(email)), "password"));
        if (userInDataBase != null)
            return "EXISTS";
        User user = new User(email, password);
        user.setApproved_status(false);
        FirebaseUtils.setValue(Arrays.asList("User", String.valueOf(Objects.hash(email))), user);
        return "REGISTERED";
    }
}
