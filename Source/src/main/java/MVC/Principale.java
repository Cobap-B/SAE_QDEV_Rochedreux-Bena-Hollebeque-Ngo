package MVC;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Principale extends Application {
    @Override
    public void start(Stage stage){
        BorderPane bp= new BorderPane();
        Scene scene = new Scene(bp,935,670);
        stage.setTitle("MVC");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}