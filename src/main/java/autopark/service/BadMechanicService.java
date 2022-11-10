package autopark.service;

import autopark.entity.Vehicles;

import java.util.HashMap;
import java.util.Map;

public class BadMechanicService implements Fixer {

    public BadMechanicService() {
    }

    @Override
    public Map<String, Integer> detectBreaking(Vehicles vehicles) {
        Map<String, Integer> map = new HashMap<>();
        return map;
    }

    @Override
    public void repair(Vehicles vehicles) {

    }

    @Override
    public boolean detectAndRepair(Vehicles vehicles) {
        return false;
    }

    @Override
    public boolean isBroken(Vehicles vehicles) {
        return false;
    }
}
