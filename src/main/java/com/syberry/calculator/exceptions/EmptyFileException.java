package com.syberry.calculator.exceptions;

/**
 * An exception can be thrown if file is empty.
 */
public class EmptyFileException extends RuntimeException {

    /**
     * {@link RuntimeException#RuntimeException(String)}.
     */
    public EmptyFileException(String message) {
        super(message);
    }
}
