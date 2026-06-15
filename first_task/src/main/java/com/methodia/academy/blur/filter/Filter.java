package com.methodia.academy.blur.filter;

import java.awt.image.BufferedImage;
import java.util.List;

public interface Filter {
    String name();

    int parameterCount();

    BufferedImage apply(BufferedImage image, List<String> parameters);
}
