package autopark.infrastructure.core.impl;

import autopark.infrastructure.config.Config;
import autopark.infrastructure.core.Cache;
import autopark.infrastructure.core.Context;
import autopark.infrastructure.core.ObjectFactory;
import autopark.infrastructure.config.impl.JavaConfig;

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
