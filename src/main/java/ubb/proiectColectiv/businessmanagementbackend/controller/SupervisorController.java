package ubb.proiectColectiv.businessmanagementbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ubb.proiectColectiv.businessmanagementbackend.service.SupervisorService;

@RestController
public class SupervisorController {

    @Autowired
    SupervisorService supervisorService;

    public SupervisorController(SupervisorService supervisorService) {
        this.supervisorService = supervisorService;
    }

    @GetMapping(value = "/supervisor/approvals")
    public ResponseEntity<String> get_Approvals() {
        return null;
    }

    @GetMapping(value = "/supervisor/requests")
    public ResponseEntity<String> get_Requests(@RequestBody String content) {
        return null;
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
