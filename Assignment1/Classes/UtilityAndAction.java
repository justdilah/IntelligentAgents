package Assignment1.Classes;

/**
 * UtilityAndAction class holds the utility value and action of the state
 */
public class UtilityAndAction implements Comparable<UtilityAndAction> {

    private Action action = null;
    private Double utility = 0.0;

    public UtilityAndAction() {
        action = null;
        utility = 0.0;
    }
    public UtilityAndAction(Action action) {
        this.action = action;
        this.utility = 0.0;
    }
    public UtilityAndAction(Action action, double utility) {
        this.utility = utility;
        this.action = action;

    }

    public Action getAction() {
        return this.action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public Double getUtility() {
        return this.utility;
    }

    public void setUtility(Double utility) {
        this.utility = utility;
    }

    @Override
    public String toString() {
        return (this.action==null) ? "X" : this.action.toString();
    }

    @Override
    public int compareTo(UtilityAndAction otherActionUtilityPair) {
        return (this.getUtility().compareTo(otherActionUtilityPair.getUtility()));
    }
}
