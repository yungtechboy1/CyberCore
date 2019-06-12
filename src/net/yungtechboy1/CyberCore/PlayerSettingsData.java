package net.yungtechboy1.CyberCore;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
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
    Type uuidType = new TypeToken<ArrayList<UUID>>() {
    }.getType();
    Type pweType = new TypeToken<ArrayList<PlayerWarningEvent>>() {
    }.getType();
    Type ptbType = new TypeToken<ArrayList<PlayerTempBanEvent>>() {
    }.getType();
    Type pkbType = new TypeToken<ArrayList<PlayerKickEvent>>() {
    }.getType();
    Type pbbType = new TypeToken<ArrayList<PlayerBanEvent>>() {
    }.getType();

    public PlayerSettingsData(CorePlayer p) {
        Cash = 1000;
        CreditLimit = 1000;
        CreditScore = 350;//Out of 1000
        UUIDS.add(p.getUniqueId());
    }

    public PlayerSettingsData(HashMap<String, Object> a) {
        Name = (String) a.get("Name");
        //https://stackoverflow.com/questions/27893342/how-to-convert-list-to-a-json-object-using-gson
        UUIDS = new Gson().fromJson((String) a.get("UUIDS"), uuidType);
        Cash = (int) a.get("Cash");
        CreditScore = (int) a.get("CreditScore");
        CreditLimit = (int) a.get("CreditLimit");
        UsedCredit = (int) a.get("UsedCredit");
        PlayerWarnings = new Gson().fromJson((String) a.get("PlayerWarnings"), pweType);
        PlayerTempBans = new Gson().fromJson((String) a.get("PlayerTempBans"), uuidType);
        PlayerKicks = new Gson().fromJson((String) a.get("PlayerKicks"), uuidType);
        PlayerBans = new Gson().fromJson((String) a.get("PlayerBans"), uuidType);
        Rank = (int) a.get("Rank");
    }

    public String UUIDSToJSON() {
        return new Gson().toJson(UUIDS, uuidType);
    }

    public String PlayerWarningToJSON() {
        return new Gson().toJson(PlayerWarnings, pweType);
    }

    public String PlayerTempBansToJSON() {
        return new Gson().toJson(PlayerTempBans, ptbType);
    }

    public String PlayerKicksToJSON() {
        return new Gson().toJson(PlayerKicks, pkbType);
    }

    public String PlayerBansToJSON() {
        return new Gson().toJson(PlayerBans, pbbType);
    }
}

class PlayerSettingsEvent {
    public int intTime;
}

class PlayerWarningEvent extends PlayerSettingsEvent {
    public String AdminName;
    public String Reason;
    public ReasonType ReasonType = net.yungtechboy1.CyberCore.ReasonType.NULL;
    public int intTime;
}

class PlayerKickEvent extends PlayerSettingsEvent {
    public String AdminName;
    public String Reason;
    public ReasonType ReasonType = net.yungtechboy1.CyberCore.ReasonType.NULL;
}

class PlayerTempBanEvent extends PlayerSettingsEvent {
    public String AdminName;
    public String Reason;
    public int intTimeLength;
}

class PlayerBanEvent extends PlayerSettingsEvent {
    public String AdminName;
    public String Reason;
    public PlayerWarningEvent LinkedWarning;
    public boolean Active;
}
