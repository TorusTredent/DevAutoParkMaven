package autopark;

import autopark.console.Writer;
import autopark.infrastructure.core.impl.ApplicationContext;
import autopark.service.BadMechanicService;
import autopark.service.Workroom;
import autopark.collection.VehicleCollection;
import autopark.entity.Fixer;
import autopark.entity.vehicle.Vehicle;
import org.postgresql.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "root");
            Writer.print(connection.getMetaData().getDriverName());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}