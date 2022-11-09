package by.incubator.parser;

import by.incubator.entity.Rent;
import by.incubator.entity.TechnicalSpecialist;
import by.incubator.entity.engine.DieselEngine;
import by.incubator.entity.engine.ElectricEngine;
import by.incubator.entity.engine.GasolineEngine;
import by.incubator.entity.engine.Startable;
import by.incubator.entity.enums.Color;
import by.incubator.entity.vehicle.Vehicle;
import by.incubator.entity.vehicle.VehicleType;
import by.incubator.infrastructure.core.annotations.Autowired;
import by.incubator.infrastructure.core.annotations.InitMethod;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static by.incubator.console.Writer.printError;
import static by.incubator.parser.DataParser.parseStringToDate;
import static by.incubator.parser.StringParser.convertStringTextToStringFields;
import static by.incubator.utils.MyFileReader.readInfo;

public class ParserVehicleFromFile {

    private VehicleTypeParser vehicleTypeParser;
    private RentParser rentParser;
    private List<VehicleType> vehicleTypeList;
    private List<Rent> rentList;
    private final String VEHICLES_PATH = "src/main/resources/vehicles.csv";

    @Autowired
    TechnicalSpecialist technicalSpecialist;

    public ParserVehicleFromFile() {
    }

    @InitMethod
    public void init() {
        vehicleTypeParser = new VehicleTypeParser();
        rentParser = new RentParser();
        vehicleTypeList = vehicleTypeParser.loadTypes();
        rentList = rentParser.loadRents();
    }

    public List<VehicleType> getVehicleTypeList() {
        return vehicleTypeList;
    }

    public List<Rent> getRentList() {
        return rentList;
    }

    public List<Vehicle> loadVehicles(){
        List<String> dataFromFile = readInfo(VEHICLES_PATH);
        return dataFromFile.stream()
                .map(this::createVehicle)
                .collect(Collectors.toList());
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
}
