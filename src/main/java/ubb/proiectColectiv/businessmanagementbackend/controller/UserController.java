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
import ubb.proiectColectiv.businessmanagementbackend.model.User;
import ubb.proiectColectiv.businessmanagementbackend.service.UserService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService service;

    public UserController(UserService userService) {
        service = userService;
    }

    /*@GetMapping(value = "/users")
    public List<String> getGheorheBoul() throws Exception {
        service.getGheorgheBoul();
        ArrayList< String > list = new ArrayList<>();
        list.add("wegrwe");
        list.add("wergw");
        return list;
    }*/

    @GetMapping(value = "/login")
    public ResponseEntity < String > login(@RequestBody String content) {
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
}