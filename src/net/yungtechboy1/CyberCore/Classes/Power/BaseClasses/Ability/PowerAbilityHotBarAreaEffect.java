package net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Ability;

import cn.nukkit.event.inventory.InventoryTransactionEvent;
import cn.nukkit.inventory.PlayerInventory;
import cn.nukkit.inventory.transaction.action.InventoryAction;
import cn.nukkit.inventory.transaction.action.SlotChangeAction;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Slot.LockedSlot;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Slot.PowerHotBarInt;

//todo
public abstract class PowerAbilityHotBarAreaEffect extends PowerAbilityAreaEffect implements PowerHotBarInt {
    public int lastHotBarUpdate = -1;
    public boolean skip = false;
    boolean check = false;


    public PowerAbilityHotBarAreaEffect(BaseClass b, int psc, LockedSlot ls, double cost) {
        super(b, psc, cost);
        TickUpdate = 20;
        setLS(ls);
        PowerHotBarInt.RemoveAnyItemsInSlot(getPlayer(), ls);
    }

    public boolean canUpdateHotBar(int tick) {
        if (tick > lastHotBarUpdate + 10) {
            lastHotBarUpdate = tick;
            return true;
        }
        return false;
    }

    @Override
    public InventoryTransactionEvent InventoryTransactionEvent(InventoryTransactionEvent e) {
//        getPlayer().sendMessage(TextFormat.RED + "TRANS CALLEDDDDD! Slot");
        if (getLS() == LockedSlot.NA) return e;
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

        return e;
    }

    @Override
    public void onTick(int tick) {
        System.out.println("POWER TICKKKKKK1");
//        if(skip){
        super.onTick(tick);
//            skip = false;
//        }
        if (canUpdateHotBar(tick)) updateHotbar(getLS(), Cooldown, this);
        check = !check;
        if (check) antiSpamCheck(this);
    }
}
