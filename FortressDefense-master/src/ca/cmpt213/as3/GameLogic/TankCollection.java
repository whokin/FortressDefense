package ca.cmpt213.as3.GameLogic;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is in charge of managing a collection of tanks in a List
 */
public class TankCollection {


    private List<Tank> tankList;
    private int activeTankCount;
    private int cumulativeDmgOutput;

    TankCollection(){
        this.tankList = null;
        this.activeTankCount =0;
        this.cumulativeDmgOutput = 0;
        tankList=new ArrayList<>();
    }

    public int getAndSetActiveTankCount() {
        int count=0;
        for(int i=0;i<tankList.size();i++)  //finds number valid tanks and their index's in TankCollection
        {
            if(tankList.get(i).isFunctioning())
            {
                ++count;
            }
        }
        activeTankCount=count;
        return activeTankCount;
    }

    public void decrementActiveTankCount()
    {
        --activeTankCount;
    }

    public int getSize()
    {
        return tankList.size();
    }

    public List<Tank> getTankList() {

        return tankList;
    }

    public void calculateCumalativeDmgOutput()
    {
        cumulativeDmgOutput=0;
        for(int i=0;i<tankList.size();i++)
        {
            if(tankList.get(i).isFunctioning())
                cumulativeDmgOutput+=tankList.get(i).getDamage();
        }
    }

    public int findTankViaCoordinate(String coordinate)
    {
        int tankIndex=0;
        boolean flag=false;

        for(int i=0;i<tankList.size();i++,tankIndex++)
        {
            for(int j=0;j<4;j++)
            {
                if(tankList.get(i).getTankCoordinates().get(j).equals(coordinate))
                {
                    flag=true;
                    break;
                }
            }
            if(flag==true)
                break;
        }
        //return tank at specified index from tankcollection

        //return tankList.get(tankIndex);
        return tankIndex;
    }

    public int getCumulativeDmgOutput() {
        return cumulativeDmgOutput;
    }


}
