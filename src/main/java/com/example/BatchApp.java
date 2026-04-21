package com.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.sql.*;
import java.util.*;

public class BatchApp {
    static String url = "jdbc:mysql://localhost:3306/testdb";
    static String user = "root";
    static String passwd = "toor";

    public static void main(String[] args) {
        try {
            File jsonFile = new File("dummy.json");
            ObjectMapper objmap = new ObjectMapper();
            ArrayList<User> users = objmap.readValue(jsonFile, new TypeReference<ArrayList<User>>() {});
            System.out.println("Parsed " + users.size() + " users.");

            saveToDatabaseBatch(users);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void saveToDatabaseBatch(List<User> users) throws Exception {
        try (Connection conn = DriverManager.getConnection(url, user, passwd)) {
            String insertSQL = "INSERT INTO users (id, name, email) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE name = VALUES(name), email = VALUES(email)";
            try (PreparedStatement prest = conn.prepareStatement(insertSQL)) {
                conn.setAutoCommit(false);
                for (User u : users) {
                    prest.setInt(1, u.getId());
                    prest.setString(2, u.getName());
                    prest.setString(3, u.getEmail());
                    prest.addBatch();
                }
                prest.executeBatch();
                conn.commit();
                System.out.println("Batch data saved to DB");
            }
        }
    }
}
