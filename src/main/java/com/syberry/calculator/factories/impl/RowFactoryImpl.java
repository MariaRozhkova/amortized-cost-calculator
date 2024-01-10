package com.syberry.calculator.factories.impl;

import com.syberry.calculator.dto.AmortizedInventory;
import com.syberry.calculator.dto.AmortizedInventoryContext;
import com.syberry.calculator.dto.Row;
import com.syberry.calculator.factories.RowFactory;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RowFactoryImpl implements RowFactory {

    private static final DateTimeFormatter DATE_HEADER_FORMATTER
        = DateTimeFormatter.ofPattern("MMM-yyyy");
    private static final int FIRST_COLUMN_IDX = 0;
    private static final String INVENTORY_NAME_HEADER = "Inventory name";
    private static final String TOTAL_COST_HEADER = "Total cost";
    private static final int DATES_HEADER_DIFF_IN_MONTHS = 1;
    private final static int DIGITS_NUMBER_AFTER_DECIMAL_POINT_IN_PRICE = 2;
    private final static int COLUMNS_COUNT_BEFORE_DATES_HEADER = 1;

    @Override
    public List<Row> generateRows(AmortizedInventoryContext context) {
        var resultRows = new ArrayList<Row>();

        var header = prepareHeader(
            context.getMinAmortizationStartDate(),
            context.getMaxAmortizationEndDate()
        );
        resultRows.add(header);

        var totalCostByMonth = new BigDecimal[header.size()];
        var amortizedInventoryRows = prepareAmortizedInventoryRows(
            context,
            totalCostByMonth
        );
        resultRows.addAll(amortizedInventoryRows);

        var totalCostByMonthRow = prepareTotalCostByMonthRow(totalCostByMonth);
        resultRows.add(totalCostByMonthRow);

        return resultRows;
    }

    private Row prepareHeader(LocalDate fromDate, LocalDate toDate) {
        var headerFields = generateDatesHeader(fromDate, toDate);
        headerFields.add(FIRST_COLUMN_IDX, INVENTORY_NAME_HEADER);

        return new Row(headerFields.toArray(String[]::new));
    }

    private List<String> generateDatesHeader(LocalDate fromDate, LocalDate toDate) {
        return Stream.iterate(fromDate, date -> date.plusMonths(DATES_HEADER_DIFF_IN_MONTHS))
            .limit(ChronoUnit.MONTHS.between(fromDate, toDate))
            .map(date -> date.format(DATE_HEADER_FORMATTER))
            .collect(Collectors.toList());
    }

    private List<Row> prepareAmortizedInventoryRows(
        AmortizedInventoryContext context,
        BigDecimal[] totalCostByMonth
    ) {
        var minAmortizationStartDate = context.getMinAmortizationStartDate();

        return context.getAmortizedInventories().stream()
            .map(amortizedInventory -> {
                int firstAmortizedCostColumnIdx = getFirstAmortizedCostColumnIdx(
                    minAmortizationStartDate,
                    amortizedInventory
                );
                return prepareAmortizedInventoryRow(
                    amortizedInventory,
                    totalCostByMonth,
                    firstAmortizedCostColumnIdx
                );
            })
            .collect(Collectors.toList());
    }

    private int getFirstAmortizedCostColumnIdx(
        LocalDate minAmortizationStartDate,
        AmortizedInventory amortizedInventory
    ) {
        return (int) ChronoUnit.MONTHS.between(
            minAmortizationStartDate,
            amortizedInventory.getAmortizationStartDate()
        ) + COLUMNS_COUNT_BEFORE_DATES_HEADER;
    }

    private Row prepareAmortizedInventoryRow(
        AmortizedInventory item,
        BigDecimal[] totalCostByMonth,
        int amortizedCostColumnIdx
    ) {
        var amortizedInventoryRow = new String[totalCostByMonth.length];
        amortizedInventoryRow[FIRST_COLUMN_IDX] = item.getInventoryName();

        var amortizedCost = convertToBigDecimal(item.getInitialCost());
        var amortization = convertToBigDecimal(item.getAmortization());

        for (
            var amortizationDate = item.getAmortizationStartDate();
            amortizationDate.isBefore(item.getAmortizationEndDate());
            amortizationDate = amortizationDate.plusMonths(DATES_HEADER_DIFF_IN_MONTHS),
                amortizedCostColumnIdx++
        ) {
            amortizedInventoryRow[amortizedCostColumnIdx] = amortizedCost.toString();

            var totalCost = totalCostByMonth[amortizedCostColumnIdx];
            totalCostByMonth[amortizedCostColumnIdx] = totalCost == null
                ? amortizedCost
                : totalCost.add(amortizedCost);

            amortizedCost = amortizedCost.subtract(amortization);
        }

        return new Row(amortizedInventoryRow);
    }

    private BigDecimal convertToBigDecimal(double item) {
        return BigDecimal.valueOf(item)
            .setScale(DIGITS_NUMBER_AFTER_DECIMAL_POINT_IN_PRICE, RoundingMode.HALF_EVEN);
    }

    private Row prepareTotalCostByMonthRow(BigDecimal[] totalCostByMonth) {
        var convertedTotalCostByMonth = new String[totalCostByMonth.length];

        for (var i = FIRST_COLUMN_IDX; i < totalCostByMonth.length; i++) {
            convertedTotalCostByMonth[i] = String.valueOf(totalCostByMonth[i]);
        }
        convertedTotalCostByMonth[FIRST_COLUMN_IDX] = TOTAL_COST_HEADER;

        return new Row(convertedTotalCostByMonth);
    }
}
