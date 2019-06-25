package net.yungtechboy1.CyberCore.Classes.Power.BaseClasses;

public enum PowerEnum {
    Unknown,MercenaryDoubleTake, MercenaryRegeneration, HolyKnightHeal, TNTAirStrike, MineLife, MinerOreKnowledge, TNTSpecalist, MercenaryBlindingStrike, MercenaryDisarm, DragonJumper, KnightSandShield, DarkKnightPosionousStench, FireStomp, FireBox;
    public static PowerEnum fromint(int i){
        if(values().length <i){
            return values()[i];
        }
        return Unknown;
    }
}
