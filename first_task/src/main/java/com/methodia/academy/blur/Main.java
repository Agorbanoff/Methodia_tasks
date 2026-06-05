package com.methodia.academy.blur;

import com.methodia.academy.blur.app.BlurApplicationService;
import com.methodia.academy.blur.app.BlurRequest;
import com.methodia.academy.blur.blur.BlurProcessorFactory;
import com.methodia.academy.blur.blur.BlurType;
import com.methodia.academy.blur.io.ImageReaderService;
import com.methodia.academy.blur.io.ImageWriterService;
import com.methodia.academy.blur.validation.BlurRequestValidator;

public class Main {
    public static void main(String[] args) {
        ImageReaderService readerService = new ImageReaderService();
        ImageWriterService writerService = new ImageWriterService();
        BlurProcessorFactory processorFactory = new BlurProcessorFactory();
        BlurRequestValidator validator = new BlurRequestValidator();

        BlurApplicationService blurApplicationService = new BlurApplicationService(
                readerService,
                writerService,
                processorFactory,
                validator
        );

        BlurRequest blurRequest = new BlurRequest(
                "src/main/resources/images/input/input.jpg",
                "src/main/resources/images/output/output.jpg",
                BlurType.BOX,
                3
        );

        blurApplicationService.process(blurRequest);
    }
}
