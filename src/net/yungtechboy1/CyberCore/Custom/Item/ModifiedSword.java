package net.yungtechboy1.CyberCore.Custom.Item;

import cn.nukkit.block.Block;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemSwordDiamond;
import cn.nukkit.item.ItemTool;

/**
 * Created by carlt on 3/13/2019.
 */
public class ModifiedSword extends ItemTool {

    private ModifiedMaterial M;
    private ModifiedLength L;
    private ItemRank IR;
    private Rarity RR;


    public ModifiedSword() {
        this(0, 1);
    }

    public ModifiedSword(Integer meta) {
        this(meta, 1);
    }

    public ModifiedSword(Integer meta, int count) {
        //TODO GET M from Meta Data
//        super(M.getId(), meta, count, "Diamond Sword");
        super(Item.DIAMOND_SWORD, meta, count, "Diamond Sword");
    }

    @Override
    public int getMaxDurability() {
        return ItemTool.DURABILITY_DIAMOND;
    }

    @Override
    public boolean isSword() {
        return true;
    }

    @Override
    public int getTier() {
        return ItemTool.TIER_DIAMOND;
    }

    @Override
    public int getAttackDamage() {
        return 7;
    }


}

