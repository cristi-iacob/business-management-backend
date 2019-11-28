package ubb.proiectColectiv.businessmanagementbackend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ubb.proiectColectiv.businessmanagementbackend.service.UserService;

import java.util.HashMap;
import java.util.Objects;

@RestController
public class UserController {

    @Autowired
    private UserService service;
    private Logger logger = LoggerFactory.getLogger(UserController.class);

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
    public ResponseEntity login(@RequestBody String content) {
        try {
            HashMap user = new ObjectMapper().readValue(content, HashMap.class);
            String returnedStatus = service.login((String) user.get("email"), (String) user.get("password"));
            ResponseEntity responseEntity = new ResponseEntity<>(returnedStatus, HttpStatus.OK);

            if (responseEntity.getBody().equals("UNAPPROVED"))
                logger.info("User " + user.get("email") + " is unapproved");
            else if (responseEntity.getBody().equals("WRONG"))
                logger.info("User " + user.get("email") + " is not registered");
            else
                logger.info("User " + user.get("email") + "logged in with token" + responseEntity.getBody());

            return responseEntity;
        } catch (JsonProcessingException e) {
            logger.error("error parsing login request content");
            return new ResponseEntity<>("ERROR", HttpStatus.OK);
        }
    }

    /**
     * Registers a users email and password credentials in the database
     *
     * @return
     */
    @PostMapping(value = "/register")
    public ResponseEntity register(@RequestBody String content) {
        try {
            HashMap user = new ObjectMapper().readValue(content, HashMap.class);
            ResponseEntity responseEntity = new ResponseEntity<>(service.register((String) user.get("email"), (String) user.get("password")), HttpStatus.OK);
            if (responseEntity.getBody().equals("EXISTS"))
                logger.info("User " + user.get("email") + " is already registered");
            else
                logger.info("User " + user.get("email") + " registered as " + Objects.hash(user.get("email")) + " with token " + responseEntity.getBody());

            return responseEntity;
        } catch (JsonProcessingException e) {
            logger.error("error parsing login request content");
            return new ResponseEntity<>("ERROR", HttpStatus.OK);
        }
    }


    @PostMapping(value = "/logout")
    public ResponseEntity logout(@RequestBody String content) {
        try {
            HashMap user = new ObjectMapper().readValue(content, HashMap.class);
            ResponseEntity responseEntity = new ResponseEntity<>(service.logout((String) user.get("email"), (String) user.get("token")), HttpStatus.OK);

            if (responseEntity.getBody().equals("DELETED"))
                logger.info("User " + user.get("email") + " logged out with token " + user.get("token"));
            else
                logger.warn("User " + user.get("email") + " tried logging out without being logged");

            return responseEntity;
        } catch (JsonProcessingException e) {
            logger.error("error parsing login request content");
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