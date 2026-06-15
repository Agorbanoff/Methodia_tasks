package com.methodia.academy.blur.filter;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;

public class CropFilter implements Filter {

    @Override
    public String name() {
        return "crop";
    }

    @Override
    public int parameterCount() {
        return 4;
    }

    @Override
    public BufferedImage apply(BufferedImage image, List<String> parameters) {
        int x = FilterParameterParser.parseNonNegativeInteger(parameters, 0, "x");
        int y = FilterParameterParser.parseNonNegativeInteger(parameters, 1, "y");
        int width = FilterParameterParser.parsePositiveInteger(parameters, 2, "width");
        int height = FilterParameterParser.parsePositiveInteger(parameters, 3, "height");

        if (x + width > image.getWidth() || y + height > image.getHeight()) {
            throw new IllegalArgumentException("Crop rectangle exceeds image bounds.");
        }

        BufferedImage sourceRegion = image.getSubimage(x, y, width, height);
        int imageType = image.getType() == 0
                ? BufferedImage.TYPE_INT_ARGB
                : image.getType();
        BufferedImage result = new BufferedImage(width, height, imageType);
        Graphics2D graphics = result.createGraphics();

        try {
            graphics.drawImage(sourceRegion, 0, 0, null);
        } finally {
            graphics.dispose();
        }

        return result;
    }
}
