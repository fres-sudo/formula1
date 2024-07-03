package it.unicam.cs.oop.formulauno;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {

    public static final double GAME_HEIGHT = 800;
    public static final double GAME_WIDTH = 800;

    @Override
    public void start(Stage stage) throws IOException {
        final boolean resizable = stage.isResizable();
        //stage.setResizable(!resizable);
        stage.setWidth(840);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), GAME_HEIGHT, GAME_WIDTH);
        fxmlLoader.getController();

        stage.setTitle("Formula 1");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
