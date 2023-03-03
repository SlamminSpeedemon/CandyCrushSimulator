import BaselineStuff.BoardPieces;
import BaselineStuff.Cords;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class Candy implements BoardPieces {

    private int candyType;
    private ImageIcon candyIcon = new ImageIcon("C:\\Users\\patelhar\\IdeaProjects\\CandyCrushSimulator\\ResourceFiles\\nullTile.png");

    private Cords location;

    private Environment environment = null;


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


    public void setEnvironment(Environment environment) {
        this.environment = environment;
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
    public void buttonClicked(ActionEvent evt) throws InterruptedException {
        System.out.println("Aye boy, u cluked on " + candyType);

        if (environment != null) {
            environment.gotClicked(this);
        }
    }

    @Override
    public String toString() {
        return "Candy of type " + candyType + " is at location " + location;
    }

    public Candy getCopy() {
        Candy temp = new Candy(candyType);
        temp.setLocation(location.getCopy());
        temp.setEnvironment(environment);
        return temp;
    }
}
