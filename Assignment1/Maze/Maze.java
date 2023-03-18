package Assignment1.Maze;

import java.util.Random;

import static Assignment1.Main.Config.*;

/**
 * Maze class
 */
public class Maze {

    private String sp = "5,3";
    private String rp = "2,1;1,4";
    private String pp = "0,1;0,3;2,3;3,3;3,4;4,4;5,1;5,5";
    private String wp = "0,2;2,0;2,2;1,5;4,1";

    private MazeState[][] map;
    public Maze(int row, int col,int scale, boolean ranAllocation,int select){
        CreateMazeMap(row,col,scale,ranAllocation,select);
    }


    private void CreateMazeMap(int rows,int cols, int scale,boolean complicated,int select){

        Random ran = new Random();

        map = new MazeState[rows*scale][cols*scale];

        for (int r=0; r<getRows(); r++) {
            for (int c=0; c<getCols(); c++) {
                map[r][c] = new MazeState(r, c);
            }
        }

        for(int i=0;i<getRows();i++){
            for(int j=0;j<getCols();j++){
                this.map[i][j].setStateReward(E_REWARD);
                this.map[i][j].setStateType(State.EMPTY);
                this.map[i][j].setVisitable(true);
            }
        }

        if(complicated){
            //1st Case
            if(select == 1) {
                sp = "3,1";
                rp = "0,3;0,5;3,0;5,0;5,3";
                pp = "1,0;2,3;2,5;5,4;5,5";
                wp = "0,1;1,2;2,0;2,1;3,5";

            } else {
                //2nd Case
                sp = "5,3";
                rp = "2,1;1,4";
                pp = "0,1;0,3;2,3;3,3;3,4;4,4;5,1;5,5";
                wp = "0,2;2,0;2,2;1,5;4,1";
            }

            //START POINT
            map[Integer.valueOf(sp.split(",")[0])][Integer.valueOf(sp.split(",")[1])].setStateReward(0);
            map[Integer.valueOf(sp.split(",")[0])][Integer.valueOf(sp.split(",")[1])].setStateType(State.START);

            //REWARD POINTS
            String[] arr = rp.split(";");
            initialiseStates(arr,R_REWARD,true,State.REWARD,scale);

            //PENALTY POINTS
            arr = pp.split(";");
            initialiseStates(arr,P_REWARD,true,State.PENALTY,scale);

            //WALL POINTS
            arr = wp.split(";");
            initialiseStates(arr,W_REWARD,false,State.WALL,scale);

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

    public void displayMazeWorld() {
        System.out.println(this.toString(true));
    }

        public MazeState[][] getMazeMap(){
        return this.map;
    }
}
