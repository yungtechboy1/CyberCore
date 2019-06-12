package net.yungtechboy1.CyberCore.Data;

import cn.nukkit.Player;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;

import java.lang.reflect.Type;
import java.sql.Connection;

import org.apache.logging.log4j.core.Core;
import org.sql2o.Sql2o;
import ru.nukkit.dblib.DbLib;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class UserSQL extends MySQL{



    /**
     * Connects to the MYSQL database assigned in config.yml for GLOBAL data
     * @return Connection
     */

    public String table = "mcpe";
    public String file = "cyber_core_data.db";
    public String createSQL = "create table if not exists " + table + " (";
    public String insertQuery = "insert into " + table + " (";
    public String addQuery = "insert into " + table + " (uuid) values (:uuid)";
    public String loadQuery = "select * from " + table + " where uuid=':uuid'";
    public String saveQuery = "update " + table + " set ";
    public HashMap<String, String> columns;

    public UserSQL(CyberCoreMain plugin) {
        super(plugin);
    }

    @Override
    public Connection connectToDb() {
        String host = plugin.MainConfig.getSection("db2").getString("mysql-host");
        String pass = plugin.MainConfig.getSection("db2").getString("mysql-pass");
        int port = plugin.MainConfig.getSection("db2").getInt("mysql-port");
        String user = plugin.MainConfig.getSection("db2").getString("mysql-user");
        String db = plugin.MainConfig.getSection("db2").getString("mysql-db-Faction");
        if (!enabled) return null;
        Connection connection = DbLib.getMySqlConnection(host, port,
                db, user, pass);

        if (connection == null) enabled = false;
        return connection;
    }



    public void createUser(String uuid) throws SQLException {
        executeUpdate(addQuery.replace(":uuid", "'"+uuid+"'"));
    }

    public void saveUser(CorePlayer player) {
        try {
            if(player != null) {
                String query = saveQuery;
                for(String data:columns.keySet()) {
                    if(data.equalsIgnoreCase("uuid")) {
                        query = query.replace(":uuid", "'"+player.getUniqueId().toString()+"'");
                    }else {
                        if(player.getClass().getField(data).getType() == String.class) {
                            query = query.replace(":" + data, "'"+player.getClass().getField(data).get(player).toString()+"'");
                        } else {
                            query = query.replace(":" + data, player.getClass().getField(data).get(player).toString());
                        }
                    }
                }
                plugin.log(query);
                executeUpdate(query);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

//    public void loadUser(String uuid) {
//        try {
//            List<HashMap<String,Object>> data = executeSelect(loadQuery, "uuid", uuid, columns.keySet());
//            CorePlayer p = _plugin.getCorePlayer(uuid);
//            for(String sel: columns.keySet()) {
//                if(!sel.equalsIgnoreCase("uuid")) {
//                    p.getClass().getField(sel).set(p, data.get(0).get(sel));
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        }
//    }
}
