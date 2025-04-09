package math_tutor.frontend;

import javafx.animation.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;
import math_tutor.backend.ConnectionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StudentDashboard {
    // Enhanced Color Palette with gradients
    private final String PRIMARY_COLOR = "linear-gradient(to right, #FF6B6B, #FF8E8E)";
    private final String SECONDARY_COLOR = "linear-gradient(to right, #4ECDC4, #6CE5E5)";
    private final String BACKGROUND_COLOR_LIGHT = "#F0F8FF";
    private final String BACKGROUND_COLOR_DARK = "#73A8E5";
    private final String CARD_BACKGROUND = "rgba(255, 255, 255, 0.15)";
    private final String TEXT_COLOR = "#1A535C";
    private final String ACCENT_COLOR = "linear-gradient(to right, #FFE66D, #FFF3B0)";
    private final String DANGER_COLOR = "linear-gradient(to right, #FF4500, #FF6347)";

    // Main layout for the student dashboard
    private BorderPane root;

    // Reference to the Home application instance
    private Home homeInstance;

    // Username of the currently logged-in student
    private String loggedInUsername;

    // Timeline for managing animations
    private Timeline floatingAnimation;

    /**
     * Base animation class for inheritance and polymorphism.
     * Provides a basic structure for creating animations.
     */
    private abstract static class BaseAnimation {
        // Duration of the animation
        protected Duration duration;

        // Node that the animation will be applied to
        protected Node target;

        /**
         * Constructor for the BaseAnimation class.
         * @param target The node to animate.
         * @param duration The duration of the animation.
         */
        public BaseAnimation(Node target, Duration duration) {
            this.target = target;
            this.duration = duration;
        }

        /**
         * Abstract method to play the animation.
         */
        public abstract void play();

        /**
         * Abstract method to stop the animation.
         */
        public abstract void stop();
    }

    /**
     * Concrete animation class for creating a floating effect.
     * Extends the BaseAnimation class to provide specific floating animation functionality.
     */
    private static class FloatingAnimation extends BaseAnimation {
        // Distance the node will float
        private double floatDistance;

        // Sequential transition for managing the animation sequence
        private SequentialTransition sequence;

        /**
         * Constructor for the FloatingAnimation class.
         * @param target The node to apply the floating animation to.
         * @param duration The duration of the animation.
         * @param distance The distance the node will float.
         */
        public FloatingAnimation(Node target, Duration duration, double distance) {
            super(target, duration);
            this.floatDistance = distance;
        }


        @Override
        public void play() {
            TranslateTransition floatUp = new TranslateTransition(duration, target);
            floatUp.setByY(-floatDistance);
            floatUp.setAutoReverse(true);
            floatUp.setCycleCount(Animation.INDEFINITE);

            TranslateTransition floatDown = new TranslateTransition(duration, target);
            floatDown.setByY(floatDistance);
            floatDown.setAutoReverse(true);
            floatDown.setCycleCount(Animation.INDEFINITE);

            sequence = new SequentialTransition(floatUp, floatDown);
            sequence.setCycleCount(Animation.INDEFINITE);
            sequence.play();
        }

        @Override
        public void stop() {
            if(sequence != null) {
                sequence.stop();
            }
        }
    }

    // Concrete animation class for scaling effect
    private static class ScaleAnimation extends BaseAnimation {
        private double scaleFactor;
        protected ScaleTransition transition;

        public ScaleAnimation(Node target, Duration duration, double scale) {
            super(target, duration);
            this.scaleFactor = scale;
        }

        @Override
        public void play() {
            transition = new ScaleTransition(duration, target);
            transition.setToX(scaleFactor);
            transition.setToY(scaleFactor);
            transition.play();
        }

        @Override
        public void stop() {
            if(transition != null) {
                transition.stop();
            }
        }
    }

    // Concrete animation class for click effect (combining multiple animations)
    private static class ClickAnimation extends BaseAnimation {
        private SequentialTransition sequence;

        public ClickAnimation(Node target) {
            super(target, Duration.millis(150));
        }

        @Override
        public void play() {
            ScaleTransition st1 = new ScaleTransition(Duration.millis(50), target);
            st1.setToX(0.95);
            st1.setToY(0.95);

            ScaleTransition st2 = new ScaleTransition(Duration.millis(100), target);
            st2.setToX(1.0);
            st2.setToY(1.0);

            sequence = new SequentialTransition(st1, st2);
            sequence.play();
        }

        @Override
        public void stop() {
            if(sequence != null) {
                sequence.stop();
            }
        }
    }

    // Constructor with Home instance and username
    public StudentDashboard(Home homeInstance, String username) {
        this.homeInstance = homeInstance;
        this.loggedInUsername = username;
    }

    // Empty constructor for backward compatibility
    public StudentDashboard() {
    }

    // Creates the main dashboard UI
    public BorderPane createDashboard() {
        this.root = new BorderPane();
        root.setStyle(
                "-fx-background-color: linear-gradient(to bottom right, " +
                        BACKGROUND_COLOR_LIGHT + ", " + BACKGROUND_COLOR_DARK + ");"
        );
        root.setTop(createHeader());
        root.setCenter(createMainContent());

        // Add subtle background animation
        startBackgroundAnimation();

        return root;
    }

    private void startBackgroundAnimation() {
        // Create a subtle pulse animation for the background
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(root.styleProperty(),
                                "-fx-background-color: linear-gradient(to bottom right, " + BACKGROUND_COLOR_LIGHT + ", " + BACKGROUND_COLOR_DARK + ");")
                ),
                new KeyFrame(Duration.seconds(10),
                        new KeyValue(root.styleProperty(),
                                "-fx-background-color: linear-gradient(to bottom right, " + lightenColor(BACKGROUND_COLOR_LIGHT, 0.05) + ", " + lightenColor(BACKGROUND_COLOR_DARK, 0.05) + ");")
                ),
                new KeyFrame(Duration.seconds(20),
                        new KeyValue(root.styleProperty(),
                                "-fx-background-color: linear-gradient(to bottom right, " + BACKGROUND_COLOR_LIGHT + ", " + BACKGROUND_COLOR_DARK + ");")
                )
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(true);
        timeline.play();
    }

    private HBox createHeader() {
        // Create navigation buttons with enhanced styling
        Button profileBtn = createStyledButton("👤 Profile", PRIMARY_COLOR);
        Button leaderboardBtn = createStyledButton("🏆 Leaderboard", SECONDARY_COLOR);
        Button logoutBtn = createStyledButton("🚪 Logout", DANGER_COLOR);

        // Add floating effect to header buttons
        applyFloatingEffect(profileBtn, 5, 2000);
        applyFloatingEffect(leaderboardBtn, 5, 2200);
        applyFloatingEffect(logoutBtn, 5, 2400);

        // Existing action assignments
        profileBtn.setOnAction(e -> showProfileView());
        leaderboardBtn.setOnAction(e -> showLeaderboardView());
        logoutBtn.setOnAction(this::confirmAndLogout);

        // Header layout with glass morphism effect
        HBox header = new HBox(20);
        header.setPadding(new Insets(15));
        header.setAlignment(Pos.CENTER);
        header.getChildren().addAll(profileBtn, leaderboardBtn, logoutBtn);
        header.setStyle(
                "-fx-background-color: rgba(255, 255, 255, 0.15);" +
                        "-fx-background-radius: 0 0 20 20;" +
                        "-fx-border-color: rgba(255, 255, 255, 0.2);" +
                        "-fx-border-width: 1px;" +
                        "-fx-border-radius: 0 0 20 20;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 15, 0, 0, 5);"
        );

        // Add subtle glow on hover
        header.setOnMouseEntered(e -> {
            header.setEffect(new Glow(0.1));
        });
        header.setOnMouseExited(e -> {
            header.setEffect(null);
        });

        return header;
    }

    private Button createStyledButton(String text, String background) {
        Button btn = new Button(text);
        btn.setStyle(
                "-fx-background-color: " + background + ";" +
                        "-fx-text-fill: white;" +
                        "-fx-background-radius: 25;" +
                        "-fx-font-size: 14px;" +
                        "-fx-padding: 10 25;" +
                        "-fx-font-weight: bold;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0, 0, 4);"
        );
        btn.setFont(Font.font("Arial Rounded MT Bold", FontWeight.BOLD, 14));
        btn.setCursor(Cursor.HAND);

        // Enhanced hover effects with transitions
        btn.setOnMouseEntered(e -> {
            btn.setStyle(
                    "-fx-background-color: " + darkenGradient(background) + ";" +
                            "-fx-text-fill: white;" +
                            "-fx-background-radius: 25;" +
                            "-fx-font-size: 14px;" +
                            "-fx-padding: 10 25;" +
                            "-fx-font-weight: bold;" +
                            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 15, 0, 0, 6);"
            );
            playScaleAnimation(btn, 1.05);
        });

        btn.setOnMouseExited(e -> {
            btn.setStyle(
                    "-fx-background-color: " + background + ";" +
                            "-fx-text-fill: white;" +
                            "-fx-background-radius: 25;" +
                            "-fx-font-size: 14px;" +
                            "-fx-padding: 10 25;" +
                            "-fx-font-weight: bold;" +
                            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0, 0, 4);"
            );
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

        // Create action cards with enhanced styling
        VBox learnCard = createActionCard(
                "Start Learning",
                "Mathematics Mastery",
                PRIMARY_COLOR,
                this::startLearning
        );
        applyFloatingEffect(learnCard, 8, 1500);
        grid.add(learnCard, 0, 0);

        VBox testCard = createActionCard(
                "Take a Test",
                "Knowledge Check",
                SECONDARY_COLOR,
                this::takeATest
        );
        applyFloatingEffect(testCard, 8, 1700);
        grid.add(testCard, 1, 0);

        VBox progressCard = createActionCard(
                "View Progress",
                "Performance Tracking",
                ACCENT_COLOR,
                this::viewProgress
        );
        applyFloatingEffect(progressCard, 8, 1900);
        grid.add(progressCard, 2, 0);

        StackPane centerPane = new StackPane(grid);
        centerPane.setPadding(new Insets(50));

        // Add welcome message with animation
        Label welcomeLabel = new Label("Welcome, " + loggedInUsername + "!");
        welcomeLabel.setStyle(
                "-fx-font-size: 24px;" +
                        "-fx-text-fill: " + TEXT_COLOR + ";" +
                        "-fx-font-weight: bold;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0, 0, 2);"
        );
        welcomeLabel.setTranslateY(-250);
        applyFloatingEffect(welcomeLabel, 3, 2500);

        centerPane.getChildren().add(welcomeLabel);

        return centerPane;
    }

    private VBox createActionCard(String title, String subtitle, String color, Runnable action) {
        VBox card = new VBox(15);
        card.setPadding(new Insets(25));
        card.setAlignment(Pos.CENTER);
        card.setStyle(
                "-fx-background-color: " + CARD_BACKGROUND + ";" +
                        "-fx-background-radius: 25;" +
                        "-fx-border-radius: 25;" +
                        "-fx-border-color: rgba(255, 255, 255, 0.3);" +
                        "-fx-border-width: 1px;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 15, 0, 0, 5);"
        );

        // Add icon based on card type
        String iconPath = "";
        if (title.contains("Learning")) iconPath = "✏️";
        else if (title.contains("Test")) iconPath = "📝";
        else if (title.contains("Progress")) iconPath = "📊";

        Label iconLabel = new Label(iconPath);
        iconLabel.setStyle("-fx-font-size: 40px;");

        Label titleLabel = new Label(title);
        titleLabel.setStyle(
                "-fx-font-size: 20px;" +
                        "-fx-text-fill: " + TEXT_COLOR + ";" +
                        "-fx-font-weight: bold;"
        );

        Label subtitleLabel = new Label(subtitle);
        subtitleLabel.setStyle(
                "-fx-font-size: 14px;" +
                        "-fx-text-fill: " + TEXT_COLOR + ";" +
                        "-fx-opacity: 0.8;"
        );

        Button actionBtn = new Button("Start →");
        actionBtn.setStyle(
                "-fx-background-color: " + color + ";" +
                        "-fx-text-fill: white;" +
                        "-fx-background-radius: 20;" +
                        "-fx-font-size: 14px;" +
                        "-fx-padding: 10 25;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 5, 0, 0, 2);"
        );
        actionBtn.setCursor(Cursor.HAND);
        actionBtn.setOnAction(e -> {
            playClickAnimation(actionBtn);
            action.run();
        });

        // Add hover effect to card
        card.setOnMouseEntered(e -> {
            card.setStyle(
                    "-fx-background-color: rgba(255, 255, 255, 0.2);" +
                            "-fx-background-radius: 25;" +
                            "-fx-border-radius: 25;" +
                            "-fx-border-color: rgba(255, 255, 255, 0.5);" +
                            "-fx-border-width: 1px;" +
                            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 20, 0, 0, 8);"
            );
        });

        card.setOnMouseExited(e -> {
            card.setStyle(
                    "-fx-background-color: " + CARD_BACKGROUND + ";" +
                            "-fx-background-radius: 25;" +
                            "-fx-border-radius: 25;" +
                            "-fx-border-color: rgba(255, 255, 255, 0.3);" +
                            "-fx-border-width: 1px;" +
                            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 15, 0, 0, 5);"
            );
        });

        card.getChildren().addAll(iconLabel, titleLabel, subtitleLabel, actionBtn);
        return card;
    }

    // Refactored animation methods using polymorphism
    private void applyFloatingEffect(Region node, double amount, double durationMs) {
        FloatingAnimation animation = new FloatingAnimation(
                node,
                Duration.millis(durationMs),
                amount
        );
        animation.play();
    }

    private void playScaleAnimation(Button button, double scale) {
        ScaleAnimation animation = new ScaleAnimation(
                button,
                Duration.millis(150),
                scale
        );
        animation.play();
    }

    private void playClickAnimation(Button button) {
        ClickAnimation animation = new ClickAnimation(button);
        animation.play();
    }

    // Color utility methods
    private String darkenGradient(String gradient) {
        if (gradient.contains("linear-gradient")) {
            Pattern pattern = Pattern.compile("rgba\\((\\d+),(\\d+),(\\d+),([0-9]*\\.?[0-9]+)\\)");
            Matcher matcher = pattern.matcher(gradient);
            StringBuffer sb = new StringBuffer();

            while (matcher.find()) {
                int r = Integer.parseInt(matcher.group(1));
                int g = Integer.parseInt(matcher.group(2));
                int b = Integer.parseInt(matcher.group(3));
                double alpha = Double.parseDouble(matcher.group(4)) * 0.8; // Reduce opacity by 20%

                // Format the new rgba string
                String newRgba = String.format("rgba(%d,%d,%d,%.2f)", r, g, b, Math.min(1.0, alpha));
                matcher.appendReplacement(sb, newRgba);
            }
            matcher.appendTail(sb);

            return sb.toString();
        }
        return gradient;
    }

    private String lightenColor(String hexColor, double factor) {
        if (hexColor.startsWith("#")) {
            hexColor = hexColor.substring(1);
            int r = Integer.parseInt(hexColor.substring(0, 2), 16);
            int g = Integer.parseInt(hexColor.substring(2, 4), 16);
            int b = Integer.parseInt(hexColor.substring(4, 6), 16);

            r = (int) Math.min(255, r + (255 - r) * factor);
            g = (int) Math.min(255, g + (255 - g) * factor);
            b = (int) Math.min(255, b + (255 - b) * factor);

            return String.format("#%02x%02x%02x", r, g, b);
        }
        return hexColor;
    }

    private void confirmAndLogout(javafx.event.ActionEvent event) {
        Alert confirmLogout = new Alert(Alert.AlertType.CONFIRMATION);
        confirmLogout.setTitle("Confirm Logout");
        confirmLogout.setHeaderText("Are you sure you want to logout?");
        confirmLogout.setContentText("Your session will be terminated if you choose to logout.");

        Optional<ButtonType> result = confirmLogout.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            navigateToHomeScreen();
        }
    }

    private void navigateToHomeScreen() {
        if (homeInstance != null) {
            homeInstance.showHomeScreen();
        } else {
            System.err.println("Error: Cannot navigate to home screen - Home instance is not available");
        }
    }

    private void showProfileView() {
        BorderPane profileRoot = new BorderPane();
        profileRoot.setStyle("-fx-background-color: linear-gradient(to bottom right, " + BACKGROUND_COLOR_LIGHT + ", " + BACKGROUND_COLOR_DARK + ");");

        Button backBtn = createStyledButton("🔙 Back", PRIMARY_COLOR);
        backBtn.setOnAction(e -> showDashboardView());

        HBox profileHeader = new HBox(backBtn);
        profileHeader.setPadding(new Insets(20));
        profileRoot.setTop(profileHeader);

        StudentProfile studentProfile = new StudentProfile(loggedInUsername);
        VBox profileContent = studentProfile.createProfileContent();
        profileContent.setPadding(new Insets(40));
        profileRoot.setCenter(profileContent);

        root.setTop(profileHeader);
        root.setCenter(profileContent);
    }

    private void showLeaderboardView() {
        BorderPane leaderboardRoot = new BorderPane();
        leaderboardRoot.setStyle("-fx-background-color: linear-gradient(to bottom right, " + BACKGROUND_COLOR_LIGHT + ", " + BACKGROUND_COLOR_DARK + ");");

        Button backBtn = createStyledButton("🔙 Back", PRIMARY_COLOR);
        backBtn.setOnAction(e -> showDashboardView());

        HBox leaderboardHeader = new HBox(backBtn);
        leaderboardHeader.setPadding(new Insets(20));
        leaderboardRoot.setTop(leaderboardHeader);

        Leaderboard leaderboard = new Leaderboard();
        VBox leaderboardContent = leaderboard.createLeaderboardContent();
        leaderboardContent.setPadding(new Insets(40));
        leaderboardRoot.setCenter(leaderboardContent);

        root.setTop(leaderboardHeader);
        root.setCenter(leaderboardContent);
    }

    void showDashboardView() {
        root.setTop(createHeader());
        root.setCenter(createMainContent());
    }

    private void startLearning() {
        // Original implementation
        root.getChildren().clear();
        GradeSelection gradeSelection = new GradeSelection(this::showDashboardView);
        AnchorPane gradeSelectionContent = gradeSelection.createGradeSelectionContent();
        gradeSelectionContent.setMaxWidth(Double.MAX_VALUE);
        gradeSelectionContent.setMaxHeight(Double.MAX_VALUE);
        root.setCenter(gradeSelectionContent);
        root.setTop(null);
    }

    private void takeATest() {
        // Original implementation
        TestSelection testSelection = new TestSelection(
                this::showDashboardView,
                root,
                homeInstance,
                loggedInUsername,
                getLoggedInStudentId()
        );
        BorderPane testSelectionContent = testSelection.createTestSelectionContent();
        root.setCenter(testSelectionContent);
    }

    private void viewProgress() {
        // Original implementation
        root.getChildren().clear();
        ProgressTracker progressTracker = new ProgressTracker();
        BorderPane progressTrackerContent = progressTracker.createProgressTrackerContent(this);
        root.setCenter(progressTrackerContent);
        root.setTop(null);
    }

    // Existing database and utility methods
    public String getLoggedInUsername() {
        return loggedInUsername;
    }

    private int getLoggedInStudentId() {
        try (Connection conn = ConnectionDB.getInstance().getConnection()) {
            String query = "SELECT student_id FROM students WHERE username = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, loggedInUsername);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("student_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}