package com.methodia.academy.blur.blur;

public class BlurProcessorFactory {
    public BlurProcessor create(BlurType type) {
        if (type == BlurType.BOX) {
            return new BoxBlurProcessor();
        }

        if (type == BlurType.GAUSSIAN) {
            return new GaussianBlurProcessor();
        }

        throw new IllegalArgumentException("Unsupported blur type: " + type);
    }
}
