package math_tutor.frontend;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
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
import math_tutor.backend.TestService;

import java.util.Arrays;

public class Leaderboard {
    private final String themeColor = "#FF6B6B"; // Main theme color used in the leaderboard interface
    private final String lightColor = "#D4E4FF"; // Light color for gradient background
    private final String darkColor = "#73A8E5"; // Dark color for gradient background

    // Method to show the leaderboard on the primary stage (window)
    public void showLeaderboard(Stage primaryStage) {
        VBox leaderboardContainer = createLeaderboardContent(); // Create the content for the leaderboard (e.g., player scores)

        // Create a linear gradient background for the leaderboard
        LinearGradient gradient = new LinearGradient(
                0, 0, 0, 1, true, null, // Gradient goes vertically (from top to bottom)
                new Stop(0, Color.web(lightColor)), // Starting color is lightColor
                new Stop(1, Color.web(darkColor)) // Ending color is darkColor
        );

        // Apply the gradient as the background for the leaderboard container
        BackgroundFill backgroundFill = new BackgroundFill(gradient, CornerRadii.EMPTY, Insets.EMPTY);
        Background background = new Background(backgroundFill); // Set the background to the leaderboard container
        leaderboardContainer.setBackground(background);

        // Get the screen size to maximize the window based on the screen resolution
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

        // Create a new scene for the leaderboard and set its size to the screen width and height
        Scene scene = new Scene(leaderboardContainer, screenBounds.getWidth(), screenBounds.getHeight());

        // Set the scene, title, and maximize the window
        primaryStage.setScene(scene); // Set the scene to the primary stage
        primaryStage.setTitle("Leaderboard"); // Set the window title to "Leaderboard"
        primaryStage.setMaximized(true); // Maximize the window to fill the screen
        primaryStage.show(); // Display the primary stage with the leaderboard content
    }


    public VBox createLeaderboardContent() {
        VBox leaderboardContainer = new VBox(20);
        leaderboardContainer.setAlignment(Pos.CENTER);
        leaderboardContainer.setPadding(new Insets(20, 40, 40, 40));

        // Enhanced Title Section
        Label titleLabel = new Label("LEADERBOARD");
        titleLabel.setFont(Font.font("Poppins", FontWeight.BOLD, 48));
        titleLabel.setTextFill(Color.web(themeColor));
        titleLabel.setStyle("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 10, 0, 0, 3);");

        Label subtitleLabel = new Label("Top performing students this month");
        subtitleLabel.setFont(Font.font("Poppins", FontWeight.NORMAL, 20));
        subtitleLabel.setTextFill(Color.web("#333333"));
        subtitleLabel.setPadding(new Insets(0, 0, 30, 0));

        ObservableList<Student> leaderboardData = FXCollections.observableArrayList();
        TestService.LeaderboardEntry[] entries = TestService.getLeaderboard().toArray(new TestService.LeaderboardEntry[0]);

        // Sort entries by score in descending order
        Arrays.sort(entries, (a, b) -> Integer.compare(b.score, a.score));

        // Create Student objects with ranks
        for (int i = 0; i < entries.length; i++) {
            leaderboardData.add(new Student(
                    i + 1, // Rank
                    entries[i].getStudentName(),
                    entries[i].getScore()
            ));
        }

        TableView<Student> leaderboardTable = new TableView<>();
        leaderboardTable.setItems(leaderboardData);
        leaderboardTable.setPrefHeight(900);
        leaderboardTable.setStyle(
                "-fx-background-color: rgba(255, 255, 255, 0.95); " +
                        "-fx-background-radius: 25; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.25), 15, 0, 0, 8); " +
                        "-fx-padding: 10; " +
                        "-fx-border-color: " + themeColor + "; " +
                        "-fx-border-width: 0 0 0 0; " +
                        "-fx-border-radius: 25;"
        );
        leaderboardTable.setSelectionModel(null);

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

        TableColumn<Student, String> starsCol = new TableColumn<>("Stars");
        starsCol.setCellValueFactory(new PropertyValueFactory<>("starsWithIcon"));
        starsCol.setPrefWidth(120);
        starsCol.setStyle(
                "-fx-alignment: CENTER; " +
                        "-fx-font-size: 16px; " +
                        "-fx-padding: 10px;"
        );

        leaderboardTable.getColumns().addAll(rankCol, nameCol, starsCol);
        leaderboardTable.setFixedCellSize(60);
        leaderboardTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        leaderboardTable.setStyle(leaderboardTable.getStyle() +
                "-fx-table-header-background: linear-gradient(to bottom, " + lightColor + ", " + darkColor + "); " +
                "-fx-table-cell-border-color: rgba(115, 168, 229, 0.3);"
        );

        leaderboardTable.setRowFactory(tv -> {
            TableRow<Student> row = new TableRow<>();
            row.itemProperty().addListener((obs, oldItem, newItem) -> {
                if (newItem != null) {
                    final Color lightGold = Color.rgb(255, 215, 0, 0.7);
                    final Color lightRoyalBlue = Color.rgb(65, 105, 225, 0.7);
                    final Color lightRubyRed = Color.rgb(224, 17, 95, 0.7);

                    if (newItem.getRank() == 1) {
                        row.setStyle("-fx-background-color: " + colorToHex(lightGold) + ";");
                    } else if (newItem.getRank() == 2) {
                        row.setStyle("-fx-background-color: " + colorToHex(lightRoyalBlue) + ";");
                    } else if (newItem.getRank() == 3) {
                        row.setStyle("-fx-background-color: " + colorToHex(lightRubyRed) + ";");
                    } else {
                        row.setStyle("-fx-background-color: " + (row.getIndex() % 2 == 0 ? "#F0F0F0" : "#FFFFFF") + ";");
                    }
                }
            });
            return row;
        });

        leaderboardContainer.getChildren().addAll(titleLabel, subtitleLabel, leaderboardTable);

        // Enhanced Legend Section
        HBox legendBox = new HBox(10);
        legendBox.setAlignment(Pos.CENTER_RIGHT);
        legendBox.setPadding(new Insets(20, 0, 0, 0));

        Label legendLabel = new Label("★ = Achievement points earned from completed activities");
        legendLabel.setTextFill(Color.web("#333333"));
        legendLabel.setFont(Font.font("Poppins", FontWeight.NORMAL, 14));

        legendBox.getChildren().add(legendLabel);
        leaderboardContainer.getChildren().add(legendBox);
        return leaderboardContainer;
    }

    private String colorToHex(Color color) {
        return String.format("#%02x%02x%02x%02x",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255),
                (int) (color.getOpacity() * 255));
    }

    public static class Student {
        private final Integer rank;
        private final String name;
        private final Integer stars;

        public Student(Integer rank, String name, Integer stars) {
            this.rank = rank;
            this.name = name;
            this.stars = stars;
        }

        public Integer getRank() {
            return rank;
        }

        public String getName() {
            return name;
        }

        public Integer getStars() {
            return stars;
        }

        public String getStarsWithIcon() {
            return stars + " ★";
        }
    }
}
