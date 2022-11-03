package by.incubator.collection;

import by.incubator.console.Writer;
import by.incubator.entity.Rent;
import by.incubator.entity.engine.DieselEngine;
import by.incubator.entity.engine.ElectricEngine;
import by.incubator.entity.engine.GasolineEngine;
import by.incubator.entity.engine.Startable;
import by.incubator.entity.enums.Color;
import by.incubator.entity.vehicle.Vehicle;
import by.incubator.entity.vehicle.VehicleType;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static by.incubator.console.Writer.printError;

public class VehicleCollection {

    public List<VehicleType> vehicleTypeList;
    public List<Vehicle> vehicleList;
    public List<Rent> rentList;

    private static final String REGEX_FOR_STRING_NUMBERS = "(\")(\\d+)(,)(\\d+)(\")";
    private static final String TEXT_HEADER = "\n\n%5s%10s%20s%20s%25s%15s%15s%15s%15s%12s%15s\n";
    private static final String TEXT_LINES = "%-8d%-10s%-29s%-20s%-22d%-12d%-15d%-15s%-15.2f%-15.2f%-15.2f\n";
    private static final String TEXT_TOTAL = "%-161s%.2f";
    private static final String PREFIX_FILE_URL = "src/main/resources/";
    private static final String POSTFIX_FILE_URL = ".csv";
    public VehicleCollection(String rents, String types, String vehicles) {
        rents = PREFIX_FILE_URL + rents + POSTFIX_FILE_URL;
        types = PREFIX_FILE_URL + types + POSTFIX_FILE_URL;
        vehicles = PREFIX_FILE_URL + vehicles + POSTFIX_FILE_URL;
        try {
            this.vehicleTypeList = loadTypes(types);
            this.rentList = loadRents(rents);
            this.vehicleList = loadVehicles(vehicles);
        } catch (IllegalArgumentException e) {
            printError(e);
        }
    }

    public List<VehicleType> getVehicleTypeList() {
        return vehicleTypeList;
    }

    public List<Vehicle> getVehicleList() {
        return vehicleList;
    }

    public List<Rent> getRentList() {
        return rentList;
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


    private List<Rent> loadRents(String rents) throws IllegalArgumentException {
        List<String> dataFromFile = readFromFile(rents);
        return dataFromFile.stream()
                .map(this::createRent)
                .collect(Collectors.toList());
    }

    private List<VehicleType> loadTypes(String rents) throws IllegalArgumentException {
        List<String> dataFromFile = readFromFile(rents);
        return dataFromFile.stream()
                .map(this::createType)
                .collect(Collectors.toList());
    }

    private List<Vehicle> loadVehicles(String vehicles) throws IllegalArgumentException {
        List<String> dataFromFile = readFromFile(vehicles);
        return dataFromFile.stream()
                .map(this::createVehicle)
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

    private Vehicle createVehicle(String s) {
        String[] fields = convertStringTextToStringFields(s);
        return new Vehicle(
                Integer.parseInt(fields[0]),
                vehicleTypeList.get(Integer.parseInt(fields[1]) - 1),
                fields[2],
                fields[3],
                Integer.parseInt(fields[4]),
                Integer.parseInt(fields[5]),
                Integer.parseInt(fields[6]),
                Color.valueOf(fields[7].toUpperCase()),
                getEngine(fields),
                getRentsFromList(Integer.parseInt(fields[0]))
        );
    }

    private List<Rent> getRentsFromList(int id) {
        return rentList.stream()
                .filter(rent -> rent.getId() == id)
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

    private List<String> readFromFile(String fileName) {
        List<String> list = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            String fileContent = null;
            while ((fileContent = bufferedReader.readLine()) != null) {
                list.add(fileContent);
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("Cant read data from file", e);
        }
        return list;
    }

    private String[] convertStringTextToStringFields(String text) {
        return text.replaceAll(REGEX_FOR_STRING_NUMBERS, "$2" + "." + "$4").split(",");
    }

    private Startable getEngine(String[] fields) {
        switch (fields[8]) {
            case "Gasoline": {
                return createGasolineEngine(fields);
            }
            case "Electrical": {
                return createElectricalEngine(fields);
            }
            case "Diesel": {
                return createDieselEngine(fields);
            }
        }
        throw new IllegalArgumentException("Wrong engine type");
    }

    private GasolineEngine createGasolineEngine(String[] fields) {
        return new GasolineEngine(
                Double.parseDouble(fields[9]),
                Double.parseDouble(fields[10]),
                Double.parseDouble(fields[11])
        );
    }

    private ElectricEngine createElectricalEngine(String[] fields) {
        return new ElectricEngine(
                Double.parseDouble(fields[9]),
                Double.parseDouble(fields[10])
        );
    }

    private DieselEngine createDieselEngine(String[] fields) {
        return new DieselEngine(
                Double.parseDouble(fields[9]),
                Double.parseDouble(fields[10]),
                Double.parseDouble(fields[11])
        );
    }

    private Date parseStringToDate(String date) {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        try {
            return format.parse(date);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Wrong date format", e);
        }
    }
}
