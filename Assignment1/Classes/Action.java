package Assignment1.Classes;

import java.util.Random;

public enum Action {
    UP("ʌ ", 0, -1) {
//        public Action getStationaryAction() {return STATIONARY;}
        public Action getClockwiseAction() {return RIGHT;}
        public Action getAntiClockwiseAction() {return LEFT;}
        public Action getOppositeAction() {return DOWN;}
    },
    DOWN("v ", 0, 1) {
//        public Action getStationaryAction() {return STATIONARY;}
        public Action getClockwiseAction() {return LEFT;}
        public Action getAntiClockwiseAction() {return RIGHT;}
        public Action getOppositeAction() {return UP;}
    },
    LEFT("< ", -1, 0) {
//        public Action getStationaryAction() {return STATIONARY;}
        public Action getClockwiseAction() {return UP;}
        public Action getAntiClockwiseAction() {return DOWN;}
        public Action getOppositeAction() {return RIGHT;}

    },
    RIGHT("> ", 1, 0) {
//        public Action getStationaryAction() {return STATIONARY;}
        public Action getClockwiseAction() {return DOWN;}
        public Action getAntiClockwiseAction() {return UP;}
        public Action getOppositeAction() {return LEFT;}

    };

    private String strRep;
    private int xChange, yChange;
    Action(String strRep, int xChange, int yChange){
        this.strRep = strRep;
        this.xChange = xChange;
        this.yChange = yChange;
    }

    @Override
    public String toString() {
        return this.strRep;
    }

    public int getActionX() {
        return this.xChange;
    }

    public int getActionY() {
        return this.yChange;
    }

    public static final Action[] ACTIONS_LIST = values();
    public static final int NO_OF_ACTIONS = ACTIONS_LIST.length;

    public static Action getRandAction() {
        return ACTIONS_LIST[generateRandomInt(0, NO_OF_ACTIONS-1)];
    }

    public static int generateRandomInt(int min, int max) {
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

//    public abstract Action getStationaryAction();

    public abstract Action getClockwiseAction();

    public abstract Action getAntiClockwiseAction();

    public abstract Action getOppositeAction();
}
