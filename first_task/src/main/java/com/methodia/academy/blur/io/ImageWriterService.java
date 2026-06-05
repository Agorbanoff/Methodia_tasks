package com.methodia.academy.blur.io;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageWriterService {
    public void write(BufferedImage image, String path) {
        try {
            String format = getFormat(path);
            ImageIO.write(image, format, new File(path));
        } catch (IOException e) {
            throw new RuntimeException("Could not write image: " + path, e);
        }
    }

    private String getFormat(String path) {
        int dotIndex = path.lastIndexOf('.');

        if (dotIndex == -1 || dotIndex == path.length() - 1) {
            throw new IllegalArgumentException("Output path must contain file extension.");
        }

        return path.substring(dotIndex + 1);
    }
}
