module MVC {
    requires javafx.controls;
    requires javafx.fxml;

    requires java.desktop;
    exports Classes;
    exports MVC;
    opens MVC to javafx.fxml;
}