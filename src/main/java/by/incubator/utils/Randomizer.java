package by.incubator.utils;

import java.util.Random;

public class Randomizer {

    private static final Random random = new Random();

    public static int getRandomNumber(int max) {
        return random.nextInt(max);
    }

    public static String getRandomStringFromArray(String[] array) {
        return array[random.nextInt(array.length)];
    }
}
