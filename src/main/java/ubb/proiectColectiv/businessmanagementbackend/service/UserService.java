package ubb.proiectColectiv.businessmanagementbackend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import ubb.proiectColectiv.businessmanagementbackend.model.User;
import ubb.proiectColectiv.businessmanagementbackend.utils.FirebaseUtils;

import java.util.*;


@Service
public class UserService {

    private ObjectMapper mapper = new ObjectMapper();
    private HashMap<String, Long> lastLoginAttempts = new HashMap<>();

    public String login(String email, String password) {
        if (lastLoginAttempts.get(email) != null && System.currentTimeMillis() <= lastLoginAttempts.get(email) + 10 * 1000) //check if 10 seconds have passed since last login attempt
            return "SPAM";

        String hashedEmail = "\"" + Objects.hash(email) + "\"";
        Object userInDatabase = FirebaseUtils.getUpstreamData(Arrays.asList("User", hashedEmail)); //get user from Firebase as HashMap
        if (userInDatabase != null) {
            User user = mapper.convertValue(userInDatabase, User.class); //convert HashMap to User POJO
            if (password.equals(user.getPassword())) {

                if (user.getBlockedStatus().equals(true)) {
                    lastLoginAttempts.put(email, System.currentTimeMillis()); //set time of last login attempt for current user
                    return "BLOCKED";
                }

                if (user.getFailedLoginCounter() != 0) {
                    FirebaseUtils.setValue(Arrays.asList("User", hashedEmail, "failedLoginCounter"), 0); //reset counter to 0 cause user entered correct credentials
                }

                if (user.getApprovedStatus().equals(true)) {
                    String token = RandomStringUtils.randomAlphanumeric(15);    //generate token for this user
                    if (TokenService.containsKey(email))
                        TokenService.getTokens().get(email).add(token);
                    else
                        TokenService.getTokens().put(email, new ArrayList<>(Collections.singletonList(token)));
                    return token;
                }
                return "UNAPPROVED";
            }

            lastLoginAttempts.put(email, System.currentTimeMillis()); //set time of last login attempt for current user
            FirebaseUtils.setValue(Arrays.asList("User", hashedEmail, "failedLoginCounter"), user.getFailedLoginCounter() + 1);
            if (user.getFailedLoginCounter() == 2) { //this counter is the one before the incrementation
                FirebaseUtils.setValue(Arrays.asList("User", hashedEmail, "blockedStatus"), Boolean.TRUE);
                return "BLOCKED";
            }
            return "WRONG PASSWORD";
        }
        return "INEXISTENT";
    }

    public String register(String email, String password) {
        Object userInDataBase = FirebaseUtils.getUpstreamData(Arrays.asList("User", String.valueOf(Objects.hash(email)), "password"));

        if (userInDataBase != null)
            return "EXISTS";

        User user = new User();
        user.setApprovedStatus(false);
        user.setBlockedStatus(false);
        user.setEmail(email);
        user.setFailedLoginCounter(0);
        user.setPassword(password);
        FirebaseUtils.setValue(Arrays.asList("User", "\"" + Objects.hash(email) + "\""), user);
        String token = RandomStringUtils.randomAlphanumeric(15);
        TokenService.getTokens().put(email, new ArrayList<>(Collections.singletonList(token)));
        return token;
    }

    public String logout(String email, String token) {
        if (TokenService.getTokens().get(email).remove(token))
            return "LOGGED OUT";
        else
            return "NOT LOGGED";
    }

    public Object getPersonalData(String token, String email) {
        if (TokenService.containsToken(email, token)) {
            return FirebaseUtils.getUpstreamData(Arrays.asList("User", "\"" + (Objects.hash(email) + "\"")));
        }
        return "INVALID TOKEN";
    }
}
