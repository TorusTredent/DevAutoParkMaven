package autopark.parser;

import autopark.entity.engine.ElectricEngine;
import autopark.entity.engine.GasolineEngine;
import autopark.entity.enums.Color;
import autopark.entity.vehicle.VehicleType;
import autopark.entity.Rent;
import autopark.entity.TechnicalSpecialist;
import autopark.entity.engine.DieselEngine;
import autopark.entity.engine.Startable;
import autopark.entity.vehicle.Vehicle;
import autopark.infrastructure.core.annotations.Autowired;
import autopark.infrastructure.core.annotations.InitMethod;

import java.util.List;
import java.util.stream.Collectors;

import static autopark.utils.MyFileReader.readInfo;

public class ParserVehicleFromFile {

    private VehicleTypeParser vehicleTypeParser;
    private RentParser rentParser;
    private List<VehicleType> vehicleTypeList;
    private List<Rent> rentList;
    private final String VEHICLES_PATH = "src/main/resources/vehicles.csv";

    @Autowired
    private TechnicalSpecialist technicalSpecialist;

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

    public TechnicalSpecialist getTechnicalSpecialist() {
        return technicalSpecialist;
    }

    public void setTechnicalSpecialist(TechnicalSpecialist technicalSpecialist) {
        this.technicalSpecialist = technicalSpecialist;
    }

    public VehicleTypeParser getVehicleTypeParser() {
        return vehicleTypeParser;
    }

    public void setVehicleTypeParser(VehicleTypeParser vehicleTypeParser) {
        this.vehicleTypeParser = vehicleTypeParser;
    }

    public RentParser getRentParser() {
        return rentParser;
    }

    public void setRentParser(RentParser rentParser) {
        this.rentParser = rentParser;
    }

    public void setVehicleTypeList(List<VehicleType> vehicleTypeList) {
        this.vehicleTypeList = vehicleTypeList;
    }

    public void setRentList(List<Rent> rentList) {
        this.rentList = rentList;
    }

    public List<Vehicle> loadVehicles(){
        List<String> dataFromFile = readInfo(VEHICLES_PATH);
        return dataFromFile.stream()
                .map(this::createVehicle)
                .collect(Collectors.toList());
    }


    private Vehicle createVehicle(String s) {
        String[] fields = StringParser.convertStringTextToStringFields(s);

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
