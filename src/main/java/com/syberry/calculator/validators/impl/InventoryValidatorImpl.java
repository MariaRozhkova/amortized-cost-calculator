package com.syberry.calculator.validators.impl;

import com.syberry.calculator.dto.Inventory;
import com.syberry.calculator.exceptions.IncorrectInventoryException;
import com.syberry.calculator.validators.InventoryValidator;

/**
 * {@inheritDoc}
 */
public class InventoryValidatorImpl implements InventoryValidator {

    @Override
    public void validateInventory(Inventory inventory) {
        validateIfPositive(
            inventory.getServicePeriod(),
            Inventory.Fields.servicePeriod
        );
        validateIfPositive(
            inventory.getPrice(),
            Inventory.Fields.price
        );
    }

    private void validateIfPositive(
        double field,
        String fieldName
    ) {
        if (field > 0) {
            return;
        }
        var message = String.format(
            "Value for %s field should be positive",
            fieldName
        );
        throw new IncorrectInventoryException(message);
    }
}
