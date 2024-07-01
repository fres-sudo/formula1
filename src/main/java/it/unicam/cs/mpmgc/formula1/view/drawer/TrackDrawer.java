package it.unicam.cs.mpmgc.formula1.view.drawer;

import it.unicam.cs.mpmgc.formula1.model.track.Track;
import it.unicam.cs.mpmgc.formula1.model.point.Point;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.List;

import static it.unicam.cs.mpmgc.formula1.model.track.Track.TRACK_FACTOR;

/**
 * Class to perform the drawing in the UI of the Track
 */
public class TrackDrawer implements Drawer<Track> {
    /**
     * Method to draw the track in the UI
     *
     * @param track track the user want to draw
     * @param pane  the pane where the user want to draw
     */
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
