package com.methodia.academy.blur.io;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageReaderService {
    public BufferedImage read(String path) {
        try {
            BufferedImage image = ImageIO.read(new File(path));

            if (image == null) {
                throw new IllegalArgumentException("Invalid image file: " + path);
            }

            return image;
        } catch (IOException e) {
            throw new RuntimeException("Could not read image: " + path, e);
        }
    }
}
