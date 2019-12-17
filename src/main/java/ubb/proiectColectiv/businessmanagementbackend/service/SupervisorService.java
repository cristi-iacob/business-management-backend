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

    // TODO: 11-Dec-19 documentation
    public HashMap<String, User> getRegistrationRequests() {

        HashMap<String, User> unregisteredUsers = new HashMap<>();
        HashMap<String, User> users = (HashMap) FirebaseUtils.getUpstreamData(Arrays.asList("User"));
        User unregisteredUser = new User();
        User user;
        System.out.println(users);
        for (Map.Entry<String, User> entry : users.entrySet()) {
            user = mapper.convertValue(entry.getValue(), User.class);
            if (user.getApprovedStatus() == false) {
                unregisteredUsers.put(entry.getKey(), user);
            }
        }
        return unregisteredUsers;
    }

    // TODO: 11-Dec-19 documentation
    private Boolean isSupervisor(String id) {
        HashMap<String, Object> user = (HashMap) FirebaseUtils.getUpstreamData(Arrays.asList("User", id));
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

    // TODO: 11-Dec-19 documentation
    public String approveRegistrationRequest(String email) {
        FirebaseUtils.setValue(Arrays.asList("User", String.valueOf(Objects.hash(email)), "approvedStatus"), "true");
        return "APPROVED";
    }
}
