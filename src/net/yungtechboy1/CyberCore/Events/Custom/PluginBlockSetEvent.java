package net.yungtechboy1.CyberCore.Events.Custom;

import cn.nukkit.block.BlockFire;
import cn.nukkit.math.Vector3;

public class PluginBlockSetEvent extends CyberEvent {
    private final Vector3 v;
    private final BlockFire blockFire;

    public PluginBlockSetEvent(Vector3 v, BlockFire blockFire) {
        super();
        this.v = v;
        this.blockFire = blockFire;
    }

    public Vector3 getV() {
        return v;
    }

    public BlockFire getBlockFire() {
        return blockFire;
    }
}
