package net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Slot;

import cn.nukkit.event.inventory.InventoryTransactionEvent;
import cn.nukkit.inventory.PlayerInventory;
import cn.nukkit.inventory.transaction.action.InventoryAction;
import cn.nukkit.inventory.transaction.action.SlotChangeAction;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Base.StagePowerAbstract;

public abstract class PowerHotBarStage extends StagePowerAbstract  implements PowerHotBarInt {

    public int lastHotBarUpdate = -1;

    public PowerHotBarStage(BaseClass b, int psc, double cost, LockedSlot ls) {
        super(b,psc, cost);
        TickUpdate = 20;
        setLS(ls);
        PowerHotBarInt.RemoveAnyItemsInSlot(getPlayer(),ls);
    }

    @Override
    public InventoryTransactionEvent InventoryTransactionEvent(InventoryTransactionEvent e) {
//        getPlayer().sendMessage(TextFormat.RED + "TRANS CALLEDDDDD! Slot");
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

        return e;
    }

    @Override
    public void sendCanNotRunMessage() {
        super.sendCanNotRunMessage();
        if(canUpdateHotBar(getPlayer().getServer().getTick()))updateHotbar(getLS(),Cooldown,this);
    }

    public boolean canUpdateHotBar(int tick) {
        if (tick > lastHotBarUpdate + 10) {
            lastHotBarUpdate = tick;
            return true;
        }
        return false;
    }

    boolean check = false;
    @Override
    public void onTick(int tick) {
        System.out.println("POWER TICKKKKKK1111111");
        super.onTick(tick);
        updateHotbar(getLS(),Cooldown,this);
        check = !check;
        if (check) antiSpamCheck(this);
    }

}

