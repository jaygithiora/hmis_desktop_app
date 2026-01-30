package com.githiora.hmis_app.controllers;

import com.githiora.hmis_app.configs.ConfigService;
import com.githiora.hmis_app.configs.DbConfig;
import java.io.IOException;
import java.sql.DriverManager;
import java.util.Optional;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class DbSetupController {

            DbConfig cfg = new DbConfig();
            
    @FXML
    private TextField hostField;
    @FXML
    private TextField portField;
    @FXML
    private TextField dbField;
    @FXML
    private TextField userField;
    @FXML
    private PasswordField passField;
    @FXML
    private Label statusLabel;
    @FXML
    private Button testConnectionBtn;
    
    @FXML
    private Button saveBtn;
    @FXML
    public void initialize() {
        // Load existing configuration
        loadExistingConfig();
    }
    private void loadExistingConfig() {
        try{
        // Load configuration from file
        DbConfig dbConfig = ConfigService.load();
        
        // Populate fields with saved values
        hostField.setText(dbConfig.host);
        portField.setText(""+dbConfig.port+"");
        dbField.setText(dbConfig.database);
        userField.setText(dbConfig.username);
        passField.setText(dbConfig.password);
        
        System.out.println("Loaded existing database configuration");
        }catch(IOException e){
            showError("❌ " + e.getMessage());
        }
    }
    
    @FXML
    private void onSave() {
        try {
            cfg.host = hostField.getText();
            cfg.port = Integer.parseInt(portField.getText());
            cfg.database = dbField.getText();
            cfg.username = userField.getText();
            cfg.password = Optional.ofNullable(passField.getText()).orElse("");

            testConnection(cfg);

        testConnectionBtn.setDisable(true);
        saveBtn.setDisable(true);
            ConfigService.save(cfg);
        testConnectionBtn.setDisable(false);
        saveBtn.setDisable(false);

            showSuccess("✅ Saved successfully. Restart app.");

        } catch (Exception e) {
            //e.printStackTrace();
            showError("❌ " + e.getMessage());
        }
    }
    
    @FXML
    private void onTestConnection() {
        System.out.println("Checking connection...");
        try {
            cfg.host = hostField.getText();
            cfg.port = Integer.parseInt(portField.getText());
            cfg.database = dbField.getText();
            cfg.username = userField.getText();
            cfg.password = Optional.ofNullable(passField.getText()).orElse("");

            testConnection(cfg);

            showSuccess("✅ Connection successful.");

        } catch (Exception e) {
            //e.printStackTrace();
            showError("❌ Connection Failed" + e.getMessage());
        }
    }

    private void testConnection(DbConfig cfg) throws Exception {
        /*new Thread(() -> {
            boolean success = DatabaseManager.testConnection(host, port, database, username, password);
            
            javafx.application.Platform.runLater(() -> {
                testConnectionBtn.setDisable(false);
                testConnectionBtn.setText("Test Connection");
                
                if (success) {
                    showSuccess("Connection successful!");
                } else {
                    showError("Connection failed! Please check your settings and ensure:\n" +
                             "1. MySQL server is running\n" +
                             "2. Database exists\n" +
                             "3. Credentials are correct");
                }
            });
        }).start();*/
        testConnectionBtn.setDisable(true);
        saveBtn.setDisable(true);
        String url = String.format(
                "jdbc:mysql://%s:%d/%s?useSSL=false&serverTimezone=UTC",
                cfg.host, cfg.port, cfg.database
        );

        DriverManager.getConnection(
                url, cfg.username, cfg.password
        ).close();
        testConnectionBtn.setDisable(false);
        saveBtn.setDisable(false);
    }
    
    
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    private void showWarning(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
