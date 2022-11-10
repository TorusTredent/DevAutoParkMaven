package autopark.infrastructure.dto.impl;

import autopark.infrastructure.core.annotations.InitMethod;
import autopark.infrastructure.core.annotations.Property;
import autopark.infrastructure.dto.ConnectionFactory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;

@Getter
@Setter
@NoArgsConstructor
public class ConnectionFactoryImpl implements ConnectionFactory {

    @Property("url")
    private String url;
    @Property("username")
    private String username;
    @Property("password")
    private String password;

    private Connection connection;

    @SneakyThrows
    @InitMethod
    public void initConnection() {
        connection = DriverManager.getConnection(url, username, password);
    }

    @Override
    public Connection getConnection() {
        return connection;
    }
}
