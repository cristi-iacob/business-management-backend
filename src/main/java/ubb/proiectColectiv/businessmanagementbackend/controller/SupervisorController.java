package ubb.proiectColectiv.businessmanagementbackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.var;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ubb.proiectColectiv.businessmanagementbackend.service.SupervisorService;

import java.util.List;

@RestController
@CrossOrigin
public class SupervisorController {

    @Autowired
    SupervisorService supervisorService;
    private ObjectMapper objectMapper;
    private Logger logger = LoggerFactory.getLogger(UserController.class);

    public SupervisorController(SupervisorService supervisorService) {
        this.supervisorService = supervisorService;
        objectMapper = new ObjectMapper();
    }

    // TODO: 11-Dec-19 documentation
    @GetMapping(value = "/supervisor/approvals")
    public ResponseEntity<String> get_Approvals() {
        return null;
    }

    // TODO: 11-Dec-19 documentation
    @GetMapping(value = "/supervisor/registrationRequests")
    public ResponseEntity<String> getRegistrationRequests() {
        try {
            // TODO: 17-Dec-19 Check if user is supervisor using the token from the header
            //getKeyByToken()
            var approvalRequests = objectMapper.writeValueAsString(supervisorService.getRegistrationRequests());
            logger.info("Sending all approval requests!");
            return new ResponseEntity<>(approvalRequests, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error at sending all unapproved users!");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // TODO: 11-Dec-19 documentation
    @GetMapping(value = "/supervisor/{id}/users")
    public ResponseEntity<String> getUsersForSupervisor(@PathVariable("id") String id) {
        try {
            logger.info("Getting all users for a supervisor");
            List<String> users = supervisorService.getUsersForSupervisor(id);
            if (users == null) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(objectMapper.writeValueAsString(users), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error at sending all user for supervisor");
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // TODO: 11-Dec-19 documentation
    @PostMapping(value = "/supervisor/approveRegistrationRequest/{email}")
    public ResponseEntity<String> approveRegistrationRequest(@PathVariable String email) {
        // TODO: 17-Dec-19  Check if user is supervisor using the token from the header
        return new ResponseEntity<>(supervisorService.setRequest(email), HttpStatus.OK);
    }

    // TODO: 17-Dec-19
    @PostMapping(value = "/supervisor/rejectRegistrationRequest/{email}")
    public ResponseEntity<String> rejectRegistrationRequest(@PathVariable String email) {
        // TODO: 17-Dec-19  Check if user is supervisor using the token from the header
        return null;
    }
}
