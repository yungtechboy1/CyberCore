package net.yungtechboy1.CyberCore.Commands;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.Messages;
import net.yungtechboy1.CyberCore.RankList;

/**
 * Created by carlt_000 on 3/21/2016.
 */

public class Spawn extends Command {
    CyberCoreMain Owner;

    public Spawn(CyberCoreMain server) {
        super("spawn", "Teleport to spawn", "/spawn");
        Owner = server;
        this.commandParameters.clear();
        this.commandParameters.put("default", new CommandParameter[]{
                new CommandParameter("message", CommandParameter.ARG_TYPE_RAW_TEXT, false)
        });
    }

    @Override
    public boolean execute(CommandSender s, String label, String[] args) {
        runCommand(s,args,Owner);
        return true;
    }

    public static void runCommand(CommandSender s,String[] args, CyberCoreMain server){
        if(s instanceof Player){
            Player p = (Player)s;
            int r = server.GetPlayerRankInt(p,true);
            if(args.length == 1 && r > RankList.PERM_ADMIN_1){
                Player t = server.getServer().getPlayer(args[0]);
                if(t == null){
                    s.sendMessage(TextFormat.RED+"Error! Target Player Not Found!");
                    return;
                }
                t.teleport(t.getLevel().getSafeSpawn());
                t.sendMessage(TextFormat.YELLOW+" Your at spawn!");
                s.sendMessage(TextFormat.GREEN+t.getName()+" Teleported to spawn!");
            }else{
                p.teleport(p.getLevel().getSafeSpawn());
                s.sendMessage(TextFormat.GREEN+"Your at spawn!");
            }
        } else {
            if(args.length == 1){
                Player t = server.getServer().getPlayer(args[0]);
                if(t == null){
                    s.sendMessage(TextFormat.RED+"Error! Target Player Not Found!");
                    return;
                }
                t.teleport(t.getLevel().getSafeSpawn());
                t.sendMessage(TextFormat.YELLOW+" Your at spawn!");
                s.sendMessage(TextFormat.GREEN+t.getName()+" Teleported to spawn!");
                return;
            }
            s.sendMessage(Messages.NEED_TO_BE_PLAYER);
        }
    }
}