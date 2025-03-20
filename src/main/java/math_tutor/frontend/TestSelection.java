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
import math_tutor.frontend.Tests.*;

public class TestSelection {

    private final String lightColor = "#D4E4FF";
    private final String darkColor = "#73A8E5";
    private final String buttonColor = "#fa009e";
    private final String buttonHoverColor = "#FF6B6B";
    private final Runnable backToDashboard;
    private final BorderPane mainContainer;

    public TestSelection(Runnable backToDashboard, BorderPane mainContainer) {
        this.backToDashboard = backToDashboard;
        this.mainContainer = mainContainer;
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

        Label headerLabel = new Label("Select a Grade for the Test");
        headerLabel.setFont(Font.font("Poppins", FontWeight.BOLD, 24));
        headerLabel.setTextFill(Color.WHITE);

        Button grade1Button = createStyledButton("Grade 1 Test");
        Button grade2Button = createStyledButton("Grade 2 Test");
        Button grade3Button = createStyledButton("Grade 3 Test");
        Button grade4Button = createStyledButton("Grade 4 Test");
        Button grade5Button = createStyledButton("Grade 5 Test");

        grade1Button.setOnAction(e -> {
            Grade1Test test = new Grade1Test(mainContainer, () -> {
                mainContainer.setCenter(createTestSelectionContent());
            });
            test.showTest();
        });

        grade2Button.setOnAction(e -> {
            Grade2Test test = new Grade2Test(mainContainer, () -> {
                mainContainer.setCenter(createTestSelectionContent());
            });
            test.showTest();
        });

        grade3Button.setOnAction(e -> {
            Grade3Test test = new Grade3Test(mainContainer, () -> {
                mainContainer.setCenter(createTestSelectionContent());
            });
            test.showTest();
        });

        grade4Button.setOnAction(e -> {
            Grade4Test test = new Grade4Test(mainContainer, () -> {
                mainContainer.setCenter(createTestSelectionContent());
            });
            test.showTest();
        });

        grade5Button.setOnAction(e -> {
            Grade5Test test = new Grade5Test(mainContainer, () -> {
                mainContainer.setCenter(createTestSelectionContent());
            });
            test.showTest();
        });


        content.getChildren().addAll(headerLabel, grade1Button, grade2Button, grade3Button, grade4Button, grade5Button);

        root.setTop(header);
        root.setCenter(content);

        return root;
    }

    private Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setPrefSize(200, 50);
        button.setStyle("-fx-background-color: " + buttonColor + "; -fx-text-fill: white; -fx-font-size: 16px; " +
                "-fx-background-radius: 20; -fx-font-weight: bold; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0, 0, 5);");

        button.setOnMouseEntered(e -> {
            button.setStyle("-fx-background-color: " + buttonHoverColor + "; -fx-text-fill: white; -fx-font-size: 16px; " +
                    "-fx-background-radius: 20; -fx-font-weight: bold; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 10, 0, 0, 5);");
        });

        button.setOnMouseExited(e -> {
            button.setStyle("-fx-background-color: " + buttonColor + "; -fx-text-fill: white; -fx-font-size: 16px; " +
                    "-fx-background-radius: 20; -fx-font-weight: bold; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0, 0, 5);");
        });

        return button;
    }
}
