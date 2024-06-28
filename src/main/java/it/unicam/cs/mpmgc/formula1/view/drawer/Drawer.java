package it.unicam.cs.mpmgc.formula1.view.drawer;

import javafx.scene.layout.Pane;

public interface Drawer<T> {
    void draw(T item, Pane pane);
}
