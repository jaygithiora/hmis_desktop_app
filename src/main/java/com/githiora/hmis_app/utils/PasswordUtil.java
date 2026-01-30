package com.githiora.hmis_app.utils;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {
    
    // Hash a password (same as Laravel Hash::make())
    public static String hashPassword(String plainPassword) {
        // Use BCrypt with default cost (10), same as Laravel default
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(10));
    }
    
    // Verify password against hash (same as Laravel Hash::check())
    public static boolean verifyPassword(String plainPassword, String hashedPassword) {
        try {
            return BCrypt.checkpw(plainPassword, hashedPassword);
        } catch (Exception e) {
            return false;
        }
    }
    
    // Check if password needs rehashing (if cost factor changed)
    public static boolean needsRehash(String hashedPassword) {
        // Check if the hash was created with a different cost factor
        // Laravel default is 10
        return !hashedPassword.startsWith("$2y$10$") && 
               !hashedPassword.startsWith("$2a$10$");
    }
}