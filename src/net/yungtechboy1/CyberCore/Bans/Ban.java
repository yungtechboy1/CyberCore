package net.yungtechboy1.CyberCore.Bans;

import cn.nukkit.Player;
import cn.nukkit.event.player.PlayerLoginEvent;
import cn.nukkit.event.player.PlayerPreLoginEvent;
import cn.nukkit.utils.ConfigSection;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.CyberCoreMain;

/**
 * Created by carlt_000 on 1/21/2017.
 */
public class Ban {
    public String name = null;
    String ip = null;
    long randomclientid = -1;
    String UniqueId = null;

    long tempban = -1;

    boolean IpBan = false;
    boolean ClientBan = true;
    boolean NameBan = true;

    public Ban(CyberCoreMain server, String name, String reason) {
        this(server, name, reason, -1);
    }

    public Ban(CyberCoreMain server, String name, String reason, long date) {
        this(server, name, reason, date, true);
    }

    public Ban(CyberCoreMain server, String name, String reason, long date, boolean nameban) {
        this(server, name, reason, date, nameban, false);
    }

    public Ban(CyberCoreMain server, String name, String reason, long date, boolean nameban, boolean ipban) {
        this(server, name, reason, date, nameban, ipban, true);
    }

    public Ban(CyberCoreMain server, String name, String reason, long date, boolean nameban, boolean ipban, boolean clientban) {
        this.name = name;
        Player p = server.getServer().getPlayerExact(name);
        if (p != null) {
            ip = p.getAddress();
            randomclientid = p.getClientId();
            UniqueId = p.getUniqueId().toString();
        }
        IpBan = ipban;
        ClientBan = clientban;
        NameBan = nameban;
        server.bans.add(this);
    }

    public Ban(CyberCoreMain server, Player p, String reason) {
        this(server, p, reason, -1);
    }

    /**
     *
     * @param server
     * @param p
     * @param reason
     * @param date
     */
    public Ban(CyberCoreMain server, Player p, String reason, long date) {
        this(server, p, reason, date, true);
    }

    /**
     *
     * @param server
     * @param p
     * @param reason
     * @param date
     * @param nameban
     */
    public Ban(CyberCoreMain server, Player p, String reason, long date, boolean nameban) {
        this(server, p, reason, date, nameban, false);
    }

    /**
     *
     * @param server
     * @param p
     * @param reason
     * @param date
     * @param nameban
     * @param ipban
     */
    public Ban(CyberCoreMain server, Player p, String reason, long date, boolean nameban, boolean ipban) {
        this(server, p, reason, date, nameban, ipban, true);
    }

    /**
     *
     * @param server
     * @param p
     * @param reason
     * @param date Long Val
     * @param nameban Ban Name
     * @param ipban Ban IP
     * @param clientban Ban Client
     */
    public Ban(CyberCoreMain server, Player p, String reason, long date, boolean nameban, boolean ipban, boolean clientban) {
        this.name = name;
        ip = p.getAddress();
        randomclientid = p.getClientId();
        UniqueId = p.getUniqueId().toString();

        IpBan = ipban;
        ClientBan = clientban;
        NameBan = nameban;
        server.bans.add(this);
    }


    public ConfigSection toconfig() {
        return new ConfigSection() {{
            put("name", name);
            put("ip", ip);
            put("randomclientid", randomclientid);
            put("UniqueId", UniqueId);
            put("IpBan", IpBan);
            put("ClientBan", ClientBan);
            put("NameBan", NameBan);
        }};
    }

    public Ban(ConfigSection configSection){
        name = configSection.getString("name");
        ip = configSection.getString("name");
        randomclientid = configSection.getLong("randomclientid");
        UniqueId = configSection.getString("UniqueId");
        IpBan = configSection.getBoolean("IpBan");
        ClientBan = configSection.getBoolean("ClientBan");
        NameBan = configSection.getBoolean("NameBan");
    }

    /**
     * 0 - Allowed
     * 1 - IP Ban
     * 2 - Client Ban
     * 3 - Name ban
     * @param player
     * @return
     */
    public boolean checkbanned(Player player, PlayerLoginEvent event){
        if(IpBan && ip.equalsIgnoreCase(player.getAddress())){
            event.setKickMessage(TextFormat.RED+"Your Banned! E-1");
            event.setCancelled();
            return true;
        }
        if(ClientBan && randomclientid == player.getClientId() && UniqueId.equals(player.getUniqueId().toString())){
            event.setKickMessage(TextFormat.RED+"Your Banned! E-2");
            event.setCancelled();
            return true;
        }
        if(NameBan && name.equalsIgnoreCase(player.getName())){
            event.setKickMessage(TextFormat.RED+"Your Banned! E-3");
            event.setCancelled();
            return true;
        }
        return false;
    }
}
