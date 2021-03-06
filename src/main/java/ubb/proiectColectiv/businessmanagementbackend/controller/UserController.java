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
import ubb.proiectColectiv.businessmanagementbackend.model.*;
import ubb.proiectColectiv.businessmanagementbackend.service.TokenService;
import ubb.proiectColectiv.businessmanagementbackend.service.UserService;
import ubb.proiectColectiv.businessmanagementbackend.utils.FirebaseUtils;
import ubb.proiectColectiv.businessmanagementbackend.utils.MailServer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@RestController
@CrossOrigin
public class UserController {

    @Autowired
    private UserService service;
    private Logger logger = LoggerFactory.getLogger(UserController.class);
    private ObjectMapper mapper = new ObjectMapper();

    /**
     * Checks if the user already exists based on his username and password approval_status
     *
     * @param content Json with credentials
     * @return Token if login was successful
     */
    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody String content) {
        try {
            User user = mapper.readValue(content, User.class);
            TokenTransport loginStatus = service.login(user.getEmail(), user.getPassword());
            HttpStatus httpStatus = loginStatus.getResponse().equals(LoginResponseValue.SUCCESSFUL) ? HttpStatus.OK : HttpStatus.UNAUTHORIZED;
            ResponseEntity<?> responseEntity = new ResponseEntity<>(loginStatus, httpStatus);

            switch (loginStatus.getResponse()) {
                case SPAM:
                    logger.info("User " + user.getEmail() + " tried to log in again before 10 seconds have passed");
                    break;
                case BLOCKED:
                    logger.info("User " + user.getEmail() + " tried to log in but is blocked");
                    break;
                case UNAPPROVED:
                    logger.info("User " + user.getEmail() + " is unapproved");
                    break;
                case WRONG_PASSWORD:
                    logger.info("User " + user.getEmail() + " tried to log in with wrong password");
                    break;
                case INEXISTENT:
                    logger.info("User " + user.getEmail() + " is not registered");
                    break;
                default:
                    logger.info("User " + user.getEmail() + " logged in with token " + loginStatus.getToken());
            }

            return responseEntity;
        } catch (JsonProcessingException e) {
            logger.error("error parsing login request content");
            return new ResponseEntity<>("ERROR", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Registers a users email and password credentials in the database
     *
     * @param content Json with credentials to register
     * @return Token if registration was successful
     */
    @PostMapping(value = "/register")
    public ResponseEntity<?> register(@RequestBody String content) {
        try {
            User user = mapper.readValue(content, User.class);
            String registerStatus = service.register(user);
            ResponseEntity<?> responseEntity = new ResponseEntity<>(registerStatus, HttpStatus.OK);

            if (registerStatus.equals("EXISTS")) {
                logger.info("User " + user.getEmail() + " is already registered");
            } else {
                MailServer.sendRealEmail();
                logger.info("Email sent to all admins!");
                logger.info("User " + user.getEmail() + " registered as " + Objects.hash(user.getEmail()));
            }
            return responseEntity;
        } catch (JsonProcessingException e) {
            logger.error("error parsing register request content");
            return new ResponseEntity<>("ERROR", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Deletes users token
     *
     * @param token   Active token on this session
     * @return Message of "DELETED", "NOT LOGGED" or "ERROR"
     */
    @DeleteMapping(value = "/logout")
    public ResponseEntity<?> logout(@RequestHeader String token) {
        try {
            String email = TokenService.getKeyByToken(token);
            String logoutStatus = service.logout(email, token);
            if (logoutStatus.equals("LOGGED OUT")) {
                logger.info("User " + email + " logged out with token: " + token);
            } else {
                logger.warn("User " + email + " tried logging out without being logged or having a token");
            }
            return new ResponseEntity<>(logoutStatus, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error at logging out!");
            return new ResponseEntity<>("ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Retrieves personal info of user
     *
     * @param token Active token on this session
     * @param email Email of user
     * @return Personal informations of user as Json
     */
    @GetMapping(value = "/users/userdata/{email}")
    public ResponseEntity<?> getUserPersonalInfo(@RequestHeader("Authorization") String token, @PathVariable String email) {
        try {
            Object userReturned = service.getPersonalData(token, email);
            logger.info("Retrieved personal info from user " + email + " with token " + token);
            return new ResponseEntity<>(mapper.writeValueAsString(userReturned), HttpStatus.OK);
        } catch (JsonProcessingException e) {
            logger.error("error parsing getPersonalInfo request content");
            return new ResponseEntity<>("ERROR", HttpStatus.BAD_REQUEST);
        }
    }

    // TODO: 11-Dec-19 documentation
    // TODO: 17-Dec-19 add authentication check at controller level
    @GetMapping(value = "/users/{email}/projects")
    public ResponseEntity<?> getAllUsers(@RequestHeader("Authorization") String token, @PathVariable String email) {
        try {

            try {
                TokenService.getKeyByToken(token);
            } catch (NullPointerException e) {
                return new ResponseEntity<>("Not Authorized", HttpStatus.UNAUTHORIZED);
            }

            var entries = service.getAllProjectExperienceEntriesForUserWithEmail(email);

            return new ResponseEntity<>(entries, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Oops, something went wrong while retrieving project experience!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // TODO: 19-Dec-19 documentation
    // TODO: 19-Dec-19 token check
    @PostMapping(value = "/users/{email}/create-pending-changes")
    public ResponseEntity<?> registerPendingChange(@RequestHeader("Authorization") String token, @PathVariable String email, @RequestBody List<ChangeModel> changeModels) {
        try {
            TokenService.getKeyByToken(token);
        } catch (NullPointerException e) {
            return new ResponseEntity<>("Not Authorized", HttpStatus.UNAUTHORIZED);
        }

        service.registerPedingingChangesForUserWithEmail(changeModels, email);
        return null;
    }

    @GetMapping(value = "/projects")
    public ResponseEntity<?> getAllProjects(@RequestHeader("Authorization") String token) {
        try {
            try {
                TokenService.getKeyByToken(token);
            } catch (NullPointerException e) {
                return new ResponseEntity<>("Not Authorized", HttpStatus.UNAUTHORIZED);
            }

            var entries = service.getAllPossibleProjects();
            return new ResponseEntity<>(entries, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Oops, something went wrong while retrieving projects!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/levels")
    public ResponseEntity<?> getAllLevels(@RequestHeader("Authorization") String token) {
        try {
            try {
                TokenService.getKeyByToken(token);
            } catch (NullPointerException e) {
                return new ResponseEntity<>("Not Authorized", HttpStatus.UNAUTHORIZED);
            }

            var entries = service.getAllPossibleConsultingLevels();
            return new ResponseEntity<>(entries, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Oops, something went wrong while retrieving consulting levels!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/users/{email}")
    public ResponseEntity<?> getUser(@RequestHeader("Authorization") String token, @PathVariable String email) {
        try {
            try {
                TokenService.getKeyByToken(token);
            } catch (NullPointerException e) {
                return new ResponseEntity<>("Not Authorized", HttpStatus.UNAUTHORIZED);
            }

            var fullUserSpecification = service.getFullUserSpecificationForEmail(email);
            return new ResponseEntity<>(fullUserSpecification, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Oops, something went wrong while retrieving the user!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/users/{email}/diff")
    public ResponseEntity<?> getUserDiff(@RequestHeader("Authorization") String token, @PathVariable String email) {
        try {
            try {
                TokenService.getKeyByToken(token);
            } catch (NullPointerException e) {
                return new ResponseEntity<>("Not Authorized", HttpStatus.UNAUTHORIZED);
            }
            var fullUserSpecification = service.createDiff(email);
            return new ResponseEntity<>(fullUserSpecification, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Oops, something went wrong while retrieving the user diff!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/users/{email}/accept")
    public ResponseEntity<?> acceptChanges(@RequestHeader("Authorization") String token, @PathVariable String email) {
        try {
            try {
                TokenService.getKeyByToken(token);
            } catch (NullPointerException e) {
                return new ResponseEntity<>("Not Authorized", HttpStatus.UNAUTHORIZED);
            }
            service.acceptChanges(email);
            return null;
        } catch (Exception e) {
            return new ResponseEntity<>("Oops, something went wrong while accepting the chan!ges!!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "rpc/patch-experience")
    public ResponseEntity<?> patchExperience(@RequestHeader("Authorization") String token, @RequestBody HashMap<String, Object> map) {
        try {
            try {
                TokenService.getKeyByToken(token);
            } catch (NullPointerException e) {
                return new ResponseEntity<>("Not Authorized", HttpStatus.UNAUTHORIZED);
            }
            ProjectExperienceEntry ret = service.buildProjectExperienceEntryFromMap(map, map.get("newId").toString());
            return new ResponseEntity<>(ret, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Oops, something went wrong while accepting the changes!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/regions")
    public ResponseEntity<?> getAllRegions(@RequestHeader("Authorization") String token) {
        try {
            try {
                TokenService.getKeyByToken(token);
            } catch (NullPointerException e) {
                return new ResponseEntity<>("Not Authorized", HttpStatus.UNAUTHORIZED);
            }
            var regions = UserService.getAllPossibleRegions();
            return new ResponseEntity<>(regions, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Oops, something went wrong while fetching regions!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping(value = "rpc/patch-skill")
    public ResponseEntity<?> patchSkill(@RequestHeader("Authorization") String token, @RequestBody HashMap<String, Object> map) {
        try {
            try {
                TokenService.getKeyByToken(token);
            } catch (NullPointerException e) {
                return new ResponseEntity<>("Not Authorized", HttpStatus.UNAUTHORIZED);
            }
            var skill = UserService.patchSkillWithId(map.get("id").toString());
            return new ResponseEntity<>(skill, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Oops, something went wrong while patching the skill!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/skills")
    public ResponseEntity<?> getAllSkills(@RequestHeader("Authorization") String token) {
        try {
            try {
                TokenService.getKeyByToken(token);
            } catch (NullPointerException e) {
                return new ResponseEntity<>("Not Authorized", HttpStatus.UNAUTHORIZED);
            }
            var skills = UserService.getAllPersistedSkills();
            return new ResponseEntity<>(skills, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Oops, something went wrong pathing the skill with the given id!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
