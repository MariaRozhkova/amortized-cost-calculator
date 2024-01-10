package com.syberry.calculator.exceptions;

/**
 * An exception can be thrown while parsing command line arguments.
 */
public class InputArgsParseException extends RuntimeException {

    /**
     * {@link RuntimeException#RuntimeException(String, Throwable)}.
     */
    public InputArgsParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
