package autopark.infrastructure.configurators.impl;

import autopark.infrastructure.configurators.ObjectConfigurator;
import autopark.infrastructure.core.Context;
import autopark.infrastructure.core.annotations.Autowired;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static autopark.infrastructure.configurators.impl.Validator.getSetterFieldName;

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
