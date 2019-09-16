package net.yungtechboy1.CyberCore.Manager.Factions.Cmds.Base;

import cn.nukkit.Player;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.Manager.Factions.Faction;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionRank;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;

import java.util.LinkedHashMap;

/**
 * Created by carlt_000 on 7/9/2016.
 */
public abstract class Commands {

    public String[] Args;
    public String Usage;
    public CorePlayer Sender;
    public FactionsMain Main;
    public boolean senderMustBeInFaction = false;
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

    public Commands(CorePlayer s, String[] a, String usage, FactionsMain main) {
        Args = a;
        Sender = s;
        Usage = usage;
        Main = main;
    }

    public boolean run() {
        fac = Main.FFactory.getPlayerFaction((Player) Sender);
        if (!CheckPerms() && sendFailReason) {
            PermFail failcode = CheckPermsCodes();
            String message = "Unknown Error!";
            if (failcode == PermFail.Not_Player) message = "You must be a Player to use this Command!";
            if (failcode == PermFail.Not_In_Faction) message = "You must be in a faction to use this Command!";
            if (failcode == PermFail.IncorrectPerms){
                return false;
            }
            Sender.sendMessage(FactionsMain.NAME + message);
            if (sendUsageOnFail) Sender.sendMessage(FactionsMain.NAME + TextFormat.YELLOW + "Usage : " + Usage);
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

    public boolean CheckPerms() {
        legacySupport();
        if (CheckPermsCodes() != PermFail.No_Error) return false;
        return true;
    }

    public enum PermFail {
        No_Error(0),
        Not_Player(1),
        Not_In_Faction(2),
        IncorrectPerms(3);

        int id = -1;

        private PermFail(int i) {
            id = i;
        }

        public int getId() {
            return id;
        }

        public static PermFail getPermFail(int pid) {
            if (pid == No_Error.getId()) return No_Error;
            if (pid == Not_Player.getId()) return Not_Player;
            if (pid == Not_In_Faction.getId()) return Not_In_Faction;
            if (pid == IncorrectPerms.getId()) return IncorrectPerms;
            return No_Error;
        }

    }

    public PermFail CheckPermsCodes() {
        if (senderMustBePlayer && Sender == null) return PermFail.Not_Player;
        CorePlayer sp = Sender;
        if (fac == null && senderMustBeInFaction) return PermFail.Not_In_Faction;
        if (fac != null) {
            FactionRank fr = fac.getPlayerRank(sp);
            if (fr.HasPerm(senderMustBe)) {
                return PermFail.No_Error;
                //Success
            } else {
                fr.SendFailReason(senderMustBe, Sender);
                return PermFail.IncorrectPerms;
            }

//
//            if (!fac.IsMember(sp) && !fac.IsOfficer(Sender.getName()) && !fac.IsGeneral(Sender.getName()) && senderMustBeMember && !(fac.GetLeader().equalsIgnoreCase(Sender.getName())))
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
        return PermFail.No_Error;

    }

    public String PreRunCommand() {
        //Check If Player,Memebr,Mod,Admin
        return null;
    }

    public String GetStringAtArgs(Integer x) {
        return GetStringAtArgs(x, null);
    }

    public String GetStringAtArgs(Integer x, String def) {
        if (Args.length == 1) return def;
        if (Args.length < (x + 1)) return def;
        return Args[x];
    }

    public Integer GetIntegerAtArgs(Integer x, Integer def) {
        //1            1
        if (Args.length == 1) return def;
        if (Args.length < (x + 1)) return def;
        if (!isIntParsable(Args[x])) return def;
        return Integer.parseInt(Args[x]);
    }

    public Faction GetFactionAtArgs(Integer x) {
        //1            1
        if (Args.length == 1) return null;
        if (Args.length < (x + 1)) return null;
        String facname = Main.FFactory.factionPartialName(Args[x]);
        if (facname == null) return null;
        return Main.FFactory.getFaction(facname);
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

    public void SendUseage() {
        Sender.sendMessage(FactionsMain.NAME + Usage);
    }

    public void RunCommand() {
    }
}
