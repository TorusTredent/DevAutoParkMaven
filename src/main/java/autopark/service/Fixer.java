package autopark.service;

import autopark.entity.Vehicles;

import java.util.Map;

public interface Fixer {

    Map<String, Integer> detectBreaking(Vehicles vehicles);

    void repair(Vehicles vehicles);

    boolean isBroken(Vehicles vehicles);

    default boolean detectAndRepair(Vehicles vehicles) {
        detectBreaking(vehicles);
        if (isBroken(vehicles)) {
            repair(vehicles);
            return true;
        }
        return false;
    }
}
