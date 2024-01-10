package com.syberry.calculator.validators;

import com.syberry.calculator.dto.Inventory;

/**
 * Validator for {@link Inventory}.
 */
public interface InventoryValidator {

    /**
     * Validates if inventory contains correct values.
     */
    void validateInventory(Inventory inventory);
}
