package by.incubator.parser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataParser {

    public static Date parseStringToDate(String date) {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        try {
            return format.parse(date);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Wrong date format", e);
        }
    }
}
