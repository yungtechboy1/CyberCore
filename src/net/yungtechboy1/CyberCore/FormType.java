package net.yungtechboy1.CyberCore;

/**
 * Created by carlt on 3/22/2019.
 */
public final class FormType {
//    public interface DrinkTypeInterface {
//
//        String getDisplayableType();
//    }

    public static enum MainForm {

        NULL,
        Enchanting_0,
        Enchanting_1,
        Class_0,
        Class_1,
        Class_2,
        Class_3,
        Faction_Create_0,
        Faction_Admin_Page_1,
        Faction_Admin_Page_SLR,
        Faction_Create_0_Error,
        Faction_Invite_Choose,
        Faction_Invited,
        Faction_Chat_Choose,
        Faction_Delete_Confirm,
        Faction_Enemy_Choose,
        Faction_Info_Self,
        Faction_Info_Other,
        Faction_Join_List,
        Faction_Kick_List,
        Faction_Chat_Faction, Class_Settings_TNT, Class_HowToUse_TNT, Class_HowToUse_TNT_Commands, Class_HowToUse_TNT_PA, Class_HowToUse_TNT_Unlocks, Class_HowToUse_TNT_EXP, PlayerSettings0, PlayerSettings1, PlayerSettingsPage0, SettingsPage0, PlayerFactionSettingsPage0;
        public int getID(){
            return ordinal();
        }

    }

    public static enum SubMenu {
        Enchanting_Confirm,
        MainMenu,
        Offense,
        Defense, Miner,

    }

//    public static enum Drink {
//
//        COLUMBIAN("Columbian Blend", DrinkType.COFFEE),
//        ETHIOPIAN("Ethiopian Blend", DrinkType.COFFEE),
//        MINT_TEA("Mint", DrinkType.TEA),
//        HERBAL_TEA("Herbal", DrinkType.TEA),
//        EARL_GREY("Earl Grey", DrinkType.TEA);
//        private final SubForm label;
//        private final MainForm type;
//
//        private Drink(String label, DrinkType type) {
//            this.label = label;
//            this.type = type;
//        }
//
//        public String getDisplayableType() {
//            return type.getDisplayableType();
//        }
//
//        public String getLabel() {
//            return label;
//        }
//    }

}
