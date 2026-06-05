package com.methodia.academy.blur.blur;

import java.awt.image.BufferedImage;

public interface BlurProcessor {
    BufferedImage process(BufferedImage image, int radius);
}
