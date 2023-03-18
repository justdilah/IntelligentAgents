package Assignment1.Classes;

import Assignment1.Maze.Maze;
import Assignment1.Maze.MazeState;

import java.util.*;

import static Assignment1.Main.Config.*;

/**
 * Policy Iteration is used to build the modified Policy Iteration algorithm
 */

public class PolicyIteration {
    private double discount = 0.0;
    private int rows = 0;
    private int cols = 0;
    private int no_of_iterations = 0;
    private boolean unchanged = false;

    private MazeState[][] Maze = null;
    private UtilityAndAction[][] currentActionAndUtilityArr = null;
    private UtilityAndAction[][] optimalPolicyActionUtilArr = null;
    private List<UtilityAndAction[][]> ListOfActionUtilityArrays = new ArrayList<>();

    public PolicyIteration(Maze map, double discount, int K) {
        this.discount = discount;

        //retrieves attributes of the Maze
        this.rows = map.getRows();
        this.cols = map.getCols();
        this.Maze = map.getMazeMap();

        this.no_of_iterations = 0;

        // Intialise the array with state utility of 0 and random action for each state
        this.currentActionAndUtilityArr = generateRandomPolicy();

        this.ListOfActionUtilityArrays.add(this.currentActionAndUtilityArr);

        do {
            this.no_of_iterations++;

            // Update the state utilties by executing policy evaluation
            this.optimalPolicyActionUtilArr = performPolicyEvaluation(this.currentActionAndUtilityArr, this.Maze, K, discount);

            // Update Actions by executing policy improvement
            this.unchanged = performPolicyImprovement();

        } while (this.unchanged);
    }

    public List<UtilityAndAction[][]> getUtilityEstimates() {
        return this.ListOfActionUtilityArrays;
    }

    public UtilityAndAction[][] getOptimalPolicy() {
        return this.ListOfActionUtilityArrays.get(this.getNoOfIterations());
    }

    public int getNoOfIterations() {
        return this.no_of_iterations;
    }


    private UtilityAndAction[][] copyArray(UtilityAndAction[][] src) {
        return Arrays.stream(src).map(UtilityAndAction[]::clone).toArray(UtilityAndAction[][]::new);
    }

    private UtilityAndAction[][] generateRandomPolicy() {
        UtilityAndAction[][] actionUtilArr = new UtilityAndAction[this.rows][this.cols];

        for (int row = 0; row < this.rows; row++) {
            for (int col = 0; col < this.cols; col++) {
                if (this.Maze[row][col].isVisitable()) {
                    //retrieve a random action with a utility of 0
                    actionUtilArr[row][col] = new UtilityAndAction(Action.getRandAction());
                } else {
                    actionUtilArr[row][col] = new UtilityAndAction();
                }
            }
        }
        return actionUtilArr;
    }

    private UtilityAndAction[][] performPolicyEvaluation(UtilityAndAction[][] ActionUtilArr, MazeState[][] Maze, int K, double discount) {
        UtilityAndAction[][] currentActionAndUtilityArr = copyArray(ActionUtilArr);

        //initialise with the UtilityAndAction
        UtilityAndAction[][] newUtilityAndAction = initialiseArr();

        UtilityAndAction newActionUtility = null;
        for (int i=0; i<K; i++) {

            for (int row = 0; row < this.rows; row++) {
                for (int col = 0; col < this.cols; col++) {
                    // Check if MazeState is Wall
                    if (!Maze[row][col].isVisitable()) {continue;}

                    newActionUtility = getSimplifiedBellmanUpdate(row, col, currentActionAndUtilityArr, Maze, discount);
                    newUtilityAndAction[row][col] = newActionUtility;
                }
            }
            currentActionAndUtilityArr = copyArray(newUtilityAndAction);
        }
        return newUtilityAndAction;
    }

    private boolean performPolicyImprovement(){
        double newMaxUtility = 0.0;
        double currPolicyUtility = 0.0;
        boolean ucvar = false;
        for (int row = 0; row < this.rows; row++){
            for (int col = 0; col < this.cols; col++) {
                // Check if MazeState is Wall
                if (!Maze[row][col].isVisitable()) {
                    continue;
                }

                UtilityAndAction chosenUtilityAndAction = getMaxUtilityAndAction(this.optimalPolicyActionUtilArr, row, col);
                newMaxUtility = chosenUtilityAndAction.getUtility();
                Action policyAction = this.optimalPolicyActionUtilArr[row][col].getAction();
                currPolicyUtility = getExpectedUtility(this.optimalPolicyActionUtilArr, policyAction, row, col);

                if (newMaxUtility > currPolicyUtility) {
                    this.optimalPolicyActionUtilArr[row][col].setAction(chosenUtilityAndAction.getAction());
                    ucvar = true;
                }
            }
        }

        this.currentActionAndUtilityArr = copyArray(this.optimalPolicyActionUtilArr);
        this.ListOfActionUtilityArrays.add(this.currentActionAndUtilityArr);

        return ucvar;
    }

    private UtilityAndAction[][] initialiseArr() {
        UtilityAndAction[][] utilityAndActionArray = new UtilityAndAction[this.rows][this.cols];

        for (int row = 0; row < this.rows; row++) {
            for (int col = 0; col < this.cols; col++) {
                utilityAndActionArray[row][col] = new UtilityAndAction();
            }
        }
        return utilityAndActionArray;
    }

    private UtilityAndAction getSimplifiedBellmanUpdate(int row, int col, UtilityAndAction[][] currentActionAndUtilityArr, MazeState[][] Maze, double discount) {
        Action action = currentActionAndUtilityArr[row][col].getAction();
        double currUtility = getExpectedUtility(currentActionAndUtilityArr, action, row, col);

        //retrieves the updated utility value
        double newUtility = Maze[row][col].getStateReward() + (discount * currUtility);
        return new UtilityAndAction(action, newUtility);
    }

    //retrieves the expected utility of the action
    private double getExpectedUtility(UtilityAndAction[][] actionUtilArr, Action action, int row, int col) {
        //Calculate all the expected utility of all possible actions
        double intendedUtility = getExpUtilityBasedAction(actionUtilArr, action, row, col);
        double clockwiseUtility = getExpUtilityBasedAction(actionUtilArr, action.getClockwiseAction(), row, col);
        double antiClockwiseUtility = getExpUtilityBasedAction(actionUtilArr, action.getAntiClockwiseAction(), row, col);

        return (INTENDED_PROB  * intendedUtility) + (CW_PROB  * clockwiseUtility) + (ACW_PROB  * antiClockwiseUtility);

    }

    private double getExpUtilityBasedAction(UtilityAndAction[][] currentActionAndUtilityArr, Action action, int row, int col) {
        int ymodified = row;
        int xmodified = col;

        if (action != null) {
            ymodified = row + action.getChangeY();
            xmodified = col + action.getChangeX();
        }

        if (ymodified >= 0 && xmodified >= 0 && ymodified < this.rows && xmodified < this.cols && this.Maze[ymodified][xmodified].isVisitable()) {
            return currentActionAndUtilityArr[ymodified][xmodified].getUtility();
        }

        return currentActionAndUtilityArr[row][col].getUtility();
    }

    private UtilityAndAction getMaxUtilityAndAction(UtilityAndAction[][] currentActionAndUtilityArr, int row, int col) {
        List<UtilityAndAction> listOfUtilityAndAction = new ArrayList<>();

        for (Action action : Action.values()) {
            listOfUtilityAndAction.add(new UtilityAndAction(action, getExpectedUtility(currentActionAndUtilityArr, action, row, col)));
        }

        UtilityAndAction maxUtilityAndAction = Collections.max(listOfUtilityAndAction);
        return maxUtilityAndAction;
    }
}
