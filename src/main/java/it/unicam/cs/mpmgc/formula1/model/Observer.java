package it.unicam.cs.mpmgc.formula1.model;

public interface Observer<T> {

    void update(T state);

}
