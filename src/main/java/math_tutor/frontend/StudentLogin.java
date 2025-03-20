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
import math_tutor.middleware.LoginHandler;

public class StudentLogin {

    private Home home; // Reference to the Home application
    private LoginHandler loginHandler; // Add LoginHandler

    public StudentLogin(Home home) {
        this.home = home; // Store reference to Home instance
        this.loginHandler = new LoginHandler(); // Initialize LoginHandler
    }

    public StackPane getLoginView() {
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #D4E4FF , #73A8E5);");

        VBox content = createContent();
        WaveBackground waveBackground = new WaveBackground();
        Path topWave = waveBackground.createWavePath(Color.web("#FF69B4"), -190, true);
        Path bottomWave = waveBackground.createWavePath(Color.web("#FF69B4"), 190, false);

        Button backToHomeButton = createBackToHomeButton();
        StackPane.setAlignment(backToHomeButton, Pos.TOP_LEFT);
        StackPane.setMargin(backToHomeButton, new Insets(20));

        root.getChildren().addAll(topWave, bottomWave);
        root.getChildren().add(content);
        root.getChildren().add(backToHomeButton);

        return root;
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

        Label title = new Label("ThinkyMath Student Login!! 🚀");
        title.setStyle("-fx-font-size: 36px; -fx-text-fill: #FF69B4; -fx-font-weight: bold; -fx-font-family: 'Comic Sans MS';");

        GridPane inputGrid = new GridPane();
        inputGrid.setHgap(10);
        inputGrid.setVgap(20);
        inputGrid.setAlignment(Pos.CENTER);

        Label studentIdLabel = new Label("Username:");
        studentIdLabel.setStyle("-fx-font-weight: bold;-fx-font-size: 20px;-fx-font-family: 'Comic Sans MS';");

        Label passwordLabel = new Label("Password:");
        passwordLabel.setStyle("-fx-font-weight: bold;-fx-font-size: 20px;-fx-font-family: 'Comic Sans MS';");

        TextField usernameField = createCuteTextField("📱 Enter Username");
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

        Button loginButton = createCuteButton("🌟 Enter Classroom", "#FF69B4");
        Button registerButton = createCuteButton("✨ New Student", "#4169E1");

        // Add button hover effect
        addButtonAnimations(loginButton);
        addButtonAnimations(registerButton);
        addButtonAnimations(togglePasswordButton);

        loginButton.setOnAction(e -> {
            TextField currentField = passwordField.isVisible() ? passwordField : passwordVisible;
            String username = usernameField.getText().trim();
            String password = currentField.getText().trim();

            if (username.isEmpty() || password.isEmpty()) {
                // Show error alert if either field is empty
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login Error");
                alert.setHeaderText("Missing Information");
                alert.setContentText("Please enter both username and password!");
                alert.showAndWait();
            } else {
                // Call login handler
                boolean isValidLogin = loginHandler.handleStudentLogin(username, password);
                if (isValidLogin) {
                    // Show welcome message before redirecting to dashboard
                    showWelcomeMessage(username);
                } else {
                    // Show login failed alert
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Login Failed");
                    alert.setHeaderText("Invalid Credentials");
                    alert.setContentText("Incorrect username or password!");
                    alert.showAndWait();
                }
            }
        });

        registerButton.setOnAction(e -> {
            StudentRegistration registration = new StudentRegistration(home);
            home.rootLayout.setCenter(registration.getView());
        });

        inputGrid.add(studentIdLabel, 0, 0);
        inputGrid.add(usernameField, 1, 0);
        inputGrid.add(passwordLabel, 0, 1);
        inputGrid.add(passwordField, 1, 1);
        inputGrid.add(togglePasswordButton, 2, 1); // Add the toggle button in a separate column

        HBox buttonBox = new HBox(20);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(loginButton, registerButton);

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

    private void showWelcomeMessage(String username) {
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

        javafx.scene.text.Text welcomeText = new javafx.scene.text.Text("Welcome, Student " + username + "! 🎉");
        welcomeText.setFont(javafx.scene.text.Font.font("Comic Sans MS", javafx.scene.text.FontWeight.BOLD, 32));
        welcomeText.setFill(Color.web("#FF1493"));

        javafx.scene.text.Text loadingText = new javafx.scene.text.Text("Loading your dashboard...");
        loadingText.setFont(javafx.scene.text.Font.font("Comic Sans MS", 20));
        loadingText.setFill(javafx.scene.paint.Color.DARKSLATEGRAY);
        loadingText.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);

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
                    // Create and show student dashboard after welcome message
                    home.showDashboardScreen();
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

    private void addButtonAnimations(Button button) {
        String baseStyle = button.getStyle();
        button.setOnMouseEntered(e -> button.setStyle(baseStyle + "-fx-scale-x: 1.05; -fx-scale-y: 1.05;"));
        button.setOnMouseExited(e -> button.setStyle(baseStyle));
    }

    static class WaveBackground {
        public Path createWavePath(Color color, int translateY, boolean isTop) {
            Path wavePath = new Path();
            wavePath.setFill(color);

            wavePath.getElements().addAll(
                    isTop ? new javafx.scene.shape.MoveTo(0, 50) : new javafx.scene.shape.MoveTo(600, 350),
                    isTop ? new javafx.scene.shape.CubicCurveTo(150, -30, 450, 70, 600, 20) :
                            new javafx.scene.shape.CubicCurveTo(450, 400, 150, 300, 0, 360)
            );
            wavePath.setTranslateY(translateY);
            return wavePath;
        }
    }
}
