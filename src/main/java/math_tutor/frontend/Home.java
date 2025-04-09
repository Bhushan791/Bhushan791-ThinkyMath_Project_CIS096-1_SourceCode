package math_tutor.frontend;

import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import math_tutor.backend.TestTableSetup;

import java.util.Objects;

/**
 * Main application class for the ThinkyMath educational platform.
 */
public class Home extends Application {

    // Main layout for managing scenes
    public BorderPane rootLayout;

    /**
     * Displays the student dashboard with the provided username.
     * @param username The username of the student.
     */
    public void showDashboardScreen(String username) {
        StudentDashboard dashboard = new StudentDashboard(this, username); // Pass username
        rootLayout.setCenter(dashboard.createDashboard());
    }

    /**
     * Compatibility method to handle cases where no username is provided.
     */
    public void showDashboardScreen() {
        showDashboardScreen(null); // Handle null case
    }

    @Override
    public void start(Stage primaryStage) {
        // Initialize the main layout
        rootLayout = new BorderPane();

        // Set a gradient background for the root layout
        BackgroundFill backgroundFill = new BackgroundFill(
                new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE,
                        new Stop(0, Color.web("#D4E4FF")),
                        new Stop(1, Color.web("#73A8E5"))),
                CornerRadii.EMPTY, Insets.EMPTY);
        rootLayout.setBackground(new Background(backgroundFill));

        // Set up the main scene
        Scene scene = new Scene(rootLayout, 1024, 768);

        // Set the title of the primary stage
        primaryStage.setTitle("ThinkyMath - Educational Platform");

        // Add the app logo to the title bar
        String logoPath = "file:/D:/ThinkyMath/security/2/src/main/resources/Media/app_logo.png";
        Image appLogo = new Image(logoPath);
        primaryStage.getIcons().add(appLogo);

        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();

        // Show the home screen initially
        showHomeScreen();
    }

    /**
     * Sets the home screen content in the center of the root layout.
     */
    public void showHomeScreen() {
        rootLayout.setCenter(createHomeContent());
    }

    /**
     * Creates the home screen content, including buttons and text.
     * @return A VBox containing the home screen content.
     */
    private VBox createHomeContent() {
        VBox contentBox = new VBox(30);
        contentBox.setAlignment(Pos.CENTER);
        contentBox.setPadding(new Insets(20));

        // Welcome text
        Text welcomeText = new Text("Welcome to ThinkyMath");
        welcomeText.setFont(Font.font("Comic Sans MS", 42));
        welcomeText.setFill(Color.DARKSLATEGRAY);

        // Subheader text
        Text subHeaderText = new Text("Let's Learn With Us");
        subHeaderText.setFont(Font.font("Comic Sans MS", 24));
        subHeaderText.setFill(Color.DARKSLATEGRAY);

        // Role selection text
        Text roleText = new Text("Please select your role to continue");
        roleText.setFont(Font.font("comic sans", 20));
        roleText.setFill(Color.DARKSLATEGRAY);

        // Box for buttons
        HBox buttonBox = new HBox(40);
        buttonBox.setAlignment(Pos.CENTER);

        // Create styled buttons for teacher and student roles
        Button teacherButton = createStyledButton("Teacher");
        Button studentButton = createStyledButton("Student");

        buttonBox.getChildren().addAll(teacherButton, studentButton);

        // Button action for Student
        studentButton.setOnAction(e -> {
            StudentLogin studentLogin = new StudentLogin(this); // Pass Home instance
            rootLayout.setCenter(studentLogin.getLoginView()); // Set login view in center
        });

        contentBox.getChildren().addAll(welcomeText, subHeaderText, roleText, buttonBox);

        // Button action for Teacher
        teacherButton.setOnAction(e -> {
            TeacherLogin teacherLogin = new TeacherLogin(this); // Pass Home instance
            rootLayout.setCenter(teacherLogin.getLoginView()); // Set teacher login view in center
        });

        return contentBox;
    }

    /**
     * Utility method to create a styled button with specific design properties.
     * @param text The text to display on the button.
     * @return A styled Button instance.
     */
    private Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setPrefSize(180, 70);

        // Set button style properties
        button.setStyle("-fx-background-color: hotpink; -fx-text-fill: white; -fx-font-size: 18px; " +
                "-fx-padding: 15px 30px; -fx-background-radius: 50; -fx-font-weight: bold; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 8, 0, 0, 4);");

        button.setCursor(Cursor.HAND);

        // Mouse enter event handler to change button style and scale
        button.setOnMouseEntered(e -> {
            button.setStyle("-fx-background-color: rgb(199, 21, 133); -fx-text-fill: white; -fx-font-size: 18px; " +
                    "-fx-padding: 15px 30px; -fx-background-radius: 50; -fx-font-weight: bold; " +
                    "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.5), 10, 0, 0, 5);");
            ScaleTransition st = new ScaleTransition(Duration.millis(150), button);
            st.setToX(1.05);
            st.setToY(1.05);
            st.play();
        });

        // Mouse exit event handler to reset button style and scale
        button.setOnMouseExited(e -> {
            button.setStyle("-fx-background-color: hotpink; -fx-text-fill: white; -fx-font-size: 18px; " +
                    "-fx-padding: 15px 30px; -fx-background-radius: 50; -fx-font-weight: bold; " +
                    "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 8, 0, 0, 4);");
            ScaleTransition st = new ScaleTransition(Duration.millis(150), button);
            st.setToX(1.0);
            st.setToY(1.0);
            st.play();
        });

        return button;
    }

    /**
     * Displays the teacher dashboard with the provided teacher name.
     * @param teacherName The name of the teacher.
     */
    public void showTeacherDashboard(String teacherName) {
        TeacherDashboard dashboard = new TeacherDashboard(this, teacherName);
        rootLayout.setCenter(dashboard.createDashboard());
    }

    public static void main(String[] args) {
        // Ensure the tests table is set up before launching the application
        TestTableSetup.setupTestsTable();
        launch(args); // Start JavaFX application
    }
}
