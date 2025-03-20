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

public class Grade1Test {

    private static final String[] questions = {
            "What is the number that comes after 5?",
            "What shape is a ball?",
            "If I have 3 pencils and I add 2 more, how many pencils do I have now?",
            "What is the time when the sun rises?",
            "If I have 5 toys and I give away 1, how many toys do I have left?",
            "What is the number that comes before 8?",
            "What shape is a book?",
            "If I have 2 blocks and I add 1 more, how many blocks do I have now?",
            "What is the time when we usually have lunch?",
            "If I have 4 crayons and I give away 1, how many crayons do I have left?",
            "What is the number that comes after 9?",
            "What shape is a coin?",
            "If I have 1 toy car and I add 2 more, how many toy cars do I have now?",
            "What is the time when we usually go to bed?",
            "If I have 3 marbles and I give away 2, how many marbles do I have left?",
            "What is the number that comes before 1?",
            "What shape is a door?",
            "If I have 5 stickers and I add 1 more, how many stickers do I have now?",
            "What is the time when we usually have breakfast?",
            "If I have 2 dolls and I give away 1, how many dolls do I have left?",
            "What is the number that comes after 7?",
            "What shape is a table?",
            "If I have 1 book and I add 3 more, how many books do I have now?",
            "What is the time when we usually go to school?"
    };

    private static final String[][] options = {
            {"5", "6", "7", "8"},
            {"Circle", "Square", "Triangle", "Rectangle"},
            {"3", "4", "5", "6"},
            {"Morning", "Afternoon", "Evening", "Night"},
            {"4", "5", "6", "7"},
            {"6", "7", "8", "9"},
            {"Square", "Circle", "Triangle", "Rectangle"},
            {"2", "3", "4", "5"},
            {"Afternoon", "Morning", "Evening", "Night"},
            {"3", "4", "5", "6"},
            {"9", "10", "11", "12"},
            {"Circle", "Square", "Triangle", "Rectangle"},
            {"1", "2", "3", "4"},
            {"Night", "Morning", "Afternoon", "Evening"},
            {"1", "2", "3", "4"},
            {"0", "1", "2", "3"},
            {"Rectangle", "Square", "Circle", "Triangle"},
            {"5", "6", "7", "8"},
            {"Morning", "Afternoon", "Evening", "Night"},
            {"1", "2", "3", "4"},
            {"7", "8", "9", "10"},
            {"Rectangle", "Square", "Circle", "Triangle"},
            {"1", "2", "3", "4"},
            {"Morning", "Afternoon", "Evening", "Night"}
    };

    private static final int[] correctAnswers = {
            1, // 6
            0, // Circle
            2, // 5
            0, // Morning
            0, // 4
            1, // 7
            0, // Square
            1, // 3
            0, // Afternoon
            0, // 3
            1, // 10
            0, // Circle
            2, // 3
            0, // Night
            0, // 1
            0, // 0
            0, // Rectangle
            1, // 6
            0, // Morning
            0, // 1
            1, // 8
            0, // Rectangle
            2, // 4
            0  // Morning
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

    public Grade1Test(BorderPane mainContainer, Runnable returnToTestSelection) {
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
