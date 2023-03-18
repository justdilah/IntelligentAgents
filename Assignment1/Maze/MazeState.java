package Assignment1.Maze;

/**
 * MazeState class stores the state type, reward value, the x, and y coordinates of each state
 */
public class MazeState {
    protected int x_coord;
    protected int y_coord;
    protected double stateReward;
    protected boolean isEmpty;
    protected boolean isVisitable;
    protected boolean isFinal;

    protected State stateType;

    public MazeState(int x, int y) {
        this.x_coord = x;
        this.y_coord = y;
        this.isEmpty = true;
        this.isVisitable = true;
        this.stateReward = 0;
        this.stateType = State.EMPTY;
        this.setFinal(false);
    }

    public String getCoord() {
        return getX() + ", " + getY();
    }
    public int getX() {
        return x_coord;
    }

    public void setX(int x_coord) {
        this.x_coord = x_coord;
    }

    public int getY() {
        return y_coord;
    }

    public void setY(int y_coord) {
        this.y_coord = y_coord;
    }

    public double getStateReward() {
        return stateReward;
    }

    public void setStateReward(double stateReward) {
        this.stateReward = stateReward;
    }


    public boolean isVisitable() {
        return isVisitable;
    }

    public void setFinal(boolean isFinal) {
        this.isFinal = isFinal;
    }

    public void setVisitable(boolean visitable) {
        isVisitable = visitable;
    }

    public State getStateType() {
        return stateType;
    }

    public void setStateType(State stateType) {
        this.stateType = stateType;
    }

    public String toString(Boolean is_symbol) {
        if (is_symbol) {
            return "| " + this.stateType.toString() + " ";
        } else {
            return "[" + this.stateType.toString() + ", " + this.getCoord() + "]";
        }
    }
}
