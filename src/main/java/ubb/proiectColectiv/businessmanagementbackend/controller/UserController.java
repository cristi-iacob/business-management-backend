package ubb.proiectColectiv.businessmanagementbackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ubb.proiectColectiv.businessmanagementbackend.service.UserService;
import ubb.proiectColectiv.businessmanagementbackend.utils.FirebaseUtils;

import java.util.ArrayList;
import java.util.Arrays;
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
     * @param content
     * @return message of "ok" or "wrong"
     */
    @GetMapping(value = "/login")
    public ResponseEntity<String> login(@RequestBody String content) {
        //TODO check login by username password AND APPROVAL_STATUS (see user model)
        //TODO update method documentation
        try {
            HashMap<String, String> user = new ObjectMapper().readValue(content, HashMap.class);
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            if (service.login(user.get("username"), user.get("password"))) {
                return new ResponseEntity<>(ow.writeValueAsString("OK"), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(ow.writeValueAsString("WRONG"), HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error parsing login request content");
            return new ResponseEntity<>("ERROR", HttpStatus.OK);
        }
    }

    /**
     * Registers a users email and password credentials and the other credentials if they exist in the database
     * @return
     */
    @PostMapping(value = "/users/register")
    public ResponseEntity<String> register(){
        return null;
    }

    /**
     * Retrieves all the requests of an user
     * @return
     */
    @GetMapping(value = "/users/{user_email}/requests")
    public ResponseEntity<String> getRequests() {
        return null;
    }
}