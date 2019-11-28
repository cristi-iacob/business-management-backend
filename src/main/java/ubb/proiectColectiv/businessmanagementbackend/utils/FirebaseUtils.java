package ubb.proiectColectiv.businessmanagementbackend.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

public class FirebaseUtils {

    private static String databaseLink = "https://proiect-colectiv-85e07.firebaseio.com";
    private static Logger logger = LoggerFactory.getLogger(FirebaseUtils.class);

    @SuppressWarnings("Duplicates")
    public static Object getUpstreamData(List<String> parameters) {
        try {
            String link = databaseLink;

            if (parameters.size() == 0)
                return null;

            if (parameters.size() == 1) {
                link += "/" + parameters.get(0);
            } else {
                for (String parameter : parameters.subList(0, parameters.size() - 1)) {
                    link += "/" + parameter;
                }
            }

            link += ".json";

            URL url = new URL(link);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            int status = con.getResponseCode();
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }

            HashMap data = new ObjectMapper().readValue(content.toString(), HashMap.class);

            in.close();
            if (parameters.size() > 1) {
                return data.get(parameters.get(parameters.size() - 1));
            } else {
                return data;
            }

        } catch (NullPointerException e) {
            logger.warn("Nothing recieved from firebase");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void setValue(List<String> parameters, Object newValue) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

        for (String parameter : parameters) {
            ref = ref.child(parameter);
        }

        logger.trace(ref.toString());
        ref.setValueAsync(newValue);
    }
}
