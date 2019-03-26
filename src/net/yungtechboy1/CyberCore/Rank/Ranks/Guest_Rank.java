package net.yungtechboy1.CyberCore.Rank.Ranks;

import cn.nukkit.Player;
import net.yungtechboy1.CyberCore.Rank.Rank;
import net.yungtechboy1.CyberCore.Rank.RankFactory;
import net.yungtechboy1.CyberCore.Rank.RankList;

/**
 * Created by carlt on 3/21/2019.
 */
public class Guest_Rank extends Rank {
    public Guest_Rank() {
        super(0, "Guest");
        RankList rankData = RankList.PERM_GUEST;
        display_name = rankData.getName();
        chat_prefix = rankData.getChat_prefix();
    }



}
