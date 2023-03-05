package Assignment1;

public class Grid {

    private int[][] map;
    public Grid(int row, int col){
        CreateGridMap(row,col);
    }

    private void CreateGridMap(int rows,int cols){
        map = new int[rows][cols];
        for (int r=0; r<rows; r++) {
            for (int c=0; c<cols; c++) {
                map[r][c] = 0;
            }
        }
    }

    protected int[][] getGridMap(){
        return this.map;
    }
}
