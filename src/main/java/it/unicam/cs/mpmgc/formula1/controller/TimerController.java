package it.unicam.cs.mpmgc.formula1.controller;

import javafx.application.Platform;
import javafx.scene.control.Label;

/**
 * Class responsible to manage the Timer of the UI
 */
public class TimerController {

    /**
     * Current time elapsed.
     */
    private int seconds = 0;
    private final Label timerLabel;

    /**
     * Method Constructor to define the UI label.
     *
     * @param timerLabel the UI label used for displaying the current time elapsed
     */
    public TimerController(Label timerLabel) {
        this.timerLabel = timerLabel;
    }

    /**
     * Method to increment the timer.
     */
    public void incrementTime() {
        seconds++;
        updateLabel();
    }

    /**
     * Method to reset the timer
     */
    public void resetTime() {
        seconds = 0;
        updateLabel();
    }

    public int getSeconds() {
        return this.seconds;
    }

    private void updateLabel() {
        Platform.runLater(() -> timerLabel.setText(seconds + " seconds 🕒"));
    }

}
