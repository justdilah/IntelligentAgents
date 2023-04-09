package Assignment1.Main;

import Assignment1.Classes.PolicyIteration;
import Assignment1.Classes.UtilityAndAction;
import Assignment1.Classes.ValueIteration;
import Assignment1.ExternalMethods.WriteToFile;
import Assignment1.Maze.Maze;
import Assignment1.Maze.MazeState;

import static Assignment1.ExternalMethods.DisplayManager.*;
import static Assignment1.Main.Config.*;

/**(Part 2)
 * ComplicatedMazeEnv1stCase class is executable file to run the Complicated Maze Environment (1st Case:  Each category of states (Penalty, Reward, Wall) has an equal number of states)
 */
public class ComplicatedMazeEnv1stCase {
    public static int scale = 1;
    public static boolean complicated = true;

    public static void main(String[] args) {

        //complicated = true - means that the maze environment created will be designed for Bonus Question
        //1 - means it will use the coordinates defined for maze environment (1st case) for each states (PENALTY,REWARD,WALL,EMPTY,START)
        Maze map = new Maze(NUM_ROWS, NUM_COLS, scale, complicated, 1);
        map.showMazeWorld();

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
        System.out.println("No of Iterations: " + valueIteration.getNumOfIterations());
        System.out.println("Convergence Criteria: " + valueIteration.getConvergenceCriteria());

        UtilityAndAction[][] OP_valueIteration = valueIteration.getOptimalPolicy();
        showActionsInGrid(OP_valueIteration);
        showUtilities(mapObject, OP_valueIteration);
        showUtilitiesInGrid(OP_valueIteration);



        // Output to csv file to plot utility estimates as a function of iteration
        WriteToFile.writeToFile(valueIteration.getUtilityEstimates(), scale, "results/", "part2_1stCase_value_iteration_utility_estimates");

        //POLICY ITERATION
        PolicyIteration policyIteration = new PolicyIteration(map, DISCOUNT, K);

        System.out.println("========================");
        System.out.println("Policy Iteration");
        System.out.println("========================");
        System.out.println("Discount: " + DISCOUNT);
        System.out.println("Maximum Reward Value: " + MAX_REWARD);
        System.out.println("Constant(c): " + C);
        System.out.println("No of times Simplified bellman update is executed: " + K);
        System.out.println("No of Iterations: " + policyIteration.getNoOfIterations());

        UtilityAndAction[][] OP_policyIteration = policyIteration.getOptimalPolicy();

        //shows actions in a grid format
        showActionsInGrid(OP_policyIteration);
        showUtilities(mapObject, OP_policyIteration);
        //shows utilities in a grid format
        showUtilitiesInGrid(OP_policyIteration);


        // Output to csv file to plot utility estimates as a function of iteration
        WriteToFile.writeToFile(policyIteration.getUtilityEstimates(), scale, "results/", "part2_1stCase_policy_iteration_utility_estimates");

    }
}
