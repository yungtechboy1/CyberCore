package net.yungtechboy1.CyberCore.Events;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.block.BlockPlaceEvent;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.event.inventory.CraftItemEvent;
import cn.nukkit.event.inventory.InventoryOpenEvent;
import cn.nukkit.event.inventory.InventoryPickupArrowEvent;
import cn.nukkit.event.inventory.InventoryPickupItemEvent;
import cn.nukkit.event.player.*;
import net.yungtechboy1.CyberCore.CyberCoreMain;

/**
 * Created by carlt_000 on 2/4/2017.
 */
public class ForbidAction implements Listener {
    CyberCoreMain Main;

    public ForbidAction(CyberCoreMain main) {
        Main = main;
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onBlockPlace(BlockPlaceEvent event) {
        cancel(event.getPlayer(), event);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onBlockBreak(BlockBreakEvent event) {
        cancel(event.getPlayer(), event);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onEntityDamage(EntityDamageEvent event) {
        Player player = null;
        if ((event.getEntity() instanceof Player)) {
            player = (Player) event.getEntity();
        } else if ((event instanceof cn.nukkit.event.entity.EntityDamageByEntityEvent)) {
            cn.nukkit.entity.Entity damager = ((cn.nukkit.event.entity.EntityDamageByEntityEvent) event).getDamager();
            if ((damager instanceof Player)) player = (Player) damager;
        } else if ((event instanceof cn.nukkit.event.entity.EntityDamageByChildEntityEvent)) {
            cn.nukkit.entity.Entity damager = ((cn.nukkit.event.entity.EntityDamageByChildEntityEvent) event).getDamager();
            if ((damager instanceof Player)) player = (Player) damager;
        }
        if (player != null) cancel(player, event);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onInventoryItemPickup(InventoryPickupItemEvent event) {
        Player player = (event.getInventory().getHolder() instanceof Player) ? (Player) event.getInventory().getHolder() : null;
        if (player == null) return;
        cancel(player, event);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onInventoryArrowPickup(InventoryPickupArrowEvent event) {
        Player player = (event.getInventory().getHolder() instanceof Player) ? (Player) event.getInventory().getHolder() : null;
        if (player == null) return;
        cancel(player, event);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onInventoryOpenEvent(InventoryOpenEvent event) {
        if (event.getPlayer().isOnline()) cancel(event.getPlayer(), event);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onPlayerGameModeChangeEvent(PlayerGameModeChangeEvent event) {
        cancel(event.getPlayer(), event);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onCraftItemEvent(CraftItemEvent event) {
        cancel(event.getPlayer(), event);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onPlayerAchievementAwardedEvent(PlayerAchievementAwardedEvent event) {
        cancel(event.getPlayer(), event);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onPlayerChatEvent(PlayerChatEvent event) {
        cancel(event.getPlayer(), event);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onPlayerCommandPreprocessEvent(PlayerCommandPreprocessEvent event) {
        if (Main.PasswordFactoy.isPlayerLoggedIn(event.getPlayer())) return;
        String cmd = event.getMessage().substring(1).split(" ")[0];
        if ((cmd != null) && ((cmd.equalsIgnoreCase("register")) || (cmd.equalsIgnoreCase("login"))))
            return;
        event.setCancelled();
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onPlayerDropItemEvent(PlayerDropItemEvent event) {
        cancel(event.getPlayer(), event);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onPlayerFoodLevelChangeEvent(cn.nukkit.event.player.PlayerFoodLevelChangeEvent event) {
        cancel(event.getPlayer(), event);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onPlayerInteractEvent(cn.nukkit.event.player.PlayerInteractEvent event) {
        cancel(event.getPlayer(), event);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onPlayerItemConsumeEvent(PlayerItemConsumeEvent event) {
        cancel(event.getPlayer(), event);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onPlayerMoveEvent(PlayerMoveEvent event) {
        cancel(event.getPlayer(), event);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onPlayerToggleSneakEvent(cn.nukkit.event.player.PlayerToggleSneakEvent event) {
        cancel(event.getPlayer(), event);
    }

    private void cancel(Player player, cn.nukkit.event.Cancellable event) {
        if (!Main.PasswordFactoy.GetPassword(player).getLoggedin()) event.setCancelled();
    }
}