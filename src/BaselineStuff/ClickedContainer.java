package BaselineStuff;

import java.util.ArrayList;

public class ClickedContainer {
    //the entire class just serves as a container to store 2 variables
    //its like an interface between the environment class and the candy class

    private ArrayList<Candy> clickedCandies;

    public ClickedContainer() {
        clickedCandies = new ArrayList<>();
    }

    public void clicked(Candy clickedCandy) {
        if (clickedCandies.size() < 2) {
            clickedCandies.add(clickedCandy);
        }
    }

    public ArrayList<Candy> getClickedCandies() {
        return clickedCandies;
    }

    public void clearClicked() {
        clickedCandies.clear();
    }
}
