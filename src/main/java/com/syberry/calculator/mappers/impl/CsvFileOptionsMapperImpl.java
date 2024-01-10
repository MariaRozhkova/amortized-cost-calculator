package com.syberry.calculator.mappers.impl;

import com.syberry.calculator.dto.CsvFileOptions;
import com.syberry.calculator.exceptions.InputArgsParseException;
import com.syberry.calculator.mappers.CsvFileOptionsMapper;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.StringUtils;

/**
 * {@inheritDoc}
 */
public class CsvFileOptionsMapperImpl implements CsvFileOptionsMapper {

    private static final DefaultParser DEFAULT_PARSER = new DefaultParser();
    private static final String INPUT_FILE_PATH_ARG_NAME = "inputFilePath";
    private static final String OUTPUT_DIR_ARG_NAME = "outputDir";
    private static final String SEPARATOR_ARG_NAME = "separator";
    private static final char DEFAULT_SEPARATOR = ',';
    private static final int FIRST_IDX = 0;

    @Override
    public CsvFileOptions map(String[] args) {
        var options = new Options();
        options.addRequiredOption(
            INPUT_FILE_PATH_ARG_NAME,
            INPUT_FILE_PATH_ARG_NAME,
            true,
            "File path to input file"
        );
        options.addRequiredOption(
            OUTPUT_DIR_ARG_NAME,
            OUTPUT_DIR_ARG_NAME,
            true,
            "Path to directory where output file will be stored"
        );
        options.addOption(
            SEPARATOR_ARG_NAME,
            SEPARATOR_ARG_NAME,
            true,
            "Column separator in csv file"
        );

        try {
            var commandLine = DEFAULT_PARSER.parse(options, args);

            var separatorFromArgs = commandLine.getOptionValue(SEPARATOR_ARG_NAME);
            var separator = StringUtils.isEmpty(separatorFromArgs)
                ? DEFAULT_SEPARATOR
                : separatorFromArgs.charAt(FIRST_IDX);
            return new CsvFileOptions(
                commandLine.getOptionValue(INPUT_FILE_PATH_ARG_NAME),
                commandLine.getOptionValue(OUTPUT_DIR_ARG_NAME),
                separator
            );
        } catch (ParseException ex) {
            var message = "Error occurred while parsing command line arguments";
            throw new InputArgsParseException(message, ex);
        }
    }
}
