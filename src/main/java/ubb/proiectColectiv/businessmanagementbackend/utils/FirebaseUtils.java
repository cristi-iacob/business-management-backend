package ubb.proiectColectiv.businessmanagementbackend.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.core.ApiFuture;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import lombok.SneakyThrows;
import lombok.var;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class FirebaseUtils {

    private static String databaseLink = "https://proiect-colectiv-85e07.firebaseio.com";
    private static Logger logger = LoggerFactory.getLogger(FirebaseUtils.class);

    // TODO: 11-Dec-19 documentation
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

    // TODO: 11-Dec-19 documentation
    public static ApiFuture<Void> setValue(List<String> parameters, Object newValue) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

        for (String parameter : parameters) {
            ref = ref.child(parameter);
        }

        logger.trace(ref.toString());
        return ref.setValueAsync(newValue);
    }

    // TODO: 11-Dec-19 documentation
    public static void removeValue(List<String> parameters) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

        for (String parameter : parameters) {
            ref = ref.child(parameter);
        }
        ref.removeValueAsync();
        logger.trace(ref.toString() + " has been removed");
    }

    /**
     * Method used to retrieve a collection from the specified resource URI.
     *
     * @param parameters String objects used to determine the desired route for the resource.
     *                   All strings are used (from left to right) to create the route.
     *                   ["A", "B", "C"] will result in a path A/B/C.json
     * @param type       Class reference. Used to implicitly cast the type of the elements from the collection.
     * @param <TInner>   Desired type of the collection.
     * @return A list of non-null values found at the specified URI.
     */
    public static <TInner extends Object> List<TInner> getCollectionAsUpstreamData(List<String> parameters, boolean removeNulls, Class<TInner> type) {
        try {
            var content = fetchContentFromRoute(parameters);
            var data = new ObjectMapper().readValue(content, ArrayList.class);
            if (removeNulls) {
                data = (ArrayList) data.stream().filter(map -> map != null).collect(Collectors.toList());
            }
            return data;
        } catch (NullPointerException e) {
            logger.warn("Nothing received from firebase");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <TInner extends Object> HashMap<String, TInner> getNestedCollectionAsUpstreamData(List<String> parameters, boolean removeNulls, Class<TInner> type) {
        try {
            var content = fetchContentFromRoute(parameters);
            var data = new ObjectMapper().readValue(content, HashMap.class);
            return data;
        } catch (NullPointerException e) {
            logger.warn("Nothing received from firebase");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // TODO: 11-Dec-19 documentation
    @SneakyThrows
    private static String fetchContentFromRoute(List<String> parameters) {
        var link = parameters.stream().reduce(databaseLink, (partial, current) -> partial + "/" + current) + ".json";
        var con = (HttpURLConnection) new URL(link).openConnection();
        con.setRequestMethod("GET");

        var in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        var content = in.lines().collect(Collectors.joining());

        in.close();
        con.disconnect();

        return content;
    }

    /**
     * Method used to retrieve a single resource from the specified URI.
     *
     * @param parameters String objects used to determine the desired route for the resource.
     *                   All strings are used (from left to right) to create the route.
     *                   ["A", "B", "C"] will result in a path A/B/C.json
     * @param type       Class reference. Determines the output type, will alter inner object mapping.
     * @param <T>        The type of the fetched resource.
     * @return The nullable resource from the provided URI.
     */
    public static <T extends Object> T getSingleAsUpstream(List<String> parameters, Class<T> type) {
        try {
            var content = fetchContentFromRoute(parameters);
            var data = new ObjectMapper().readValue(content, type);
            return data;
        } catch (NullPointerException e) {
            logger.warn("Nothing received from firebase");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
