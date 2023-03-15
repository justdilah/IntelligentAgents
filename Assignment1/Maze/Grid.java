package Assignment1.Maze;

import java.util.Random;

import static Assignment1.Main.Config.*;

public class Grid {
    protected int noOfWalls = 5;
    protected int noOfRewards = 2;
    protected int noOfPenalties = 8;
    private GridState[][] map;
    public Grid(int row, int col,int scale, boolean ranAllocation){
        CreateGridMap(row,col,scale,ranAllocation);
    }


    private void CreateGridMap(int rows,int cols, int scale,boolean ranAllocation){

        Random ran = new Random();

        map = new GridState[rows*scale][cols*scale];

        for (int r=0; r<getRows(); r++) {
            for (int c=0; c<getCols(); c++) {
                map[r][c] = new GridState(r, c);
            }
        }

        for(int i=0;i<getRows();i++){
            for(int j=0;j<getCols();j++){
                this.map[i][j].setStateReward(E_REWARD);
                this.map[i][j].setStateType(State.EMPTY);
                this.map[i][j].setVisitable(true);
            }
        }

//        boolean ranAllocation = true;
        int totalNumOfAllowedStates = 1 + noOfRewards + noOfPenalties + noOfWalls;
        if(ranAllocation){

            for(int r = 0;r< totalNumOfAllowedStates + 1;r++){
                int ranRow = 0;
                int ranCol = 0;
                boolean flag = true;
                while (flag == true){
                    ranRow = ran.nextInt(getRows());
                    ranCol= ran.nextInt(getCols());
                    if (map[ranRow][ranCol].getStateType() == State.EMPTY){
                        flag = false;
                    }
                }
                System.out.println(ranRow + ", " + ranCol);
                if(r == 0){
                    map[ranRow][ranCol].setStateReward(0);
                    map[ranRow][ranCol].setStateType(State.START);
                    this.map[ranRow][ranCol].setVisitable(true);
                } else if (r <= noOfRewards){
                    System.out.println("Reward");
                    this.map[ranRow][ranCol].setStateReward(R_REWARD);
                    this.map[ranRow][ranCol].setStateType(State.REWARD);
                    this.map[ranRow][ranCol].setVisitable(true);
                } else if (r <= noOfRewards + noOfPenalties) {
                    System.out.println("Penalty");
                    this.map[ranRow][ranCol].setStateReward(P_REWARD);
                    this.map[ranRow][ranCol].setStateType(State.PENALTY);
                    this.map[ranRow][ranCol].setVisitable(true);
                } else if (r <= noOfRewards + noOfPenalties + noOfWalls)  {
                    System.out.println("Wall");
                    this.map[ranRow][ranCol].setStateReward(W_REWARD);
                    this.map[ranRow][ranCol].setStateType(State.WALL);
                    this.map[ranRow][ranCol].setVisitable(false);
                }



            }
        } else {
            //START POINT
            map[Integer.valueOf(START_POINT.split(",")[0])][Integer.valueOf(START_POINT.split(",")[1])].setStateReward(0);
            map[Integer.valueOf(START_POINT.split(",")[0])][Integer.valueOf(START_POINT.split(",")[1])].setStateType(State.START);

            //REWARD POINTS
            String[] arr = REWARD_POINTS.split(";");
            initialiseStates(arr,R_REWARD,true,State.REWARD,scale);

            //PENALTY POINTS
            arr = PENALTY_POINTS.split(";");
            initialiseStates(arr,P_REWARD,true,State.PENALTY,scale);

            //WALL POINTS
            arr = WALLS_POINTS.split(";");
            initialiseStates(arr,W_REWARD,false,State.WALL,scale);
        }



    }
    
    public void initialiseStates(String[] arr, Double reward, boolean isVisitable, State state,int scale){

            for (String s : arr) {
                int row = Integer.valueOf(s.split(",")[0]);
                int col = Integer.valueOf(s.split(",")[1]);
                if (scale == 1) {
                    System.out.println(s);
                    this.map[row][col].setStateReward(reward);
                    this.map[row][col].setStateType(state);
                    this.map[row][col].setVisitable(isVisitable);
                } else {
                    for (int r=row*scale; r<scale*(row+1); r++) {
                        for (int c=col*scale; c<scale*(col+1); c++) {
                            this.map[r][c].setStateReward(reward);
                            this.map[r][c].setStateType(state);
                            this.map[r][c].setVisitable(isVisitable);
                        }
                    }
                }
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
            str.append("+————");
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

        public GridState[][] getGridMap(){
        return this.map;
    }
}
