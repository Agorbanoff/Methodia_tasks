package com.methodia.academy.blur.blur;

import java.awt.image.BufferedImage;

public class BoxBlurProcessor implements BlurProcessor {

    @Override
    public BufferedImage process(BufferedImage image, int radius) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage result = new BufferedImage(width, height, image.getType());

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                result.setRGB(x, y, blurPixel(image, x, y, radius));
            }
        }

        return result;
    }

    private int blurPixel(BufferedImage image, int centerX, int centerY, int radius) {
        long alphaSum = 0;
        long redSum = 0;
        long greenSum = 0;
        long blueSum = 0;
        int pixelCount = 0;

        for (int offsetY = -radius; offsetY <= radius; offsetY++) {
            int sampleY = clamp(centerY + offsetY, 0, image.getHeight() - 1);

            for (int offsetX = -radius; offsetX <= radius; offsetX++) {
                int sampleX = clamp(centerX + offsetX, 0, image.getWidth() - 1);
                int argb = image.getRGB(sampleX, sampleY);

                alphaSum += (argb >>> 24) & 0xFF;
                redSum += (argb >>> 16) & 0xFF;
                greenSum += (argb >>> 8) & 0xFF;
                blueSum += argb & 0xFF;
                pixelCount++;
            }
        }

        int alpha = (int) (alphaSum / pixelCount);
        int red = (int) (redSum / pixelCount);
        int green = (int) (greenSum / pixelCount);
        int blue = (int) (blueSum / pixelCount);

        return composeArgb(alpha, red, green, blue);
    }

    private int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }

    private int composeArgb(int alpha, int red, int green, int blue) {
        return (alpha << 24)
                | (red << 16)
                | (green << 8)
                | blue;
    }
}
