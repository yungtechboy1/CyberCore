package net.yungtechboy1.CyberCore.Ranks;

import cn.nukkit.Player;

/**
 * Created by carlt_000 on 1/22/2017.
 */
public class Rank {
    Player player;
    int rank;

    public Rank(Player player, Integer rank) {
        this.player = player;
        this.rank = rank;
    }

    public int getRank() {
        return rank;
    }

    public Player getPlayer() {
        return player;
    }
}
