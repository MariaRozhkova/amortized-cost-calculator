package com.syberry.calculator.writers;

import com.syberry.calculator.dto.AmortizedInventoryContext;
import com.syberry.calculator.dto.CsvFileOptions;

/**
 * Writer for amortized inventories.
 */
public interface AmortizedInventoryWriter {

    /**
     * Writes amortized inventories.
     */
    void write(CsvFileOptions fileOptions, AmortizedInventoryContext context);
}
