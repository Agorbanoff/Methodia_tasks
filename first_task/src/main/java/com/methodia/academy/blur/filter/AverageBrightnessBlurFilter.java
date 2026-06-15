package com.methodia.academy.blur.filter;

import java.awt.image.BufferedImage;
import java.util.List;

public class AverageBrightnessBlurFilter extends BaseNeighborhoodFilter implements Filter {

    @Override
    public String name() {
        return "averagebrightnessblur";
    }

    @Override
    public int parameterCount() {
        return 1;
    }

    @Override
    public BufferedImage apply(BufferedImage image, List<String> parameters) {
        int radius = FilterParameterParser.parsePositiveInteger(parameters, 0, "radius");
        BufferedImage result = createTargetImage(image);

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                result.setRGB(x, y, blurPixel(image, x, y, radius));
            }
        }

        return result;
    }

    private int blurPixel(BufferedImage image, int centerX, int centerY, int radius) {
        long alphaSum = 0;
        long brightnessSum = 0;
        int pixelCount = 0;

        for (int offsetY = -radius; offsetY <= radius; offsetY++) {
            int sampleY = clamp(centerY + offsetY, 0, image.getHeight() - 1);

            for (int offsetX = -radius; offsetX <= radius; offsetX++) {
                int sampleX = clamp(centerX + offsetX, 0, image.getWidth() - 1);
                int argb = image.getRGB(sampleX, sampleY);

                alphaSum += (argb >>> 24) & 0xFF;
                brightnessSum += averageBrightness(argb);
                pixelCount++;
            }
        }

        int alpha = (int) (alphaSum / pixelCount);
        int brightness = (int) (brightnessSum / pixelCount);

        return composeArgb(alpha, brightness, brightness, brightness);
    }

    private int averageBrightness(int argb) {
        int red = (argb >>> 16) & 0xFF;
        int green = (argb >>> 8) & 0xFF;
        int blue = argb & 0xFF;
        return (red + green + blue) / 3;
    }
}
