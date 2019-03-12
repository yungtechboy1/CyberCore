package net.yungtechboy1.CyberCore.Classes.New.Offense;

import cn.nukkit.Player;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.item.Item;
import cn.nukkit.utils.ConfigSection;
import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.Classes.New.CustomDamageModifiers;
import net.yungtechboy1.CyberCore.CyberCoreMain;

public class Assassin extends BaseClass {
    public Assassin(CyberCoreMain main, Player player, int rank, int xp, ConfigSection cooldowns) {
        super(main, player, rank, xp, cooldowns);
    }

    public Assassin(CyberCoreMain main, Player player, ConfigSection cs) {
        super(main, player, cs);
    }

    @Override
    public EntityDamageEvent EntityDamageEvent(EntityDamageEvent event) {
        Player p = (Player)event.getEntity();
        Item i = p.getInventory().getItemInHand();
        if(i.getId() == Item.BOW){
            float ad = event.getDamage(EntityDamageEvent.MODIFIER_BASE) * -.1f;
            event.setDamage(ad,CustomDamageModifiers.MODIFIER_ARMOR_Class);
        }
//        if()
        return event;
    }

    @Override
    public String getName() {
        return "Assassin";
    }

    @Override
    public int getMainID() {
        return BaseClass.TYPE_Offensive_Assassin;
    }
}
