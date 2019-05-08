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
        CompoundTag tag;
        if (item.hasCompoundTag()) tag = item.getNamedTag();
        else tag = new CompoundTag();
        tag.putCompound("display", new CompoundTag());

        if (!item.getCustomName().equals("") && tag.contains("display") && tag.get("display") instanceof CompoundTag)
            tag.getCompound("display").putString("Name2", item.getCustomName());

        tag.getCompound("display").putInt("masterid", masterid);
        tag.getCompound("display").putInt("cost", Cost);
        tag.getCompound("display").putString("soldbyn", Soldbyn);
        tag.getCompound("display").putString("soldby", Soldby);

        item.setNamedTag(tag);


        String cn = item.getCustomName();

        if (cn.equalsIgnoreCase("")) cn = item.getName();

        cn += TextFormat.RESET + "\n" + TextFormat.AQUA +
                "-------------" + TextFormat.RESET + "\n" +
                TextFormat.GREEN + "$" + Cost + TextFormat.RESET + "\n" +
                TextFormat.GOLD + "Sold By: " + Soldbyn
        // + TextFormat.RESET + "\n" +TextFormat.BLACK+"{#"+id;
        ;

        item.setCustomName(cn);
        return item;
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
}
