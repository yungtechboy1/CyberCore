package net.yungtechboy1.CyberCore.Manager.Factions.Cmds;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;
import main.java.CyberFactions.Faction;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;

import java.util.ArrayList;

/**
 * Created by carlt_000 on 7/9/2016.
 */
public class Create extends Commands {

    private ArrayList<String> bannednames = new ArrayList<String>(){{
        add("wilderness");
        add("safezone");
        add("peace");
    }};

    public Create(CommandSender s, String[] a, FactionsMain m){
        super(s,a,"/f create <Name>",m);
        senderMustBePlayer = true;
        sendUsageOnFail = true;

        if(run()){
            RunCommand();
        }
    }

    @Override
    public void RunCommand(){
        if(Args.length <= 1){
            Sender.sendMessage(FactionsMain.NAME+TextFormat.GRAY+"Usage /f create <name>");
            return;
        }
        if(!Args[1].matches("^[a-zA-Z0-9]*")) {
            Sender.sendMessage(FactionsMain.NAME+TextFormat.RED+"You may only use letters and numbers!");
            return;
        }
        if(bannednames.contains(Args[1].toLowerCase())){
            Sender.sendMessage(FactionsMain.NAME+TextFormat.RED+"That is a Banned faction Name!");
            return;
        }
        if(Main.factionExists(Args[1])) {
            Sender.sendMessage(FactionsMain.NAME+TextFormat.RED+"Faction already exists");
            return;
        }
        if(Args[1].length() > 20) {
            Sender.sendMessage(FactionsMain.NAME+TextFormat.RED+"Faction name is too long. Please try again!");
            return;
        }
        if(Main.isInFaction(Sender.getName())) {
            Sender.sendMessage(FactionsMain.NAME+TextFormat.RED+"You must leave your faction first");
            return;
        } else {
            Main.FFactory.CreateFaction(Args[1],(Player) Sender);
            Sender.sendMessage(FactionsMain.NAME+TextFormat.GRAY+"Your Faction has 2 power!");
            Main.CC.Setnametag((Player) Sender);
            Main.sendBossBar((Player) Sender);
            return;
        }
    }
}
