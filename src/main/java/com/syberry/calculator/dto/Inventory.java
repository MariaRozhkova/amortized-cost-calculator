package com.syberry.calculator.dto;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import java.time.LocalDate;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

/**
 * Class which describes inventory.
 */
@Data
@FieldNameConstants
public class Inventory {

    @CsvBindByName(column = "name", required = true)
    private String name;

    @CsvBindByName(column = "purchase date", required = true)
    @CsvDate(value = "yyyy-MM-dd")
    private LocalDate purchaseDate;

    @CsvBindByName(column = "service period", required = true)
    private int servicePeriod;

    @CsvBindByName(column = "price", required = true)
    private double price;
}
