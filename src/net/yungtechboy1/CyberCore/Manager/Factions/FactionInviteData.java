package net.yungtechboy1.CyberCore.Manager.Factions;

import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;

public class FactionInviteData {
    String PlayerName;
    int TimeStamp = -1;
    String Faction;
    String InvitedBy;
    FactionRank FacRank = FactionRank.Recruit;

    public FactionInviteData(String playerName, int timeStamp, String faction, String invitedBy, FactionRank fr) {

        PlayerName = playerName;
        TimeStamp = timeStamp;
        Faction = faction;
        InvitedBy = invitedBy;
        FacRank = fr;
    }

    public FactionInviteData(String playerName, int timeStamp, String faction, String invitedBy) {
        this(playerName, timeStamp, faction, invitedBy, FactionRank.Recruit);
    }


    public FactionInviteData(String playerName, int timeStamp, String faction) {
        this(playerName, timeStamp, faction, (String) null);
    }

    public FactionInviteData(String playerName, int timeStamp, String faction, FactionRank fr) {
        this(playerName, timeStamp, faction, null, fr);
    }

    public String getPlayerName() {
        return PlayerName;
    }

    public int getTimeStamp() {
        return TimeStamp;
    }

    public String getFaction() {
        return Faction;
    }

    public String getInvitedBy() {
        return InvitedBy;
    }

    public boolean isPlayer(CorePlayer cp) {
        return cp.getName().equalsIgnoreCase(PlayerName);
    }

    public boolean isValid(int time) {
        return time < TimeStamp;
    }

    public boolean isValid() {
        return CyberCoreMain.getInstance().getIntTime() < TimeStamp;
    }


}
