package by.incubator.infrastructure.core.impl;

import by.incubator.infrastructure.core.Cache;

import java.util.HashMap;
import java.util.Map;

public class CacheImpl implements Cache {

    private Map<String, Object> cache;

    public CacheImpl() {
        cache = new HashMap<>();
    }

    @Override
    public boolean contains(Class<?> clazz) {
        return cache.containsKey(clazz.getSimpleName());
    }

    @Override
    public <T> T get(Class<?> clazz) {
        return (T) cache.get(clazz.getSimpleName());
    }

    @Override
    public <T> void put(Class<T> target, T value) {
        cache.put(target.getSimpleName(), value);
    }
}
