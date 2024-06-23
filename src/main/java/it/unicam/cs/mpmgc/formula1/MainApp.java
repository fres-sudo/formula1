package it.unicam.cs.mpmgc.formula1;

import it.unicam.cs.mpmgc.formula1.controller.GameController;
import it.unicam.cs.mpmgc.formula1.model.*;
import it.unicam.cs.mpmgc.formula1.model.mapper.PointMapper;
import it.unicam.cs.mpmgc.formula1.model.parser.TrackParser;
import it.unicam.cs.mpmgc.formula1.model.point.Point;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 800);

        fxmlLoader.getController();

        stage.setTitle("Formula 1");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
