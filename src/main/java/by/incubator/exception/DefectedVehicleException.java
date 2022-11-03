package by.incubator.exception;

public class DefectedVehicleException extends RuntimeException{

    public DefectedVehicleException() {
        super();
    }

    public DefectedVehicleException(String message) {
        super(message);
    }

    public DefectedVehicleException(String message, Throwable cause) {
        super(message, cause);
    }

    public DefectedVehicleException(Throwable cause) {
        super(cause);
    }

    protected DefectedVehicleException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
