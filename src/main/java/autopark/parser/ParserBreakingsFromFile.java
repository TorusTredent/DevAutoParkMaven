package autopark.parser;

import autopark.infrastructure.core.annotations.InitMethod;

import java.util.List;

import static autopark.utils.MyFileReader.readInfo;

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
