package com.gao.gomoku.counter;

public class AIChessCounter extends ChessCounter {

    public AIChessCounter(){
        super();
        gameMode = GameMode.SINGLE;
    }

    //lépés
    @Override
    public void step(){
        if(!mustPressed())
            pointCounter();
    }

    //megszámít az adott darabnak két határa lyokas-e
    private int countLimit(int x, int y, int mode){
        int hasLimit =0;
        int x1 = x;
        int x2 = x;
        int y1 = y;
        int y2 = y;

        while (true){
            if(x1 < 0 || y1 < 0 || x1 >= 15 || y1 >= 15 || (chess[x1][y1] != 0 && chess[x1][y1] != chess[x][y])){
                hasLimit++;
                break;
            }
            else if(chess[x1][y1] == 0)
                break;
            else {
                switch (mode) {
                    case 0 -> x1++;
                    case 1 -> y1++;
                    case 2 -> {
                        x1++;
                        y1--;
                    }
                    case 3 -> {
                        x1++;
                        y1++;
                    }
                    default -> throw new IllegalStateException("Unexpected value: " + mode);
                }
            }
        }

        while (true){
            if(x2 < 0 || y2 < 0 || x2 >= 15 || y2 >= 15 || (chess[x2][y2] != 0 && chess[x2][y2] != chess[x][y])){
                hasLimit++;
                break;
            }
            else if(chess[x2][y2] == 0)
                break;
            else{
                switch (mode) {
                    case 0 -> x2--;
                    case 1 -> y2--;
                    case 2 -> {
                        x2--;
                        y2++;
                    }
                    case 3 -> {
                        x2--;
                        y2--;
                    }
                    default -> throw new IllegalStateException("Unexpected value: " + mode);
                }
            }
        }
        return hasLimit;
    }

    //számolási logikusok
    //ezek az standerd számolási alapértemezett, mindenkinek szerepel egy különleges pontot
    private int five(int x, int y){
        int count = 0;
        for(int m = 0; m < 4; m++){
            if(countLine(x, y, m) >= 5)
                count++;
        }
        return count;
    }

    private int livedFour(int x, int y){
        int count = 0;
        for(int m = 0; m < 4; m++){
             if(countLine(x, y, m) == 4 && countLimit(x, y, m) == 0)
                 count++;
        }
        return count;
    }

    private int blockedFour(int x, int y){
        int count = 0;
        for(int m = 0; m < 4; m++){
            if(countLine(x, y, m) == 4 && countLimit(x, y, m) == 1)
                count++;
        }
        return count;
    }

    private int livedThree(int x, int y){
        int count = 0;
        for(int m = 0; m < 4; m++){
            if(countLine(x, y, m) == 3 && countLimit(x, y, m) == 0)
                count++;
        }
        return count;
    }

    private int blockedThree(int x, int y){
        int count = 0;
        for(int m = 0; m < 4; m++){
            if(countLine(x, y, m) == 3 && countLimit(x, y, m) == 1)
                count++;
        }
        return count;
    }

    private int livedTwo(int x, int y){
        int count = 0;
        for(int m = 0; m < 4; m++){
            if(countLine(x, y, m) == 2 && countLimit(x, y, m) == 0)
                count++;
        }
        return count;
    }

    private int blockedTwo(int x, int y){
        int count = 0;
        for(int m = 0; m < 4; m++){
            if(countLine(x, y, m) == 2 && countLimit(x, y, m) == 1)
                count++;
        }
        return count;
    }

    /**
     *
     * @param color
     * @return
     */
    private boolean mustPressedLogic1(int color){
        for(int i = 0; i < 15; i++){
            for (int j = 0; j < 15; j++){
                if(chess[i][j] == 0){
                    chess[i][j] = color;
                    if(five(i,j) >= 1){
                        chess[i][j] = 2;
                        pushToStack(i,j);
                        return true;
                    }
                    chess[i][j] = 0;
                }
            }
        }
        return false;
    }

    private boolean mustPressedLogic2(int color){
        for(int i = 0; i < 15; i++){
            for (int j = 0; j < 15; j++){
                if(chess[i][j] == 0){
                    chess[i][j] = color;
                    if(livedFour(i,j) >= 1 ||
                            blockedFour(i,j) >= 2 ||
                            (blockedFour(i,j) >= 1 && livedThree(i,j) >= 1)){
                        chess[i][j] = 2;
                        pushToStack(i,j);
                        return true;
                    }
                    chess[i][j] = 0;
                }
            }
        }
        return false;
    }

    private boolean mustPressedLogic3(int color){
        for(int i = 0; i < 15; i++){
            for (int j = 0; j < 15; j++){
                if(chess[i][j] == 0){
                    chess[i][j] = color;
                    if (livedThree(i,j) >= 2){
                        chess[i][j] = 2;
                        pushToStack(i,j);
                        return true;
                    }
                    chess[i][j] = 0;
                }
            }
        }
        return false;
    }

    private boolean mustPressed(){
        return mustPressedLogic1(2) ||
                mustPressedLogic1(1) ||
                mustPressedLogic2(1) ||
                mustPressedLogic2(2) ||
                mustPressedLogic3(1) ||
                mustPressedLogic3(2);
    }

    //pontszámoló, megkeres a leghatékonyasabb pont
    private void pointCounter(){
        int x = -1;
        int y = -1;
        int maxPoint = -1;
        for(int i = 0; i < 15; i++){
            for (int j = 0; j < 15; j++){
                if(chess[i][j] == 0){
                    int point = 0;

                    chess[i][j] = 1;
                    point += blockedFour(i,j) * 150;
                    point += livedThree(i,j) * 100;
                    point += blockedThree(i,j) * 15;
                    point += livedTwo(i,j) * 20;
                    point += blockedTwo(i,j) * 5;

                    chess[i][j] = 2;
                    point += blockedFour(i,j) * 130;
                    point += livedThree(i,j) * 90;
                    point += blockedThree(i,j) * 20;
                    point += livedTwo(i,j) * 80;
                    point += blockedTwo(i, j);

                    chess[i][j] = 0;
                    if(point > maxPoint){
                        x = i;
                        y = j;
                        maxPoint = point;
                    }
                }
            }
        }
        chess[x][y] = 2;
        pushToStack(x,y);
    }

}
