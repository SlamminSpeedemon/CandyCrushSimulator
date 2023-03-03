import BaselineStuff.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Environment extends JFrame {

    private BoardPieces[][] board;
    private JButton[][] jButtss;
    private JFrame mainDisplayFrame;

    private int rows;
    private int cols;

    private Cords[] selected;

    private Scanner waiter;

    private GameEngine gameEngine;

    private JPanel keyListener; //get input

    private ClickedContainer clickedCandies;

    public Environment(int rows, int cols) {
        board = new BoardPieces[rows][cols];
//        for (int i = 0; i < rows; i++) {
//            for (int j = 0; j < cols; j++) {
//                if (Math.random() * 2 > 1) {
//                    board[i][j] = new BackgroundNull();
//                } else {
//                    board[i][j] = new Candy((int)(Math.random() * 2 + 1));
//                }
//
//            }
//        }
        int[][] topology = {{0,0,0,0,1,1,1,1,1,1},
                {0,1,0,1,1,1,1,1,0,1},
                {0,1,0,1,1,1,1,1,0,1},
                {0,1,0,1,1,1,1,1,0,1},
                {1,1,0,1,1,1,1,1,0,1},
                {1,1,0,1,1,1,1,1,0,1},
                {1,1,0,1,1,1,1,1,0,1},
                {1,1,0,1,1,1,1,1,0,1},
                {1,1,0,1,1,1,1,1,0,1},
                {1,1,0,1,1,1,1,1,0,1}};


        BoardTopologySetUp setUp = new BoardTopologySetUp(topology);
        board = setUp.getBoard();

        jButtss = new JButton[board.length][board[0].length];
        mainDisplayFrame = new JFrame();

        clickedCandies = new ClickedContainer();

        this.rows = rows;
        this.cols = cols;

        waiter = new Scanner(System.in);

        gameEngine = new GameEngine(board);
        initComponents();
    }

    public void initComponents() {
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[0].length; j++) {
                jButtss[i][j] = new JButton();
            }
        }

        for (int i = 0; i < jButtss.length ; i++) {
            //System.out.println("I iterator is "+i);
            for (int j = 0; j < jButtss[i].length; j++) {
                //System.out.println("J iterator is "+j);
                jButtss[i][j].setIcon(board[i][j].getIcon());

                int finalJ = j;
                int finalI = i;
                jButtss[i][j].addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        try {
                            board[finalI][finalJ].buttonClicked(evt);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });

                jButtss[i][j].setBounds(1+j*32,1+i*32,32,32);

                mainDisplayFrame.add(jButtss[i][j]);
            }
        }
        mainDisplayFrame.setSize((cols * 1 * 32 + 18),(rows * 1 * 32 + 43));
        mainDisplayFrame.setLayout(null);
        mainDisplayFrame.setVisible(true);
        mainDisplayFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        gameEngine.setEnvironment(this);
    }

    public void updateComponents() throws InterruptedException {
        gameEngine.matchFinder();
        System.out.println("matches found");
        boolean runner = false;
        while (!runner) {
            runner = gameEngine.gravity();
//            System.out.println("doing gravity");
//            System.out.println("runner is " + runner);
//            gameEngine.printCandyArray();
//            waiter.nextLine();
//            updateBoardOnly();

            gameEngine.generateOnOpenTiles();
//            gameEngine.printCandyArray();
//            updateBoardOnly();
//            waiter.nextLine();
        }

        board = gameEngine.getBoard();

        for (int i = 0; i < jButtss.length ; i++) {
            for (int j = 0; j < jButtss[i].length; j++) {
                jButtss[i][j].setIcon(board[i][j].getIcon());
            }
        }
    }

    public void updateBoardOnly() {
        board = gameEngine.getBoard();

        for (int i = 0; i < jButtss.length ; i++) {
            for (int j = 0; j < jButtss[i].length; j++) {
                jButtss[i][j].setIcon(board[i][j].getIcon());
            }
        }
    }

    public void gotClicked(Candy clickedCandy) throws InterruptedException {
        if (clickedCandies.clicked(clickedCandy)) {
            //we have selected 2 or less candies and the candies have been added
        } else {
            //more than 2 have been selected or candy was not added
            clickedCandies.clearClicked();
        }

        if (clickedCandies.getClickedCandies().size() == 2) {
            //2 valid candies have been selected, see if they are next to each other
            if (clickedCandies.areCandiesNextToEachOther()) {
                //switch the candies

                System.out.println("switiching candies");
                Cords cord1 = clickedCandies.getClickedCandies().get(0).getLocation();
                Cords cord2 = clickedCandies.getClickedCandies().get(1).getLocation();

                Candy tempCandy = (Candy) board[cord1.row][cord1.col];
                Candy temp = tempCandy.getCopy();
                board[cord1.row][cord1.col] = ((Candy) board[cord2.row][cord2.col]).getCopy();
                board[cord2.row][cord2.col] = temp;

                updateComponents();

            } else {
                clickedCandies.clearClicked();
            }
        }


    }


}
