package net.yungtechboy1.CyberCore.Commands;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.Messages;
import net.yungtechboy1.CyberCore.Rank.RankList;
import net.yungtechboy1.CyberCore.Tasks.TeleportEvent;

/**
 * Created by carlt_000 on 3/21/2016.
 */

public class Spawn extends Command {
    CyberCoreMain Owner;

    public Spawn(CyberCoreMain server) {
        super("spawn", "Teleport to spawn", "/spawn [player]");
        Owner = server;
        this.commandParameters.clear();
        this.commandParameters.put("default", new CommandParameter[]{
                new CommandParameter("player",  CommandParamType.TARGET, true)
        });
    }

    @Override
    public boolean execute(CommandSender s, String label, String[] args) {
        runCommand(s,args,Owner);
        return true;
    }

    public static void runCommand(CommandSender s,String[] args, CyberCoreMain server){
        if(s instanceof CorePlayer){
            CorePlayer p = (CorePlayer)s;
            int r = p.kills;
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
                if(r > RankList.PERM_ADMIN_1){
                    p.teleport(p.getLevel().getSafeSpawn());
                    s.sendMessage(TextFormat.GREEN + "Teleporting you!");
                }else {
                    p.StartTeleport(((CorePlayer) s).getLevel().getSafeSpawn(),5);
                    s.sendMessage(TextFormat.GREEN + "Teleporting you to spawn in 5 Secs!");
                }
            }
        }
    }
}