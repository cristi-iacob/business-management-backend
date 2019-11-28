package ubb.proiectColectiv.businessmanagementbackend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ubb.proiectColectiv.businessmanagementbackend.model.User;
import ubb.proiectColectiv.businessmanagementbackend.service.UserService;

import java.util.HashMap;
import java.util.Objects;

@RestController
public class UserController {

    @Autowired
    private UserService service;
    private Logger logger = LoggerFactory.getLogger(UserController.class);
    private ObjectMapper mapper = new ObjectMapper();

    /**
     * Checks if the user already exists based on his username and password approval_status
     *
     * @param content Json with credentials
     * @return Token if login was successful
     */
    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody String content) {
        try {
            User user = mapper.readValue(content, User.class);
            String loginStatus = service.login(user.getEmail(), user.getPassword());
            ResponseEntity<?> responseEntity = new ResponseEntity<>(loginStatus, HttpStatus.OK);

            if (loginStatus.equals("UNAPPROVED"))
                logger.info("User " + user.getEmail() + " is unapproved");
            else if (loginStatus.equals("WRONG"))
                logger.info("User " + user.getEmail() + " is not registered");
            else
                logger.info("User " + user.getEmail() + "logged in with token " + responseEntity.getBody());

            return responseEntity;
        } catch (JsonProcessingException e) {
            logger.error("error parsing login request content");
            return new ResponseEntity<>("ERROR", HttpStatus.OK);
        }
    }

    /**
     * Registers a users email and password credentials in the database
     *
     * @param content Json with credentials to register
     * @return Token if registration was successful
     */
    @PostMapping(value = "/register")
    public ResponseEntity<?> register(@RequestBody String content) {
        try {
            User user = mapper.readValue(content, User.class);
            String registerStatus = service.register(user.getEmail(), user.getPassword());
            ResponseEntity<?> responseEntity = new ResponseEntity<>(registerStatus, HttpStatus.OK);

            if (registerStatus.equals("EXISTS"))
                logger.info("User " + user.getEmail() + " is already registered");
            else
                logger.info("User " + user.getEmail() + " registered as " + Objects.hash(user.getEmail()) + " with token " + responseEntity.getBody());

            return responseEntity;
        } catch (JsonProcessingException e) {
            logger.error("error parsing register request content");
            return new ResponseEntity<>("ERROR", HttpStatus.OK);
        }
    }

    /**
     * Deletes users token
     *
     * @param token   Active token on this session
     * @param content Email of user
     * @return Message of "DELETED", "NOT LOGGED" or "ERROR"
     */
    @PostMapping(value = "/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String token, @RequestBody String content) {
        try {
            HashMap json = mapper.readValue(content, HashMap.class);
            String logoutStatus = service.logout((String) json.get("email"), token);
            ResponseEntity<?> responseEntity = new ResponseEntity<>(logoutStatus, HttpStatus.OK);

            if (logoutStatus.equals("DELETED"))
                logger.info("User " + json.get("email") + " logged out with token " + json.get("token"));
            else
                logger.warn("User " + json.get("email") + " tried logging out without being logged or having a token");

            return responseEntity;
        } catch (JsonProcessingException e) {
            logger.error("error parsing logout request content");
            return new ResponseEntity<>("ERROR", HttpStatus.OK);
        }
    }

    /**
     * Retrieves personal info of user
     *
     * @param token Active token on this session
     * @param email Email of user
     * @return Personal informations of user as Json
     */
    @GetMapping(value = "/users/userdata/{email}")
    public ResponseEntity getUserPersonalInfo(@RequestHeader("Authorization") String token, @PathVariable(value = "email") String email) {

        try {
            Object userReturned = service.getPersonalData(token, email);
            logger.info("Retrieved personal info from user " + email + " with token " + token);
            return new ResponseEntity<>(mapper.writeValueAsString(userReturned), HttpStatus.OK);

        } catch (JsonProcessingException e) {
            logger.error("error parsing getPersonalInfo request content");
            return new ResponseEntity<>("ERROR", HttpStatus.OK);
        }
    }

}
