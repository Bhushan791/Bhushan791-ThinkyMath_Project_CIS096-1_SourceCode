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

public class Grade2Test {

    // ========== QUESTION DATA ========== //
    private static final TestConfig TEST_CONFIG = new TestConfig();

    // ========== UI COLORS ========== //
    private static class UIColors {
        static final String LIGHT_COLOR = "#D4E4FF";
        static final String DARK_COLOR = "#73A8E5";
        static final String BUTTON_COLOR = "#fa009e";
        static final String BUTTON_HOVER_COLOR = "#FF6B6B";
        static final String BACKGROUND_GRADIENT =
                "-fx-background-color: linear-gradient(to bottom right, " + LIGHT_COLOR + ", " + DARK_COLOR + ");";
    }

    // ========== TEST CONFIGURATION ========== //
    private static class TestConfig {
        static final String[] QUESTIONS = {
                "What is the place value of 7 in 57?",
                "What is 45 + 32?",
                "What is 68 - 23?",
                "What is 9 + 8?",
                "What is 50 - 25?",
                "What is 6 × 4?",
                "What is 36 ÷ 6?",
                "If a pencil weighs 20 grams, how much do 5 pencils weigh?",
                "How many centimeters are in a meter?",
                "If you have 3 apples and buy 2 more, how many apples do you have?"
        };

        static final String[][] OPTIONS = {
                {"7", "50", "20", "30"},
                {"77", "87", "97", "67"},
                {"45", "55", "35", "25"},
                {"15", "16", "17", "18"},
                {"20", "25", "30", "35"},
                {"24", "20", "30", "40"},
                {"5", "6", "7", "8"},
                {"80 grams", "100 grams", "120 grams", "60 grams"},
                {"10", "100", "1000", "1"},
                {"5", "4", "6", "7"}
        };

        static final int[] CORRECT_ANSWERS = {
                0, 1, 1, 0, 1, 0, 1, 1, 1, 0
        };
        static final int POINTS_PER_QUESTION = 10;
        static final int TOTAL_QUESTIONS = QUESTIONS.length;
    }

    // ========== COMPONENTS ========== //
    private final BorderPane mainContainer;
    private final Runnable returnToTestSelection;
    private final String loggedInStudentName;
    private final int loggedInStudentId;

    private int currentQuestion = 0;
    private int score = 0;
    private Label questionLabel;
    private RadioButton[] radioButtons;
    private ToggleGroup toggleGroup;
    private Button nextButton;
    private Button submitButton;
    private Label scoreLabel;
    private Label questionNumberLabel;

    // ========== CONSTRUCTOR ========== //
    public Grade2Test(BorderPane mainContainer, Runnable returnToTestSelection,
                      String studentName, int studentId) {
        this.mainContainer = mainContainer;
        this.returnToTestSelection = returnToTestSelection;
        this.loggedInStudentName = studentName;
        this.loggedInStudentId = studentId;
    }

    // ========== TEST DISPLAY ========== //
    public void showTest() {
        if (!isTestConfigValid()) {
            showErrorAlert("Test configuration is invalid.");
            return;
        }

        BorderPane testLayout = createTestLayout();
        mainContainer.setCenter(testLayout);
    }

    // ========== VALIDATION ========== //
    private boolean isTestConfigValid() {
        return TEST_CONFIG.QUESTIONS.length > 0 &&
                TEST_CONFIG.OPTIONS.length > 0 &&
                TEST_CONFIG.CORRECT_ANSWERS.length > 0;
    }

    // ========== LAYOUT CREATION ========== //
    private BorderPane createTestLayout() {
        BorderPane testLayout = new BorderPane();
        testLayout.setStyle(UIColors.BACKGROUND_GRADIENT);

        VBox content = createTestContent();
        testLayout.setTop(createHeader());
        testLayout.setCenter(content);

        return testLayout;
    }

    // ========== HEADER ========== //
    private VBox createHeader() {
        Button backButton = createStyledButton("🔙 Back");
        backButton.setOnAction(e -> returnToTestSelection.run());

        VBox header = new VBox(backButton);
        header.setPadding(new Insets(20));
        header.setAlignment(Pos.TOP_LEFT);

        return header;
    }

    // ========== TEST CONTENT ========== //
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

    // ========== QUESTION NUMBER LABEL ========== //
    private Label createQuestionNumberLabel() {
        Label label = new Label("Question " + (currentQuestion + 1) + " of " + TEST_CONFIG.TOTAL_QUESTIONS);
        label.setFont(Font.font("Poppins", FontWeight.BOLD, 22));
        label.setTextFill(Color.WHITE);
        label.setBackground(createTransparentBackground(Color.rgb(0, 0, 0, 0.1), 5));
        return label;
    }

    // ========== QUESTION LABEL ========== //
    private Label createQuestionLabel() {
        Label label = new Label(TEST_CONFIG.QUESTIONS[currentQuestion]);
        label.setFont(Font.font("Poppins", FontWeight.BOLD, 28));
        label.setTextFill(Color.WHITE);
        label.setWrapText(true);
        label.setMaxWidth(700);
        label.setAlignment(Pos.CENTER);
        label.setPadding(new Insets(15));
        label.setBackground(createTransparentBackground(Color.web("#FF69B4"), 0.8));
        label.setBorder(createSubtleBorder(Color.WHITE, 0.5));
        return label;
    }

    // ========== OPTIONS BOX ========== //
    private VBox createOptionsBox() {
        VBox optionsBox = new VBox(15);
        optionsBox.setAlignment(Pos.CENTER);
        optionsBox.setPadding(new Insets(20));
        optionsBox.setBackground(createTransparentBackground(Color.WHITE, 0.2));

        toggleGroup = new ToggleGroup();
        radioButtons = new RadioButton[4];

        for (int i = 0; i < 4; i++) {
            radioButtons[i] = createRadioButton(TEST_CONFIG.OPTIONS[currentQuestion][i]);
            optionsBox.getChildren().add(radioButtons[i]);
        }

        return optionsBox;
    }

    // ========== RADIO BUTTON ========== //
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

    // ========== BUTTON BOX ========== //
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

    // ========== SCORE LABEL ========== //
    private Label createScoreLabel() {
        Label label = new Label("Current Score: 0");
        label.setFont(Font.font("Poppins", FontWeight.BOLD, 22));
        label.setTextFill(Color.WHITE);
        return label;
    }

    // ========== ANSWER CHECKING ========== //
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

    // ========== FIND SELECTED OPTION INDEX ========== //
    private int findSelectedOptionIndex(RadioButton selected) {
        for (int i = 0; i < radioButtons.length; i++) {
            if (radioButtons[i].equals(selected)) {
                return i;
            }
        }
        return -1;
    }

    // ========== TEST PROGRESS ========== //
    private void updateTestProgress() {
        if (currentQuestion < TEST_CONFIG.TOTAL_QUESTIONS) {
            updateQuestionContent();
        } else {
            finalizeTest();
        }
    }

    // ========== QUESTION UPDATE ========== //
    private void updateQuestionContent() {
        questionNumberLabel.setText("Question " + (currentQuestion + 1) + " of " + TEST_CONFIG.TOTAL_QUESTIONS);
        questionLabel.setText(TEST_CONFIG.QUESTIONS[currentQuestion]);
        for (int i = 0; i < 4; i++) {
            radioButtons[i].setText(TEST_CONFIG.OPTIONS[currentQuestion][i]);
            radioButtons[i].setSelected(false);
        }
        toggleGroup.selectToggle(null);
    }

    // ========== TEST FINALIZATION ========== //
    private void finalizeTest() {
        questionLabel.setText("Test Completed!");
        nextButton.setVisible(false);
        submitButton.setVisible(true);
    }

    // ========== TEST SUBMISSION ========== //
    private void submitTest() {
        scoreLabel.setText("Final Score: " + score + "/100");
        submitButton.setDisable(true);

        TestHandler testHandler = new TestHandler();
        LocalDate completionDate = LocalDate.now();

        boolean isRecorded = testHandler.handleTestCompletion(
                "Grade2Test",
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

    // ========== UI UTILITIES ========== //
    private Background createTransparentBackground(Color color, double opacity) {
        return new Background(new BackgroundFill(
                Color.web(color.toString(), opacity),
                new CornerRadii(5),
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

    // ========== RADIO BUTTON STYLE ========== //
    private String createRadioButtonStyle() {
        return "-fx-background-color: rgba(255, 255, 255, 0.1);" +
                "-fx-background-radius: 10;" +
                "-fx-border-color: rgba(255, 255, 255, 0.3);" +
                "-fx-border-radius: 10;" +
                "-fx-padding: 10 15;";
    }

    // ========== BUTTON STYLING ========== //
    private Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setStyle(createButtonStyle(UIColors.BUTTON_COLOR));

        button.setOnMouseEntered(e -> {
            button.setCursor(javafx.scene.Cursor.HAND);
            button.setStyle(createButtonStyle(UIColors.BUTTON_HOVER_COLOR));
        });
        button.setOnMouseExited(e -> {
            button.setCursor(javafx.scene.Cursor.DEFAULT);
            button.setStyle(createButtonStyle(UIColors.BUTTON_COLOR));
        });

        return button;
    }

    // ========== BUTTON STYLE GENERATOR ========== //
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

    // ========== ALERTS ========== //
    private void showAlert(String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showErrorAlert(String message) {
        showAlert(message, Alert.AlertType.ERROR);
    }
}
