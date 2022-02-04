package ru.itlab.clickertests.provider;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class ValidNumberProvider implements ArgumentsProvider {
    private static final int NUMBERS_COUNT = 3;
    private static final int HEIGHT = 50;
    private final Random random = new Random();

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        List<Arguments> arguments = new ArrayList<>();
        arguments.add(Arguments.of(1));
        arguments.add(Arguments.of(2));
        arguments.add(Arguments.of(49));
        arguments.add(Arguments.of(50));
        for (int i = 0; i < NUMBERS_COUNT; i++){
            arguments.add(Arguments.of(1 + random.nextInt(HEIGHT)));
        }
            return arguments.stream();
    }
}
