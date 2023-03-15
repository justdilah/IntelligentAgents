package Assignment1.Classes;

import Assignment1.Maze.Grid;
import Assignment1.Maze.GridState;

import java.util.*;

import static Assignment1.Main.Config.*;

public class PolicyIteration {
    private double discount = 0.0;
    private int rows = 0;
    private int cols = 0;
    private int no_of_iterations = 0;
    private boolean policyIsUnstable = false;

    private GridState[][] grid = null;
    private UtilityAndAction[][] currActionUtilArr = null;
    private UtilityAndAction[][] optimalPolicyActionUtilArr = null;
    private List<UtilityAndAction[][]> ListOfActionUtilityArrays = new ArrayList<>();

    public PolicyIteration(Grid gridWorld, double discount, int K) {
        this.discount = discount;

        this.rows = gridWorld.getRows();
        this.cols = gridWorld.getCols();
        this.grid = gridWorld.getGridMap();

        this.no_of_iterations = 0;

        // Generate Random Policy
        this.currActionUtilArr = generateRandomPolicy();
        this.ListOfActionUtilityArrays.add(this.currActionUtilArr);

        double newMaxUtility = 0.0;
        double currPolicyUtility = 0.0;
        do {
            this.no_of_iterations++;

            // Policy Evaluation: Update Utilities
            this.optimalPolicyActionUtilArr = policyEvaluation(this.currActionUtilArr, this.grid, K, discount);

            // Policy Improvement: Update Actions
            this.policyIsUnstable = false;

            for (int row = 0; row < this.rows; row++){
                for (int col = 0; col < this.cols; col++) {
                    // Check if GridState is Wall
                    if (!grid[row][col].isVisitable()) {continue;}

                    UtilityAndAction chosenUtilityAndAction = getChosenUtilityAndAction(this.optimalPolicyActionUtilArr, row, col);
                    newMaxUtility = chosenUtilityAndAction.getUtility();
                    Action policyAction = this.optimalPolicyActionUtilArr[row][col].getAction();
                    currPolicyUtility = getTransitionStateActionPairUtil(this.optimalPolicyActionUtilArr, policyAction, row, col);

                    if (newMaxUtility > currPolicyUtility) {
                        this.optimalPolicyActionUtilArr[row][col].setAction(chosenUtilityAndAction.getAction());
                        this.policyIsUnstable = true;
                    }
                }
            }
            this.currActionUtilArr = replicateUtilArray(this.optimalPolicyActionUtilArr);
            this.ListOfActionUtilityArrays.add(this.currActionUtilArr);

        } while (this.policyIsUnstable);
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


    private UtilityAndAction[][] replicateUtilArray(UtilityAndAction[][] src) {
        return Arrays.stream(src).map(UtilityAndAction[]::clone).toArray(UtilityAndAction[][]::new);
    }

    private UtilityAndAction[][] generateRandomPolicy() {
        UtilityAndAction[][] actionUtilArr = new UtilityAndAction[this.rows][this.cols];

        for (int row = 0; row < this.rows; row++) {
            for (int col = 0; col < this.cols; col++) {
                if (this.grid[row][col].isVisitable()) {
                    //retrieve a random action with a utility of 0
                    actionUtilArr[row][col] = new UtilityAndAction(Action.getRandAction());
                } else {
                    actionUtilArr[row][col] = new UtilityAndAction();
                }
            }
        }
        return actionUtilArr;
    }

    private UtilityAndAction[][] policyEvaluation(UtilityAndAction[][] ActionUtilArr, GridState[][] grid, int K, double discount) {
        UtilityAndAction[][] currActionUtilArr = replicateUtilArray(ActionUtilArr);

        //initialise with the UtilityAndAction
        UtilityAndAction[][] newActionUtilArr = generateInitialPolicy();

        UtilityAndAction newActionUtility = null;
        for (int i=0; i<K; i++) {

            for (int row = 0; row < this.rows; row++) {
                for (int col = 0; col < this.cols; col++) {
                    // Check if GridState is Wall
                    if (!grid[row][col].isVisitable()) {continue;}

                    newActionUtility = getSimplifiedBellmanUpdate(row, col, currActionUtilArr, grid, discount);
                    newActionUtilArr[row][col] = newActionUtility;
                }
            }
            currActionUtilArr = replicateUtilArray(newActionUtilArr);
        }
        return newActionUtilArr;
    }

    private UtilityAndAction[][] generateInitialPolicy() {
        UtilityAndAction[][] actionUtilArr = new UtilityAndAction[this.rows][this.cols];

        for (int row = 0; row < this.rows; row++) {
            for (int col = 0; col < this.cols; col++) {
                actionUtilArr[row][col] = new UtilityAndAction();
            }
        }
        return actionUtilArr;
    }

    private UtilityAndAction getSimplifiedBellmanUpdate(int row, int col, UtilityAndAction[][] currActionUtilArr, GridState[][] grid, double discount) {
        Action action = currActionUtilArr[row][col].getAction();
        double currUtility = getTransitionStateActionPairUtil(currActionUtilArr, action, row, col);
        //retrieves the updated utility value
        double newUtility = grid[row][col].getStateReward() + (discount * currUtility);
        return new UtilityAndAction(action, newUtility);
    }

    private double getTransitionStateActionPairUtil(UtilityAndAction[][] actionUtilArr, Action action, int row, int col) {
        double actionUtility = 0.000;

        double intentUtility = getUtilOfStateActionPair(actionUtilArr, action, row, col);
        double stationaryUtility = getUtilOfStateActionPair(actionUtilArr, null, row, col);
        double clockwiseUtility = getUtilOfStateActionPair(actionUtilArr, action.getClockwiseAction(), row, col);
        double antiClockwiseUtility = getUtilOfStateActionPair(actionUtilArr, action.getAntiClockwiseAction(), row, col);
        double oppositeUtility = getUtilOfStateActionPair(actionUtilArr, action.getOppositeAction(), row, col);

        actionUtility = (PROB_INTENT * intentUtility) +
                (PROB_STATIONARY * stationaryUtility) +
                (PROB_CW * clockwiseUtility) +
                (PROB_ACW * antiClockwiseUtility) +
                (PROB_OPPOSITE * oppositeUtility);
        return actionUtility;
    }

    private double getUtilOfStateActionPair(UtilityAndAction[][] currActionUtilArr, Action action, int row, int col) {
        int newY = row;
        int newX = col;

        if (action != null) {
            newY = row + action.getActionY();
            newX = col + action.getActionX();
        }

        if (newY >= 0 && newX >= 0 && newY < this.rows && newX < this.cols &&
                this.grid[newY][newX].isVisitable()) {
            return currActionUtilArr[newY][newX].getUtility();
        }

        return currActionUtilArr[row][col].getUtility();
    }

    private UtilityAndAction getChosenUtilityAndAction(UtilityAndAction[][] currActionUtilArr, int row, int col) {
        List<UtilityAndAction> listOfUtilityAndAction = new ArrayList<>();
        EnumSet.allOf(Action.class).
                forEach(action -> listOfUtilityAndAction.add(
                                new UtilityAndAction(action, getTransitionStateActionPairUtil(currActionUtilArr, action, row, col))
                        )
                );

        UtilityAndAction chosenUtilityAndAction = Collections.max(listOfUtilityAndAction);
        return chosenUtilityAndAction;
    }
}
