package by.incubator.infrastructure.core.impl;

import by.incubator.infrastructure.core.annotations.InitMethod;
import by.incubator.infrastructure.configurators.ObjectConfigurator;
import by.incubator.infrastructure.core.Context;
import by.incubator.infrastructure.core.ObjectFactory;
import by.incubator.infrastructure.core.Scanner;
import lombok.SneakyThrows;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ObjectFactoryImpl implements ObjectFactory {

    private final Context context;
    private final List<ObjectConfigurator> objectConfigurators = new ArrayList<>();

    @SneakyThrows
    public ObjectFactoryImpl(Context context) {
        this.context = context;
        Scanner scanner = context.getConfig().getScanner();
        Set set = scanner.getSubTypesOf(ObjectConfigurator.class);
        Iterator iterator = set.iterator();

        while (iterator.hasNext()) {
            Class<? extends ObjectConfigurator> subClass = (Class<? extends ObjectConfigurator>) iterator.next();
            objectConfigurators.add(subClass.getConstructor().newInstance());
        }
    }

    @SneakyThrows
    @Override
    public <T> T createObject(Class<T> implementation) {
        T obj = create(implementation);
        configure(implementation);
        initialize(implementation, obj);
        return obj;
    }

    private <T> T create(Class <T> implementation) throws Exception {
        return implementation.getConstructor().newInstance();
    }

    private <T> void configure(T object) {
        for (ObjectConfigurator objectConfigurator : objectConfigurators) {
            objectConfigurator.configure(object, context);
        }
    }

    private <T> void initialize (Class<T> implementation, T object) throws Exception {
        for (Method method: implementation.getMethods()) {
            if (method.isAnnotationPresent(InitMethod.class)) {
                method.invoke(object);
            }
        }
    }
}
