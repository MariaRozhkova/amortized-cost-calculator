package com.syberry.calculator.exceptions;

/**
 * An exception can be thrown while reading inventories from source.
 */
public class InventoryReaderException extends RuntimeException {

    /**
     * {@link RuntimeException#RuntimeException(String)}.
     */
    public InventoryReaderException(String message, Throwable cause) {
        super(message, cause);
    }
}
