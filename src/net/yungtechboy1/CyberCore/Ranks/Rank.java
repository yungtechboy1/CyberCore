package net.yungtechboy1.CyberCore.Ranks;

/**
 * Created by carlt_000 on 1/22/2017.
 */
public class Rank {

    int rank = 0;

    String display_name = "[]";
    String chat_prefix = "&7";

    public Rank(Integer rank, String display_name, String chat_prefix) {
        this.rank = rank;
        this.display_name = display_name;
        this.chat_prefix = chat_prefix;
    }

    public int getRank() {
        return rank;
    }

    public String getDisplayName() {
        return display_name;
    }

    public String getChat_prefix() {
        return chat_prefix;
    }
}
