package ca.cmpt213.as3.GameLogic;

/**
 * This class is a model of a cell composing the game board
 */

public class Cell {
    private String coordinates;
    private boolean visibility;
    private boolean hasTank;
    private int row;
    private int col;

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public Cell() {
        this.coordinates = "";
        this.visibility = false;
        this.hasTank = false;
        this.row=0;
        this.col=0;

    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public boolean isVisibility() {
        return visibility;
    }

    public boolean hasTank() {
        return hasTank;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

    public void setHasTank(boolean hasTank) {
        this.hasTank = hasTank;
    }

    public char getSymbol(){
        char symbol;
        if(!visibility)
            symbol = '~';

        else if (hasTank)
            symbol = 'X';
        else
            symbol = ' ';
        return symbol;
    }
}
