package it.unicam.cs.mpmgc.formula1.view.drawer;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class GridDrawer implements Drawer<Canvas> {
    @Override
    public void draw(Canvas canvas, Pane pane) {
        GraphicsContext context = canvas.getGraphicsContext2D();
        int cellSize = 10;
        context.setStroke(Color.LIGHTGREY.deriveColor(0, 1.0, 1.0, 0.15));

        for (int i = 0; i < canvas.getWidth(); i += cellSize) {
            context.strokeLine(i, 0, i, canvas.getHeight());
        }

        for (int i = 0; i < canvas.getHeight(); i += cellSize) {
            context.strokeLine(0, i, canvas.getWidth(), i);
        }

        pane.getChildren().add(canvas);
    }
}
