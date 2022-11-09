package by.incubator.service;

import by.incubator.entity.Fixer;
import by.incubator.entity.vehicle.Vehicle;
import by.incubator.infrastructure.core.annotations.Autowired;
import by.incubator.parser.ParserBreakingsFromFile;
import by.incubator.utils.MyFileWriter;
import by.incubator.utils.Randomizer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static by.incubator.utils.MyFileWriter.writeListToFile;

public class MechanicService implements Fixer {

    private static final String[] DETAILS = {"Фильтр", "Втулка", "Вал", "Ось", "Свеча", "Масло", "ГРМ", "ШРУС"};
    private static final int MAX_NUMBER_OF_BROKEN_DETAILS = 8;
    private static final int MAX_NUMBER_OF_BREAKS = 4;
    private static final String ORDERS_FILE_NAME = "src/main/resources/orders.csv";
    List<String> orders;

    @Autowired
    private ParserBreakingsFromFile ordersParser;

    public MechanicService() {
    }

    public ParserBreakingsFromFile getOrdersParser() {
        return ordersParser;
    }

    public void setOrdersParser(ParserBreakingsFromFile ordersParser) {
        this.ordersParser = ordersParser;
    }

    public List<String> getOrders() {
        return orders;
    }

    public void setOrders(List<String> orders) {
        this.orders = orders;
    }

    @Override
    public Map<String, Integer> detectBreaking(Vehicle vehicle) {
        Map<String, Integer> map = new HashMap<>();
        int numberOfBrokenDetails = Randomizer.getRandomNumber(MAX_NUMBER_OF_BROKEN_DETAILS);
        for (int i = 0; i < numberOfBrokenDetails; i++) {
            setMapOfBrokenDetails(map);
        }
        if (!map.isEmpty()) MyFileWriter.printToFile(ORDERS_FILE_NAME, vehicle, map);
        return map;
    }

    @Override
    public boolean repair(Vehicle vehicle) {
        orders = ordersParser.getOrders();
        String regex = vehicle.getId() + ".*";
        orders.removeIf(i -> i.matches(regex));
        writeListToFile(orders, ORDERS_FILE_NAME);
        return true;
    }

    @Override
    public boolean isBroken(Vehicle vehicle) {
        return getLineFromOrderFile(vehicle) != null;
    }

    public int getSumNumberOfBreaks(Vehicle vehicle) {
        int sum = 0;
        Pattern pattern = Pattern.compile("\\s\\d");
        Matcher matcher = pattern.matcher(getLineFromOrderFile(vehicle));
        while (matcher.find()) {
            sum += Integer.parseInt(matcher.group().trim());
        }
        return sum;
    }


    private String getLineFromOrderFile(Vehicle vehicle) {
        orders = ordersParser.getOrders();
        String regex = vehicle.getId() + ".*";

        return orders.stream()
                .filter(x -> x.matches(regex))
                .findFirst().orElse(null);
    }

    private void setMapOfBrokenDetails(Map<String, Integer> map) {
        while (true) {
            String randomStringFromArray = Randomizer.getRandomStringFromArray(DETAILS);
            int numberOfBreaks = Randomizer.getRandomNumber(MAX_NUMBER_OF_BREAKS);
            Integer value = map.get(randomStringFromArray);
            if (value == null) {
                map.put(randomStringFromArray, numberOfBreaks);
                break;
            }
        }
    }
}
