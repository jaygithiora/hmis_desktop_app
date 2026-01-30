package com.githiora.hmis_app.controllers;

import com.githiora.hmis_app.Hmis_app;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

public class MainLayoutController {
    
    @FXML
    private StackPane contentArea;

    @FXML
    public void initialize() {
        // Load default view on startup
        loadView("dashboard");
    }

    public void loadView(String viewName) {
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/dashboard/" + viewName + ".fxml")
            );
            
            Parent view = loader.load();
            contentArea.getChildren().clear();
            contentArea.getChildren().add(view);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void loadViewWithPath(String viewName) {
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource(viewName)
            );
            
            Parent view = loader.load();
            contentArea.getChildren().clear();
            contentArea.getChildren().add(view);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showDashboard() {
        loadView("Dashboard");
    }

    @FXML
    private void showUsers() {
        loadView("users");
    }

    @FXML
    private void showSettings() {
        loadView("settings");
    }

    @FXML
    private void handleLogout() {
        try {
            Hmis_app.showLogin();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
