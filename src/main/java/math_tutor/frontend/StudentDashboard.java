package math_tutor.frontend;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.animation.ScaleTransition;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import java.util.Optional;

public class StudentDashboard {
    private final String themeColor = "#FF6B6B";
    private final String lightColor = "#D4E4FF";
    private final String darkColor = "#73A8E5";
    private final String cardColor = "rgba(40, 44, 52, 0.7)";
    private final String subtitleColor = "#E0E0E0";

    private BorderPane root;
    private Home homeInstance; // Reference to Home instance

    // Constructor that takes a Home instance
    public StudentDashboard(Home homeInstance) {
        this.homeInstance = homeInstance;
    }

    // Default constructor to maintain compatibility with existing code
    public StudentDashboard() {
        // Empty constructor for backward compatibility
    }

    public BorderPane createDashboard() {
        this.root = new BorderPane();
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, " + lightColor + ", " + darkColor + ");");
        root.setTop(createHeader());
        root.setCenter(createMainContent());
        return root;
    }

    private HBox createHeader() {
        Button profileBtn = createNavButton("👤 Profile");
        Button leaderboardBtn = createNavButton("🏆 Leaderboard");
        Button logoutBtn = createNavButton("🚪 Logout");

        // Stars display
        Label starsLabel = new Label("⭐ Stars earned: 125");
        starsLabel.setStyle("-fx-text-fill: #333; -fx-font-size: 16px; -fx-font-weight: bold;");
        starsLabel.setFont(Font.font("Poppins", FontWeight.BOLD, 16));

        profileBtn.setOnAction(e -> showProfileView());
        leaderboardBtn.setOnAction(e -> showLeaderboardView());

        // Set the logout button action to our new method
        logoutBtn.setOnAction(e -> confirmAndLogout());

        // Create parent container
        HBox header = new HBox();
        header.setPadding(new Insets(20));

        // Center buttons container
        HBox centerButtons = new HBox(20);
        centerButtons.setAlignment(Pos.CENTER);
        centerButtons.getChildren().addAll(profileBtn, leaderboardBtn, logoutBtn);
        HBox.setHgrow(centerButtons, Priority.ALWAYS);

        // Right side stars container
        HBox rightStars = new HBox();
        rightStars.setAlignment(Pos.CENTER_RIGHT);
        rightStars.getChildren().add(starsLabel);

        header.getChildren().addAll(centerButtons, rightStars);

        return header;
    }

    // New method specifically for logout confirmation and handling
    private void confirmAndLogout() {
        // Create a new confirmation dialog
        Alert confirmLogout = new Alert(Alert.AlertType.CONFIRMATION);
        confirmLogout.setTitle("Confirm Logout");
        confirmLogout.setHeaderText("Are you sure you want to logout?");
        confirmLogout.setContentText("Your session will be terminated if you choose to logout.");

        // Style the dialog to match the application theme
        DialogPane dialogPane = confirmLogout.getDialogPane();
        dialogPane.setStyle("-fx-background-color: " + lightColor + ";");
        dialogPane.lookup(".header-panel").setStyle("-fx-background-color: " + darkColor + ";");
        dialogPane.lookup(".header-panel .label").setStyle("-fx-text-fill: white; -fx-font-weight: bold;");

        // Show the dialog and wait for response
        Optional<ButtonType> result = confirmLogout.showAndWait();

        // Process the response
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // User confirmed logout, call the new method to navigate to home
            navigateToHomeScreen();
        }
    }

    // New method specifically for navigation to home screen
    private void navigateToHomeScreen() {
        if (homeInstance != null) {
            // Call the Home class method to show home screen
            homeInstance.showHomeScreen();
        } else {
            // Log an error if home instance is not available
            System.err.println("Error: Cannot navigate to home screen - Home instance is not available");
        }
    }

    private void showProfileView() {
        BorderPane profileRoot = new BorderPane();
        profileRoot.setStyle("-fx-background-color: linear-gradient(to bottom right, " + lightColor + ", " + darkColor + ");");

        // Header with back button
        Button backBtn = createNavButton("🔙 Back");
        backBtn.setOnAction(e -> showDashboardView());

        HBox profileHeader = new HBox(backBtn);
        profileHeader.setPadding(new Insets(20));
        profileRoot.setTop(profileHeader);

        // Profile content
        VBox profileContent = new StudentProfile().createProfileContent();
        profileContent.setPadding(new Insets(40));
        profileRoot.setCenter(profileContent);

        root.setTop(profileHeader);
        root.setCenter(profileContent);
    }

    private void showLeaderboardView() {
        BorderPane leaderboardRoot = new BorderPane();
        leaderboardRoot.setStyle("-fx-background-color: linear-gradient(to bottom right, " + lightColor + ", " + darkColor + ");");

        // Header with back button
        Button backBtn = createNavButton("🔙 Back");
        backBtn.setOnAction(e -> showDashboardView());

        HBox leaderboardHeader = new HBox(backBtn);
        leaderboardHeader.setPadding(new Insets(20));
        leaderboardRoot.setTop(leaderboardHeader);

        // Leaderboard content
        VBox leaderboardContent = new Leaderboard().createLeaderboardContent();
        leaderboardContent.setPadding(new Insets(40));
        leaderboardRoot.setCenter(leaderboardContent);

        root.setTop(leaderboardHeader);
        root.setCenter(leaderboardContent);
    }

    private void showDashboardView() {
        root.setTop(createHeader());
        root.setCenter(createMainContent());
    }

    private Button createNavButton(String text) {
        Button btn = new Button(text);
        btn.setStyle("-fx-background-color: rgba(255, 255, 255, 0.2); -fx-text-fill: #333; " +
                "-fx-background-radius: 20; -fx-font-size: 16px; -fx-padding: 10 20;");
        btn.setFont(Font.font("Poppins", FontWeight.BOLD, 14));
        btn.setCursor(Cursor.HAND);

        btn.setOnMouseEntered(e -> {
            btn.setStyle("-fx-background-color: " + themeColor + "; -fx-text-fill: white; " +
                    "-fx-background-radius: 20; -fx-font-size: 16px; -fx-padding: 10 20;");
            playScaleAnimation(btn, 1.05);
        });

        btn.setOnMouseExited(e -> {
            btn.setStyle("-fx-background-color: rgba(255, 255, 255, 0.2); -fx-text-fill: #333; " +
                    "-fx-background-radius: 20; -fx-font-size: 16px; -fx-padding: 10 20;");
            playScaleAnimation(btn, 1.0);
        });

        return btn;
    }

    private StackPane createMainContent() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(30);
        grid.setVgap(30);
        grid.setPadding(new Insets(30));

        // Modified to have 3 cards in a single row (removed Daily Challenge)
        grid.add(createActionCard("Start Learning", "Mathematics Mastery", "#FF8C00", this::startLearning), 0, 0);
        grid.add(createActionCard("Take a Test", "Knowledge Check", "#2196F3", this::takeATest), 1, 0);
        grid.add(createActionCard("View Progress", "Performance Tracking", "#43A047", this::viewProgress), 2, 0);

        StackPane centerPane = new StackPane(grid);
        centerPane.setPadding(new Insets(50));
        return centerPane;
    }

    private void startLearning() {
        // Clear existing content
        root.getChildren().clear();

        // Create GradeSelection content
        GradeSelection gradeSelection = new GradeSelection(this::showDashboardView);
        AnchorPane gradeSelectionContent = gradeSelection.createGradeSelectionContent();
        gradeSelectionContent.setMaxWidth(Double.MAX_VALUE);
        gradeSelectionContent.setMaxHeight(Double.MAX_VALUE);

        // Set GradeSelection content to fill the entire window
        root.setCenter(gradeSelectionContent);
        root.setTop(null); // Remove header
    }

    private void takeATest() {
        // Clear existing content
        root.getChildren().clear();

        // Create TestSelection content
        TestSelection testSelection = new TestSelection(this::showDashboardView, root);
        BorderPane testSelectionContent = testSelection.createTestSelectionContent();

        // Set TestSelection content to fill the entire window
        root.setCenter(testSelectionContent);
        root.setTop(null); // Remove header if needed
    }


    private void viewProgress() {
        // Clear existing content
        root.getChildren().clear();

        // Create ProgressTracker content
        ProgressTracker progressTracker = new ProgressTracker(this::showDashboardView);
        BorderPane progressTrackerContent = progressTracker.createProgressTrackerContent();

        // Set ProgressTracker content to fill the entire window
        root.setCenter(progressTrackerContent);
        root.setTop(null); // Remove header since ProgressTracker has its own back button
    }

    private void playScaleAnimation(Button button, double scale) {
        ScaleTransition st = new ScaleTransition(Duration.millis(150), button);
        st.setToX(scale);
        st.setToY(scale);
        st.play();
    }

    private VBox createActionCard(String title, String subtitle, String color, Runnable action) {
        VBox card = new VBox(15);
        card.setPadding(new Insets(25));
        card.setStyle("-fx-background-color: " + cardColor + "; -fx-background-radius: 20; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0, 0, 5);");

        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-size: 22px; -fx-text-fill: white;");
        titleLabel.setFont(Font.font("Poppins", FontWeight.BOLD, 22));

        Label subtitleLabel = new Label(subtitle);
        subtitleLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: " + subtitleColor + ";");

        Button actionBtn = new Button("Start →");
        actionBtn.setStyle("-fx-background-color: " + color + "; -fx-text-fill: white; " +
                "-fx-background-radius: 15; -fx-font-size: 16px;");
        actionBtn.setCursor(Cursor.HAND);

        actionBtn.setOnAction(e -> action.run());

        card.getChildren().addAll(titleLabel, subtitleLabel, actionBtn);
        return card;
    }
}