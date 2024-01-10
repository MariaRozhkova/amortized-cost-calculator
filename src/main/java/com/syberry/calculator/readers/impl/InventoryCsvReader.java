package com.syberry.calculator.readers.impl;

import com.opencsv.bean.CsvToBeanBuilder;
import com.syberry.calculator.dto.CsvFileOptions;
import com.syberry.calculator.dto.Inventory;
import com.syberry.calculator.exceptions.EmptyFileException;
import com.syberry.calculator.exceptions.InventoryReaderException;
import com.syberry.calculator.readers.InventoryReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;

/**
 * {@inheritDoc}
 */
@Slf4j
public class InventoryCsvReader implements InventoryReader {

    @Override
    public List<Inventory> read(CsvFileOptions inputArgs) {
        var inputFilePath = inputArgs.inputFilePath();
        try (var fileReader = new FileReader(inputFilePath)) {
            var inventories = new CsvToBeanBuilder<Inventory>(fileReader)
                .withType(Inventory.class)
                .withSeparator(inputArgs.separator())
                .build()
                .parse();

            validateIfNotEmpty(inventories, inputFilePath);

            log.info(
                "{} inventories were successfully read from file {}",
                inventories.size(),
                inputFilePath
            );

            return inventories;
        } catch (IOException ex) {
            var message = String.format(
                "Error occurred while reading file by path %s",
                inputFilePath
            );
            throw new InventoryReaderException(message, ex);
        }
    }

    private void validateIfNotEmpty(List<Inventory> inventories, String inputFilePath) {
        if (CollectionUtils.isEmpty(inventories)) {
            var message = String.format(
                "File by path %s does not contain inventories to process",
                inputFilePath
            );

            throw new EmptyFileException(message);
        }
    }
}
