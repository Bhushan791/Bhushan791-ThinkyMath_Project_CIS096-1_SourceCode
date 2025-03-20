package math_tutor.frontend.Tests;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Grade3Test {

    private static final String[] questions = {
            "What is the value of the thousands place in the number 4,321?",
            "If I have 2,456 pencils and I add 1,234 more, how many pencils do I have now?",
            "What is 3 times 6?",
            "If I have 12 cookies and I want to share them equally among 4 friends, how many cookies will each friend get?",
            "What is the time when the clock shows 9 hours and 45 minutes?",
            "If I have 5,678 pencils and I subtract 2,345 pencils, how many pencils do I have left?",
            "What is 2/4 as a simplified fraction?",
            "If I have 9 groups of 8 pencils, how many pencils do I have in total?",
            "What is the time when the clock shows 12 hours and 30 minutes?",
            "If I have 1,234 pencils and I add 567 more, how many pencils do I have now?",
            "What is 5 times 9?",
            "If I have 15 cookies and I want to share them equally among 5 friends, how many cookies will each friend get?",
            "What is the time when the clock shows 3 hours and 15 minutes?",
            "If I have 8,901 pencils and I subtract 4,567 pencils, how many pencils do I have left?",
            "What is 3/6 as a simplified fraction?",
            "If I have 7 groups of 9 pencils, how many pencils do I have in total?",
            "What is the time when the clock shows 6 hours and 45 minutes?",
            "If I have 2,345 pencils and I add 1,678 more, how many pencils do I have now?",
            "What is 4 times 8?",
            "If I have 12 cookies and I want to share them equally among 6 friends, how many cookies will each friend get?",
            "What is the time when the clock shows 9 hours and 30 minutes?",
            "If I have 5,432 pencils and I subtract 2,123 pencils, how many pencils do I have left?",
            "What is 1/2 as a simplified fraction?",
            "If I have 9 groups of 7 pencils, how many pencils do I have in total?",
            "What is the time when the clock shows 12 hours and 45 minutes?",
            "If I have 1,987 pencils and I add 654 more, how many pencils do I have now?",
            "What is 6 times 9?",
            "If I have 18 cookies and I want to share them equally among 6 friends, how many cookies will each friend get?",
            "What is the time when the clock shows 4 hours and 30 minutes?",
            "If I have 7,654 pencils and I subtract 3,210 pencils, how many pencils do I have left?",
            "What is 2/6 as a simplified fraction?",
            "If I have 8 groups of 9 pencils, how many pencils do I have in total?",
            "What is the time when the clock shows 10 hours and 15 minutes?",
            "If I have 4,321 pencils and I add 2,987 more, how many pencils do I have now?",
            "What is 9 times 8?"
    };

    private static final String[][] options = {
            {"1", "2", "3", "4"},
            {"3,690", "3,691", "3,692", "3,690"},
            {"16", "18", "20", "22"},
            {"2", "3", "4", "5"},
            {"9:45 AM", "10:45 AM", "11:45 AM", "12:45 PM"},
            {"3,333", "3,334", "3,335", "3,336"},
            {"1/2", "1/4", "3/4", "2/4"},
            {"72", "64", "70", "68"},
            {"12:30 PM", "1:30 PM", "2:30 PM", "3:30 PM"},
            {"1,801", "1,802", "1,803", "1,804"},
            {"45", "46", "47", "48"},
            {"3", "4", "5", "6"},
            {"3:15 PM", "4:15 PM", "5:15 PM", "6:15 PM"},
            {"4,334", "4,335", "4,336", "4,337"},
            {"1/2", "1/3", "1/4", "1/6"},
            {"63", "64", "65", "66"},
            {"6:45 PM", "7:45 PM", "8:45 PM", "9:45 PM"},
            {"4,023", "4,024", "4,025", "4,026"},
            {"32", "33", "34", "35"},
            {"2", "3", "4", "5"},
            {"9:30 AM", "10:30 AM", "11:30 AM", "12:30 PM"},
            {"3,309", "3,310", "3,311", "3,312"},
            {"1/2", "1/3", "1/4", "1/5"},
            {"63", "64", "65", "66"},
            {"12:45 PM", "1:45 PM", "2:45 PM", "3:45 PM"},
            {"2,641", "2,642", "2,643", "2,644"},
            {"54", "55", "56", "57"},
            {"3", "4", "5", "6"},
            {"4:30 PM", "5:30 PM", "6:30 PM", "7:30 PM"},
            {"4,444", "4,445", "4,446", "4,447"},
            {"1/3", "1/4", "1/6", "1/2"},
            {"72", "73", "74", "75"},
            {"10:15 AM", "11:15 AM", "12:15 PM", "1:15 PM"},
            {"7,308", "7,309", "7,310", "7,311"},
            {"72", "73", "74", "75"}
    };

    private static final int[] correctAnswers = {
            0, // 4
            0, // 3,690
            0, // 18
            0, // 3
            0, // 9:45 AM
            0, // 3,333
            0, // 1/2
            0, // 72
            0, // 12:30 PM
            0, // 1,801
            0, // 45
            0, // 3
            0, // 3:15 PM
            0, // 4,334
            0, // 1/2
            0, // 63
            0, // 6:45 PM
            0, // 4,023
            0, // 32
            0, // 2
            0, // 9:30 AM
            0, // 3,309
            0, // 1/2
            0, // 63
            0, // 12:45 PM
            0, // 2,641
            0, // 54
            0, // 3
            0, // 4:30 PM
            0, // 4,444
            0, // 1/3
            0, // 72
            0, // 10:15 AM
            0, // 7,308
            0  // 72
    };

    private final String lightColor = "#D4E4FF";
    private final String darkColor = "#73A8E5";
    private final String buttonColor = "#fa009e";
    private final String buttonHoverColor = "#FF6B6B";

    private final BorderPane mainContainer;
    private final Runnable returnToTestSelection;

    private int currentQuestion = 0;
    private int score = 0;
    private Label questionLabel;
    private RadioButton[] radioButtons;
    private ToggleGroup toggleGroup;
    private Button nextButton;
    private Button submitButton;
    private Label scoreLabel;

    public Grade3Test(BorderPane mainContainer, Runnable returnToTestSelection) {
        this.mainContainer = mainContainer;
        this.returnToTestSelection = returnToTestSelection;
    }

    public void showTest() {
        if (questions.length > 0 && options.length > 0 && correctAnswers.length > 0) {
            BorderPane testLayout = new BorderPane();
            testLayout.setStyle("-fx-background-color: linear-gradient(to bottom right, " + lightColor + ", " + darkColor + ");");

            // Back button
            Button backButton = createStyledButton("🔙 Back");
            backButton.setOnAction(e -> returnToTestSelection.run());

            VBox header = new VBox(backButton);
            header.setPadding(new Insets(20));
            header.setAlignment(Pos.TOP_LEFT);

            // Question content
            VBox content = new VBox(20);
            content.setAlignment(Pos.CENTER);
            content.setPadding(new Insets(20));

            questionLabel = new Label(questions[currentQuestion]);
            questionLabel.setFont(Font.font("Poppins", FontWeight.BOLD, 24));
            questionLabel.setTextFill(javafx.scene.paint.Color.YELLOW);

            toggleGroup = new ToggleGroup();
            radioButtons = new RadioButton[4];
            for (int i = 0; i < 4; i++) {
                radioButtons[i] = new RadioButton(options[currentQuestion][i]);
                radioButtons[i].setToggleGroup(toggleGroup);
                radioButtons[i].setFont(Font.font("Poppins", 18));
                radioButtons[i].setTextFill(javafx.scene.paint.Color.YELLOW);
            }

            nextButton = createStyledButton("Next Question");
            nextButton.setOnAction(e -> checkAnswer());

            submitButton = createStyledButton("Submit Test");
            submitButton.setOnAction(e -> submitTest());
            submitButton.setVisible(false);

            scoreLabel = new Label("Current Score: 0");
            scoreLabel.setFont(Font.font("Poppins", FontWeight.BOLD, 20));
            scoreLabel.setTextFill(javafx.scene.paint.Color.YELLOW);

            content.getChildren().addAll(questionLabel);
            content.getChildren().addAll(radioButtons);
            content.getChildren().addAll(nextButton, submitButton, scoreLabel);

            testLayout.setTop(header);
            testLayout.setCenter(content);
            mainContainer.setCenter(testLayout);
        } else {
            System.out.println("Arrays are not properly initialized.");
        }
    }

    private void checkAnswer() {
        if (currentQuestion < questions.length) {
            RadioButton selected = (RadioButton) toggleGroup.getSelectedToggle();
            if (selected != null) {
                int selectedIndex = -1;
                for (int i = 0; i < 4; i++) {
                    if (radioButtons[i].equals(selected)) {
                        selectedIndex = i;
                        break;
                    }
                }
                if (selectedIndex == correctAnswers[currentQuestion]) {
                    score += 3; // 3 points per question for 35 questions = 105 total
                    scoreLabel.setText("Current Score: " + score);
                }
            }

            currentQuestion++;
            if (currentQuestion < questions.length) {
                questionLabel.setText(questions[currentQuestion]);
                for (int i = 0; i < 4; i++) {
                    radioButtons[i].setText(options[currentQuestion][i]);
                    radioButtons[i].setSelected(false);
                }
                toggleGroup.selectToggle(null);
            } else {
                questionLabel.setText("Test Completed!");
                nextButton.setVisible(false);
                submitButton.setVisible(true);
            }
        } else {
            System.out.println("No more questions available.");
        }
    }

    private void submitTest() {
        scoreLabel.setText("Final Score: " + score + "/105");
        submitButton.setDisable(true);
        // Add logic here to handle test submission, e.g., save score to database
    }

    private Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: " + buttonColor + "; -fx-text-fill: yellow; -fx-font-size: 16px; " +
                "-fx-background-radius: 15; -fx-font-weight: bold; -fx-padding: 8 15;");

        // Store the original style to avoid redundancy
        String originalStyle = button.getStyle();

        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: " + buttonHoverColor + "; " +
                originalStyle.substring(originalStyle.indexOf(";") + 1))); // Keep the rest of the style

        button.setOnMouseExited(e -> button.setStyle(originalStyle)); // Reset to original style

        return button;
    }
}
