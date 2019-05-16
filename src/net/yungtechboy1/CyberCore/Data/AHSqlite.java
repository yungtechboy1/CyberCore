package net.yungtechboy1.CyberCore.Data;

import cn.nukkit.item.Item;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import ru.nukkit.dblib.DbLib;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by carlt on 5/1/2019.
 */
public class AHSqlite extends MySQL {


    public AHSqlite(CyberCoreMain plugin) {
        super(plugin);
        LoadAllItems();
    }


    @Override
    public Connection connectToDb() {
        String host = plugin.MainConfig.getSection("db2").getString("mysql-host");
        String pass = plugin.MainConfig.getSection("db2").getString("mysql-pass");
        int port = plugin.MainConfig.getSection("db2").getInt("mysql-port");
        String user = plugin.MainConfig.getSection("db2").getString("mysql-user");
        String db = plugin.MainConfig.getSection("db2").getString("mysql-db-Server");
        if (!enabled) return null;
        Connection connection = DbLib.getMySqlConnection(host, port,
                db, user, pass);

        if (connection == null) enabled = false;
        return connection;
    }

    public void LoadAllItems() {
        try {
            ArrayList<HashMap<String, Object>> data = executeSelect("SELECT * FROM `AuctionHouse`");
            if (data == null) {
                CyberCoreMain.getInstance().getLogger().error("Error Loading Auctions from Sqlite!");
                return;
            } else {
                plugin.getLogger().info("Loading " + data.size() + " Auction Items!");
            }

            for (HashMap<String, Object> v : data) {
                AuctionItemData a = new AuctionItemData(v);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public ArrayList<HashMap<String, Object>> executeSelect(String query) throws SQLException {
        ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
        Connection connection = connectToDb();
        if (connection == null) return null;
        ResultSet resultSet = connection.createStatement().executeQuery(query);
        if (resultSet == null) return null;
        while (resultSet.next()) {
            HashMap<String, Object> map = new HashMap<>();
            ResultSet rs = resultSet;
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                map.put(metaData.getColumnName(i), resultSet.getObject(i));
            }
            data.add(map);
        }
        if (connection != null) connection.close();
        return data.isEmpty() ? null : data;
    }

    public AuctionItemData Save(AuctionItemData data) {
        try {
            if (data.masterid != -1)
                executeUpdate("DELETE FROM `AuctionHouse` WHERE `master_id` == '" + data.masterid + "'");
            String fnt = "";
            if (data.item.hasCompoundTag()) fnt = new String(data.item.writeCompoundTag(data.item.getNamedTag()));
            executeUpdate(
                    "INSERT INTO `AuctionHouse` VALUES (null," +
                            data.item.getId() + "," + data.item.getDamage() + "," + data.item.getCount() + ",?," + data.Cost + ",'" + data.Soldby + "','" + data.Soldbyn + "',false)", data.item.getNamedTag());

            plugin.getLogger().info("AH saved for " + data.toString());
            ExecuteQuerySQLite("SELECT * FROM `AuctionHouse` ");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public ResultSet ExecuteQuerySQLite(String sql) throws SQLException {
        Connection connection = connectToDb();
        if (connection == null) return null;
        ResultSet resultSet = connection.createStatement().executeQuery(sql);
        if (resultSet == null) return null;
        return resultSet;
    }


    public Item GetItemfromDB(int idd) {
        try {
            if (idd == 0) return null;
            ResultSet rs = ExecuteQuerySQLite("SELECT * FROM `AuctionHouse` WHERE `master_id` = '" + idd + "'");
            if (rs != null) {
                try {
                    if (rs.next()) {
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
                        if (tag == null) tag = new CompoundTag();

                        if (!i.getCustomName().equals("") && tag.contains("display") && tag.get("display") instanceof CompoundTag)
                            tag.getCompound("display").putString("Name2", i.getCustomName());

                        if (tag.contains("display") && tag.get("display") instanceof CompoundTag) {
                            tag.getCompound("display").putInt("keyid", id);
                            tag.getCompound("display").putInt("cost", cost);
                            tag.getCompound("display").putString("soldby", soldby);
                        } else {
                            tag.putCompound("display", (new CompoundTag("display")).putInt("keyid", id).putInt("cost", cost).putString("soldby", soldby));
                        }
                        i.setNamedTag(tag);


                        String cn = i.getCustomName();

                        if (cn.equalsIgnoreCase("")) cn = i.getName();

                        cn += TextFormat.RESET + "\n" + TextFormat.AQUA +
                                "-------------" + TextFormat.RESET + "\n" +
                                TextFormat.GREEN + "$" + cost + TextFormat.RESET + "\n" +
                                TextFormat.GOLD + "Sold By: " + soldby
                        // + TextFormat.RESET + "\n" +TextFormat.BLACK+"{#"+id;
                        ;

                        i.setCustomName(cn);

                        return i;
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    CyberCoreMain.getInstance().getLogger().info("ERror loading Items2!");
                    return null;
                }
            }
            return null;
        } catch (Exception e) {
            return null;
        }


    }


    private void SaveAll(CorePlayer p) {
        try {
            executeUpdate("DELETE FROM `Homes` WHERE `owneruuid` LIKE '" + p.getUniqueId() + "'");
            for (HomeData h : p.HD) {
                executeUpdate("INSERT INTO `Homes` VALUES (0,'" + h.getName() + "'," + h.getX() + "," + h.getY() + "," + h.getZ() + ",'" + h.getLevel() + "','" + h.getOwner() + "','" + h.getOwneruuid() + "')");
            }
            plugin.getLogger().info("Homes saved for " + p.getName());
            p.sendTip("Homes Saved!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void AddItemForSale(AuctionItemData aid) {
        Save(aid);
    }
}
