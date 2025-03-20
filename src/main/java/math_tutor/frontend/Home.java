package math_tutor.frontend;

import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

import java.util.Objects;

public class Home extends Application {

    public BorderPane rootLayout; // Main layout for scene management

    // New method specifically for student dashboard with reference to Home
    public void showStudentDashboardScreen() {
        StudentDashboard dashboard = new StudentDashboard(this);
        rootLayout.setCenter(dashboard.createDashboard());
    }

    // Keep the original method for compatibility with existing code
    public void showDashboardScreen() {
        StudentDashboard dashboard = new StudentDashboard(this);
        rootLayout.setCenter(dashboard.createDashboard());
    }

    @Override
    public void start(Stage primaryStage) {
        rootLayout = new BorderPane(); // Initialize the main layout

        // Set a gradient background for the root layout
        BackgroundFill backgroundFill = new BackgroundFill(
                new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE,
                        new Stop(0, Color.web("#D4E4FF")),
                        new Stop(1, Color.web("#73A8E5"))),
                CornerRadii.EMPTY, Insets.EMPTY);
        rootLayout.setBackground(new Background(backgroundFill));

        // Set up the main scene
        Scene scene = new Scene(rootLayout, 1024, 768);
        primaryStage.setTitle("ThinkyMath - Educational Platform");
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();

        // Show the home screen initially
        showHomeScreen();
    }

    // Method to set the home screen content in the center of the root layout
    public void showHomeScreen() {
        rootLayout.setCenter(createHomeContent());
    }

    // Method to create the home screen content (buttons, text, etc.)
    private VBox createHomeContent() {
        VBox contentBox = new VBox(30);
        contentBox.setAlignment(Pos.CENTER);
        contentBox.setPadding(new Insets(20));

        Text welcomeText = new Text("Welcome to ThinkyMath");
        welcomeText.setFont(Font.font("Comic Sans MS", 42));
        welcomeText.setFill(Color.DARKSLATEGRAY);

        Text subHeaderText = new Text("Let's Learn With Us");
        subHeaderText.setFont(Font.font("Comic Sans MS", 24));
        subHeaderText.setFill(Color.DARKSLATEGRAY);

        Text roleText = new Text("Please select your role to continue" );
        roleText.setFont(Font.font("comic sans", 20));
        roleText.setFill(Color.DARKSLATEGRAY);

//        VBox emojiDisplay = EmojiHandler.createEmojiDisplay();

        HBox buttonBox = new HBox(40);
        buttonBox.setAlignment(Pos.CENTER);

        Button teacherButton = createStyledButton("Teacher");
        Button studentButton = createStyledButton("Student");

        buttonBox.getChildren().addAll(teacherButton, studentButton);

        // Button action for Student
        studentButton.setOnAction(e -> {
            StudentLogin studentLogin = new StudentLogin(this); // Pass Home instance
            rootLayout.setCenter(studentLogin.getLoginView()); // Set login view in center
        });

        contentBox.getChildren().addAll(welcomeText, subHeaderText, roleText, buttonBox);
        teacherButton.setOnAction(e -> {
            TeacherLogin teacherLogin = new TeacherLogin(this); // Pass Home instance
            rootLayout.setCenter(teacherLogin.getLoginView()); // Set teacher login view in center
        });


        return contentBox;
    }

    // Utility method to create a styled button
    private Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setPrefSize(180, 70);

        button.setStyle("-fx-background-color: hotpink; -fx-text-fill: white; -fx-font-size: 18px; " +
                "-fx-padding: 15px 30px; -fx-background-radius: 50; -fx-font-weight: bold; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 8, 0, 0, 4);");

        button.setCursor(Cursor.HAND);

        button.setOnMouseEntered(e -> {
            button.setStyle("-fx-background-color: rgb(199, 21, 133); -fx-text-fill: white; -fx-font-size: 18px; " +
                    "-fx-padding: 15px 30px; -fx-background-radius: 50; -fx-font-weight: bold; " +
                    "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.5), 10, 0, 0, 5);");
            ScaleTransition st = new ScaleTransition(Duration.millis(150), button);
            st.setToX(1.05);
            st.setToY(1.05);
            st.play();
        });

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

    public void showTeacherDashboard(String teacherName) {
        TeacherDashboard dashboard = new TeacherDashboard(this, teacherName);
        rootLayout.setCenter(dashboard.createDashboard());
    }

    public static void main(String[] args) {
        launch(args);
    }
}