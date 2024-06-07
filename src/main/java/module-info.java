module it.unicam.cs.mpmgc.formula1.formula1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;

    opens it.unicam.cs.mpmgc.formula1.formula1 to javafx.fxml;
    exports it.unicam.cs.mpmgc.formula1.formula1;
}