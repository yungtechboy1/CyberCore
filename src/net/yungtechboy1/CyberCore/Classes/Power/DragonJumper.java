package net.yungtechboy1.CyberCore.Classes.Power;

import cn.nukkit.math.BlockFace;
import cn.nukkit.math.Vector3;
import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.Classes.New.Offense.DragonSlayer;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.PowerEnum;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.PowerStackable;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Slot.LockedSlot;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Slot.PowerHotBar;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.Custom.Events.CustomEntityDamageByEntityEvent;
import net.yungtechboy1.CyberCore.PlayerJumpEvent;

public class DragonJumper extends PowerHotBar {
    public DragonJumper(BaseClass b) {
        super(b, 100, 1, LockedSlot.SLOT_9);
    }

    @Override
    public CustomEntityDamageByEntityEvent CustomEntityDamageByEntityEvent(CustomEntityDamageByEntityEvent e) {
        return e;
    }

//    @Override
//    public PlayerJumpEvent PlayerJumpEvent(PlayerJumpEvent e) {
//        initPowerRun();
//        return super.PlayerJumpEvent(e);
//    }


    @Override
    protected int getCooldownTime() {
        return 15;
    }

    @Override
    public PowerEnum getType() {
        return PowerEnum.DragonJumper;
    }

    public Object usePower( Object... args) {
        BlockFace bf = getPlayer().getDirection();
        Vector3 m = bf.getUnitVector();
        Vector3 mm = getPlayer().getMotion().add(0,1,0).multiply(3).add(m);
//        getPlayer().addMotion(mm.x,mm.y,mm.z);
        getPlayer().addMovement(mm.x,mm.y,mm.z,0,0,0);
        getPlayer().sendMessage("Drangon Jumper Activated!!!!!!!!!!!!!!!!!"+mm);
        return null;
    }

    @Override
    public String getName() {
        return "Dragon Jumper";
    }
}
