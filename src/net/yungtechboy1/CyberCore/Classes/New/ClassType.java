package net.yungtechboy1.CyberCore.Classes.New;

public enum ClassType {
    Unknown, Class_Miner_TNT_Specialist, Class_Miner_MineLife, Class_Offense_Mercenary, Class_Offense_DragonSlayer, Class_Magic_Enchanter, Class_Rouge_Theif, Class_Offense_Knight, Class_Offense_Holy_Knight, Class_Offense_Dark_Knight, Class_Offense_Assassin, Class_Offense_Raider, Class_Magic_Sorcerer, Class_Priest, Priest;


    public int getKey() {
        return ordinal();
    }
}
