package ubb.proiectColectiv.businessmanagementbackend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.database.DatabaseReference;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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

    public boolean login(String username, String password) {
        String link = "https://proiect-colectiv-85e07.firebaseio.com/User/" + username + ".json";
        boolean ret = false;

        try {
            URL url = new URL(link);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            int status = con.getResponseCode();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }

            HashMap<Object, Object> user = new ObjectMapper().readValue(content.toString(), HashMap.class);
            String pass = (String) user.get("password");
            if (pass.compareTo(password) == 0) {
                ret = true;
            } else {
                ret = false;
            }

            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            ret = false;
        }

        return ret;
    }
}
