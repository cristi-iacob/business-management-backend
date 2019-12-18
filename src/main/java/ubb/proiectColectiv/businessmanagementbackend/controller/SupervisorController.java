package ubb.proiectColectiv.businessmanagementbackend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
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

    /**
     * Gets all unapproved users
     * @return Returns all unapporved users in the body of the request
     */
    @GetMapping(value = "/supervisor/registrationRequests")
    public ResponseEntity<String> getRegistrationRequests() {
        try {
            // TODO: 17-Dec-19 Check if user is supervisor using the token from the header
            //getKeyByToken()
            var approvalRequests = objectMapper.writeValueAsString(supervisorService.getRegistrationRequests());
            logger.info("Sending all approval requests!");
            return new ResponseEntity<>(approvalRequests, HttpStatus.OK);
        } catch (JsonProcessingException e) {
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

    /**
     *
     * @param json hashedEmail to be approved
     * @return A confirmation if the approval was successful, an error messege otherwise
     */
    @PutMapping(value = "/supervisor/approveRegistrationRequest")
    public ResponseEntity<String> approveRegistrationRequest(@RequestBody String json) {
        try {
        // TODO: 17-Dec-19  Check if user is supervisor using the token from the header
            supervisorService.approveRegistrationRequest(json);
            logger.info("Registration Request of user " + json + " has been approved");
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (NullPointerException | JsonProcessingException e) {
            logger.error("Error at approving user " + json);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // TODO: 17-Dec-19
    @PutMapping(value = "/supervisor/rejectRegistrationRequest")
    public ResponseEntity<String> rejectRegistrationRequest(@RequestBody String json) {
        try {
            // TODO: 17-Dec-19  Check if user is supervisor using the token from the header
            supervisorService.rejectRegistrationRequest(json);
            logger.info("Registration Request of user " + json + " has been rejected");
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (NullPointerException | JsonProcessingException e) {
            logger.error("Error at rejecting user " + json);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
