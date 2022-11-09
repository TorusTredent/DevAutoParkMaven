package autopark.utils;

import autopark.console.Writer;
import autopark.entity.vehicle.Vehicle;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Map;

public class MyFileWriter {

    public static void printToFile(String fileName, Vehicle vehicle, Map<String, Integer> map) {
        String line = wrapInLine(vehicle, map);
        try {
            Files.write(Paths.get(fileName), line.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
        } catch (IOException e) {
            Writer.printError(e.getMessage());
        }
    }

    public static void writeListToFile(List<String> list, String fileName) {
        try {
            Files.write(Paths.get(fileName), list, StandardCharsets.UTF_8);
        } catch (IOException e) {
            Writer.printError(e.getMessage());
        }
    }


    private static String wrapInLine(Vehicle vehicle, Map<String, Integer> map) {
        String line = String.valueOf(vehicle.getId());
        for (Map.Entry<String, Integer> entry: map.entrySet()) {
            line = line + ", " + entry.getKey() + ", " + entry.getValue();
        }
        line = line + "\n";
        return line;
    }
}
