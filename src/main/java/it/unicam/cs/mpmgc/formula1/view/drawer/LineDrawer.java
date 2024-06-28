package it.unicam.cs.mpmgc.formula1.view.drawer;

import it.unicam.cs.mpmgc.formula1.model.point.Point;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class LineDrawer implements Drawer<Point[]> {
    private final Color color;

    public LineDrawer(Color color) {
        this.color = color;
    }

    @Override
    public void draw(Point[] points, Pane pane) {
        Point startPoint = points[0];
        Point endPoint = points[1];
        Line line = new Line(startPoint.x(), startPoint.y(), endPoint.x(), endPoint.y());
        line.setStroke(color);
        pane.getChildren().add(line);
    }
}
