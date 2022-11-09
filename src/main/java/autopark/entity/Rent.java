package autopark.entity;

import java.util.Date;

public class Rent {

    private int id;
    private Date rentalDate;
    private double rentalPrice;

    public Rent() {
    }

    public Rent(int id, Date rentalDate, double rentalPrice) {
        this.id = id;
        this.rentalDate = rentalDate;
        this.rentalPrice = rentalPrice;
    }

    public Rent(Date rentalDate, double rentalPrice) {
        this.rentalDate = rentalDate;
        this.rentalPrice = rentalPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(Date rentalDate) {
        this.rentalDate = rentalDate;
    }

    public double getRentalPrice() {
        return rentalPrice;
    }

    public void setRentalPrice(double rentalPrice) {
        this.rentalPrice = rentalPrice;
    }

    @Override
    public String toString() {
        return  id + ", " +
                rentalDate + ", " +
                rentalPrice;
    }
}
