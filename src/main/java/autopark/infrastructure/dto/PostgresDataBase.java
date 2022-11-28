package autopark.infrastructure.dto;

import autopark.exception.BadFieldTypeException;
import autopark.exception.NoSuchAnnotation;
import autopark.exception.NoSuchCorrectFieldException;
import autopark.infrastructure.core.Context;
import autopark.infrastructure.core.annotations.Autowired;
import autopark.infrastructure.core.annotations.InitMethod;
import autopark.infrastructure.dto.annotations.Column;
import autopark.infrastructure.dto.annotations.ID;
import autopark.infrastructure.dto.annotations.Table;
import autopark.infrastructure.dto.enums.SqlFieldType;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import static autopark.console.Writer.printError;
import static autopark.infrastructure.configurators.impl.Validator.getSetterFieldName;

@Getter
@Setter
public class PostgresDataBase {

    @Autowired
    private ConnectionFactory connectionFactory;

    @Autowired
    private Context context;

    private Connection connection;
    private Statement statement;
    private Map<String, String> classToSql = new HashMap<>();
    private Map<String, String> insertPatternByClass = new HashMap<>();
    private Map<String, String> insertByClassPattern = new HashMap<>();

    private static final String CREATE_TABLE_SQL_PATTERN =
            "CREATE TABLE IF NOT EXISTS %s (\n" +
                    "%s SERIAL PRIMARY KEY, " +
                    "%S\n);";
    private static final String INSERT_SQL_PATTERN =
            "INSERT INTO %s(%s)\n" +
                    "VALUES (%s)\n" +
                    "RETURNING %s ;";

    public PostgresDataBase() {
    }

    @InitMethod
    public void init() {
        connection = connectionFactory.getConnection();
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            printError(e.getMessage());
        }

        SqlFieldType[] sqlFieldTypes = SqlFieldType.values();

        Arrays.stream(sqlFieldTypes)
                .forEach(x -> classToSql.put(x.getType().getName(), x.getSqlType()));
        Arrays.stream(sqlFieldTypes)
                .forEach(x -> insertPatternByClass.put(x.getType().getName(), x.getInsertPattern()));

        Set<Class<?>> entities = context.getConfig().getScanner().getReflections().getTypesAnnotatedWith(Table.class);

        validateEntities(entities);
        createTablesIfNotExists(entities);

        initInsertByClass(entities);
    }

    public void save(Object obj) {
        String valuesLine = getValuesLine(obj);
        String sql = String.format(insertByClassPattern.get(obj.getClass().getName()), valuesLine);
        try {
            statement.execute(sql);
        } catch (SQLException e) {
            printError(e.getMessage());
        }
    }

    public <T> Optional<T> get(Long id, Class<T> clazz) {
        if (!clazz.isAnnotationPresent(Table.class)) {
            throw new NoSuchAnnotation("This class hasn't @Table annotation");
        }

        String sql = "SELECT * FROM " + clazz.getAnnotation(Table.class).name() + " WHERE " + findIdField(clazz) + " = " + id;

        Optional optional = null;
        try {
            ResultSet rs = statement.executeQuery(sql);
            if (rs.next()) {
                optional = Optional.of(createObject(rs, clazz));
            }
            rs.close();
        } catch (SQLException e) {
            printError(e.getMessage());
        }
        return optional;
    }

    public <T> List<T> getAll(Class<T> clazz) {
        if (!clazz.isAnnotationPresent(Table.class)) {
            throw new NoSuchAnnotation("This class hasn't @Table annotation");
        }

        String tableName = clazz.getAnnotation(Table.class).name();
        String sql = "SELECT * FROM " + tableName;

        List<T> list = new ArrayList<>();
        try {
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                list.add(createObject(rs, clazz));
            }
            rs.close();
        } catch (SQLException e) {
            printError(e.getMessage());
        }
        return list;
    }

    @SneakyThrows
    private <T> T createObject(ResultSet rs, Class<T> clazz) {
        T o = clazz.getConstructor().newInstance();

        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(Column.class) || field.isAnnotationPresent(ID.class)) {
                String setterName = getSetterFieldName(field);
                Method setterMethod = o.getClass().getMethod(setterName, field.getType());
                invokeSetterMethodByFieldType(setterMethod, field, o, rs);
            }
        }

        for (Method method : clazz.getMethods()) {
            if (method.isAnnotationPresent(InitMethod.class)) {
                method.invoke(o);
            }
        }
        return o;
    }

    @SneakyThrows
    private void invokeSetterMethodByFieldType(Method setterMethod, Field field, Object o, ResultSet rs) {
        switch(field.getType().toString()) {
            case "class java.lang.Integer" : {
                setterMethod.invoke(o, rs.getInt(field.getName()));
            }
            case "class java.lang.Long" : {
                setterMethod.invoke(o, rs.getLong(field.getName()));
            }
            case "class java.lang.Double" : {
                setterMethod.invoke(o, rs.getDouble(field.getName()));
            }
            case "class java.lang.String" : {
                setterMethod.invoke(o, rs.getString(field.getName()));
            }
            case "class java.util.Date" : {
                setterMethod.invoke(o, rs.getDate(field.getName()));
            }
        }
    }

    private String getValuesLine(Object o) {
        Field[] fields = o.getClass().getDeclaredFields();
        StringBuilder stringBuilder = new StringBuilder("");

        for (Field field : fields) {
            if (field.isAnnotationPresent(Column.class)) {
                try {
                    Method method = o.getClass().getMethod(getSetterFieldName(field));
                    try {
                        stringBuilder.append("'").append(method.invoke(o)).append("'").append(", ");
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        printError(e.getMessage());
                    }
                } catch (NoSuchMethodException e) {
                    printError(e.getMessage());
                }
            }
        }
        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length() - 1);
        return String.valueOf(stringBuilder);
    }

    private void validateEntities(Set<Class<?>> entities) {
        for (Class<?> entity : entities) {
            Class<?> clazz = entity;
            validateClass(clazz);
        }
    }

    private void validateClass(Class<?> clazz) {
        checkIfExistsLongFieldWithIdAnnotation(clazz);
        checkIfFieldsHaveCorrectColumnNamesAndTypes(clazz);
    }

    private void checkIfExistsLongFieldWithIdAnnotation(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        if (Arrays.stream(fields)
                .filter(field -> field.isAnnotationPresent(ID.class))
                .anyMatch(field -> field.getType() == Long.class)) {
            return;
        }
        throw new NoSuchCorrectFieldException("No field with ID-annotation/type Long in class " + clazz.getName());
    }

    private void checkIfFieldsHaveCorrectColumnNamesAndTypes(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.getType().isPrimitive()) {
                throw new BadFieldTypeException("There is primitive data type field " + field + " in class " + clazz.getName());
            }
        }
    }

    private void createTablesIfNotExists(Set<Class<?>> entities) {
        for (Class clazz : entities) {
            createTable(clazz);
        }
    }

    private void createTable(Class<?> clazz) {
        String tableName = clazz.getAnnotation(Table.class).name();
        String idField = findIdField(clazz);
        String fields = String.valueOf(createFieldsLine(clazz));

        String sql = String.format(CREATE_TABLE_SQL_PATTERN, tableName, idField, fields);
        try {
            statement.execute(sql);
        } catch (SQLException e) {
            printError(e.getMessage());
        }
    }

    private String findIdField(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        return Arrays.stream(fields)
                .filter(field -> field.isAnnotationPresent(ID.class))
                .findFirst()
                .map(Field::getName).orElse(null);
    }

    private StringBuilder createFieldsLine(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        StringBuilder fieldsLine = new StringBuilder("");

        Arrays.stream(fields)
                .filter(field -> field.isAnnotationPresent(Column.class))
                .map(field -> field.getName() + " " +
                        classToSql.get(field.getType().getName()) + " " +
                        checkNullable(field) + " " +
                        checkUnique(field) + ", ")
                .forEach(fieldsLine::append);

        fieldsLine.delete(fieldsLine.length() - 2, fieldsLine.length() - 1);
        return fieldsLine;
    }

    private String checkNullable(Field field) {
        return field.getAnnotation(Column.class).nullable() ? "NOT NULL" : "";
    }

    private String checkUnique(Field field) {
        return field.getAnnotation(Column.class).unique() ? "UNIQUE" : "";
    }

    private void initInsertByClass(Set<Class<?>> entities) {
        entities.forEach(this::putInsertPatternToMap);
    }

    private void putInsertPatternToMap(Class<?> clazz) {
        String tableName = clazz.getAnnotation(Table.class).name();
        String insertField = String.valueOf(createFieldsLineForInsert(clazz));
        String idFieldName = findIdField(clazz);

        String sql = String.format(INSERT_SQL_PATTERN, tableName, insertField, "%s", idFieldName);

        insertByClassPattern.put(clazz.getName(), sql);
    }

    private StringBuilder createFieldsLineForInsert(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        StringBuilder fieldsLine = new StringBuilder("");

        Arrays.stream(fields)
                .filter(field -> field.isAnnotationPresent(Column.class))
                .map(field -> field.getName() + ", ")
                .forEach(fieldsLine::append);

        fieldsLine.delete(fieldsLine.length() - 2, fieldsLine.length() - 1);

        return fieldsLine;
    }
}
