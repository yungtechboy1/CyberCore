package net.yungtechboy1.CyberCore;

import cn.nukkit.nbt.tag.CompoundTag;

/**
 * Created by carlt on 4/12/2019.
 */
public class CoreSettings {
    private boolean HudOff = false;
    private boolean HudClassOff = false;
    private boolean HudPosOff = false;
    private boolean HudFactionOff = false;

    public CoreSettings(boolean hudOff, boolean hudClassOff, boolean hudPosOff, boolean hudFactionOff) {
        HudOff = hudOff;
        HudClassOff = hudClassOff;
        HudPosOff = hudPosOff;
        HudFactionOff = hudFactionOff;
    }

    public CoreSettings() {
        HudOff = false;
        HudClassOff = false;
        HudPosOff = false;
        HudFactionOff = false;
    }

    public CoreSettings(CompoundTag ct) {
        if (ct.contains("HudOff")) HudOff = ct.getBoolean("HudOff");
        if (ct.contains("HudClassOff")) HudClassOff = ct.getBoolean("HudClassOff");
        if (ct.contains("HudPosOff")) HudPosOff = ct.getBoolean("HudPosOff");
        if (ct.contains("HudFactionOff")) HudFactionOff = ct.getBoolean("HudFactionOff");
    }

    public boolean isHudOff() {
        return HudOff;
    }

    public void setHudOff(boolean hudOff) {
        HudOff = hudOff;
    }

    public void setHudOff() {
        HudOff = true;
        HudClassOff = true;
        HudPosOff = true;
        HudFactionOff = true;
    }

    public boolean isHudClassOff() {
        return HudClassOff;
    }

    public void setHudClassOff(boolean hudClassOff) {
        HudClassOff = hudClassOff;
    }

    public void setHudClassOff() {


        setHudClassOff(!isHudClassOff());
    }

    public boolean isHudPosOff() {
        return HudPosOff;
    }

    public void setHudPosOff(boolean hudPosOff) {
        HudPosOff = hudPosOff;
    }

    public void setHudPosOff() {

        setHudPosOff(!isHudPosOff());
    }

    public boolean isHudFactionOff() {
        return HudFactionOff;
    }

    public void setHudFactionOff(boolean hudFactionOff) {
        HudFactionOff = hudFactionOff;
    }

    public void setHudFactionOff() {
        setHudFactionOff(!isHudFactionOff());
    }

    public void TurnOnHUD() {
        HudOff = false;
        HudClassOff = false;
        HudPosOff = false;
        HudFactionOff = false;
    }

    public CompoundTag toCompoundTag() {
        return new CompoundTag("CoreSettings") {{
            putBoolean("HudOff", HudOff);
            putBoolean("HudClassOff", HudClassOff);
            putBoolean("HudPosOff", HudPosOff);
            putBoolean("HudFactionOff", HudFactionOff);
        }};
    }
}
