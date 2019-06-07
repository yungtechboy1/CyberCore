package net.yungtechboy1.CyberCore.Classes.New.Offense;

import cn.nukkit.Player;
import cn.nukkit.utils.ConfigSection;
import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.Classes.New.Buff;
import net.yungtechboy1.CyberCore.Classes.New.DeBuff;
import net.yungtechboy1.CyberCore.Classes.Power.Mercenary.MercenaryDoubleTake;
import net.yungtechboy1.CyberCore.Classes.Power.Mercenary.MercenaryReneration;
import net.yungtechboy1.CyberCore.Classes.Power.PowerEnum;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.Custom.Events.CustomEntityDamageByEntityEvent;
import net.yungtechboy1.CyberCore.Custom.Events.CustomEntityDamageEvent;
import net.yungtechboy1.CyberCore.CyberCoreMain;

public class Mercenary  extends BaseClass {
//    public Knight(CyberCoreMain main, Player player, int rank, int xp, ConfigSection cooldowns) {
//        super(main, player, rank,BaseClass.TYPE_Offensive_Knight, xp, cooldowns);
//        SwingTime = 27;
//    }

    public Mercenary(CyberCoreMain main, CorePlayer player, ConfigSection cs) {
        super(main, player, ClassType.Class_Offense_Mercenary);
    }

    @Override
    public CustomEntityDamageByEntityEvent CustomEntityDamageByEntityEvent(CustomEntityDamageByEntityEvent event) {
        Player p = (Player)event.entity;
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
        return "Mercenary";
    }

    @Override
    public void SetPowers() {
        AddPower(new MercenaryDoubleTake(this,getLVL()));
        AddPower(new MercenaryReneration(this,getLVL()));
    }

    @Override
    public void onUpdate(int tick) {
        super.onUpdate(tick);
    }

    @Override
    public int getMainID() {
        return BaseClass.TYPE_Offensive_Raider;
    }

    @Override
    public void initBuffs() {
        addBuff(new Buff(Buff.BuffType.Damage,1.1f));
//        addBuff(new Buff(Buff.BuffType.Armor,1.3f));
//        addBuff(new Buff(Buff.BuffType.Health,4f));
        addDeBuff(new DeBuff(Buff.BuffType.Armor,.85f));
        addBuff(new Buff(Buff.BuffType.Movement,.85f));
        addBuff(new Buff(Buff.BuffType.SwingSpeed,1.2f));
//        addBuff(new Buff(Buff.BuffType.SuperFoodHeartRegin,1f));
    }

}
