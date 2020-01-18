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
import ubb.proiectColectiv.businessmanagementbackend.service.TokenService;

import java.util.List;
import java.util.Objects;

@CrossOrigin
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

    // TODO: 11-Dec-19 documentation
    @GetMapping(value = "/supervisor/approvals")
    public ResponseEntity<String> get_Approvals() {
        return null;
    }

    /**
     * Gets all unapproved users
     *
     * @param token Token that the request uses
     * @return Returns all unapporved users in the body of the request
     */
    @GetMapping(value = "/supervisor/registrationRequests")
    public ResponseEntity<String> getRegistrationRequests(@RequestHeader String token) {
        try {
            String email = TokenService.getKeyByToken(token);
            if (!supervisorService.isSupervisor(String.valueOf(Objects.hash(email)))) {
                logger.error("User is not a supervisor");
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
            var approvalRequests = objectMapper.writeValueAsString(supervisorService.getRegistrationRequests());
            logger.info("Sending all approval requests!");
            return new ResponseEntity<>(approvalRequests, HttpStatus.OK);
        } catch (JsonProcessingException e) {
            logger.error("Error at processing the json!");
        } catch (NullPointerException e) {
            logger.error("Token is not in the tokens list");
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.error("Error at sending all unapproved requests!");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    /**
     * Gets all users with unapproved profile edits
     *
     * @param token Token that the request uses
     * @return Returns all unapporved users in the body of the request
     */
    @GetMapping(value = "/supervisor/profileEdits")
    public ResponseEntity<String> getProfileEdits(@RequestHeader String token) {
        try {
            String email = TokenService.getKeyByToken(token);
            if (!supervisorService.isSupervisor(String.valueOf(Objects.hash(email)))) {
                logger.error("User is not a supervisor");
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
            var profileEdits = objectMapper.writeValueAsString(supervisorService.getProfileEdits());
            logger.info("Sending all profile edits!");
            return new ResponseEntity<>(profileEdits, HttpStatus.OK);
        } catch (JsonProcessingException e) {
            logger.error("Error at processing the json!");
        } catch (NullPointerException e) {
            logger.error("Token is not in the tokens list");
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.error("Error at sending all profile edits!");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    /**
     * Checks if token belongs to a user
     *
     * @param token Token that the request uses
     * @return Status code Ok if user is supervisor, INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping(value = "/supervisor/check")
    public ResponseEntity<String> checkIfSupervisor(@RequestHeader String token) {
        try {
            String email = TokenService.getKeyByToken(token);
            if (supervisorService.isSupervisor(String.valueOf(Objects.hash(email)))) {
                logger.info("User is supervisor");
                return new ResponseEntity<>("True", HttpStatus.OK);
            }
        } catch (NullPointerException e) {
            logger.error("Token is not in the tokens list");
        } catch (Exception e) {
            logger.error("Error at checking if user is a supervisor");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
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
     * Calls Service change the appreoved Status of a user
     *
     * @param token Token that the request uses
     * @param json  hashedEmail to be approved
     * @return Status of Ok or Internal_Server_Error
     */
    @PutMapping(value = "/supervisor/approveRegistrationRequest")
    public ResponseEntity<String> approveRegistrationRequest(@RequestHeader String token, @RequestBody String json) {
        try {
            String email = TokenService.getKeyByToken(token);
            if (!supervisorService.isSupervisor(String.valueOf(Objects.hash(email)))) {
                logger.error("User is not a supervisor");
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
            supervisorService.approveRegistrationRequest(json);
            logger.info("Registration Request of user " + json + " has been approved");
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (JsonProcessingException e) {
            logger.error("Error at processing the json!");
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        } catch (NullPointerException e) {
            logger.error("Error at approving user " + json);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Calls Service to deletes user from Firebase
     *
     * @param token Token that the request uses
     * @param json  Json containing a users hashed email
     * @return Status of Ok or Internal_Server_Error
     */
    @PutMapping(value = "/supervisor/rejectRegistrationRequest")
    public ResponseEntity<String> rejectRegistrationRequest(@RequestHeader String token, @RequestBody String json) {
        try {
            String email = TokenService.getKeyByToken(token);
            if (!supervisorService.isSupervisor(String.valueOf(Objects.hash(email)))) {
                logger.error("User is not a supervisor");
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
            supervisorService.rejectRegistrationRequest(json);
            logger.info("Registration Request of user " + json + " has been rejected");
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (NullPointerException | JsonProcessingException e) {
            logger.error("Error at rejecting user " + json);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Gets all blocked users
     *
     * @param token Token that the request uses
     * @return Returns all blocked users in the body of the request
     */
    @GetMapping(value = "/supervisor/blockedUsers")
    public ResponseEntity<String> getBlockedUsers(@RequestHeader String token) {
        try {
            String email = TokenService.getKeyByToken(token);
            if (!supervisorService.isSupervisor(String.valueOf(Objects.hash(email)))) {
                logger.error("User is not a supervisor");
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
            var blockedUsers = objectMapper.writeValueAsString(supervisorService.getBlockedUsers());
            logger.info("Sending all blocked users!");
            return new ResponseEntity<>(blockedUsers, HttpStatus.OK);
        } catch (JsonProcessingException e) {
            logger.error("Error at processing the json!");
        } catch (NullPointerException e) {
            logger.error("Token is not in the tokens list");
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.error("Error at sending all blocked users!");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    /**
     * Calls Service change the appreoved Status of a user
     *
     * @param token Token that the request uses
     * @param json  hashedEmail to be approved
     * @return Status of Ok or Internal_Server_Error
     */
    @PutMapping(value = "/supervisor/approveBlockedUser")
    public ResponseEntity<String> approveBlockedUser(@RequestHeader String token, @RequestBody String json) {
        try {
            String email = TokenService.getKeyByToken(token);
            if (!supervisorService.isSupervisor(String.valueOf(Objects.hash(email)))) {
                logger.error("User is not a supervisor");
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
            supervisorService.approveBlockedUser(json);
            logger.info("Blocked user " + json + " has been unblocked");
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (JsonProcessingException e) {
            logger.error("Error at processing the json!");
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        } catch (NullPointerException e) {
            logger.error("Error at unblocking user " + json);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Calls Service to deletes user from Firebase
     *
     * @param token Token that the request uses
     * @param json  Json containing a users hashed email
     * @return Status of Ok or Internal_Server_Error
     */
    @PutMapping(value = "/supervisor/rejectBlockedUser")
    public ResponseEntity<String> rejectBlockedUser(@RequestHeader String token, @RequestBody String json) {
        try {
            String email = TokenService.getKeyByToken(token);
            if (!supervisorService.isSupervisor(String.valueOf(Objects.hash(email)))) {
                logger.error("User is not a supervisor");
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
            supervisorService.rejectBlockedUser(json);
            logger.info("Blocked user " + json + " has been rejected");
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (NullPointerException | JsonProcessingException e) {
            logger.error("Error at rejecting user " + json);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
