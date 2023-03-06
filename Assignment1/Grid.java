package Assignment1;

import static Assignment1.Config.*;

public class Grid {

    private GridState[][] map;
    public Grid(int row, int col){
        CreateGridMap(row,col);
    }


    private void CreateGridMap(int rows,int cols){

        map = new GridState[rows][cols];
        for (int r=0; r<rows; r++) {
            for (int c=0; c<cols; c++) {
                map[r][c] = new GridState(r, c);
            }
        }

        for(int i=0;i<getRows();i++){
            for(int j=0;j<getCols();j++){
                this.map[i][j].setStateReward(EMPTY_REWARD);
                this.map[i][j].setStateType(State.EMPTY);
                this.map[i][j].setVisitable(true);
            }
        }

        //START POINT
        map[Integer.valueOf(START_POINT.split(",")[0])][Integer.valueOf(START_POINT.split(",")[1])].setStateReward(0);
        map[Integer.valueOf(START_POINT.split(",")[0])][Integer.valueOf(START_POINT.split(",")[1])].setStateType(State.START);

        //REWARD POINTS
        String[] arr = REWARD_POINTS.split(";");
        initialiseStates(arr,REWARD_REWARD,true,State.REWARD);

        //PENALTY POINTS
        arr = PENALTY_POINTS.split(";");
        initialiseStates(arr,PENALTY_REWARD,true,State.PENALTY);

        //WALL POINTS
        arr = WALLS_POINTS.split(";");
        initialiseStates(arr,WALL_REWARD,false,State.WALL);
    }
    
    public void initialiseStates(String[] arr, Double reward, boolean isVisitable, State state){
        for (String s: arr){
            System.out.println(s);
            this.map[Integer.valueOf(s.split(",")[0])][Integer.valueOf(s.split(",")[1])].setStateReward(reward);
            this.map[Integer.valueOf(s.split(",")[0])][Integer.valueOf(s.split(",")[1])].setStateType(state);
            this.map[Integer.valueOf(s.split(",")[0])][Integer.valueOf(s.split(",")[1])].setVisitable(isVisitable);
        }
    }

    public int getRows() {
        return map.length;
    }

    public int getCols() {
        return map[0].length;
    }

    public StringBuilder addInBetweenDesign(StringBuilder str) {
        str.append("   ");
        for (int col = 0; col < getCols(); col++) {
            str.append("+———");
        }
        return str.append("+");
    }

    private static String convertToUpperCase(String msg, boolean toUpperCase) {
        if (toUpperCase) {
            return msg.toUpperCase();
        }
        return msg;
    }
    public static String getHeader(String msg, boolean toUpperCase) {
        StringBuilder str = new StringBuilder();
        msg = convertToUpperCase(msg, toUpperCase);
        str.append("\n▒▒▒▒▒▒▒▒▒ " + msg + " ▒▒▒▒▒▒▒▒▒\n");
        return str.toString();
    }
    public String toString(Boolean isSymbol) {
        StringBuilder str = new StringBuilder();

        if (isSymbol) {
            str.append(getHeader("Symbol Matrix:", false));
            for (int row = 0; row < getRows(); row++) {
                if (row==0) {
                    for (int r = 0; r < getRows() + 1; r++) {
                        if (r-1 < 0) {
                            str.append("     "); continue;
                        }
                        str.append(r-1);
                        str.append("   ");
                    }
                    str.append("\n");
                }
                str = addInBetweenDesign(str);
                str.append("\n");
                for (int col = 0; col < getCols(); col++) {
                    if (col==0) {
                        str.append(row);
                        str.append("  ");
                    }
                    str.append(map[row][col].toString(true));
                    if (col==getRows()-1) {
                        str.append("|");
                    }
                }
                str.append("\n");
            }
            str = addInBetweenDesign(str);
        } else {
            str.append("Matrix:\n");
            for (int row = 0; row < getRows(); row++) {
                for (int col = 0; col < getCols(); col++) {
                    str.append(map[row][col].toString(false) + ", ");
                }
                str.append("\n");
            }
        }
        return str.toString();
    }

    public void displayGridWorld() {
        System.out.println(this.toString(true));
    }

    protected GridState[][] getGridMap(){
        return this.map;
    }
}
