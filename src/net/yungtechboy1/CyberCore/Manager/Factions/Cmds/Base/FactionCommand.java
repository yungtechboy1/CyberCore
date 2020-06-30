package net.yungtechboy1.CyberCore.Manager.Factions.Cmds.Base;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.Manager.Factions.Faction;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionRank;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;

import java.util.LinkedHashMap;

public abstract class FactionCommand extends Command {

    public String Description;
    public String Usage;
    public CyberCoreMain Main;
    public boolean senderMustBeInFaction = false;
    public boolean senderMustNOTBeInFaction = false;
    public FactionRank senderMustBe = FactionRank.Member;
    public boolean senderMustBePlayer = false;
    @Deprecated
    public boolean senderMustBeMember = false;
    @Deprecated
    public boolean senderMustBeOfficer = false;
    @Deprecated
    public boolean senderMustBeGeneral = false;
    @Deprecated
    public boolean senderMustBeLeader = false;
    public boolean senderMustBeAdmin = false;
    public boolean sendUsageOnFail = false;
    public boolean sendFailReason = false;
    public LinkedHashMap<String, String> optionalArgs;
    public Faction fac;
    public String SubCommand;

    public FactionCommand( String subcmd, String desc,String usage, CyberCoreMain main) {
        super("factest "+subcmd,desc,usage,new String[]{"factiontest"});
        this.commandParameters.clear();
        SubCommand = subcmd;
        Description = desc;
        Usage = usage;
        Main = main;
    }

    public void addCommandParameters(){
        commandParameters.put("default",
                new CommandParameter[]{
                        new CommandParameter("player", CommandParamType.TARGET, false),
                        new CommandParameter("reason", CommandParamType.STRING, true)
                });
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        fac = Main.FM.FFactory.getPlayerFaction((Player) sender);
        if (!CheckPerms((CorePlayer) sender)) {
            if (sendFailReason) {
                Commands.PermFail failcode = CheckPermsCodes((CorePlayer) sender);
                String message = "Unknown Error!";
                sender.sendMessage(FactionsMain.NAME + failcode.getTxt());
                if (sendUsageOnFail) sender.sendMessage(FactionsMain.NAME + TextFormat.YELLOW + "Usage : " + Usage);
                return false;
            } else {
                return false;
            }
        }
        return true;
        /*
        String PRC = PreRunCommand();
        if(PRC != null && sendFailReason){
            sender.sendMessage(PRC);
            if(sendUsageOnFail)sender.sendMessage(TextFormat.YELLOW+"Usage : "+Usage);
            return;
        }
        RunCommand();*/
    }

    private void legacySupport() {
        if (senderMustBe != null) return;
        if (senderMustBeMember) {
            senderMustBe = FactionRank.Member;
        } else if (senderMustBeOfficer) {
            senderMustBe = FactionRank.Officer;

        } else if (senderMustBeGeneral) {
            senderMustBe = FactionRank.General;

        } else if (senderMustBeLeader) {
            senderMustBe = FactionRank.Leader;

        } else {
            senderMustBe = FactionRank.Recruit;
        }
    }

    public boolean CheckPerms(CorePlayer p) {
        legacySupport();
        if (CheckPermsCodes(p) != Commands.PermFail.No_Error) return false;
        return true;
    }

    public Commands.PermFail CheckPermsCodes(CorePlayer sender) {
        if (senderMustBePlayer && sender == null) return Commands.PermFail.Not_Player;
        CorePlayer sp = sender;
        if (fac == null && senderMustBeInFaction) return Commands.PermFail.Not_In_Faction;
        if (fac != null) {
            if (senderMustNOTBeInFaction) return Commands.PermFail.Is_In_Faction;
            FactionRank fr = fac.getPlayerRank(sp);
            if (fr.hasPerm(senderMustBe)) {
                return Commands.PermFail.No_Error;
                //Success
            } else {
                fr.SendFailReason(senderMustBe, sender);
                return Commands.PermFail.IncorrectPerms;
            }

//
//            if (!fac.IsMember(sp) && !fac.IsOfficer(sender.getName()) && !fac.IsGeneral(sender.getName()) && senderMustBeMember && !(fac.GetLeader().equalsIgnoreCase(sender.getName())))
//                return 3;
//            if (!fac.IsOfficer(sp) && senderMustBeOfficer && !fac.IsGeneral(sp) && !(fac.GetLeader().equalsIgnoreCase(sp.getName())))
//                return 4;
//            if (!fac.IsGeneral(sp) && senderMustBeGeneral && !(fac.GetLeader().equalsIgnoreCase(sp.getName())))
//                return 5;
//            if (!(fac.GetLeader().equalsIgnoreCase(sp.getName())) && senderMustBeLeader)
//                return 6;
//            if (senderMustBeAdmin && !sp.isOp())
//                return 7;
        }
        return Commands.PermFail.No_Error;

    }

    public String PreRunCommand() {
        //Check If Player,Memebr,Mod,Admin
        return null;
    }

    public String GetStringAtArgs(String[] Args,Integer x) {
        return GetStringAtArgs(Args,x,null);
    }

    public String GetStringAtArgs(String[] Args, Integer x, String def) {
        if (Args.length == 1) return def;
        if (Args.length < (x + 1)) return def;
        return Args[x];
    }

    public Integer GetIntegerAtArgs(String[] Args,Integer x, Integer def) {
        //1            1
        if (Args.length == 1) return def;
        if (Args.length < (x + 1)) return def;
        if (!isIntParsable(Args[x])) return def;
        return Integer.parseInt(Args[x]);
    }

    public Faction GetFactionAtArgs(String[] Args,Integer x) {
        //1            1
        if (Args.length == 1) return null;
        if (Args.length < (x + 1)) return null;
        String facname = Main.FM.FFactory.factionPartialName(Args[x]);
        if (facname == null) return null;
        return Main.FM.FFactory.getFaction(facname);
    }

    private boolean isIntParsable(String input) {
        boolean parsable = true;
        try {
            Integer.parseInt(input);
        } catch (NumberFormatException e) {
            parsable = false;
        }
        return parsable;
    }

    public void SendUseage(CommandSender sender) {
        sender.sendMessage(FactionsMain.NAME + Usage);
    }

    public void RunCommand() {
    }

    public enum PermFail {
        No_Error(0),
        Not_Player(1, "You must be a Player to use this Command!"),
        Not_In_Faction(2, "You must be in a faction to use this Command!"),
        IncorrectPerms(3, "You do not have the correct permissions to use this command!"),
        Is_In_Faction(4, "You must NOT be in a faction to use this Command!");

        int id = -1;
        String txt;

        private PermFail(int i) {
            id = i;
            txt = "No Fail Message! E:" + i;
        }

        private PermFail(int i, String t) {
            id = i;
            txt = t;
        }

        public static PermFail getPermFail(int pid) {
            if (pid > values().length) {
                CyberCoreMain.getInstance().getLogger().error("Error Locating PermFail: " + pid);
                return No_Error;
            }
            return values()[pid];
//            return No_Error;
        }

        public String getTxt() {
            return txt;
        }

        public int getId() {
            return id;
        }

    }
}
