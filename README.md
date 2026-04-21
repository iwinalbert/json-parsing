# JSON to Database

This is a simple Java application that demonstrates parsing a JSON file and saving its contents to a MySQL database.

## Features
- **JSON Parsing**: Uses Jackson to read `dummy.json`.
- **POJO Mapping**: Maps JSON data to a `User` object.
- **Database Integration**: Uses JDBC to save and verify data in MySQL.

## Setup
1. Create a database named `testdb` in your MySQL server.
2. Create the `users` table manually:
   ```sql
   CREATE TABLE users (
       id INT PRIMARY KEY,
       name VARCHAR(255),
       email VARCHAR(255)
   );
   ```
3. Update the database credentials in `src/main/java/com/example/App.java`:
   ```java
   static String url = "jdbc:mysql://localhost:3306/testdb";
   static String user = "root";
   static String passwd = "your_password";
   ```

## Running the Application
Use Maven to compile and run the app:
```bash
mvn compile
mvn exec:java
```

## Project Structure
- `dummy.json`: Input data file.
- `src/main/java/com/example/App.java`: Main logic for parsing and DB operations.
- `src/main/java/com/example/User.java`: User model (POJO).
