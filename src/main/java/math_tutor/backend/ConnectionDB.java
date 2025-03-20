package math_tutor.backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class ConnectionDB {
    private static ConnectionDB instance;
    private Connection connection;
    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String DATABASE_NAME = "Thinkymath";
    private static final String USER = "root";
    private static final String PASSWORD = "bhushanbhatta";

    private ConnectionDB() {
        try {
            // Step 1: Connect without specifying a database to create it
            Connection tempConnection = DriverManager.getConnection(URL, USER, PASSWORD);
            Statement statement = tempConnection.createStatement();
            String createDBQuery = "CREATE DATABASE IF NOT EXISTS " + DATABASE_NAME;
            statement.executeUpdate(createDBQuery);
            statement.close();
            tempConnection.close();

            // Step 2: Connect to the created database
            connection = DriverManager.getConnection(URL + DATABASE_NAME, USER, PASSWORD);

            // Step 3: Create tables if they don't exist
            createTables();

            // Step 4: Insert default teacher if table is empty
            insertDefaultTeacher();

        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database", e);
        }
    }

    private void createTables() throws SQLException {
        Statement statement = connection.createStatement();

        // Create teachers table
        String createTeachersTableQuery = """
            CREATE TABLE IF NOT EXISTS teachers (
                teacher_id INT AUTO_INCREMENT PRIMARY KEY,
                email VARCHAR(255) UNIQUE NOT NULL,
                password VARCHAR(255) NOT NULL,
                name VARCHAR(255),
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            )
        """;
        statement.executeUpdate(createTeachersTableQuery);

        // Create students table
        String createStudentsTableQuery = """
            CREATE TABLE IF NOT EXISTS students (
                student_id INT AUTO_INCREMENT PRIMARY KEY,
                name VARCHAR(255) NOT NULL,
                dob DATE,
                guardian_name VARCHAR(255) NOT NULL,
                guardian_contact VARCHAR(20) NOT NULL,
                username VARCHAR(255) UNIQUE NOT NULL,
                password VARCHAR(255) NOT NULL,
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            )
        """;
        statement.executeUpdate(createStudentsTableQuery);

        statement.close();
    }

    private void insertDefaultTeacher() throws SQLException {
        Statement statement = connection.createStatement();

        // Check if the teachers table is empty
        String checkTableQuery = "SELECT COUNT(*) FROM teachers";
        ResultSet resultSet = statement.executeQuery(checkTableQuery);
        resultSet.next();
        int count = resultSet.getInt(1);

        // If the table is empty, insert the default teacher
        if (count == 0) {
            String insertTeacherQuery = """
                INSERT INTO teachers (email, password, name)
                VALUES ('Sandhya@gmail.com', 'Sandhya007', 'Sandhya Chaudhary')
            """;
            statement.executeUpdate(insertTeacherQuery);
            System.out.println("Default teacher inserted.");
        } else {
            System.out.println("Teachers table is not empty. Skipping default teacher insertion.");
        }

        statement.close();
    }

    public static ConnectionDB getInstance() {
        if (instance == null) {
            synchronized (ConnectionDB.class) {
                if (instance == null) {
                    instance = new ConnectionDB();
                }
            }
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    public boolean isConnected() {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        ConnectionDB dbInstance = ConnectionDB.getInstance();

        if (dbInstance.isConnected()) {
            System.out.println("Database Connected Successfully! ✅");
        } else {
            System.out.println("Database Connection Failed! ❌");
        }
    }
}
