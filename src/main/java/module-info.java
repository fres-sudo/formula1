module it.unicam.cs.mpmgc.formula1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires org.json;


    opens it.unicam.cs.mpmgc.formula1 to javafx.fxml;
    exports it.unicam.cs.mpmgc.formula1;
    exports it.unicam.cs.mpmgc.formula1.controller;
    opens it.unicam.cs.mpmgc.formula1.controller to javafx.fxml;
}