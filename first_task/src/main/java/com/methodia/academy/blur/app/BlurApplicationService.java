package com.methodia.academy.blur.app;

import com.methodia.academy.blur.blur.BlurProcessor;
import com.methodia.academy.blur.blur.BlurProcessorFactory;
import com.methodia.academy.blur.io.ImageReaderService;
import com.methodia.academy.blur.io.ImageWriterService;
import com.methodia.academy.blur.validation.BlurRequestValidator;

import java.awt.image.BufferedImage;

public class BlurApplicationService {
    private final ImageReaderService readerService;
    private final ImageWriterService writerService;
    private final BlurProcessorFactory processorFactory;
    private final BlurRequestValidator validator;

    public BlurApplicationService(
            ImageReaderService readerService,
            ImageWriterService writerService,
            BlurProcessorFactory processorFactory,
            BlurRequestValidator validator
    ) {
        this.readerService = readerService;
        this.writerService = writerService;
        this.processorFactory = processorFactory;
        this.validator = validator;
    }

    public void process(BlurRequest request) {
        validator.validate(request);

        BufferedImage inputImage = readerService.read(request.inputPath());

        BlurProcessor processor = processorFactory.create(request.blurType());

        BufferedImage outputImage = processor.process(
                inputImage,
                request.radius()
        );

        writerService.write(outputImage, request.outputPath());
    }
}
