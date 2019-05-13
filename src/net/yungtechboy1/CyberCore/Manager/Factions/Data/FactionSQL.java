package net.yungtechboy1.CyberCore.Manager.Factions.Data;

import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.Data.CoreSQL;
import net.yungtechboy1.CyberCore.Data.MySQL;
import net.yungtechboy1.CyberCore.Data.SQLite;
import ru.nukkit.dblib.DbLib;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FactionSQL extends MySQL {


    public FactionSQL(CyberCoreMain plugin) {

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




}
