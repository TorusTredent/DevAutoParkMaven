package autopark.console;

import autopark.entity.vehicle.Vehicle;

import java.util.List;

public class Writer {

    public static <T> void print(T text) {
        System.out.println(text);
    }

    public static <T> void printError(T text) {
        System.err.println(text);
    }

    public static <T> void printArray(T[] array) {
        for (T item : array) {
            print(item);
        }
    }

    public static void printfHeader(String text) {
        System.out.printf(text, "Id", "Type", "ModelName", "Number", "Weight (kg)",
                                "Year", "Mileage", "Color", "Income", "Tax", "Profit");
    }

    public static void printfVehiclesInLine(String text, Vehicle vehicle) {
        System.out.format(text, vehicle.getId(), vehicle.getVehicleType().getName(), vehicle.getModelName(),
                vehicle.getRegNumber(), vehicle.getWeight(), vehicle.getManufactureYear(), vehicle.getMileage(),
                vehicle.getColor(), vehicle.getTotallncom(), vehicle.getCalcTaxPerMonth(), vehicle.getTotalProfit());
    }

    public static void printfTotal(String textTotal, double sumTotalProfit) {
        System.out.printf(textTotal, "Total", sumTotalProfit);
    }

    public static <T> void printList(List<T> list) {
        list.forEach(System.out::println);
    }
}
