package it.unicam.cs.mpmgc.formula1.view.drawer;

import it.unicam.cs.mpmgc.formula1.model.track.Track;
import it.unicam.cs.mpmgc.formula1.model.point.Point;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.List;

public class TrackDrawer implements Drawer<Track> {
    private static final int TRACK_FACTOR = 2;

    @Override
    public void draw(Track track, Pane pane) {
        List<Point> points = track.getPoints();

        for (int i = 0; i < points.size() - 1; i++) {
            Point p1 = points.get(i);
            Point p2 = points.get(i + 1);
            Line trackLine = new Line(
                    p1.x() * TRACK_FACTOR,
                    p1.y() * TRACK_FACTOR,
                    p2.x() * TRACK_FACTOR,
                    p2.y() * TRACK_FACTOR);
            trackLine.setStroke(Color.BLACK);
            trackLine.setStrokeWidth(2);
            pane.getChildren().add(trackLine);
        }
    }
}