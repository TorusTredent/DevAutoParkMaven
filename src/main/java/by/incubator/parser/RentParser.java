package by.incubator.parser;

import by.incubator.entity.Rent;

import java.util.List;
import java.util.stream.Collectors;

import static by.incubator.parser.DataParser.parseStringToDate;
import static by.incubator.parser.StringParser.convertStringTextToStringFields;
import static by.incubator.utils.MyFileReader.readInfo;

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
        String[] fields = convertStringTextToStringFields(s);
        return new Rent(
                Integer.parseInt(fields[0]),
                parseStringToDate(fields[1]),
                Double.parseDouble(fields[2])
        );
    }
}
