package net.yungtechboy1.CyberCore.Manager.Factions;

import net.yungtechboy1.CyberCore.CyberCoreMain;

import java.net.ServerSocket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlotManager {
    public HashMap<String, String> List = new HashMap();
    HashMap<String,Integer> Health = new HashMap<>();
    private FactionFactory FF;

    public PlotManager(FactionFactory factionFactory) {
        FF = factionFactory;
        ReloadPlots();
    }

    public ArrayList<String> getFactionPlots(String faction) {
        ArrayList<String> p = new ArrayList<>();
        Connection c = CyberCoreMain.getInstance().FM.FFactory.getMySqlConnection();
        try {
            Statement s = c.createStatement();
            ResultSet r = s.executeQuery("SELECT * FROM plots WHERE faction LIKE '" + faction + "'");
            while (r.next()) {
                p.add(r.getString("plotid"));
            }
            c.close();
            return p;
//        Main.FFactory.allyrequest.put(getName(), fac.getName());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error sending plots to DB!!! Please report Error 'E209DB t'o an admin");
            return null;
        }
    }

    public void ReloadPlots() {
        try {
            ArrayList<String> results = new ArrayList<>();
            ResultSet r = FF.ExecuteQuerySQL("select * from `plots`");
            if (r == null) {
                System.out.println("NO PLOTS WERE FOUND");
                CyberCoreMain.getInstance().getLogger().error("NO PLOTS WERE FOUND!!!!!!");
                return;
//                throw new Exception("WHYYYYYYYYY BUSH 221122");
            }
            while (r.next()) {
                List.put(r.getInt("x") + "|" + r.getInt("z"),r.getString("faction"));
                Health.put(r.getInt("x") + "|" + r.getInt("z"), r.getInt("health"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public boolean isPlotClaimed(int x, int z){
        return List.containsKey(x+"|"+z);
    }

    public String getFactionFromPlot(int x,int z){
        if(!isPlotClaimed(x,z))return null;
        return List.get(getPlotKey(x,z));
    }

    public boolean addPlot(int x, int z, String faction){
        if(isPlotClaimed(x,z)){
            System.out.println("errrnrr Plot ttt Claimmed E34224");
            return false;
        }
        Connection c = CyberCoreMain.getInstance().FM.FFactory.getMySqlConnection();
        String k = getPlotKey(x,z);
        try {
            Statement s = c.createStatement();
            //1 = Ally Request
            //0 = Friend Requestw
            //2 = ?????
            //CyberCoreMain.getInstance().getIntTime
            s.executeQuery(String.format("INSERT INTO `plots` VALUES ('%s','%s',2)", k, faction));
            c.close();
//        Main.FFactory.allyrequest.put(getName(), fac.getName());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error sending plots to DB!!! Please report Error 'E209DB t'o an admin");
            return false;

        }
        List.put(k,faction);
        updatePlotHealth(x,z,2);
        return true;
    }
    public boolean delPlot(int x, int z, String faction){

        return delPlot(x, z, faction,false);
    }
    public boolean delPlot(int x, int z, String faction, boolean overclaim){
        if(!isPlotClaimed(x,z)){
            System.out.println("errrnrr Tring to delete Plot NOT Claimmed E3472");
            return false;
        }
        String f = getFactionFromPlot(x,z);
        if(!f.equalsIgnoreCase(faction)){
            System.out.println("Error! tring to delete plot! E799");
            return false;
        }
        Connection c = CyberCoreMain.getInstance().FM.FFactory.getMySqlConnection();
        String k = getPlotKey(x,z);
        try {
            Statement s = c.createStatement();
            s.executeQuery("DELETE FROM `plots` WHERE plotid LIKE '"+k+"'");
            c.close();
//        Main.FFactory.allyrequest.put(getName(), fac.getName());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error sending plots to DB!!! Please report Error 'E209DB t'o an admin");
            return false;

        }
        List.remove(k);
        Health.remove(k);
        return true;
    }

    public String getPlotKey(int x, int z){
        return x+"|"+z;
    }

    public void updatePlotHealth(int x, int z, int amount){
        String k = getPlotKey(x,z);
        Health.put(k,2);
    }
}
