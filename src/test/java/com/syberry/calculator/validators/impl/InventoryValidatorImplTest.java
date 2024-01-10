package com.syberry.calculator.validators.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.syberry.calculator.dto.Inventory;
import com.syberry.calculator.exceptions.IncorrectInventoryException;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class InventoryValidatorImplTest {

    private static final String INVENTORY_NAME = "firstInventoryName";
    private static final LocalDate PURCHASE_DATE = LocalDate.of(2023, 1, 15);
    private static final int SERVICE_PERIOD_IN_MONTHS = 12;
    private static final double PRICE = 1200.00;
    private static final double INCORRECT_PRICE = -10.00;
    private static final int INCORRECT_SERVICE_PERIOD_IN_MONTHS = -12;

    private final InventoryValidatorImpl inventoryValidator = new InventoryValidatorImpl();

    @Test
    void shouldValidateInventory() {
        var inventory = prepareInventory(SERVICE_PERIOD_IN_MONTHS, PRICE);

        assertDoesNotThrow(() -> inventoryValidator.validateInventory(inventory));
    }

    @Test
    void shouldThrowExceptionWhenPriceIsNegative() {
        var inventory = prepareInventory(SERVICE_PERIOD_IN_MONTHS, INCORRECT_PRICE);

        var actualResult = assertThrows(
            IncorrectInventoryException.class,
            () -> inventoryValidator.validateInventory(inventory)
        );

        var expectedMessage = String.format(
            "Value for %s field should be positive",
            Inventory.Fields.price
        );
        assertEquals(expectedMessage, actualResult.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenServicePeriodIsNegative() {
        var inventory = prepareInventory(INCORRECT_SERVICE_PERIOD_IN_MONTHS, PRICE);

        var actualResult = assertThrows(
            IncorrectInventoryException.class,
            () -> inventoryValidator.validateInventory(inventory)
        );

        var expectedMessage = String.format(
            "Value for %s field should be positive",
            Inventory.Fields.servicePeriod
        );
        assertEquals(expectedMessage, actualResult.getMessage());
    }

    private Inventory prepareInventory(
        int servicePeriod,
        double price
    ) {
        var inventory = new Inventory();
        inventory.setName(INVENTORY_NAME);
        inventory.setPurchaseDate(PURCHASE_DATE);
        inventory.setServicePeriod(servicePeriod);
        inventory.setPrice(price);

        return inventory;
    }
}
