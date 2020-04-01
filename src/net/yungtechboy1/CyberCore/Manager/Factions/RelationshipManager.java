package net.yungtechboy1.CyberCore.Manager.Factions;

import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.CyberUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class RelationshipManager {
    protected final int MustUpdateEvery = 60 * 5;//5 Mins
    public ArrayList<String> AllyList = new ArrayList<>();
    public ArrayList<String> EnemyList = new ArrayList<String>();
    public int LastUpdated = 0;
    private FactionFactory FF;

    public RelationshipManager(FactionFactory factionFactory) {

        FF = factionFactory;
    }

    public String FactionNamesToKey(String fac1, String fac2) {
        return fac1 + "|" + fac2;
    }

    public boolean isAllys(String fac1, String fac2) {
        String a1 = FactionNamesToKey(fac1, fac2);
        String a2 = FactionNamesToKey(fac2, fac1);
        return (AllyList.contains(a1) || AllyList.contains(a2));
    }

    public boolean isEnemy(String fac1, String fac2) {
        String a1 = FactionNamesToKey(fac1, fac2);
        String a2 = FactionNamesToKey(fac2, fac1);
        return (EnemyList.contains(a1) || EnemyList.contains(a2));
    }

    public int TimeToInt() {
        return CyberUtils.getIntTime();

    }

    public void update() {
        update(false);
    }

    public void update(boolean force) {
        //      100        + 600              <=   800
        if (LastUpdated + MustUpdateEvery <= TimeToInt()) {
            if (!force) return;
        }
        Connection c = CyberCoreMain.getInstance().FM.FFactory.getMySqlConnection();
        try {
            Statement s = c.createStatement();
            ResultSet r = s.executeQuery("SELECT * FROM `Ally`");
            AllyList.clear();
            while (r.next()) {
                String k = r.getString("key");
                AllyList.add(k);
            }
            r.close();
            c.close();
//        Main.FFactory.allyrequest.put(getName(), fac.getName());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error UPDATING ALLY FROM DB!!! Please report Error 'E65DB' to an admin");

        }
    }

    public boolean removeAllyRelationship(String fac1, String fac2) {
        if (!isAllys(fac1, fac2)) {
            System.out.println("Error! Facctions are NOT already allied!!!");
            return false;
        }


        String k1 = FactionNamesToKey(fac1, fac2);
        String k2 = FactionNamesToKey(fac2, fac1);
        int time = CyberCoreMain.getInstance().getIntTime();
        Connection c = CyberCoreMain.getInstance().FM.FFactory.getMySqlConnection();
        try {
            Statement s = c.createStatement();
            //1 = Ally Request
            //0 = Friend Requestw
            //2 = ?????
            //CyberCoreMain.getInstance().getIntTime
            s.executeUpdate("DELETE FROM `Ally` WHERE `key` LIKE k1");
            s.executeUpdate("DELETE FROM `Ally` WHERE `key` LIKE k2");
//            s.executeQuery(String.format("INSERT INTO `plots` VALUES ('%s','%s',2)", k, faction));
            c.close();
            return true;
//        Main.FFactory.allyrequest.put(getName(), fac.getName());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error DELETING ALLY FROM DB!!! Please report Error 'E61DB' to an admin");
            return false;

        }


    }

    public boolean addAllyRelationship(String fac1, String fac2) {

        if (isAllys(fac1, fac2)) {
            System.out.println("Error! Facctions are already allied!!!");
            return false;
        }

        String k = FactionNamesToKey(fac1, fac2);
        int time = CyberCoreMain.getInstance().getIntTime();
        Connection c = CyberCoreMain.getInstance().FM.FFactory.getMySqlConnection();
        try {
            Statement s = c.createStatement();
            //1 = Ally Request
            //0 = Friend Requestw
            //2 = ?????
            //CyberCoreMain.getInstance().getIntTime
            s.executeQuery("INSERT INTO `Ally` VALUES (null,'" + k + "'," + time + ")");
//            s.executeQuery(String.format("INSERT INTO `plots` VALUES ('%s','%s',2)", k, faction));
            c.close();
            return true;
//        Main.FFactory.allyrequest.put(getName(), fac.getName());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error ALLY to DB!!! Please report Error 'E92DB' to an admin");
            return false;

        }
    }


    //ENEMY


    public boolean removeEnemyRelationship(String fac1, String fac2) {
        if (!isEnemy(fac1, fac2)) {
            System.out.println("Error! Facctions are NOT already set as enemies!!!");
            return false;
        }


        String k1 = FactionNamesToKey(fac1, fac2);
        String k2 = FactionNamesToKey(fac2, fac1);
        int time = CyberCoreMain.getInstance().getIntTime();
        Connection c = CyberCoreMain.getInstance().FM.FFactory.getMySqlConnection();
        try {
            Statement s = c.createStatement();
            s.executeUpdate("DELETE FROM `Enemy` WHERE `key` LIKE k1");
            s.executeUpdate("DELETE FROM `Enemy` WHERE `key` LIKE k2");
            c.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error DELETING ALLY FROM DB!!! Please report Error 'E61DB' to an admin");
            return false;

        }


    }

    public boolean addEnemyRelationship(String fac1, String fac2) {

        if (isEnemy(fac1, fac2)) {
            System.out.println("Error! Facctions are already allied!!!");
            return false;
        }

        String k = FactionNamesToKey(fac1, fac2);
        int time = CyberCoreMain.getInstance().getIntTime();
        Connection c = CyberCoreMain.getInstance().FM.FFactory.getMySqlConnection();
        try {
            Statement s = c.createStatement();
            s.executeQuery("INSERT INTO `Enemy` VALUES (null,'" + k + "'," + time + ")");
            c.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error Adding Enemy to DB!!! Please report Error 'E182DB' to an admin");
            return false;

        }
    }


    public String[] splitKey(String key) {
        return key.split("\\|");
    }

    public ArrayList<String> getFactionEnemy(String faction) {
        ArrayList<String> f = new ArrayList<>();
        for (String a : EnemyList) {
            if (a.contains(faction + "|")) {
                String[] b = splitKey(a);
                String c1 = b[0];
                String c2 = b[1];
                if (c1.equalsIgnoreCase(faction)) {
                    f.add(c2);
                } else if (c2.equalsIgnoreCase(faction)) {
                    f.add(c1);
                } else {
                    System.out.println("Hun Error E3083 : " + a + " || " + faction);
                }

            }
        }
        return f;
    }
    public ArrayList<String> getFactionAllies(String faction) {
        ArrayList<String> f = new ArrayList<>();
        for (String a : AllyList) {
            if (a.contains(faction + "|")) {
                String[] b = splitKey(a);
                String c1 = b[0];
                String c2 = b[1];
                if (c1.equalsIgnoreCase(faction)) {
                    f.add(c2);
                } else if (c2.equalsIgnoreCase(faction)) {
                    f.add(c1);
                } else {
                    System.out.println("Hun Error E3224 : " + a + " || " + faction);
                }

            }
        }
        return f;
    }
}
