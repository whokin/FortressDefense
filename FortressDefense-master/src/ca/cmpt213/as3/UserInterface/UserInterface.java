package ca.cmpt213.as3.UserInterface;

import ca.cmpt213.as3.GameLogic.*;

import java.util.Scanner;

/**
 * This class is the user interface class in charge of printing the board
 */
public class UserInterface {
    public void displayIntroMessage(int tankCount)
    {
        System.out.println("**************************************\n" +
                "Fortress Defense Game\n" +
                "**************************************\n" +
                "Starting game with "+tankCount+" tanks.\n" +
                "----------------------------\n" +
                "Welcome to Fortress Defense!\n" +
                "by Nirag and Warren\n" +
                "----------------------------\n");
    }

    public void displayGameBoard(Board board)
    {
        System.out.println("Game Board:");

        System.out.println("       1  2  3  4  5  6  7  8  9  10");   //display columns
        for(int i=0;i<10;i++)
        {
            System.out.print("    " + (char) ('A' + i)+"  ");
            for(int j=0;j<10;j++)
            {
                char charToDisplay=board.getCellMatrix()[i][j].getSymbol();
                System.out.print(charToDisplay+"  ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public void displayGameBoardOnWinOrLoss(Board board,TankCollection tankCollection)
    {
        System.out.println("Game Board:");

        System.out.println("       1  2  3  4  5  6  7  8  9  10");   //display columns
        for(int i=0;i<10;i++)
        {
            System.out.print("    " + (char) ('A' + i)+"  ");
            for(int j=0;j<10;j++)
            {
                StringBuilder str=new StringBuilder();
                str.append((char)('A'+i));
                str.append((char)('1'+j));
                Cell cellTemp=board.searchCell(str.toString());
                if(cellTemp.hasTank())
                {
                    int tankIndex=tankCollection.findTankViaCoordinate(str.toString());
                    if(cellTemp.isVisibility()) //ie the cell has been hit
                    {
                        char charToDisplay=(char)('a'+tankIndex);
                        System.out.print(charToDisplay+"  ");
                    }
                    else
                    {
                        char charToDisplay=(char)('A'+tankIndex);
                        System.out.print(charToDisplay+"  ");
                    }
                }
                else
                    System.out.print(".  ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public void displayFortressHealth(Fortress fortress) {
        System.out.println("Fortress Structure Left: "+fortress.getHealth()+".");
    }

    public void displayAliveTanksWithDmg(TankCollection tankCollection) {
        int activeTankCount=tankCollection.getAndSetActiveTankCount();
        int counter=0;
        for(int i=0;i<tankCollection.getSize();i++)
        {
            Tank tempTank=tankCollection.getTankList().get(i);
            if(tempTank.isFunctioning())
            {
                ++counter;
                System.out.println("Alive tank #"+counter+" of "+activeTankCount+" shot you for "+tempTank.getDamage()+" !");
            }
        }
    }


    public String enterMoveInput() {
        while (true) {
            System.out.println("Enter your move: ");
            Scanner scan=new Scanner(System.in);
            String input=scan.nextLine();
            input=input.replace(" ","");

            if(!checkInputValidity(input))
            {
                System.out.println("Invalid target. Please enter a coordinte such as D10.");
            }
            else
                return input.toUpperCase();
        }
    }

    private boolean checkInputValidity(String input) {
        if(input.length()<2 || input.length()>3) {
            return false;
        }

        char firstChar=input.charAt(0);
        char secondChar=input.charAt(1);

        if((firstChar >= 'A' && firstChar <= 'J') || (firstChar >= 'a' && firstChar <= 'j')) {
            if(input.length()==2)
            {
                return secondChar >= '1' && secondChar <= '9';
            }
            char thirdChar=input.charAt(2);
            return secondChar == '1' && thirdChar == '0';
        }
        return false;
    }
}
