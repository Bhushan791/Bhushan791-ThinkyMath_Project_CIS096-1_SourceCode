package math_tutor.backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

/**
 * This class manages the connection to the MySQL database for the ThinkyMath application.
 * It ensures the database and necessary tables are created, and provides a singleton instance for accessing the database connection.
 */
public class ConnectionDB {
    // Singleton instance of the ConnectionDB class
    private static ConnectionDB instance;

    // The active database connection
    private Connection connection;

    // Database connection properties
    private static final String URL = "jdbc:mysql://localhost:3306/"; // Base URL for MySQL connection
    private static final String DATABASE_NAME = "Thinky_math"; // Name of the database to use
    private static final String USER = "root"; // Database username
    private static final String PASSWORD = "bhushanbhatta"; // Database password

    /**
     * Private constructor to prevent direct instantiation.
     * Initializes the database connection and creates necessary tables.
     */
    private ConnectionDB() {
        try {
            // Step 1: Connect to MySQL without specifying a database to create the database if it doesn't exist
            Connection tempConnection = DriverManager.getConnection(URL, USER, PASSWORD);
            try (Statement statement = tempConnection.createStatement()) {
                // SQL query to create the database if it does not exist
                String createDBQuery = "CREATE DATABASE IF NOT EXISTS " + DATABASE_NAME;
                statement.executeUpdate(createDBQuery);
            }
            // Close the temporary connection
            tempConnection.close();

            // Step 2: Connect to the created database
            connection = DriverManager.getConnection(URL + DATABASE_NAME, USER, PASSWORD);

            // Step 3: Create tables if they do not exist
            createTables();

            // Step 4: Insert a default teacher if the teachers table is empty
            insertDefaultTeacher();

        } catch (SQLException e) {
            // Handle SQL exceptions by throwing a runtime exception with the error message
            throw new RuntimeException("Error connecting to the database", e);
        }
    }

    /**
     * Creates the necessary tables in the database if they do not exist.
     * Currently creates tables for teachers and students.
     * @throws SQLException If an error occurs during table creation.
     */
    private void createTables() throws SQLException {
        try (Statement statement = connection.createStatement()) {
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
        }
    }

    /**
     * Inserts a default teacher into the teachers table if it is empty.
     * @throws SQLException If an error occurs during the insertion process.
     */
    private void insertDefaultTeacher() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            // Check if the teachers table is empty
            String checkTableQuery = "SELECT COUNT(*) FROM teachers";
            try (ResultSet resultSet = statement.executeQuery(checkTableQuery)) {
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
            }
        }
    }

    /**
     * Returns the singleton instance of the ConnectionDB class.
     * Ensures thread safety by synchronizing the creation of the instance.
     * @return The singleton instance of ConnectionDB.
     */
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

    /**
     * Returns the active database connection.
     * If the connection is closed or null, it attempts to reconnect.
     * @return The active database connection.
     */
    public Connection getConnection() {
        // Check if the connection is closed or null
        if (connection == null || isClosed()) {
            try {
                // Reconnect to the database if necessary
                connection = DriverManager.getConnection(URL + DATABASE_NAME, USER, PASSWORD);
            } catch (SQLException e) {
                // Handle SQL exceptions by throwing a runtime exception with the error message
                throw new RuntimeException("Error reconnecting to the database", e);
            }
        }
        return connection;
    }

    /**
     * Checks if the database connection is active.
     * @return True if the connection is not null and not closed, false otherwise.
     */
    public boolean isConnected() {
        return connection != null && !isClosed();
    }

    /**
     * Checks if the database connection is closed.
     * @return True if the connection is closed, false otherwise.
     */
    private boolean isClosed() {
        try {
            return connection.isClosed();
        } catch (SQLException e) {
            // If an exception occurs, assume the connection is closed
            e.printStackTrace();
            return true;
        }
    }

    public static void main(String[] args) {
        // Get the singleton instance of ConnectionDB
        ConnectionDB dbInstance = ConnectionDB.getInstance();

        // Check if the database connection is successful
        if (dbInstance.isConnected()) {
            System.out.println("Database Connected Successfully! ");
        } else {
            System.out.println("Database Connection Failed! ");
        }
    }
}
