package Assignment1;

public class ActionUtilityPair implements Comparable<ActionUtilityPair> {

    private Action action = null;
    private Double utility = 0.0;

    public ActionUtilityPair() {
        action = null;
        utility = 0.0;
    }
    public ActionUtilityPair(Action action) {
        this.action = action;
        this.utility = 0.0;
    }
    public ActionUtilityPair(Action action, double utility) {
        this.action = action;
        this.utility = utility;
    }

    public Action getAction() {
        return this.action;
    }

    public void setAction(Action action) {
        if (action==null) {
            System.out.print("ACTION NULL");
        }
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
    public int compareTo(ActionUtilityPair otherActionUtilityPair) {
        // returns 1 if "actionUtilityPair" > "otherActionUtilityPair"
        return (this.getUtility().compareTo(otherActionUtilityPair.getUtility()));
        // returns 1 if "otherActionUtilityPair" > "actionUtilityPair"
        // return (otherActionUtilityPair.getUtility().compareTo(getUtility()));
    }
}
