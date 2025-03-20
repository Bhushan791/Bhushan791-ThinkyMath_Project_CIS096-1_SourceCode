package math_tutor.frontend;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.geometry.Rectangle2D;

public class StudentProfile extends Application {
    private final String themeColor = "#FF6B6B";
    private final String lightColor = "#D4E4FF";
    private final String darkColor = "#73A8E5";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        VBox profileContainer = createProfileContent();

        // Set up the scene with a background gradient
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        Scene scene = new Scene(profileContainer, screenBounds.getWidth(), screenBounds.getHeight());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Student Profile");
        primaryStage.setMaximized(true); // Maximize the window
        primaryStage.show();
    }

    public VBox createProfileContent() {
        VBox profileContainer = new VBox(40);
        profileContainer.setAlignment(Pos.CENTER);
        profileContainer.setPadding(new Insets(40));
        profileContainer.setStyle("-fx-background-color: linear-gradient(to bottom, " + lightColor + ", " + darkColor + "); " +
                "-fx-background-radius: 19;"); // Gradient background

        // Title
        Label titleLabel = new Label("Profile Information");
        titleLabel.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 40));
        titleLabel.setTextFill(Color.web(themeColor));
        titleLabel.setStyle("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 10, 0, 0, 3);");

        // Profile Card
        VBox profileCard = new VBox(25);
        profileCard.setAlignment(Pos.CENTER);
        profileCard.setStyle("-fx-background-color: #FFFFFF; " +
                "-fx-background-radius: 20; " +
                "-fx-padding: 30; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 15, 0, 0, 8);");

        // Profile Details
        GridPane profileDetails = new GridPane();
        profileDetails.setAlignment(Pos.CENTER);
        profileDetails.setHgap(40);
        profileDetails.setVgap(25);

        // Sample data
        String studentName = "John Doe";
        String dob = "01/01/2005";
        String guardianName = "Jane Doe";
        String guardianContact = "+1234567890";
        String username = "johndoe123";

        // Adding labels to the grid with a professional style and colored boxes
        profileDetails.add(createColoredLabel("Name:", "#64B5F6"), 0, 0);
        profileDetails.add(createColoredLabel(studentName, "#FFB74D"), 1, 0);

        profileDetails.add(createColoredLabel("Date of Birth:", "#4DB6AC"), 0, 1);
        profileDetails.add(createColoredLabel(dob, "#81C784"), 1, 1);

        profileDetails.add(createColoredLabel("Guardian Name:", "#FFD54F"), 0, 2);
        profileDetails.add(createColoredLabel(guardianName, "#64B5F6"), 1, 2);

        profileDetails.add(createColoredLabel("Guardian Contact:", "#FFAB91"), 0, 3);
        profileDetails.add(createColoredLabel(guardianContact, "#FF8A65"), 1, 3);

        profileDetails.add(createColoredLabel("Username:", "#4CAF50"), 0, 4);
        profileDetails.add(createColoredLabel(username, "#FFC107"), 1, 4);

        // Adding components to the profile card
        profileCard.getChildren().add(profileDetails);

        // Adding components to the profile container
        profileContainer.getChildren().addAll(titleLabel, profileCard);

        // Center the profile card within the profile container
        VBox.setVgrow(profileCard, Priority.ALWAYS); // Allow the profile card to grow vertically

        return profileContainer;
    }

    private Label createColoredLabel(String text, String backgroundColor) {
        Label label = new Label(text);
        label.setFont(Font.font("Comic Sans MS", FontWeight.NORMAL, 20));
        label.setTextFill(Color.WHITE);
        label.setPadding(new Insets(15));
        label.setStyle("-fx-background-color: " + backgroundColor + "; " +
                "-fx-background-radius: 15; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 5, 0, 0, 3); " +
                "-fx-border-color: transparent; " +
                "-fx-border-width: 1;");
        return label;
    }
}
