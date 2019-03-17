package net.yungtechboy1.CyberCore.Manager.Factions.Cmds;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.Manager.Factions.Faction;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;

import java.util.LinkedHashMap;

/**
 * Created by carlt_000 on 7/9/2016.
 */
public class Commands {

    public String[] Args;
    public String Usage;
    public CommandSender Sender;
    public FactionsMain Main;
    public boolean senderMustBeInFaction = false;
    public boolean senderMustBePlayer = false;
    public boolean senderMustBeMember = false;
    public boolean senderMustBeOfficer = false;
    public boolean senderMustBeGeneral = false;
    public boolean senderMustBeLeader = false;
    public boolean senderMustBeAdmin = false;
    public boolean sendUsageOnFail = false;
    public boolean sendFailReason = false;
    public LinkedHashMap<String, String> optionalArgs;
    Faction fac;

    public Commands(CommandSender s, String[] a, String usage, FactionsMain main){
        Args = a;
        Sender = s;
        Usage = usage;
        Main = main;
    }
    public boolean run(){
        fac = Main.FFactory.getPlayerFaction((Player)Sender);
        if(!CheckPerms() && sendFailReason){
            Integer failcode = CheckPermsCodes();
            String message = "Unknown Error!";
            if(failcode == 1)message = "You must be a Player to use this Command!";
            if(failcode == 2)message = "You must be in a faction to use this Command!";
            if(failcode == 3)message = "You must be a Member to use this Command!";
            if(failcode == 4)message = "You must be a Officer to use this Command!";
            if(failcode == 5)message = "You must be a General to use this Command!";
            if(failcode == 6)message = "You must be a Leader to use this Command!";
            if(failcode == 7)message = "You must be a Admin to use this Command!";
            Sender.sendMessage(FactionsMain.NAME+message);
            if(sendUsageOnFail)Sender.sendMessage(FactionsMain.NAME+TextFormat.YELLOW+"Usage : "+Usage);
            return false;
        }
        return true;
        /*
        String PRC = PreRunCommand();
        if(PRC != null && sendFailReason){
            Sender.sendMessage(PRC);
            if(sendUsageOnFail)Sender.sendMessage(TextFormat.YELLOW+"Usage : "+Usage);
            return;
        }
        RunCommand();*/
    }

    public boolean CheckPerms(){
        if(CheckPermsCodes() != 0)return false;
        return true;
    }

    public Integer CheckPermsCodes(){
        if(senderMustBePlayer && !(Sender instanceof Player))return 1;
        Player sp = (Player)Sender;
        if(fac == null && senderMustBeInFaction)return 2;
        if(fac != null) {
            if (!fac.IsMember(sp) && !fac.IsOfficer(Sender.getName()) && !fac.IsGeneral(Sender.getName()) && senderMustBeMember && !(fac.GetLeader().equalsIgnoreCase(Sender.getName())))
                return 3;
            if (!fac.IsOfficer(sp) && senderMustBeOfficer && !fac.IsGeneral(sp) && !(fac.GetLeader().equalsIgnoreCase(sp.getName())))
                return 4;
            if (!fac.IsGeneral(sp) && senderMustBeGeneral && !(fac.GetLeader().equalsIgnoreCase(sp.getName())))
                return 5;
            if (!(fac.GetLeader().equalsIgnoreCase(sp.getName())) && senderMustBeLeader)
                return 6;
            if(senderMustBeAdmin && !sp.isOp())
                return 7;
        }
        return 0;

    }

    public String PreRunCommand(){
        //Check If Player,Memebr,Mod,Admin
        return null;
    }

    public String GetStringAtArgs(Integer x){
        return GetStringAtArgs(x,null);
    }
    public String GetStringAtArgs(Integer x,String def){
        if(Args.length == 1)return def;
        if(Args.length < (x+1))return def;
        return Args[x];
    }
    public Integer GetIntegerAtArgs(Integer x,Integer def){
        //1            1
        if(Args.length == 1)return def;
        if(Args.length < (x+1))return def;
        if(!isIntParsable(Args[x]))return def;
        return Integer.parseInt(Args[x]);
    }
    public Faction GetFactionAtArgs(Integer x){
        //1            1
        if(Args.length == 1)return null;
        if(Args.length < (x+1))return null;
        String facname = Main.FFactory.factionPartialName(Args[x]);
        if(facname == null)return null;
        return Main.FFactory.getFaction(facname);
    }

    private boolean isIntParsable(String input){
        boolean parsable = true;
        try{
            Integer.parseInt(input);
        }catch(NumberFormatException e){
            parsable = false;
        }
        return parsable;
    }

    public void SendUseage(){
        Sender.sendMessage(FactionsMain.NAME+Usage);
    }

    public void RunCommand(){
    }
}
