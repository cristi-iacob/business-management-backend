package ubb.proiectColectiv.businessmanagementbackend.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import ubb.proiectColectiv.businessmanagementbackend.model.User;
import ubb.proiectColectiv.businessmanagementbackend.utils.FirebaseUtils;

import java.util.*;


@Service
public class UserService {

    private Map<String, List<String>> tokens = new HashMap<>();

    public String login(String email, String password) {

        Object userPassword = FirebaseUtils.getUpstreamData(Arrays.asList("User", String.valueOf(Objects.hash(email)), "password")); //get password of user from Firebase

        if (password.equals(userPassword)) {

            Object user_approved_status = FirebaseUtils.getUpstreamData(Arrays.asList("User", String.valueOf(Objects.hash(email)), "approved_status"));
            if (user_approved_status.equals(true)) {

                String token = RandomStringUtils.randomAlphanumeric(15);    //generate token
                if (tokens.containsKey(email))
                    tokens.get(email).add(token);
                else
                    tokens.put(email, new ArrayList<>(Collections.singletonList(token)));
                return token;

            } else
                return "UNAPPROVED";
        }
        return "WRONG";
    }

    public String register(String email, String password) {
        Object userInDataBase = FirebaseUtils.getUpstreamData(Arrays.asList("User", String.valueOf(Objects.hash(email)), "password"));

        if (userInDataBase != null)
            return "EXISTS";

        User user = new User(email, password);
        user.setApprovedStatus(false);
        user.setFailedLoginCounter(0);

        FirebaseUtils.setValue(Arrays.asList("User", String.valueOf(Objects.hash(email))), user);
        String token = RandomStringUtils.randomAlphanumeric(15);
        tokens.put(email, new ArrayList<>(Collections.singletonList(token)));
        return token;
    }

    public String logout(String email, String token) {
        if (tokens.get(email).remove(token))
            return "DELETED";
        else
            return "NOT LOGGED";
    }

    public Object getPersonalData(String token, String email) {
        if (tokens.get(email).contains(token))
            return FirebaseUtils.getUpstreamData(Arrays.asList("User", String.valueOf(Objects.hash(email))));
        return "INVALID TOKEN";
    }
}
