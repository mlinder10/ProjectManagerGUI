module com.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;
    requires json.simple;

    opens com.example to javafx.fxml;
    opens controllers to javafx.fxml;
    opens models to javafx.fxml;
    opens utils to javafx.fxml;
    exports com.example;
}
