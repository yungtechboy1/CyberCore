package net.yungtechboy1.CyberCore.Commands.Homes;

import cn.nukkit.Player;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import cn.nukkit.math.Vector3;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.CyberCoreMain;

import java.util.LinkedHashMap;

import static net.yungtechboy1.CyberCore.CyberCoreMain.Prefix;

/**
 * Created by carlt_000 on 1/21/2017.
 */
public class HomeManager {
    public CyberCoreMain MainServer;
    public Config homes;
    public HomeManager(CyberCoreMain main){
        MainServer = main;
        homes = main.Homes;
    }

    public void TPPlayerHome(Player p, String num) {
        String pn = p.getName().toLowerCase();
        if (HasHomeAtKey(pn, num)) {
            LinkedHashMap<String, String> v = (LinkedHashMap<String, String>) homes.get(pn);
            String[] key = v.get(num).split("&");
            Vector3 v3 = new Vector3(Double.parseDouble(key[0]), Double.parseDouble(key[1]), Double.parseDouble(key[2]));
            if (!p.getLevel().getFolderName().equalsIgnoreCase(key[3])) {
                Level l = MainServer.getServer().getLevelByName(key[3]);
                p.setPosition(new Position(Double.parseDouble(key[0]), Double.parseDouble(key[1]), Double.parseDouble(key[2]), l));
                p.teleport(v3);
                //p.sendMessage(Prefix+TextFormat.GREEN+" POS"+Double.parseDouble(key[0])+" "+Double.parseDouble(key[1])+" "+Double.parseDouble(key[2])+" L:"+l.getFolderName());
            } else {
                p.teleport(v3);
                p.sendMessage(Prefix + TextFormat.YELLOW + " Error!");
            }
            p.sendMessage(Prefix + TextFormat.GREEN + " Teleporting You Home!");
        } else {
            p.sendMessage(Prefix + TextFormat.RED + " Home Not Set!");
        }
        if (MainServer.TPING.contains(p.getName().toLowerCase())) MainServer.TPING.remove(p.getName().toLowerCase());
    }

    public void DelPlayerHome(Player p, String num) {
        String pn = p.getName().toLowerCase();
        if (HasHomeAtKey(pn, num)) {
            homes.remove(pn+"."+num);
            p.sendMessage(Prefix + TextFormat.GREEN + " Home Successfully Deleted!");
        } else {
            p.sendMessage(Prefix + TextFormat.RED + " No Home at that key!");
        }
    }

    public void AddPlayerHome(Player p, String num) {
        String key = p.getX() + "&" + p.getY() + "&" + p.getZ() + "&" + p.getLevel().getFolderName();
        String pn = p.getName().toLowerCase();
        if (!num.matches("^[a-zA-Z0-9]*")) {
            p.sendMessage(Prefix + TextFormat.RED + " Invalid Home Name!");
            return;
        }
        if (HasHomeAtKey(pn, num)) {
            if (MainServer.Final.contains(pn)) {
                MainServer.Final.remove(pn);
                SetPlayerHome(pn, num, key);
                p.sendMessage(Prefix + TextFormat.GREEN + " Home Set! Use" + TextFormat.AQUA + " /home " + num + TextFormat.GREEN + " To go to your new home!");
            } else {
                p.sendMessage(Prefix + TextFormat.RED + " There is already a home set... If you wish to over-ride that home please type the command again with the same home name.");
                MainServer.Final.add(pn);
                return;
            }
        } else {
            if (MainServer.Final.contains(pn)) MainServer.Final.remove(pn);
            if (CountHomes(pn) > GetMaxHomes(pn)) {
                p.sendMessage(Prefix + TextFormat.RED + "Max HomeManager Set!");
                return;
            }
            SetPlayerHome(pn, num, key);
            p.sendMessage(Prefix + TextFormat.GREEN + " Home Set! Use" + TextFormat.AQUA + " /home " + num + TextFormat.GREEN + " To go to your new home!");
        }
    }

    public void SetPlayerHome(String p, String num, String value) {
        p = p.toLowerCase();
        homes.set(p+"."+num.toLowerCase(), value);
    }

    public Integer GetMaxHomes(String player) {
        String a = "";
       /* a = CC.GetAdminRank(player.toLowerCase());
        if (a == null) a = CC.GetMasterRank(player.toLowerCase());
        if (a == null) a = CC.GetSecondaryRank(player.toLowerCase());
        if (a == null) return 5;
        if (a.equalsIgnoreCase("tourist")) {
            return 10;
        } else if (a.equalsIgnoreCase("islander")) {
            return 15;
        } else if (a.equalsIgnoreCase("adventurer")) {
            return 20;
        } else if (a.equalsIgnoreCase("conquerer") || a.equalsIgnoreCase("yt")) {
            return 25;
        } else if (a.equalsIgnoreCase("TMOD")) {
            return 5;
        } else if (a.equalsIgnoreCase("MOD1")) {
            return 6;
        } else if (a.equalsIgnoreCase("MOD2")) {
            return 7;
        } else if (a.equalsIgnoreCase("MOD3")) {
            return 8;
        } else if (a.equalsIgnoreCase("ADMIN1")) {
            return 9;
        } else if (a.equalsIgnoreCase("ADMIN2")) {
            return 10;
        } else if (a.equalsIgnoreCase("ADMIN3")) {
            return 11;
        } else if (a.equalsIgnoreCase("OP")) {
            return 12;
        }*/
        return 5;
    }

    public boolean HasHomeAtKey(String player, String num) {
        if (homes != null && homes.exists(player.toLowerCase()+"."+num.toLowerCase()))return true;
        return false;
    }

    public Integer CountHomes(String player) {
        if (homes != null && homes.exists(player)) {
            LinkedHashMap<String, String> v = (LinkedHashMap<String, String>) homes.get(player);
            return v.size();
        }
        return 0;
    }
}
