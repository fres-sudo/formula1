package it.unicam.cs.oop.formulauno.view.drawer;

import it.unicam.cs.oop.formulauno.model.point.Point;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 * Class to perform the drawing in the UI of the Lines
 */
public class LineDrawer implements Drawer<Point[]> {

    private final Color color;

    public LineDrawer(Color color) {
        this.color = color;
    }

    /**
     * Methods that draws in the UI the line, taking starting and ending Points.
     *
     * @param points The two points where the line starts and finish
     * @param pane   the pane where the user want to draw
     */
    @Override
    public void draw(Point[] points, Pane pane) {
        Point startPoint = points[0];
        Point endPoint = points[1];
        Line line = new Line(startPoint.x(), startPoint.y(), endPoint.x(), endPoint.y());
        line.setStroke(color);
        pane.getChildren().add(line);
    }
}
