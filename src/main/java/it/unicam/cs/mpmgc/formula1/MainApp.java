package it.unicam.cs.mpmgc.formula1;

import it.unicam.cs.mpmgc.formula1.controller.GameController;
import it.unicam.cs.mpmgc.formula1.model.*;
import it.unicam.cs.mpmgc.formula1.service.JSONTrackLoader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class MainApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);

        GameController controller = fxmlLoader.getController();

        // Add the input event handler to the scene
        scene.addEventHandler(KeyEvent.KEY_PRESSED, controller::handleKeyEvent);

        stage.setTitle("Formula 1");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        // Load the track from the JSON file
        JSONTrackLoader trackLoader = new JSONTrackLoader();
        Track track;
        try {
            track = trackLoader.loadTrack("src/main/resources/track.json");
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        // Print the loaded track points
        List<Point> points = track.getPoints();
        for (Point point : points) {
            System.out.println("Point: " + "[ " + point.getX() + " " + point.getY() + " ]");
        }
        launch();
    }
}
