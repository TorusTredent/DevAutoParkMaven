package by.incubator.infrastructure.configurators.impl;

import by.incubator.infrastructure.core.annotations.Autowired;
import by.incubator.infrastructure.configurators.ObjectConfigurator;
import by.incubator.infrastructure.core.Context;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static by.incubator.infrastructure.configurators.impl.Validator.getSetterFieldName;

public class AutowiredObjectConfigurator implements ObjectConfigurator {
    @SneakyThrows
    @Override
    public void configure(Object t, Context context) {
        for (Field field : t.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Autowired.class)) {
                String setterName = getSetterFieldName(field);
                Method setterMethod = t.getClass().getMethod(setterName, field.getType());
                setterMethod.invoke(t, context.getObject(field.getType()));
            }
        }
    }
}
