package com.methodia.academy.blur;

import com.methodia.academy.blur.app.ImageProcessingApplicationService;
import com.methodia.academy.blur.app.ImageProcessingRequest;
import com.methodia.academy.blur.cli.CommandLineRequestParser;
import com.methodia.academy.blur.filter.AverageBrightnessBlurFilter;
import com.methodia.academy.blur.filter.BoxBlurFilter;
import com.methodia.academy.blur.filter.ColorFilter;
import com.methodia.academy.blur.filter.CropFilter;
import com.methodia.academy.blur.filter.FilterRegistry;
import com.methodia.academy.blur.filter.GaussianBlurFilter;
import com.methodia.academy.blur.input.CommandLineInputProvider;
import com.methodia.academy.blur.input.InputProvider;
import com.methodia.academy.blur.io.ImageReaderService;
import com.methodia.academy.blur.io.ImageWriterService;
import com.methodia.academy.blur.validation.ImageProcessingRequestValidator;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        ImageReaderService readerService = new ImageReaderService();
        ImageWriterService writerService = new ImageWriterService();
        FilterRegistry filterRegistry = new FilterRegistry(List.of(
                new BoxBlurFilter(),
                new GaussianBlurFilter(),
                new AverageBrightnessBlurFilter(),
                new ColorFilter(),
                new CropFilter()
        ));
        ImageProcessingRequestValidator validator = new ImageProcessingRequestValidator();
        CommandLineRequestParser parser = new CommandLineRequestParser(filterRegistry);
        InputProvider inputProvider = new CommandLineInputProvider(args);

        ImageProcessingApplicationService applicationService = new ImageProcessingApplicationService(
                readerService,
                writerService,
                filterRegistry,
                validator
        );

        ImageProcessingRequest request = parser.parse(inputProvider.provide());
        applicationService.process(request);
    }
}
