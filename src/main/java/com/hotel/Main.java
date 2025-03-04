package com.hotel;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Main extends Application {

    private static int loginAttempts = 0;
    private static LocalDateTime lockTime;
    private static final int MAX_ATTEMPTS = 3;
    private static final long LOCK_DURATION_MINUTES = 5;

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Charger l'écran de connexion
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
        primaryStage.setTitle("Connexion");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

    public static void login(String username, String password) {
        if (isLocked()) {
            System.out.println("Account is locked. Please try again later.");
            return;
        }

        // Logique de vérification des identifiants
        boolean success = checkCredentials(username, password);

        if (success) {
            System.out.println("Login successful!");
            loginAttempts = 0; // Réinitialiser les tentatives après une connexion réussie
        } else {
            if (loginAttempts < MAX_ATTEMPTS) {
                loginAttempts++;
                System.out.println("Login failed. Attempt " + loginAttempts);
            } else {
                lockTime = LocalDateTime.now();
                System.out.println("Account locked for " + LOCK_DURATION_MINUTES + " minutes.");
            }
        }
    }

    private static boolean isLocked() {
        if (lockTime == null) return false;
        long minutesSinceLock = ChronoUnit.MINUTES.between(lockTime, LocalDateTime.now());
        if (minutesSinceLock >= LOCK_DURATION_MINUTES) {
            lockTime = null; // Débloquer le compte
            loginAttempts = 0; // Réinitialiser les tentatives
            return false;
        }
        return true;
    }

    private static boolean checkCredentials(String username, String password) {
        // Exemple simple de vérification des identifiants
        return "admin".equals(username) && "password".equals(password);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
