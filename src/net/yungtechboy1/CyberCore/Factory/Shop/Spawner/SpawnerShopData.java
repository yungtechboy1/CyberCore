package net.yungtechboy1.CyberCore.Factory.Shop.Spawner;

import cn.nukkit.block.Block;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemBlock;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.Custom.Block.SpawnerWithLevelBlock;
import net.yungtechboy1.CyberCore.Custom.Item.CustomItemBlockSpawnerWithLevelBlock;
import sun.plugin.dom.core.Text;

public class SpawnerShopData {
    public SpawnerWithLevelBlock.SpawnerType SpawnerType;
    public int Price;
    public int LevelPrice;
    public int XPPrice;

    public SpawnerShopData(SpawnerWithLevelBlock.SpawnerType spawnerType, int price, int levelPrice, int xpprice) {
        SpawnerType = spawnerType;
        Price = price;
        LevelPrice = levelPrice;
        XPPrice = xpprice;
    }

    public Item getItem(boolean pretty){
        Block b =new SpawnerWithLevelBlock(SpawnerType.getID());
//        b.
//        Item i =new ItemBlock(b);
        Item i = new CustomItemBlockSpawnerWithLevelBlock(SpawnerType.getID());
        i.setNamedTag(new CompoundTag().putInt("TYPE",getSpawnerType().getID()));
//        i.setCustomName(b.getName());
        if(pretty)return i;
        i.setLore(TextFormat.AQUA+"Spawner Type: "+TextFormat.GREEN+b.getName(),
                TextFormat.AQUA+"Spawner Price: "+TextFormat.GREEN+Price,
                TextFormat.AQUA+"Level Price: "+TextFormat.GREEN+LevelPrice,
                TextFormat.AQUA+"XP Price: "+TextFormat.GREEN+XPPrice,
                TextFormat.DARK_GRAY+"type: "+getSpawnerType().name()
                );
        return i.clone();
    }
    public Item getItem(){
        return getItem(false);
    }

    public SpawnerWithLevelBlock.SpawnerType getSpawnerType() {
        return SpawnerType;
    }

    public int getPrice(int c) {
        return Price*c;
    }

    public int getPrice() {
        return Price;
    }

    public int getLevelPrice() {
        return LevelPrice;
    }

    public int getXPPrice() {
        return XPPrice;
    }

    public String getPrettyString(int count) {
        Block b =new SpawnerWithLevelBlock(SpawnerType.getID());
        return count+" "+b.getName();
    }
}
