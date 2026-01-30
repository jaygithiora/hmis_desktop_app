package com.githiora.hmis_app.controllers;

import com.githiora.hmis_app.Hmis_app;
import com.githiora.hmis_app.configs.ConfigService;
import com.githiora.hmis_app.controllers.Login.LoginController;
import com.githiora.hmis_app.controllers.dashboard.SidebarController;
import com.githiora.hmis_app.utils.MigrationManager;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.BorderPane;
import org.flywaydb.core.api.output.MigrateResult;

public class MainLayoutController {

    @FXML
    private BorderPane mainContainer;

    @FXML
    private StackPane contentArea;

    @FXML
    public void initialize() {
        mainContainer.setBottom(null);
        mainContainer.setLeft(null);
        mainContainer.setTop(null);
        // Load default view on startup
        //loadView("dashboard");
        if (ConfigService.configExists()) {
            loadLoginPage("/login/login.fxml");
            runMigrations();
        } else {
            loadViewWithPath("/DbSetup.fxml");
        }
    }

    private void loadLoginPage(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(fxml)
            );

            Node page = loader.load();

            if (loader.getController() instanceof LoginController login) {
                login.setMainController(this);
            }

            contentArea.getChildren().clear();
            contentArea.getChildren().add(page);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setSidebar() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/dashboard/sidebar/sidebar.fxml")
            );

            Node page = loader.load();

            if (loader.getController() instanceof SidebarController sidebar) {
                sidebar.setMainController(this);
            }

            mainContainer.setLeft(page);
            //contentArea.getChildren().add(page);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    private void runMigrations() {
        try {
            System.out.println("Running database migrations...");
            MigrationManager.initialize();
            MigrateResult result = MigrationManager.migrate();

            System.out.println("Migrations completed successfully!");
            System.out.println("Migrations executed: " + result.migrationsExecuted);

            // Show migration status
            MigrationManager.status();

        } catch (Exception e) {
            System.err.println("Migration failed: " + e.getMessage());
            e.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Migration Warning");
            alert.setHeaderText("Database Migration Issue");
            alert.setContentText("Some migrations failed. Please check database configuration.\n\n"
                    + e.getMessage());
            alert.showAndWait();
        }
    }
}
