module MVC {
    requires javafx.controls;
    requires javafx.fxml;

    requires java.desktop;

    exports MVC;
    opens MVC to javafx.fxml;
}