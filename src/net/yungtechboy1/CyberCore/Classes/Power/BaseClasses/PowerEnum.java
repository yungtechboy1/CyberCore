package net.yungtechboy1.CyberCore.Classes.Power.BaseClasses;

public enum PowerEnum {
    Unknown, MercenaryDoubleTake, MercenaryRegeneration, HolyKnightHeal, TNTAirStrike, MineLife, MinerOreKnowledge, TNTSpecalist, MercenaryBlindingStrike, MercenaryDisarm, DragonJumper, KnightSandShield, DarkKnightPosionousStench, FireStomp, FireBox, DoubleTime, AntidotePower, ThunderStrike, Swappa, UnEven, RagePower, LastStand;

    public static PowerEnum fromint(int i) {
        if (values().length < i) {
            return values()[i];
        }
        return Unknown;
    }

    public static PowerEnum fromstr(String i) {
        for (PowerEnum e : values()) {
            if (e.name().equalsIgnoreCase(i)) return e;
        }
        return Unknown;
    }

    @Override
    public String toString() {
        return this.name();
    }
}
