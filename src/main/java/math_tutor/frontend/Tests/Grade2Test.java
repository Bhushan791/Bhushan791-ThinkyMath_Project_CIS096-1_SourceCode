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

public class Grade2Test {

    private static final String[] questions = {
            "What is the place value of 7 in 57?",
            "What is 45 + 32?",
            "What is 68 - 23?",
            "What is 9 + 8?",
            "What is 50 - 25?",
            "What is 6 × 4?",
            "What is 36 ÷ 6?",
            "If a pencil weighs 20 grams, how much do 5 pencils weigh?",
            "How many centimeters are in a meter?",
            "If you have 3 apples and you buy 2 more, how many apples do you have?",
            "What is the number that comes after 29?",
            "What is 72 + 18?",
            "What is 90 - 45?",
            "What is 5 × 3?",
            "What is 24 ÷ 3?",
            "If a bottle holds 1 liter, how many milliliters is that?",
            "What is the length of a pencil if it is 15 cm long?",
            "If you have 10 candies and you eat 3, how many do you have left?",
            "What is 8 + 7?",
            "What is 100 - 37?",
            "What is 4 × 5?",
            "What is 48 ÷ 8?",
            "If a book costs $15 and you have $50, how much money will you have left after buying the book?",
            "What is the weight of a 2 kg bag of flour in grams?",
            "If you have 12 cookies and you give away 4, how many cookies do you have left?",
            "What is 33 + 22?",
            "What is 60 - 15?"
    };

    private static final String[][] options = {
            {"7", "50", "20", "30"},
            {"77", "87", "97", "67"},
            {"45", "55", "35", "25"},
            {"15", "16", "17", "18"},
            {"20", "25", "30", "35"},
            {"24", "20", "30", "40"},
            {"5", "6", "7", "8"},
            {"80 grams", "100 grams", "120 grams", "60 grams"},
            {"10", "100", "1000", "1"},
            {"5", "4", "6", "7"},
            {"30", "29", "28", "31"},
            {"90", "100", "80", "70"},
            {"50", "45", "55", "60"},
            {"15", "12", "10", "9"},
            {"8", "6", "7", "5"},
            {"1000 ml", "100 ml", "10 ml", "1 ml"},
            {"10 cm", "15 cm", "20 cm", "25 cm"},
            {"7", "6", "5", "4"},
            {"15", "16", "17", "18"},
            {"60", "50", "40", "30"},
            {"6", "8", "10", "12"},
            {"5", "10", "15", "20"},
            {"2000 grams", "1500 grams", "1000 grams", "500 grams"},
            {"8", "6", "4", "2"},
            {"55", "45", "35", "25"},
            {"45", "50", "55", "60"}
    };

    private static final int[] correctAnswers = {
            0, // 7
            1, // 77
            1, // 45
            2, // 17
            1, // 25
            0, // 24
            0, // 6
            1, // 100 grams
            0, // 100
            2, // 5
            0, // 30
            1, // 90
            1, // 45
            0, // 8
            0, // 8
            0, // 1000 ml
            1, // 15 cm
            0, // 7
            1, // 23
            0, // 20
            0, // 6
            0, // 2000 grams
            1, // 8
            0, // 55
            1  // 45
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

    public Grade2Test(BorderPane mainContainer, Runnable returnToTestSelection) {
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