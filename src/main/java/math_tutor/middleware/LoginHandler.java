package math_tutor.middleware;

import math_tutor.backend.LoginService; // Import the LoginService class for login validation

public class LoginHandler {
    private LoginService loginService; // Instance of LoginService to validate logins

    // Constructor to initialize the LoginService
    public LoginHandler() {
        this.loginService = new LoginService(); // Instantiate the LoginService to handle login validation
    }

    // Method to handle student login by validating the username and password
    public boolean handleStudentLogin(String username, String password) {
        // Call the validateStudentLogin method from LoginService to check credentials
        return loginService.validateStudentLogin(username, password);
    }

    // Method to handle teacher login by validating the email and password
    public boolean handleTeacherLogin(String email, String password) {
        // Call the validateTeacherLogin method from LoginService to check credentials
        return loginService.validateTeacherLogin(email, password);
    }
}
