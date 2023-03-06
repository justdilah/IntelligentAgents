package Assignment1;

import java.util.Random;
public enum State {
    START("S"),
    EMPTY(" "),


    PENALTY("--"),
    REWARD("+R"),
    //    REWARD(new String(Character.toChars(0x221A))), // √
    WALL("X");

    private String strRep;
    State(String strRep) {
        this.strRep = strRep;
    }

    @Override
    public String toString() {
        return this.strRep;
    }

    private static final State[] AVAILABLE_STATES = values();
    private static final int NO_OF_STATES = AVAILABLE_STATES.length;

    public static State generateRandState() {
        /** Randomly generates a state with the exception of EnumState.START */
        return AVAILABLE_STATES[generateRandomInt(1, NO_OF_STATES)];
    }

    public static int generateRandomInt(int min, int max) {
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
}
