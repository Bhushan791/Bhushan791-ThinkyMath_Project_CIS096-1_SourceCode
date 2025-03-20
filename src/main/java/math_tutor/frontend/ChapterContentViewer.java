package math_tutor.frontend;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChapterContentViewer {

    private AnchorPane content;
    private final Runnable backToChapterSelectionHandler;
    private final String chapterTitle;
    private final String filePath;
    private boolean isChapterCompleted = false;

    // Colors
    private static final Color TITLE_COLOR = Color.rgb(40, 40, 140);
    private static final Color SECTION_COLOR = Color.rgb(100, 50, 150);
    private static final Color HIGHLIGHT_COLOR = Color.rgb(255, 75, 75);
    private static final Color BACKGROUND_START = Color.web("#D4E4FF");
    private static final Color BACKGROUND_END = Color.web("#73A8E5");

    // Button styles
    private static final String COMPLETE_BUTTON_STYLE_BASE =
            "-fx-background-color: linear-gradient(to right, #2ecc71, #27ae60); " +
                    "-fx-text-fill: white; " +
                    "-fx-font-size: 16px; " +
                    "-fx-padding: 12px 24px; " +
                    "-fx-background-radius: 30; " +
                    "-fx-font-weight: bold; " +
                    "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 5, 0, 0, 3);";

    private static final String COMPLETE_BUTTON_STYLE_HOVER =
            "-fx-background-color: linear-gradient(to right, #27ae60, #2ecc71); " +
                    "-fx-text-fill: white; " +
                    "-fx-font-size: 16px; " +
                    "-fx-padding: 12px 24px; " +
                    "-fx-background-radius: 30; " +
                    "-fx-font-weight: bold; " +
                    "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.5), 8, 0, 0, 5);";

    private static final String COMPLETED_BUTTON_STYLE =
            "-fx-background-color: linear-gradient(to right, #3498db, #2980b9); " +
                    "-fx-text-fill: white; " +
                    "-fx-font-size: 16px; " +
                    "-fx-padding: 12px 24px; " +
                    "-fx-background-radius: 30; " +
                    "-fx-font-weight: bold; " +
                    "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 5, 0, 0, 3);";

    public ChapterContentViewer(String chapterTitle, String filePath, Runnable backToChapterSelectionHandler) {
        this.chapterTitle = chapterTitle;
        this.filePath = filePath;
        this.backToChapterSelectionHandler = backToChapterSelectionHandler;
        this.content = createChapterContent();
    }

    private AnchorPane createChapterContent() {
        // Create gradient background
        Stop[] stops = new Stop[] {
                new Stop(0, BACKGROUND_START),
                new Stop(1, BACKGROUND_END)
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

        // Create the header section with back button and complete button
        HBox headerSection = createHeaderSection();

        // Create content display area
        VBox contentArea = createContentDisplayArea();

        // Position the header and content sections
        AnchorPane.setTopAnchor(headerSection, 20.0);
        AnchorPane.setLeftAnchor(headerSection, 20.0);
        AnchorPane.setRightAnchor(headerSection, 20.0);

        AnchorPane.setTopAnchor(contentArea, 80.0);
        AnchorPane.setLeftAnchor(contentArea, 0.0);
        AnchorPane.setRightAnchor(contentArea, 0.0);
        AnchorPane.setBottomAnchor(contentArea, 0.0);

        mainLayout.getChildren().addAll(headerSection, contentArea);

        return mainLayout;
    }

    private HBox createHeaderSection() {
        // Create back button
        Button backButton = createBackButton();

        // Create spacer to push the complete button to the right
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Create complete button
        Button completeButton = createCompleteButton();

        // Create header section
        HBox headerSection = new HBox(10, backButton, spacer, completeButton);
        headerSection.setAlignment(Pos.CENTER_LEFT);

        return headerSection;
    }

    private Button createBackButton() {
        Button backButton = new Button("← Back to Dashboard");
        backButton.setStyle(
                "-fx-background-color: linear-gradient(to right, #ff69b4, #ff1493); " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 14px; " +
                        "-fx-padding: 10px 20px; " +
                        "-fx-background-radius: 20; " +
                        "-fx-font-weight: bold; " +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 3, 0, 0, 2);"
        );
        backButton.setCursor(Cursor.HAND);

        // Add hover effect
        backButton.setOnMouseEntered(e -> {
            backButton.setStyle(
                    "-fx-background-color: linear-gradient(to right, #ff1493, #ff69b4); " +
                            "-fx-text-fill: white; " +
                            "-fx-font-size: 14px; " +
                            "-fx-padding: 10px 20px; " +

                            "-fx-background-radius: 20; " +
                            "-fx-font-weight: bold; " +
                            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.4), 5, 0, 0, 3);"
            );

            // Add glow effect
            Glow glow = new Glow(0.5);
            backButton.setEffect(glow);
        });

        backButton.setOnMouseExited(e -> {
            backButton.setStyle(
                    "-fx-background-color: linear-gradient(to right, #ff69b4, #ff1493); " +
                            "-fx-text-fill: white; " +
                            "-fx-font-size: 14px; " +
                            "-fx-padding: 10px 20px; " +
                            "-fx-background-radius: 20; " +
                            "-fx-font-weight: bold; " +
                            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 3, 0, 0, 2);"
            );

            // Remove glow effect
            DropShadow shadow = new DropShadow(3, 0, 2, Color.color(0, 0, 0, 0.2));
            backButton.setEffect(shadow);
        });

        // Add click animation
        backButton.setOnMousePressed(event -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(100), backButton);
            st.setToX(0.95);
            st.setToY(0.95);
            st.setAutoReverse(true);
            st.setCycleCount(2);
            st.play();
        });

        // Set action to use the provided back handler
        backButton.setOnAction(e -> {
            FadeTransition fadeOut = new FadeTransition(Duration.millis(300), content);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);

            fadeOut.setOnFinished(evt -> {
                // Clear existing content and return to dashboard
                BorderPane root = (BorderPane) content.getScene().getRoot();
                root.setTop(null);
                root.setCenter(null);
                root.setCenter(new StudentDashboard().createDashboard());
            });

            fadeOut.play();
        });

        return backButton;
    }


    private Button createCompleteButton() {
        Button completeButton = new Button("Click here to complete chapter ✓");
        completeButton.setStyle(COMPLETE_BUTTON_STYLE_BASE);
        completeButton.setCursor(Cursor.HAND);

        // Add hover effect
        completeButton.setOnMouseEntered(e -> {
            if (!isChapterCompleted) {
                completeButton.setStyle(COMPLETE_BUTTON_STYLE_HOVER);

                // Add glow effect
                Glow glow = new Glow(0.5);
                completeButton.setEffect(glow);
            }
        });

        completeButton.setOnMouseExited(e -> {
            if (!isChapterCompleted) {
                completeButton.setStyle(COMPLETE_BUTTON_STYLE_BASE);

                // Remove glow effect
                DropShadow shadow = new DropShadow(3, 0, 2, Color.color(0, 0, 0, 0.2));
                completeButton.setEffect(shadow);
            }
        });

        // Add click animation and functionality
        completeButton.setOnAction(e -> {
            if (!isChapterCompleted) {
                // Create a nice animation when completing the chapter
                ScaleTransition scale = new ScaleTransition(Duration.millis(300), completeButton);
                scale.setFromX(1.0);
                scale.setFromY(1.0);
                scale.setToX(1.1);
                scale.setToY(1.1);
                scale.setCycleCount(2);
                scale.setAutoReverse(true);

                // Create a translate transition for a little bounce effect
                TranslateTransition translate = new TranslateTransition(Duration.millis(300), completeButton);
                translate.setByY(-10);
                translate.setCycleCount(2);
                translate.setAutoReverse(true);

                // Play both animations in parallel
                ParallelTransition parallelTransition = new ParallelTransition(scale, translate);
                parallelTransition.setOnFinished(evt -> {
                    completeButton.setText("✓ Chapter Completed!");
                    completeButton.setStyle(COMPLETED_BUTTON_STYLE);
                    isChapterCompleted = true;

                    // Save the completion status to the database
                    // This is a placeholder for the actual database operation
                    // saveCompletionStatus(chapterTitle, true);
                    System.out.println("Chapter completed: " + chapterTitle);
                });

                parallelTransition.play();
            }
        });

        return completeButton;
    }

    private VBox createContentDisplayArea() {
        // Main content container
        VBox contentContainer = new VBox(20);
        contentContainer.setAlignment(Pos.TOP_CENTER);
        contentContainer.setPadding(new Insets(20, 30, 30, 30));

        // Create a StackPane to hold the title and its animations
        StackPane titleContainer = new StackPane();

        // Chapter title
        Text titleText = new Text(chapterTitle);
        titleText.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 32));
        titleText.setFill(TITLE_COLOR);

        // Add effects to the title
        DropShadow shadow = new DropShadow();
        shadow.setRadius(5.0);
        shadow.setOffsetX(3.0);
        shadow.setOffsetY(3.0);
        shadow.setColor(Color.color(0.0, 0.0, 0.0, 0.3));
        titleText.setEffect(shadow);

        titleContainer.getChildren().add(titleText);

        // Add animated effects to the title
        ScaleTransition st = new ScaleTransition(Duration.millis(1500), titleText);
        st.setFromX(0.8);
        st.setFromY(0.8);
        st.setToX(1.0);
        st.setToY(1.0);
        st.setCycleCount(1);
        st.setAutoReverse(false);

        FadeTransition ft = new FadeTransition(Duration.millis(1500), titleText);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);

        ParallelTransition pt = new ParallelTransition(st, ft);
        pt.play();

        // Content area with white background and rounded corners
        VBox notesBox = new VBox(20);
        notesBox.setPadding(new Insets(40));
        notesBox.setStyle(
                "-fx-background-color: rgba(255, 255, 255, 0.9); " +
                        "-fx-background-radius: 20; " +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 15, 0, 0, 8);"
        );
        notesBox.setMaxWidth(950);  // Limit max width for readability

        // Parse and load the text content
        TextFlow noteContent = loadNoteContent();

        // Add fade animation to content
        FadeTransition fadeIn = new FadeTransition(Duration.millis(1200), noteContent);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.setDelay(Duration.millis(300));
        fadeIn.play();

        notesBox.getChildren().add(noteContent);

        // Create a container for the content that will be inside the scroll pane
        VBox contentBox = new VBox(30);
        contentBox.setAlignment(Pos.TOP_CENTER);
        contentBox.getChildren().addAll(titleContainer, notesBox);
        contentBox.setPadding(new Insets(0, 0, 40, 0));  // Add padding at the bottom

        // Create a scroll pane that doesn't scroll the title
        ScrollPane scrollPane = new ScrollPane(contentBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-padding: 0;");
        scrollPane.setPannable(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        // Add a bit of padding to prevent scrolling too far
        scrollPane.setPadding(new Insets(0, 0, 20, 0));

        contentContainer.getChildren().add(scrollPane);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        return contentContainer;
    }

    private TextFlow loadNoteContent() {
        TextFlow textFlow = new TextFlow();
        textFlow.setLineSpacing(8);  // Add more space between lines for readability

        try {
            // Read the file content
            String content = readFile(filePath);

            // Parse the content with formatting
            List<Text> formattedTexts = parseFormattedText(content);
            textFlow.getChildren().addAll(formattedTexts);

        } catch (IOException e) {
            Text errorText = new Text("Error loading content: " + e.getMessage());
            errorText.setFill(Color.RED);
            errorText.setFont(Font.font("Arial", FontWeight.BOLD, 16));
            textFlow.getChildren().add(errorText);
        }

        return textFlow;
    }

    private String readFile(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString();
    }

    private List<Text> parseFormattedText(String content) {
        List<Text> textNodes = new ArrayList<>();

        // Split by lines first
        String[] lines = content.split("\n");

        for (String line : lines) {
            // Skip empty lines, but add a line break
            if (line.trim().isEmpty()) {
                Text lineBreak = new Text("\n");
                textNodes.add(lineBreak);
                continue;
            }

            // Detect patterns for formatting
            if (line.contains("✨") && (line.contains("**") || line.contains("#"))) {
                // This looks like a title
                // Create a new line before the title for better spacing
                Text spacer = new Text("\n");
                textNodes.add(spacer);

                // Add the title text with enhanced formatting
                Text titleText = new Text(line + "\n");
                titleText.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 28));
                titleText.setFill(TITLE_COLOR);

                // Add drop shadow to title
                DropShadow shadow = new DropShadow();
                shadow.setRadius(3.0);
                shadow.setOffsetX(2.0);
                shadow.setOffsetY(2.0);
                shadow.setColor(Color.color(0.0, 0.0, 0.0, 0.3));
                titleText.setEffect(shadow);

                textNodes.add(titleText);

            } else if (line.startsWith("📌") || line.contains("🌟") ||
                    line.startsWith("1️⃣") || line.startsWith("2️⃣") ||
                    line.startsWith("3️⃣") || line.startsWith("4️⃣") ||
                    line.startsWith("5️⃣")) {
                // This looks like a section header
                // Create a new line before the section header for better spacing
                Text spacer = new Text("\n");
                textNodes.add(spacer);

                Text sectionText = new Text(line + "\n");
                sectionText.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 22));
                sectionText.setFill(SECTION_COLOR);

                // Add subtle drop shadow to section headers
                DropShadow shadow = new DropShadow();
                shadow.setRadius(2.0);
                shadow.setOffsetX(1.0);
                shadow.setOffsetY(1.0);
                shadow.setColor(Color.color(0.0, 0.0, 0.0, 0.2));
                sectionText.setEffect(shadow);

                textNodes.add(sectionText);

            } else if (line.startsWith("✅") || line.startsWith("-")) {
                // List items with enhanced formatting
                Text listText = new Text(line + "\n");
                listText.setFont(Font.font("Arial", FontWeight.NORMAL, 16));

                // Make list items stand out a bit
                if (line.startsWith("✅")) {
                    listText.setFill(Color.web("#2e7d32"));  // Dark green for completed items
                    listText.setFont(Font.font("Arial", FontWeight.BOLD, 16));
                }

                textNodes.add(listText);

            } else if (line.startsWith("🎯") || line.contains("Great Job!")) {
                // Conclusion or important text with enhanced formatting
                // Add a spacer before important text
                Text spacer = new Text("\n");
                textNodes.add(spacer);

                Text importantText = new Text(line + "\n");
                importantText.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 20));
                importantText.setFill(HIGHLIGHT_COLOR);

                // Add glow effect to important text
                Glow glow = new Glow(0.3);
                importantText.setEffect(glow);

                textNodes.add(importantText);

                // Add another spacer after important text
                Text spacerAfter = new Text("\n");
                textNodes.add(spacerAfter);

            } else {
                // Process inline formatting
                List<Text> inlineFormattedText = processInlineFormatting(line);
                textNodes.addAll(inlineFormattedText);
                textNodes.add(new Text("\n")); // Add line break
            }
        }

        return textNodes;
    }

    private List<Text> processInlineFormatting(String line) {
        List<Text> textNodes = new ArrayList<>();

        // Pattern for bold text **text**
        Pattern boldPattern = Pattern.compile("\\*\\*(.*?)\\*\\*");
        Matcher boldMatcher = boldPattern.matcher(line);

        int lastEnd = 0;
        while (boldMatcher.find()) {
            // Add the text before the bold part
            if (boldMatcher.start() > lastEnd) {
                Text normalText = new Text(line.substring(lastEnd, boldMatcher.start()));
                normalText.setFont(Font.font("Arial", 16));
                textNodes.add(normalText);
            }

            // Add the bold text with enhanced formatting
            Text boldText = new Text(boldMatcher.group(1));
            boldText.setFont(Font.font("Arial", FontWeight.BOLD, 16));
            boldText.setFill(HIGHLIGHT_COLOR);
            textNodes.add(boldText);

            lastEnd = boldMatcher.end();
        }

        // Add any remaining text
        if (lastEnd < line.length()) {
            Text remainingText = new Text(line.substring(lastEnd));
            remainingText.setFont(Font.font("Arial", 16));
            textNodes.add(remainingText);
        }

        // If no formatting was found, return the whole line as normal text
        if (textNodes.isEmpty()) {
            Text normalText = new Text(line);
            normalText.setFont(Font.font("Arial", 16));
            textNodes.add(normalText);
        }

        return textNodes;
    }

    public AnchorPane getContent() {
        return content;
    }
}