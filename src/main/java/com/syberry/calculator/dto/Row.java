package com.syberry.calculator.dto;

/**
 * Class represents row to be written to result file.
 */
public record Row(String[] fields) {

    public int size() {
        return this.fields.length;
    }
}
