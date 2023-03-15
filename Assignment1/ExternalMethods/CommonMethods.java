package Assignment1.ExternalMethods;

import java.util.Random;

public class CommonMethods {
    static String convertToUpperCase(String msg, boolean toUpperCase) {
        if (toUpperCase) {
            return msg.toUpperCase();
        }
        return msg;
    }

    public static int generateRandomInt(int min, int max) {
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
}
