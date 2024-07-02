package it.unicam.cs.oop.formulauno.model.parser;

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
     * @param path the path of the file to parse
     * @return The object parsed
     * @throws IOException if the source of the file is not correct or something happens reading from it
     */
    T parse(String path) throws IOException;
}
