package by.incubator.infrastructure.config;

import by.incubator.infrastructure.core.Scanner;

public interface Config {

    <T> Class<? extends T> getImplementation(Class<T> target);

    Scanner getScanner();
}
