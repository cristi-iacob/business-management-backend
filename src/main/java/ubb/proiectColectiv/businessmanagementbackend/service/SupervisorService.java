package ubb.proiectColectiv.businessmanagementbackend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import ubb.proiectColectiv.businessmanagementbackend.model.User;
import ubb.proiectColectiv.businessmanagementbackend.utils.FirebaseUtils;

import java.util.*;

@Service
public class SupervisorService {

    private ObjectMapper mapper = new ObjectMapper();

    /**
     * Retrieves users which have approvedStatus == false
     * @return list of users with appreovedStatus == false
     */
    public List<User> getRegistrationRequests() {
        List<User> unregisteredUsers = new ArrayList<>();
        HashMap<String, User> users = (HashMap) FirebaseUtils.getUpstreamData(Arrays.asList("User"));
        User user;
        for (Map.Entry<String, User> entry : users.entrySet()) {
            user = mapper.convertValue(entry.getValue(), User.class);
            if (user.getApprovedStatus() == false) {
                user.setHashedEmail(entry.getKey());
                unregisteredUsers.add(user);
            }
        }
        return unregisteredUsers;
    }

    /**
     * Checks if user is a supervisor
     * @param hashedEmail
     * @return true if user is supervisor, false otherwise
     */
    public Boolean isSupervisor(String hashedEmail) {
        HashMap<String, Object> user = (HashMap) FirebaseUtils.getUpstreamData(Arrays.asList("User", hashedEmail));
        if (user.get("roleId").toString().compareTo("2") == 0) {
            return true;
        }
        return false;
    }

    // TODO: 11-Dec-19 documentation
    public List<String> getUsersForSupervisor(String id) {
        List<String> retList = new ArrayList<>();
        if (!isSupervisor(id)) {
            return null;
        }
        HashMap<String, Object> userData = (HashMap) FirebaseUtils.getUpstreamData(Arrays.asList("User", id, "usersList"));
        for (Object email : userData.values()) {
            retList.add((String) email);
        }
        return retList;
    }

    /**
     * Sets users approvedStatus to true
     * @param json Json containing users hashed email
     * @throws JsonProcessingException If the recieved json is incorrect
     */
    public void approveRegistrationRequest(String json) throws JsonProcessingException {
        HashMap<String, String> map = mapper.readValue(json, HashMap.class);
        FirebaseUtils.setValue(Arrays.asList("User", map.get("hashedEmail"), "approvedStatus"), true);
    }

    /**
     * Deletes user from Firebase
     * @param json Json containing users hashed email
     * @throws JsonProcessingException If the recieved json is incorrect
     */
    public void rejectRegistrationRequest(String json) throws JsonProcessingException {
        HashMap<String, String> map = mapper.readValue(json, HashMap.class);
        FirebaseUtils.removeValue(Arrays.asList("User", map.get("hashedEmail")));
    }
}
