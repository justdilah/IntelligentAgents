package Assignment1.Classes;

import Assignment1.Maze.Maze;
import Assignment1.Maze.MazeState;

import java.util.*;


import static Assignment1.Main.Config.*;

/**
 * Value Iteration class is used to build the Value Iteration algorithm
 */
public class ValueIteration {
    private int rows = 0;
    private int cols = 0;
    private int numOfIterations = 0;
    private double convergenceCriteria = 0.0;

    private MazeState[][] Maze = null;
    private UtilityAndAction[][] currentActionAndUtilityArr = null;
    private UtilityAndAction[][] newUtilityAndActionArr = null;
    private List<UtilityAndAction[][]> ListOfActionUtilityArrays = new ArrayList<>();


    public ValueIteration(Maze map, double epsilon, double discount) {

        //retrieves attributes of the Maze
        this.rows = map.getRows();
        this.cols = map.getCols();
        this.Maze = map.getMazeMap();


        //Initialisation
        this.currentActionAndUtilityArr = new UtilityAndAction[rows][cols];
        this.newUtilityAndActionArr = new UtilityAndAction[rows][cols];
        newUtilityAndActionArr = this.initialiseArr();


        double maxChangeInUtility = 0;

        this.convergenceCriteria = epsilon * ((1-discount) / discount);

        this.numOfIterations = 0;

        double newUtility = 0.0;
        double currentUtility = 0.0;


        do {
            this.numOfIterations++;

            this.currentActionAndUtilityArr = copyArray(newUtilityAndActionArr);

            //acts as a starting point of the utility estimates (used for the plot)
            this.ListOfActionUtilityArrays.add(this.currentActionAndUtilityArr);
            maxChangeInUtility = 0;

            // loop through all the states of the map
            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < cols; col++) {
                    // Check if MazeState is Wall
                    if (!Maze[row][col].isVisitable()) {
                        continue;
                    }

                    //Retrieves the optimal action and utility
                    UtilityAndAction currState = getMaxUtilityAndAction(row, col);

                    //retrieves the new utility of the current state by using the bellman equation
                    newUtility = performBellmanUpdate(row,col,currState,discount);
                    currentUtility = this.currentActionAndUtilityArr[row][col].getUtility();

                    //sets new utility of the curr state
                    currState.setUtility(newUtility);

                    //updated utility and action of the state
                    this.newUtilityAndActionArr[row][col] = currState;

                    //Checks if the difference between the new Utility and current utility larger than the previous difference
                    if (Math.abs(newUtility - currentUtility) > maxChangeInUtility) {
                        //If yes, updates the value for max change
                        maxChangeInUtility = Math.abs(newUtility - currentUtility);
                    }
                }
            }

        } while (maxChangeInUtility >= this.convergenceCriteria);
        this.ListOfActionUtilityArrays.add(newUtilityAndActionArr);
    }


    public double performBellmanUpdate(int row,int col, UtilityAndAction nextState, double discount){
        return this.Maze[row][col].getStateReward() + (discount * nextState.getUtility());
    }

    public UtilityAndAction[][] getMaxUtilityAndAction() {
        return this.ListOfActionUtilityArrays.get(this.getNumOfIterations());
    }

    public List<UtilityAndAction[][]> getUtilityEstimates() {
        return this.ListOfActionUtilityArrays;
    }

    //Copies Array
    private UtilityAndAction[][] copyArray(UtilityAndAction[][] src) {
        return Arrays.stream(src).map(UtilityAndAction[]::clone).toArray(UtilityAndAction[][]::new);
    }

    private UtilityAndAction[][] initialiseArr() {
        UtilityAndAction[][] actionUtilArr = new UtilityAndAction[this.rows][this.cols];

        //At each coordinate, initialise UtilityAndAction
        for (int row = 0; row < this.rows; row++) {
            for (int col = 0; col < this.cols; col++) {
                actionUtilArr[row][col] = new UtilityAndAction();
            }
        }
        return actionUtilArr;
    }

    public double getConvergenceCriteria() {
        return this.convergenceCriteria;
    }
    public int getNumOfIterations() {
        return this.numOfIterations;
    }

    private UtilityAndAction getMaxUtilityAndAction(int row, int col) {
        List<UtilityAndAction> listOfUtilityAndAction = new ArrayList<>();
        //Goes through the loop of Actions (UP,DOWN,LEFT,RIGHT)
        for (Action action : Action.values()) {
            listOfUtilityAndAction.add(new UtilityAndAction(action, getPotentialNextStateUtility(action, row, col)));
        }

        //Retrieve utility of the next state
        UtilityAndAction maxUtilityAndAction = Collections.max(listOfUtilityAndAction);
        return maxUtilityAndAction;
    }

    //retrieves the expected utility of the action
    private double getPotentialNextStateUtility(Action action, int row, int col) {
        //Calculate all the expected utility of all possible actions
        double intendedUtility = getExpUtilityBasedAction(action, row, col);
        double clockwiseUtility = getExpUtilityBasedAction(action.getClockwiseAction(), row, col);
        double antiClockwiseUtility = getExpUtilityBasedAction(action.getAntiClockwiseAction(), row, col);

        return (INTENDED_PROB  * intendedUtility) + (CW_PROB  * clockwiseUtility) + (ACW_PROB  * antiClockwiseUtility);
    }

    private double getExpUtilityBasedAction(Action action, int row, int col) {
        int ymodified = row;
        int xmodified = col;
        //not stationery
        if (action != null) {
            //based on the action, it will retrieve the coordinate of the next state
            ymodified = row + action.getChangeY();
            xmodified = col + action.getChangeX();
        }

        //get the utility of the new state if the action is taken
        if (ymodified >= 0 && xmodified >= 0 && ymodified < this.rows && xmodified < this.cols &&
                this.Maze[ymodified][xmodified].isVisitable()) {
            return this.currentActionAndUtilityArr[ymodified][xmodified].getUtility();
        }
        //else get the utility of the current state
        return this.currentActionAndUtilityArr[row][col].getUtility();
    }
}
