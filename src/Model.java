import java.util.ArrayList;

public class Model {
    private Viewer viewer;
    private int[][] desktop;
    private int indexX;
    private int indexY;
    private int currentLevel = 0;
    private Levels levels;
    private ClientSokoban clientSokoban;
    private int[][] currentCrossIndexes;

    public Model(Viewer viewer) {
        levels = new Levels();
        this.viewer = viewer;
        clientSokoban = new ClientSokoban();
        startNextLevel();
    }

    public void setLevelOne() {
        desktop = new int[][]{
                {2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
                {2, 0, 0, 0, 0, 0, 0, 0, 0, 2},
                {2, 0, 0, 0, 0, 0, 0, 0, 2, 2},
                {2, 0, 0, 0, 3, 2, 3, 0, 2, 2},
                {2, 0, 0, 0, 3, 0, 0, 3, 2, 2},
                {2, 2, 2, 2, 3, 0, 3, 0, 2, 2},
                {2, 4, 4, 0, 0, 0, 2, 0, 0, 2},
                {2, 2, 4, 0, 0, 0, 0, 0, 0, 2},
                {2, 4, 4, 0, 0, 0, 0, 0, 0, 2},
                {2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
        };
        indexX = 4;
        indexY = 4;
        desktop[indexX][indexY] = 1;
    }

    public void setLevelTwo() {
        desktop = new int[][]{
                {2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
                {2, 0, 0, 2, 0, 0, 0, 0, 2, 2},
                {2, 0, 0, 3, 0, 0, 0, 0, 2, 2},
                {2, 0, 0, 0, 2, 2, 0, 0, 2, 2},
                {2, 2, 0, 2, 2, 2, 3, 0, 4, 2},
                {2, 2, 0, 0, 2, 2, 0, 0, 0, 2},
                {2, 2, 0, 3, 0, 2, 0, 0, 3, 2},
                {2, 4, 0, 0, 2, 2, 0, 0, 0, 2},
                {2, 2, 2, 4, 2, 2, 4, 0, 2, 2},
                {2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
        };
        indexX = 1;
        indexY = 1;
        desktop[indexX][indexY] = 1;

    }

    public void setLevelThree() {
        desktop = new int[][]{
                {2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
                {2, 0, 0, 2, 0, 0, 2, 0, 0, 2},
                {2, 0, 3, 2, 0, 3, 2, 0, 4, 2},
                {2, 0, 0, 2, 0, 0, 2, 0, 0, 2},
                {2, 2, 0, 2, 0, 0, 0, 0, 4, 2},
                {2, 0, 0, 0, 0, 0, 0, 0, 0, 2},
                {2, 0, 0, 2, 0, 0, 2, 0, 4, 2},
                {2, 0, 3, 2, 0, 3, 2, 0, 0, 2},
                {2, 0, 0, 2, 0, 0, 2, 0, 4, 2},
                {2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
        };
        indexX = 1;
        indexY = 1;
        desktop[indexX][indexY] = 1;

    }

    public void setLevelFromFile(String path, int x, int y){
        try {
            desktop = levels.nextLevel(path);
            indexX = x;
            indexY = y;
            desktop[indexX][indexY] = 1;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setLevelFromServer(String levelNumber, int x, int y){
        String desktopStr = clientSokoban.requestRemoteLevel(levelNumber);
        if(desktopStr != null && !desktopStr.equals("invalid level number")){
            desktopStr = desktopStr.replaceAll("N", "\n");
            desktop = levels.convertStrTo2DArray(desktopStr);
            indexX = x;
            indexY = y;
            desktop[indexX][indexY] = 1;
        } else{
            viewer.showDialogBadServerResponse();
        }

    }

    public void move(String direction) {
        if (direction.equals("up")) {
            moveUp();
        } else if (direction.equals("right")) {
            moveRight();
        } else if (direction.equals("down")) {
            moveDown();
        } else if (direction.equals("left")) {
            moveLeft();
        }
        checkGoal();
        viewer.update();
        won();
    }

    private void checkGoal() {
        for (int[] row : currentCrossIndexes) {
            if (desktop[row[0]][row[1]] == 0) {
                desktop[row[0]][row[1]] = 4;
            }
        }
    }

    public void restartLevel(int level) {
        if (level == 1) {
            setLevelOne();
        } else if (level == 2) {
            setLevelTwo();
        } else if (level == 3) {
            setLevelThree();
        } else if (level == 4) {
            setLevelFromFile("src/levels/level4.sok", 1, 1);
        } else if (level == 5) {
            setLevelFromFile("src/levels/level5.sok", 1, 1);
        } else if (level == 6) {
            setLevelFromFile("src/levels/level6.sok", 1, 1);
        } else if (level == 7) {
            setLevelFromServer("7", 1,1);
        } else if (level == 8) {
            setLevelFromServer("8", 1,1);
        } else if (level == 9) {
            setLevelFromServer("9", 1,1);
        }
        updateGoalCoords();

    }

    public void startNextLevel() {
        this.currentLevel++;
        restartLevel(this.currentLevel);
    }

    private void updateGoalCoords() {
        ArrayList<Integer> xCrossIndexes = new ArrayList<>();
        ArrayList<Integer> yCrossIndexes = new ArrayList<>();

        for(int i=0; i < desktop.length; i++){
            for(int j=0; j < desktop[i].length; j++){
                if(desktop[i][j] == 4){
                    xCrossIndexes.add(i);
                    yCrossIndexes.add(j);
                }
            }
        }
        currentCrossIndexes = new int[xCrossIndexes.size()][2];
        for(int i=0; i < xCrossIndexes.size(); i++){
            currentCrossIndexes[i][0] = xCrossIndexes.get(i);
            currentCrossIndexes[i][1] = yCrossIndexes.get(i);
        }

    }

    private void won() {
        boolean isWon = true;
        int countClosedBox = 0;

        for(int i=0; i<currentCrossIndexes.length; i++){
            if(desktop[currentCrossIndexes[i][0]][currentCrossIndexes[i][1]] == 3){
                countClosedBox++;
            }
        }

        if(countClosedBox != currentCrossIndexes.length){
            isWon = false;
        }

        if (isWon) {
            if (viewer.showDialogWon()) {
                System.out.println("WIN");
                startNextLevel();
                viewer.update();
            }
        }
    }
    
    public void startChoosedLevel(int level){
        this.currentLevel = level;
        restartLevel(level);
        viewer.update();
    } 


    private void moveDown() {
        if (desktop[indexX + 1][indexY] == 3) {
            if (desktop[indexX + 2][indexY] == 0 || desktop[indexX + 2][indexY] == 4) {
                desktop[indexX + 1][indexY] = 0;
                desktop[indexX + 2][indexY] = 3;
            }
        }

        if (desktop[indexX + 1][indexY] == 0 || desktop[indexX + 1][indexY] == 4) {
            desktop[indexX][indexY] = 0;
            indexX = indexX + 1;
            desktop[indexX][indexY] = 1;
        }
    }

    private void moveRight() {
        if (desktop[indexX][indexY + 1] == 3) {
            if (desktop[indexX][indexY + 2] == 0 || desktop[indexX][indexY + 2] == 4) {
                desktop[indexX][indexY + 1] = 0;
                desktop[indexX][indexY + 2] = 3;
            }
        }

        if (desktop[indexX][indexY + 1] == 0 || desktop[indexX][indexY + 1] == 4) {
            desktop[indexX][indexY] = 0;
            indexY = indexY + 1;
            desktop[indexX][indexY] = 1;
        }
    }

    private void moveUp() {
        if (desktop[indexX - 1][indexY] == 3) {
            if (desktop[indexX - 2][indexY] == 0 || desktop[indexX - 2][indexY] == 4) {
                desktop[indexX - 1][indexY] = 0;
                desktop[indexX - 2][indexY] = 3;
            }
        }

        if (desktop[indexX - 1][indexY] == 0 || desktop[indexX - 1][indexY] == 4) {
            desktop[indexX][indexY] = 0;
            indexX = indexX - 1;
            desktop[indexX][indexY] = 1;
        }
    }

    private void moveLeft() {
        if (desktop[indexX][indexY - 1] == 3) {
            if (desktop[indexX][indexY - 2] == 0 || desktop[indexX][indexY - 2] == 4) {
                desktop[indexX][indexY - 1] = 0;
                desktop[indexX][indexY - 2] = 3;
            }
        }

        if (desktop[indexX][indexY - 1] == 0 || desktop[indexX][indexY - 1] == 4) {
            desktop[indexX][indexY] = 0;
            indexY = indexY - 1;
            desktop[indexX][indexY] = 1;
        }
    }

    public int[][] getDesktop() {
        return desktop;
    }

    public void closeServerConnection(){
        clientSokoban.requestRemoteLevel("cancel");
        System.exit(0);
    }
}
