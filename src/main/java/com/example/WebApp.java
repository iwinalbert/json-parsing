package com.example;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinThymeleaf;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WebApp {
    static String url = "jdbc:mysql://localhost:3306/testdb";
    static String user = "root";
    static String passwd = "toor";

    public static void main(String[] args) {
        initDb();

        Javalin app = Javalin.create(config -> {
            config.staticFiles.add("/public");
        }).start(8080);

        app.get("/", ctx -> {
            List<User> users = getUsers();
            ctx.render("templates/index.html", Map.of("users", users));
        });
        app.post("/users", ctx -> {
            try {
                int id = Integer.parseInt(ctx.formParam("id"));
                String name = ctx.formParam("name");
                String email = ctx.formParam("email");
                saveToDatabase(new User(id, name, email));
            } catch (Exception e) {
                System.err.println("Error parsing form data: " + e.getMessage());
            }
            ctx.redirect("/");
        });
        app.get("/api/users", ctx -> {
            ctx.json(getUsers());
        });
    }

    private static void initDb() {
        try (Connection conn = DriverManager.getConnection(url, user, passwd);
             Statement stmt = conn.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS users (" +
                         "id INT PRIMARY KEY, " +
                         "name VARCHAR(255), " +
                         "email VARCHAR(255))";
            stmt.execute(sql);
            System.out.println("Database initialized.");
        } catch (Exception e) {
            System.err.println("DB Initialization failed: " + e.getMessage());
        }
    }

    private static List<User> getUsers() {
        List<User> users = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url, user, passwd)) {
            String selectSQL = "SELECT * FROM users";
            try (Statement stmt = conn.createStatement();
                 var rs = stmt.executeQuery(selectSQL)) {
                while (rs.next()) {
                    users.add(new User(rs.getInt("id"), rs.getString("name"), rs.getString("email")));
                }
            }
        } catch (Exception e) {
            System.err.println("Error fetching users: " + e.getMessage());
        }
        return users;
    }

    private static void saveToDatabase(User u) {
        try (Connection conn = DriverManager.getConnection(url, user, passwd)) {
            String insertSQL = "INSERT INTO users (id, name, email) VALUES (?, ?, ?) " +
                               "ON DUPLICATE KEY UPDATE name = VALUES(name), email = VALUES(email)";
            try (PreparedStatement prest = conn.prepareStatement(insertSQL)) {
                prest.setInt(1, u.getId());
                prest.setString(2, u.getName());
                prest.setString(3, u.getEmail());
                prest.executeUpdate();
            }
        } catch (Exception e) {
            System.err.println("Error saving user: " + e.getMessage());
        }
    }
}
