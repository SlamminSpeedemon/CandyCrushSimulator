package BaselineStuff;

public class Cords {

    //is a class

    public int row;
    public int col;

    public Cords(int Row, int Col) {
        row = Row;
        col = Col;
    }

    public void add(Cords other) {
        row += other.getRow();
        col += other.getCol();
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public Cords getCopy() {
        return new Cords(row, col);
    }

    public boolean isSame(Cords other) {
        if(row == other.getRow() && col == other.getCol()) return true;
        return false;
    }

    @Override
    public String toString() {
        return "["+row+"]"+"["+col+"]";
    }
}
