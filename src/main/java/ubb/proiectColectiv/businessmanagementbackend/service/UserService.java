package ubb.proiectColectiv.businessmanagementbackend.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import ubb.proiectColectiv.businessmanagementbackend.model.User;
import ubb.proiectColectiv.businessmanagementbackend.utils.FirebaseUtils;

import java.util.*;

@Service
public class UserService {

    private HashMap<String, String> tokens = new HashMap<>();

    public String login(String hashedEmail, String password) {

        Object userPassword = FirebaseUtils.getUpstreamData(Arrays.asList("User", String.valueOf(Objects.hash(hashedEmail)), "password")); //get password of user from Firebase

        if (password.equals(userPassword)) {
            Object user_approved_status = FirebaseUtils.getUpstreamData(Arrays.asList("User", String.valueOf(Objects.hash(hashedEmail)), "approved_status"));

            if (user_approved_status.equals(true)) {
                String generatedString = RandomStringUtils.randomAlphanumeric(15);
                tokens.put(hashedEmail, generatedString);
                return generatedString;
            } else
                return "UNAPPROVED";
        }

        return "WRONG";
    }

    public String register(String hashedEmail, String password) {
        Object userInDataBase = FirebaseUtils.getUpstreamData(Arrays.asList("User", String.valueOf(Objects.hash(hashedEmail)), "password"));

        if (userInDataBase != null)
            return "EXISTS";

        User user = new User(hashedEmail, password);
        user.setApproved_status(false);
        user.setFailed_login_counter(0);

        FirebaseUtils.setValue(Arrays.asList("User", String.valueOf(Objects.hash(hashedEmail))), user);
        String generatedString = RandomStringUtils.randomAlphanumeric(15);
        tokens.put(hashedEmail, generatedString);
        return generatedString;
    }

    public String logout(String hashedEmail) {
        tokens.remove(hashedEmail);
        return "DELETED";
    }
}
