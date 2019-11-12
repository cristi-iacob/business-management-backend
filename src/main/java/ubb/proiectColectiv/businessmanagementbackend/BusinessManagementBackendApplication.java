package ubb.proiectColectiv.businessmanagementbackend;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileInputStream;
import java.io.IOException;

@SpringBootApplication
public class BusinessManagementBackendApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(BusinessManagementBackendApplication.class, args);

        // Fetch the service account key JSON file contents
        FileInputStream serviceAccount = new FileInputStream("./src/main/resources/proiect-colectiv-85e07-firebase-adminsdk-2ko40-fe4a9f6285.json");

        // Initialize the app with a service account, granting admin privileges
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://proiect-colectiv-85e07.firebaseio.com")
                .build();
        FirebaseApp.initializeApp(options);
    }

}
