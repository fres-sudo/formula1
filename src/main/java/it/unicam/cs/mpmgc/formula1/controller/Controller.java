package it.unicam.cs.mpmgc.formula1.controller;

public interface Controller<T> {

    void start();

    void stop();

    void setModel(T model);

    T getModel();
}
