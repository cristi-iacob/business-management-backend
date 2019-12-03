package ubb.proiectColectiv.businessmanagementbackend.service;

import org.springframework.stereotype.Service;
import ubb.proiectColectiv.businessmanagementbackend.model.User;
import ubb.proiectColectiv.businessmanagementbackend.utils.FirebaseUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Service
public class SupervisorService {
	public List <String> getPendingRequests() {
		List < String > retList = new ArrayList<>();

		HashMap< String, Object > users = (HashMap) FirebaseUtils.getUpstreamData(Arrays.asList("User"));

		for (String id : users.keySet()) {
			HashMap < String, Object > attributes = (HashMap) FirebaseUtils.getUpstreamData(Arrays.asList("User", id));

			if ((Boolean) attributes.get("approvedStatus") == false) {
				retList.add((String) attributes.get("email"));
			}
		}

		return retList;
	}

	private Boolean isSupervisor(String id) {
		HashMap < String, Object > user = (HashMap) FirebaseUtils.getUpstreamData(Arrays.asList("User", id));

		if (user.get("roleId").toString().compareTo("2") == 0) {
			return true;
		}

		return false;
	}

	public List < String > getUsersForSupervisor(String id) {
		List < String > retList = new ArrayList<>();

		if (!isSupervisor(id)) {
			return null;
		}

		HashMap < String, Object > userData = (HashMap) FirebaseUtils.getUpstreamData(Arrays.asList("User", id, "usersList"));

		for (Object email : userData.values()) {
			retList.add((String) email);
		}

		return retList;
	}
}
