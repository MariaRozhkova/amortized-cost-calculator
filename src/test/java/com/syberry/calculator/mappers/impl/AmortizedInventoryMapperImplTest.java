package com.syberry.calculator.mappers.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.syberry.calculator.dto.AmortizedInventory;
import com.syberry.calculator.dto.AmortizedInventoryContext;
import com.syberry.calculator.dto.Inventory;
import com.syberry.calculator.validators.InventoryValidator;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith({MockitoExtension.class})
class AmortizedInventoryMapperImplTest {

    private static final String FIRST_INVENTORY_NAME = "firstInventoryName";
    private static final LocalDate FIRST_PURCHASE_DATE = LocalDate.of(2023, 1, 15);
    private static final int FIRST_SERVICE_PERIOD_IN_MONTHS = 12;
    private static final double FIRST_PRICE = 1200.00;
    private static final String SECOND_INVENTORY_NAME = "secondInventoryName";
    private static final LocalDate SECOND_PURCHASE_DATE = LocalDate.of(2023, 4, 10);
    private static final int SECOND_SERVICE_PERIOD_IN_MONTHS = 24;
    private static final double SECOND_PRICE = 3400.00;
    private static final LocalDate MIN_AMORTIZATION_START_DATE = LocalDate.of(2023, 2, 1);
    private static final LocalDate MAX_AMORTIZATION_START_DATE = LocalDate.of(2025, 5, 1);
    private static final LocalDate FIRST_AMORTIZATION_START_DATE = LocalDate.of(2023, 2, 1);
    private static final LocalDate FIRST_AMORTIZATION_END_DATE = LocalDate.of(2024, 2, 1);
    private static final double FIRST_AMORTIZATION = 100.00;
    private static final LocalDate SECOND_AMORTIZATION_START_DATE = LocalDate.of(2023, 5, 1);
    private static final LocalDate SECOND_AMORTIZATION_END_DATE = LocalDate.of(2025, 5, 1);
    private static final double SECOND_AMORTIZATION = SECOND_PRICE / SECOND_SERVICE_PERIOD_IN_MONTHS;

    @Mock
    private InventoryValidator inventoryValidator;

    @InjectMocks
    private AmortizedInventoryMapperImpl amortizedInventoryMapper;

    @Test
    void shouldMapToAmortizedInventories() {
        var amortizedInventories = prepareInventories();
        var actualResult = amortizedInventoryMapper
            .mapToAmortizedInventories(amortizedInventories);

        var expectedResult = prepareExpectedAmortizedInventoryContext();
        assertEquals(expectedResult, actualResult);
    }

    private List<Inventory> prepareInventories() {
        var firstInventory = new Inventory();
        firstInventory.setName(FIRST_INVENTORY_NAME);
        firstInventory.setPurchaseDate(FIRST_PURCHASE_DATE);
        firstInventory.setServicePeriod(FIRST_SERVICE_PERIOD_IN_MONTHS);
        firstInventory.setPrice(FIRST_PRICE);

        var secondInventory = new Inventory();
        secondInventory.setName(SECOND_INVENTORY_NAME);
        secondInventory.setPurchaseDate(SECOND_PURCHASE_DATE);
        secondInventory.setServicePeriod(SECOND_SERVICE_PERIOD_IN_MONTHS);
        secondInventory.setPrice(SECOND_PRICE);

        return List.of(firstInventory, secondInventory);
    }

    private AmortizedInventoryContext prepareExpectedAmortizedInventoryContext() {
        return AmortizedInventoryContext.builder()
            .amortizedInventories(prepareExpectedAmortizedInventories())
            .minAmortizationStartDate(MIN_AMORTIZATION_START_DATE)
            .maxAmortizationEndDate(MAX_AMORTIZATION_START_DATE)
            .build();
    }

    private List<AmortizedInventory> prepareExpectedAmortizedInventories() {
        var firstAmortizedInventory = AmortizedInventory.builder()
            .inventoryName(FIRST_INVENTORY_NAME)
            .amortizationStartDate(FIRST_AMORTIZATION_START_DATE)
            .amortizationEndDate(FIRST_AMORTIZATION_END_DATE)
            .initialCost(FIRST_PRICE)
            .amortization(FIRST_AMORTIZATION)
            .build();

        var secondAmortizedInventory = AmortizedInventory.builder()
            .inventoryName(SECOND_INVENTORY_NAME)
            .amortizationStartDate(SECOND_AMORTIZATION_START_DATE)
            .amortizationEndDate(SECOND_AMORTIZATION_END_DATE)
            .initialCost(SECOND_PRICE)
            .amortization(SECOND_AMORTIZATION)
            .build();

        return List.of(firstAmortizedInventory, secondAmortizedInventory);
    }
}