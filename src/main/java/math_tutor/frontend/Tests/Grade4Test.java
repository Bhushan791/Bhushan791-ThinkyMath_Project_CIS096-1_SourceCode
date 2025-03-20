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

public class Grade4Test {

    private static final String[] questions = {
            "Is the number 7 a prime or composite number?",
            "What is 6 times 9?",
            "What is 1/2 of 12?",
            "What is the perimeter of a rectangle with length 5 cm and width 3 cm?",
            "If I have $15 and I spend $3 on a toy, how much money do I have left?",
            "Is the number 9 a prime or composite number?",
            "What is 48 divided by 6?",
            "What is 3/4 of 16?",
            "What is the area of a square with side length 4 cm?",
            "If I have 12 hours to complete a task and I work for 4 hours, how many hours do I have left?",
            "Is the number 11 a prime or composite number?",
            "What is 9 times 8?",
            "What is 2/3 of 18?",
            "What is the perimeter of a triangle with sides 3 cm, 4 cm, and 5 cm?",
            "If I have $20 and I spend $5 on a book, how much money do I have left?",
            "Is the number 15 a prime or composite number?",
            "What is 72 divided by 9?",
            "What is 1/4 of 20?",
            "What is the area of a rectangle with length 6 cm and width 4 cm?",
            "If I have 15 minutes to complete a task and I work for 5 minutes, how many minutes do I have left?",
            "Is the number 25 a prime or composite number?",
            "What is 6 times 7?",
            "What is 3/4 of 24?",
            "What is the perimeter of a circle with radius 3 cm? (Use π = 3.14)",
            "If I have $10 and I spend $2 on a snack, how much money do I have left?"
    };

    private static final String[][] options = {
            {"Prime", "Composite", "Neither", "Both"},
            {"48", "54", "60", "66"},
            {"4", "6", "8", "12"},
            {"10 cm", "12 cm", "14 cm", "16 cm"},
            {"$12", "$13", "$14", "$15"},
            {"Prime", "Composite", "Neither", "Both"},
            {"6", "7", "8", "9"},
            {"8", "10", "12", "14"},
            {"16 cm²", "20 cm²", "24 cm²", "32 cm²"},
            {"8 hours", "10 hours", "12 hours", "14 hours"},
            {"Prime", "Composite", "Neither", "Both"},
            {"72", "74", "76", "78"},
            {"8", "10", "12", "14"},
            {"12 cm", "14 cm", "16 cm", "18 cm"},
            {"$15", "$16", "$17", "$18"},
            {"Prime", "Composite", "Neither", "Both"},
            {"6", "7", "8", "9"},
            {"2", "4", "5", "6"},
            {"24 cm²", "30 cm²", "36 cm²", "40 cm²"},
            {"10 minutes", "12 minutes", "14 minutes", "16 minutes"},
            {"Prime", "Composite", "Neither", "Both"},
            {"42", "44", "46", "48"},
            {"12", "16", "18", "20"},
            {"18.84 cm", "19.84 cm", "20.84 cm", "21.84 cm"},
            {"$8", "$9", "$10", "$11"}
    };

    private static final int[] correctAnswers = {
            0, // Prime
            1, // 54
            2, // 6
            2, // 16 cm
            0, // $12
            1, // Composite
            0, // 8
            2, // 12
            2, // 16 cm²
            0, // 8 hours
            0, // Prime
            0, // 72
            2, // 12
            1, // 12 cm
            0, // $15
            1, // Composite
            0, // 8
            2, // 5
            2, // 24 cm²
            0, // 10 minutes
            1, // Composite
            0, // 42
            2, // 18
            0, // 18.84 cm
            0  // $8
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

    public Grade4Test(BorderPane mainContainer, Runnable returnToTestSelection) {
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
