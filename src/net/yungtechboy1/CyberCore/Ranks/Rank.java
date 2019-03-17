package net.yungtechboy1.CyberCore.Ranks;

/**
 * Created by carlt_000 on 1/22/2017.
 */
public class Rank {

    int rank = 0;

    String display_name = "[]";

    public Rank(Integer rank, String display_name) {
        this.rank = rank;
        this.display_name = display_name;
    }

    public int getRank() {
        return rank;
    }

    public String getDisplayName() {
        return display_name;
    }
}
