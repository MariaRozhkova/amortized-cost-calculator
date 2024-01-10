package com.syberry.calculator.mappers;

import com.syberry.calculator.dto.CsvFileOptions;

/**
 * Mapper for converting command line arguments to {@link CsvFileOptions}.
 */
public interface CsvFileOptionsMapper {

    /**
     * Maps command line arguments to {@link CsvFileOptions}.
     */
    CsvFileOptions map(String[] args);
}
