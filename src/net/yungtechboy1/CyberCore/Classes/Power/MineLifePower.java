package net.yungtechboy1.CyberCore.Classes.Power;

import cn.nukkit.block.Block;
import cn.nukkit.item.Item;
import cn.nukkit.potion.Effect;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;

public class MineLifePower extends Power {
    public MineLifePower(int level) {
        super(((int) Math.floor(.65d * level) + 1), level);
        int psc = ((int) Math.floor(.65d * level) + 1);

//        PotionEffect = new
    }

    @Override
    public void initStages() {
        switch (getStage()) {
            case STAGE_2:
                DurationTicks = 20 * 25;
                break;
            case STAGE_3:
                DurationTicks = 20 * 35;
                break;
            case STAGE_4:
                DurationTicks = 20 * 50;
                break;
            case STAGE_5:
            case STAGE_1:
                DurationTicks = 20 * 15;
                break;
            default:
        }
    }

    @Override
    public int getType() {
        return Power.MineLife;
    }

    @Override
    public Stage getStage() {
        if (Level <= 19) {
            return Stage.STAGE_1;
        } else if (Level <= 39) {
            return Stage.STAGE_2;
        } else if (Level <= 89) {
            return Stage.STAGE_3;
        } else if (Level <= 100) {
            return Stage.STAGE_4;
        }
        return Stage.STAGE_1;
    }

    @Override
    public Effect getEffect() {
        Effect h = Effect.getEffect(Effect.HASTE);
        h.setDuration(getEffectDuration());
        return h;
    }

    @Override
    public int getEffectDuration() {
        return super.getEffectDuration();
    }

    private double GetBreakTime(Item itemInHand, Block target, double cbreakTime) {
        CyberCoreMain.getInstance().getLogger().info("BREAKTIMMMMM >> " + cbreakTime);
        if (itemInHand == null || target == null || cbreakTime < .5d) return cbreakTime;
        double fbreaktime = cbreakTime;
        int l = (int) Math.floor(Level / 10);
        fbreaktime *= (1 - ((Level / 100d) / 2));
        CyberCoreMain.getInstance().getLogger().info("NEWE BREAKTIMMMMM >> " + fbreaktime);
        return fbreaktime;
    }

    public Object usePower(Item itemInHand, Block target, double cbreakTime) {
        return GetBreakTime(itemInHand, target, cbreakTime);

    }

    @Override
    public int getCooldownTime() {
        if (Level <= 19) {
            return 60 * 15;
        } else if (Level <= 39) {
            return 60 * 13;
        } else if (Level <= 59) {
            return 60 * 11;
        } else if (Level <= 79) {
            return 60 * 8;
        } else if (Level <= 100) {
            return 60 * 5;
        }
        return super.getCooldownTime();
    }

    @Override
    public Object usePower(CorePlayer cp, Object... args) {
        if(cp == null)return null;
        if (Cooldown == null || Cooldown.isValid()) {
            cp.addEffect(getEffect());
            addCooldown();

        }
        return null;
    }
}
