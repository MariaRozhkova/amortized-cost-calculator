package com.syberry.calculator.dto;

import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.Data;

/**
 * Class contains info about all amortized inventories.
 */
@Data
@Builder
public class AmortizedInventoryContext {

    private LocalDate minAmortizationStartDate;
    private LocalDate maxAmortizationEndDate;
    private List<AmortizedInventory> amortizedInventories;
}
