package net.yungtechboy1.CyberCore.Classes.Power;

import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.math.BlockFace;
import cn.nukkit.math.Vector3;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Passive.PassivePower;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.PowerEnum;
import net.yungtechboy1.CyberCore.Custom.Events.CustomEntityDamageByEntityEvent;
import net.yungtechboy1.CyberCore.PlayerJumpEvent;

public class FireBox extends PassivePower {
    private boolean WaitingOnFall = false;
    private int JumpTick = -1;


    public FireBox(BaseClass b) {
        super(b, 100);
        //TODO make this so that this power Runs Automatically!
        TickUpdate = 10;
        CanSendCanNotRunMessage = false;
    }

    @Override
    public CustomEntityDamageByEntityEvent CustomEntityDamageByEntityEvent(CustomEntityDamageByEntityEvent e) {
        return e;
    }

    @Override
    public PlayerJumpEvent PlayerJumpEvent(PlayerJumpEvent e) {
        initPowerRun();
        return e;
//        return super.PlayerJumpEvent(e);
    }

    @Override
    public EntityDamageEvent EntityDamageEvent(EntityDamageEvent e) {

        return super.EntityDamageEvent(e);
    }

    @Override
    public int getPowerSuccessChance() {
        return (90 / 5) * getStage().getValue();
    }

    @Override
    protected int getCooldownTime() {
        switch (getStage()) {
            default:
            case NA:
            case STAGE_1:
                return 45;
            case STAGE_2:
                return 35;
            case STAGE_3:
                return 30;
            case STAGE_4:
                return 25;
            case STAGE_5:
                return 15;
        }
    }

    @Override
    public PowerEnum getType() {
        return PowerEnum.DragonJumper;
    }

    public Object usePower(Object... args) {
        BlockFace bf = getPlayer().getDirection();
        getPlayer().sendMessage("ORIGINBAL M " + getPlayer().getMotion());
        Vector3 mm = getPlayer().getMotion().add(0, .5, 0).multiply(1.25);
        getPlayer().setMotion(mm);
        getPlayer().sendMessage("Drangon Jumper Activated!!!!!!!!!!!!!!!!!" + mm);
        WaitingOnFall = true;
        JumpTick = getPlayer().getServer().getTick();
        return null;
    }

    @Override
    public String getName() {
        return "Dragon Jumper";
    }

    @Override
    public void onTick(int tick) {
        if (WaitingOnFall) {
            if (getPlayer().isOnGround() || getPlayer().isSwimming()) {
                WaitingOnFall = false;
                JumpTick = -1;
            } else if (getPlayer().getLevel().getBlock(getPlayer().down()).getId() != 0) {
                WaitingOnFall = false;
                getPlayer().resetFallDistance();
                JumpTick = -1;
            } else if (((20 * 10) + JumpTick <  getPlayer().getServer().getTick())) {
                WaitingOnFall = false;
                JumpTick = -1;
            }else{
                getPlayer().resetFallDistance();
            }
        }
        super.onTick(tick);
    }

}
