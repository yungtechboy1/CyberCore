package net.yungtechboy1.CyberCore.Rank.Ranks;

import cn.nukkit.Player;
import net.yungtechboy1.CyberCore.Rank.Rank;
import net.yungtechboy1.CyberCore.Rank.RankList;

/**
 * Created by carlt on 3/21/2019.
 */
public class Guest_Rank extends Rank {
    public static final RankList RankList = net.yungtechboy1.CyberCore.Rank.RankList.PERM_GUEST;
    public Guest_Rank() {
        super(RankList, RankList.getName());
    }
}
