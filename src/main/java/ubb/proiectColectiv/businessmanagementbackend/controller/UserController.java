package ubb.proiectColectiv.businessmanagementbackend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ubb.proiectColectiv.businessmanagementbackend.service.UserService;

import java.util.HashMap;

@RestController
public class UserController {

    @Autowired
    private UserService service;

    public UserController(UserService userService) {
        service = userService;
    }

    /**
     * Checks if the user already exists based on his username and password approval_status
     *
     * @param content json with credentials
     * @return message of "ok" or "wrong"
     */
    @PostMapping(value = "/login")
    public ResponseEntity<String> login(@RequestBody String content) {
        try {
            HashMap user = new ObjectMapper().readValue(content, HashMap.class);

            switch (service.login((String) user.get("email"), (String) user.get("password"))) {
                case "APPROVED":
                    return new ResponseEntity<>("APPROVED", HttpStatus.OK);
                case "UNAPPROVED":
                    return new ResponseEntity<>("UNAPPROVED", HttpStatus.OK);
                case "WRONG":
                default:
                    return new ResponseEntity<>("WRONG", HttpStatus.OK);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error parsing login request content");
            return new ResponseEntity<>("ERROR", HttpStatus.OK);
        }
    }

    /**
     * Registers a users email and password credentials in the database
     *
     * @return
     */
    @PostMapping(value = "/register")
    public ResponseEntity<String> register(@RequestBody String content) {
        try {
            HashMap user = new ObjectMapper().readValue(content, HashMap.class);

            if (service.register((String) user.get("email"), (String) user.get("password")).equals("REGISTERED"))
                return new ResponseEntity<>("REGISTERED", HttpStatus.OK);
            else
                return new ResponseEntity<>("EXISTS", HttpStatus.OK);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new ResponseEntity<>("ERROR", HttpStatus.OK);
        }
    }

    /**
     * Retrieves all the requests of an user
     *
     * @return
     */
    @GetMapping(value = "/users/{user_email}/requests")
    public ResponseEntity<String> getRequests() {
        return null;
    }
}