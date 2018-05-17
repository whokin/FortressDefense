package ca.cmpt213.as3.GameLogic;

import ca.cmpt213.as3.UserInterface.UserInterface;

import java.util.*;

/**
 * This class is the controler class in charge of running the game using instances of the different game model
 * components and the UI class
 */
public class Game
{
    private int tryCount=0;
    private static final int ONEARG=1;
    private static final int TWOARGS=2;
    private static final int INCORRECT_ARGUMENT=-1;
    private static final int ADD_TANK_FAIL=0;
    private static final int VICTORY=1;
    private static final int DEFEAT=-1;
    private static final int CONTINUE_GAME=0;

    public static void main(String args[]){
        Game game=new Game();
        UserInterface userInterface=new UserInterface();
        Board board = new Board();
        Fortress fortress=new Fortress();
        TankCollection tankList = new TankCollection();

        int tankCount=Integer.parseInt(args[0]);
        boolean cheatActivated=false;

        userInterface.displayIntroMessage(tankCount);
        if(args.length==ONEARG)
        {
            try {
                tankCount=Integer.parseInt(args[0]);
            }
            catch (Exception e){
                System.out.println("first argument is invalid");
                System.exit(INCORRECT_ARGUMENT);
            }
        }
        else if(args.length==TWOARGS)
        {
            try {
                tankCount=Integer.parseInt(args[0]);
            }
            catch (Exception e){
                System.out.println("first argument is invalid");
                System.exit(INCORRECT_ARGUMENT);
            }
            if(args[1].equals("--cheat"))
            {
                cheatActivated=true;
            }
            else
            {
                System.out.println("first argument is invalid");
                System.exit(INCORRECT_ARGUMENT);
            }
        }
        game.placeTankInBoard(tankCount,board,tankList);
        if(cheatActivated)
            userInterface.displayGameBoardOnWinOrLoss(board,tankList);
        game.playGame(board,tankList,fortress,userInterface);
    }



    private String generateRandCoordinates()
    {
        StringBuilder coordinates = new StringBuilder();
        Random randRow = new Random();
        Random randCol = new Random();
        char row='A';
        int col='1';

        row+= randRow.nextInt(9);
        col+=randCol.nextInt(8);

        coordinates.append(row);
        coordinates.append((char) col);

        return  coordinates.toString();
    }

    //public void inBound()

    public void placeTankInBoard(int tankNumToBePlaced,Board board, TankCollection tankList)
    {
        while(tankNumToBePlaced > 0)
        {
            //if number of trys for any single tank exceeds 10, we terminate the program
            if(tryCount==10)
            {
                System.out.println("Sorry, we couldnt place all your tanks on the game board");
                System.exit(ADD_TANK_FAIL);
            }

            //generates the root cell/first cell for the tank placement
            String randCoordinates= generateRandCoordinates();
            //list to store successfully added coorindates of each tank
            List<String> tankCellCoordinates=new ArrayList<>();

            if(board.searchCell(randCoordinates).hasTank())
            {
                ++tryCount;
                continue;
            }

            //adds first coordinate by default to tank
            tankCellCoordinates.add(randCoordinates);
            //checks for second to 4th cell

            boolean checkIfFourCoordinatesAddedForTanks=true;
            for(int i=0;i<3;i++)
            {
                Collections.shuffle(tankCellCoordinates);
                if(!board.findNextCoordinate(tankCellCoordinates.get(0),tankCellCoordinates))
                {
                    ++tryCount;
                    checkIfFourCoordinatesAddedForTanks=false;
                    break;
                }
            }
            if(!checkIfFourCoordinatesAddedForTanks)
                continue;


            //At this point we should have a complete list of coordinates to generate a single tank
            for(int i=0;i<4;i++)
            {
                board.searchCell(tankCellCoordinates.get(i)).setHasTank(true);
            }
            --tankNumToBePlaced;
            Tank tank=new Tank(tankCellCoordinates);
            tankList.getTankList().add(tank);
            tryCount=0;
        }
    }

    public boolean playGame(Board board,TankCollection tankCollection,Fortress fortress,UserInterface userInterface)
    {
        Stack<String> coordinateStack=new Stack<>();
        while (checkForVictory(tankCollection,fortress)==CONTINUE_GAME)
        {
            userInterface.displayGameBoard(board);
            userInterface.displayFortressHealth(fortress);
            String coordinateInput=userInterface.enterMoveInput();

            Cell cellTemp=board.searchCell(coordinateInput);
            if(!cellTemp.hasTank())
            {
                System.out.println("Miss");
                cellTemp.setVisibility(true);
                tankCollection.calculateCumalativeDmgOutput();
                fortress.updateHealth(tankCollection);
                coordinateStack.push(coordinateInput);
            }
            //repeated coordinate by user then we dont want to decrement health of tank again. It essentially has no effect
            else if(cellTemp.hasTank() && coordinateStack.contains(coordinateInput))
            {
                System.out.println("Hit");
                tankCollection.calculateCumalativeDmgOutput();
                fortress.updateHealth(tankCollection);
                //do nothing since coordinate was simply repeated
            }
            else if(cellTemp.hasTank())
            {
                System.out.println("Hit!");
                cellTemp.setVisibility(true);
                int tankIndexInCollection=tankCollection.findTankViaCoordinate(coordinateInput);
                Tank tankTemp=tankCollection.getTankList().get(tankIndexInCollection);
                //Tank tankTemp=tankCollection.findTankViaCoordinate(coordinateInput);
                tankTemp.decrementTankHealth();
                tankTemp.calculateAndGetNewDamage();
                //check if tank is destroyed
                if(tankTemp.checkIsFunctioning()==false)
                {
                    tankTemp.setIsFunctioning(false);
                    tankCollection.decrementActiveTankCount();
                }

                //calculates total tank damage (by decreasing) and updates fortress health
                tankCollection.calculateCumalativeDmgOutput();
                fortress.updateHealth(tankCollection);
                coordinateStack.push(coordinateInput);
            }
            userInterface.displayAliveTanksWithDmg(tankCollection);
            System.out.println();

            if(checkForVictory(tankCollection,fortress)==VICTORY)
            {
                userInterface.displayGameBoard(board);
                userInterface.displayFortressHealth(fortress);
                System.out.println("Congratulations! You won!");

                userInterface.displayGameBoardOnWinOrLoss(board,tankCollection);
                return true;
            }
            else if(checkForVictory(tankCollection,fortress)==DEFEAT)
            {
                userInterface.displayGameBoard(board);
                userInterface.displayFortressHealth(fortress);
                System.out.println("I'm sorry, your fortress has been smashed!");

                userInterface.displayGameBoardOnWinOrLoss(board,tankCollection);
                return false;
            }

            continue;

        }
        //these lines should never execute
        if(checkForVictory(tankCollection,fortress)==VICTORY)
        {
            return true;
        }
        else
            return false;

    }



    public int checkForVictory(TankCollection tankCollection,Fortress fortress)
    {
        if(tankCollection.getAndSetActiveTankCount()<=0)
            return 1;
        else if(fortress.getHealth()<=0)
            return -1;
        else
            return 0;

    }
}
