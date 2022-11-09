package by.incubator.infrastructure.core.impl;

import by.incubator.infrastructure.config.Config;
import by.incubator.infrastructure.config.impl.JavaConfig;
import by.incubator.infrastructure.core.Cache;
import by.incubator.infrastructure.core.Context;
import by.incubator.infrastructure.core.ObjectFactory;

import java.util.Map;

public class ApplicationContext implements Context {

    private final Config config;
    private final Cache cache;
    private final ObjectFactory factory;

    public ApplicationContext(String packageToScan, Map<Class<?>, Class<?>> interfaceToImplementation) {
        this.config = new JavaConfig(new ScannerImpl(packageToScan), interfaceToImplementation);
        this.cache = new CacheImpl();
        cache.put(Context.class, this);
        this.factory = new ObjectFactoryImpl(this);
    }

    @Override
    public <T> T getObject(Class<T> type) {
        if (cache.contains(type)) {
            return cache.get(type);
        }
        T object = null;
        if (type.isInterface()) {
            Class implementation = config.getImplementation(type);
            object = (T) factory.createObject(implementation);
        } else {
            object = (T) factory.createObject(type);
        }
        cache.put(type, object);
        return object;
    }

    @Override
    public Config getConfig() {
        return config;
    }
}
