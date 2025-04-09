package math_tutor.frontend;

import javafx.animation.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.scene.shape.Circle; // Import Circle for animated background

public class GradeSelection {

    private final String[] buttonColors = {
            "#FF6B6B", // Red
            "#4ECDC4", // Teal
            "#FFD166", // Yellow
            "#9370DB", // Purple
            "#4CAF50"  // Green
    };

    private final String[] buttonHoverColors = {
            "#FF4949", // Darker Red
            "#36B5AC", // Darker Teal
            "#FFC233", // Darker Yellow
            "#7D5CC4", // Darker Purple
            "#3E8E41"  // Darker Green
    };

    private AnchorPane root; // Define root here
    private Runnable backToDashboardHandler;

    public GradeSelection(Runnable backToDashboardHandler) {
        this.backToDashboardHandler = backToDashboardHandler;
        this.root = new AnchorPane(); // Initialize root
    }

    public AnchorPane createGradeSelectionContent() {
        // Create gradient background
        LinearGradient gradient = new LinearGradient(0, 0, 1, 0, true, null,
                new Stop(0, Color.web("#D4E4FF")),
                new Stop(1, Color.web("#73A8E5"))
        );
        root.setBackground(new Background(new BackgroundFill(gradient, null, null)));

        // Add animated background (example: subtle moving particles)
        addAnimatedBackground();

        // Back button
        Button backButton = createBackButton();

        // Grade selection content
        VBox centerContent = createGradeButtons();

        // Layout setup
        AnchorPane.setTopAnchor(backButton, 20.0);
        AnchorPane.setLeftAnchor(backButton, 20.0);
        AnchorPane.setTopAnchor(centerContent, 100.0);
        AnchorPane.setLeftAnchor(centerContent, 0.0);
        AnchorPane.setRightAnchor(centerContent, 0.0);

        root.getChildren().addAll(backButton, centerContent);
        return root;
    }

    private void addAnimatedBackground() {
        // Example: Add circles that move and fade
        for (int i = 0; i < 15; i++) {
            Circle particle = new Circle(Math.random() * root.getWidth(), Math.random() * root.getHeight(), Math.random() * 20 + 5, Color.WHITE.deriveColor(0, 1, 1, Math.random() * 0.3));
            root.getChildren().add(particle);

            // Animate the particle
            TranslateTransition move = new TranslateTransition(Duration.millis((Math.random() * 5 + 2) * 1000), particle);
            move.setToX(Math.random() * root.getWidth() - particle.getCenterX());
            move.setToY(Math.random() * root.getHeight() - particle.getCenterY());
            move.setAutoReverse(true);
            move.setCycleCount(Animation.INDEFINITE);

            FadeTransition fade = new FadeTransition(Duration.millis((Math.random() * 5 + 2) * 1000), particle);
            fade.setToValue(0);
            fade.setAutoReverse(true);
            fade.setCycleCount(Animation.INDEFINITE);

            ParallelTransition animation = new ParallelTransition(move, fade);
            animation.play();
        }
    }

    private Button createBackButton() {
        Button backButton = new Button("← Back to Dashboard");
        backButton.setStyle(
                "-fx-background-color: #ff69b4; " +
                        "-fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8px 16px; " +
                        "-fx-background-radius: 20; -fx-font-weight: bold; " +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 3, 0, 0, 2);"
        );
        backButton.setCursor(Cursor.HAND);

        // Hover effects
        backButton.setOnMouseEntered(e -> backButton.setStyle(
                "-fx-background-color: #ff1493; " +
                        "-fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8px 16px; " +
                        "-fx-background-radius: 20; -fx-font-weight: bold; " +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.4), 5, 0, 0, 3);"
        ));

        backButton.setOnMouseExited(e -> backButton.setStyle(
                "-fx-background-color: #ff69b4; " +
                        "-fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8px 16px; " +
                        "-fx-background-radius: 20; -fx-font-weight: bold; " +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 3, 0, 0, 2);"
        ));

        // Click animation
        backButton.setOnMousePressed(event -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(100), backButton);
            st.setToX(0.95);
            st.setToY(0.95);
            st.setAutoReverse(true);
            st.setCycleCount(2);
            st.play();
        });

        backButton.setOnAction(e -> backToDashboardHandler.run());
        return backButton;
    }

    private VBox createGradeButtons() {
        VBox buttonBox = new VBox(25);
        buttonBox.setAlignment(Pos.CENTER);

        Text title = new Text("Choose Grade! 😊✨");
        title.setFont(Font.font("Comic Sans MS", 36));
        title.setStyle("-fx-font-weight: bold; -fx-fill: #ff1493;");

        // Add a subtle animation to the title
        FadeTransition fadeInOut = new FadeTransition(Duration.millis(2000), title);
        fadeInOut.setFromValue(0.5);
        fadeInOut.setToValue(1.0);
        fadeInOut.setAutoReverse(true);
        fadeInOut.setCycleCount(Animation.INDEFINITE);
        fadeInOut.play();

        for (int i = 0; i < 5; i++) {
            Button gradeButton = createGradeButton(i + 1);
            buttonBox.getChildren().add(gradeButton);
        }

        VBox content = new VBox(40, title, buttonBox);
        content.setAlignment(Pos.CENTER);
        return content;
    }

    private Button createGradeButton(int grade) {
        Button button = new Button("Grade " + grade);
        String baseColor = buttonColors[grade - 1];
        String hoverColor = buttonHoverColors[grade - 1];

        String baseStyle = "-fx-background-color: " + baseColor + "; " +
                "-fx-text-fill: white; -fx-font-size: 18px; -fx-padding: 15px 30px; " +
                "-fx-background-radius: 50; -fx-font-weight: bold; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 5, 0, 0, 3); " +
                "-fx-min-width: 240px;";

        button.setStyle(baseStyle);
        button.setCursor(Cursor.HAND);

        // Hover effects
        button.setOnMouseEntered(e -> {
            button.setStyle(
                    "-fx-background-color: " + hoverColor + "; " +
                            "-fx-text-fill: white; -fx-font-size: 18px; -fx-padding: 15px 30px; " +
                            "-fx-background-radius: 50; -fx-font-weight: bold; " +
                            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.5), 8, 0, 0, 5); " +
                            "-fx-min-width: 240px;"
            );
            // Add a scale animation on hover
            ScaleTransition scaleUp = new ScaleTransition(Duration.millis(200), button);
            scaleUp.setToX(1.1);
            scaleUp.setToY(1.1);
            scaleUp.play();
        });

        button.setOnMouseExited(e -> {
            button.setStyle(baseStyle);
            // Add a scale animation on exit
            ScaleTransition scaleDown = new ScaleTransition(Duration.millis(200), button);
            scaleDown.setToX(1.0);
            scaleDown.setToY(1.0);
            scaleDown.play();
        });

        // Click animation and action
        button.setOnMousePressed(event -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(100), button);
            st.setToX(0.93);
            st.setToY(0.93);
            st.setAutoReverse(true);
            st.setCycleCount(2);
            st.play();
            handleGradeSelection(grade);
        });

        return button;
    }

    // In GradeSelection class
    private void handleGradeSelection(int grade) {
        System.out.println("Selected Grade: " + grade);

        // Create a new back handler that will properly recreate the grade selection screen
        Runnable backToGradeHandler = () -> {
            // Clear the root first
            root.getChildren().clear();

            // Create a new grade selection content
            Button backButton = createBackButton();
            VBox centerContent = createGradeButtons();

            // Layout setup
            AnchorPane.setTopAnchor(backButton, 20.0);
            AnchorPane.setLeftAnchor(backButton, 20.0);
            AnchorPane.setTopAnchor(centerContent, 100.0);
            AnchorPane.setLeftAnchor(centerContent, 0.0);
            AnchorPane.setRightAnchor(centerContent, 0.0);

            // Add the components to root
            root.getChildren().addAll(backButton, centerContent);
        };

        // Create ChapterSelectionContent with proper back handler
        ChapterSelectionContent chapterContent = new ChapterSelectionContent(grade, backToGradeHandler);

        // Get the AnchorPane from ChapterSelectionContent
        AnchorPane chapterSelectionPane = chapterContent.getContent();

        // Clear current content
        root.getChildren().clear();

        // Make sure the chapter content fills the entire space
        AnchorPane.setTopAnchor(chapterSelectionPane, 0.0);
        AnchorPane.setRightAnchor(chapterSelectionPane, 0.0);
        AnchorPane.setBottomAnchor(chapterSelectionPane, 0.0);
        AnchorPane.setLeftAnchor(chapterSelectionPane, 0.0);

        // Add the chapter content
        root.getChildren().add(chapterSelectionPane);
    }

}
