package net.yungtechboy1.CyberCore.Classes.New.Offense;

import cn.nukkit.Player;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.item.Item;
import cn.nukkit.utils.ConfigSection;
import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.Classes.New.ClassType;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.PowerEnum;
import net.yungtechboy1.CyberCore.Classes.PowerSource.PrimalPowerType;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;

public class Theif extends BaseClass {

    public Theif(CyberCoreMain main, CorePlayer player, ConfigSection cs) {
        super(main, player, ClassType.Class_Rouge_Theif);
    }

    @Override
    public EntityDamageEvent EntityDamageEvent(EntityDamageEvent event) {
        Player p = (Player) event.getEntity();
        Item i = p.getInventory().getItemInHand();
        //TODO Check to see if item is a Dagger/Short Sword
//        if()
        return event;
    }

    @Override
    public String getName() {
        return "Theif";
    }


    @Override
    public PrimalPowerType getPowerSourceType() {
        return PrimalPowerType.Moon;
    }

    @Override
    public ClassType getTYPE() {
        return ClassType.Class_Rouge_Theif;
    }

    @Override
    public void SetPowers() {

    }

    @Override
    public void initBuffs() {

    }

    @Override
    public Object RunPower(PowerEnum powerid, Object... args) {
        return null;
    }

}
