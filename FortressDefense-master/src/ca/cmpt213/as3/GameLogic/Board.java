package ca.cmpt213.as3.GameLogic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class is a collection of Cell objects and is in charge or simulating and managing the game board.
 */
public class Board {

    private Cell[][] cellMatrix;

    public Board() {
        cellMatrix=new Cell[10][10];
        for(int i=0;i<10;i++)
        {
            for(int j=0;j<10;j++)
            {
                Cell cell=new Cell();
                StringBuilder coordinates=new StringBuilder();
                if(j==9)
                {
                    coordinates.append((char)('A'+i));
                    coordinates.append(("10"));
                    cell.setCoordinates(coordinates.toString());
                    cell.setRow(i);
                    cell.setCol(j);
                    cellMatrix[i][j]=cell;
                    break;
                }
                coordinates.append((char)('A'+i));
                coordinates.append((char)('0'+j+1));
                cell.setCoordinates(coordinates.toString());
                cell.setRow(i);
                cell.setCol(j);
                cellMatrix[i][j]=cell;
            }
        }
    }

    public Cell[][] getCellMatrix() {
        return cellMatrix;
    }

    public boolean findNextCoordinate(String coordinates, List<String> tankCoordinates)
    {
        final int UP=1;
        final int RIGHT=2;
        final int DOWN=3;
        final int LEFT=4;

        List<Integer> shuffleDirectionChoice  = new ArrayList<>();
        shuffleDirectionChoice.add(UP);
        shuffleDirectionChoice.add(RIGHT);
        shuffleDirectionChoice.add(DOWN);
        shuffleDirectionChoice.add(LEFT);

        Collections.shuffle(shuffleDirectionChoice);

        for(int i=0;i<4;i++)
        {
            switch (shuffleDirectionChoice.get(0))
            {
                case UP:    {
                    if(checkTop(coordinates,tankCoordinates))
                        return true;
                    break;
                }
                case RIGHT: {
                    if(checkRight(coordinates,tankCoordinates))
                        return true;
                    break;
                }
                case DOWN: {
                    if(checkBottom(coordinates,tankCoordinates))
                        return true;
                    break;
                }
                case LEFT: {
                    if(checkLeft(coordinates,tankCoordinates))
                        return true;
                    break;
                }
            }
            shuffleDirectionChoice.remove(0);
        }
        return false;
    }

    private boolean checkSafetyConditions(String direction, List<String> tankCooridnates)
    {
        if(isOutOfBounds(direction))
            return false;
        if(searchCell(direction).hasTank())
            return false;
        if(tankCooridnates.size()==1)
            return true;
        if(tankCooridnates.size()>1)
        {
            for(int i=1;i<tankCooridnates.size();i++)
            {
                if(direction.equals(tankCooridnates.get(i)))
                    return false;
            }
        }
        return true;
    }

    private boolean checkTop(String coordinates, List<String> tankCooridnates)
    {
        char row=(char)(coordinates.charAt(0)-1);
        StringBuilder stringBuilder=new StringBuilder(coordinates);
        stringBuilder.setCharAt(0,row);

        String Up=stringBuilder.toString();
        if(!checkSafetyConditions(Up,tankCooridnates))
            return false;
        else
        {
            tankCooridnates.add(Up);
            return true;
        }

    }
    private boolean checkBottom(String coordinates, List<String> tankCooridnates)
    {
        char row=(char)(coordinates.charAt(0)+1);
        StringBuilder stringBuilder=new StringBuilder(coordinates);
        stringBuilder.setCharAt(0,row);

        String down=stringBuilder.toString();
        if(!checkSafetyConditions(down,tankCooridnates))
            return false;
        else
        {
            tankCooridnates.add(down);
            return true;
        }
    }
    private boolean checkRight(String coordinates, List<String> tankCooridnates)
    {
        StringBuilder stringBuilder=new StringBuilder(coordinates);
        if(coordinates.length()==3)
            return false;   //from 10 to 11 is out of bounds
        char col=(char)(coordinates.charAt(1)+1);

        stringBuilder.setCharAt(1,col);
        String right=stringBuilder.toString();
        if(!checkSafetyConditions(right,tankCooridnates))
            return false;
        else
        {
            tankCooridnates.add(right);
            return true;
        }

    }
    private boolean checkLeft(String coordinates, List<String> tankCooridnates)
    {
        StringBuilder stringBuilder=new StringBuilder(coordinates);
        char col=(char)(coordinates.charAt(1)-1);

        stringBuilder.setCharAt(1,col);
        String left=stringBuilder.toString();
        if(!checkSafetyConditions(left,tankCooridnates))
            return false;
        else
        {
            tankCooridnates.add(left);
            return true;
        }
    }


    public Cell searchCell(String coordinates){
        int row=0,col=0;
        row=coordinates.charAt(0)-65;
        if(coordinates.length()==2)
            col=(int)(coordinates.charAt(1)-49);
        if(coordinates.length()==3)
            col=9;
        return cellMatrix[row][col];
    }

    private boolean isOutOfBounds(String coordinates) {
        char row;
        char col;
        col = coordinates.charAt(1);
        row = coordinates.charAt(0);
        if (row < 'A' || row > 'J')
            return true;
        if (coordinates.length() == 2) {
            if (col < '1' || col > '9')
                return true;
        }
        return coordinates.length() == 3 && (coordinates.charAt(1) != '1' || coordinates.charAt(2) != '0');
    }
}

