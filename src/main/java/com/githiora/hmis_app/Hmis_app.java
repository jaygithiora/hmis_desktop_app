
package com.githiora.hmis_app;

import com.githiora.hmis_app.configs.ConfigService;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Hmis_app extends Application {

   private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        Font.loadFont(getClass().getResourceAsStream("/fonts/Poppins-Black.ttf"),12);
        Font.loadFont(getClass().getResourceAsStream("/fonts/Poppins-BlackItalic.ttf"),12);
        Font.loadFont(getClass().getResourceAsStream("/fonts/Poppins-ExtraBold.ttf"),12);
        Font.loadFont(getClass().getResourceAsStream("/fonts/Poppins-ExtraBoldItalic.ttf"),12);
        Font.loadFont(getClass().getResourceAsStream("/fonts/Poppins-ExtraLight.ttf"),12);
        Font.loadFont(getClass().getResourceAsStream("/fonts/Poppins-ExtraLightItalic.ttf"),12);
        Font.loadFont(getClass().getResourceAsStream("/fonts/Poppins-Italic.ttf"),12);
        Font.loadFont(getClass().getResourceAsStream("/fonts/Poppins-Light.ttf"),12);
        Font.loadFont(getClass().getResourceAsStream("/fonts/Poppins-LightItalic.ttf"),12);
        Font.loadFont(getClass().getResourceAsStream("/fonts/Poppins-Regular.ttf"),12);
        Font.loadFont(getClass().getResourceAsStream("/fonts/Poppins-Medium.ttf"),12);
        Font.loadFont(getClass().getResourceAsStream("/fonts/Poppins-MediumItalic.ttf"),12);
        Font.loadFont(getClass().getResourceAsStream("/fonts/Poppins-SemiBold.ttf"),12);
        Font.loadFont(getClass().getResourceAsStream("/fonts/Poppins-SemiBoldItalic.ttf"),12);
        Font.loadFont(getClass().getResourceAsStream("/fonts/Poppins-Bold.ttf"),12);
        Font.loadFont(getClass().getResourceAsStream("/fonts/Poppins-BoldItalic.ttf"),12);
        Font.loadFont(getClass().getResourceAsStream("/fonts/Poppins-Thin.ttf"),12);
        Font.loadFont(getClass().getResourceAsStream("/fonts/Poppins-ThinItalic.ttf"),12);
        
        System.out.println("Wacha tuone bana:"+Font.getFamilies().contains("Poppins"));
        Font.getFamilies().stream()
    .filter(f -> f.toLowerCase().contains("poppins"))
    .forEach(System.out::println);

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
        scene.getRoot().setStyle("-fx-font-family: 'Poppins Regular';");
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
