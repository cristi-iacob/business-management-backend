package ubb.proiectColectiv.businessmanagementbackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ubb.proiectColectiv.businessmanagementbackend.service.SupervisorService;

@RestController
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
            logger.info("Sending all unapproved users!\n");
            return new ResponseEntity<>(objectMapper.writeValueAsString(supervisorService.getPendingRequests()), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error at sending all unapproved users!\n" + e.getStackTrace());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/supervisor/approvals/{user}")
    public ResponseEntity<String> get_Requests_Per_User() {
        return null;
    }

    @PostMapping(value = "/supervisor/setApproval")
    public ResponseEntity<String> set_Request(@RequestBody String content){
        //TODO endpoint for approving users
        return null;
    }
}
