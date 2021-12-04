public class Model {
    private Viewer viewer;
    private int[][] desktop;
    private int indexX;
    private int indexY;

    public Model(Viewer viewer) {
        this.viewer = viewer;
        initialization();
    }

    public void initialization() {
        desktop = new int[][] {
                {2,2,2,2,2,2,2,2,2,2},
                {2,4,3,0,4,0,0,0,0,2},
                {2,0,0,0,3,0,0,0,0,2},
                {2,0,0,0,0,0,0,0,0,2},
                {2,0,0,0,1,3,0,0,4,2},
                {2,0,0,0,0,0,0,0,0,2},
                {2,0,0,0,3,0,0,0,0,2},
                {2,0,0,0,4,0,0,0,0,2},
                {2,0,0,0,0,0,0,0,0,2},
                {2,2,2,2,2,2,2,2,2,2},
        };
        indexX = 4;
        indexY = 4;
        desktop[indexX][indexY] = 1;
    }

    public void nextLevel() {
        desktop = new int[][] {
                {2,2,2,2,2,2,2,2,2,2},
                {2,0,0,0,0,0,0,0,0,2},
                {2,0,0,0,0,0,0,0,0,2},
                {2,0,0,0,0,0,0,0,0,2},
                {2,0,2,2,2,2,2,2,2,2},
                {2,0,0,0,0,0,0,0,0,2},
                {2,0,0,0,0,0,0,0,0,2},
                {2,0,0,0,3,4,0,0,0,2},
                {2,0,0,0,0,0,0,0,0,2},
                {2,2,2,2,2,2,2,2,2,2},
        };
        indexX = 1;
        indexY = 1;
        desktop[indexX][indexY] = 1;

    }

    public int getX() {
        return indexX;
    }

    public void setX(int x) {
        this.indexX = x;
    }

    public int getY() {
        return indexY;
    }

    public void setY(int y) {
        this.indexY = y;
    }

    public void move(String direction) {
        if(direction.equals("up")){
            moveUp();
        }
        else if(direction.equals("right")){
            moveRight();
        }
        else if(direction.equals("down")){
            moveDown();
        }
        else if(direction.equals("left")){
            moveLeft();
        }
        checkGoal();
        viewer.update();
        won();
    }

    private void checkGoal(){
        if(desktop[4][8] == 0){
            desktop[4][8] = 4;
        } else if(desktop[1][1] == 0){
            desktop[1][1] = 4;
        }else if(desktop[1][4] == 0){
            desktop[1][4] = 4;
        }else if(desktop[7][4] == 0){
            desktop[7][4] = 4;
        }
    }

    private void won(){
        boolean isWon = true;

        if(desktop[4][8] != 3 || desktop[1][1] != 3 || desktop[1][4] != 3 || desktop[7][4] != 3){
            isWon = false;
        }

        if(isWon){
            if(viewer.showDialogWon()){
                nextLevel();
                viewer.update();
            }
        }
    }

    public void move(int x, int y) {
        int newX = indexX+x;
        int newY = indexY+y;
        this.setX(newX);
        this.setY(newY);
    }

    private void moveDown() {
        if(desktop[indexX+1][indexY] == 3){
            if(desktop[indexX+2][indexY] == 0 || desktop[indexX+2][indexY] == 4) {
                desktop[indexX+1][indexY] = 0;
                desktop[indexX+2][indexY] = 3;
            }
        }

        if(desktop[indexX+1][indexY] == 0 || desktop[indexX+1][indexY] == 4){
            desktop[indexX][indexY] = 0;
            indexX = indexX+1;
            desktop[indexX][indexY] = 1;
        }
    }

    private void moveRight() {
        if(desktop[indexX][indexY+1] == 3){
            if(desktop[indexX][indexY+2] == 0 || desktop[indexX][indexY+2] == 4) {
                desktop[indexX][indexY+1] = 0;
                desktop[indexX][indexY+2] = 3;
            }
        }

        if(desktop[indexX][indexY+1] == 0 || desktop[indexX][indexY+1] == 4){
            desktop[indexX][indexY] = 0;
            indexY = indexY+1;
            desktop[indexX][indexY] = 1;
        }
    }

    private void moveUp() {
        if(desktop[indexX-1][indexY] == 3){
            if(desktop[indexX-2][indexY] == 0 || desktop[indexX-2][indexY] == 4) {
                desktop[indexX-1][indexY] = 0;
                desktop[indexX-2][indexY] = 3;
            }
        }

        if(desktop[indexX-1][indexY] == 0 || desktop[indexX-1][indexY] == 4){
            desktop[indexX][indexY] = 0;
            indexX = indexX-1;
            desktop[indexX][indexY] = 1;
        }
    }

    private void moveLeft() {
        if(desktop[indexX][indexY-1] == 3){
            if(desktop[indexX][indexY-2] == 0 || desktop[indexX][indexY-2] == 4) {
                desktop[indexX][indexY-1] = 0;
                desktop[indexX][indexY-2] = 3;
            }
        }

        if(desktop[indexX][indexY-1] == 0 || desktop[indexX][indexY-1] == 4){
            desktop[indexX][indexY] = 0;
            indexY = indexY-1;
            desktop[indexX][indexY] = 1;
        }
    }

    public int[][] getDesktop(){
        return desktop;
    }
}
