package net.yungtechboy1.CyberCore.Classes.New.Offense;

import cn.nukkit.Player;
import cn.nukkit.utils.ConfigSection;
import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.Classes.New.Buff;
import net.yungtechboy1.CyberCore.Classes.New.DeBuff;
import net.yungtechboy1.CyberCore.Classes.Power.PowerEnum;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.Custom.Events.CustomEntityDamageByEntityEvent;
import net.yungtechboy1.CyberCore.Custom.Events.CustomEntityDamageEvent;
import net.yungtechboy1.CyberCore.CyberCoreMain;

public class Knight extends BaseClass {
//    public Knight(CyberCoreMain main, Player player, int rank, int xp, ConfigSection cooldowns) {
//        super(main, player, rank,BaseClass.TYPE_Offensive_Knight, xp, cooldowns);
//        SwingTime = 27;
//    }

    public Knight(CyberCoreMain main, CorePlayer player) {
        super(main, player, ClassType.Class_Miner_TNT_Specialist);
    }

    @Override
    public CustomEntityDamageByEntityEvent CustomEntityDamageByEntityEvent(CustomEntityDamageByEntityEvent event) {
//        Player p = (Player)event.entity;
            float ad = event.getDamage(CustomEntityDamageEvent.CustomDamageModifier.BASE) * -.1f;
            event.setDamage(ad, CustomEntityDamageEvent.CustomDamageModifier.MODIFIER_ARMOR_ABILLITY);
            event.setCoolDownTicks(SwingTime);
        return event;
    }

    //TODO
    @Override
    public float getDamageBuff() {
        return 1f+(.5f*(getTeir().ordinal()/10));
    }
    //TODO
    @Override
    public float getArmorBuff() {
        return 1f+(.3f*(getTeir().ordinal()/10));
    }

    @Override
    public int getExtraHealth(){
        return 4;
    }

    @Override
    public Object RunPower(PowerEnum powerid, Object... args) {
        return null;
    }


    @Override
    public String getName() {
        return "Knight";
    }

    @Override
    public ClassType getTYPE() {
        return ClassType.Class_Offense_Knight;
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
        addBuff(new Buff(Buff.BuffType.Damage,1.5f));
        addBuff(new Buff(Buff.BuffType.Armor,1.3f));
        addBuff(new Buff(Buff.BuffType.Health,4f));
        addDeBuff(new DeBuff(Buff.BuffType.Movement,.65f));
        addDeBuff(new DeBuff(Buff.BuffType.SwingSpeed,1.5f));
        addBuff(new Buff(Buff.BuffType.SuperFoodHeartRegin,1f));
    }

}
