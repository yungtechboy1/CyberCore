package net.yungtechboy1.CyberCore.Classes.Power;

import cn.nukkit.block.Block;
import cn.nukkit.item.Item;
import cn.nukkit.potion.Effect;
import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Base.PowerSettings;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Base.StagePowerAbstract;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.PowerEnum;
import net.yungtechboy1.CyberCore.Custom.Events.CustomEntityDamageByEntityEvent;
import net.yungtechboy1.CyberCore.CyberCoreMain;

public class MineLifePower extends StagePowerAbstract {
    public MineLifePower(BaseClass b) {
        super(b, new PowerSettings(false,true,true,false),(int) Math.floor(.65d * b.getLVL()) + 1);
        int psc = ((int) Math.floor(.65d * b.getLVL()) + 1);

//        PotionEffect = new
    }

    @Override
    public void initStages() {
        switch (getStage()) {
            case STAGE_2:
                setDurationTick(20 * 25);
                break;
            case STAGE_3:
                setDurationTick(20 * 35);
                break;
            case STAGE_4:
                setDurationTick(20 * 50);
                break;
            case STAGE_5:
            case STAGE_1:
                setDurationTick(20 * 15);
                break;
            default:
        }
    }

    @Override
    public PowerEnum getType() {
        return PowerEnum.MineLife;
    }

    @Override
    public StageEnum getStage() {
        if (PlayerClass.getLVL() <= 19) {
            return StageEnum.STAGE_1;
        } else if (PlayerClass.getLVL() <= 39) {
            return StageEnum.STAGE_2;
        } else if (PlayerClass.getLVL() <= 89) {
            return StageEnum.STAGE_3;
        } else if (PlayerClass.getLVL() <= 100) {
            return StageEnum.STAGE_4;
        }
        return StageEnum.STAGE_1;
    }

    @Override
    public String getName() {
        return "MineLife";
    }

    @Override
    public String getDispalyName() {
        return getName();
    }

    @Override
    public Effect getEffect() {
        Effect h = Effect.getEffect(Effect.HASTE);
        h.setDuration(getDurationTick());
        return h;
    }


    private double GetBreakTime(Item itemInHand, Block target, double cbreakTime) {
        CyberCoreMain.getInstance().getLogger().info("BREAKTIMMMMM >> " + cbreakTime);
        if (itemInHand == null || target == null || cbreakTime < .5d) return cbreakTime;
        double fbreaktime = cbreakTime;
        int l = (int) Math.floor(PlayerClass.getLVL() / 10);
        fbreaktime *= (1 - ((PlayerClass.getLVL() / 100d) / 2));
        CyberCoreMain.getInstance().getLogger().info("NEWE BREAKTIMMMMM >> " + fbreaktime);
        return fbreaktime;
    }

    public Object usePower(Item itemInHand, Block target, double cbreakTime) {
        return GetBreakTime(itemInHand, target, cbreakTime);

    }

    @Override
    public CustomEntityDamageByEntityEvent CustomEntityDamageByEntityEvent(CustomEntityDamageByEntityEvent e) {
        return e;
    }

    @Override
    public int getCooldownTime() {
        if (PlayerClass.getLVL() <= 19) {
            return 60 * 15;
        } else if (PlayerClass.getLVL() <= 39) {
            return 60 * 13;
        } else if (PlayerClass.getLVL() <= 59) {
            return 60 * 11;
        } else if (PlayerClass.getLVL() <= 79) {
            return 60 * 8;
        } else if (PlayerClass.getLVL() <= 100) {
            return 60 * 5;
        }
        return super.getCooldownTime();
    }
}
