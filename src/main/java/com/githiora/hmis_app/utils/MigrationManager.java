package com.githiora.hmis_app.utils;

import com.githiora.hmis_app.configs.ConfigService;
import com.githiora.hmis_app.configs.DbConfig;
import java.io.IOException;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationInfo;
import org.flywaydb.core.api.output.MigrateResult;

public class MigrationManager {
    private static Flyway flyway;

// Initialize Flyway with database configuration
    public static void initialize() throws IOException {
       DbConfig cfg = ConfigService.load();
        String url = String.format(
            "jdbc:mysql://%s:%s/%s?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true",
            cfg.host,
            cfg.port,
            cfg.database
        );
        
        flyway = Flyway.configure()
                .dataSource(url, cfg.username, cfg.password)
                .locations("classpath:db/migration") // Migration files location
                .baselineOnMigrate(true) // Like Laravel's baseline
                .load();
    }
    
    // Run migrations (like: php artisan migrate)
    public static MigrateResult migrate() throws IOException {
        if (flyway == null) {
            initialize();
        }
        return flyway.migrate();
    }
    
    // Rollback migrations (like: php artisan migrate:rollback)
    public static void rollback(int steps) throws IOException {
        if (flyway == null) {
            initialize();
        }
        // Flyway doesn't support rollback in community edition
        // You'd need Flyway Teams or implement custom rollback
        System.out.println("Rollback not supported in Flyway Community Edition");
    }
    
    // Get migration status (like: php artisan migrate:status)
    public static void status() throws IOException {
        if (flyway == null) {
            initialize();
        }
        
        MigrationInfo[] infos = flyway.info().all();
        System.out.println("\n=== Migration Status ===");
        for (MigrationInfo info : infos) {
            System.out.printf("Version: %s | Description: %s | State: %s%n",
                info.getVersion(),
                info.getDescription(),
                info.getState()
            );
        }
    }
    
    // Clean database (like: php artisan migrate:fresh)
    public static void fresh() throws IOException {
        if (flyway == null) {
            initialize();
        }
        flyway.clean();
        flyway.migrate();
    }
}
