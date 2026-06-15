package com.methodia.academy.blur.input;

import java.util.Arrays;
import java.util.List;

public class CommandLineInputProvider implements InputProvider {
    private final String[] args;

    public CommandLineInputProvider(String[] args) {
        this.args = args == null ? new String[0] : args.clone();
    }

    @Override
    public List<String> provide() {
        return Arrays.asList(args);
    }
}
