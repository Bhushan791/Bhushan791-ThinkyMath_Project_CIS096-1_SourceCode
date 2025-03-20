package math_tutor.frontend;

import javafx.animation.ScaleTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;



import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ChapterSelectionContent {









    private final Map<Integer, List<String>> chaptersMap = new HashMap<>();
    private final String[] buttonColors = {
            "#4CAF50", // Green
            "#2196F3", // Blue
            "#9C27B0", // Purple
            "#FF9800", // Orange
            "#E91E63"  // Pink
    };

    private AnchorPane content;
    private final Runnable backToGradeSelectionHandler; // Handler for back button

    public ChapterSelectionContent(int grade, Runnable backToGradeSelectionHandler) {
        this.backToGradeSelectionHandler = backToGradeSelectionHandler;
        initializeChapters();
        content = createChapterSelectionContent(grade);

        // Ensure the content pane fills the entire window
// Ensure the content pane fills the entire window
        content.setMaxWidth(Double.MAX_VALUE);
        content.setMaxHeight(Double.MAX_VALUE);

    }

    private void initializeChapters() {
        // Grade 1 Chapters
        List<String> grade1Chapters = new ArrayList<>();
        grade1Chapters.add("Grade1Chapter1: Numbers 1-10");
        grade1Chapters.add("Grade1Chapter2: Addition within 10");
        grade1Chapters.add("Grade1Chapter3: Subtraction within 10");
        grade1Chapters.add("Grade1Chapter4: Shapes and Patterns");
        grade1Chapters.add("Grade1Chapter5: Numbers 11-20");
        chaptersMap.put(1, grade1Chapters);

        // Grade 2 Chapters
        List<String> grade2Chapters = new ArrayList<>();
        grade2Chapters.add("Grade2Chapter1: Place Value");
        grade2Chapters.add("Grade2Chapter2: Addition within 100");
        grade2Chapters.add("Grade2Chapter3: Subtraction within 100");
        grade2Chapters.add("Grade2Chapter4: Time and Money");
        grade2Chapters.add("Grade2Chapter5: Measurements");
        chaptersMap.put(2, grade2Chapters);

        // Grade 3 Chapters
        List<String> grade3Chapters = new ArrayList<>();
        grade3Chapters.add("Grade3Chapter1: Multiplication Basics");
        grade3Chapters.add("Grade3Chapter2: Division Basics");
        grade3Chapters.add("Grade3Chapter3: Fractions Introduction");
        grade3Chapters.add("Grade3Chapter4: Geometry and Area");
        grade3Chapters.add("Grade3Chapter5: Data and Graphs");
        chaptersMap.put(3, grade3Chapters);

        // Grade 4 Chapters
        List<String> grade4Chapters = new ArrayList<>();
        grade4Chapters.add("Grade4Chapter1: Multi-digit Multiplication");
        grade4Chapters.add("Grade4Chapter2: Long Division");
        grade4Chapters.add("Grade4Chapter3: Fractions and Decimals");
        grade4Chapters.add("Grade4Chapter4: Angles and Triangles");
        grade4Chapters.add("Grade4Chapter5: Perimeter and Area");
        chaptersMap.put(4, grade4Chapters);

        // Grade 5 Chapters
        List<String> grade5Chapters = new ArrayList<>();
        grade5Chapters.add("Grade5Chapter1: Order of Operations");
        grade5Chapters.add("Grade5Chapter2: Decimal Operations");
        grade5Chapters.add("Grade5Chapter3: Fraction Operations");
        grade5Chapters.add("Grade5Chapter4: Volume and 3D Shapes");
        grade5Chapters.add("Grade5Chapter5: Coordinate Grid");
        chaptersMap.put(5, grade5Chapters);
    }

    private AnchorPane createChapterSelectionContent(int grade) {
        // Create gradient background from #D4E4FF to #73A8E5
        Stop[] stops = new Stop[] {
                new Stop(0, Color.web("#D4E4FF")),
                new Stop(1, Color.web("#73A8E5"))
        };
        LinearGradient gradient = new LinearGradient(0, 0, 1, 0, true, null, stops);

        AnchorPane mainLayout = new AnchorPane();
        mainLayout.setBackground(new Background(new BackgroundFill(gradient, null, null)));

        // Set the mainLayout to fill the entire window
        mainLayout.setPrefWidth(Region.USE_COMPUTED_SIZE);
        mainLayout.setPrefHeight(Region.USE_COMPUTED_SIZE);
        mainLayout.setMaxWidth(Double.MAX_VALUE);
        mainLayout.setMaxHeight(Double.MAX_VALUE);
        HBox.setHgrow(mainLayout, Priority.ALWAYS);
        VBox.setVgrow(mainLayout, Priority.ALWAYS);

        // Back to Grade Selection Button
        Button backButton = new Button("← Back to Grade Selection");
        backButton.setStyle(
                "-fx-background-color: #ff69b4; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 14px; " +
                        "-fx-padding: 8px 16px; " +
                        "-fx-background-radius: 20; " +
                        "-fx-font-weight: bold; " +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 3, 0, 0, 2);"
        );
        backButton.setCursor(Cursor.HAND);

        // Add hover effect to back button
        backButton.setOnMouseEntered(e -> {
            backButton.setStyle(
                    "-fx-background-color: #ff1493; " +
                            "-fx-text-fill: white; " +
                            "-fx-font-size: 14px; " +
                            "-fx-padding: 8px 16px; " +
                            "-fx-background-radius: 20; " +
                            "-fx-font-weight: bold; " +
                            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.4), 5, 0, 0, 3);"
            );
        });

        backButton.setOnMouseExited(e -> {
            backButton.setStyle(
                    "-fx-background-color: #ff69b4; " +
                            "-fx-text-fill: white; " +
                            "-fx-font-size: 14px; " +
                            "-fx-padding: 8px 16px; " +
                            "-fx-background-radius: 20; " +
                            "-fx-font-weight: bold; " +
                            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 3, 0, 0, 2);"
            );
        });

        // Add click animation for back button
        backButton.setOnMousePressed(event -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(100), backButton);
            st.setToX(0.95);
            st.setToY(0.95);
            st.setAutoReverse(true);
            st.setCycleCount(2);
            st.play();
        });

        // Set the back button action to use the provided handler
        backButton.setOnAction(e -> {
            if (backToGradeSelectionHandler != null) {
                backToGradeSelectionHandler.run();
            }
        });

        // Title showing selected grade
        Text gradeTitle = new Text("Grade " + grade + " Chapters 📚");
        gradeTitle.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 36));
        gradeTitle.setFill(Color.web("#ff1493"));

        // Create VBox for the chapter buttons to align vertically
        VBox chaptersBox = new VBox(20); // Spacing between buttons
        chaptersBox.setAlignment(Pos.CENTER);
        chaptersBox.setPadding(new Insets(20, 0, 50, 0)); // Add padding at the bottom

        // Make sure the chaptersBox expands to fill available width
        chaptersBox.setMaxWidth(Double.MAX_VALUE);

        // Get chapters for the selected grade
        List<String> chapters = chaptersMap.get(grade);

        // Create buttons for each chapter
        for (int i = 0; i < chapters.size(); i++) {
            final String chapter = chapters.get(i);
            String buttonColor = buttonColors[i % buttonColors.length];
            String hoverColor = darkenColor(buttonColor);

            Button chapterButton = createChapterButton(chapter, buttonColor, hoverColor);
            chaptersBox.getChildren().add(chapterButton);
        }

        // Wrap chaptersBox in an HBox for better responsiveness
        HBox wrapperBox = new HBox(chaptersBox);
        wrapperBox.setAlignment(Pos.CENTER); // Center align horizontally
        wrapperBox.setMaxWidth(Double.MAX_VALUE); // Allow it to stretch fully
        HBox.setHgrow(wrapperBox, Priority.ALWAYS);

        // Main content area with wrapperBox instead of chaptersBox directly
        VBox centerContent = new VBox(20, gradeTitle, wrapperBox);
        centerContent.setAlignment(Pos.CENTER);
        centerContent.setPadding(new Insets(20, 0, 0, 0));

        // Ensure the centerContent fills the available width
        centerContent.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(centerContent, Priority.ALWAYS);
        VBox.setVgrow(centerContent, Priority.ALWAYS);

        // Layout with back button and content
        AnchorPane.setTopAnchor(backButton, 20.0);
        AnchorPane.setLeftAnchor(backButton, 20.0);

        // Make centerContent fill the entire area
        AnchorPane.setTopAnchor(centerContent, 70.0);
        AnchorPane.setLeftAnchor(centerContent, 0.0);
        AnchorPane.setRightAnchor(centerContent, 0.0);
        AnchorPane.setBottomAnchor(centerContent, 0.0);

        mainLayout.getChildren().addAll(backButton ,centerContent );

        return mainLayout;
    }


    private Button createChapterButton(String chapterText, String baseColor, String hoverColor) {
        Button button = new Button(chapterText);


        String baseStyle =
                "-fx-background-color: " + baseColor + "; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 16px; " +
                        "-fx-padding: 15px; " +
                        "-fx-background-radius: 15; " +
                        "-fx-font-weight: bold; " +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 5, 0, 0, 3); " +
                        "-fx-min-width: 280px; " +
                        "-fx-max-width: 450px; " + // Increased max-width
                        "-fx-min-height: 100px; " +
                        "-fx-alignment: center;";

        String hoverStyle =
                "-fx-background-color: " + hoverColor + "; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 16px; " +
                        "-fx-padding: 15px; " +
                        "-fx-background-radius: 15; " +
                        "-fx-font-weight: bold; " +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.5), 8, 0, 0, 5); " +
                        "-fx-min-width: 280px; " +
                        "-fx-max-width: 450px; " + // Increased max-width
                        "-fx-min-height: 100px; " +
                        "-fx-alignment: center;";

        button.setStyle(baseStyle);
        button.setWrapText(true);
        button.setCursor(Cursor.HAND);

        // Make the button expand to fill available space
        button.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(button, Priority.ALWAYS);

        // Add hover effect
        button.setOnMouseEntered(e -> button.setStyle(hoverStyle));
        button.setOnMouseExited(e -> button.setStyle(baseStyle));

        // Add click animation
        button.setOnMousePressed(event -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(100), button);
            st.setToX(0.95);
            st.setToY(0.95);
            st.setAutoReverse(true);
            st.setCycleCount(2);
            st.play();
        });

        // Set the button action
// Update the createChapterButton method in your ChapterSelectionContent class
// Replace the current button action with this code:

// Inside your createChapterButton method, replace the button.setOnAction event handler with this:
        button.setOnAction(event -> {
            // Extract grade and chapter numbers from the chapter text
            String chapterTextValue = chapterText;
            int grade = 0;
            int chapterNumber = 0;

            // Parse grade and chapter number from the chapter text
            if (chapterText.startsWith("Grade")) {
                // Format is like "Grade1Chapter1: Numbers 1-10"
                String[] parts = chapterText.split("Chapter");
                if (parts.length > 0) {
                    String gradePart = parts[0].replace("Grade", "").trim();
                    try {
                        grade = Integer.parseInt(gradePart);
                    } catch (NumberFormatException e) {
                        System.err.println("Could not parse grade number: " + gradePart);
                    }
                }

                if (parts.length > 1) {
                    String chapterPart = parts[1];
                    int colonIndex = chapterPart.indexOf(':');
                    if (colonIndex > 0) {
                        chapterPart = chapterPart.substring(0, colonIndex);
                    }
                    try {
                        chapterNumber = Integer.parseInt(chapterPart.trim());
                    } catch (NumberFormatException e) {
                        System.err.println("Could not parse chapter number: " + chapterPart);
                    }
                }
            }

            // If parsing was successful, load the appropriate chapter content
            if (grade > 0 && chapterNumber > 0) {
                System.out.println("Loading Grade " + grade + ", Chapter " + chapterNumber);

                // Generate the file path based on grade and chapter
                String filePath = "src/main/resources/notes/grade" + grade + "_chapter" + chapterNumber + ".txt";

//


                // Create a back handler to return to the chapter selection screen
                final AnchorPane currentPane = content; // Save reference to the current content pane
                // Create a back handler to return to the chapter selection screen
                Runnable backToChapterSelection = () -> {
                    // Replace the chapter content with the chapter selection content
                    AnchorPane parent = (AnchorPane) currentPane.getParent();
                    if (parent != null) {
                        // Clear the parent
                        parent.getChildren().clear();

                        // Reset the opacity to ensure it's visible
                        currentPane.setOpacity(1.0);

                        // Ensure it fills the entire space
                        AnchorPane.setTopAnchor(currentPane, 0.0);
                        AnchorPane.setRightAnchor(currentPane, 0.0);
                        AnchorPane.setBottomAnchor(currentPane, 0.0);
                        AnchorPane.setLeftAnchor(currentPane, 0.0);

                        // Add the content back to the parent
                        parent.getChildren().add(currentPane);

                        // Force layout refresh
                        parent.requestLayout();
                    }
                };
                // Create and display the chapter content
                ChapterContentViewer contentViewer = new ChapterContentViewer(
                        chapterTextValue, filePath, backToChapterSelection);
                AnchorPane chapterContentPane = contentViewer.getContent();

                // Replace current content with chapter content
                AnchorPane parent = (AnchorPane) content.getParent();
                if (parent != null) {
                    parent.getChildren().clear();

                    // Ensure it fills the entire space
                    AnchorPane.setTopAnchor(chapterContentPane, 0.0);
                    AnchorPane.setRightAnchor(chapterContentPane, 0.0);
                    AnchorPane.setBottomAnchor(chapterContentPane, 0.0);
                    AnchorPane.setLeftAnchor(chapterContentPane, 0.0);

                    parent.getChildren().add(chapterContentPane);
                }
            } else {
                System.out.println("Could not determine grade and chapter numbers from: " + chapterText);
            }
        });

        return button;
    }

    private String darkenColor(String colorHex) {
        Color color = Color.web(colorHex);
        double factor = 0.8; // Darkening factor
        Color darker = color.deriveColor(0, 1, factor, 1);
        return String.format("#%02X%02X%02X",
                (int)(darker.getRed() * 255),
                (int)(darker.getGreen() * 255),
                (int)(darker.getBlue() * 255));
    }

    public AnchorPane getContent() {
        return content;
    }
}