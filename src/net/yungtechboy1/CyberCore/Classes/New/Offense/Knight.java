package net.yungtechboy1.CyberCore.Classes.New.Offense;

import cn.nukkit.Player;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.utils.ConfigSection;
import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.Classes.New.CustomDamageModifiers;
import net.yungtechboy1.CyberCore.CyberCoreMain;

public class Knight extends BaseClass {
    public Knight(CyberCoreMain main, Player player, int rank, int xp, ConfigSection cooldowns) {
        super(main, player, rank,BaseClass.TYPE_Offensive_Knight, xp, cooldowns);
    }

    public Knight(CyberCoreMain main, Player player, ConfigSection cs) {
        super(main, player, BaseClass.TYPE_Offensive_Knight,cs);
    }

    @Override
    public PlayerInteractEvent PlayerInteractEvent(PlayerInteractEvent event) {
//        PlayerInteractEvent
        if(event.getAction() == PlayerInteractEvent.RIGHT_CLICK_AIR && event.){

        }

        return super.PlayerInteractEvent(event);
    }

    @Override
    public EntityDamageEvent EntityDamageEvent(EntityDamageEvent event) {
        Player p = (Player)event.getEntity();
        if(event.getCause() == EntityDamageEvent.CAUSE_ENTITY_ATTACK){
            EntityDamageByEntityEvent edee = (EntityDamageByEntityEvent) event;
            if(edee != null){
                edee.getDamager()
            }
        }
            float ad = event.getDamage(EntityDamageEvent.MODIFIER_BASE) * -.1f;
            event.setDamage(ad,CustomDamageModifiers.MODIFIER_ARMOR_Class);
        return event;
    }

    @Override
    public String getName() {
        return "Raider";
    }

    @Override
    public int getMainID() {
        return BaseClass.TYPE_Offensive_Raider;
    }
}
