package net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Ability;

import cn.nukkit.event.inventory.InventoryTransactionEvent;
import cn.nukkit.inventory.PlayerInventory;
import cn.nukkit.inventory.transaction.action.InventoryAction;
import cn.nukkit.inventory.transaction.action.SlotChangeAction;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Ability.PowerAbility;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Slot.PowerHotBarInt;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Slot.LockedSlot;

public abstract class PowerAbilityHotBar extends PowerAbility implements PowerHotBarInt {
    public PowerAbilityHotBar(BaseClass b, int psc, double cost, LockedSlot ls) {
        super(b, psc, cost);
        TickUpdate = 20;
        setLS(ls);
        PowerHotBarInt.RemoveAnyItemsInSlot(getPlayer(),ls);
    }

    @Override
    public final void activate() {
        super.activate();
    }

    @Override
    public InventoryTransactionEvent InventoryTransactionEvent(InventoryTransactionEvent e) {
        if(getLS() == LockedSlot.NA)return e;
        for (InventoryAction action : e.getTransaction().getActions()) {
            if (!(action instanceof SlotChangeAction)) {
                continue;
            }
            SlotChangeAction slotChange = (SlotChangeAction) action;

            if (slotChange.getInventory() instanceof PlayerInventory) {
//                who = (Player) slotChange.getInventory().getHolder();
                //Check to see if Slot is fucked with
                if (slotChange.getSlot() == getLS().getSlot()) {
                    e.setCancelled();
                    getPlayer().sendMessage(TextFormat.RED + "Error! You can not change your Class Slot!");
                }
            }
        }

        return super.InventoryTransactionEvent(e);
    }


    boolean check = false;
    @Override
    public void onTick(int tick) {
        if(getLS() == LockedSlot.NA)return;
        if (Cooldown == null || !Cooldown.isValid()) {
            setPowerAvailable(this);
            System.out.println("ACTIVE POWER");
        } else {
            System.out.println("UNNNNNNNNACTIVE POWER");
            setPowerUnAvailable(this);
        }
        check = !check;
        if (check) antiSpamCheck(this);
        super.onTick(tick);
    }




}
