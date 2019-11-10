package ubb.proiectColectiv.businessmanagementbackend.controller;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Controller {

    public void get() throws IOException {
        // Fetch the service account key JSON file contents
        FileInputStream serviceAccount = new FileInputStream("./src/main/resources/proiect-colectiv-85e07-firebase-adminsdk-2ko40-fe4a9f6285.json");

        // Initialize the app with a service account, granting admin privileges
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://proiect-colectiv-85e07.firebaseio.com")
                .build();
        FirebaseApp.initializeApp(options);

        // As an admin, the app has access to read and write all data, regardless of Security Rules
        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference("User");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Object document = dataSnapshot.getValue();
                System.out.println(document);
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }
}

