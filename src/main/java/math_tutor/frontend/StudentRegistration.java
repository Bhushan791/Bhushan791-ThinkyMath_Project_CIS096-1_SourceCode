package math_tutor.frontend;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.effect.DropShadow;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.control.DatePicker;
import math_tutor.middleware.RegistrationHandler;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StudentRegistration {
    private Home home; // Reference to the Home screen, used for navigation
    private RegistrationHandler registrationHandler; // Handles registration logic

    // Constructor for StudentRegistration, initializing the home reference and registration handler
    public StudentRegistration(Home home) {
        this.home = home; // Store reference to the Home instance for navigation purposes
        this.registrationHandler = new RegistrationHandler(); // Initialize the registration handler for processing registration
    }

    // Method to create and return the registration view
    public VBox getView() {
        // Create a VBox container to hold all UI elements vertically
        VBox mainContainer = new VBox(15);
        // Set the background style to a gradient color
        mainContainer.setStyle("-fx-background-color: linear-gradient(to bottom right, #D4E4FF , #73A8E5);");
        // Set padding around the VBox container
        mainContainer.setPadding(new Insets(25));
        // Align the contents to the top center of the VBox
        mainContainer.setAlignment(Pos.TOP_CENTER);

        // Create header label for the registration page with a special font style and color
        Label headerLabel = new Label("✨ Register Now! ✨");
        headerLabel.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 28)); // Set font style, size, and weight
        headerLabel.setStyle("-fx-text-fill: #FF69B4;"); // Set text color

        // Create subtitle label under the header with a different font style and color
        Label subtitleLabel = new Label("Join our lovely community! 🌸");
        subtitleLabel.setFont(Font.font("Comic Sans MS", 14)); // Set font style and size
        subtitleLabel.setStyle("-fx-text-fill: #9370DB;"); // Set subtitle text color

        // Create a button to navigate back to the login page
        Button backButton = createBackToLoginButton();
        // Create a VBox container for the registration form fields
        VBox formContainer = createFormContainer();

        // Create form fields for student name, date of birth, guardian's name, and contact details
        FormField studentNameField = new FormField("Student's Name 📚");
        DatePicker dobDatePicker = createDatePicker("Date of Birth 🎂");
        FormField guardianNameField = new FormField("Guardian's Name 👪");
        FormField guardianContactField = new FormField("Guardian's Contact No. 📞");
        FormField usernameField = new FormField("Username 🌟");

        // Create password fields with toggle visibility for password and confirmation
        PasswordFieldWithToggle passwordField = new PasswordFieldWithToggle("Password 🔒");
        PasswordFieldWithToggle confirmPasswordField = new PasswordFieldWithToggle("Confirm Password 🔒");

        // Create a sign-up button with all necessary form fields for registration
        Button signUpButton = createSignUpButton(studentNameField, dobDatePicker, guardianNameField,
                guardianContactField, usernameField,
                passwordField, confirmPasswordField);

        // Add all form field elements and the sign-up button to the form container
        formContainer.getChildren().addAll(
                studentNameField.getTextField(),
                dobDatePicker,
                guardianNameField.getTextField(),
                guardianContactField.getTextField(),
                usernameField.getTextField(),
                passwordField.getContainer(),
                confirmPasswordField.getContainer(),
                signUpButton
        );

        // Create a VBox container to hold the header and subtitle labels
        VBox headerContainer = new VBox(5, headerLabel, subtitleLabel);
        headerContainer.setAlignment(Pos.CENTER); // Align the header contents to the center

        // Create a StackPane container for the back button, placing it in the top-left corner
        StackPane topContainer = new StackPane(backButton);
        StackPane.setAlignment(backButton, Pos.CENTER_LEFT); // Align back button to the top-left corner

        // Add all UI components (back button, header, and form container) to the main container
        mainContainer.getChildren().addAll(topContainer, headerContainer, formContainer);

        // Return the fully constructed registration view as a VBox
        return mainContainer;
    }

    private Button createBackToLoginButton() {
        Button backButton = new Button("⬅ Back to Login");
        backButton.setOnAction(e -> {
            home.rootLayout.setCenter(new StudentLogin(home).getLoginView());
        });
        backButton.setFont(Font.font("Comic Sans MS", 14));
        backButton.setStyle("-fx-background-color: #FF69B4; -fx-text-fill: white; -fx-font-size: 16px; " +
                "-fx-background-radius: 20; -fx-padding: 10px 20px; -fx-font-weight: bold; -fx-cursor: hand;");

        backButton.setOnMouseEntered(e -> backButton.setStyle("-fx-background-color: #FF1493; -fx-text-fill: white; " +
                "-fx-font-size: 16px; -fx-background-radius: 20; -fx-padding: 10px 20px; -fx-font-weight: bold;"));

        backButton.setOnMouseExited(e -> backButton.setStyle("-fx-background-color: #FF69B4; -fx-text-fill: white; " +
                "-fx-font-size: 16px; -fx-background-radius: 20; -fx-padding: 10px 20px; -fx-font-weight: bold;"));
        return backButton;
    }

    private DatePicker createDatePicker(String placeholder) {
        DatePicker datePicker = new DatePicker();
        datePicker.setPromptText(placeholder);
        datePicker.setStyle(
                "-fx-font-family: 'Comic Sans MS';" +
                        "-fx-font-size: 14px;" +
                        "-fx-background-color: rgba(255, 255, 255, 0.9);" +
                        "-fx-border-color: #FFB6C1;" +
                        "-fx-border-radius: 15;" +
                        "-fx-background-radius: 15;" +
                        "-fx-padding: 10;" +
                        "-fx-prompt-text-fill: #B0A4BE;"
        );
        datePicker.setPrefHeight(40);
        datePicker.setPrefWidth(450);
        return datePicker;
    }

    private VBox createFormContainer() {
        VBox formContainer = new VBox(15);
        formContainer.setStyle(
                "-fx-background-color: rgba(255, 255, 255, 0.7);" +
                        "-fx-border-radius: 15;" +
                        "-fx-background-radius: 15;" +
                        "-fx-padding: 20;"
        );
        formContainer.setMaxWidth(500);
        formContainer.setPrefWidth(500);
        formContainer.setEffect(new DropShadow(10, Color.rgb(0, 0, 0, 0.1)));
        return formContainer;
    }

    private Button createSignUpButton(FormField studentNameField, DatePicker dobDatePicker,
                                      FormField guardianNameField, FormField guardianContactField,
                                      FormField usernameField, PasswordFieldWithToggle passwordField,
                                      PasswordFieldWithToggle confirmPasswordField) {
        Button signUpButton = new Button("Sign Up ✨");
        signUpButton.setFont(Font.font("Comic Sans MS", 16));
        signUpButton.setStyle(
                "-fx-background-color: #FF1493;" +
                        "-fx-text-fill: white;" +
                        "-fx-padding: 12 30;" +
                        "-fx-border-radius: 25;" +
                        "-fx-background-radius: 25;" +
                        "-fx-cursor: hand;"
        );

        signUpButton.setOnAction(e -> {
            String studentName = studentNameField.getTextField().getText().trim();
            LocalDate dob = dobDatePicker.getValue();
            String guardianName = guardianNameField.getTextField().getText().trim();
            String guardianContact = guardianContactField.getTextField().getText().trim();
            String username = usernameField.getTextField().getText().trim();
            String password = passwordField.getPassword();
            String confirmPassword = confirmPasswordField.getPassword();

            if (studentName.isEmpty() || dob == null || guardianName.isEmpty() ||
                    guardianContact.isEmpty() || username.isEmpty() || password.isEmpty()) {
                showAlert("All fields are required! 🌈", Alert.AlertType.ERROR);
                return;
            }

            if (!password.equals(confirmPassword)) {
                showAlert("Passwords don't match! 🌈", Alert.AlertType.ERROR);
                return;
            }

            if (!isValidPhoneNumber(guardianContact)) {
                showAlert("Invalid phone number! 🌈", Alert.AlertType.ERROR);
                return;
            }

            if (!isValidPassword(password)) {
                showAlert("Password must contain at least one uppercase letter and one number! 🌈", Alert.AlertType.ERROR);
                return;
            }

            //Date Format
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            boolean isRegistered = registrationHandler.handleRegistration(
                    studentName, dob.format(dateFormatter), guardianName, guardianContact, username, password
            );

            if (isRegistered) {
                showAlert("Registration Successful! 🎉", Alert.AlertType.INFORMATION);
                home.rootLayout.setCenter(new StudentLogin(home).getLoginView());
            } else {
                showAlert("Registration Failed. Please try again. 🌧️", Alert.AlertType.ERROR);
            }
        });

        return signUpButton;
    }

    // Helper methods for validation
    private boolean isValidPhoneNumber(String phoneNumber) {
        Pattern pattern = Pattern.compile("^\\d{10}$");
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }

    private boolean isValidPassword(String password) {
        Pattern uppercase = Pattern.compile("[A-Z]");
        Pattern number = Pattern.compile("[0-9]");

        return uppercase.matcher(password).find() && number.matcher(password).find();
    }

    private void showAlert(String message, Alert.AlertType type) {
        Alert alert = new Alert(type, message);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    static class FormField {
        private TextField textField;

        public FormField(String placeholder) {
            this.textField = createCuteTextField(placeholder);
        }

        private TextField createCuteTextField(String placeholder) {
            TextField field = new TextField();
            field.setPromptText(placeholder);
            field.setFont(Font.font("Comic Sans MS", 14));
            field.setStyle(
                    "-fx-background-color: rgba(255, 255, 255, 0.9);" +
                            "-fx-border-color: #FFB6C1;" +
                            "-fx-border-radius: 15;" +
                            "-fx-background-radius: 15;" +
                            "-fx-padding: 10;" +
                            "-fx-prompt-text-fill: #B0A4BE;"
            );
            field.setPrefHeight(40);
            field.setPrefWidth(450);
            return field;
        }

        public TextField getTextField() {
            return textField;
        }
    }

    static class PasswordFieldWithToggle {
        private PasswordField passwordField;
        private TextField visibleTextField;
        private Button toggleButton;
        private HBox container;

        public PasswordFieldWithToggle(String placeholder) {
            passwordField = createPasswordField(placeholder);
            visibleTextField = createVisibleTextField(placeholder);
            toggleButton = createToggleButton();
            setupPasswordToggle();
        }

        private PasswordField createPasswordField(String placeholder) {
            PasswordField field = new PasswordField();
            field.setPromptText(placeholder);
            field.setFont(Font.font("Comic Sans MS", 14));
            field.setStyle("-fx-background-color: rgba(255,255,255,0.9); -fx-border-color: #FFB6C1; -fx-border-radius: 15; -fx-background-radius: 15; -fx-padding: 10;");
            field.setPrefHeight(40);
            field.setPrefWidth(390);
            return field;
        }

        private TextField createVisibleTextField(String placeholder) {
            TextField field = new TextField();
            field.setPromptText(placeholder);
            field.setVisible(false);
            field.setManaged(false);
            field.setFont(Font.font("Comic Sans MS", 14));
            field.setStyle("-fx-background-color: rgba(255,255,255,0.9); -fx-border-color: #FFB6C1; -fx-border-radius: 15; -fx-background-radius: 15; -fx-padding: 10;");
            field.setPrefHeight(40);
            field.setPrefWidth(390);
            return field;
        }

        private Button createToggleButton() {
            Button button = new Button("🔒");
            button.setStyle("-fx-background-color: #FF1493; -fx-text-fill: white; -fx-border-radius: 15; -fx-background-radius: 15; -fx-cursor: hand;");
            button.setPrefHeight(40);
            button.setPrefWidth(50);
            return button;
        }

        private void setupPasswordToggle() {
            passwordField.textProperty().bindBidirectional(visibleTextField.textProperty());
            toggleButton.setOnAction(e -> toggleVisibility());

            container = new HBox(5, passwordField, visibleTextField, toggleButton);
            container.setAlignment(Pos.CENTER_LEFT);
        }

        private void toggleVisibility() {
            boolean visible = !visibleTextField.isVisible();
            visibleTextField.setVisible(visible);
            visibleTextField.setManaged(visible);
            passwordField.setVisible(!visible);
            passwordField.setManaged(!visible);
            toggleButton.setText(visible ? "👁" : "🔒");
        }

        public String getPassword() {
            return passwordField.getText();
        }

        public HBox getContainer() {
            return container;
        }
    }
}
