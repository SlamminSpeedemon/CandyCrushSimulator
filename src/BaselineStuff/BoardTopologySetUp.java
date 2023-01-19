package BaselineStuff;

import javax.swing.text.ParagraphView;
import java.lang.reflect.Parameter;


public class BoardTopologySetUp {
    private int[][] topology; //a matrix where 1 represents valid candy tile, 0 represents background tile
    private BoardPieces[][] board;

    public BoardTopologySetUp(int[][] topology) {
        board = new BoardPieces[topology.length][topology[0].length];

        //place all
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[0].length; j++) {
                if (topology[i][j] == 0) {
                    board[i][j] = new BackgroundNull();
                } else {
                    board[i][j] = new Candy((int)(Math.random() *  Constants.maxCandyType + 1));
                }
            }
        }
    }

    public BoardPieces[][] getBoard() {
        return board;
    }
}
