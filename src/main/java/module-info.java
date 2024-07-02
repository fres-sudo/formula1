module it.unicam.cs.mpmgc.formulauno {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires org.json;
    requires jdk.jdi;


    opens it.unicam.cs.oop.formulauno to javafx.fxml;
    exports it.unicam.cs.oop.formulauno;
    exports it.unicam.cs.oop.formulauno.controller;
    opens it.unicam.cs.oop.formulauno.controller to javafx.fxml;
}