package net.yungtechboy1.CyberCore.Data;

import cn.nukkit.item.Item;
import cn.nukkit.nbt.NBTIO;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.CorePlayer;

import java.sql.ResultSet;
import java.util.HashMap;

/**
 * Created by carlt on 4/28/2019.
 */
public class AuctionItemData {
    Item item;
    int Cost;
    String Soldby;//UUID
    String Soldbyn;//Display Name
    int masterid = -1;

    public AuctionItemData(Item i, int cost, CorePlayer seller) {
        item = i;
        Cost = cost;
        Soldby = seller.getUniqueId().toString();
        Soldbyn = seller.getDisplayName();
    }

    public AuctionItemData(ResultSet rs) throws Exception {
        int item_id = rs.getInt("item-id");
        int item_meta = rs.getInt("item-meta");
        int item_count = rs.getInt("item-count");
        byte[] namedtag = rs.getString("namedtag").getBytes();
        Cost = rs.getInt("cost");
        String soldbyn = rs.getString("soldbyn");
        String soldby = rs.getString("soldby");
        int mid = rs.getInt("master_id");

        item = Item.get(item_id, item_meta, item_count);
        item.setCompoundTag(namedtag);

        Soldby = soldby;
        Soldbyn = soldbyn;
        masterid = mid;

    }

    public AuctionItemData(HashMap<String, Object> v) {
        String nt = (String) v.get("namedtag");
        item = Item.get((int) v.get("item-id"), (int) v.get("item-meta"), (int) v.get("item-count"));
        try {
            item.setCompoundTag(NBTIO.read(nt.getBytes()));
        } catch (Exception e) {
            System.out.println("ERROR TRING TO FORMAT NAMEDTAG!!!!");
        }
        Cost = (int) v.get("cost");
        Soldby = (String) v.get("soldby");
        Soldbyn = (String) v.get("soldbyn");
        masterid = (int) v.get("master_id");
    }

    public Item MakePretty() {
        Item titem = item.clone();
        CompoundTag tag;
        if (titem.hasCompoundTag()) tag = titem.getNamedTag();
        else tag = new CompoundTag();
        if(tag.contains("ah-data"))tag.putCompound("ah-data", new CompoundTag());

        if (!titem.getCustomName().equals("") && tag.contains("ah-data") && tag.get("ah-data") instanceof CompoundTag)
            tag.getCompound("ah-data").putString("Name", titem.getCustomName());

        tag.getCompound("ah-data").putInt("masterid", masterid);
        tag.getCompound("ah-data").putInt("cost", Cost);
        tag.getCompound("ah-data").putString("soldbyn", Soldbyn);
        tag.getCompound("ah-data").putString("soldby", Soldby);

        titem.setNamedTag(tag);


        String cn = titem.getCustomName();

        if (cn.equalsIgnoreCase("")) cn = titem.getName();

        cn += TextFormat.RESET + "\n" + TextFormat.AQUA +
                "-------------" + TextFormat.RESET + "\n" +
                TextFormat.GREEN + "$" + Cost + TextFormat.RESET + "\n" +
                TextFormat.GOLD + "Sold By: " + Soldbyn
        // + TextFormat.RESET + "\n" +TextFormat.BLACK+"{#"+id;
        ;

        titem.setCustomName(cn);
        return titem.clone();
    }

    public void AddToDB() {

    }

    public Item getItem() {
        return item;
    }

    public int getCost() {
        return Cost;
    }

    public String getSoldby() {
        return Soldby;
    }

    public String getSoldbyn() {
        return Soldbyn;
    }

    public int getMasterid() {
        return masterid;
    }

    @Override
    public String toString() {
        return item.getName() + " | " + item.getCustomName() + " | " + Soldby + " | " + masterid;
    }

    public Item getKeepItem() {
        Item titem = item.clone();
        CompoundTag tag;
        if (titem.hasCompoundTag()) tag = titem.getNamedTag();
        else tag = new CompoundTag();
        if(tag.contains("ah-data")){
            CompoundTag ctah = tag.getCompound("ah-data");
            if(ctah.contains("Name"))titem.setCustomName(ctah.getString("Name"));
            tag.remove("ah-data");
        }

        titem.setCompoundTag(tag);
        return titem.clone();
    }
}
