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

public class Grade5Test {

    private static final String[] questions = {
            "What is the value of 5 × (3 + 2)?",
            "If Sally has 15 pencils and she gives 3 to her friend, how many pencils does Sally have left?",
            "What is 3/4 of 24?",
            "What is the decimal equivalent of 3/5?",
            "A shirt is on sale for 15% off its original price of $25. How much will you pay for the shirt?",
            "What is the value of 7 - 3 + 2?",
            "If a book costs $15 and a pencil costs $0.50, how much will you pay for 3 books and 2 pencils?",
            "What is 2/3 of 18?",
            "What is the decimal equivalent of 2/5?",
            "A toy car is on sale for 20% off its original price of $30. How much will you pay for the toy car?",
            "What is the value of 9 × (2 - 1)?",
            "If you have 12 groups of 4 pencils, how many pencils do you have in total?",
            "What is 3/4 of 32?",
            "What is the decimal equivalent of 1/2?",
            "A bicycle is on sale for 10% off its original price of $80. How much will you pay for the bicycle?",
            "What is the value of 11 - 4 + 2?",
            "If a toy costs $8 and a game costs $12, how much will you pay for 2 toys and 1 game?",
            "What is 2/3 of 27?",
            "What is the decimal equivalent of 3/10?",
            "A TV is on sale for 25% off its original price of $200. How much will you pay for the TV?",
            "What is the value of 6 × (3 + 1)?",
            "If you have 15 groups of 3 pencils, how many pencils do you have in total?",
            "What is 3/4 of 40?",
            "What is the decimal equivalent of 4/5?"
    };

    private static final String[][] options = {
            {"25", "30", "35", "40"},
            {"12", "13", "14", "15"},
            {"12", "16", "18", "20"},
            {"0.5", "0.6", "0.7", "0.8"},
            {"$20.75", "$21.25", "$22.75", "$23.75"},
            {"6", "7", "8", "9"},
            {"$46.00", "$46.50", "$47.00", "$47.50"},
            {"8", "10", "12", "14"},
            {"0.3", "0.4", "0.5", "0.6"},
            {"$24.00", "$24.50", "$25.00", "$25.50"},
            {"9", "10", "11", "12"},
            {"48", "50", "52", "54"},
            {"20", "22", "24", "26"},
            {"0.5", "0.6", "0.7", "0.8"},
            {"$72.00", "$72.50", "$73.00", "$73.50"},
            {"9", "10", "11", "12"},
            {"$28.00", "$28.50", "$29.00", "$29.50"},
            {"12", "16", "18", "20"},
            {"0.2", "0.3", "0.4", "0.5"},
            {"$150.00", "$150.50", "$151.00", "$151.50"},
            {"24", "26", "28", "30"},
            {"45", "46", "47", "48"},
            {"24", "26", "28", "30"},
            {"0.8", "0.9", "1.0", "1.1"}
    };

    private static final int[] correctAnswers = {
            1, // 35
            0, // 12
            2, // 18
            0, // 0.6
            1, // $21.25
            0, // 6
            0, // $46.00
            2, // 12
            0, // 0.4
            0, // $24.00
            0, // 9
            0, // 48
            2, // 24
            0, // 0.5
            0, // $72.00
            0, // 9
            0, // $28.00
            2, // 18
            0, // 0.3
            0, // $150.00
            2, // 24
            0, // 45
            2, // 24
            0  // 0.8
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

    public Grade5Test(BorderPane mainContainer, Runnable returnToTestSelection) {
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
                    score += 4; // 4 points per question for 25 questions = 100 total
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
        scoreLabel.setText("Final Score: " + score + "/100");
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
