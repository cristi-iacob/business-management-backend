package ubb.proiectColectiv.businessmanagementbackend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ubb.proiectColectiv.businessmanagementbackend.service.RequestService;

@RestController
public class RequestController {

    @Autowired
    private RequestService service;
    private Logger logger = LoggerFactory.getLogger(RequestController.class);

    // TODO: 11-Dec-19 documentation
    @PostMapping(value = "/requests/profileRequest/create")
    public ResponseEntity<?> createUserPersonalInfoRequest(@RequestHeader("Authorization") String token, @RequestBody String content) {
        try {
            String[] requestStatus = service.createProfileRequest(token, content); //[responseMessage][email][requestId]
            ResponseEntity<?> responseEntity = new ResponseEntity<>(requestStatus[0], HttpStatus.OK);

            if (requestStatus[0].equals("REQUEST ADDED")) {
                logger.info("User " + requestStatus[1] + " created a request with id: " + requestStatus[2]);
            } else {
                logger.warn("Someone tried to create a request without being logged or having a token");
            }

            return responseEntity;
        } catch (JsonProcessingException e) {
            logger.error("error parsing login request content");
            return new ResponseEntity<>("ERROR", HttpStatus.OK);
        }
    }

    // TODO: 11-Dec-19 documentation
    @PostMapping(value = "/requests/profileRequest/approve/{requestId}")
    public ResponseEntity<?> approveUserPersonalInfoRequest(@RequestHeader("Authorization") String token, @PathVariable String requestId) {
        try {
            String[] requestStatus = service.approveProfileRequest(token, requestId);

            if (requestStatus[0].equals("REQUEST APPROVED")) {
                logger.info("User " + requestStatus[1] + " approved request with id: " + requestId);
            } else {
                logger.warn("Someone tried to create a request without being logged or having a token");
            }

            return new ResponseEntity<>(requestStatus[0], HttpStatus.OK);
        } catch (JsonProcessingException e) {
            logger.error("error parsing login request content");
            return new ResponseEntity<>("ERROR", HttpStatus.OK);
        }
    }
}
