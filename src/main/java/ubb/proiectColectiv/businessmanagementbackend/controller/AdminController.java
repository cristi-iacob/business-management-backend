package ubb.proiectColectiv.businessmanagementbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import ubb.proiectColectiv.businessmanagementbackend.service.UserService;

@RestController
public class AdminController {

    @Autowired
    private UserService service;

    public AdminController(UserService userService) {
        service = userService;
    }

    /**
     * Retrieves all the requests of the admin
     * @return
     */
    @GetMapping(value = "/admin/requests")
    public ResponseEntity<String> getApprovals() {
        return null;
    }

    /**
     * Updates the user based on the admins decision
     * @return
     */
    @PutMapping(value = "/admin/request_user/{user_email}")
    public ResponseEntity<String> user_request(){
        return null;
    }
}
