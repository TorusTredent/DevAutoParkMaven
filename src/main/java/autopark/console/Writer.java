package autopark.console;

import autopark.entity.Vehicles;

import java.util.Arrays;
import java.util.List;

public class Writer {

    public static <T> void print(T text) {
        System.out.println(text);
    }

    public static <T> void printError(T text) {
        System.err.println(text);
    }

    public static <T> void printArray(T[] array) {
        Arrays.stream(array).forEach(Writer::print);
    }

    public static <T> void printList(List<T> list) {
        list.forEach(System.out::println);
    }
}
