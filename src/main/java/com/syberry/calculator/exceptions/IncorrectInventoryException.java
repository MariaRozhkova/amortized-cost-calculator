package com.syberry.calculator.exceptions;

/**
 * An exception can be thrown if inventory contains incorrect data.
 */
public class IncorrectInventoryException extends RuntimeException {

    /**
     * {@link RuntimeException#RuntimeException(String)}.
     */
    public IncorrectInventoryException(String message) {
        super(message);
    }
}
