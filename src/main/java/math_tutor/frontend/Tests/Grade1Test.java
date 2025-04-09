package math_tutor.frontend.Tests;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import math_tutor.middleware.TestHandler;

import java.time.LocalDate;

public class Grade1Test {

    // Test Configuration
    private static final TestConfig TEST_CONFIG = new TestConfig();

    // UI Color Palette
    private static class UIColors {
        static final String LIGHT_COLOR = "#D4E4FF";
        static final String DARK_COLOR = "#73A8E5";
        static final String BUTTON_COLOR = "#fa009e";
        static final String BUTTON_HOVER_COLOR = "#FF6B6B";
        static final String BACKGROUND_GRADIENT =
                "-fx-background-color: linear-gradient(to bottom right, " + LIGHT_COLOR + ", " + DARK_COLOR + ");";
    }

    // Test Configuration Static Inner Class
    private static class TestConfig {
        static final String[] QUESTIONS = {
                "What is the number that comes after 5?",
                "What shape is a ball?",
                "If I have 3 pencils and I add 2 more, how many pencils do I have now?",
                "What is the time when the sun rises?",
                "If I have 5 toys and I give away 1, how many toys do I have left?",
                "What is 2 + 2?",
                "What color is the sky on a clear day?",
                "How many sides does a triangle have?",
                "If I have 4 apples and I give away 2, how many apples do I have?",
                "What is the opposite of up?"
        };

        static final String[][] OPTIONS = {
                {"5", "6", "7", "8"},
                {"Circle", "Square", "Triangle", "Rectangle"},
                {"3", "4", "5", "6"},
                {"Morning", "Afternoon", "Evening", "Night"},
                {"4", "5", "6", "7"},
                {"2", "3", "4", "5"},
                {"Blue", "Green", "Red", "Yellow"},
                {"2", "3", "4", "5"},
                {"1", "2", "3", "4"},
                {"Up", "Down", "Left", "Right"}
        };

        static final int[] CORRECT_ANSWERS = {1, 0, 2, 0, 0, 2, 0, 2, 2, 1};
        static final int POINTS_PER_QUESTION = 10;
        static final int TOTAL_QUESTIONS = QUESTIONS.length;
    }

    // UI Components
    private final BorderPane mainContainer;
    private final Runnable returnToTestSelection;

    // Test State Variables
    private int currentQuestion = 0;
    private int score = 0;

    // UI Elements
    private Label questionLabel;
    private RadioButton[] radioButtons;
    private ToggleGroup toggleGroup;
    private Button nextButton;
    private Button submitButton;
    private Label scoreLabel;
    private Label questionNumberLabel;

    // Student Information
    private final String loggedInStudentName;
    private final int loggedInStudentId;

    // Constructor
    public Grade1Test(BorderPane mainContainer,
                      Runnable returnToTestSelection,
                      String studentName,
                      int studentId) {
        this.mainContainer = mainContainer;
        this.returnToTestSelection = returnToTestSelection;
        this.loggedInStudentName = studentName;
        this.loggedInStudentId = studentId;
    }

    // Main method to show the test
    public void showTest() {
        if (!isTestConfigValid()) {
            showErrorAlert("Test configuration is invalid.");
            return;
        }

        BorderPane testLayout = createTestLayout();
        mainContainer.setCenter(testLayout);
    }

    // Validate Test Configuration
    private boolean isTestConfigValid() {
        return TEST_CONFIG.QUESTIONS.length > 0 &&
                TEST_CONFIG.OPTIONS.length > 0 &&
                TEST_CONFIG.CORRECT_ANSWERS.length > 0;
    }

    // Create Test Layout
    private BorderPane createTestLayout() {
        BorderPane testLayout = new BorderPane();
        testLayout.setStyle(UIColors.BACKGROUND_GRADIENT);

        VBox content = createTestContent();
        testLayout.setTop(createHeader());
        testLayout.setCenter(content);

        return testLayout;
    }

    // Create Header with Back Button
    private VBox createHeader() {
        Button backButton = createStyledButton("🔙 Back");
        backButton.setOnAction(e -> returnToTestSelection.run());

        VBox header = new VBox(backButton);
        header.setPadding(new Insets(20));
        header.setAlignment(Pos.TOP_LEFT);

        return header;
    }

    // Create Test Content
    private VBox createTestContent() {
        VBox content = new VBox(30);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(40));

        questionNumberLabel = createQuestionNumberLabel();
        questionLabel = createQuestionLabel();
        VBox optionsBox = createOptionsBox();
        VBox buttonBox = createButtonBox();
        scoreLabel = createScoreLabel();

        content.getChildren().addAll(
                questionNumberLabel,
                questionLabel,
                optionsBox,
                buttonBox,
                scoreLabel
        );

        return content;
    }

    // Create Question Number Label
    private Label createQuestionNumberLabel() {
        Label label = new Label("Question " + (currentQuestion + 1) + " of " + TEST_CONFIG.TOTAL_QUESTIONS);
        label.setFont(Font.font("Poppins", FontWeight.BOLD, 22));
        label.setTextFill(Color.WHITE);
        label.setBackground(createTransparentBackground(Color.rgb(0, 0, 0, 0.1), 5)); // Lighter Black Background, less curved
        return label;
    }

    // Create Question Label
    private Label createQuestionLabel() {
        Label label = new Label(TEST_CONFIG.QUESTIONS[currentQuestion]);
        label.setFont(Font.font("Poppins", FontWeight.BOLD, 28));
        label.setTextFill(Color.WHITE);
        label.setWrapText(true);
        label.setMaxWidth(700);
        label.setAlignment(Pos.CENTER);
        label.setPadding(new Insets(15));
        label.setBackground(createTransparentBackground(Color.web("#FF69B4"), 0.8)); // Changed to #FF69B4
        label.setBorder(createSubtleBorder(Color.WHITE, 0.5));
        return label;
    }

    // Create Options Box
    private VBox createOptionsBox() {
        VBox optionsBox = new VBox(15);
        optionsBox.setAlignment(Pos.CENTER);
        optionsBox.setPadding(new Insets(20));
        optionsBox.setBackground(createTransparentBackground(Color.WHITE, 0.2));
        // optionsBox.setBorder(null); // Removed the border

        toggleGroup = new ToggleGroup();
        radioButtons = new RadioButton[4];

        for (int i = 0; i < 4; i++) {
            radioButtons[i] = createRadioButton(TEST_CONFIG.OPTIONS[currentQuestion][i]);
            optionsBox.getChildren().add(radioButtons[i]);
        }

        return optionsBox;
    }

    // Create Radio Button
    private RadioButton createRadioButton(String text) {
        RadioButton radioButton = new RadioButton(text);
        radioButton.setToggleGroup(toggleGroup);
        radioButton.setFont(Font.font("Poppins", FontWeight.MEDIUM, 22));
        radioButton.setTextFill(Color.WHITE);
        radioButton.setPrefWidth(400);
        radioButton.setAlignment(Pos.CENTER_LEFT);
        radioButton.setStyle(createRadioButtonStyle());
        return radioButton;
    }

    // Create Button Box
    private VBox createButtonBox() {
        nextButton = createStyledButton("Next Question");
        nextButton.setOnAction(e -> checkAnswer());

        submitButton = createStyledButton("Submit Test");
        submitButton.setOnAction(e -> submitTest());
        submitButton.setVisible(false);

        VBox buttonBox = new VBox(15);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(nextButton, submitButton);

        return buttonBox;
    }

    // Create Score Label
    private Label createScoreLabel() {
        Label label = new Label("Current Score: 0");
        label.setFont(Font.font("Poppins", FontWeight.BOLD, 22));
        label.setTextFill(Color.WHITE);
        return label;
    }

    // Check Answer and Progress
    private void checkAnswer() {
        RadioButton selected = (RadioButton) toggleGroup.getSelectedToggle();
        if (selected == null) {
            showErrorAlert("Please select an answer.");
            return;
        }

        int selectedIndex = findSelectedOptionIndex(selected);

        if (selectedIndex == TEST_CONFIG.CORRECT_ANSWERS[currentQuestion]) {
            score += TEST_CONFIG.POINTS_PER_QUESTION;
            scoreLabel.setText("Current Score: " + score);
        }

        currentQuestion++;
        updateTestProgress();
    }

    // Find Selected Option Index
    private int findSelectedOptionIndex(RadioButton selected) {
        for (int i = 0; i < radioButtons.length; i++) {
            if (radioButtons[i].equals(selected)) {
                return i;
            }
        }
        return -1;
    }

    // Update Test Progress
    private void updateTestProgress() {
        if (currentQuestion < TEST_CONFIG.TOTAL_QUESTIONS) {
            updateQuestionContent();
        } else {
            finalizeTest();
        }
    }

    // Update Question Content
    private void updateQuestionContent() {
        questionNumberLabel.setText("Question " + (currentQuestion + 1) + " of " + TEST_CONFIG.TOTAL_QUESTIONS);
        questionLabel.setText(TEST_CONFIG.QUESTIONS[currentQuestion]);
        for (int i = 0; i < 4; i++) {
            radioButtons[i].setText(TEST_CONFIG.OPTIONS[currentQuestion][i]);
            radioButtons[i].setSelected(false);
        }

        toggleGroup.selectToggle(null);
    }

    // Finalize Test
    private void finalizeTest() {
        questionLabel.setText("Test Completed!");
        nextButton.setVisible(false);
        submitButton.setVisible(true);
    }

    // Submit Test
    private void submitTest() {
        scoreLabel.setText("Final Score: " + score + "/100");
        submitButton.setDisable(true);

        TestHandler testHandler = new TestHandler();
        LocalDate completionDate = LocalDate.now();

        boolean isRecorded = testHandler.handleTestCompletion(
                "Grade1Test",
                loggedInStudentName,
                loggedInStudentId,
                score,
                completionDate.toString()
        );

        if (isRecorded) {
            showAlert("Test completed successfully! Your score has been recorded.", Alert.AlertType.INFORMATION);
        } else {
            showAlert("Failed to record your test. Please try again.", Alert.AlertType.ERROR);
        }
    }

    // Utility Methods for UI Styling
    private Background createTransparentBackground(Color color, double opacity) {
        return new Background(new BackgroundFill(
                Color.web(color.toString(), opacity),
                new CornerRadii(5), // Less Curved
                Insets.EMPTY
        ));
    }

    private Border createSubtleBorder(Color color, double opacity) {
        return new Border(new BorderStroke(
                Color.web(color.toString(), opacity),
                BorderStrokeStyle.SOLID,
                new CornerRadii(15),
                BorderStroke.THIN
        ));
    }

    // Styled Button Creation
// Styled Button Creation
    private Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setStyle(createButtonStyle(UIColors.BUTTON_COLOR));

        button.setOnMouseEntered(e -> {
            button.setCursor(javafx.scene.Cursor.HAND); // Set cursor to hand
            button.setStyle(createButtonStyle(UIColors.BUTTON_HOVER_COLOR));
        });
        button.setOnMouseExited(e -> {
            button.setCursor(javafx.scene.Cursor.DEFAULT); // Reset cursor
            button.setStyle(createButtonStyle(UIColors.BUTTON_COLOR));
        });

        return button;
    }

    // Button Style Generator
    private String createButtonStyle(String backgroundColor) {
        return String.format(
                "-fx-background-color: %s; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 16px; " +
                        "-fx-background-radius: 15; " +
                        "-fx-font-weight: bold; " +
                        "-fx-padding: 8 15;",
                backgroundColor
        );
    }

    // Radio Button Style Generator
    private String createRadioButtonStyle() {
        return "-fx-background-color: rgba(255, 255, 255, 0.1);" +
                "-fx-background-radius: 10;" +
                "-fx-border-color: rgba(255, 255, 255, 0.3);" +
                "-fx-border-radius: 10;" +
                "-fx-padding: 10 15;";
    }

    // Alert Dialogs
    private void showAlert(String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showErrorAlert(String message) {
        showAlert(message, Alert.AlertType.ERROR);
    }
}
