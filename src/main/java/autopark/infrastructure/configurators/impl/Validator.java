package autopark.infrastructure.configurators.impl;

import java.lang.reflect.Field;

public class Validator {

    public static String getSetterFieldName(Field field) {
        return "set" + String.valueOf(field.getName().charAt(0)).toUpperCase() + field.getName().substring(1);
    }
}
