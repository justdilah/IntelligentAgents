package Assignment1.Maze;

public class GridState {
    private char symbol;
    protected int x_coord;
    protected int y_coord;
    protected double stateReward;
    protected boolean isEmpty;
    protected boolean isVisitable;
    protected boolean isFinal;

    protected String color;
    protected State stateType;

    public GridState(int x, int y) {
        this.x_coord = x;
        this.y_coord = y;
        this.isEmpty = true;
        this.isVisitable = true;
        this.stateReward = 0;
        this.color = "";
        this.stateType = State.EMPTY;
        this.setFinal(false);
    }

    public String getColor(){
        return this.color;
    }

    public void setColor(String color){
        this.color = color;
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

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }

    public boolean isVisitable() {
        return isVisitable;
    }

    public boolean isFinal() {
        return this.isFinal;
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
