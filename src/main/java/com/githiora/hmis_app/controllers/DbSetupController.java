package com.githiora.hmis_app.controllers;

import com.githiora.hmis_app.configs.ConfigService;
import com.githiora.hmis_app.configs.DbConfig;
import java.sql.DriverManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class DbSetupController {
    @FXML private TextField hostField;
@FXML private TextField portField;
@FXML private TextField dbField;
@FXML private TextField userField;
@FXML private PasswordField passField;
@FXML private Label statusLabel;

@FXML
private void onSave() {
    try {
        DbConfig cfg = new DbConfig();
        cfg.host = hostField.getText();
        cfg.port = Integer.parseInt(portField.getText());
        cfg.database = dbField.getText();
        cfg.username = userField.getText();
        cfg.password = passField.getText();

        testConnection(cfg);

        ConfigService.save(cfg);

        statusLabel.setText("✅ Saved successfully. Restart app.");

    } catch (Exception e) {
        statusLabel.setText("❌ " + e.getMessage());
    }
}

private void testConnection(DbConfig cfg) throws Exception {
    String url = String.format(
        "jdbc:mysql://%s:%d/%s?useSSL=false&serverTimezone=UTC",
        cfg.host, cfg.port, cfg.database
    );

    DriverManager.getConnection(
        url, cfg.username, cfg.password
    ).close();
}

}
