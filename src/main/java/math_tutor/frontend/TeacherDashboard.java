package math_tutor.frontend;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import org.w3c.dom.ls.LSOutput;
import math_tutor.backend.TestService;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

public class TeacherDashboard {

    private Home home; // Reference to the Home application, used to navigate between screens
    private String teacherName = "Sandhya Chaudhary"; // Teacher's name, initialized as "Sandhya Chaudhary"
    private BorderPane dashboard; // Reference to the dashboard layout for the teacher's interface

    // Inner class to store and manage student performance data
    public static class StudentPerformance {
        private final String testId; // Unique ID for the test
        private final String studentId; // Unique ID for the student
        private final String testName; // Name of the test
        private final String studentName; // Name of the student
        private final String score; // The score obtained by the student
        private final String completionDate; // Date when the test was completed

        // Constructor to initialize the student performance data
        public StudentPerformance(String testId, String studentId, String testName,
                                  String studentName, String score, String completionDate) {
            this.testId = testId; // Set the test ID
            this.studentId = studentId; // Set the student ID
            this.testName = testName; // Set the test name
            this.studentName = studentName; // Set the student's name
            this.score = score; // Set the student's score
            this.completionDate = completionDate; // Set the completion date of the test
        }



    public String getTestId() { return testId; }
        public String getStudentId() { return studentId; }
        public String getTestName() { return testName; }
        public String getStudentName() { return studentName; }
        public String getScore() { return score; }
        public String getCompletionDate() { return completionDate; }
    }

    public TeacherDashboard(Home home) {
        this.home = home;
    }

    public TeacherDashboard(Home home, String teacherName) {
        this.home = home;
        this.teacherName = teacherName;
    }

    public BorderPane createDashboard() {
        dashboard = new BorderPane();
        dashboard.setTop(createHeader());
        dashboard.setCenter(createMainContent());
        dashboard.setLeft(createSidebar());
        return dashboard;
    }

    private HBox createHeader() {
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(15, 25, 15, 25));
        header.setStyle("-fx-background-color: linear-gradient(to right, #FF69B4, #FF1493); -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 5, 0, 0, 2);");

        Label titleLabel = new Label("ThinkyMath");
        titleLabel.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 28));
        titleLabel.setTextFill(Color.WHITE);

        Label dashboardLabel = new Label(" | Teacher Dashboard 👩‍🏫");
        dashboardLabel.setFont(Font.font("Comic Sans MS", 20));
        dashboardLabel.setTextFill(Color.WHITE);

        HBox spacer = new HBox();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label teacherLabel = new Label("Welcome, " + teacherName + "!");
        teacherLabel.setFont(Font.font("Comic Sans MS", 18));
        teacherLabel.setTextFill(Color.WHITE);

        header.getChildren().addAll(titleLabel, dashboardLabel, spacer, teacherLabel);

        return header;
    }

    private VBox createSidebar() {
        VBox sidebar = new VBox(15);
        sidebar.setPadding(new Insets(20));
        sidebar.setPrefWidth(250);
        sidebar.setStyle("-fx-background-color: rgba(255,255,255,0.9);");

        Text menuHeader = new Text("Menu 📋");
        menuHeader.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 24));
        menuHeader.setFill(Color.web("#FF1493"));

        Button dashboardBtn = createMenuButton("📊 Dashboard", true);
        Button profileBtn = createMenuButton("👤 Profile", false);

        // Add action to dashboard button
        dashboardBtn.setOnAction(e -> {
            // Set selected styles
            dashboardBtn.setStyle("-fx-background-color: #FF69B4; -fx-text-fill: white; -fx-font-size: 16px; " +
                    "-fx-background-radius: 15; -fx-padding: 10px 20px; -fx-font-weight: bold; -fx-cursor: hand; " +
                    "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 5, 0, 0, 3);");

            profileBtn.setStyle("-fx-background-color: white; -fx-text-fill: #555555; -fx-font-size: 16px; " +
                    "-fx-background-radius: 15; -fx-padding: 10px 20px; -fx-font-weight: bold; -fx-cursor: hand; " +
                    "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 3, 0, 0, 1);");

            // Show dashboard content
            dashboard.setCenter(createMainContent());
        });

        // Add action to profile button
        profileBtn.setOnAction(e -> {
            // Set selected styles
            profileBtn.setStyle("-fx-background-color: #FF69B4; -fx-text-fill: white; -fx-font-size: 16px; " +
                    "-fx-background-radius: 15; -fx-padding: 10px 20px; -fx-font-weight: bold; -fx-cursor: hand; " +
                    "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 5, 0, 0, 3);");

            dashboardBtn.setStyle("-fx-background-color: white; -fx-text-fill: #555555; -fx-font-size: 16px; " +
                    "-fx-background-radius: 15; -fx-padding: 10px 20px; -fx-font-weight: bold; -fx-cursor: hand; " +
                    "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 3, 0, 0, 1);");

            // Show profile content
            dashboard.setCenter(createProfileContent());
        });

        Button logoutBtn = createMenuButton("🚪 Logout", false);
        logoutBtn.setStyle("-fx-background-color: #FF6B6B; -fx-text-fill: white; -fx-font-size: 16px; " +
                "-fx-background-radius: 15; -fx-padding: 10px 20px; -fx-font-weight: bold; -fx-cursor: hand; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 5, 0, 0, 3);");

        // Add action to logout button with a logout message
        logoutBtn.setOnAction(e -> {
            // Show logout message
            showLogoutMessage();
        });

        // Add some space before logout button
        VBox spacer = new VBox();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        sidebar.getChildren().addAll(menuHeader, dashboardBtn, profileBtn, spacer, logoutBtn);

        return sidebar;
    }

    private void showLogoutMessage() {
        // Create a confirmation dialog
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout Confirmation");
        alert.setHeaderText("Are you sure you want to log out?");
        alert.setContentText("Click Yes to log out, or No to cancel.");

        // Add buttons to the alert
        ButtonType yesButton = new ButtonType("Yes");
        ButtonType noButton = new ButtonType("No");
        alert.getButtonTypes().setAll(yesButton, noButton);

        // Show the dialog and wait for the user's response
        alert.showAndWait().ifPresent(response -> {
            if (response == yesButton) {
                // Proceed with the logout process if the user clicks Yes
                performLogout();
            } else {
                // Do nothing (just close the dialog) if the user clicks No
                System.out.println("Logout canceled.");
            }
        });
    }

    private void performLogout() {
        // Create a temporary pane with logout message
        StackPane logoutPane = new StackPane();
        logoutPane.setStyle("-fx-background-color: linear-gradient(to bottom right, #D4E4FF , #73A8E5);");

        VBox messageBox = new VBox(20);
        messageBox.setAlignment(Pos.CENTER);
        messageBox.setPadding(new Insets(30));
        messageBox.setMaxWidth(500);
        messageBox.setMaxHeight(300);
        messageBox.setStyle("-fx-background-color: rgba(255,255,255,0.9); -fx-background-radius: 20; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 5);");

        Text logoutText = new Text("Logging out... 👋");
        logoutText.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 32));
        logoutText.setFill(Color.web("#FF1493"));

        Text thankYouText = new Text("Thank you for using ThinkyMath!\nSee you next time!");
        thankYouText.setFont(Font.font("Comic Sans MS", 20));
        thankYouText.setFill(Color.DARKSLATEGRAY);
        thankYouText.setTextAlignment(TextAlignment.CENTER);

        messageBox.getChildren().addAll(logoutText, thankYouText);
        logoutPane.getChildren().add(messageBox);

        // Display the logout message
        home.rootLayout.setCenter(logoutPane);

        // Use a thread to delay going back to home screen
        new Thread(() -> {
            try {
                Thread.sleep(2000); // 2 seconds delay
                javafx.application.Platform.runLater(() -> home.showHomeScreen());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private Button createMenuButton(String text, boolean selected) {
        Button button = new Button(text);
        button.setPrefWidth(210);
        button.setAlignment(Pos.CENTER_LEFT);

        if (selected) {
            button.setStyle("-fx-background-color: #FF69B4; -fx-text-fill: white; -fx-font-size: 16px; " +
                    "-fx-background-radius: 15; -fx-padding: 10px 20px; -fx-font-weight: bold; -fx-cursor: hand; " +
                    "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 5, 0, 0, 3);");
        } else {
            button.setStyle("-fx-background-color: white; -fx-text-fill: #555555; -fx-font-size: 16px; " +
                    "-fx-background-radius: 15; -fx-padding: 10px 20px; -fx-font-weight: bold; -fx-cursor: hand; " +
                    "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 3, 0, 0, 1);");
        }

        // Add hover effects
        button.setOnMouseEntered(e -> {
            if (!selected) {
                button.setStyle("-fx-background-color: #FFE6F2; -fx-text-fill: #FF1493; -fx-font-size: 16px; " +
                        "-fx-background-radius: 15; -fx-padding: 10px 20px; -fx-font-weight: bold; -fx-cursor: hand; " +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 4, 0, 0, 2);");
            }
        });

        button.setOnMouseExited(e -> {
            if (!selected) {
                button.setStyle("-fx-background-color: white; -fx-text-fill: #555555; -fx-font-size: 16px; " +
                        "-fx-background-radius: 15; -fx-padding: 10px 20px; -fx-font-weight: bold; -fx-cursor: hand; " +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 3, 0, 0, 1);");
            }
        });

        return button;
    }

    private VBox createMainContent() {
        VBox mainContent = new VBox(20);
        mainContent.setPadding(new Insets(20));
        mainContent.setStyle("-fx-background-color: linear-gradient(to bottom right, #D4E4FF , #73A8E5);");

        // Welcome section
        StackPane welcomePane = new StackPane();
        welcomePane.setPadding(new Insets(30));
        welcomePane.setStyle("-fx-background-color: rgba(255,255,255,0.8); -fx-background-radius: 20; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 10, 0, 0, 5);");

        VBox welcomeContent = new VBox(15);
        welcomeContent.setAlignment(Pos.CENTER);

        Text welcomeText = new Text("Welcome to Your Teacher Dashboard! 🎓");
        welcomeText.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 28));
        welcomeText.setFill(Color.web("#FF1493"));

        Text subtitleText = new Text("Track your students' progress and help them excel in mathematics");
        subtitleText.setFont(Font.font("Comic Sans MS", 16));
        subtitleText.setFill(Color.DARKSLATEGRAY);
        subtitleText.setTextAlignment(TextAlignment.CENTER);

        welcomeContent.getChildren().addAll(welcomeText, subtitleText);
        welcomePane.getChildren().add(welcomeContent);

        // Student performance section
        StackPane performancePane = new StackPane();
        performancePane.setPadding(new Insets(20));
        performancePane.setStyle("-fx-background-color: rgba(255,255,255,0.8); -fx-background-radius: 20; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 10, 0, 0, 5);");

        VBox performanceContent = new VBox(15);

        HBox titleAndButtonBox = new HBox(15);
        titleAndButtonBox.setAlignment(Pos.CENTER_LEFT);

        Text performanceTitle = new Text("Student Performance Overview 📊");
        performanceTitle.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 22));
        performanceTitle.setFill(Color.web("#FF1493"));

        // Add PDF export button
        Button exportPdfButton = new Button("📄 Export to PDF");
        exportPdfButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px; " +
                "-fx-background-radius: 15; -fx-padding: 8px 15px; -fx-font-weight: bold; -fx-cursor: hand; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 3, 0, 0, 2);");

        // Add hover effects for export button
        exportPdfButton.setOnMouseEntered(e ->
                exportPdfButton.setStyle("-fx-background-color: #45a049; -fx-text-fill: white; -fx-font-size: 14px; " +
                        "-fx-background-radius: 15; -fx-padding: 8px 15px; -fx-font-weight: bold; -fx-cursor: hand; " +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.4), 4, 0, 0, 3);")
        );

        exportPdfButton.setOnMouseExited(e ->
                exportPdfButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px; " +
                        "-fx-background-radius: 15; -fx-padding: 8px 15px; -fx-font-weight: bold; -fx-cursor: hand; " +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 3, 0, 0, 2);")
        );

        // Add spacer to push the button to the right
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        titleAndButtonBox.getChildren().addAll(performanceTitle, spacer, exportPdfButton);

        // Create table view for student performance
        TableView<StudentPerformance> performanceTable = createPerformanceTable();
        VBox.setVgrow(performanceTable, Priority.ALWAYS);

        // Add action to export PDF button
        exportPdfButton.setOnAction(e -> exportToPdf(performanceTable));

        performanceContent.getChildren().addAll(titleAndButtonBox, performanceTable);
        performancePane.getChildren().add(performanceContent);

        // Make the performance pane take up remaining space
        VBox.setVgrow(performancePane, Priority.ALWAYS);

        mainContent.getChildren().addAll(welcomePane, performancePane);

        return mainContent;
    }

    // Method to export table data to PDF
    private void exportToPdf(TableView<StudentPerformance> table) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save PDF File");

        // Set default file name with timestamp
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        fileChooser.setInitialFileName("StudentProgress_" + timestamp + ".pdf");

        // Set file extension filter
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showSaveDialog(dashboard.getScene().getWindow());

        if (file != null) {
            try {
                // Create PDF document
                Document document = new Document(PageSize.A4.rotate());
                PdfWriter.getInstance(document, new FileOutputStream(file));
                document.open();

                // Add title
                com.itextpdf.text.Font titleFont = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA, 18, com.itextpdf.text.Font.BOLD, new BaseColor(255, 105, 180));
                Paragraph title = new Paragraph("ThinkyMath - Student Progress Report", titleFont);
                title.setAlignment(Element.ALIGN_CENTER);
                document.add(title);

                // Add date and teacher info
                com.itextpdf.text.Font infoFont = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA, 12, com.itextpdf.text.Font.NORMAL);
                Paragraph info = new Paragraph(
                        "Generated by: " + teacherName + "\n" +
                                "Date: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                        infoFont);
                info.setAlignment(Element.ALIGN_RIGHT);
                document.add(info);

                document.add(new Paragraph(" ")); // Empty space

                // Create table
                PdfPTable pdfTable = new PdfPTable(6); // 6 columns
                pdfTable.setWidthPercentage(100);

                // Set table header
                com.itextpdf.text.Font headerFont = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA, 12, com.itextpdf.text.Font.BOLD, BaseColor.WHITE);
                BaseColor headerColor = new BaseColor(255, 20, 147); // Hot pink

                // Add table headers
                for (TableColumn<StudentPerformance, ?> column : table.getColumns()) {
                    PdfPCell cell = new PdfPCell(new Phrase(column.getText(), headerFont));
                    cell.setBackgroundColor(headerColor);
                    cell.setPadding(5);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    pdfTable.addCell(cell);
                }

                // Add table data
                com.itextpdf.text.Font cellFont = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA, 10);
                for (StudentPerformance performance : table.getItems()) {
                    pdfTable.addCell(new Phrase(performance.getTestId(), cellFont));
                    pdfTable.addCell(new Phrase(performance.getStudentId(), cellFont));
                    pdfTable.addCell(new Phrase(performance.getTestName(), cellFont));
                    pdfTable.addCell(new Phrase(performance.getStudentName(), cellFont));
                    pdfTable.addCell(new Phrase(performance.getScore(), cellFont));
                    pdfTable.addCell(new Phrase(performance.getCompletionDate(), cellFont));
                }

                document.add(pdfTable);

                // Add footer
                document.add(new Paragraph(" "));
                Paragraph footer = new Paragraph("This is an automatically generated report from ThinkyMath Teacher Dashboard.",
                        new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA, 8, com.itextpdf.text.Font.ITALIC));
                footer.setAlignment(Element.ALIGN_CENTER);
                document.add(footer);

                document.close();

                // Show success message
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Export Successful");
                alert.setHeaderText("PDF Created Successfully");
                alert.setContentText("The student progress report has been saved to:\n" + file.getAbsolutePath());
                alert.showAndWait();

            } catch (Exception ex) {
                // Show error message
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Export Error");
                alert.setHeaderText("Failed to generate PDF");
                alert.setContentText("Error: " + ex.getMessage());
                alert.showAndWait();
                ex.printStackTrace();
            }
        }
    }

    private VBox createProfileContent() {
        VBox profileContent = new VBox(20);
        profileContent.setPadding(new Insets(20));
        profileContent.setStyle("-fx-background-color: linear-gradient(to bottom right, #D4E4FF , #73A8E5);");

        // Profile header section
        StackPane profileHeaderPane = new StackPane();
        profileHeaderPane.setPadding(new Insets(15));
        profileHeaderPane.setStyle("-fx-background-color: rgba(255,255,255,0.8); -fx-background-radius: 10; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 5, 0, 0, 2);");

        VBox profileHeaderContent = new VBox(10);
        profileHeaderContent.setAlignment(Pos.CENTER);

        Text profileTitle = new Text("Teacher Profile 👩‍🏫");
        profileTitle.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 20));
        profileTitle.setFill(Color.web("#FF1493"));

        // Create a circular avatar placeholder
        StackPane avatarContainer = new StackPane();
        avatarContainer.setPrefSize(50, 50);
        avatarContainer.setStyle("-fx-background-color: #FFE6F2; -fx-background-radius: 25; -fx-border-radius: 25; -fx-border-width: 2; -fx-border-color: #FF69B4;");

        Text avatarText = new Text(teacherName.substring(0, 1));
        avatarText.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 30));
        avatarText.setFill(Color.web("#FF1493"));
        avatarContainer.getChildren().add(avatarText);

        profileHeaderContent.getChildren().addAll(profileTitle, avatarContainer);
        profileHeaderPane.getChildren().add(profileHeaderContent);

        // Profile details section
        StackPane profileDetailsPane = new StackPane();
        profileDetailsPane.setPadding(new Insets(15));
        profileDetailsPane.setStyle("-fx-background-color: rgba(255,255,255,0.8); -fx-background-radius: 10; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 5, 0, 0, 2);");

        VBox profileDetailsContent = new VBox(10);

        HBox nameBox = createProfileField("Name", teacherName);
        HBox roleBox = createProfileField("Role", "Lead Math Teacher & System Administrator");
        HBox expBox = createProfileField("Experience", "2+ years in primary education");
        HBox specBox = createProfileField("Specialization", "Early Mathematics, Creative Learning Techniques");

        VBox contactBox = new VBox(8);
        Text contactLabel = new Text("Contact Information");
        contactLabel.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 16));
        contactLabel.setFill(Color.web("#FF1493"));

        VBox contactDetails = new VBox(8);
        contactDetails.setPadding(new Insets(5));
        contactDetails.setStyle("-fx-background-color: rgba(255,255,255,0.5); -fx-border-color: #FFB6C1; -fx-border-radius: 5;");

        Text emailText = new Text("📧 Email: " + teacherName.toLowerCase().replace(" ", ".") + "@thinkymath.edu");
        emailText.setFont(Font.font("Comic Sans MS", 14));

        Text phoneText = new Text("📱 Phone: (+977) 9848857475 ");
        phoneText.setFont(Font.font("Comic Sans MS", 14));

        contactDetails.getChildren().addAll(emailText, phoneText);
        contactBox.getChildren().addAll(contactLabel, contactDetails);

        Button editProfileBtn = new Button("Edit Profile");
        editProfileBtn.setStyle("-fx-background-color: #FF69B4; -fx-text-fill: white; -fx-font-size: 14px; " +
                "-fx-background-radius: 10; -fx-padding: 5px 15px; -fx-font-weight: bold; -fx-cursor: hand; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 5, 0, 0, 2);");

        profileDetailsContent.getChildren().addAll(nameBox, roleBox, expBox, specBox, contactBox, editProfileBtn);
        profileDetailsPane.getChildren().add(profileDetailsContent);

        profileContent.getChildren().addAll(profileHeaderPane, profileDetailsPane);

        return profileContent;
    }

    private HBox createProfileField(String label, String value) {
        HBox fieldBox = new HBox(10);
        fieldBox.setAlignment(Pos.CENTER_LEFT);

        Text labelText = new Text(label + ":");
        labelText.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 14));
        labelText.setFill(Color.web("#FF1493"));

        Text valueText = new Text(value);
        valueText.setFont(Font.font("Comic Sans MS", 14));

        fieldBox.getChildren().addAll(labelText, valueText);
        return fieldBox;
    }

    private TableView<StudentPerformance> createPerformanceTable() {
        TableView<StudentPerformance> table = new TableView<>();

        // Define columns
        TableColumn<StudentPerformance, String> testIdCol = new TableColumn<>("Test ID");
        testIdCol.setCellValueFactory(new PropertyValueFactory<>("testId"));

        TableColumn<StudentPerformance, String> studentIdCol = new TableColumn<>("Student ID");
        studentIdCol.setCellValueFactory(new PropertyValueFactory<>("studentId"));

        TableColumn<StudentPerformance, String> testNameCol = new TableColumn<>("Test Name");
        testNameCol.setCellValueFactory(new PropertyValueFactory<>("testName"));

        TableColumn<StudentPerformance, String> studentNameCol = new TableColumn<>("Student Name");
        studentNameCol.setCellValueFactory(new PropertyValueFactory<>("studentName"));

        TableColumn<StudentPerformance, String> scoreCol = new TableColumn<>("Score");
        scoreCol.setCellValueFactory(new PropertyValueFactory<>("score"));

        TableColumn<StudentPerformance, String> completionDateCol = new TableColumn<>("Completion Date");
        completionDateCol.setCellValueFactory(new PropertyValueFactory<>("completionDate"));

        // Add columns to the table
        table.getColumns().addAll(testIdCol, studentIdCol, testNameCol, studentNameCol, scoreCol, completionDateCol);
        // Fetch data from TestService
        ObservableList<StudentPerformance> data = FXCollections.observableArrayList();
        for (TestService.StudentPerformanceEntry entry : TestService.getAllTestRecords()) {
            data.add(new StudentPerformance(
                    entry.getTestId(),
                    entry.getStudentId(),
                    entry.getTestName(),
                    entry.getStudentName(),
                    entry.getScore(),
                    entry.getCompletionDate()
            ));
        }

        table.setItems(data); // set the items
        return table;
    }
}