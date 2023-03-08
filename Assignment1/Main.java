package Assignment1;

import static Assignment1.CommonMethods.*;
import static Assignment1.Config.DISCOUNT;
import static Assignment1.Config.K;
import static Assignment1.DisplayHelper.*;

public class Main {
    public static void main(String[] args) {
        Grid map = new Grid(6,6);
        map.displayGridWorld();

        GridState[][] matrix = map.getGridMap();

        System.out.println("[1] Value Iteration" + false);
        System.out.println("Parameters" + true);
        System.out.println("Discount : " + 0.990);
        System.out.println("Rmax : " + 1.000);
        System.out.println("Constant(c) : " + 0.100);

        double epsilon = 0.100*1.000;
        System.out.println("Epsilon(c*Rmax) : " + epsilon);

        ValueIteration valueIteration = new ValueIteration(map,
                epsilon,
                0.990);

        final ActionUtilityPair[][] optimalPolicy_VI = valueIteration.getOptimalPolicy();
        displayActionPolicy(optimalPolicy_VI);
        displayUtilities(matrix, optimalPolicy_VI);
        displayUtilitiesGrid(optimalPolicy_VI);
        System.out.println("Number of iterations: " + valueIteration.getNoOfIterations());
        System.out.println("Convergence Criteria: " + valueIteration.getConvergenceCriteria());

        // Policy Iteration
        printHeader("[2] Policy Iteration", false);
        printSectionHeader("Parameters", true);
        printDetails("Discount : " + DISCOUNT);
        printDetails("K : " + K +
                "\n(i.e. Simplified Bellman Update is repeated K times per state to produce next utility estimate)");

        PolicyIteration mPolicyIteration = new PolicyIteration(map,
                DISCOUNT,
                K);

        final ActionUtilityPair[][] optimalPolicy_MPI = mPolicyIteration.getOptimalPolicy();

        displayActionPolicy(optimalPolicy_MPI);
        displayUtilities(matrix, optimalPolicy_MPI);
        displayUtilitiesGrid(optimalPolicy_MPI);
        print("Number of iterations: " + mPolicyIteration.getNoOfIterations());
//
//        // Output to csv file to plot utility estimates as a function of iteration
//        FileIO.writeToFile(mPolicyIteration.getResults(), SCALE, "results/", "original_modified_policy_iteration_utility_history");
//
//        // Check if policies obtained are the same
//        checkSamePolicy(optimalPolicy_VI, optimalPolicy_MPI);
    }
}
