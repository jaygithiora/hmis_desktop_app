
package com.githiora.hmis_app;

import com.githiora.hmis_app.configs.ConfigService;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Hmis_app extends Application {

   private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        primaryStage.setTitle("My Application");
        //if (ConfigService.configExists()) {
            //showLogin();
        //} else {
            //showDbSetup();
        //}
        // Start with login screen
        //showLogin();
        showMainLayout();
    }

    public static void showLogin() throws Exception {
        FXMLLoader loader = new FXMLLoader(Hmis_app.class.getResource("/login/login.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setMaximized(true);
    }

    public static void showMainLayout() throws Exception {
        FXMLLoader loader = new FXMLLoader(Hmis_app.class.getResource("/MainLayout.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setMaximized(true);
    }
    
    private void showDbSetup() throws Exception {
    FXMLLoader loader = new FXMLLoader(Hmis_app.class.getResource("/DbSetup.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
}

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
