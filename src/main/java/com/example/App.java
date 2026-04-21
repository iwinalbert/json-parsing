package com.example;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.sql.*;

public class App {
    static String url = "jdbc:mysql://localhost:3306/testdb";
    static String user = "root";
    static String passwd = "toor";

    public static void main(String[] args) {
        try {
            File jsonFile = new File("dummy.json");
            ObjectMapper objmap = new ObjectMapper();
            User user = objmap.readValue(jsonFile, User.class);
            System.out.println("Parsed User: " + user);

            saveToDatabase(user);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void saveToDatabase(User u) throws Exception {
        try (Connection conn = DriverManager.getConnection(url, user, passwd)) {
            String insertSQL = "INSERT INTO users (id, name, email) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE name = VALUES(name), email = VALUES(email)";
            try (PreparedStatement prest = conn.prepareStatement(insertSQL)) {
                prest.setInt(1, u.getId());
                prest.setString(2, u.getName());
                prest.setString(3, u.getEmail());
                prest.executeUpdate();
            }
            System.out.println("Data saved to DB");

            String selectSQL = "SELECT * FROM users WHERE id = " + u.getId();
            try (Statement stmt = conn.createStatement();
                 var rs = stmt.executeQuery(selectSQL)) {
                if (rs.next()) {
                    System.out.println("Verified Record in DB: " + rs.getString("name") + " (" + rs.getString("email") + ")");
                }
            }
        }
    }
}
