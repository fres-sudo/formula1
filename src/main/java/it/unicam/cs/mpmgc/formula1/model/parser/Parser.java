package it.unicam.cs.mpmgc.formula1.model.parser;

import java.io.IOException;

/**
 * Describe the behaviour of a parser
 *
 * @param <T> Object that the user need to parse
 */
public interface Parser<T> {

    /**
     * Method used to parse a file, usually a Json, to a Java Object
     *
     * @param s the path of the file
     * @return The object parsed
     * @throws IOException if the source of the file is not correct
     */
    T parse(String s) throws IOException;
}
