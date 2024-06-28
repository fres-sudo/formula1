package it.unicam.cs.mpmgc.formula1.view.drawer;

import javafx.scene.layout.Pane;

/**
 * An interface to describe the behaviour of a Class capable to Draw in the UI.
 *
 * @param <T> The object that the user want to draw
 */
public interface Drawer<T> {
    /**
     * Method to perform the draw actions.
     *
     * @param item item the user want to draw
     * @param pane the pane where the user want to draw
     */
    void draw(T item, Pane pane);
}
