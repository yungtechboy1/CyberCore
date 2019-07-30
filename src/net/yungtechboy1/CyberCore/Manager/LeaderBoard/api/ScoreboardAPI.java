package net.yungtechboy1.CyberCore.Manager.LeaderBoard.api;

import cn.nukkit.Player;
import net.yungtechboy1.CyberCore.Manager.LeaderBoard.network.Scoreboard;

public class ScoreboardAPI {

    public static Scoreboard createScoreboard() {
        return new Scoreboard();
    }

    public static void setScoreboard( Player player, Scoreboard scoreboard ) {
        scoreboard.showFor( player );
    }

    public static void removeScorebaord( Player player, Scoreboard scoreboard ){
        scoreboard.hideFor( player );
    }

}
