package Assignment1;

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

        map[0][0].setStateReward(0);

    }

    protected GridState[][] getGridMap(){
        return this.map;
    }
}
