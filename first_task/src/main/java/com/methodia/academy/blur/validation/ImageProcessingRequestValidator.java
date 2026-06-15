package com.methodia.academy.blur.validation;

import com.methodia.academy.blur.app.ImageProcessingRequest;
import com.methodia.academy.blur.filter.FilterDefinition;

public class ImageProcessingRequestValidator {

    public void validate(ImageProcessingRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Image processing request cannot be null.");
        }

        if (isBlank(request.inputPath())) {
            throw new IllegalArgumentException("Input image path cannot be blank.");
        }

        if (isBlank(request.outputPath())) {
            throw new IllegalArgumentException("Output image path cannot be blank.");
        }

        if (request.filters() == null || request.filters().isEmpty()) {
            throw new IllegalArgumentException("At least one filter must be provided.");
        }

        for (FilterDefinition filterDefinition : request.filters()) {
            if (filterDefinition == null) {
                throw new IllegalArgumentException("Filter definition cannot be null.");
            }

            if (isBlank(filterDefinition.name())) {
                throw new IllegalArgumentException("Filter name cannot be blank.");
            }
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
