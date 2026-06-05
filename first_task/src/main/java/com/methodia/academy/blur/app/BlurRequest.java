package com.methodia.academy.blur.app;

import com.methodia.academy.blur.blur.BlurType;

public record BlurRequest(String inputPath, String outputPath, BlurType blurType, int radius) {
}
