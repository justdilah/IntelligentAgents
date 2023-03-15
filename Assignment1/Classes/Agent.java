package Assignment1.Classes;

import java.util.List;
public interface Agent {
    List<UtilityAndAction[][]> getResults();

    UtilityAndAction[][] getOptimalPolicy();

    int getNoOfIterations();
}
