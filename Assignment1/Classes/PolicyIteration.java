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
    private UtilityAndAction[][] currentUtilityAndActionArray = null;
    private UtilityAndAction[][] optimalUtilityAndActionArray = null;
    private List<UtilityAndAction[][]> allUtilityAndActionArray = new ArrayList<>();

    public PolicyIteration(Maze map, double discount, int K) {
        this.discount = discount;

        //retrieves attributes of the Maze
        this.rows = map.getRows();
        this.cols = map.getCols();
        this.Maze = map.getMazeMap();

        this.no_of_iterations = 0;

        // Intialise the array with state utility of 0 and random action for each state
        this.currentUtilityAndActionArray = generateRandomPolicy();

        this.allUtilityAndActionArray.add(this.currentUtilityAndActionArray);

        do {
            this.no_of_iterations++;

            // Update the state utilties by executing policy evaluation
            this.optimalUtilityAndActionArray = performPolicyEvaluation(this.currentUtilityAndActionArray, this.Maze, K, discount);

            // Update Actions by executing policy improvement
            this.unchanged = performPolicyImprovement();

        } while (!this.unchanged);
    }

    public List<UtilityAndAction[][]> getUtilityEstimates() {
        return this.allUtilityAndActionArray;
    }

    public UtilityAndAction[][] getOptimalPolicy() {
        return this.allUtilityAndActionArray.get(this.getNoOfIterations());
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
        boolean ucvar = true;
        for (int row = 0; row < this.rows; row++){
            for (int col = 0; col < this.cols; col++) {
                // Check if MazeState is Wall
                if (!Maze[row][col].isVisitable()) {
                    continue;
                }

                UtilityAndAction chosenUtilityAndAction = getMaxUtilityAndAction(this.optimalUtilityAndActionArray, row, col);
                newMaxUtility = chosenUtilityAndAction.getUtility();
                Action policyAction = this.optimalUtilityAndActionArray[row][col].getAction();
                currPolicyUtility = getExpectedUtility(this.optimalUtilityAndActionArray, policyAction, row, col);

                if (newMaxUtility > currPolicyUtility) {
                    this.optimalUtilityAndActionArray[row][col].setAction(chosenUtilityAndAction.getAction());
                    ucvar = false;
                }
            }
        }

        this.currentUtilityAndActionArray = copyArray(this.optimalUtilityAndActionArray);
        this.allUtilityAndActionArray.add(this.currentUtilityAndActionArray);

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

    private UtilityAndAction getSimplifiedBellmanUpdate(int row, int col, UtilityAndAction[][] currentUtilityAndActionArray, MazeState[][] Maze, double discount) {
        Action action = currentUtilityAndActionArray[row][col].getAction();
        double currUtility = getExpectedUtility(currentUtilityAndActionArray, action, row, col);

        //retrieves the updated utility value
        double newUtility = Maze[row][col].getStateReward() + (discount * currUtility);
        return new UtilityAndAction(action, newUtility);
    }

    //retrieves the expected utility of the action
    private double getExpectedUtility(UtilityAndAction[][] utilityAndActionArray, Action action, int row, int col) {
        //Calculate the expected utility of a possible actions
        double intendedUtility = getExpUtilityBasedAction(utilityAndActionArray, action, row, col);
        double clockwiseUtility = getExpUtilityBasedAction(utilityAndActionArray, action.getClockwiseAction(), row, col);
        double antiClockwiseUtility = getExpUtilityBasedAction(utilityAndActionArray, action.getAntiClockwiseAction(), row, col);

        return (INTENDED_PROB  * intendedUtility) + (CW_PROB  * clockwiseUtility) + (ACW_PROB  * antiClockwiseUtility);

    }

    private double getExpUtilityBasedAction(UtilityAndAction[][] currenUtilityAndActionArray, Action action, int row, int col) {
        int ymodified = row;
        int xmodified = col;

        if (action != null) {
            //based on the action, it will retrieve the coordinate of the next state
            ymodified = row + action.getChangeY();
            xmodified = col + action.getChangeX();
        }
        //based on the action, it will retrieve the coordinate of the next state
        if (ymodified >= 0 && xmodified >= 0 && ymodified < this.rows && xmodified < this.cols && this.Maze[ymodified][xmodified].isVisitable()) {
            return currenUtilityAndActionArray[ymodified][xmodified].getUtility();
        }
        //else get the utility of the current state
        return currenUtilityAndActionArray[row][col].getUtility();
    }

    private UtilityAndAction getMaxUtilityAndAction(UtilityAndAction[][] currentUtilityAndActionArray, int row, int col) {
        List<UtilityAndAction> listOfUtilityAndAction = new ArrayList<>();

        for (Action action : Action.values()) {
            listOfUtilityAndAction.add(new UtilityAndAction(action, getExpectedUtility( currentUtilityAndActionArray, action, row, col)));
        }

        UtilityAndAction maxUtilityAndAction = Collections.max(listOfUtilityAndAction);
        return maxUtilityAndAction;
    }
}
