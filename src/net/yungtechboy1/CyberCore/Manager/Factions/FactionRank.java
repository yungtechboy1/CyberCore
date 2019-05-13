package net.yungtechboy1.CyberCore.Manager.Factions;

import cn.nukkit.utils.TextFormat;

public enum FactionRank{
    Recruit(0),
    Member(1),
    Officer(2),
    General(3),
    Leader(4),
    All(-1);

    private int Power = -1;

    FactionRank(int p){
        Power = -1;
    }

    public String GetChatPrefix(){
        switch (Power){
            case 0:
                return TextFormat.GRAY+ "R";
            case 1:
                return TextFormat.AQUA+ "M";
            case 2:
                return TextFormat.YELLOW+ "G";
            case 3:
                return TextFormat.GREEN+ "O";
            case 4:
                return TextFormat.GOLD+ "L";
            default:
                return TextFormat.LIGHT_PURPLE+"-";
        }
        return
    }

}
