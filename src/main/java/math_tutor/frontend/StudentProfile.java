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
import math_tutor.backend.ConnectionDB;
import java.sql.*;

public class StudentProfile extends Application {
    // Enhanced color scheme but keeping the original theme
    private final String themeColor = "#FF6B6B";
    private final String lightColor = "#D4E4FF";
    private final String darkColor = "#73A8E5";
    private final String gradientStart = "#EAEFFF";
    private final String gradientEnd = "#BFD4FF";

    private String username;

    public StudentProfile(String username) {
        this.username = username;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        VBox profileContainer = createProfileContent();
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        Scene scene = new Scene(profileContainer, screenBounds.getWidth(), screenBounds.getHeight());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Student Profile");
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    public VBox createProfileContent() {
        // Main container with beautiful gradient background
        VBox profileContainer = new VBox(50);
        profileContainer.setAlignment(Pos.CENTER);
        profileContainer.setPadding(new Insets(60));
        profileContainer.setStyle("-fx-background-color: linear-gradient(to bottom, " + gradientStart + ", " + gradientEnd + ");");

        // Title with cute styling
        Label titleLabel = new Label("Profile Information");
        titleLabel.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 46));
        titleLabel.setTextFill(Color.web(themeColor));
        titleLabel.setStyle("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 10, 0, 0, 3);" +
                "-fx-background-color: rgba(255,255,255,0.8);" +
                "-fx-background-radius: 25;" +
                "-fx-padding: 15 40;");

        // Profile card with improved styling
        VBox profileCard = new VBox(30);
        profileCard.setAlignment(Pos.CENTER);
        profileCard.setMaxWidth(900);
        profileCard.setMinHeight(400);
        profileCard.setStyle("-fx-background-color: rgba(255,255,255,0.9); " +
                "-fx-background-radius: 30; " +
                "-fx-padding: 40; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.25), 20, 0, 0, 10);");

        // Profile details grid with improved layout
        GridPane profileDetails = new GridPane();
        profileDetails.setAlignment(Pos.CENTER);
        profileDetails.setHgap(50);
        profileDetails.setVgap(30);
        profileDetails.setPadding(new Insets(20));

        // Fetch student data from database (keeping original implementation)
        String studentName = "Unknown";
        String dob = "N/A";
        String guardianName = "N/A";
        String guardianContact = "N/A";

        try (Connection conn = ConnectionDB.getInstance().getConnection()) {
            String query = """
                SELECT name, dob, guardian_name, guardian_contact 
                FROM students 
                WHERE username = ?
            """;
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                studentName = rs.getString("name");
                dob = rs.getString("dob");
                guardianName = rs.getString("guardian_name");
                guardianContact = rs.getString("guardian_contact");
            }
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        }

        // Create a colorful profile with cute bubbles
        String[] labelColors = {"#FF6B6B", "#FF85A1", "#5C8AFF", "#48ADFF", "#5AEAA8"};
        String[] valueColors = {"#FF9D9D", "#FFAAC2", "#7CA2FF", "#70BCFF", "#7AEEB8"};

        // Add profile details with enhanced styling
        profileDetails.add(createColoredLabel("Name:", labelColors[0]), 0, 0);
        profileDetails.add(createColoredLabel(studentName, valueColors[0]), 1, 0);

        profileDetails.add(createColoredLabel("Date of Birth:", labelColors[1]), 0, 1);
        profileDetails.add(createColoredLabel(dob, valueColors[1]), 1, 1);

        profileDetails.add(createColoredLabel("Guardian Name:", labelColors[2]), 0, 2);
        profileDetails.add(createColoredLabel(guardianName, valueColors[2]), 1, 2);

        profileDetails.add(createColoredLabel("Guardian Contact:", labelColors[3]), 0, 3);
        profileDetails.add(createColoredLabel(guardianContact, valueColors[3]), 1, 3);

        profileDetails.add(createColoredLabel("Username:", labelColors[4]), 0, 4);
        profileDetails.add(createColoredLabel(username, valueColors[4]), 1, 4);

        profileCard.getChildren().add(profileDetails);
        profileContainer.getChildren().addAll(titleLabel, profileCard);
        VBox.setVgrow(profileCard, Priority.ALWAYS);
        return profileContainer;
    }

    private Label createColoredLabel(String text, String backgroundColor) {
        Label label = new Label(text);
        label.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 22));
        label.setTextFill(Color.WHITE);
        label.setPadding(new Insets(16, 24, 16, 24));
        label.setMinWidth(220);
        label.setAlignment(Pos.CENTER);
        label.setStyle(
                "-fx-background-color: " + backgroundColor + "; " +
                        "-fx-background-radius: 18; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 8, 0, 0, 3); " +
                        "-fx-border-color: rgba(255,255,255,0.6); " +
                        "-fx-border-width: 2;" +
                        "-fx-border-radius: 18;"
        );
        return label;
    }
}