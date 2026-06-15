package com.methodia.academy.blur.app;

import com.methodia.academy.blur.filter.FilterDefinition;

import java.util.List;

public record ImageProcessingRequest(String inputPath, String outputPath, List<FilterDefinition> filters) {
}
