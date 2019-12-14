package net.yungtechboy1.CyberCore.Manager.Factions;

import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.CyberUtils;

public class Invitation {

    private String fac = null;
    private String PlayerName;
    private FactionRank Rank = FactionRank.Recruit;
    private int Timeout;

    public Invitation(Faction f, String playerName, int timeout, FactionRank r) {
        fac = f.getName();
        PlayerName = playerName;
        Timeout = timeout;
        Rank = r;
    }

    public Invitation(Faction f, CorePlayer sender, Integer value, FactionRank r) {
        fac = f.getName();
        PlayerName = sender.getName();
        Timeout = value;
        Rank = r;
    }

    public Invitation(String f, CorePlayer sender, Integer value, FactionRank r) {
        fac = f;
        PlayerName = sender.getName();
        Timeout = value;
        Rank = r;
    }

    public Invitation(String f, String sender, Integer value, FactionRank r) {
        fac = f;
        PlayerName = sender;
        Timeout = value;
        Rank = r;
    }

    public FactionRank getRank() {
        return Rank;
    }

    public void setRank(FactionRank rank) {
        Rank = rank;
    }

    public String getFac() {
        return fac;
    }

    public void setFac(Faction f) {
        fac = f.getName();
    }

    public void setFac(String f) {
        fac = f;
    }

    public String getPlayerName() {
        return PlayerName;
    }

    public void setPlayerName(String playerName) {
        PlayerName = playerName;
    }

    public int getTimeout() {
        return Timeout;
    }

    public void setTimeout(int timeout) {
        Timeout = timeout;
    }

    @Override
    public String toString() {
        return CyberUtils.toStringCode(getFac(), getPlayerName(), getTimeout());
    }

    public boolean isValid() {
        return !(CyberCoreMain.getInstance().getIntTime() > getTimeout());
    }
}
