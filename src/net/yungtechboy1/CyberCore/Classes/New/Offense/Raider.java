package net.yungtechboy1.CyberCore.Classes.New.Offense;

import cn.nukkit.Player;
import cn.nukkit.utils.ConfigSection;
import net.yungtechboy1.CyberCore.Classes.New.BaseClass;

import net.yungtechboy1.CyberCore.Classes.Power.PowerEnum;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.Custom.Events.CustomEntityDamageEvent;
import net.yungtechboy1.CyberCore.CyberCoreMain;

public class Raider extends BaseClass {

    public Raider(CyberCoreMain main, CorePlayer player, ConfigSection cs) {
        super(main, player, ClassType.Class_Miner_TNT_Specialist);
    }

    public CustomEntityDamageEvent CustomEntityDamageEvent(CustomEntityDamageEvent event) {
        Player p = (Player)event.entity;
        int x = p.getFloorX() >> 4;
        int z = p.getFloorZ() >> 4;
        if(CyberCoreMain.getInstance().FM.FFactory.PlotsList.containsKey(x+"|"+z)){
            float ad = event.getDamage(CustomEntityDamageEvent.CustomDamageModifier.BASE) * -.1f;
            event.setDamage(ad, CustomEntityDamageEvent.CustomDamageModifier.ARMOR);
        }
        return event;
    }

    @Override
    public String getName() {
        return "Raider";
    }

    @Override
    public ClassType getTYPE() {
        return null;
    }

    @Override
    public void SetPowers() {

    }

    @Override
    public int getMainID() {
        return BaseClass.TYPE_Offensive_Raider;
    }

    @Override
    public void initBuffs() {

    }

    @Override
    public Object RunPower(PowerEnum powerid, Object... args) {
        return null;
    }
}
