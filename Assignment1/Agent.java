package Assignment1;

import java.util.List;
public interface Agent {
    List<ActionUtilityPair[][]> getResults();

    ActionUtilityPair[][] getOptimalPolicy();

    int getNoOfIterations();
}
