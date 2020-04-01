package net.yungtechboy1.CyberCore.Manager.Factions;

import net.yungtechboy1.CyberCore.CyberCoreMain;

import java.util.ArrayList;

public class FactionLocalCache {
    private Faction Fac;

    private ArrayList<String> Allies = new ArrayList<>();
    private ArrayList<String> Enemies = new ArrayList<>();

    private int LastUpdated = 0;

    public FactionLocalCache(Faction faction) {
        Fac = faction;
    }

    public ArrayList<String> getAllies() {
        return Allies;
    }

    public Faction getFac() {
        return Fac;
    }

    public ArrayList<String> getEnemies() {
        return Enemies;
    }

    public int getLastUpdated() {
        return LastUpdated;
    }

    public void addAlly(String name){
        Allies.add(name);
    }

    public void Update(){

    }

    public int getTimeToInt(){
        return CyberCoreMain.getInstance().getIntTime();
    }
}
