package ubb.proiectColectiv.businessmanagementbackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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

    @GetMapping(value = "/supervisor/approvals")
    public ResponseEntity<String> get_Approvals() {
        return null;
    }

    @GetMapping(value = "/supervisor/requests")
    public ResponseEntity<String> getRequests() {
        try {
            logger.info("Sending all unapproved users!");
            return new ResponseEntity<>(objectMapper.writeValueAsString(supervisorService.getPendingRequests()), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error at sending all unapproved users!");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/supervisor/approvals/{id}")
    public ResponseEntity<String> getRequestsPer_User() {
        return null;
    }

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

    @PostMapping(value = "/supervisor/setApproval/{email}")
    public ResponseEntity<String> set_Request(@PathVariable String email) {
        return new ResponseEntity<>(supervisorService.approveUserRegistration(email), HttpStatus.OK);
    }
}
