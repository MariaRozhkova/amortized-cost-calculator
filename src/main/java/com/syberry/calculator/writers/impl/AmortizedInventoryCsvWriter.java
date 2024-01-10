package com.syberry.calculator.writers.impl;

import com.opencsv.CSVWriter;
import com.syberry.calculator.dto.AmortizedInventoryContext;
import com.syberry.calculator.dto.CsvFileOptions;
import com.syberry.calculator.dto.Row;
import com.syberry.calculator.exceptions.AmortizedInventoryWriterException;
import com.syberry.calculator.factories.RowFactory;
import com.syberry.calculator.writers.AmortizedInventoryWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * {@inheritDoc}
 */
@RequiredArgsConstructor
@Slf4j
public class AmortizedInventoryCsvWriter implements AmortizedInventoryWriter {

    private static final String DEFAULT_OUTPUT_FILENAME = "result.csv";

    private final RowFactory rowFactory;

    @Override
    public void write(CsvFileOptions fileOptions, AmortizedInventoryContext context) {
        var outputDir = fileOptions.outputDir();
        createDirectories(outputDir);
        var resultFilePath = Path.of(outputDir, DEFAULT_OUTPUT_FILENAME).toString();

        try (
            var fileWriter = new FileWriter(resultFilePath);
            var csvWriter = new CSVWriter(
                fileWriter,
                fileOptions.separator(),
                CSVWriter.NO_QUOTE_CHARACTER,
                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                CSVWriter.DEFAULT_LINE_END
            )
        ) {
            var rowsToWrite = rowFactory.generateRows(context);

            for (Row row : rowsToWrite) {
                csvWriter.writeNext(row.fields());
            }
            log.info(
                "Info about amortized inventories cost by month was successfully written "
                    + "to file by path {}",
                resultFilePath
            );
        } catch (IOException ex) {
            var message = String.format(
                "Error occurred while writing amortized inventories to file by path %s",
                resultFilePath
            );
            throw new AmortizedInventoryWriterException(message, ex);
        }
    }

    private void createDirectories(String filePath) {
        try {
            Files.createDirectories(Path.of(filePath));
        } catch (IOException ex) {
            var message = String.format(
                "Error occurred while creating directories by path %s",
                filePath
            );
            throw new AmortizedInventoryWriterException(message, ex);
        }
    }
}
