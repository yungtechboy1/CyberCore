package net.yungtechboy1.CyberCore.Manager.Factions;

import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.CyberCoreMain;

import java.util.HashMap;
import java.util.Objects;

public enum FactionErrorString {
    NO_ERROR("NO ERROR", 0),

    Default_Faction_MOTD("user /f motd to change the Message of The Day!", -2),
    Default_Faction_Description("Brand new faction, ready to take over!", -1),
    Success_FactionCreated(TextFormat.GREEN + "[CyboticFactions] Faction successfully created!", 101),
    Success_ADMIN_Faction_Saved(TextFormat.GREEN + "[CyboticFactions] All Factions Successfully Saved!", 102),
    Success_ADMIN_Faction_Load(TextFormat.GREEN + "[CyboticFactions] All Factions Successfully Saved!", 103),
    Success_ADMIN_Faction_Reload(TextFormat.GREEN + "[CyboticFactions] All Factions Successfully Saved!", 104),
    Error_OnlyNumbersNLetters(FactionsMain.NAME + TextFormat.RED + "You may only use letters and numbers!", 1),
    Error_BannedName(FactionsMain.NAME + TextFormat.RED + "That is a Banned faction Name!", 2),
    Error_FactionExists(FactionsMain.NAME + TextFormat.RED + "Faction already exists", 3),
    Error_NameTooLong(FactionsMain.NAME + TextFormat.RED + "Faction name is too long. Please try again!", 4),
    Error_InFaction(FactionsMain.NAME + TextFormat.RED + "You must leave your faction first", 5),
    Error_NameTooShort(FactionsMain.NAME + TextFormat.RED + "Your faction name must be at least 3 Letters long", 6),
    Error_NotInFaction(FactionsMain.NAME + TextFormat.RED + "Your Not in a faction!", 7),
    Error_FactionFull(FactionsMain.NAME + TextFormat.RED + "Error! Your Faction is full. Please kick players to make more room.", 8),
    Error_CMD_Invite_UnableToFindPlayer(FactionsMain.NAME + TextFormat.RED + "Error! No Player By That Name Is Online!", 9),
    Error_UnableToFindFaction(FactionsMain.NAME + TextFormat.RED + "Error! Unable to find faction By That Name!", 9),
    Error_CMD_Invite_PlayerInFaction(FactionsMain.NAME + TextFormat.RED + "Error! Player is currently in a faction", 9),
    Error_SA221("Seems like an error occured! Please report Error: SA221", 221),
    Error_SA224("Seems like an error occured! Please report Error: SA224", 221),
    Error_SA223("Seems like an error occured while creating your faction! Please report Error: SA223", 223),
    Error_CMD_Invite_No_Player_Entered("Error! Please enter a players name to invite", 1),
    Error_Settings_No_Permission("Error! You do not have permission to edit Faction Settwings", 3233);
//        FactionExists(FactionsMain.NAME+TextFormat.RED+"Faction already exists",2),
//        FactionExists(FactionsMain.NAME+TextFormat.RED+"Faction already exists",2),



    static int k = 0;
    private String Msg;
    private int ID;


    FactionErrorString(String s, int id) {
        Msg = s;
        ID = id;
//        FactionsMain.getInstance().registerFactionString(id, this);
        //FactionsMain.getInstance().FactionErrorString.put(id, this);
//        RegisterMessage(this);

    }

    public String getMsg() {
        return Msg;
    }

    public int getID() {
        return ID;
    }

    public String toError() {
        return Msg + " Error #: " + ID;
    }


    @Override
    public String toString() {
        return getMsg();
    }

}

/**
 * Created by carlt on 5/12/2019.
 */
class FactionStringDic {
//    public static int k = 0;
public static HashMap<Integer, FactionErrorString> list = new HashMap<>();

    public FactionStringDic() {

    }

    public static void RegisterMessage(FactionErrorString fs) {
        CyberCoreMain.getInstance().getLogger().notice("Registering Message " + fs.getMsg() + " to " + fs.getID());
        if (list.containsKey(fs.getID())) {
            if(Objects.equals(list.get(fs.getID()).getMsg(), fs.getMsg()))return;
            //Set new ID
            //200 Tries
            for (int i = 0; i < 200; i++) {
                if (!list.containsKey(fs.getID() + i)) {
                    list.put(fs.getID() + i, fs);
                    CyberCoreMain.getInstance().getLogger().notice("Changed Registered Message ID From " + fs.getID() + " to " + (fs.getID() + i));
                    break;
                }
            }
        } else {
            list.put(fs.getID(),fs);
            CyberCoreMain.getInstance().getLogger().notice(" Message ID > " + fs.getID());
        }
    }
}
