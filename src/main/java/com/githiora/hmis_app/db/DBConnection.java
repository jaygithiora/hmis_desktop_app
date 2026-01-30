
    package com.githiora.hmis_app.db;

import com.githiora.hmis_app.configs.ConfigService;
import com.githiora.hmis_app.configs.DbConfig;
import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

     public static Connection getConnection() throws Exception {
        DbConfig cfg = ConfigService.load();

        String url = String.format(
                "jdbc:mysql://%s:%d/%s?useSSL=false&serverTimezone=UTC",
                cfg.host, cfg.port, cfg.database
        );

        return DriverManager.getConnection(
                url, cfg.username, cfg.password
        );
    }
}
