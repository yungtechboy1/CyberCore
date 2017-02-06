package net.yungtechboy1.CyberCore.Custom.Inventory;

import cn.nukkit.Player;
import cn.nukkit.entity.EntityHuman;
import cn.nukkit.inventory.ContainerInventory;
import cn.nukkit.inventory.InventoryType;
import cn.nukkit.level.Level;
import cn.nukkit.network.protocol.BlockEventPacket;

/**
 * Created by carlt_000 on 1/19/2017.
 */
public class TestInv2 extends ContainerInventory {

    public TestInv2(EntityHuman chest) {
        super(chest, InventoryType.ENDER_CHEST);
    }

    @Override
    public EntityHuman getHolder() {
        return (EntityHuman)this.holder;
    }

    @Override
    public void onOpen(Player who) {
        super.onOpen(who);

        if (this.getViewers().size() == 1) {
            BlockEventPacket pk = new BlockEventPacket();
            pk.x = (int) this.getHolder().getX();
            pk.y = (int) this.getHolder().getY();
            pk.z = (int) this.getHolder().getZ();
            pk.case1 = 1;
            pk.case2 = 2;

            Level level = this.getHolder().getLevel();
            if (level != null) {
                level.addChunkPacket((int) this.getHolder().getX() >> 4, (int) this.getHolder().getZ() >> 4, pk);
            }
        }
    }

    @Override
    public void onClose(Player who) {
        if (this.getViewers().size() == 1) {
            BlockEventPacket pk = new BlockEventPacket();
            pk.x = (int) this.getHolder().getX();
            pk.y = (int) this.getHolder().getY();
            pk.z = (int) this.getHolder().getZ();
            pk.case1 = 1;
            pk.case2 = 0;

            Level level = this.getHolder().getLevel();
            if (level != null) {
                level.addChunkPacket((int) this.getHolder().getX() >> 4, (int) this.getHolder().getZ() >> 4, pk);
            }
        }

        super.onClose(who);
    }
}