package com.methodia.academy.blur.filter;

import java.awt.image.BufferedImage;
import java.util.List;

public class ColorFilter extends BaseNeighborhoodFilter implements Filter {

    @Override
    public String name() {
        return "colorfilter";
    }

    @Override
    public int parameterCount() {
        return 1;
    }

    @Override
    public BufferedImage apply(BufferedImage image, List<String> parameters) {
        String color = FilterParameterParser.requireParameter(parameters, 0, "color").toLowerCase();
        BufferedImage result = createTargetImage(image);

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                result.setRGB(x, y, applyColorFilter(image.getRGB(x, y), color));
            }
        }

        return result;
    }

    private int applyColorFilter(int argb, String color) {
        int alpha = (argb >>> 24) & 0xFF;
        int red = (argb >>> 16) & 0xFF;
        int green = (argb >>> 8) & 0xFF;
        int blue = argb & 0xFF;

        return switch (color) {
            case "red" -> composeArgb(alpha, red, 0, 0);
            case "green" -> composeArgb(alpha, 0, green, 0);
            case "blue" -> composeArgb(alpha, 0, 0, blue);
            default -> throw new IllegalArgumentException(
                    "Unsupported color for colorfilter: " + color + ". Supported values: red, green, blue."
            );
        };
    }
}
