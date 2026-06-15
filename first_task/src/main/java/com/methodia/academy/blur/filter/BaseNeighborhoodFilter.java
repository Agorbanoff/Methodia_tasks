package com.methodia.academy.blur.filter;

import java.awt.image.BufferedImage;

public abstract class BaseNeighborhoodFilter {

    protected int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }

    protected int composeArgb(int alpha, int red, int green, int blue) {
        return (alpha << 24)
                | (red << 16)
                | (green << 8)
                | blue;
    }

    protected BufferedImage createTargetImage(BufferedImage sourceImage) {
        int imageType = sourceImage.getType() == 0
                ? BufferedImage.TYPE_INT_ARGB
                : sourceImage.getType();

        return new BufferedImage(sourceImage.getWidth(), sourceImage.getHeight(), imageType);
    }
}
