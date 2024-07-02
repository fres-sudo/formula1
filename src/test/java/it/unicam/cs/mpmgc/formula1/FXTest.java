package it.unicam.cs.mpmgc.formula1;

import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;

/**
 * Helper class to startup JavaFx Thread to allow the user to test Classes with JavaFX element inside.
 */
public abstract class FXTest {
    @BeforeAll
    static void initJfxRuntime() {
        Platform.startup(() -> {});
    }
}
