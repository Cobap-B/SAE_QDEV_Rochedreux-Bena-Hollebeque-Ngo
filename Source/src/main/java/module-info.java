module MVC {
    requires javafx.controls;
    requires javafx.fxml;

    requires java.desktop;
    requires net.sourceforge.plantuml;
    exports Classes;
    exports MVC;
    opens MVC to javafx.fxml;
}