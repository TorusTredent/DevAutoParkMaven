package by.incubator.infrastructure.core;

public interface Cache {

    boolean contains(Class<?> clazz);

    <T> T get(Class<?> clazz);

    <T> void put(Class<T> clazz, T value);
}
