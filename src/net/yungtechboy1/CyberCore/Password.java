package net.yungtechboy1.CyberCore;

import cn.nukkit.Player;
import cn.nukkit.potion.Effect;
import cn.nukkit.utils.ConfigSection;
import cn.nukkit.utils.TextFormat;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.ResultSet;
import java.util.Calendar;
import java.util.UUID;

/**
 * Created by carlt_000 on 1/6/2017.
 */
public class Password {
    private String Player = null;
    private String Hash = null;
    private Long CID = null;
    private String UnqiqueID = null;
    private String Ipaddress = null;
    private Long LastLogin = 0L;
    private Long Registered = 0L;
    private String Email = "";
    private Boolean Loggedin = false;

    public Password(String player) {
        Player = player.toLowerCase();
    }

    public String getHash() {
        return Hash;
    }

    public void setHash(String hash) {
        Registered = (Calendar.getInstance().getTime().getTime() / 1000);
        LastLogin = (Calendar.getInstance().getTime().getTime() / 1000);
        Hash = hash;
    }

    public String getPlayer() {
        return Player;
    }

    public void setPlayer(String player) {
        Player = player;
    }

    public Long getCID() {
        if (CID == null) return 0L;
        return CID;
    }

    public void setCID(Long cid) {
        CID = cid;
    }

    public void setUnqiqueID(UUID id) {
        UnqiqueID = id.toString();
    }

    public String getUnqiqueID() {
        return UnqiqueID;
    }

    public void setUnqiqueID(String unqiqueID) {
        UnqiqueID = unqiqueID;
    }

    public String getIpaddress() {
        return Ipaddress;
    }

    public void setIpaddress(String ip) {
        Ipaddress = ip;
    }

    public void SetLoggedin(Boolean val) {
        Loggedin = val;
    }

    public Boolean getLoggedin() {
        return Loggedin;
    }

    public void setLoggedin(Boolean loggedin) {
        Loggedin = loggedin;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public Long getRegistered() {
        return Registered;
    }

    public void setRegistered(Long registered) {
        Registered = registered;
    }

    public Long getLastLogin() {
        return LastLogin;
    }

    public void setLastLogin(Long lastLogin) {
        LastLogin = lastLogin;
    }

    public boolean IsValid() {
        if (Player != null && Hash != null && !Hash.equalsIgnoreCase("null")) return true;
        return false;
    }

    public void HashPassword(String pw) {
        // Hash a password for the first time
        Hash = BCrypt.hashpw(pw, BCrypt.gensalt(12));
    }

    public boolean CheckPassoword(String pw) {
        if (Hash == null || Hash.equalsIgnoreCase("null")) return false;
        if (BCrypt.checkpw(pw, Hash)) return true;
        return false;
    }

    public boolean CheckUUID(Long uuid) {
        if (uuid.equals(CID)) return true;
        return false;
    }

    public boolean CheckUUID(String uuid) {
        if (uuid.equalsIgnoreCase(UnqiqueID)) return true;
        return false;
    }

    public boolean CheckIP(String id) {
        if (id.equalsIgnoreCase(Ipaddress)) return true;
        return false;
    }

    public boolean IsRegistered() {
        if (Hash != null && !Hash.equalsIgnoreCase("null")) return true;
        return false;
    }

    public boolean RegisterPass(Player p, String pass) {
        if (IsRegistered()) return false;
        HashPassword(pass);
        CID = p.getClientId();
        UnqiqueID = p.getUniqueId().toString();
        Ipaddress = p.getAddress();
        Loggedin = true;
        p.removeEffect(Effect.BLINDNESS);
        return true;
    }

    public void CheckAutoLogin(Player player) {
        if (getHash() == null || getHash().equalsIgnoreCase("null")) return;
        if (CheckIP(player.getAddress()) && (CheckUUID(player.getUniqueId().toString()) || CheckUUID(player.getClientId()))) {
            player.sendMessage(TextFormat.GREEN + "You are now Logged in!");
            Loggedin = true;
            LastLogin = (Calendar.getInstance().getTime().getTime() / 1000);

            player.removeEffect(Effect.BLINDNESS);
        }
    }

    public boolean Login(Player player, String pw) {
        if (Loggedin) {
            player.sendMessage(TextFormat.YELLOW + "Already Logged in!");
            return true;
        }
        if (!IsRegistered()) {
            player.sendMessage(TextFormat.RED + "You must first `/register`!");
            return false;
        }
        if (!CheckPassoword(pw)) {
            player.sendMessage(TextFormat.RED + "Invalid Password!");
            return false;
        }
        LastLogin = (Calendar.getInstance().getTime().getTime() / 1000);
        UnqiqueID = player.getUniqueId().toString();
        CID = player.getClientId();
        Ipaddress = player.getAddress();
        Loggedin = true;
        return true;
    }

    public ConfigSection tohash() {
        ConfigSection cs = new ConfigSection();
        cs.put("Player", Player);
        cs.put("Hash", Hash);
        cs.put("CID", CID);
        cs.put("UnqiqueID", UnqiqueID);
        cs.put("Ipaddress", Ipaddress);
        return cs;
    }
}
