module math_tutor.frontend {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;
    requires mysql.connector.j;
    requires itextpdf;
    requires javafx.graphics;


    opens math_tutor.frontend to javafx.fxml;
    exports math_tutor.frontend;
}