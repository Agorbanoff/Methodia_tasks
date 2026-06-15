package com.methodia.academy.blur.app;

import com.methodia.academy.blur.filter.FilterDefinition;
import com.methodia.academy.blur.filter.FilterRegistry;
import com.methodia.academy.blur.io.ImageReaderService;
import com.methodia.academy.blur.io.ImageWriterService;
import com.methodia.academy.blur.validation.ImageProcessingRequestValidator;

import java.awt.image.BufferedImage;

public class ImageProcessingApplicationService {
    private final ImageReaderService readerService;
    private final ImageWriterService writerService;
    private final FilterRegistry filterRegistry;
    private final ImageProcessingRequestValidator validator;

    public ImageProcessingApplicationService(
            ImageReaderService readerService,
            ImageWriterService writerService,
            FilterRegistry filterRegistry,
            ImageProcessingRequestValidator validator
    ) {
        this.readerService = readerService;
        this.writerService = writerService;
        this.filterRegistry = filterRegistry;
        this.validator = validator;
    }

    public void process(ImageProcessingRequest request) {
        validator.validate(request);

        BufferedImage currentImage = readerService.read(request.inputPath());

        for (FilterDefinition filterDefinition : request.filters()) {
            currentImage = filterRegistry
                    .getByName(filterDefinition.name())
                    .apply(currentImage, filterDefinition.parameters());
        }

        writerService.write(currentImage, request.outputPath());
    }
}
