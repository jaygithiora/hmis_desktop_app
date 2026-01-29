package com.githiora.hmis_app.controllers.Login;

import com.githiora.hmis_app.Hmis_app;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void handleLogin() {
         String username = usernameField.getText();
        String password = passwordField.getText();
        
        if (username.isEmpty() || password.isEmpty()) {
            showError("Please enter username and password");
            return;
        }
        
        // Simple validation (replace with your actual authentication)
        if (authenticate(username, password)) {
            try {
               Hmis_app.showMainLayout();
            } catch (Exception e) {
                e.printStackTrace();
                showError("Error loading main screen");
            }
        } else {
            showError("Invalid credentials");
        }
    }private boolean authenticate(String username, String password) {
        // Replace with your actual authentication logic
        return "admin".equals(username) && "password".equals(password);
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Login Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

