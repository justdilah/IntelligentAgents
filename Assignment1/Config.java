package Assignment1;

import java.util.ArrayList;

public class Config {
    public static final String START_POINT = "3,2";
    public static final String REWARD_POINTS = "0,0;0,2;0,5;1,3;2,4;3,5";
    public static final String PENALTY_POINTS = "1,1;1,5;2,2;3,3;4,4";
    public static final String WALLS_POINTS = "0,1;1,4;4,1;4,2;4,3";

    // Reward functions
    public static final double EMPTY_REWARD = -0.040;
    public static final double REWARD_REWARD = +1.000;
    public static final double PENALTY_REWARD = -1.000;
    public static final double WALL_REWARD = 0.000;

    /// Transition model
    public static final double PROB_INTENT = 0.800;
    public static final double PROB_STATIONARY = 0.000;
    public static final double PROB_CW = 0.100;
    public static final double PROB_ACW = 0.100;
    public static final double PROB_OPPOSITE = 0.000;

    // Discount factor
    public static final double DISCOUNT =  0.990;

    // Rmax
    public static final double R_MAX = 1.000;

    // Constant c
    public static final double C = 0.100;

    // Epsilon e = c * Rmax
    public static final double EPSILON = C * R_MAX;

    // Constant k (i.e. number of times simplified Bellman update is executed
    // to produce the next utility estimate)
    public static final int K = 30;
}
