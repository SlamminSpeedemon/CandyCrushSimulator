import BaselineStuff.Cords;

import java.util.ArrayList;

public class ClickedContainer {
    //the entire class just serves as a container to store 2 variables
    //its like an interface between the environment class and the candy class

    private ArrayList<Candy> clickedCandies;

    public ClickedContainer() {
        clickedCandies = new ArrayList<>();
    }

    public boolean clicked(Candy clickedCandy) {
        if (clickedCandies.size() <= 2) {
            clickedCandies.add(clickedCandy);
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<Candy> getClickedCandies() {
        return clickedCandies;
    }

    public void clearClicked() {
        clickedCandies.clear();
    }

    public boolean areCandiesNextToEachOther() {

        System.out.println(clickedCandies.get(0).toString());


        Cords cordDiff = clickedCandies.get(0).getLocation().getDiff(clickedCandies.get(1).getLocation());

        System.out.println("Cords diff for clicked is " + cordDiff);
        System.out.println("Cords diff magnitude is " + cordDiff.getMagnitude());

        if (cordDiff.getMagnitude() == 1) {
            return true;
        } else {
            return false;
        }
    }

    public void switchCandies() {
        Cords tempHolder = clickedCandies.get(0).getLocation();
        clickedCandies.get(0).setLocation(clickedCandies.get(1).getLocation().getCopy());
        clickedCandies.get(1).setLocation(tempHolder.getCopy());

        System.out.println("Set locations for the switched candies have been updated");

    }
}
