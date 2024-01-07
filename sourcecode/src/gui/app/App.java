package gui.app;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application {
	@Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/gui/view/gui.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Image icon = new Image(getClass().getResourceAsStream("/gui/view/main.png"));
        stage.getIcons().add(icon);
        stage.setTitle("Genetic Algorithm Visualizer");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
