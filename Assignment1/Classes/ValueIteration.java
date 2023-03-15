package Assignment1.Classes;

import Assignment1.Maze.Grid;
import Assignment1.Maze.GridState;

import java.util.*;


import static Assignment1.Main.Config.*;

public class ValueIteration implements Agent{
    private int rows = 0;
    private int cols = 0;
    private int no_of_iterations = 0;
    private double convergenceCriteria = 0.0;

    private GridState[][] grid = null;
    private UtilityAndAction[][] currActionUtilArr = null;
    private UtilityAndAction[][] newActionUtilArr = null;
    private List<UtilityAndAction[][]> ListOfActionUtilityArrays = new ArrayList<>();

    //eplison - Stopping Criterion (Max change in the value function at each iteration is compared
    // against eplison)
    public ValueIteration(Grid map, double epsilon, double discount) {

        this.rows = map.getRows();
        this.cols = map.getCols();
        this.grid = map.getGridMap();


        //Initialisation
        this.currActionUtilArr = new UtilityAndAction[rows][cols];
        this.newActionUtilArr = new UtilityAndAction[rows][cols];

        //For each block,
        newActionUtilArr = this.generateInitialPolicy();

        double maxChangeInUtility = 0;

        this.convergenceCriteria = epsilon * ((1-discount) / discount);

        this.no_of_iterations = 0;

        double newUtility = 0.0;
        double currUtility = 0.0;


        do {
            this.no_of_iterations++;

            //Copy content of newActionUtilArr to the currActionUtilArr
            this.currActionUtilArr = replicateUtilArray(newActionUtilArr);


            this.ListOfActionUtilityArrays.add(this.currActionUtilArr);
            maxChangeInUtility = 0;

            // loop through all the states of the map
            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < cols; col++) {
                    // Check if GridState is Wall
                    if (!grid[row][col].isVisitable()) {
                        continue;
                    }

                    //Retrieves the optimal action and utility
                    UtilityAndAction chosenUtilityAndAction = getChosenUtilityAndAction(row, col);

                    //retrieves the new utility of the current state
                    newUtility = this.grid[row][col].getStateReward() + (discount * chosenUtilityAndAction.getUtility());
                    currUtility = this.currActionUtilArr[row][col].getUtility();

                    chosenUtilityAndAction.setUtility(newUtility);
                    this.newActionUtilArr[row][col] = chosenUtilityAndAction;

                    if (Math.abs(newUtility - currUtility) > maxChangeInUtility) {
                        maxChangeInUtility = Math.abs(newUtility - currUtility);
                    }
                }
            }

        } while (maxChangeInUtility >= this.convergenceCriteria);
        this.ListOfActionUtilityArrays.add(newActionUtilArr);
    }

    public List<UtilityAndAction[][]> getResults() {
        return this.ListOfActionUtilityArrays;
    }

    public UtilityAndAction[][] getOptimalPolicy() {
        return this.ListOfActionUtilityArrays.get(this.getNoOfIterations());
    }

    public int getNoOfIterations() {
        return this.no_of_iterations;
    }

    public double getConvergenceCriteria() {
        return this.convergenceCriteria;
    }

    //Returns a replicated Util Array
    private UtilityAndAction[][] replicateUtilArray(UtilityAndAction[][] src) {
        return Arrays.stream(src).map(UtilityAndAction[]::clone).toArray(UtilityAndAction[][]::new);
    }

    private UtilityAndAction[][] generateInitialPolicy() {
        UtilityAndAction[][] actionUtilArr = new UtilityAndAction[this.rows][this.cols];

        //At each coordinate, initialise UtilityAndAction
        for (int row = 0; row < this.rows; row++) {
            for (int col = 0; col < this.cols; col++) {
                actionUtilArr[row][col] = new UtilityAndAction();
            }
        }
        return actionUtilArr;
    }

    private UtilityAndAction getChosenUtilityAndAction(int row, int col) {
        List<UtilityAndAction> listOfUtilityAndAction = new ArrayList<>();
        //Goes through the loop of Actions (UP,DOWN,LEFT,RIGHT)
        EnumSet.allOf(Action.class).
                forEach(action -> listOfUtilityAndAction.add(
                                new UtilityAndAction(action, getTransitionStateActionPairUtil(action, row, col))
                        )
                );
        //Retrieve the Max Utility of the actions
        UtilityAndAction chosenUtilityAndAction = Collections.max(listOfUtilityAndAction);
        return chosenUtilityAndAction;
    }

    //retrieves the maximum utility from (expected utility of taking every possible action in state s)
    private double getTransitionStateActionPairUtil(Action action, int row, int col) {
        double actionUtility = 0.000;

        //Calculate all the expected utility of all possible actions
        double intentUtility = getUtilOfStateActionPair(action, row, col);
        double stationaryUtility = getUtilOfStateActionPair(null, row, col);
        double clockwiseUtility = getUtilOfStateActionPair(action.getClockwiseAction(), row, col);
        double antiClockwiseUtility = getUtilOfStateActionPair(action.getAntiClockwiseAction(), row, col);
        double oppositeUtility = getUtilOfStateActionPair(action.getOppositeAction(), row, col);

        //Expected Utility
        actionUtility = (PROB_INTENT  * intentUtility) +
                (PROB_STATIONARY  * stationaryUtility) +
                (PROB_CW * clockwiseUtility) +
                (PROB_ACW  * antiClockwiseUtility) +
                (PROB_OPPOSITE  * oppositeUtility);

        return actionUtility;
    }

    private double getUtilOfStateActionPair(Action action, int row, int col) {
        int newY = row;
        int newX = col;
        //not stationery
        if (action != null) {
            //based on the action, it will retrieve a + 1/-1 coordinate from the coordinate
            newY = row + action.getActionY();
            newX = col + action.getActionX();
        }

        //get the utility of the new state if the action is taken
        if (newY >= 0 && newX >= 0 && newY < this.rows && newX < this.cols &&
                this.grid[newY][newX].isVisitable()) {
            return this.currActionUtilArr[newY][newX].getUtility();
        }

        return this.currActionUtilArr[row][col].getUtility();
    }
}
