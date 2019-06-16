package net.yungtechboy1.CyberCore;

import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.nbt.tag.CompoundTag;

import java.util.HashMap;

/**
 * Created by carlt on 4/12/2019.
 */
public class CoreSettings {
    private boolean ShowDamageTags = true;
    private boolean ShowAdvancedDamageTags = false;
    private boolean HudOff = false;
    private boolean HudClassOff = false;
    private boolean HudPosOff = false;
    private boolean HudFactionOff = false;
    private boolean AllowFactionRequestPopUps = false;

    public CoreSettings(HashMap<String, Object> v) {
        super();
        HudOff = (boolean) v.getOrDefault("HUD_OFF", false);
        HudClassOff = (boolean) v.getOrDefault("HUD_CLASS_OFF", false);
        HudPosOff = (boolean) v.getOrDefault("HUD_POS_OFF", false);
        HudFactionOff = (boolean) v.getOrDefault("HUD_FAC_OFF", false);
    }
    public CoreSettings(boolean hudOff, boolean hudClassOff, boolean hudPosOff, boolean hudFactionOff) {
        super();
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
        AllowFactionRequestPopUps = true;
    }

    public CoreSettings(CompoundTag ct) {
        super();
        if (ct.contains("HudOff")) HudOff = ct.getBoolean("HudOff");
        if (ct.contains("HudClassOff")) HudClassOff = ct.getBoolean("HudClassOff");
        if (ct.contains("HudPosOff")) HudPosOff = ct.getBoolean("HudPosOff");
        if (ct.contains("HudFactionOff")) HudFactionOff = ct.getBoolean("HudFactionOff");
    }

    public boolean getShowDamageTags() {
        return ShowDamageTags;
    }

    public void setShowDamageTags(boolean showDamageTags) {
        ShowDamageTags = showDamageTags;
    }

    public boolean getShowAdvancedDamageTags() {
        return ShowAdvancedDamageTags;
    }

    public void setShowAdvancedDamageTags(boolean showAdvancedDamageTags) {
        ShowAdvancedDamageTags = showAdvancedDamageTags;
    }

    public boolean isAllowFactionRequestPopUps() {
        return AllowFactionRequestPopUps;
    }

    public void setAllowFactionRequestPopUps(boolean allowFactionRequestPopUps) {
        AllowFactionRequestPopUps = allowFactionRequestPopUps;
    }

    public void updateFromWindow(FormResponseCustom frc) {
        setShowDamageTags(frc.getToggleResponse(0));
        setShowAdvancedDamageTags(frc.getToggleResponse(1));
        setHudOff(!frc.getToggleResponse(2));
        setHudClassOff(!frc.getToggleResponse(3));
        setHudFactionOff(!frc.getToggleResponse(4));
        setHudPosOff(!frc.getToggleResponse(5));
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
