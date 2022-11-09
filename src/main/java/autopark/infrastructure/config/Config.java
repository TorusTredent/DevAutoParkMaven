package autopark.infrastructure.config;

import autopark.infrastructure.core.Scanner;

public interface Config {

    <T> Class<? extends T> getImplementation(Class<T> target);

    Scanner getScanner();
}
