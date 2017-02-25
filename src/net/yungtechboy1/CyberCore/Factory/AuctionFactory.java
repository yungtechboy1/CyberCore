package net.yungtechboy1.CyberCore.Factory;

import cn.nukkit.Player;
import cn.nukkit.item.Item;
import cn.nukkit.nbt.NBTIO;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.Tag;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.ConfigSection;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.CyberCoreMain;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by carlt_000 on 2/22/2017.
 */
public class AuctionFactory {
    CyberCoreMain CCM;
    /**
     * Settings:
     * Key: {
     * id:
     * meta:
     * count:
     * namedtag:
     * cost:
     * soldby:
     * }
     */
    Config Settings;

    ArrayList<Item> items = new ArrayList<>();

    public AuctionFactory(CyberCoreMain CCM) {
        this.CCM = CCM;
        Settings = new Config(new File(CCM.getDataFolder(), "Auctions.yml"), Config.YAML);
    }

    public boolean SellHand(Item i, int amount) {
        i.getNamedTag().getAllTags();

        return true;
    }

    public void Init(){
        ArrayList<Item> is = new ArrayList<>();
        ResultSet rs = ExecuteQuerySQLite("SELECT * FROM `auctions`");
        if(rs != null){
            try {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    int item_id = rs.getInt("item-id");
                    int item_meta = rs.getInt("item-meta");
                    int item_count = rs.getInt("item-count");
                    byte[] namedtag = rs.getString("namedtag").getBytes();
                    int cost = rs.getInt("cost");
                    String soldby = rs.getString("soldby");

                    Item i = Item.get(item_id, item_meta, item_count);
                    i.setCompoundTag(namedtag);

                    CompoundTag tag = i.getNamedTag();
                    if (!i.getCustomName().equals("") && tag.contains("display") && tag.get("display") instanceof CompoundTag)
                        tag.getCompound("display").putString("Name2", i.getCustomName());

                    if(tag.contains("display") && tag.get("display") instanceof CompoundTag) {
                        tag.getCompound("display").putString("soldby", soldby);
                    } else {
                        tag.putCompound("display", (new CompoundTag("display")).putString("soldby", soldby));
                    }

                    i.setNamedTag(tag);

                    String cn = i.getCustomName();

                    cn += TextFormat.RESET + "\n" + TextFormat.AQUA +
                            "-------------" + TextFormat.RESET +
                            TextFormat.GREEN + "$" + cost + TextFormat.RESET + "\n" +
                            TextFormat.GOLD + "Sold By: " + soldby
                    // + TextFormat.RESET + "\n" +TextFormat.BLACK+"{#"+id;
                    ;

                    i.setCustomName(cn);

                    is.add(i);
                }
            }catch (Exception ex){
                return;
            }
            items = is;
        }

    }

    public ArrayList<Item> getListOfItems() {
        return items;
    }

    public ArrayList<Item> getPage(int page){
        int stop = page*45 - 1;
        int start = stop - 44;
        if(start > getListOfItems().size()){
            return new ArrayList<>();
        }

        int z = 10;
        int x = 13;

       ArrayList<Item> list = new ArrayList<>();

        for(int a = start; start < getListOfItems().size(); a++){
            if(a >= stop) break;
            Item newitem = getListOfItems().get(a).clone();
            if(newitem == null)continue;
            list.add(newitem);
        }

        return list;

        /*
        1 => 0 | 44
        2 => 45 | 89
         */
    }

    public void additem(Item i, Player p, int cost){
        int id = i.getId();
        int meta = i.getDamage();
        int count = i.getCount();
        String namedtag = i.getNamedTag().toString();
        String sql = "INSERT INTO `auctions` (`id`, `item-id`, `item-meta`, `item-count`, `namedtag`, `cost`, `soldby`, `moneysent`) VALUES (NULL, '"+id+"', '"+meta+"', '"+count+"', '"+namedtag+"', '"+cost+"', '"+p.getName()+"', '0')";
        ExecuteUpdateSQLite(sql);
        //Take Item

        CompoundTag tag = i.getNamedTag();
        if (!i.getCustomName().equals("") && tag.contains("display") && tag.get("display") instanceof CompoundTag)
            tag.getCompound("display").putString("Name2", i.getCustomName());

        if(tag.contains("display") && tag.get("display") instanceof CompoundTag) {
            tag.getCompound("display").putString("soldby", p.getName());
        } else {
            tag.putCompound("display", (new CompoundTag("display")).putString("soldby", p.getName()));
        }

        i.setNamedTag(tag);

        String cn = i.getCustomName();

        cn += TextFormat.RESET + "\n" + TextFormat.AQUA +
                "-------------" + TextFormat.RESET +
                TextFormat.GREEN + "$" + cost + TextFormat.RESET + "\n" +
                TextFormat.GOLD + "Sold By: " + p.getName()
        // + TextFormat.RESET + "\n" +TextFormat.BLACK+"{#"+id;
        ;

        i.setCustomName(cn);

        items.add(i);
    }

    public ResultSet ExecuteQuerySQLite(String s) {
        try {
            Statement stmt = CCM.getMySqlConnection().createStatement();
            ResultSet r = stmt.executeQuery(s);
            return r;
        } catch (Exception ex) {
            CCM.getServer().getLogger().info(ex.getClass().getName() + ":822 " + ex.getMessage());
            return null;
        }
    }

    public void ExecuteUpdateSQLite(String s) {
        try {
            Statement stmt = CCM.getMySqlConnection().createStatement();
            stmt.executeUpdate(s);
        } catch (Exception ex) {
            CCM.getServer().getLogger().info(ex.getClass().getName() + ":2822 " + ex.getMessage());
        }
    }
}
