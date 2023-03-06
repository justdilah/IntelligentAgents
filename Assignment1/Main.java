package Assignment1;

import static Assignment1.DisplayHelper.*;

public class Main {
    public static void main(String[] args) {
        Grid map = new Grid(6,6);
        map.displayGridWorld();

        GridState[][] matrix = map.getGridMap();

        System.out.println("[1] Value Iteration" + false);
        System.out.println("Parameters" + true);
        System.out.println("Discount : " + 0.99);
        System.out.println("Rmax : " + 1.00);
        System.out.println("Constant(c) : " + 0.10);
        System.out.println("Epsilon(c*Rmax) : " + (0.10*1.00));

        ValueIteration valueIteration = new ValueIteration(map,
                (0.10*1.00),
                0.99);

        final ActionUtilityPair[][] optimalPolicy_VI = valueIteration.getOptimalPolicy();
        displayActionPolicy(optimalPolicy_VI);
        displayUtilities(matrix, optimalPolicy_VI);
        displayUtilitiesGrid(optimalPolicy_VI);
        System.out.println("Number of iterations: " + valueIteration.getNoOfIterations());
        System.out.println("Convergence Criteria: " + valueIteration.getConvergenceCriteria());
    }
}
