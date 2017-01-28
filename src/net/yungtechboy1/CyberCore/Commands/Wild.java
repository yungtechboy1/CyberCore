package net.yungtechboy1.CyberCore.Commands;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.level.Position;
import cn.nukkit.math.Vector3;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.Tasks.ReTPTask;

import java.util.Random;

/**
 * Created by carlt_000 on 3/21/2016.
 */
public class Wild {
    public static void runCommand(CommandSender s, CyberCoreMain server){
        if(!(s instanceof Player)){
            s.sendMessage("Error You Must Be A Player To Use This");
            return;
        }
        Random rand = new Random();
        Integer X = 50000 - rand.nextInt((100000)+1);
        Integer Z = 50000 - rand.nextInt((100000)+1);
        Position pos = ((Player) s).getLevel().getSafeSpawn(new Vector3(X,50,Z));
        ((Player) s).getLevel().generateChunk(X >> 4,Z >> 4,true);
        s.sendMessage(TextFormat.GREEN+"Teleporting to Wild in 5 Secs!");
        server.getServer().getScheduler().scheduleDelayedTask(new ReTPTask(server,(Player)s,pos),20*5);
        //((Player) s).teleport(pos);
    }
}
