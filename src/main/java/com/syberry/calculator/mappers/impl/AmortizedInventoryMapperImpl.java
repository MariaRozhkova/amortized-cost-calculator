package com.syberry.calculator.mappers.impl;

import com.syberry.calculator.dto.AmortizedInventory;
import com.syberry.calculator.dto.AmortizedInventoryContext;
import com.syberry.calculator.dto.Inventory;
import com.syberry.calculator.mappers.AmortizedInventoryMapper;
import com.syberry.calculator.validators.InventoryValidator;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AmortizedInventoryMapperImpl implements AmortizedInventoryMapper {

    private final InventoryValidator inventoryValidator;

    @Override
    public AmortizedInventoryContext mapToAmortizedInventories(List<Inventory> inventories) {
        var amortizedInventoryItems = new ArrayList<AmortizedInventory>();

        LocalDate minAmortizationStartDate = null;
        LocalDate maxAmortizationEndDate = null;

        for (var inventory : inventories) {
            inventoryValidator.validateInventory(inventory);
            var amortizedInventory = prepareAmortizedInventory(inventory);
            amortizedInventoryItems.add(amortizedInventory);

            var amortizationStartDate = amortizedInventory.getAmortizationStartDate();
            var amortizationEndDate = amortizedInventory.getAmortizationEndDate();

            if (minAmortizationStartDate == null
                || amortizationStartDate.isBefore(minAmortizationStartDate)) {
                minAmortizationStartDate = amortizationStartDate;
            }
            if (maxAmortizationEndDate == null
                || amortizationEndDate.isAfter(maxAmortizationEndDate)) {
                maxAmortizationEndDate = amortizationEndDate;
            }
        }

        return AmortizedInventoryContext.builder()
            .minAmortizationStartDate(minAmortizationStartDate)
            .maxAmortizationEndDate(maxAmortizationEndDate)
            .amortizedInventories(amortizedInventoryItems)
            .build();
    }

    private AmortizedInventory prepareAmortizedInventory(Inventory inventory) {
        var purchaseDate = inventory.getPurchaseDate();
        var amortizationStartDate = purchaseDate.with(TemporalAdjusters.firstDayOfNextMonth());
        var servicePeriodInMonths = inventory.getServicePeriod();
        var amortizationEndDate = amortizationStartDate.plusMonths(servicePeriodInMonths);

        var initialCost = inventory.getPrice();
        var amortizationByLinearMethod = initialCost / servicePeriodInMonths;

        return AmortizedInventory.builder()
            .inventoryName(inventory.getName())
            .initialCost(initialCost)
            .amortizationStartDate(amortizationStartDate)
            .amortizationEndDate(amortizationEndDate)
            .amortization(amortizationByLinearMethod)
            .build();
    }
}
