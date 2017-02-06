package net.yungtechboy1.CyberCore.Commands;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import cn.nukkit.math.Vector3;
import cn.nukkit.utils.ConfigSection;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.Messages;
import net.yungtechboy1.CyberCore.RankList;
import net.yungtechboy1.CyberCore.Tasks.ReTPTask;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by carlt_000 on 2/1/2017.
 */
public class Warp extends Command {
    private String Prefix = TextFormat.AQUA + "[TerraTP]";
    CyberCoreMain Owner;

    public Warp(CyberCoreMain server) {
        super("warp", "Warps you to a warp!", "/warp [key]");
        Owner = server;
        this.commandParameters.clear();
        this.commandParameters.put("default", new CommandParameter[]{
                new CommandParameter("key", CommandParameter.ARG_TYPE_STRING, true)
        });
        this.commandParameters.put("2nd", new CommandParameter[]{});
    }

    @Override
    public boolean execute(CommandSender s, String label, String[] args) {
        Player p = (Player) s;
        if (args.length != 1 || p == null){
            /*
            ConfigSection list = Owner.MainConfig.getSections("warp");

            ArrayList<String> ll = new ArrayList<>();
            for(String a: list.keySet()){
                ll.add()
            }*/
            //FUck warp Listing
            return true;
        }
        args[0] = args[0].toLowerCase();
        if (Owner.MainConfig.exists("warp." + args[0])) {
            try {
                String[] v = ((String)  Owner.MainConfig.get("warp." + args[0])).split("&");
                Level l = Owner.getServer().getLevelByName(v[3]);
                if (l == null) return true;
                Position pos = new Position(Double.parseDouble(v[0]), Double.parseDouble(v[1]), Double.parseDouble(v[2]), l);
                p.sendMessage(Prefix + " Teleported to warp in 5 Secs!");
                Owner.getServer().getScheduler().scheduleDelayedTask(new ReTPTask(Owner,(Player)s,pos),20*5);
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }
}