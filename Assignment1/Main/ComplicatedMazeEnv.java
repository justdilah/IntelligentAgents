package Assignment1.Main;

import Assignment1.Classes.PolicyIteration;
import Assignment1.Classes.UtilityAndAction;
import Assignment1.Classes.ValueIteration;
import Assignment1.ExternalMethods.FileIO;
import Assignment1.Maze.Grid;
import Assignment1.Maze.GridState;

import static Assignment1.Main.Config.*;
import static Assignment1.ExternalMethods.DisplayHelper.*;

public class ComplicatedMazeEnv {

    public static int scale = 1;
    public static boolean ranAllocation = false;
    public static void main(String[] args) {

        Grid map = new Grid(NUM_ROWS,NUM_COLS, scale,ranAllocation);
        map.displayGridWorld();

        GridState[][] mapObject = map.getGridMap();

        //VALUE ITERATION
        ValueIteration valueIteration = new ValueIteration(map, EPSILON, DISCOUNT);

        System.out.println("========================");
        System.out.println("Value Iteration");
        System.out.println("========================");
        System.out.println("Discount: " + DISCOUNT);
        System.out.println("Maximum Reward Value : " + MAX_REWARD);
        System.out.println("Constant(c): " + C);
        System.out.println("Epsilon(c*Rmax): " + EPSILON);

        final UtilityAndAction[][] OP_valueIteration = valueIteration.getOptimalPolicy();
        displayActionPolicy(OP_valueIteration);
        displayUtilities(mapObject, OP_valueIteration);
        displayUtilitiesGrid(OP_valueIteration);

        System.out.println("No of Iterations: " + valueIteration.getNoOfIterations());
        System.out.println("Convergence Criteria: " + valueIteration.getConvergenceCriteria());

        // Output to csv file to plot utility estimates as a function of iteration
        FileIO.writeToFile(valueIteration.getResults(), scale,"results/", "original_value_iteration_utility_history");

        //POLICY ITERATION
        PolicyIteration policyIteration = new PolicyIteration(map, DISCOUNT, K);

        System.out.println("========================");
        System.out.println("Policy Iteration");
        System.out.println("========================");
        System.out.println("Discount: " + DISCOUNT);
        System.out.println("Maximum Reward Value: " + MAX_REWARD);
        System.out.println("Constant(c): " + C);
        System.out.println("No of times Simplified bellman update is executed: " + K);

        final UtilityAndAction[][] OP_policyIteration = policyIteration.getOptimalPolicy();

        displayActionPolicy(OP_policyIteration);
        displayUtilities(mapObject, OP_policyIteration);
        displayUtilitiesGrid(OP_policyIteration);
        System.out.println("No of Iterations: " + policyIteration.getNoOfIterations());

        // Output to csv file to plot utility estimates as a function of iteration
        FileIO.writeToFile(policyIteration.getResults(), scale, "results/", "original_modified_policy_iteration_utility_history");

    }
}
