package ubb.proiectColectiv.businessmanagementbackend.service;

import org.springframework.stereotype.Service;
import ubb.proiectColectiv.businessmanagementbackend.model.User;
import ubb.proiectColectiv.businessmanagementbackend.utils.FirebaseUtils;

import java.util.Arrays;
import java.util.Objects;

@Service
public class UserService {

    public String login(String email, String password) {

        Object userPassword = FirebaseUtils.getUpstreamData(Arrays.asList("User", String.valueOf(Objects.hash(email)), "password")); //get password of user from Firebase

        if (password.equals(userPassword)) {
            Object user_approved_status = FirebaseUtils.getUpstreamData(Arrays.asList("User", String.valueOf(Objects.hash(email)), "approved_status"));

            if (user_approved_status.equals(true))
                return "APPROVED";
            else
                return "UNAPPROVED";
        }

        return "WRONG";
    }

    public String register(String email, String password) {
        Object userInDataBase = FirebaseUtils.getUpstreamData(Arrays.asList("User", String.valueOf(Objects.hash(email)), "password"));

        if (userInDataBase != null)
            return "EXISTS";

        User user = new User(email, password);
        user.setApproved_status(false);
        user.setFailed_login_counter(0);

        FirebaseUtils.setValue(Arrays.asList("User", String.valueOf(Objects.hash(email))), user);
        return "REGISTERED";
    }
}
