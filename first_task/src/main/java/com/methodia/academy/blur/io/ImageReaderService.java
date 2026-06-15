package com.methodia.academy.blur.io;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

public class ImageReaderService {
    public BufferedImage read(String path) {
        try {
            BufferedImage image = isHttpUrl(path)
                    ? readFromUrl(path)
                    : ImageIO.read(new File(path));

            if (image == null) {
                throw new IllegalArgumentException("Invalid image file: " + path);
            }

            return image;
        } catch (IOException e) {
            throw new RuntimeException("Could not read image: " + path, e);
        }
    }

    private BufferedImage readFromUrl(String path) throws IOException {
        try (InputStream inputStream = URI.create(path).toURL().openStream()) {
            return ImageIO.read(inputStream);
        }
    }

    private boolean isHttpUrl(String path) {
        return path.startsWith("http://") || path.startsWith("https://");
    }
}
