package math_tutor.middleware;

import math_tutor.backend.LoginService;

public class LoginHandler {
    private LoginService loginService;

    public LoginHandler() {
        this.loginService = new LoginService();
    }

    public boolean handleStudentLogin(String username, String password) {
        return loginService.validateStudentLogin(username, password);

    }
    public boolean handleTeacherLogin(String email, String password) {
        return loginService.validateTeacherLogin(email, password);
}}
