package Assignment1.Classes;
import Assignment1.ExternalMethods.RepeatedFunctions;

import java.util.Random;

import static Assignment1.ExternalMethods.RepeatedFunctions.generateRandomInt;

/**
 * Action class contains the transition probabilities and the actions that are available for the agents to use
 */
public enum Action {
    UP("ʌ ", 0, -1) {
        public Action getClockwiseAction() {return RIGHT;}
        public Action getAntiClockwiseAction() {return LEFT;}
    },
    DOWN("v ", 0, 1) {
        public Action getClockwiseAction() {return LEFT;}
        public Action getAntiClockwiseAction() {return RIGHT;}
    },
    LEFT("< ", -1, 0) {
        public Action getClockwiseAction() {return UP;}
        public Action getAntiClockwiseAction() {return DOWN;}

    },
    RIGHT("> ", 1, 0) {
        public Action getClockwiseAction() {return DOWN;}
        public Action getAntiClockwiseAction() {return UP;}

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

    public int getChangeX() {
        return this.xChange;
    }

    public int getChangeY() {
        return this.yChange;
    }

    public static final Action[] ACTIONS_LIST = values();
    public static final int NO_OF_ACTIONS = ACTIONS_LIST.length;

    public static Action getRandAction() {
        return ACTIONS_LIST[generateRandomInt(0, NO_OF_ACTIONS-1)];
    }


    public abstract Action getClockwiseAction();

    public abstract Action getAntiClockwiseAction();

}
