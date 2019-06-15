package net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Slot;

public enum LockedSlot {
    NA,
    SLOT_9,
    SLOT_8,
    SLOT_7;

    public int getSlot() {
        if (this == NA) return -1;
        if (this == SLOT_7) return 6;
        if (this == SLOT_8) return 7;
        if (this == SLOT_9) return 8;
        return 8;
    }
}