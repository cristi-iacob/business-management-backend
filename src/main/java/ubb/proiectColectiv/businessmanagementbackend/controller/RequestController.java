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

    @PostMapping(value = "/requests/profileRequest/create")
    public ResponseEntity<?> createUserPersonalInfoRequest(@RequestHeader("Authorization") String token, @RequestBody String content) {
        try {
            HashMap<?, ?> request = mapper.readValue(content, HashMap.class);
            String[] requestStatus = service.createProfileRequest(token, request); //[requestId][email]
            ResponseEntity<?> responseEntity = new ResponseEntity<>(requestStatus, HttpStatus.OK);

            if (requestStatus[0].equals("REQUEST ADDED")) {
                logger.info("User " + requestStatus[1] + " created a request with id: " + requestStatus[0]);
            } else {
                logger.warn("User " + requestStatus[1] + " tried to create a request without being logged or having a token");
            }

            return responseEntity;
        } catch (JsonProcessingException e) {
            logger.error("error parsing login request content");
            return new ResponseEntity<>("ERROR", HttpStatus.OK);
        }
    }

    @PostMapping(value = "/requests/profileRequest/approve/{requestId}")
    public ResponseEntity<?> approveUserPersonalInfoRequest(@RequestHeader("Authorization") String token, @PathVariable String requestId){
        return null;
    }
}
