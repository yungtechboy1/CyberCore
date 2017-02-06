package net.yungtechboy1.CyberCore.Factory;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerLoginEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.potion.Effect;
import cn.nukkit.utils.ConfigSection;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.Events.ForbidAction;
import net.yungtechboy1.CyberCore.Password;
import net.yungtechboy1.CyberCore.Tasks.Notify;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by carlt_000 on 2/4/2017.
 */
public class PasswordFactoy implements Listener{

    public ConfigSection Passwords = new ConfigSection();
    public Connection Connect = null;
    public CyberCoreMain CCM = null;

    public PasswordFactoy (CyberCoreMain main){
        CCM = main;
        new File(CCM.getDataFolder().toString()).mkdirs();
        CCM.getServer().getPluginManager().registerEvents(new ForbidAction(CCM), CCM);
        CCM.getServer().getPluginManager().registerEvents(this, CCM);
        CCM.getServer().getScheduler().scheduleDelayedRepeatingTask(new Notify(CCM), 20, 20);
    }

    public ResultSet ExecuteQuerySQLite(String s) {
        try {
            Statement stmt = this.getMySqlConnection().createStatement();
            ResultSet r = stmt.executeQuery(s);
            //this.getServer().getLogger().info( s );
            return r;
        } catch (Exception ex) {

            CCM.getServer().getLogger().info(ex.getClass().getName() + ":8 " + ex.getMessage());
            return null;
        }
    }

    public void onDisable() {
        try {
            Statement stmt = getMySqlConnection().createStatement();
            for (Object pass : Passwords.values()) {
                if (pass instanceof Password) {
                    stmt.executeUpdate("DELETE FROM `password` WHERE `player` = '" + ((Password) pass).getPlayer() + "';");
                    stmt.executeUpdate("INSERT INTO `password` VALUES ('" + ((Password) pass).getPlayer() + "','" + ((Password) pass).getHash() + "','" + ((Password) pass).getCID() + "','" + ((Password) pass).getUnqiqueID() + "','" + ((Password) pass).getIpaddress() + "','" + ((Password) pass).getEmail() + "')");
                }
            }
        } catch (Exception ex) {
            CCM.getServer().getLogger().info(ex.getClass().getName() + ":7 " + ex.getMessage());
        }
    }

    public Password GetPassword(Player name) {
        return GetPassword(name.getName().toLowerCase());
    }

    public Password GetPassword(String name) {
        if (!Passwords.containsKey(name.toLowerCase())) {
            try {
                ResultSet r = this.ExecuteQuerySQLite("select * from password where player = '" + name + "'");
                if (r != null) {
                    while (r.next()) {
                        String player = r.getString("player");
                        Password pass = new Password(player);
                        String Hash = r.getString("Hash");
                        Long CID = r.getLong("CID");
                        String UniqueID = r.getString("UniqueID");
                        String Ipaddress = r.getString("Ipaddress");
                        String Email = r.getString("Email");
                        pass.setHash(Hash);
                        pass.setCID(CID);
                        pass.setUnqiqueID(UniqueID);
                        pass.setIpaddress(Ipaddress);
                        pass.setEmail(Email);
                        Passwords.put(player.toLowerCase(), pass);
                        break;
                    }

                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        if (Passwords.exists(name.toLowerCase()) && Passwords.get(name.toLowerCase()) instanceof Password)
            return (Password) Passwords.get(name.toLowerCase());
        System.out.println(Passwords.get(name.toLowerCase(), "").getClass().getName().toString());
        Password a = new Password(name);
        Passwords.set(name.toLowerCase(), a);
        return a;
    }

    public Boolean isPlayerLoggedIn(Player p) {
        return GetPassword(p.getName()).getLoggedin();
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onJoin(PlayerLoginEvent event) {
        Password a = GetPassword(event.getPlayer());
        event.getPlayer().removeEffect(Effect.BLINDNESS);
        a.CheckAutoLogin(event.getPlayer());
        if (a.getLoggedin()) return;
        Effect e1 = Effect.getEffect(Effect.BLINDNESS);
        e1.setAmplifier(5);
        e1.setDuration(60 * 60 * 60);
        event.getPlayer().addEffect(e1);
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        Password a = GetPassword(event.getPlayer());
        a.SetLoggedin(false);
    }

    public Connection getMySqlConnection() {
        //return getMySqliteConnection();
        try {
            if (Connect != null && !Connect.isClosed()) return Connect;
            CCM.getServer().getLogger().info("REGEtting Connection!");
            Class.forName("com.mysql.jdbc.Driver");
            Connect = DriverManager.getConnection("jdbc:mysql://localhost/Terratide?user=TerraTideMC&password=TerraTideMC");
            return Connect;
        } catch (Exception ex) {
            CCM.getServer().getLogger().info("Asdddd");
            return null;
        }
    }
}
