package net.yungtechboy1.CyberCore.Factory;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.nbt.NBTIO;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.StringTag;
import cn.nukkit.nbt.tag.Tag;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.ConfigSection;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.CyberCoreMain;

import java.io.File;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by carlt_000 on 2/22/2017.
 */
public class AuctionFactory {
    CyberCoreMain CCM;
    /**
     * Settings:
     *      Key: {
     *          id:
     *          meta:
     *          count:
     *          namedtag:
     *          cost:
     *          soldby:
     *      }
     */
    Config Settings;

    public AuctionFactory(CyberCoreMain CCM) {
        this.CCM = CCM;
        Settings = new Config(new File(CCM.getDataFolder(), "Auctions.yml"), Config.YAML);
    }

    public boolean SellHand(Item i, int amount) {
        i.getNamedTag().getAllTags();

        return true;
    }

    public ArrayList<Item> getListOfItems(){
        ArrayList<Item> is = new ArrayList<>();
        for(Object o: Settings.getAll().values()){
            if(o instanceof ConfigSection){
                int id = ((ConfigSection) o).getInt("id");
                int meta = ((ConfigSection) o).getInt("meta");
                int count = ((ConfigSection) o).getInt("count");
                int cost = ((ConfigSection) o).getInt("cost");
                String soldby = ((ConfigSection) o).getString("soldby");
                String nmdtg = ((ConfigSection) o).getString("namedtag");
                Item i = Item.get(id,meta,count);
                i.setCompoundTag(nmdtg.getBytes());

                CompoundTag tag = i.getNamedTag();
                if(!i.getCustomName().equals("") && tag.contains("display") && tag.get("display") instanceof CompoundTag)tag.getCompound("display").putString("Name2", i.getCustomName());
                i.setNamedTag(tag);
                String cn = i.getCustomName();
                cn += TextFormat.RESET+"\n"+TextFormat.AQUA+
                        "-------------"+TextFormat.RESET+
                        TextFormat.GREEN+"$"+cost+TextFormat.RESET+"\n"+
                        TextFormat.GOLD+"Sold By: "+soldby;
                i.setCustomName(cn);
                is.add(i);
            }
        }
        return is;
    }

    public void saveall(){
        for(Map.Entry<String>)
    }

    public ConfigSection CompoundTagtoConfigSection(CompoundTag ct) {
        ConfigSection cs = new ConfigSection();
        for (Tag t : ct.getAllTags()) {
            if (t instanceof)
        }
    }

    public CompoundTag getCompoundFromString(String name) {
        try {
            return NBTIO.readCompressed(name.getBytes());
        } catch (Exception ex) {
            CCM.getLogger().error("Auction Item Corruped!");
        }
        return null;
    }
}
