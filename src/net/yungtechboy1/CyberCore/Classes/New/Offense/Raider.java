package net.yungtechboy1.CyberCore.Classes.New.Offense;

import cn.nukkit.Player;
import cn.nukkit.utils.ConfigSection;
import net.yungtechboy1.CyberCore.Classes.New.BaseClass;

import net.yungtechboy1.CyberCore.Classes.Power.FactionDamagerPower;
import net.yungtechboy1.CyberCore.Classes.Power.Power;
import net.yungtechboy1.CyberCore.Classes.Power.VanisherPower;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.Custom.Events.CustomEntityDamageEvent;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.Manager.Factions.Cmds.List;

public class Raider extends BaseClass {

    public Raider(CyberCoreMain main, CorePlayer player, ConfigSection cs) {
        super(main, player, ClassType.Offensive_Raider);
        ListenToEvents = true;
    }

    //TODO
    //Not Implemented and CustomEntityDamageEvent not Implemented!
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
    public void SetPowers() {
        Powers.add(new FactionDamagerPower(getPlayer(),getLVL()));
        Powers.add(new VanisherPower(getPlayer(),getLVL()));
    }

    @Override
    public int getMainID() {
        return BaseClass.ClassType.Offensive_Raider.ordinal();
    }

    @Override
    public Object RunPower(Power.PowerType powerid, Object... args) {
        return null;
    }
}
