package com.hotel;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class LoginController {

    @FXML
    private ImageView backgroundImage;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    @FXML
    public void initialize() {
        // Charge l'image d'arrière-plan depuis les ressources
        Image image = new Image(getClass().getResourceAsStream("/images/imageBackground.jpg"));
        backgroundImage.setImage(image);
    }

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Vérifiez les identifiants (remplacez cela par votre logique)
        if (username.equals("admin") && password.equals("password")) {
            // Connexion réussie, charger l'application principale
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/fxml/main.fxml"));
                Stage stage = (Stage) usernameField.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("Gestion Hôtelière");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // Affichez une alerte d'erreur
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur de connexion");
            alert.setHeaderText(null);
            alert.setContentText("Nom d'utilisateur ou mot de passe incorrect.");
            alert.showAndWait();
        }
    }
}
