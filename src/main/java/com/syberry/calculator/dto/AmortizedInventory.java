package com.syberry.calculator.dto;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;

/**
 * Class which describes amortized inventory.
 */
@Data
@Builder
public class AmortizedInventory {

    private String inventoryName;
    private LocalDate amortizationStartDate;
    private LocalDate amortizationEndDate;
    private double initialCost;
    private double amortization;
}
