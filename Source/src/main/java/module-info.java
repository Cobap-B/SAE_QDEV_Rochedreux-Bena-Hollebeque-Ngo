module MVC {
    requires javafx.controls;
    requires javafx.fxml;

    requires net.sourceforge.plantuml;
    requires javafx.swing;
    exports Classes;
    exports MVC;
    opens MVC to javafx.fxml;
}