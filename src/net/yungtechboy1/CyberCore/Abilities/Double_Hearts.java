package net.yungtechboy1.CyberCore.Abilities;

import cn.nukkit.Player;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.event.player.PlayerToggleSprintEvent;
import net.yungtechboy1.CyberCore.Classes.BaseClass;
import net.yungtechboy1.CyberCore.CyberCoreMain;

/**
 * Created by carlt_000 on 1/26/2017.
 */
public class Double_Hearts extends Ability {
    public Double_Hearts(CyberCoreMain main,BaseClass bc){
        super(main,bc, BaseClass.TYPE_TANK,Ability.TANK_DOUBLE_HEARTS);
    }

    @Override
    public boolean activate() {
        player.setMaxHealth(40);
        player.setMovementSpeed(0.05f);
        player.sendAttributes();
        return true;
    }


    @Override
    public int GetCooldown() {
        return getTime() + 240;
    }

    @Override
    public void EntityDamageEvent(EntityDamageEvent event) {
        if (event instanceof EntityDamageByEntityEvent) {
            if (((EntityDamageByEntityEvent) event).getDamager().getName().equalsIgnoreCase(player.getName())) {
                event.setDamage(event.getDamage() * .80f);
            }
        }
    }

    @Override
    public void PlayerToggleSprintEvent(PlayerToggleSprintEvent event) {
        if(event.isSprinting())event.setCancelled();
    }

    @Override
    public void deactivate() {
        player.setMaxHealth(20);
        player.setMovementSpeed(0.1f);
        player.sendAttributes();
        super.deactivate();
    }

    @Override
    public void onRun(int i) {
        deactivate();
    }

}