package net.yungtechboy1.CyberCore.Custom.Block;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockTransparentMeta;
import cn.nukkit.blockentity.BlockEntity;
import cn.nukkit.blockentity.BlockEntityEnchantTable;
import cn.nukkit.form.element.Element;
import cn.nukkit.form.element.ElementStepSlider;
import cn.nukkit.form.window.FormWindowCustom;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemTool;
import cn.nukkit.math.BlockFace;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.ListTag;
import cn.nukkit.nbt.tag.Tag;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.Custom.CustomEnchant.CustomEnchantment;
import net.yungtechboy1.CyberCore.Manager.Form.Windows.Enchanting0Window;
import net.yungtechboy1.CyberCore.Manager.Form.Windows.Enchanting1Window;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by carlt on 3/25/2019.
 */
public class BlockEnchantingTable extends BlockTransparentMeta {


    public BlockEnchantingTable() {
        super();
    }

    public BlockEnchantingTable(int meta) {
        super(meta);
    }

    @Override
    public int getId() {
        return ENCHANTING_TABLE;
    }

    public String getCustomName() {
        return getName();
    }

    @Override
    public String getName() {
        if (GetTier() == CustomEnchantment.Tier.Unknown) return "Enchanting Table";
        return GetTier().getString() + " Enchanting Table";
    }

    public CustomEnchantment.Tier GetTier() {
        BlockEntityEnchantTable a = getBlockEntity();
        if (a == null || !a.namedTag.contains("level")) return CustomEnchantment.Tier.Basic;
        int t = a.namedTag.getInt("level");
        return CustomEnchantment.Tier.GetTier(t);
    }

    @Override
    public int getToolType() {
        return ItemTool.TYPE_PICKAXE;
    }

    @Override
    public double getHardness() {
        return 5;
    }

    @Override
    public double getResistance() {
        return 6000;
    }

    @Override
    public int getLightLevel() {
        return 12;
    }

    @Override
    public boolean canBeActivated() {
        return true;
    }

    @Override
    public Item[] getDrops(Item item) {
        if (item.isPickaxe() && item.getTier() >= ItemTool.TIER_WOODEN) {
            return new Item[]{
                    toItem()
            };
        } else {
            return new Item[0];
        }
    }

    @Override
    public boolean place(Item item, Block block, Block target, BlockFace face, double fx, double fy, double fz, Player player) {
        block.setDamage(item.getDamage());
        player.getLevel().setBlock(block, this, true, true);


        CompoundTag nbt = new CompoundTag()
                .putString("id", BlockEntity.ENCHANT_TABLE)
                .putInt("x", (int) this.x)
                .putInt("y", (int) this.y)
                .putInt("z", (int) this.z)
                .putInt("level", 1);

        if (item.hasCustomName()) {
            nbt.putString("CustomName", item.getCustomName());
        }

        if (item.hasCustomBlockData()) {
            Map<String, Tag> customData = item.getCustomBlockData().getTags();
            for (Map.Entry<String, Tag> tag : customData.entrySet()) {
                nbt.put(tag.getKey(), tag.getValue());
            }
        }

        BlockEntity.createBlockEntity(BlockEntity.ENCHANT_TABLE, getLevel().getChunk((int) this.x >> 4, (int) this.z >> 4), nbt);

        return true;
    }

    public BlockEntityEnchantTable getBlockEntity() {
        if (this.getLevel() == null) return null;
        BlockEntity t = this.getLevel().getBlockEntity(this);
        BlockEntityEnchantTable enchantTable;
        if (t instanceof BlockEntityEnchantTable) {
            enchantTable = (BlockEntityEnchantTable) t;
        } else {
            getLevel().removeBlockEntity(t);
            CompoundTag nbt = new CompoundTag()
                    .putList(new ListTag<>("Items"))
                    .putString("id", BlockEntity.ENCHANT_TABLE)
                    .putInt("x", (int) this.x)
                    .putInt("y", (int) this.y)
                    .putInt("z", (int) this.z)
                    .putInt("level", (int) 1);
            enchantTable = new BlockEntityEnchantTable(this.getLevel().getChunk((int) (this.x) >> 4, (int) (this.z) >> 4), nbt);
            getLevel().addBlockEntity(enchantTable);
        }
        return enchantTable;
    }

    @Override
    public boolean onActivate(Item item, Player sender) {
        if (sender != null) {
            Player p = (Player) sender;
            CorePlayer cp = (CorePlayer) p;
            p.showFormWindow(new Enchanting0Window(getName()));
            cp.setNewWindow(new Enchanting1Window(cp,GetTier(),item));
        }

        return true;
    }

    @Override
    public boolean canHarvestWithHand() {
        return false;
    }
}
