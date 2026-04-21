# User Management & Low-Code Demo

This project is a comprehensive Java-based user management system demonstrating various patterns for data ingestion, processing, and visualization. It serves as a "low-code" style demo using the Javalin framework for web operations and Jackson for JSON processing.

## Features

- **JSON Ingestion**: Single and batch processing of JSON data into a MySQL database.
- **Web Portal**: Interactive user management interface using **Javalin** and **Thymeleaf**.
- **Batch Processing**: Efficient database operations using JDBC batches.
- **Auto-Initialization**: Automatic database schema creation on application startup.
- **REST API**: Simple API endpoint to retrieve user data in JSON format.

## Technology Stack

- **Java 17**
- **Javalin**: Low-code web framework.
- **Thymeleaf**: Server-side template engine.
- **MySQL**: Persistent storage.
- **Jackson**: High-performance JSON processing.
- **Maven**: Project management and build tool.

## Setup

### 1. Database Configuration
The application is configured to connect to a MySQL server.
- **URL**: `jdbc:mysql://localhost:3306/testdb`
- **Username**: `root`
- **Password**: `toor`

*Note: Ensure a database named `testdb` exists before running the applications.*

### 2. Dependencies
Install required dependencies using Maven:
```bash
mvn install
```

## Running the Applications

The project includes three distinct entry points for different use cases:

### A. Web Portal (Recommended)
Launches the interactive web interface at [http://localhost:8080](http://localhost:8080).
```bash
mvn exec:java -Dexec.mainClass="com.example.WebApp"
```
*Features automated DB initialization.*

### B. Batch Importer
Processes a collection of users from `dummy.json` using JDBC batching.
```bash
mvn exec:java -Dexec.mainClass="com.example.BatchApp"
```

### C. Single Object Parser
A simple utility to parse a single user from `dummy.json` and save it to the DB.
```bash
mvn exec:java -Dexec.mainClass="com.example.App"
```

## Project Structure

- `src/main/java/com/example/`
  - `WebApp.java`: Main web application with Javalin.
  - `BatchApp.java`: Batch processing logic.
  - `App.java`: Basic JSON parser and JDBC implementation.
  - `User.java`: Data model (POJO).
- `src/main/resources/`
  - `templates/index.html`: Web portal UI template.
- `dummy.json`: Sample data file.
- `tools/mysql.tar.xz`: Database setup assets.
- `pom.xml`: Maven configuration and dependencies.
