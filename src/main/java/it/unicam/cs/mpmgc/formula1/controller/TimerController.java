package it.unicam.cs.mpmgc.formula1.controller;

import javafx.application.Platform;
import javafx.scene.control.Label;

public class TimerController {

    private int seconds = 0;
    private final Label timerLabel;

    public TimerController(Label timerLabel) {
        this.timerLabel = timerLabel;
    }

    public void incrementTime() {
        seconds++;
        updateLabel();
    }

    public void resetTime() {
        seconds = 0;
        updateLabel();
    }

    private void updateLabel() {
        Platform.runLater(() -> timerLabel.setText(seconds + " seconds 🕒"));
    }

    public String getLabelText() {
        return timerLabel.getText();
    }
}
