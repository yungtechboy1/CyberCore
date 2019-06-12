package net.yungtechboy1.CyberCore;

import net.yungtechboy1.CyberCore.Manager.Econ.PlayerEconData;
import net.yungtechboy1.CyberCore.Rank.Rank;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by carlt on 4/14/2019.
 */
public class PlayerSettingsData {

    public String Name;
    public ArrayList<UUID> UUIDS = new ArrayList<>();
    public double Cash = 0;
    public int CreditScore = 0;
    public int CreditLimit = 500;
    public int UsedCredit = 0;
    public ArrayList<PlayerWarningEvent> PlayerWarnings = new ArrayList<>();
    public ArrayList<PlayerTempBanEvent> PlayerTempBans = new ArrayList<>();
    public ArrayList<PlayerKickEvent> PlayerKicks = new ArrayList<>();
    public ArrayList<PlayerBanEvent> PlayerBans = new ArrayList<>();
    public int Rank = 0;

}

class PlayerSettingsEvent{
    public int intTime;
}

enum ReasonType{
    NULL,
    Type_Hacking,
    Type_Hacking_Speed,
    Type_Hacking_Fly,
    Type_Hacking_Inv,
    Type_Chat_Spam,
    Type_Chat_Abuse,
    Type_Chat_Racism,
    Type_Chat_Other,
    Type_Other,
    Type_Other_Misc,
}

class PlayerWarningEvent extends PlayerSettingsEvent {
    public String AdminName;
    public String Reason;
    public ReasonType ReasonType = net.yungtechboy1.CyberCore.ReasonType.NULL;
    public int intTime;
}

class PlayerKickEvent extends PlayerSettingsData{
    public String AdminName;
    public String Reason;
}
class PlayerTempBanEvent extends PlayerSettingsData{
    public String AdminName;
    public String Reason;
    public int intTimeLength;
}
class PlayerBanEvent extends PlayerSettingsData{
    public String AdminName;
    public String Reason;
    public PlayerWarningEvent LinkedWarning;
    public boolean Active;
}
