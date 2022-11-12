package autopark.collection;

import autopark.console.Writer;
import autopark.entity.vehicle.Vehicle;
import autopark.entity.vehicle.VehicleType;
import autopark.infrastructure.core.annotations.Autowired;
import autopark.infrastructure.core.annotations.InitMethod;
import autopark.parser.ParserVehicleFromFile;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class VehicleCollection {

    private List<VehicleType> vehicleTypeList = new ArrayList<>();
    private List<Vehicle> vehicleList = new ArrayList<>();

    private static final String TEXT_HEADER = "\n\n%5s%10s%20s%20s%25s%15s%15s%15s%15s%12s%15s\n";
    private static final String TEXT_LINES = "%-8d%-10s%-29s%-20s%-22d%-12d%-15d%-15s%-15.2f%-15.2f%-15.2f\n";
    private static final String TEXT_TOTAL = "%-161s%.2f";

    @Autowired
    private ParserVehicleFromFile parser;

    public VehicleCollection() {
    }

    @InitMethod
    public void init() {
        vehicleList = parser.loadVehicles();
        vehicleTypeList = parser.getVehicleTypeList();
    }

    public List<VehicleType> getVehicleTypeList() {
        return vehicleTypeList;
    }

    public void setVehicleTypeList(List<VehicleType> vehicleTypeList) {
        this.vehicleTypeList = vehicleTypeList;
    }

    public List<Vehicle> getVehicleList() {
        return vehicleList;
    }

    public void setVehicleList(List<Vehicle> vehicleList) {
        this.vehicleList = vehicleList;
    }

    public ParserVehicleFromFile getParser() {
        return parser;
    }

    public void setParser(ParserVehicleFromFile parser) {
        this.parser = parser;
    }

    public void insert(int index, Vehicle vehicle) {
        if (index >= vehicleList.size() || index < 0) {
            vehicleList.add(vehicle);
        } else {
            vehicleList.add(index, vehicle);
        }
    }

    public int delete(int index) {
        if (index >= vehicleList.size() || index < 0) {
            return -1;
        } else {
            vehicleList.remove(index);
            return index;
        }
    }

    public double sumTotalProfit() {
        return vehicleList.stream()
                .mapToDouble(Vehicle::getTotalProfit)
                .sum();
    }

    public void display() {
        Writer.printfHeader(TEXT_HEADER);
        for (Vehicle vehicle : vehicleList) {;
            Writer.printfVehiclesInLine(TEXT_LINES, vehicle);
        }
        Writer.printfTotal(TEXT_TOTAL, sumTotalProfit());
    }

    public void sort(Comparator<Vehicle> comparator) {
        vehicleList.sort(comparator);
    }
}
