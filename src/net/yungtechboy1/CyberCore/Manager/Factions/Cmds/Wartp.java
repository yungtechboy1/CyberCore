package net.yungtechboy1.CyberCore.Manager.Factions.Cmds;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.level.Level;
import cn.nukkit.math.Vector3;
import cn.nukkit.utils.TextFormat;

import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;

import javax.naming.InsufficientResourcesException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;


public class Wartp extends Commands {

    public Wartp(CommandSender s, String[] a, FactionsMain m) {
        super(s, a, "/f wartp", m);
        senderMustBeInFaction = true;
        senderMustBeGeneral = true;
        sendFailReason = true;
        sendUsageOnFail = true;
        if (run()) {
            RunCommand();
        }
    }

    @Override
    public void RunCommand() {
        if (!fac.AtWar()) {
            Sender.sendMessage(FactionsMain.NAME+ TextFormat.RED + " You must be at war to use this command!!!");
            return;
        }
        if(Main.Econ.GetMoney(Sender.getName()) < 500){
            Sender.sendMessage(FactionsMain.NAME+TextFormat.RED+" Error! You must have $500 inorder to teleport!");
            return;
        }
        Main.Econ.TakeMoney(Sender.getName(),500);
        Vector3 pos = GetRandomTPArea(7);
        if (pos != null) {
            ((Player) Sender).teleport(((Player)Sender).getLevel().getSafeSpawn(pos));
            Sender.sendMessage(FactionsMain.NAME+TextFormat.GREEN + "Teleported To War Zone!");
        }
        return;
    }

    private Vector3 GetRandomTPArea(Integer randnum){
        Integer x = null;
        Integer z = null;
        Random rand = new Random();
        ArrayList<Player> op = fac.GetOnlinePlayers();

        if (fac.GetPlots().size() != 0) {
            int a = rand.nextInt(fac.GetPlots().size() - 1);
            String cords = fac.GetPlots().get(a);
            x = Integer.parseInt(cords.split("\\|")[0]) << 4 + (rand.nextInt(randnum * 2) - randnum);
            z = Integer.parseInt(cords.split("\\|")[1]) << 4 + (rand.nextInt(randnum * 2) - randnum);
        }else{
            if (op.size()>0) {
                int a = rand.nextInt(op.size() - 1);
                Player tp = op.get(a);
                if (tp != null) {
                    x = (int) tp.getX() + (rand.nextInt(randnum * 2) - randnum);
                    z = (int) tp.getZ() + (rand.nextInt(randnum * 2) - randnum);
                    return new Vector3((double)x,150,(double)z);
                }
            }else{
                Sender.sendMessage(FactionsMain.NAME+TextFormat.RED+"Error Teleporting! No one from that faction is online and no Chunk is available for TP!");
                return null;
            }
        }

        //50% Chance it will choose a Player instead of Land
        //If no Players are online then this is skipped
        if (rand.nextInt(100)>50){
            if (op.size()>0) {
                int a = rand.nextInt(op.size() - 1);
                Player tp = op.get(a);
                if (tp != null) {
                    x = (int) tp.getX() + (rand.nextInt(randnum * 2) - randnum);
                    z = (int) tp.getZ() + (rand.nextInt(randnum * 2) - randnum);
                    return new Vector3((double)x,150,(double)z);
                }
            }
        }

        if (x == null || z == null){
            Sender.sendMessage(FactionsMain.NAME+TextFormat.RED+"ERROR! Can not find a valid TP Location! Try again later!");
            return null;
        }
        return new Vector3((double)x,150,(double)z);
    }
}