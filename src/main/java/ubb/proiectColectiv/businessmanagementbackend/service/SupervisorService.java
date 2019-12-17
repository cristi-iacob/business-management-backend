package ubb.proiectColectiv.businessmanagementbackend.service;

import org.springframework.stereotype.Service;
import ubb.proiectColectiv.businessmanagementbackend.utils.FirebaseUtils;

import java.util.*;

@Service
public class SupervisorService {

    // TODO: 11-Dec-19 documentation
    public List<String> getPendingRequests() {
        List<String> retList = new ArrayList<>();
        HashMap<String, Object> users = (HashMap) FirebaseUtils.getUpstreamData(Arrays.asList("User"));
        for (String id : users.keySet()) {
            HashMap<String, Object> attributes = (HashMap) FirebaseUtils.getUpstreamData(Arrays.asList("User", id));
            if ((Boolean) attributes.get("approvedStatus") == false) {
                retList.add((String) attributes.get("email"));
            }
        }
        return retList;
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
    public String approveUserRegistration(String email) {
        FirebaseUtils.setValue(Arrays.asList("User", String.valueOf(Objects.hash(email)), "approvedStatus"), "true");
        return "APPROVED";
    }
}
