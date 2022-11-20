package autopark.infrastructure.core.impl;

import autopark.infrastructure.config.ProxyConfigurator;
import autopark.infrastructure.configurators.ObjectConfigurator;
import autopark.infrastructure.core.Context;
import autopark.infrastructure.core.ObjectFactory;
import autopark.infrastructure.core.Scanner;
import autopark.infrastructure.core.annotations.InitMethod;
import lombok.SneakyThrows;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static autopark.console.Writer.printError;

public class ObjectFactoryImpl implements ObjectFactory {

    private final Context context;
    private final List<ObjectConfigurator> objectConfigurators = new ArrayList<>();
    private final List<ProxyConfigurator> proxyConfigurators = new ArrayList<>();

    @SneakyThrows
    public ObjectFactoryImpl(Context context) {
        this.context = context;
        Scanner scanner = context.getConfig().getScanner();
        initObjectConfig(scanner);
        initProxyConfig(scanner);
    }

    @SneakyThrows
    @Override
    public <T> T createObject(Class<T> implementation) {
        T obj = create(implementation);
        configure(obj);
        initialize(implementation, obj);
        obj = makeProxy(implementation, obj);
        return obj;
    }


    private <T> T create(Class<T> implementation) throws Exception {
        return implementation.getConstructor().newInstance();
    }

    private <T> void configure(T object) {
        for (ObjectConfigurator objectConfigurator : objectConfigurators) {
            objectConfigurator.configure(object, context);
        }
    }

    private <T> void initialize(Class<T> implementation, T object) throws Exception {
        for (Method method : implementation.getMethods()) {
            if (method.isAnnotationPresent(InitMethod.class)) {
                method.invoke(object);
            }
        }
    }

    private void initObjectConfig(Scanner scanner) {
        Set set = scanner.getSubTypesOf(ObjectConfigurator.class);
        Iterator iterator = set.iterator();
        initObject(iterator);
    }

    private void initProxyConfig(Scanner scanner) {
        Set set = scanner.getSubTypesOf(ProxyConfigurator.class);
        Iterator iterator = set.iterator();
        initProxy(iterator);
    }

    private void initObject(Iterator iterator) {
        while (iterator.hasNext()) {
            Class<? extends ObjectConfigurator> subClass = (Class<? extends ObjectConfigurator>) iterator.next();
            try {
                objectConfigurators.add(subClass.getConstructor().newInstance());
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                printError(e.getMessage());
            }
        }
    }

    private void initProxy(Iterator iterator) {
        while (iterator.hasNext()) {
            Class<? extends ProxyConfigurator> subClass = (Class<? extends ProxyConfigurator>) iterator.next();
            try {
                proxyConfigurators.add(subClass.getConstructor().newInstance());
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                printError(e.getMessage());
            }
        }
    }

    private <T> T makeProxy(Class<T> implClass, T object) {
        for (ProxyConfigurator proxyConfigurator : proxyConfigurators) {
            object = (T) proxyConfigurator.makeProxy(object, implClass, context);
        }
        return object;
    }
}
