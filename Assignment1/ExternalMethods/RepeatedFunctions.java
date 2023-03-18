package Assignment1.ExternalMethods;

import java.util.Random;

/**
 * RepeatedFunctions class is used to store functions that are commonly used in
 * most classes (eg. generateRandomInt function is used in Action class and State class)
 */
public class RepeatedFunctions {

    public static int generateRandomInt(int min, int max) {
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    static String toUpperCase(String msg, boolean toUpperCase) {
        if (toUpperCase) {
            return msg.toUpperCase();
        }
        return msg;
    }


}
