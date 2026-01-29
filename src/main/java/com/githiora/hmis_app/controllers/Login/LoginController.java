package com.githiora.hmis_app.controllers.Login;

import com.githiora.hmis_app.Hmis_app;
import com.githiora.hmis_app.db.DBConnection;
import java.sql.Connection;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class LoginController {
    @FXML
    private HBox rootContainer;
    
    @FXML
    private VBox leftSection;
    
    @FXML
    private VBox rightSection;
    
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;
    
    private static final double HIDE_LEFT_THRESHOLD = 800; // Hide left section below 800px
    
    @FXML
    public void initialize() {
        // Listen for width changes
        rootContainer.widthProperty().addListener((obs, oldVal, newVal) -> {
            handleResponsiveLayout(newVal.doubleValue());
        });
        
        // Initial check
        handleResponsiveLayout(rootContainer.getWidth());
    }
    
    private void handleResponsiveLayout(double width) {
        if (width < HIDE_LEFT_THRESHOLD) {
            // Hide left section, show only login form
            leftSection.setVisible(false);
            leftSection.setManaged(false);
            
            // Right section takes full width
            rightSection.prefWidthProperty().unbind();
            rightSection.setMaxWidth(Double.MAX_VALUE);
            HBox.setHgrow(rightSection, javafx.scene.layout.Priority.ALWAYS);
            
        } else {
            // Show left section with 4:3 ratio
            leftSection.setVisible(true);
            leftSection.setManaged(true);
            
            // Bind widths to maintain 4:3 ratio
            leftSection.prefWidthProperty().bind(
                rootContainer.widthProperty().multiply(4.5 / 7.0)
            );
            
            rightSection.prefWidthProperty().bind(
                rootContainer.widthProperty().multiply(2.5 / 7.0)
            );
        }
    }

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
    
    @FXML
private void testConnection() {
    try (Connection conn = DBConnection.getConnection()) {
        System.out.println("✅ MySQL connected");
    } catch (Exception e) {
        e.printStackTrace();
    }
}
}

