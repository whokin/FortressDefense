package ca.cmpt213.as3.GameLogic;

/**
 * This class is a model of a fortress for the fortress defence game
 */
public class Fortress {

    final int MAXFORTRESSHEALTH=1500;
    private int health;

    public Fortress() {
        this.health = MAXFORTRESSHEALTH;
    }

    public void updateHealth(TankCollection tankList){
        health = health - tankList.getCumulativeDmgOutput();

        if(health<0)
            health=0;
    }

    public int getHealth() {
        return health;
    }
}
