package net.yungtechboy1.CyberCore.Custom.Item;

import cn.nukkit.block.Block;
import cn.nukkit.item.ItemBlock;

public class CustomItemEnchantingBlock extends ItemBlock {
    public CustomItemEnchantingBlock(Block block) {
        super(block);
    }

    public CustomItemEnchantingBlock(Block block, Integer meta) {
        super(block, meta);
    }

    public CustomItemEnchantingBlock(Block block, Integer meta, int count) {
        super(block, meta, count);
    }
}
