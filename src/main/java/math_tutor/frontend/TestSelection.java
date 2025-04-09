package math_tutor.frontend;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import math_tutor.frontend.Tests.Grade1Test;
import math_tutor.frontend.Tests.Grade2Test;
import math_tutor.frontend.Tests.Grade3Test;
import math_tutor.frontend.Tests.Grade4Test;
import math_tutor.frontend.Tests.Grade5Test;

public class TestSelection {

    private final String lightColor = "#D4E4FF";
    private final String darkColor = "#73A8E5";
    private final String buttonColor = "#fa009e"; // Base button color
    private final String buttonHoverColor = "#FF6B6B"; // Hover color
    private final String hotPinkColor = "#FF69B4"; // Hot pink color for the label
    private final Runnable backToDashboard;
    private final BorderPane mainContainer;
    private final Home homeInstance; // Reference to Home
    private final String loggedInStudentName; // Student name
    private final int loggedInStudentId; // Student ID

    public TestSelection(Runnable backToDashboard, BorderPane mainContainer,
                         Home homeInstance, String studentName, int studentId) {
        this.backToDashboard = backToDashboard;
        this.mainContainer = mainContainer;
        this.homeInstance = homeInstance;
        this.loggedInStudentName = studentName;
        this.loggedInStudentId = studentId;
    }

    public BorderPane createTestSelectionContent() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, " + lightColor + ", " + darkColor + ");");

        // Create header with back button
        Button backButton = createStyledButton("🔙 Back");
        backButton.setOnAction(e -> backToDashboard.run());

        VBox header = new VBox(backButton);
        header.setAlignment(Pos.TOP_LEFT);
        header.setPadding(new Insets(20));

        // Main content
        VBox content = new VBox(20);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(30));

        // Updated label with larger size and button-like background
        Label headerLabel = new Label("Select a Grade for the Test");
        headerLabel.setFont(Font.font("Poppins", FontWeight.BOLD, 29)); // Increased size by 5
        headerLabel.setTextFill(Color.web(hotPinkColor));
        headerLabel.setStyle("-fx-background-color: " + lightColor + "; " +
                "-fx-padding: 10px; " +
                "-fx-background-radius: 15px; " +
                "-fx-border-radius: 15px; " +
                "-fx-border-color: " + darkColor + "; " +
                "-fx-border-width: 2px;");
        headerLabel.setMouseTransparent(true); // Makes it unclickable like a button

        // Grade buttons with different colors
        Button grade1Button = createStyledButton("Grade 1 Test", "#FF4500"); // Orange
        Button grade2Button = createStyledButton("Grade 2 Test", "#32CD32"); // Lime Green
        Button grade3Button = createStyledButton("Grade 3 Test", "#1E90FF"); // Dodger Blue
        Button grade4Button = createStyledButton("Grade 4 Test", "#FF1493"); // Deep Pink
        Button grade5Button = createStyledButton("Grade 5 Test", "#9400D3"); // Violet

        // Action handlers remain the same as in the previous version
        grade1Button.setOnAction(e -> {
            Grade1Test test = new Grade1Test(
                    mainContainer,
                    () -> {
                        mainContainer.setCenter(createTestSelectionContent());
                    },
                    loggedInStudentName,
                    loggedInStudentId
            );
            test.showTest();
        });

        grade2Button.setOnAction(e -> {
            Grade2Test test = new Grade2Test(mainContainer, () -> {
                mainContainer.setCenter(createTestSelectionContent());
            }, loggedInStudentName, loggedInStudentId);
            test.showTest();
        });

        grade3Button.setOnAction(e -> {
            Grade3Test test = new Grade3Test(mainContainer, () -> {
                mainContainer.setCenter(createTestSelectionContent());
            }, loggedInStudentName, loggedInStudentId);
            test.showTest();
        });

        grade4Button.setOnAction(e -> {
            Grade4Test test = new Grade4Test(mainContainer, () -> {
                mainContainer.setCenter(createTestSelectionContent());
            }, loggedInStudentName, loggedInStudentId);
            test.showTest();
        });

        grade5Button.setOnAction(e -> {
            Grade5Test test = new Grade5Test(mainContainer, () -> {
                mainContainer.setCenter(createTestSelectionContent());
            }, loggedInStudentName, loggedInStudentId);
            test.showTest();
        });

        content.getChildren().addAll(headerLabel, grade1Button, grade2Button, grade3Button, grade4Button, grade5Button);

        root.setTop(header);
        root.setCenter(content);

        return root;
    }

    private Button createStyledButton(String text) {
        return createStyledButton(text, buttonColor);
    }

    private Button createStyledButton(String text, String customColor) {
        Button button = new Button(text);
        button.setPrefSize(200, 50);
        button.setStyle("-fx-background-color: " + customColor + "; -fx-text-fill: white; -fx-font-size: 16px; " +
                "-fx-background-radius: 20; -fx-font-weight: bold; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0, 0, 5);");

        // Change cursor to hand when hovering
        button.setOnMouseEntered(e -> {
            button.setCursor(javafx.scene.Cursor.HAND);
            button.setStyle("-fx-background-color: " + buttonHoverColor + "; -fx-text-fill: white; -fx-font-size: 16px; " +
                    "-fx-background-radius: 20; -fx-font-weight: bold; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 10, 0, 0, 5);");
        });

        button.setOnMouseExited(e -> {
            button.setStyle("-fx-background-color: " + customColor + "; -fx-text-fill: white; -fx-font-size: 16px; " +
                    "-fx-background-radius: 20; -fx-font-weight: bold; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0, 0, 5);");
        });

        return button;
    }
}