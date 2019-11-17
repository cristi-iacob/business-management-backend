package ubb.proiectColectiv.businessmanagementbackend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import org.springframework.stereotype.Service;
import ubb.proiectColectiv.businessmanagementbackend.utils.FirebaseUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;

@Service
public class UserService {
    private DatabaseReference databaseReference;

    public Object getTemporaryData() {
        return temporaryData;
    }

    public void setTemporaryData(Object temporaryData) {
        this.temporaryData = temporaryData;
    }

    private Object temporaryData;

    UserService() {
    }

    @SuppressWarnings("Duplicates")
    public boolean login(String username, String password) {
        if (password.compareTo((String) FirebaseUtils.getUpstreamData(Arrays.asList("User", username, "password"))) == 0) {
            return true;
        }

        return false;
    }
}
