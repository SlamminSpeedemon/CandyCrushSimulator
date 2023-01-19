import BaselineStuff.*;

import java.util.ArrayList;
import java.util.Scanner;

public class GameEngine {

    private BoardPieces[][] board;
    private ArrayList<Cords> changedTiles;

    private ArrayList<Cords> horizontalChecked;
    private ArrayList<Cords> verticalChecked;

    private Scanner wait;

    private ArrayList<BoardPieces> poppedSpecialtyCandies;

    public GameEngine(BoardPieces[][] board) {
        changedTiles = new ArrayList();
        horizontalChecked = new ArrayList();
        verticalChecked = new ArrayList();
        poppedSpecialtyCandies = new ArrayList<>();

        wait = new Scanner(System.in);

        this.board = board;
    }


    public BoardPieces[][] matchFinder() {//after gravity, checkall
        System.out.println(board);
        //create list of cords for tiles to check
        ArrayList<Cords> tilesToCheck = new ArrayList<>();

        //enter all tiles into arraylist
        for(int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                //if(board[i][j].getClass() == BackgroundNull.class) {
                    //is a background, ignore it
                if(board[i][j].getCandyType() == -1) {
                    //is a not defined board piece
                } else {
                    tilesToCheck.add(new Cords(i,j));
                }
            }
        }


        while (tilesToCheck.size() > 2) {
            System.out.println("checking tile");
            if (board[tilesToCheck.get(0).row][tilesToCheck.get(0).col].getCandyType() < 0) {
                tilesToCheck.remove(0);
                continue;
            }

            int currentCandyType = getCandyColor(board[tilesToCheck.get(0).row][tilesToCheck.get(0).col].getCandyType());
            int currentTileRow = tilesToCheck.get(0).row;
            int currentTileCol = tilesToCheck.get(0).col;

            horizontalChecked.clear();
            verticalChecked.clear();
            System.out.println("arraylists cleared");
            int horizontal = getDirectionalCount(tilesToCheck.get(0).getCopy(),new Cords(0,1)) + getDirectionalCount(tilesToCheck.get(0).getCopy(),new Cords(0,-1))-1;
            horizontalChecked.remove(0); //first tile is double counted (both statements above include the start spot)
            int vertical = getDirectionalCount(tilesToCheck.get(0).getCopy(),new Cords(1,0)) + getDirectionalCount(tilesToCheck.get(0).getCopy(),new Cords(-1,0))-1;
            verticalChecked.remove(0); //first tile is double counted (both statements above include the start spot)
            boolean horizontalRemove = false,verticalRemove = false;

            int toSpawn = 0; //1 = stripe hori, 2 = stripe vert, 3 = wrapped, 4 = color bomb

            System.out.println("\n\nhorizontalCount is "+horizontal+"\tverticalCount is "+vertical);

            if((horizontal+vertical)==6 && horizontal!=5 && vertical!=5 && horizontal !=4 && vertical != 4) {
                //spawn wrapped candy
                toSpawn = 3;
                horizontalRemove = true;
                verticalRemove = true;
            } else if ((horizontal==3 || vertical==3) && (vertical < 4 && horizontal < 4)) {
                //simply remove candy
                if (horizontal == 3 && vertical < 4) horizontalRemove = true;
                else verticalRemove = true;
            } else if (horizontal == 4 && vertical < 3) { //spawn stripe hori
                toSpawn = 1;
                horizontalRemove = true;
            } else if (vertical == 4 && horizontal < 3) { //spawn stripe vert
                toSpawn = 2;
                verticalRemove = true;
            } else if (horizontal >= 5 || vertical >= 5) { //spawn color bomb
                toSpawn = 4;
                if (horizontal >= 5) horizontalRemove = true;
                else verticalRemove = true;
            }

            ArrayList<Integer> indicesToRemove = new ArrayList<>();

            if (verticalRemove) {
                System.out.println("Removing vertical! ToSpawn is " + toSpawn);
                for (int i = 0; i < verticalChecked.size(); i++) {
                    //save specialty candies that were popped
                    if(board[verticalChecked.get(i).row][verticalChecked.get(i).col].getCandyType() > 10) {
                        board[verticalChecked.get(i).row][verticalChecked.get(i).col].setLocation(verticalChecked.get(i));
                        poppedSpecialtyCandies.add(board[verticalChecked.get(i).row][verticalChecked.get(i).col]);
                    }
                    board[verticalChecked.get(i).row][verticalChecked.get(i).col] = new Candy(-2);
                    tilesToCheck.remove(verticalChecked.get(i));
                }

                for(int i = 0; i < verticalChecked.size(); i++) {
                    for (int j = 0; j < tilesToCheck.size(); j++) {
                        if(verticalChecked.get(i).isSame(tilesToCheck.get(j))) {
                            indicesToRemove.add(j);
                            break;
                        }
                    }
                }

                for (int i = 0; i < indicesToRemove.size(); i++) {
                    tilesToCheck.remove(indicesToRemove.get(i));
                }
            }

            indicesToRemove.clear();

            if (horizontalRemove) {
                System.out.println("Removing horizontal! ToSpawn is " + toSpawn);
                for (int i = 0; i < horizontalChecked.size(); i++) {
                    //record what speciality candies were popped
                    if(board[horizontalChecked.get(i).row][horizontalChecked.get(i).col].getCandyType() > 10) {
                        board[horizontalChecked.get(i).row][horizontalChecked.get(i).col].setLocation(horizontalChecked.get(i));
                        poppedSpecialtyCandies.add(board[horizontalChecked.get(i).row][horizontalChecked.get(i).col]);
                    }
                    board[horizontalChecked.get(i).row][horizontalChecked.get(i).col] = new Candy(-2);
                    tilesToCheck.remove(horizontalChecked.get(i));
                }

                for(int i = 0; i < horizontalChecked.size(); i++) {
                    for (int j = 0; j < tilesToCheck.size(); j++) {
                        if(horizontalChecked.get(i).isSame(tilesToCheck.get(j))) {
                            indicesToRemove.add(j);
                            break;
                        }
                    }
                }

                for (int i = 0; i < indicesToRemove.size(); i++) {
                    tilesToCheck.remove(indicesToRemove.get(i));
                }
            }

            if (!horizontalRemove && !verticalRemove) tilesToCheck.remove(0);

            //deal with specialty candies that were popped
            for (int i = 0; i < poppedSpecialtyCandies.size(); i++) {
                System.out.println("running speciality loop");
                if (poppedSpecialtyCandies.get(i).getCandyType() < 10) break;
                int candyColor = getCandyColor(poppedSpecialtyCandies.get(i).getCandyType());
                int candySpecialtyType = (poppedSpecialtyCandies.get(i).getCandyType() % 10);
                Cords poppedSpot = poppedSpecialtyCandies.get(i).getLocation();
                if (candySpecialtyType == 1) {//vertical stripes
                    System.out.println("\nVERTICAL POPPING! at " + poppedSpot);
                    printCandyArray();
                    wait.nextLine();

                    for (int j = 0; j < board.length; j++) {
                        if (board[j][poppedSpot.col].getCandyType() > 0) {
                            if (board[j][poppedSpot.col].getCandyType() > 10) {
                                board[j][poppedSpot.col].setLocation(new Cords(j,poppedSpot.col));
                                poppedSpecialtyCandies.add(board[j][poppedSpot.col]);
                            }
                            board[j][poppedSpot.col] = new Candy(-2);
                        }
                    }

                    System.out.println("\nVERTICAL POPPING DONE");
                    printCandyArray();
                    wait.nextLine();
                } else if (candySpecialtyType == 2) {//horizontal stripes
                    System.out.println("\nHORIZONTAL POPPING! at " + poppedSpot);
                    printCandyArray();
                    wait.nextLine();

                    for (int j = 0; j < board.length; j++) {
                        if (board[poppedSpot.row][j].getCandyType() > 0) {
                            if (board[poppedSpot.row][j].getCandyType() > 10) {
                                board[poppedSpot.row][j].setLocation(new Cords(poppedSpot.row,j));
                                poppedSpecialtyCandies.add(board[poppedSpot.row][j]);
                            }
                            board[poppedSpot.row][j] = new Candy(-2);
                        }
                    }

                    System.out.println("\nHORIZONTAL POPPING DONE");
                    printCandyArray();
                    wait.nextLine();
                }
            }

            poppedSpecialtyCandies.clear();

            //deal with toSpawn
            if (toSpawn > 0) {
                board[currentTileRow][currentTileCol] = new Candy(getConvertedCandy(currentCandyType, toSpawn));
            }

            //System.out.println(tilesToCheck.size());

            printCandyArray();
        }
        //System.out.println(board);

        System.out.println("returning board");
        return board;
    }

    public boolean gravity() {
        boolean allDown = true; //true if all candy pieces are as low as possible, no shifts downward needed

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j].getCandyType() == -2) {
                    allDown = false;
                    break;
                }
            }
            if (!allDown) break;
        }

        if (!allDown) {
            spamFillColumn(0);
            //need to move columns down, iterate by column
            for (int i = 0; i < board[0].length; i++) {
                //call bring down function for each column
                shiftColumnDown(i);
            }
        }

        return allDown;
    }
    private int getDirectionalCount(Cords spot, Cords vector) {
        int counter = 0;
        int candyType = getCandyColor(board[spot.row][spot.col].getCandyType());

        //need to make sure it doesn't go out of bounds!

        //System.out.println("Candy type is: " + candyType);

        while (getCandyColor(board[spot.row][spot.col].getCandyType()) == candyType) {
            System.out.println("Spot " + spot + " is " + board[spot.row][spot.col].getCandyType());
            if (getCandyColor(board[spot.row][spot.col].getCandyType()) != candyType) {
                System.out.println("breaking loop due to candy chain ends");
                break;
            }
            counter++;



            if(vector.row  == 0) {
                //we are horizontal
                horizontalChecked.add(new Cords(spot.row, spot.col));
            } else {
                //we are vertical
                verticalChecked.add(new Cords(spot.row, spot.col));
            }

            spot.add(vector);
//            if (spot.row < 0) spot.row = 0;
//            if (spot.row > board.length-1) spot.row = board.length-1;
//            if (spot.col < 0) spot.col = 0;
//            if (spot.col > board[0].length-1) spot.col = board[0].length-1;
            if (spot.row < 0) break;
            if (spot.row > board.length-1) break;
            if (spot.col < 0) break;
            if (spot.col > board[0].length-1) break;

            System.out.println("Count " + counter);
            if (horizontalChecked.size() > 4 || verticalChecked.size() > 4) {
                break;
            }

            System.out.println("Horizontal checked array " + horizontalChecked);
            System.out.println("Vertical checked array " + verticalChecked);
        }


        //wait.nextLine();

        return counter;
    }

    private int getCandyColor(int candyTypeNum) { //returns color of candy, even if stripe or wrapped candy
        String candyTypeStr = String.valueOf(candyTypeNum).substring(0,1);
        if (candyTypeNum < 0) {
            //candyTypeStr = String.valueOf(candyTypeNum).substring(1,2);
            return 0;
        }
        candyTypeNum = Integer.valueOf(candyTypeStr); //gets original color, even if stripe was used
        return candyTypeNum;
    }

    private int getConvertedCandy(int candyTypeNum, int toSpawn) { //takes in the color and returns the stripe or wrapped candy's number of that color
        if (toSpawn == 0) return  candyTypeNum;
        if (toSpawn == 4) return 9;
        String candyTypeStr = String.valueOf(candyTypeNum);
        candyTypeNum = Integer.valueOf(candyTypeStr + String.valueOf(toSpawn)); //gets original color, even if stripe was used
        return candyTypeNum;
    }

    private void shiftColumnDown(int column) { //given a row, iterate from bottom to top and move all down
        //determine if there are any spaces in column
        boolean needShift = false;
        int topSpot = 0;

        for (int i = board.length-1; i >= 0; i--) {
            if (board[i][column].getCandyType() == -2) {
                needShift = true;
            }
            if (board[i][column].getCandyType() == -1) {
                topSpot = i+1;
            }
        }

        if (needShift) {
            //shift them down
            for (int i = board.length-1; i >= 1; i--) {
                if (board[i][column].getCandyType() == -2 && board[i-1][column].getCandyType() > 0) {
                    board[i][column] = board[i-1][column];
                    board[i-1][column] = new Candy(-2);
                }
            }

            if (board[topSpot][column].getCandyType() == -2) {
                board[topSpot][column] = new Candy(999);
            } else {
                if (board[0][column].getCandyType() == -2) {
                    board[0][column] = new Candy(999);
                }
            }
        }

    }

    private void spamFillColumn(int column) {
        for (int i = board.length-1; i >= 0; i--) {
            if (board[i][column].getCandyType() == -2) {
                board[i][column] = new Candy(999);
            }

        }
    }

    public void generateOnOpenTiles() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j].getCandyType() == 999) {
                    board[i][j] = new Candy((int)(Math.random() *  Constants.maxCandyType + 1));
                }
            }
        }
    }

    public void setBoard(BoardPieces[][] board) {
        this.board = board;
    }

    public BoardPieces[][] getBoard() {
        return board;
    }

    public ArrayList<Cords> getChangedTiles() {
        return changedTiles;
    }

    public void printCandyArray() {
        for(int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                System.out.print(board[i][j].getCandyType() + "\t");
            }
            System.out.println();
        }
    }

    public void printBoardPiecesArray(BoardPieces[][] array) {

    }
}
