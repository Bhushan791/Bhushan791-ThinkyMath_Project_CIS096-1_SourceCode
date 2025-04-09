package math_tutor.frontend;

import javafx.animation.ScaleTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;
import math_tutor.backend.TestService;

import java.util.Arrays;

public class ProgressTracker {
    private final String themeColor = "#FF6B6B";
    private final String lightColor = "#D4E4FF";
    private final String darkColor = "#73A8E5";

    public BorderPane createProgressTrackerContent(StudentDashboard dashboard) {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, " + lightColor + ", " + darkColor + ");");

        // Header with back button
        Button backBtn = createNavButton("🔙 Back");
        backBtn.setOnAction(e -> dashboard.showDashboardView());

        HBox header = new HBox(backBtn);
        header.setPadding(new Insets(20));
        root.setTop(header);

        // Main content
        VBox content = new VBox(20);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(40));
        content.getChildren().addAll(createTitle(), createProgressTable(dashboard.getLoggedInUsername()));
        root.setCenter(content);

        return root;
    }

    private Label createTitle() {
        Label title = new Label("Your Test History");
        title.setFont(Font.font("Poppins", FontWeight.BOLD, 36));
        title.setTextFill(Color.web(themeColor));
        title.setStyle("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 10, 0, 0, 3);");
        return title;
    }

    private TableView<TestHistory> createProgressTable(String username) {
        ObservableList<TestHistory> testHistory = FXCollections.observableArrayList();
        TestService.TestHistoryEntry[] entries = TestService.getTestHistory(username).toArray(new TestService.TestHistoryEntry[0]);

        // Sort entries by date in descending order
        Arrays.sort(entries, (a, b) -> b.testDate.compareTo(a.testDate));

        // Create TestHistory objects
        for (TestService.TestHistoryEntry entry : entries) {
            testHistory.add(new TestHistory(
                    entry.testDate,
                    entry.testName,
                    entry.score
            ));
        }

        TableView<TestHistory> table = new TableView<>();
        table.setItems(testHistory);
        table.setPrefHeight(600);
        table.setStyle(
                "-fx-background-color: rgba(255, 255, 255, 0.95); " +
                        "-fx-background-radius: 25; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.25), 15, 0, 0, 8); " +
                        "-fx-padding: 10; " +
                        "-fx-border-color: " + themeColor + "; " +
                        "-fx-border-width: 0 0 0 0; " +
                        "-fx-border-radius: 25;"
        );
        table.setSelectionModel(null);

        TableColumn<TestHistory, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("testDate"));
        dateCol.setPrefWidth(200);
        dateCol.setStyle("-fx-alignment: CENTER; -fx-font-size: 14px; -fx-padding: 10px;");

        TableColumn<TestHistory, String> testNameCol = new TableColumn<>("Test Name");
        testNameCol.setCellValueFactory(new PropertyValueFactory<>("testName"));
        testNameCol.setPrefWidth(300);
        testNameCol.setStyle("-fx-alignment: CENTER-LEFT; -fx-font-size: 14px; -fx-padding: 10px 20px;");

        TableColumn<TestHistory, String> scoreCol = new TableColumn<>("Score");
        scoreCol.setCellValueFactory(new PropertyValueFactory<>("score"));
        scoreCol.setPrefWidth(150);
        scoreCol.setStyle("-fx-alignment: CENTER; -fx-font-size: 14px; -fx-padding: 10px;");

        table.getColumns().removeAll(table.getColumns());
        table.getColumns().addAll(dateCol, testNameCol, scoreCol);
        table.setFixedCellSize(60);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        table.setStyle(table.getStyle() +
                "-fx-table-header-background: linear-gradient(to bottom, " + lightColor + ", " + darkColor + "); " +
                "-fx-table-cell-border-color: rgba(115, 168, 229, 0.3);"
        );

        return table;
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
            playScaleAnimation(btn, 1.05);
        });

        btn.setOnMouseExited(e -> {
            btn.setStyle("-fx-background-color: rgba(255, 255, 255, 0.2); -fx-text-fill: #333; " +
                    "-fx-background-radius: 20; -fx-font-size: 16px; -fx-padding: 10 20;");
            playScaleAnimation(btn, 1.0);
        });

        return btn;
    }

    private void playScaleAnimation(Button button, double scale) {
        ScaleTransition st = new ScaleTransition(Duration.millis(150), button);
        st.setToX(scale);
        st.setToY(scale);
        st.play();
    }

    private String colorToHex(Color color) {
        return String.format("#%02x%02x%02x%02x",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255),
                (int) (color.getOpacity() * 255));
    }

    public static class TestHistory {
        private final String testDate;
        private final String testName;
        private final int score;

        public TestHistory(String testDate, String testName, int score) {
            this.testDate = testDate;
            this.testName = testName;
            this.score = score;
        }

        public String getTestDate() {
            return testDate;
        }

        public String getTestName() {
            return testName;
        }

        public int getScore() {
            return score;
        }
    }
}
