package BaselineStuff;

import BaselineStuff.BoardPieces;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class BackgroundNull implements BoardPieces {


    @Override
    public ImageIcon getIcon() {
        return new javax.swing.ImageIcon("C:\\Users\\patelhar\\IdeaProjects\\CandyCrushSimulator\\ResourceFiles\\backgroundTile.png");
    }

    @Override
    public void buttonClicked(ActionEvent evt) {
        System.out.println("Yo, this is background no click pls");
    }


}
