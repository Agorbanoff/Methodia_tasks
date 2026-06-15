package com.methodia.academy.blur.filter;

import java.util.List;

public final class FilterParameterParser {
    private FilterParameterParser() {
    }

    public static String requireParameter(List<String> parameters, int index, String name) {
        if (parameters == null || index >= parameters.size()) {
            throw new IllegalArgumentException("Missing parameter: " + name);
        }

        String value = parameters.get(index);

        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Parameter cannot be blank: " + name);
        }

        return value;
    }

    public static int parsePositiveInteger(List<String> parameters, int index, String name) {
        int value = parseInteger(parameters, index, name);

        if (value <= 0) {
            throw new IllegalArgumentException(name + " must be greater than 0.");
        }

        return value;
    }

    public static int parseNonNegativeInteger(List<String> parameters, int index, String name) {
        int value = parseInteger(parameters, index, name);

        if (value < 0) {
            throw new IllegalArgumentException(name + " must be greater than or equal to 0.");
        }

        return value;
    }

    private static int parseInteger(List<String> parameters, int index, String name) {
        String value = requireParameter(parameters, index, name);

        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("Invalid integer for " + name + ": " + value, exception);
        }
    }
}
