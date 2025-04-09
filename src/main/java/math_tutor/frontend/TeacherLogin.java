package math_tutor.frontend;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import math_tutor.middleware.LoginHandler; // Import LoginHandler

public class TeacherLogin {

    private Home home; // Reference to the Home application, used to navigate between screens
    private LoginHandler loginHandler; // Instance of LoginHandler to manage login functionality

    // Constructor for TeacherLogin
    public TeacherLogin(Home home) {
        this.home = home; // Store reference to the Home instance for navigation
        this.loginHandler = new LoginHandler(); // Initialize LoginHandler to manage login functionality
    }

    // Method to create and return the login view for teachers
    public StackPane getLoginView() {
        StackPane root = new StackPane(); // Create a StackPane as the root layout for the login screen
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #D4E4FF , #73A8E5);"); // Set a gradient background style

        VBox content = createContent(); // Create the main content (title, input fields, buttons)

        // Create wave-shaped background decorations (reused from StudentLogin)
        StudentLogin.WaveBackground waveBackground = new StudentLogin.WaveBackground();
        Path topWave = waveBackground.createWavePath(Color.web("#FF69B4"), -190, true); // Create top wave decoration
        Path bottomWave = waveBackground.createWavePath(Color.web("#FF69B4"), 190, false); // Create bottom wave decoration

        Button backToHomeButton = createBackToHomeButton(); // Create a "Back to Home" button
        StackPane.setAlignment(backToHomeButton, Pos.TOP_LEFT); // Position the button at the top-left corner
        StackPane.setMargin(backToHomeButton, new Insets(20)); // Add margin around the button for better spacing

        // Add all components (waves, content, and back button) to the root layout
        root.getChildren().addAll(topWave, bottomWave); // Add wave decorations
        root.getChildren().add(content); // Add main content (title, input fields, etc.)
        root.getChildren().add(backToHomeButton); // Add the "Back to Home" button

        return root; // Return the fully constructed login view as a StackPane
    }


    private Button createBackToHomeButton() {
        Button backButton = new Button("⬅ Back to Home 🏠");
        backButton.setStyle("-fx-background-color: #FF69B4; -fx-text-fill: white; -fx-font-size: 16px; " +
                "-fx-background-radius: 20; -fx-padding: 10px 20px; -fx-font-weight: bold; -fx-cursor: hand;");

        backButton.setOnMouseEntered(e -> backButton.setStyle("-fx-background-color: #FF1493; -fx-text-fill: white; " +
                "-fx-font-size: 16px; -fx-background-radius: 20; -fx-padding: 10px 20px; -fx-font-weight: bold;"));

        backButton.setOnMouseExited(e -> backButton.setStyle("-fx-background-color: #FF69B4; -fx-text-fill: white; " +
                "-fx-font-size: 16px; -fx-background-radius: 20; -fx-padding: 10px 20px; -fx-font-weight: bold;"));

        backButton.setOnAction(e -> {
            home.showHomeScreen(); // Navigate back to the Home screen
        });

        return backButton;
    }

    private VBox createContent() {
        VBox content = new VBox(35);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(40, 20, 50, 20));

        Label title = new Label("ThinkyMath Teacher Login!! 🚀");
        title.setStyle("-fx-font-size: 36px; -fx-text-fill: #FF69B4; -fx-font-weight: bold; -fx-font-family: 'Comic Sans MS';");

        GridPane inputGrid = new GridPane();
        inputGrid.setHgap(10);
        inputGrid.setVgap(20);
        inputGrid.setAlignment(Pos.CENTER);

        Label emailLabel = new Label("Email:");
        emailLabel.setStyle("-fx-font-weight: bold;-fx-font-size: 20px;-fx-font-family: 'Comic Sans MS';");

        Label passwordLabel = new Label("Password:");
        passwordLabel.setStyle("-fx-font-weight: bold;-fx-font-size: 20px;-fx-font-family: 'Comic Sans MS';");

        TextField emailField = createCuteTextField("📧 Enter Email");
        PasswordField passwordField = createCutePasswordField("🔐 Enter Password");

        // Create a text field that will show the password when toggled
        TextField passwordVisible = new TextField();
        passwordVisible.setVisible(false);
        passwordVisible.setManaged(false); // Don't take up layout space when invisible
        passwordVisible.setPromptText("🔐 Enter Password");
        passwordVisible.setStyle("-fx-background-color: rgba(255,255,255,0.8); -fx-background-radius: 20;" +
                "-fx-border-color:#FF69B4;-fx-border-radius:20;-fx-font-size:16px;-fx-padding:10px;-fx-font-weight:bold;");
        passwordVisible.setPrefWidth(300);

        // Sync the password field and text field
        passwordField.textProperty().addListener((observable, oldValue, newValue) -> {
            passwordVisible.setText(newValue);
        });

        passwordVisible.textProperty().addListener((observable, oldValue, newValue) -> {
            passwordField.setText(newValue);
        });

        // Create a simple password toggle button
        Button togglePasswordButton = new Button("🔒");
        togglePasswordButton.setStyle("-fx-background-color: #FF69B4; -fx-text-fill: white; -fx-font-size: 16px; " +
                "-fx-background-radius: 20; -fx-padding: 8px 12px; -fx-font-weight: bold; -fx-cursor: hand;");

        // Toggle password visibility
        togglePasswordButton.setOnAction(e -> {
            boolean showPassword = !passwordVisible.isVisible();
            passwordVisible.setVisible(showPassword);
            passwordField.setVisible(!showPassword);
            passwordField.setManaged(!showPassword);
            passwordVisible.setManaged(showPassword);

            // Update the toggle button text based on visibility
            togglePasswordButton.setText(showPassword ? "👁" : "🔒");

            // Add the correct field to the grid
            if (showPassword) {
                // Remove password field and add visible field
                inputGrid.getChildren().remove(passwordField);
                inputGrid.add(passwordVisible, 1, 1);
            } else {
                // Remove visible field and add password field
                inputGrid.getChildren().remove(passwordVisible);
                inputGrid.add(passwordField, 1, 1);
            }
        });

        Button loginButton = createCuteButton("🌟 Login", "#FF69B4");
        loginButton.setOnAction(e -> {
            String email = emailField.getText().trim();
            String password = passwordField.getText().trim();

            if (email.isEmpty() || password.isEmpty()) {
                // Show error alert if either field is empty
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login Error");
                alert.setHeaderText("Missing Information");
                alert.setContentText("Please enter both email and password!");
                alert.showAndWait();
            } else {
                // Call login handler
                boolean isValidLogin = loginHandler.handleTeacherLogin(email, password);
                if (isValidLogin) {
                    // Show welcome message before redirecting to dashboard
                    showWelcomeMessage(email);
                } else {
                    // Show login failed alert
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Login Failed");
                    alert.setHeaderText("Invalid Credentials");
                    alert.setContentText("Incorrect email or password!");
                    alert.showAndWait();
                }
            }
        });

        inputGrid.add(emailLabel, 0, 0);
        inputGrid.add(emailField, 1, 0);
        inputGrid.add(passwordLabel, 0, 1);
        inputGrid.add(passwordField, 1, 1);
        inputGrid.add(togglePasswordButton, 2, 1); // Add the toggle button in a separate column

        HBox buttonBox = new HBox(20);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(loginButton);

        content.getChildren().addAll(title, inputGrid, buttonBox);

        return content;
    }

    private TextField createCuteTextField(String placeholder) {
        TextField field = new TextField();
        field.setPromptText(placeholder);
        field.setStyle("-fx-background-color: rgba(255,255,255,0.8); -fx-background-radius: 20;" +
                "-fx-border-color:#FF69B4;-fx-border-radius:20;-fx-font-size:16px;-fx-padding:10px;-fx-font-weight:bold;");
        field.setPrefWidth(300);
        return field;
    }

    private PasswordField createCutePasswordField(String placeholder) {
        PasswordField field = new PasswordField();
        field.setPromptText(placeholder);
        field.setStyle("-fx-background-color: rgba(255,255,255,0.8); -fx-background-radius: 20;" +
                "-fx-border-color:#FF69B4;-fx-border-radius:20;-fx-font-size:16px;-fx-padding:10px;-fx-font-weight:bold;");
        field.setPrefWidth(300);
        return field;
    }

    private void showWelcomeMessage(String email) {
        // Extract name from email (just take everything before @ as username)
        String username = email.contains("@") ? email.split("@")[0] : email;
        // Capitalize first letter of username
        username = username.substring(0, 1).toUpperCase() + username.substring(1);

        // Create welcome message pane
        StackPane welcomePane = new StackPane();
        welcomePane.setStyle("-fx-background-color: linear-gradient(to bottom right, #D4E4FF , #73A8E5);");

        VBox messageBox = new VBox(20);
        messageBox.setAlignment(Pos.CENTER);
        messageBox.setPadding(new Insets(30));
        messageBox.setMaxWidth(600);
        messageBox.setMaxHeight(400);
        messageBox.setStyle("-fx-background-color: rgba(255,255,255,0.9); -fx-background-radius: 20; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 5);");

        Text welcomeText = new Text("Welcome, Teacher " + username + "! 🎉");
        welcomeText.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 32));
        welcomeText.setFill(Color.web("#FF1493"));

        Text loadingText = new Text("Loading your dashboard...");
        loadingText.setFont(Font.font("Comic Sans MS", 20));
        loadingText.setFill(Color.DARKSLATEGRAY);
        loadingText.setTextAlignment(TextAlignment.CENTER);

        messageBox.getChildren().addAll(welcomeText, loadingText);
        welcomePane.getChildren().add(messageBox);

        // Display the welcome message
        home.rootLayout.setCenter(welcomePane);

        // Use a thread to delay going to dashboard
        String finalUsername = username;
        new Thread(() -> {
            try {
                Thread.sleep(2000); // 2 seconds delay
                javafx.application.Platform.runLater(() -> {
                    // Create and show teacher dashboard after welcome message
                    home.showTeacherDashboard(finalUsername);
                });
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }).start();
    }

    private Button createCuteButton(String text, String color) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: " + color + "; -fx-text-fill: white; -fx-font-size: 18px; " +
                "-fx-background-radius: 20; -fx-padding: 10px 30px; -fx-font-weight: bold; -fx-cursor: hand;");
        return button;
    }
}
