package net.yungtechboy1.CyberCore.Manager.Factions;

import cn.nukkit.Player;
import cn.nukkit.utils.TextFormat;

public enum FactionRank {
    Recruit(0),
    Member(1),
    Officer(2),
    General(3),
    Leader(4),
    All(-1);

    public static FactionRank getRankFromString(String a) {
        try {
            int i = Integer.parseInt(a);
//            values()
            for (FactionRank f : values()) {
                if (f.Power == i) return f;
            }
            return null;
        } catch (Exception e) {
            System.out.println("Error attempting to parse Rank String to Int to Rank");
            return null;
        }
    }

    public static FactionRank getRankFromForm(int a) {
        switch (a) {
            case 0:
                return Leader;
            case 1:
                return General;
            case 2:
                return Officer;
            case 3:
                return Member;
            case 4:
                return Recruit;
            default:
                return Recruit;
        }
    }

    public int getFormPower() {
        if (Power == -1) return Power;
        switch (this) {
            case Leader:
                return 0;
            case General:
                return 1;
            case Officer:
                return 2;
            case Member:
                return 3;
            case Recruit:
                return 4;
            default:
                return 0;
        }
    }

    public int getPower() {
        return Power;
    }

    public void setPower(int power) {
        Power = power;
    }

    private int Power = -1;

    FactionRank(int p) {
        Power = -1;
    }

    public TextFormat getChatColor() {
        switch (Power) {
            case 0:
                return TextFormat.GRAY;
            case 1:
                return TextFormat.AQUA ;
            case 2:
                return TextFormat.YELLOW;
            case 3:
                return TextFormat.GREEN;
            case 4:
                return TextFormat.GOLD;
            default:
                return TextFormat.LIGHT_PURPLE;
        }
    }

//FactionCommandWindow.java:40
    public boolean hasPerm(FactionRank target) {
        if(target == null)return false;
        return Power >= target.Power;
    }


    public void SendFailReason(FactionRank target, Player p){
        p.sendMessage(TextFormat.RED+"Error! You must be a " +target.getName()+" to use this command!");
    }

    public String getName() {
        switch (Power) {
            case 0:
                return "Recruit";
            case 1:
                return "Member";
            case 2:
                return "General";
            case 3:
                return "Officer";
            case 4:
                return "Leader";
            default:
                return null;
        }
    }

    public String GetChatPrefix() {
        switch (Power) {
            case 0:
                return TextFormat.GRAY + "R";
            case 1:
                return TextFormat.AQUA + "M";
            case 2:
                return TextFormat.YELLOW + "G";
            case 3:
                return TextFormat.GREEN + "O";
            case 4:
                return TextFormat.GOLD + "L";
            default:
                return TextFormat.LIGHT_PURPLE + "-";
        }
    }

}
