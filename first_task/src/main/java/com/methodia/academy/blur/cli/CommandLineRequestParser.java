package com.methodia.academy.blur.cli;

import com.methodia.academy.blur.app.ImageProcessingRequest;
import com.methodia.academy.blur.filter.FilterDefinition;
import com.methodia.academy.blur.filter.FilterRegistry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.net.URI;

public class CommandLineRequestParser {
    private final FilterRegistry filterRegistry;

    public CommandLineRequestParser(FilterRegistry filterRegistry) {
        this.filterRegistry = filterRegistry;
    }

    public ImageProcessingRequest parse(List<String> rawInput) {
        if (rawInput == null || rawInput.size() < 2) {
            throw new IllegalArgumentException(buildUsageMessage());
        }

        String inputPath = rawInput.get(0);
        List<FilterDefinition> filters = new ArrayList<>();
        int currentIndex = 1;

        while (currentIndex < rawInput.size()) {
            String filterName = rawInput.get(currentIndex);
            int parameterCount = filterRegistry.getByName(filterName).parameterCount();

            if (currentIndex + parameterCount >= rawInput.size()) {
                throw new IllegalArgumentException(
                        "Missing parameters for filter '" + filterName + "'. Expected "
                                + parameterCount + " argument(s)."
                );
            }



            List<String> parameters = Arrays.asList(
                    rawInput.subList(currentIndex + 1, currentIndex + 1 + parameterCount).toArray(String[]::new)
            );

            filters.add(new FilterDefinition(filterName, parameters));
            currentIndex += parameterCount + 1;
        }

        return new ImageProcessingRequest(
                inputPath,
                buildOutputPath(inputPath),
                filters
        );
    }

    private String buildOutputPath(String inputPath) {
        String extension = extractExtension(inputPath);

        if (extension == null || extension.isBlank()) {
            throw new IllegalArgumentException("Input path must include a file extension.");
        }

        return "src/main/resources/images/output/output." + extension;
    }

    private String extractExtension(String inputPath) {
        String normalizedPath = isHttpUrl(inputPath)
                ? URI.create(inputPath).getPath()
                : inputPath;
        String fileName = normalizedPath.substring(normalizedPath.lastIndexOf('/') + 1)
                .substring(normalizedPath.substring(normalizedPath.lastIndexOf('/') + 1).lastIndexOf('\\') + 1);
        int extensionSeparatorIndex = fileName.lastIndexOf('.');

        if (extensionSeparatorIndex >= 0 && extensionSeparatorIndex < fileName.length() - 1) {
            return fileName.substring(extensionSeparatorIndex + 1).toLowerCase(Locale.ROOT);
        }

        if (isHttpUrl(inputPath) && !fileName.isBlank()) {
            return fileName.toLowerCase(Locale.ROOT);
        }

        return null;
    }

    private boolean isHttpUrl(String path) {
        return path.startsWith("http://") || path.startsWith("https://");
    }

    private String buildUsageMessage() {
        return """
                Invalid arguments.
                Usage:
                <imagePathOrHttpUrl> <filterName> <filterParameters...>

                Example:
                src/main/resources/images/input/input.jpg averagebrightnessblur 3 colorfilter red crop 10 15 100 110
                """;
    }
}
