package math_tutor.frontend;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.geometry.Rectangle2D;

public class Leaderboard extends Application {
    private final String themeColor = "#FF6B6B";
    private final String lightColor = "#D4E4FF";
    private final String darkColor = "#73A8E5";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        showLeaderboard(primaryStage);
    }

    public void showLeaderboard(Stage primaryStage) {
        VBox leaderboardContainer = createLeaderboardContent();

        // Create a background with a linear gradient
        LinearGradient gradient = new LinearGradient(
                0, 0, 0, 1, true, null,
                new Stop(0, Color.web(lightColor)),
                new Stop(1, Color.web(darkColor))
        );

        BackgroundFill backgroundFill = new BackgroundFill(
                gradient,
                CornerRadii.EMPTY,
                Insets.EMPTY
        );
        Background background = new Background(backgroundFill);
        leaderboardContainer.setBackground(background);

        // Get screen dimensions
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double screenWidth = screenBounds.getWidth();
        double screenHeight = screenBounds.getHeight();

        // Create the scene with the screen width and a fixed height
        Scene scene = new Scene(leaderboardContainer, screenWidth, screenHeight);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Leaderboard"); // Set title

        // Maximize the stage
        primaryStage.setMaximized(true);

        primaryStage.show();
    }

    public VBox createLeaderboardContent() {
        VBox leaderboardContainer = new VBox(20);
        leaderboardContainer.setAlignment(Pos.CENTER);
        leaderboardContainer.setPadding(new Insets(20, 40, 40, 40));

        // Create title
        Label titleLabel = new Label("LEADERBOARD");
        titleLabel.setFont(Font.font("Poppins", FontWeight.BOLD, 40));
        titleLabel.setTextFill(Color.web(themeColor));
        titleLabel.setStyle("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 10, 0, 0, 3);");

        // Create subtitle
        Label subtitleLabel = new Label("Top performing students this month");
        subtitleLabel.setFont(Font.font("Poppins", FontWeight.NORMAL, 18));
        subtitleLabel.setTextFill(Color.web("#333333"));
        subtitleLabel.setPadding(new Insets(0, 0, 20, 0));

        // Dummy data for leaderboard
        ObservableList<Student> leaderboardData = FXCollections.observableArrayList(
                new Student(1, "Harry", 5, 80),
                new Student(2, "Emma", 5, 75),
                new Student(3, "Ron", 4, 65),
                new Student(4, "Hermione", 5, 60),
                new Student(5, "Luna", 3, 55),
                new Student(6, "Draco", 4, 50),
                new Student(7, "Neville", 3, 45),
                new Student(8, "Ginny", 4, 40),
                new Student(9, "Fred", 2, 35),
                new Student(10, "George", 2, 30)
        );

        // Create table with improved styling
        TableView<Student> leaderboardTable = new TableView<>();
        leaderboardTable.setItems(leaderboardData);
        leaderboardTable.setPrefHeight(900); // Make table bigger
        leaderboardTable.setStyle(
                "-fx-background-color: rgba(255, 255, 255, 0.95); " +
                        "-fx-background-radius: 20; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.25), 15, 0, 0, 8); " +
                        "-fx-padding: 10; " +
                        "-fx-border-color: " + themeColor + "; " +
                        "-fx-border-width: 0 0 0 0; " +
                        "-fx-border-radius: 20;"
        );

        // Disable selection on the table
        leaderboardTable.setSelectionModel(null);


        // Create and style columns with consistent width and alignment
        TableColumn<Student, Integer> rankCol = new TableColumn<>("Rank");
        rankCol.setCellValueFactory(new PropertyValueFactory<>("rank"));
        rankCol.setPrefWidth(120);
        rankCol.setStyle(
                "-fx-alignment: CENTER; " +
                        "-fx-font-weight: bold; " +
                        "-fx-font-size: 16px; " +
                        "-fx-padding: 10px;"
        );

        TableColumn<Student, String> nameCol = new TableColumn<>("Student Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setPrefWidth(300);
        nameCol.setStyle(
                "-fx-alignment: CENTER-LEFT; " +
                        "-fx-font-size: 16px; " +
                        "-fx-padding: 10px 20px;"
        );

        TableColumn<Student, Integer> gradeCol = new TableColumn<>("Grade");
        gradeCol.setCellValueFactory(new PropertyValueFactory<>("grade"));
        gradeCol.setPrefWidth(120);
        gradeCol.setStyle(
                "-fx-alignment: CENTER; " +
                        "-fx-font-size: 16px; " +
                        "-fx-padding: 10px;"
        );

        TableColumn<Student, String> starsCol = new TableColumn<>("Stars");
        starsCol.setCellValueFactory(new PropertyValueFactory<>("starsWithIcon"));
        starsCol.setPrefWidth(120);
        starsCol.setStyle(
                "-fx-alignment: CENTER; " +
                        "-fx-font-size: 16px; " +
                        "-fx-padding: 10px;"
        );

        leaderboardTable.getColumns().addAll(rankCol, nameCol, gradeCol, starsCol);

        // Set row height and style for better spacing
        leaderboardTable.setFixedCellSize(60);

        // Apply consistent resize policy
        leaderboardTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Style the table header
        leaderboardTable.setStyle(leaderboardTable.getStyle() +
                "-fx-table-header-background: linear-gradient(to bottom, " + lightColor + ", " + darkColor + "); " +
                "-fx-table-cell-border-color: rgba(115, 168, 229, 0.3);"
        );

        // Apply row styling with alternating colors and stable visibility
        leaderboardTable.setRowFactory(tv -> {
            javafx.scene.control.TableRow<Student> row = new javafx.scene.control.TableRow<Student>() {
                @Override
                protected void updateItem(Student student, boolean empty) {
                    super.updateItem(student, empty);
                    if (empty || student == null) {
                        setStyle("");
                    } else {
                        // Define lighter versions of the highlight colors
                        final Color lightGold = Color.rgb(255, 215, 0, 0.7);   // Lighter Gold
                        final Color lightRoyalBlue = Color.rgb(65, 105, 225, 0.7); // Lighter Royal Blue
                        final Color lightRubyRed = Color.rgb(224, 17, 95, 0.7);  // Lighter Ruby Red


                        // Highlight top positions with graduated colors
                        if (student.getRank() == 1) {
                            setStyle("-fx-background-color: " + colorToHex(lightGold) + ";"); // Light Gold
                        } else if (student.getRank() == 2) {
                            setStyle("-fx-background-color: " + colorToHex(lightRoyalBlue) + ";"); // Light Royal Blue
                        } else if (student.getRank() == 3) {
                            setStyle("-fx-background-color:  " + colorToHex(lightRubyRed) + ";"); // Light Ruby Red
                        } else if (getIndex() % 2 == 0) {
                            setStyle("-fx-background-color: #F0F0F0;"); // Light gray
                        } else {
                            setStyle("-fx-background-color: #FFFFFF;"); // White
                        }
                        setCursor(javafx.scene.Cursor.DEFAULT); // Disable cursor change on hover
                    }
                }
            };
            return row;
        });

        leaderboardContainer.getChildren().addAll(titleLabel, subtitleLabel, leaderboardTable);

        // Create legend for stars at the bottom
        HBox legendBox = new HBox(10);
        legendBox.setAlignment(Pos.CENTER_RIGHT);
        legendBox.setPadding(new Insets(20, 0, 0, 0));

        Label legendLabel = new Label("★ = Achievement points earned from completed activities");
        legendLabel.setTextFill(Color.web("#333333"));
        legendLabel.setFont(Font.font("Poppins", FontWeight.NORMAL, 14));

        legendBox.getChildren().add(legendLabel);

        // Add legend to container
        leaderboardContainer.getChildren().add(legendBox);
        return leaderboardContainer;
    }

    // Utility method to convert Color to Hex
    private String colorToHex(Color color) {
        return String.format("#%02x%02x%02x%02x",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255),
                (int) (color.getOpacity() * 255));
    }


    // Student class to represent leaderboard entries
    public static class Student {
        private final Integer rank;
        private final String name;
        private final Integer grade;
        private final Integer stars;

        public Student(Integer rank, String name, Integer grade, Integer stars) {
            this.rank = rank;
            this.name = name;
            this.grade = grade;
            this.stars = stars;
        }

        public Integer getRank() {
            return rank;
        }

        public String getName() {
            return name;
        }

        public Integer getGrade() {
            return grade;
        }

        public Integer getStars() {
            return stars;
        }

        public String getStarsWithIcon() {
            return stars + " ★";
        }
    }

}
