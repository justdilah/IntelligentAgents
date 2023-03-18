package Assignment1.Main;

import Assignment1.Classes.PolicyIteration;
import Assignment1.Classes.UtilityAndAction;
import Assignment1.Classes.ValueIteration;
import Assignment1.ExternalMethods.WriteToFile;
import Assignment1.Maze.Maze;
import Assignment1.Maze.MazeState;

import static Assignment1.Main.Config.*;
import static Assignment1.ExternalMethods.DisplayManager.*;

/**
 * (Part 2)
 * ComplicatedMazeEnvScale class is executable file to run the Complicated Maze Environment which is increasing the scale of the environment
 */
public class ComplicatedMazeEnvScale {

    public static int scale = 2;
    public static boolean complicated = false;
    public static void main(String[] args) {

        //complicated = false - means that the maze environment created will be the maze that is defined in the assignment document
        //0 - means it will just use the original coordinates for each states (PENALTY,REWARD,WALL,EMPTY,START)
        //Since scale is increased from 1 to 2, the coordinates of the state will be multiplied hence, the number of states will increase
        Maze map = new Maze(NUM_ROWS,NUM_COLS, scale,complicated,0);
        map.displayMazeWorld();

        MazeState[][] mapObject = map.getMazeMap();

        //VALUE ITERATION
        ValueIteration valueIteration = new ValueIteration(map, EPSILON, DISCOUNT);

        System.out.println("========================");
        System.out.println("Value Iteration");
        System.out.println("========================");
        System.out.println("Discount: " + DISCOUNT);
        System.out.println("Maximum Reward Value : " + MAX_REWARD);
        System.out.println("Constant(c): " + C);
        System.out.println("Epsilon(c*Rmax): " + EPSILON);

        UtilityAndAction[][] OP_valueIteration = valueIteration.getMaxUtilityAndAction();


        displayActionsInGrid(OP_valueIteration);
        displayUtilities(mapObject, OP_valueIteration);
        displayUtilitiesInGrid(OP_valueIteration);

        System.out.println("No of Iterations: " + valueIteration.getNumOfIterations());
        System.out.println("Convergence Criteria: " + valueIteration.getConvergenceCriteria());

        // Output to csv file to plot utility estimates as a function of iteration
        WriteToFile.writeToFile(valueIteration.getUtilityEstimates(), scale,"results/", "part2_value_iteration_utility_estimates");

        //POLICY ITERATION
        PolicyIteration policyIteration = new PolicyIteration(map, DISCOUNT, K);

        System.out.println("========================");
        System.out.println("Policy Iteration");
        System.out.println("========================");
        System.out.println("Discount: " + DISCOUNT);
        System.out.println("Maximum Reward Value: " + MAX_REWARD);
        System.out.println("Constant(c): " + C);
        System.out.println("No of times Simplified bellman update is executed: " + K);

        UtilityAndAction[][] OP_policyIteration = policyIteration.getOptimalPolicy();

        displayActionsInGrid(OP_policyIteration);


        displayUtilities(mapObject, OP_policyIteration);
        displayUtilitiesInGrid(OP_policyIteration);
        System.out.println("No of Iterations: " + policyIteration.getNoOfIterations());

        // Output to csv file to plot utility estimates as a function of iteration
        WriteToFile.writeToFile(policyIteration.getUtilityEstimates(), scale, "results/", "part2_modified_policy_iteration_utility_estimates");

    }
}
