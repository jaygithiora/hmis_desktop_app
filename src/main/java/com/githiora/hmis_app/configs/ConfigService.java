package com.githiora.hmis_app.configs;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class ConfigService {

    private static final Path CONFIG_DIR
            = Paths.get(System.getProperty("user.home"), ".hmis_app");

    private static final Path CONFIG_FILE
            = CONFIG_DIR.resolve("db.properties");

    public static boolean configExists() {
        return Files.exists(CONFIG_FILE);
    }

    public static void save(DbConfig cfg) throws IOException {
        Files.createDirectories(CONFIG_DIR);

        Properties props = new Properties();
        props.setProperty("host", cfg.host);
        props.setProperty("port", String.valueOf(cfg.port));
        props.setProperty("database", cfg.database);
        props.setProperty("username", cfg.username);
        props.setProperty("password", cfg.password); // see security note below

        try (OutputStream os = Files.newOutputStream(CONFIG_FILE)) {
            props.store(os, "HMIS Database Configuration");
        }
    }

    public static DbConfig load() throws IOException {
        Properties props = new Properties();
        try (InputStream is = Files.newInputStream(CONFIG_FILE)) {
            props.load(is);
        }

        DbConfig cfg = new DbConfig();
        cfg.host = props.getProperty("host");
        cfg.port = Integer.parseInt(props.getProperty("port"));
        cfg.database = props.getProperty("database");
        cfg.username = props.getProperty("username");
        cfg.password = props.getProperty("password");

        return cfg;
    }
}
