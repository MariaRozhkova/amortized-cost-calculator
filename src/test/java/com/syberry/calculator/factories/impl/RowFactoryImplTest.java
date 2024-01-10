package com.syberry.calculator.factories.impl;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import com.syberry.calculator.dto.AmortizedInventory;
import com.syberry.calculator.dto.AmortizedInventoryContext;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;

class RowFactoryImplTest {

    private static final String FIRST_INVENTORY_NAME = "firstInventoryName";
    private static final double FIRST_PRICE = 200;
    private static final String SECOND_INVENTORY_NAME = "secondInventoryName";
    private static final int SECOND_SERVICE_PERIOD_IN_MONTHS = 3;
    private static final double SECOND_PRICE = 600;
    private static final LocalDate MIN_AMORTIZATION_START_DATE = LocalDate.of(2023, 2, 1);
    private static final LocalDate MAX_AMORTIZATION_START_DATE = LocalDate.of(2023, 6, 1);
    private static final LocalDate FIRST_AMORTIZATION_START_DATE = LocalDate.of(2023, 2, 1);
    private static final LocalDate FIRST_AMORTIZATION_END_DATE = LocalDate.of(2023, 4, 1);
    private static final double FIRST_AMORTIZATION = 100.00;
    private static final LocalDate SECOND_AMORTIZATION_START_DATE = LocalDate.of(2023, 3, 1);
    private static final LocalDate SECOND_AMORTIZATION_END_DATE = LocalDate.of(2023, 6, 1);
    private static final double SECOND_AMORTIZATION = SECOND_PRICE / SECOND_SERVICE_PERIOD_IN_MONTHS;

    private final RowFactoryImpl rowFactory = new RowFactoryImpl();

    @Test
    void shouldGenerateRows() {
        var amortizedInventoryContext = prepareAmortizedInventoryContext();

        var actualResult = rowFactory.generateRows(amortizedInventoryContext);

        assertArrayEquals(prepareExpectedHeaderRowFields(), actualResult.get(0).fields());
        assertArrayEquals(
            prepareExpectedFirstAmortizedInventoryRows(),
            actualResult.get(1).fields()
        );
        assertArrayEquals(
            prepareExpectedSecondAmortizedInventoryRows(),
            actualResult.get(2).fields()
        );
        assertArrayEquals(prepareExpectedTotalCostRow(), actualResult.get(3).fields());
    }

    private AmortizedInventoryContext prepareAmortizedInventoryContext() {
        return AmortizedInventoryContext.builder()
            .amortizedInventories(prepareAmortizedInventories())
            .minAmortizationStartDate(MIN_AMORTIZATION_START_DATE)
            .maxAmortizationEndDate(MAX_AMORTIZATION_START_DATE)
            .build();
    }

    private List<AmortizedInventory> prepareAmortizedInventories() {
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

    private String[] prepareExpectedHeaderRowFields() {
        return new String[]{"Inventory name", "Feb-2023", "Mar-2023", "Apr-2023", "May-2023"};
    }

    private String[] prepareExpectedFirstAmortizedInventoryRows() {
        return new String[]{FIRST_INVENTORY_NAME, "200.00", "100.00", null, null};
    }

    private String[] prepareExpectedSecondAmortizedInventoryRows() {
        return new String[]{SECOND_INVENTORY_NAME, null, "600.00", "400.00", "200.00"};
    }

    private String[] prepareExpectedTotalCostRow() {
        return new String[]{"Total cost", "200.00", "700.00", "400.00", "200.00"};
    }
}
