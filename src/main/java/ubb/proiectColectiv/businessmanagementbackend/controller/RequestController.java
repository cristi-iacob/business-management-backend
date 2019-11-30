package ubb.proiectColectiv.businessmanagementbackend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ubb.proiectColectiv.businessmanagementbackend.service.RequestService;

import java.util.HashMap;

@RestController
public class RequestController {

    @Autowired
    private RequestService service;
    private Logger logger = LoggerFactory.getLogger(RequestController.class);
    private ObjectMapper mapper = new ObjectMapper();

    @PostMapping(value = "/requests/createProfileRequest/{email}")
    public ResponseEntity<?> setUserPersonalInfo(@RequestHeader("Authorization") String token, @PathVariable String email, @RequestBody String content) {
        try {
            HashMap<?, ?> request = mapper.readValue(content, HashMap.class);
            String requestStatus = service.createProfileRequest(token, email, request);
            ResponseEntity<?> responseEntity = new ResponseEntity<>(requestStatus, HttpStatus.OK);

            if (requestStatus.equals("REQUEST ADDED"))
                logger.info("User " + email + " created a request");
            else
                logger.warn("User " + email + " tried to create a request without being logged or having a token");

            return responseEntity;
        } catch (JsonProcessingException e) {
            logger.error("error parsing login request content");
            return new ResponseEntity<>("ERROR", HttpStatus.OK);
        }
    }
}
