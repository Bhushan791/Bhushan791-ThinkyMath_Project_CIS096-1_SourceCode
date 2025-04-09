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
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import math_tutor.middleware.LoginHandler;
import javafx.application.Platform;

/**
 * Class to handle the student login functionality in the ThinkyMath application.
 * It provides a user interface for students to log in and access their dashboard.
 */
public class StudentLogin {

    private Home home; // Reference to the Home application
    private LoginHandler loginHandler; // Handler for login authentication

    /**
     * Constructor for the StudentLogin class.
     * @param home The instance of the Home class to navigate between screens.
     */
    public StudentLogin(Home home) {
        this.home = home; // Store reference to Home instance
        this.loginHandler = new LoginHandler(); // Initialize LoginHandler
    }

    /**
     * Creates and returns the login view for the student.
     * @return A StackPane containing the login form and related elements.
     */
    public StackPane getLoginView() {
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #D4E4FF , #73A8E5);");

        // Create the main content of the login screen
        VBox content = createContent();
        WaveBackground waveBackground = new WaveBackground();
        Path topWave = waveBackground.createWavePath(Color.web("#FF69B4"), -190, true);
        Path bottomWave = waveBackground.createWavePath(Color.web("#FF69B4"), 190, false);

        // Button to navigate back to the home screen
        Button backToHomeButton = createBackToHomeButton();
        StackPane.setAlignment(backToHomeButton, Pos.TOP_LEFT);
        StackPane.setMargin(backToHomeButton, new Insets(20));

        // Add all elements to the root StackPane
        root.getChildren().addAll(topWave, bottomWave);
        root.getChildren().add(content);
        root.getChildren().add(backToHomeButton);

        return root;
    }

    /**
     * Creates a button that navigates the user back to the home screen.
     * @return A Button instance styled for navigation.
     */
    private Button createBackToHomeButton() {
        Button backButton = new Button("⬅ Back to Home 🏠");
        backButton.setStyle("-fx-background-color: #FF69B4; -fx-text-fill: white; -fx-font-size: 16px; " +
                "-fx-background-radius: 20; -fx-padding: 10px 20px; -fx-font-weight: bold; -fx-cursor: hand;");

        // Hover effect to enhance user experience
        backButton.setOnMouseEntered(e -> backButton.setStyle("-fx-background-color: #FF1493; -fx-text-fill: white; " +
                "-fx-font-size: 16px; -fx-background-radius: 20; -fx-padding: 10px 20px; -fx-font-weight: bold;"));

        backButton.setOnMouseExited(e -> backButton.setStyle("-fx-background-color: #FF69B4; -fx-text-fill: white; " +
                "-fx-font-size: 16px; -fx-background-radius: 20; -fx-padding: 10px 20px; -fx-font-weight: bold;"));

        // Action to perform when the button is clicked
        backButton.setOnAction(e -> {
            home.showHomeScreen(); // Navigate back to the Home screen
        });

        return backButton;
    }

    /**
     * Creates the main content for the login screen, including labels, text fields, and buttons.
     * @return A VBox containing the login form elements.
     */
    private VBox createContent() {
        VBox content = new VBox(35);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(40, 20, 50, 20));

        // Title label for the login screen
        Label title = new Label("ThinkyMath Student Login!! 🚀");
        title.setStyle("-fx-font-size: 36px; -fx-text-fill: #FF69B4; -fx-font-weight: bold; -fx-font-family: 'Comic Sans MS';");

        // Grid for arranging input fields
        GridPane inputGrid = new GridPane();
        inputGrid.setHgap(10);
        inputGrid.setVgap(20);
        inputGrid.setAlignment(Pos.CENTER);

        // Labels for username and password
        Label studentIdLabel = new Label("Username:");
        studentIdLabel.setStyle("-fx-font-weight: bold;-fx-font-size: 20px;-fx-font-family: 'Comic Sans MS';");

        Label passwordLabel = new Label("Password:");
        passwordLabel.setStyle("-fx-font-weight: bold;-fx-font-size: 20px;-fx-font-family: 'Comic Sans MS';");

        // Input fields for username and password
        TextField usernameField = createCuteTextField("📱 Enter Username");
        PasswordField passwordField = createCutePasswordField("🔐 Enter Password");

        // Text field to show the password
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

        // Toggle button to show/hide password
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

        // Buttons for login and registration
        Button loginButton = createCuteButton("🌟 Enter Classroom", "#FF69B4");
        Button registerButton = createCuteButton("✨ New Student", "#4169E1");

        // Add button hover effect
        addButtonAnimations(loginButton);
        addButtonAnimations(registerButton);
        addButtonAnimations(togglePasswordButton);

        // Login button action
        loginButton.setOnAction(e -> {
            TextField currentField = passwordField.isVisible() ? passwordField : passwordVisible;
            String username = usernameField.getText().trim();
            String password = currentField.getText().trim();

            // Check if username or password is empty
            if (username.isEmpty() || password.isEmpty()) {
                // Show error alert if either field is empty
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login Error");
                alert.setHeaderText("Missing Information");
                alert.setContentText("Please enter both username and password!");
                alert.showAndWait();
            } else {
                // Use the handleStudentLogin method from LoginHandler
                boolean isValidLogin = loginHandler.handleStudentLogin(username, password); // Assuming this method does the case-sensitive check

                // Show welcome message or error based on login result
                if (isValidLogin) {
                    showWelcomeMessage(username);
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Login Failed");
                    alert.setHeaderText("Invalid Credentials");
                    alert.setContentText("Incorrect username or password!");
                    alert.showAndWait();
                }
            }
        });

        // Registration button action
        registerButton.setOnAction(e -> {
            StudentRegistration registration = new StudentRegistration(home);
            home.rootLayout.setCenter(registration.getView());
        });

        // Add components to the grid
        inputGrid.add(studentIdLabel, 0, 0);
        inputGrid.add(usernameField, 1, 0);
        inputGrid.add(passwordLabel, 0, 1);
        inputGrid.add(passwordField, 1, 1);
        inputGrid.add(togglePasswordButton, 2, 1); // Add the toggle button in a separate column

        // Add buttons to a horizontal box
        HBox buttonBox = new HBox(20);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(loginButton, registerButton);

        // Add all elements to the content VBox
        content.getChildren().addAll(title, inputGrid, buttonBox);

        return content;
    }

    /**
     * Creates a styled text field for user input.
     * @param placeholder The placeholder text for the text field.
     * @return A TextField instance with specified styles.
     */
    private TextField createCuteTextField(String placeholder) {
        TextField field = new TextField();
        field.setPromptText(placeholder);
        field.setStyle("-fx-background-color: rgba(255,255,255,0.8); -fx-background-radius: 20;" +
                "-fx-border-color:#FF69B4;-fx-border-radius:20;-fx-font-size:16px;-fx-padding:10px;-fx-font-weight:bold;");
        field.setPrefWidth(300);
        return field;
    }

    /**
     * Creates a styled password field for secure text entry.
     * @param placeholder The placeholder text for the password field.
     * @return A PasswordField instance with specified styles.
     */
    private PasswordField createCutePasswordField(String placeholder) {
        PasswordField field = new PasswordField();
        field.setPromptText(placeholder);
        field.setStyle("-fx-background-color: rgba(255,255,255,0.8); -fx-background-radius: 20;" +
                "-fx-border-color:#FF69B4;-fx-border-radius:20;-fx-font-size:16px;-fx-padding:10px;-fx-font-weight:bold;");
        field.setPrefWidth(300);
        return field;
    }

    /**
     * Displays a welcome message after successful login and navigates to the student dashboard.
     * @param username The username of the logged-in student.
     */
    private void showWelcomeMessage(String username) {
        // Capitalize first letter of username
        String capitalizedUsername = username.substring(0, 1).toUpperCase() + username.substring(1);

        // Create welcome message pane
        StackPane welcomePane = new StackPane();
        welcomePane.setStyle("-fx-background-color: linear-gradient(to bottom right, #D4E4FF , #73A8E5);");

        // Create a message box
        VBox messageBox = new VBox(20);
        messageBox.setAlignment(Pos.CENTER);
        messageBox.setPadding(new Insets(30));
        messageBox.setMaxWidth(600);
        messageBox.setMaxHeight(400);
        messageBox.setStyle("-fx-background-color: rgba(255,255,255,0.9); -fx-background-radius: 20; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 5);");

        // Welcome text
        Text welcomeText = new Text("Welcome, Student " + capitalizedUsername + "! 🎉");
        welcomeText.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 32));
        welcomeText.setFill(Color.web("#FF1493"));

        // Loading text
        Text loadingText = new Text("Loading your dashboard...");
        loadingText.setFont(Font.font("Comic Sans MS", 20));
        loadingText.setFill(Color.DARKSLATEGRAY);
        loadingText.setTextAlignment(TextAlignment.CENTER);

        // Add texts to the message box
        messageBox.getChildren().addAll(welcomeText, loadingText);
        welcomePane.getChildren().add(messageBox);

        // Display the welcome message
        home.rootLayout.setCenter(welcomePane);

        // Delay going to dashboard
        String finalUsername = username; // Capture username for the thread
        new Thread(() -> {
            try {
                Thread.sleep(2000); // 2 seconds delay
                Platform.runLater(() -> {
                    // Show student dashboard after welcome message
                    home.showDashboardScreen(finalUsername);
                });
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }).start();
    }

    /**
     * Creates a styled button with the given text and color.
     * @param text The text to display on the button.
     * @param color The background color of the button.
     * @return A Button instance with specified styles.
     */
    private Button createCuteButton(String text, String color) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: " + color + "; -fx-text-fill: white; -fx-font-size: 18px; " +
                "-fx-background-radius: 20; -fx-padding: 10px 30px; -fx-font-weight: bold; -fx-cursor: hand;");
        return button;
    }

    /**
     * Adds mouse hover animations to the button.
     * @param button The button to add animations to.
     */
    private void addButtonAnimations(Button button) {
        String baseStyle = button.getStyle();
        button.setOnMouseEntered(e -> button.setStyle(baseStyle + "-fx-scale-x: 1.05; -fx-scale-y: 1.05;"));
        button.setOnMouseExited(e -> button.setStyle(baseStyle));
    }

    /**
     * Internal class to create wave-like backgrounds for aesthetic appeal.
     */
    static class WaveBackground {
        /**
         * Creates a wave path with the specified color, vertical translation, and direction.
         * @param color The color of the wave.
         * @param translateY The vertical translation of the wave.
         * @param isTop A boolean indicating whether the wave is at the top or bottom.
         * @return A Path representing the wave.
         */
        public Path createWavePath(Color color, int translateY, boolean isTop) {
            Path wavePath = new Path();
            wavePath.setFill(color);

            // Define wave path elements
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
