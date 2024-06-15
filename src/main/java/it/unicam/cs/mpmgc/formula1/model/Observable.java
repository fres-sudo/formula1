package it.unicam.cs.mpmgc.formula1.model;

public interface Observable<T> {

    void addObserver(Observer<T> observer);

    void removeObserver(Observer<T> observer);

    void notifyObservers(T state);
}
