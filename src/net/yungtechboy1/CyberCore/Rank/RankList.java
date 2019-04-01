package net.yungtechboy1.CyberCore.Rank;

import net.yungtechboy1.CyberCore.Rank.Ranks.Guest_Rank;

/**
 * Created by carlt_000 on 1/21/2017.
 */
public enum RankList {
    PERM_GUEST(0, "Guest"),
    PERM_MEMBER(1, "Member"),
    PERM_VIP(3, "VIP"),
    PERM_OP(20, "SuperOP");

    final int ID;
    String name;
    String chat_prefix;

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String n) {
        this.name = n;
    }

    RankList(int id, String n) {
        ID = id;
        this.name = n;
        this.chat_prefix = "&7";
    }

    public static int PERM_MEMBER_PLUS = 2;
    public static int PERM_TOURIST = 3;
    public static int PERM_ISLANDER = 4;
    public static int PERM_ADVENTURER = 5;
    public static int PERM_CONQUERER = 6;
    public static int PERM_TMOD = 7;
    public static int PERM_MOD_1 = 8;
    public static int PERM_YT = 8;
    public static int PERM_YOUTUBE = 8;
    public static int PERM_MOD_2 = 9;
    public static int PERM_MOD_3 = 10;
    public static int PERM_ADMIN_1 = 11;
    public static int PERM_ADMIN_2 = 12;
    public static int PERM_ADMIN_3 = 13;
    public static int PERM_SERVER = 50;

    public Rank getRank() {
        if (getID() == PERM_GUEST.getID()) return new Guest_Rank();
        if (getID() == PERM_MEMBER.getID()) return new Guest_Rank();

        return new Guest_Rank();
    }

    public String getChat_prefix() {
        return chat_prefix;
    }
}
