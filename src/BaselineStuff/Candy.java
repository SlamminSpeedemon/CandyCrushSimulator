package BaselineStuff;

import BaselineStuff.BoardPieces;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class Candy implements BoardPieces {

    private int candyType;
    private ImageIcon candyIcon = new ImageIcon("C:\\Users\\patelhar\\IdeaProjects\\CandyCrushSimulator\\ResourceFiles\\nullTile.png");

    private Cords location;

    public Candy(int candyNum) {
        candyType = candyNum;
        try {
            candyIcon = new ImageIcon("C:\\Users\\patelhar\\IdeaProjects\\CandyCrushSimulator\\ResourceFiles\\candy"+candyNum+".png");
            //for some reason this never errors, it defaults to a shiny tile

            if (candyNum == -2) {
                candyIcon = new ImageIcon("C:\\Users\\patelhar\\IdeaProjects\\CandyCrushSimulator\\ResourceFiles\\nullTile.png");
            }
        } catch (Exception e){
            System.out.println("Candy icon not found, defaulting");
            candyIcon = new ImageIcon("C:\\Users\\patelhar\\IdeaProjects\\CandyCrushSimulator\\ResourceFiles\\nullTile.png");
        }
    }


    public void setEnvironment() {

    }

    public int getCandyType() {
        return candyType;
    }

    @Override
    public ImageIcon getIcon() {
        return candyIcon;
    }

    @Override
    public Cords getLocation() {
        return location;
    }

    @Override
    public void setLocation(Cords newLocation) {
        location = newLocation;
    }

    @Override
    public void buttonClicked(ActionEvent evt) {
        System.out.println("Aye boy, u cluked on " + candyType);

    }
}
