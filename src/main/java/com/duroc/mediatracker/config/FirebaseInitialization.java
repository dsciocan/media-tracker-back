package com.duroc.mediatracker.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;

/**
 * This configuration is to initialise the FirebaseAuth object
 * When the FirebaseAuth.getInstance() is called it's due to this (At least I think)
 */
@Configuration
public class FirebaseInitialization {

    @Bean
    public FirebaseApp initialization() {
        try{
            FileInputStream serviceAccount =

                    new FileInputStream("src/main/resources/project-media-tracker-firebase-adminsdk-2wn5h-e3ec6e55fb.json");


            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();
            System.out.println("FOUND IN INITIALIZATION");

            // Unclear if the FileInputStream needs to stay open for multiple requests
            // (Should be fine to close at this point)
            serviceAccount.close();
           return FirebaseApp.initializeApp(options);
        } catch (Exception error) {
            error.printStackTrace();
        }
        System.out.println("LOST in the INITIALIZATION");
        return null;
    }


}



