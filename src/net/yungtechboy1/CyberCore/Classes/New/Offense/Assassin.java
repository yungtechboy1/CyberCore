package net.yungtechboy1.CyberCore.Classes.New.Offense;

import cn.nukkit.Player;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.utils.ConfigSection;
import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;

public class Assassin extends BaseClass {
    public Assassin(CyberCoreMain main, CorePlayer player, ConfigSection cs) {
        super(main, player, ClassType.Class_Miner_TNT_Specialist);
    }

    @Override
    public EntityDamageEvent EntityDamageEvent(EntityDamageEvent event) {

        return event;
    }

    @Override
    public String getName() {
        return "Assassin";
    }

    @Override
    public void SetPowers() {

    }

    @Override
    public int getMainID() {
        return BaseClass.TYPE_Offensive_Assassin;
    }

    @Override
    public Object RunPower(int powerid, Object... args) {
        return null;
    }
}
