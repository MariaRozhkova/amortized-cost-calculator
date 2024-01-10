package com.syberry.calculator.factories;

import com.syberry.calculator.dto.AmortizedInventoryContext;
import com.syberry.calculator.dto.Row;
import java.util.List;

/**
 * Factory produces all rows to be written to result file.
 */
public interface RowFactory {

    /**
     * Generates all rows to be written to result file.
     */
    List<Row> generateRows(AmortizedInventoryContext context);
}
