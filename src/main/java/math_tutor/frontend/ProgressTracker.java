package math_tutor.frontend;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class ProgressTracker {
    private final String lightColor = "#D4E4FF";
    private final String darkColor = "#73A8E5";
    private final String themeColor = "#FF6B6B";
    private final Runnable backToDashboard;

    public ProgressTracker(Runnable backToDashboard) {
        this.backToDashboard = backToDashboard;
    }

    public BorderPane createProgressTrackerContent() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, " + lightColor + ", " + darkColor + ");");

        // Add header with back button
        HBox header = createHeader();
        root.setTop(header);

        // Add main content (test progress table)
        VBox content = createContent();
        root.setCenter(content);

        return root;
    }

    private HBox createHeader() {
        // Create back button
        Button backBtn = createNavButton("🔙 Back to Dashboard");
        backBtn.setOnAction(e -> backToDashboard.run());

        HBox header = new HBox(backBtn);
        header.setPadding(new Insets(20));
        header.setAlignment(Pos.CENTER_LEFT);

        return header;
    }

    private VBox createContent() {
        VBox content = new VBox(20);
        content.setPadding(new Insets(20, 40, 40, 40));
        content.setAlignment(Pos.TOP_CENTER);

        // Title
        Label titleLabel = new Label("Your Test Progress");
        titleLabel.setFont(Font.font("Poppins", FontWeight.BOLD, 28));
        titleLabel.setStyle("-fx-text-fill: white;");

        // Create table for test results
        TableView<TestResult> tableView = createTable();
        tableView.setMaxWidth(Double.MAX_VALUE); // Allow table to expand
        tableView.setMaxHeight(Double.MAX_VALUE); // Allow table to expand vertically

        // Use a StackPane to center the table horizontally and vertically
        StackPane tableContainer = new StackPane(tableView);
        tableContainer.setPadding(new Insets(0, 20, 20, 20)); // Adjust padding for better spacing

        content.getChildren().addAll(titleLabel, tableContainer);
        return content;
    }

    private TableView<TestResult> createTable() {
        TableView<TestResult> tableView = new TableView<>();
        tableView.setStyle("-fx-background-color: rgba(255, 255, 255, 0.9); -fx-background-radius: 10;");

        // Define columns
        TableColumn<TestResult, String> testIdCol = new TableColumn<>("Test ID");
        testIdCol.setCellValueFactory(new PropertyValueFactory<>("testId"));
        testIdCol.setPrefWidth(150);

        TableColumn<TestResult, String> studentIdCol = new TableColumn<>("Student ID");
        studentIdCol.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        studentIdCol.setPrefWidth(120);

        TableColumn<TestResult, String> studentNameCol = new TableColumn<>("Student Name");
        studentNameCol.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        studentNameCol.setPrefWidth(150);

        TableColumn<TestResult, String> gradeCol = new TableColumn<>("Grade");
        gradeCol.setCellValueFactory(new PropertyValueFactory<>("grade"));
        gradeCol.setPrefWidth(120);

        TableColumn<TestResult, Integer> scoreCol = new TableColumn<>("Score out of 100");
        scoreCol.setCellValueFactory(new PropertyValueFactory<>("score"));
        scoreCol.setPrefWidth(150);
        scoreCol.setCellFactory(col -> new TableCell<TestResult, Integer>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item + " / 100");
                }
            }
        });

        TableColumn<TestResult, Integer> starsCol = new TableColumn<>("Stars Earned");
        starsCol.setCellValueFactory(new PropertyValueFactory<>("starsEarned"));
        starsCol.setPrefWidth(120);

        tableView.getColumns().addAll(testIdCol, studentIdCol, studentNameCol, gradeCol, scoreCol, starsCol);

        // Add sample data
        ObservableList<TestResult> data = FXCollections.observableArrayList(
                new TestResult("MT_G1", "S001", "John Doe", "Grade 3", 85, 4),
                new TestResult("MT_G2", "S002", "Jane Doe", "Grade 3", 78, 3),
                new TestResult("MT_G3", "S003", "Bob Smith", "Grade 3", 92, 5),
                new TestResult("MT_G4", "S004", "Alice Johnson", "Grade 4", 88, 4),
                new TestResult("MT_G5", "S005", "Mike Brown", "Grade 4", 75, 3)
        );

        tableView.setItems(data);

        return tableView;
    }

    private Button createNavButton(String text) {
        Button btn = new Button(text);
        btn.setStyle("-fx-background-color: rgba(255, 255, 255, 0.2); -fx-text-fill: #333; " +
                "-fx-background-radius: 20; -fx-font-size: 16px; -fx-padding: 10 20;");
        btn.setFont(Font.font("Poppins", FontWeight.BOLD, 14));
        btn.setCursor(Cursor.HAND);

        btn.setOnMouseEntered(e -> {
            btn.setStyle("-fx-background-color: " + themeColor + "; -fx-text-fill: white; " +
                    "-fx-background-radius: 20; -fx-font-size: 16px; -fx-padding: 10 20;");
        });

        btn.setOnMouseExited(e -> {
            btn.setStyle("-fx-background-color: rgba(255, 255, 255, 0.2); -fx-text-fill: #333; " +
                    "-fx-background-radius: 20; -fx-font-size: 16px; -fx-padding: 10 20;");
        });

        return btn;
    }

    // Model class for test results
    public static class TestResult {
        private final String testId;
        private final String studentId;
        private final String studentName;
        private final String grade;
        private final int score;
        private final int starsEarned;

        public TestResult(String testId, String studentId, String studentName, String grade, int score, int starsEarned) {
            this.testId = testId;
            this.studentId = studentId;
            this.studentName = studentName;
            this.grade = grade;
            this.score = score;
            this.starsEarned = starsEarned;
        }

        public String getTestId() { return testId; }
        public String getStudentId() { return studentId; }
        public String getStudentName() { return studentName; }
        public String getGrade() { return grade; }
        public int getScore() { return score; }
        public int getStarsEarned() { return starsEarned; }
    }
}
