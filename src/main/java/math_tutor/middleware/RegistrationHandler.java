package math_tutor.middleware;

import math_tutor.backend.RegistrationService;

public class RegistrationHandler {
    private RegistrationService registrationService;

    public RegistrationHandler() {
        this.registrationService = new RegistrationService();
    }

    public boolean handleRegistration(String name, String dob, String guardianName, String guardianContact, String username, String password) {
        return registrationService.registerStudent(name, dob, guardianName, guardianContact, username, password);
    }
}
