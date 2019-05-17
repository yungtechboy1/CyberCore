package net.yungtechboy1.CyberCore.Rank.Ranks;

import net.yungtechboy1.CyberCore.Rank.Rank;
import net.yungtechboy1.CyberCore.Rank.RankList;

public class Member_Rank extends Rank {
    public Member_Rank() {
        super(RankList.PERM_MEMBER.getID(), "Member");
        RankList rankData = RankList.PERM_MEMBER;
        display_name = rankData.getName();
        chat_prefix = rankData.getChat_prefix();
        rl = rankData;
    }



}
