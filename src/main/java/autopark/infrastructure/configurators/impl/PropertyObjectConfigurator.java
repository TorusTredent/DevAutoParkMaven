package autopark.infrastructure.configurators.impl;

import autopark.infrastructure.configurators.ObjectConfigurator;
import autopark.infrastructure.core.Context;
import autopark.infrastructure.core.annotations.Property;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static autopark.infrastructure.configurators.impl.Validator.getSetterFieldName;

public class PropertyObjectConfigurator  implements ObjectConfigurator {

    private final Map<String, String> properties;

    @SneakyThrows
    public PropertyObjectConfigurator() {
        URL path = this.getClass().getClassLoader().getResource("application.properties");

        if (path == null) {
            throw new FileNotFoundException(String.format("File '%s' not found, ", "application.properties"));
        }

        Stream<String> lines = new BufferedReader(new InputStreamReader(path.openStream())).lines();
        properties = lines.map(line -> line.split("=")).collect(Collectors.toMap(arr -> arr[0], arr -> arr[1]));
    }

    @SneakyThrows
    @Override
    public void configure(Object object, Context context) {
        for (Field field: object.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Property.class)) {
                String setterName = getSetterFieldName(field);
                Method setterMethod = object.getClass().getMethod(setterName, field.getType());
                String value = field.getAnnotation(Property.class).value();
                if (properties.containsKey(value)) {
                    setterMethod.invoke(object, properties.get(value));
                } else {
                    setterMethod.invoke(object, properties.get(field.getName()));
                }
            }
        }
    }
}
