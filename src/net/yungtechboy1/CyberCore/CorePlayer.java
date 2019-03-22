package net.yungtechboy1.CyberCore;

import cn.nukkit.Player;
import cn.nukkit.form.window.FormWindow;
import cn.nukkit.form.window.FormWindowModal;
import cn.nukkit.network.SourceInterface;
import net.yungtechboy1.CyberCore.Rank.Rank;
import net.yungtechboy1.CyberCore.Rank.RankList;

import java.util.HashMap;

public class CorePlayer extends Player {



    private FormWindow nw;
    public Integer kills = 0;
    public Integer deaths = 0;
    public HashMap<String, Object> extraData = new HashMap<>();
    private Rank R = RankList.PERM_GUEST.getRank();

    public CorePlayer(SourceInterface interfaz, Long clientID, String ip, int port) {
        super(interfaz, clientID, ip, port);
    }

    public Integer addDeath() {
        return deaths += 1;
    }

    public Integer addDeath(Integer amount) {
        return deaths += amount;
    }

    public Integer addKill() {
        return kills += 1;
    }

    public Integer addKills(Integer amount) {
        return kills += amount;
    }

    public double calculateKD() {
        return kills / deaths;
    }
    public boolean hasNewWindow() {
        return nw != null;
    }
    public FormWindow getNewWindow() {
        return nw;
    }

    public void setNewWindow(FormWindow nw) {
        this.nw = nw;
    }
    public void clearNewWindow(FormWindow nw) {
        this.nw = null;
    }
}
