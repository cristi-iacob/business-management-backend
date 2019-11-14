package ubb.proiectColectiv.businessmanagementbackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
     * Updates the user approval_status based on what the admins decision
     * @return
     */
    @PutMapping(value = "/users/{user_email}/approval_status")
    public ResponseEntity<String> user_approval(){
        return null;
    }
}