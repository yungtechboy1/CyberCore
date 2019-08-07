package net.yungtechboy1.CyberCore;

/**
 * Created by carlt on 3/22/2019.
 */
public final class FormType {
//    public interface DrinkTypeInterface {
//
//        String getDisplayableType();
//    }

    public enum MainForm {

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
        Faction_Chat_Faction, Class_Settings_TNT, Class_HowToUse_TNT, Class_HowToUse_TNT_Commands, Class_HowToUse_TNT_PA, Class_HowToUse_TNT_Unlocks, Class_HowToUse_TNT_EXP, PlayerSettings0, PlayerSettings1, PlayerSettingsPage0, SettingsPage0, PlayerFactionSettingsPage0, Main_Class_Settings_Window, Class_Settings_Window_Knight, Class_Settings_Window_Mercenary, Class_Settings_Window, Main_Class_Settings_Window_Active_Powers, Admin_Main, Crate_Admin_ChooseCrate, Crate_Admin_KeyCreator, Crate_Admin_GetCrateKey, Shop_ConfirmPurchase, Shop_Choose_BuySell, Main_Class_Settings_Window_Learned_Power, HTP_0, HTP_1, HTP_2, HTP_3, HTP_4, HTP_5, HTP_6, HTP_7, Class_Merchant;
        public int getID(){
            return ordinal();
        }

    }

    public enum SubMenu {
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
