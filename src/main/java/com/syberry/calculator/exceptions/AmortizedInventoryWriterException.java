package com.syberry.calculator.exceptions;

/**
 * An exception can be thrown while writing amortized inventories to file.
 */
public class AmortizedInventoryWriterException extends RuntimeException {

    /**
     * {@link RuntimeException#RuntimeException(String, Throwable)}.
     */
    public AmortizedInventoryWriterException(String message, Throwable cause) {
        super(message, cause);
    }
}
