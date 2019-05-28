package net.yungtechboy1.CyberCore.Classes.Power;

import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.block.BlockPlaceEvent;
import cn.nukkit.event.entity.EntityDamageByChildEntityEvent;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.event.entity.EntityRegainHealthEvent;
import cn.nukkit.event.inventory.CraftItemEvent;
import cn.nukkit.event.player.*;
import cn.nukkit.inventory.PlayerInventory;
import cn.nukkit.item.Item;
import net.yungtechboy1.CyberCore.CorePlayer;

public abstract class PowerEvent extends Power {

    @Override
    public final boolean isEvent() {
        return false;
    }

    public PowerEvent(int lvl, CorePlayer p) {
        super(lvl, p);
    }

    public PowerEvent(int psc, int lvl, CorePlayer p) {
        super(psc, lvl, p);
    }

    public EntityDamageByEntityEvent EntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
        return event;
    }

    public EntityDamageByChildEntityEvent EntityDamageByChildEntityEvent(EntityDamageByChildEntityEvent event) {
        return event;
    }

    public CraftItemEvent CraftItemEvent(CraftItemEvent event) {
        return event;
    }

    public PlayerToggleSprintEvent PlayerToggleSprintEvent(PlayerToggleSprintEvent event) {
        return event;
    }

    public EntityDamageEvent EntityDamageEvent(EntityDamageEvent event) {
        return event;
    }

    public BlockBreakEvent BlockBreakEvent(BlockBreakEvent event) {
        return event;
    }

    public BlockPlaceEvent BlockPlaceEvent(BlockPlaceEvent event) {
        return event;
    }

    public EntityRegainHealthEvent EntityRegainHealthEvent(EntityRegainHealthEvent event) {
        return event;
    }

    public PlayerInteractEvent PlayerInteractEvent(PlayerInteractEvent event) {
        return event;
    }

    public PlayerDropItemEvent PlayerDropItemEvent(PlayerDropItemEvent event) {
//        Item dropped = event.getItem();
//        if (dropped.deepEquals(firsthand, true, true) || dropped.deepEquals(afterhand)) event.setCancelled();
        return event;
    }

    public PlayerDeathEvent PlayerDeathEvent(PlayerDeathEvent event) {
//        Item[] drops = event.getDrops();
//        Item[] drops2 = event.getDrops().clone();
//        int a = 0;
//        for (Item item : drops) {
//            if (item.deepEquals(firsthand, true, true) || item.deepEquals(afterhand, true, true)) {
//                drops2[a] = new Item(0, 0, 0);
//            }
//            a++;
//        }
//        event.setDrops(drops2);
        return event;
    }

    public PlayerQuitEvent PlayerQuitEvent(PlayerQuitEvent event) {
//        PlayerInventory pi = event.getPlayer().getInventory();
//        for (int x = 0; x < pi.getSize() - 1; x++) {
//            Item i = pi.getItem(x);
//            if (i.deepEquals(firsthand, true, true) || i.deepEquals(afterhand, true, true)) {
//                pi.setItem(x, Item.get(0));
//                break;
//            }
//
//        }
        return event;
    }

}
