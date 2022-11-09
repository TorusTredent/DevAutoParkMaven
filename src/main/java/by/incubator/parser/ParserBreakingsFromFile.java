package by.incubator.parser;

import by.incubator.infrastructure.core.annotations.InitMethod;
import by.incubator.utils.MyFileReader;

import java.util.List;

import static by.incubator.utils.MyFileReader.readInfo;

public class ParserBreakingsFromFile {

    private String ordersFile;

    public ParserBreakingsFromFile() {
    }

    @InitMethod
    public void init() {
        ordersFile = "src/main/resources/orders.csv";
    }

    public List<String> getOrders() {
        return readInfo(ordersFile);
    }
}
