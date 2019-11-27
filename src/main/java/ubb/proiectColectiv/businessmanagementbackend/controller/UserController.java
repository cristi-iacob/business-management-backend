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
     * @return message of "APPROVED", "UNAPPROVED" or "WRONG"
     */
    @PostMapping(value = "/login")
    public ResponseEntity<String> login(@RequestBody String content) {
        try {
            HashMap user = new ObjectMapper().readValue(content, HashMap.class);
            String returnedStatus = service.login((String) user.get("email"), (String) user.get("password"));
            return new ResponseEntity<>(returnedStatus, HttpStatus.OK);
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
            return new ResponseEntity<>(service.register((String) user.get("email"), (String) user.get("password")), HttpStatus.OK);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new ResponseEntity<>("ERROR", HttpStatus.OK);
        }
    }


    @PostMapping(value = "/logout")
    public ResponseEntity<String> logout(@RequestBody String content) {
        try {
            HashMap user = new ObjectMapper().readValue(content, HashMap.class);
            return new ResponseEntity<>(service.logout((String) user.get("email")), HttpStatus.OK);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            System.out.println("error parsing login request content");
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