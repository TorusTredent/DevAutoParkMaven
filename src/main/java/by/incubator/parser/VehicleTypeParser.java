package by.incubator.parser;

import by.incubator.entity.vehicle.VehicleType;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static by.incubator.parser.StringParser.convertStringTextToStringFields;
import static by.incubator.utils.MyFileReader.readInfo;

public class VehicleTypeParser {

    private final String TYPES_PATH = "src/main/resources/types.csv";

    public VehicleTypeParser() {
    }

    public List<VehicleType> loadTypes() {
        List<String> dataFromFile = readInfo(TYPES_PATH);
        return dataFromFile.stream()
                .map(this::createType)
                .collect(Collectors.toList());
    }

    private VehicleType createType(String s) {
        String[] fields = convertStringTextToStringFields(s);
        return new VehicleType(
                Integer.parseInt(fields[0]),
                fields[1],
                Double.parseDouble(fields[2])
        );
    }
}
