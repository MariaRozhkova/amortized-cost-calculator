package com.syberry.calculator.mappers;

import com.syberry.calculator.dto.AmortizedInventoryContext;
import com.syberry.calculator.dto.Inventory;
import java.util.List;

/**
 * Mapper for converting inventories to amortized inventories.
 */
public interface AmortizedInventoryMapper {

    /**
     * Maps inventories to amortized inventories.
     */
    AmortizedInventoryContext mapToAmortizedInventories(List<Inventory> inventories);
}
