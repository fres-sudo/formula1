package it.unicam.cs.mpmgc.formula1.model.parser;

import java.io.IOException;

public interface Parser<T> {
    T parse(String s) throws IOException;
}
