package autopark.service;

import autopark.entity.Vehicles;
import autopark.infrastructure.core.annotations.Autowired;
import autopark.parser.ParserBreakingsFromFile;
import autopark.utils.MyFileWriter;
import autopark.utils.Randomizer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static autopark.utils.MyFileWriter.printToFile;
import static autopark.utils.MyFileWriter.writeListToFile;

@Setter
@Getter
@NoArgsConstructor
public class MechanicService implements Fixer {

    private static final String[] DETAILS = {"Фильтр", "Втулка", "Вал", "Ось", "Свеча", "Масло", "ГРМ", "ШРУС"};
    private static final int MAX_NUMBER_OF_BROKEN_DETAILS = 8;
    private static final int MAX_NUMBER_OF_BREAKS = 4;
    private static final String ORDERS_FILE_NAME = "src/main/resources/orders.csv";
    private List<String> orders;

    @Autowired
    private ParserBreakingsFromFile ordersParser;

    @Override
    public Map<String, Integer> detectBreaking(Vehicles vehicles) {
        Map<String, Integer> map = new HashMap<>();
        int numberOfBrokenDetails = Randomizer.getRandomNumber(MAX_NUMBER_OF_BROKEN_DETAILS);
        for (int i = 0; i < numberOfBrokenDetails; i++) {
            setMapOfBrokenDetails(map);
        }
        if (!map.isEmpty()) printToFile(ORDERS_FILE_NAME, vehicles, map);
        return map;
    }

    @Override
    public void repair(Vehicles vehicles) {
        orders = ordersParser.getOrders();
        String regex = vehicles.getId() + ".*";
        orders.removeIf(i -> i.matches(regex));
        writeListToFile(orders, ORDERS_FILE_NAME);
    }

    @Override
    public boolean isBroken(Vehicles vehicles) {
        return getLineFromOrderFile(vehicles) != null;
    }

    public int getSumNumberOfBreaks(Vehicles vehicles) {
        int sum = 0;
        Pattern pattern = Pattern.compile("\\s\\d");
        Matcher matcher = pattern.matcher(getLineFromOrderFile(vehicles));
        while (matcher.find()) {
            sum += Integer.parseInt(matcher.group().trim());
        }
        return sum;
    }


    private String getLineFromOrderFile(Vehicles vehicles) {
        orders = ordersParser.getOrders();
        String regex = vehicles.getId() + ".*";

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
