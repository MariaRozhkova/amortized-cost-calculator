package com.syberry.calculator;

import com.syberry.calculator.factories.impl.RowFactoryImpl;
import com.syberry.calculator.mappers.AmortizedInventoryMapper;
import com.syberry.calculator.mappers.CsvFileOptionsMapper;
import com.syberry.calculator.mappers.impl.AmortizedInventoryMapperImpl;
import com.syberry.calculator.mappers.impl.CsvFileOptionsMapperImpl;
import com.syberry.calculator.readers.InventoryReader;
import com.syberry.calculator.readers.impl.InventoryCsvReader;
import com.syberry.calculator.validators.impl.InventoryValidatorImpl;
import com.syberry.calculator.writers.AmortizedInventoryWriter;
import com.syberry.calculator.writers.impl.AmortizedInventoryCsvWriter;
import lombok.RequiredArgsConstructor;

/**
 * Main class to launch amortized cost calculator.
 */
@RequiredArgsConstructor
public class AmortizedCostCalculator {

    private final InventoryReader inventoryReader;
    private final AmortizedInventoryMapper amortizedInventoryMapper;
    private final AmortizedInventoryWriter amortizedInventoryWriter;
    private final CsvFileOptionsMapper csvFileOptionsMapper;

    /**
     * Entry point for amortized cost calculator.
     */
    public static void main(String[] args) {
        var rowFactory = new RowFactoryImpl();
        var inventoryValidator = new InventoryValidatorImpl();
        var amortizedCostCalculator = new AmortizedCostCalculator(
            new InventoryCsvReader(),
            new AmortizedInventoryMapperImpl(inventoryValidator),
            new AmortizedInventoryCsvWriter(rowFactory),
            new CsvFileOptionsMapperImpl()
        );
        amortizedCostCalculator.calculateAmortizedCost(args);
    }

    /**
     * Calculates amortized cost by month for inventories.
     */
    public void calculateAmortizedCost(String[] args) {
        var fileOptions = csvFileOptionsMapper.map(args);

        var inventories = inventoryReader.read(fileOptions);
        var amortizedInventoryContext = amortizedInventoryMapper
            .mapToAmortizedInventories(inventories);

        amortizedInventoryWriter.write(fileOptions, amortizedInventoryContext);
    }
}
