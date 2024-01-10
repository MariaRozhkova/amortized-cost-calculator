package com.syberry.calculator.readers;

import com.syberry.calculator.dto.CsvFileOptions;
import com.syberry.calculator.dto.Inventory;
import java.util.List;

/**
 * Reader for inventories.
 */
public interface InventoryReader {

    /**
     * Reads inventories.
     */
    List<Inventory> read(CsvFileOptions inputArgs);
}
