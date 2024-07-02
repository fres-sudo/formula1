package it.unicam.cs.mpmgc.formula1.controller;

/**
 * Enum to define all the possible states of a Game
 */
enum GameState {
    INIT {
        @Override
        String getLabel() {
            return "Race Started! Turn on the engine 🚦🔫";
        }
    },
    PROGRESS {
        @Override
        String getLabel() {
            return "Race in progress, FULL GAS 🏎️💨💥";
        }
    },
    LOST {
        @Override
        String getLabel() {
            return "Race Ended, You Lost! 😿";
        }
    },
    WIN {
        @Override
        String getLabel() {
            return "Race Ended, You Won! 🏁";
        }
    },
    CRASH {
        @Override
        String getLabel() {
            return "Race Ended, You Crashed! 💥";
        }
    },
    RESET {
        @Override
        String getLabel() {
            return "Race Ended, You Reset! 🔄️";
        }
    };

    abstract String getLabel();
}
