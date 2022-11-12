package autopark.parser;

import autopark.entity.Rent;

import java.util.List;
import java.util.stream.Collectors;

import static autopark.utils.MyFileReader.readInfo;

public class RentParser {

    private final String RENT_PATH = "src/main/resources/rents.csv";

    public RentParser() {
    }

    public List<Rent> loadRents() {
        List<String> dataFromFile = readInfo(RENT_PATH);
        return dataFromFile.stream()
                .map(this::createRent)
                .collect(Collectors.toList());
    }

    private Rent createRent(String s) {
        String[] fields = StringParser.convertStringTextToStringFields(s);
        return new Rent(
                Integer.parseInt(fields[0]),
                DataParser.parseStringToDate(fields[1]),
                Double.parseDouble(fields[2])
        );
    }
}
