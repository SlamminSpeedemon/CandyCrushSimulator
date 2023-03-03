package BaselineStuff;

import javax.swing.*;


public interface BoardPieces {

    //javax.swing.ImageIcon icon = new ImageIcon("C:\\Users\\patelhar\\IdeaProjects\\CandyCrushSimulator\\ResourceFiles\\nullTile.png");
    //above is final... not sure if I can change it to be mutable

    //Cords location = new Cords(-1,-1);
    //above automatically becomes final, so I can't use it

    default ImageIcon getIcon() {
        return new ImageIcon("C:\\Users\\patelhar\\IdeaProjects\\CandyCrushSimulator\\ResourceFiles\\nullTile.png");
    }

    default void setIcon() {

    }

    default Cords getLocation() {
        return new Cords(-1,-1);
    }

    default void setLocation(Cords newLocation) {

    }

    default void buttonClicked(java.awt.event.ActionEvent evt) throws InterruptedException {
        System.out.println("button not defined");
    }

    default int getCandyType() {
        return -1;
    }
}
