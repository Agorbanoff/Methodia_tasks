package com.methodia.academy.blur.filter;

import java.awt.image.BufferedImage;
import java.util.List;

public class GaussianBlurFilter extends BaseNeighborhoodFilter implements Filter {

    @Override
    public String name() {
        return "gaussianblur";
    }

    @Override
    public int parameterCount() {
        return 1;
    }

    @Override
    public BufferedImage apply(BufferedImage image, List<String> parameters) {
        int radius = FilterParameterParser.parsePositiveInteger(parameters, 0, "radius");
        int[] kernel = createKernel(radius);
        int kernelWeight = sum(kernel);

        BufferedImage horizontalPass = createTargetImage(image);
        BufferedImage result = createTargetImage(image);

        applyHorizontalPass(image, horizontalPass, kernel, radius, kernelWeight);
        applyVerticalPass(horizontalPass, result, kernel, radius, kernelWeight);

        return result;
    }

    private void applyHorizontalPass(
            BufferedImage source,
            BufferedImage target,
            int[] kernel,
            int radius,
            int kernelWeight
    ) {
        for (int y = 0; y < source.getHeight(); y++) {
            for (int x = 0; x < source.getWidth(); x++) {
                target.setRGB(x, y, blurHorizontalPixel(source, x, y, kernel, radius, kernelWeight));
            }
        }
    }

    private void applyVerticalPass(
            BufferedImage source,
            BufferedImage target,
            int[] kernel,
            int radius,
            int kernelWeight
    ) {
        for (int y = 0; y < source.getHeight(); y++) {
            for (int x = 0; x < source.getWidth(); x++) {
                target.setRGB(x, y, blurVerticalPixel(source, x, y, kernel, radius, kernelWeight));
            }
        }
    }

    private int blurHorizontalPixel(
            BufferedImage image,
            int centerX,
            int centerY,
            int[] kernel,
            int radius,
            int kernelWeight
    ) {
        long alphaSum = 0;
        long redSum = 0;
        long greenSum = 0;
        long blueSum = 0;

        for (int index = 0; index < kernel.length; index++) {
            int offsetX = index - radius;
            int sampleX = clamp(centerX + offsetX, 0, image.getWidth() - 1);
            int argb = image.getRGB(sampleX, centerY);
            int weight = kernel[index];

            alphaSum += ((argb >>> 24) & 0xFFL) * weight;
            redSum += ((argb >>> 16) & 0xFFL) * weight;
            greenSum += ((argb >>> 8) & 0xFFL) * weight;
            blueSum += (argb & 0xFFL) * weight;
        }

        return composeArgb(
                (int) (alphaSum / kernelWeight),
                (int) (redSum / kernelWeight),
                (int) (greenSum / kernelWeight),
                (int) (blueSum / kernelWeight)
        );
    }

    private int blurVerticalPixel(
            BufferedImage image,
            int centerX,
            int centerY,
            int[] kernel,
            int radius,
            int kernelWeight
    ) {
        long alphaSum = 0;
        long redSum = 0;
        long greenSum = 0;
        long blueSum = 0;

        for (int index = 0; index < kernel.length; index++) {
            int offsetY = index - radius;
            int sampleY = clamp(centerY + offsetY, 0, image.getHeight() - 1);
            int argb = image.getRGB(centerX, sampleY);
            int weight = kernel[index];

            alphaSum += ((argb >>> 24) & 0xFFL) * weight;
            redSum += ((argb >>> 16) & 0xFFL) * weight;
            greenSum += ((argb >>> 8) & 0xFFL) * weight;
            blueSum += (argb & 0xFFL) * weight;
        }

        return composeArgb(
                (int) (alphaSum / kernelWeight),
                (int) (redSum / kernelWeight),
                (int) (greenSum / kernelWeight),
                (int) (blueSum / kernelWeight)
        );
    }

    private int[] createKernel(int radius) {
        int size = radius * 2 + 1;
        int[] kernel = new int[size];

        for (int i = 0; i < size; i++) {
            int distance = i - radius;
            kernel[i] = radius + 1 - Math.abs(distance);
        }

        return kernel;
    }

    private int sum(int[] values) {
        int total = 0;

        for (int value : values) {
            total += value;
        }

        return total;
    }
}