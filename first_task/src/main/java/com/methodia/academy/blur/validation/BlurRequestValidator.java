package com.methodia.academy.blur.validation;

import com.methodia.academy.blur.app.BlurRequest;

public class BlurRequestValidator {

    public void validate(BlurRequest blurRequest) {
        if (blurRequest == null) {
            throw new IllegalArgumentException("Blur blurRequest cannot be null.");
        }

        if (isBlank(blurRequest.inputPath())) {
            throw new IllegalArgumentException("Input image path cannot be blank.");
        }

        if (isBlank(blurRequest.outputPath())) {
            throw new IllegalArgumentException("Output image path cannot be blank.");
        }

        if (blurRequest.blurType() == null) {
            throw new IllegalArgumentException("Blur type cannot be null.");
        }

        if (blurRequest.radius() <= 0) {
            throw new IllegalArgumentException("Radius must be greater than 0.");
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
