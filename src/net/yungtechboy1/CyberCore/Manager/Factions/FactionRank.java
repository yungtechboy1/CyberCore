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

    public TextFormat GetChatColor() {
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


    public boolean HasPerm(FactionRank target){
        return Power >= target.Power;
    }


    public void SendFailReason(FactionRank target, Player p){
        p.sendMessage(TextFormat.RED+"Error! You must be a " +target.getName()+" to use this command!");
    }

    private String getName() {
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
