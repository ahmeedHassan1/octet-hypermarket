module com.mycompany.hypermarket {
    requires javafx.controls;
    requires javafx.fxml;

    requires transitive javafx.graphics;

    opens com.mycompany.hypermarket to javafx.fxml;

    exports com.mycompany.hypermarket;
}
