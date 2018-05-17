package ca.cmpt213.as3.GameLogic;

import java.util.ArrayList;
import java.util.List;

public class Tank {
    final int TANKHEALTHMAX =4;
    private int health;
    private boolean isFunctioning;
    private int damage;
    private List<String> tankCoordinates;


    public int getDamage() {
        return damage;
    }

    public List<String> getTankCoordinates() {
        return tankCoordinates;
    }

    public void setTankCoordinates(List<String> tankCoordinates) {
        this.tankCoordinates = tankCoordinates;
    }

    Tank(){
        this.health = TANKHEALTHMAX;
        this.damage = this.calculateAndGetNewDamage();
        this.isFunctioning=true;
        tankCoordinates=new ArrayList<>();
    }

    public void decrementTankHealth()
    {
        --this.health;
    }

    Tank(List<String> tankCoordinates)
    {
        this.health = TANKHEALTHMAX;
        this.isFunctioning=true;
        this.damage = this.calculateAndGetNewDamage();
        this.tankCoordinates=tankCoordinates;
    }

    int calculateAndGetNewDamage(){

        if(health==4)
            damage = 20;
        else if(health==3)
            damage=5;
        else if (health==2)
            damage=2;
        else if(health==1)
            damage=1;
        else
            damage=0;
        return damage;
    }

    public boolean checkIsFunctioning()
    {
        if(health<=0)
        {
            isFunctioning=false;
            return false;
        }
        else
            return true;
    }

    public boolean isFunctioning(){
        return this.isFunctioning;
    }

    public void setIsFunctioning(boolean status) {
        this.isFunctioning = status;
    }


}
