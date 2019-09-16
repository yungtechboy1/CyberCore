package net.yungtechboy1.CyberCore.Commands;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.level.Position;
import cn.nukkit.math.NukkitRandom;
import cn.nukkit.math.Vector3;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.Tasks.ReTPTask;
import org.apache.logging.log4j.core.Core;

import java.util.Random;

/**
 * Created by carlt_000 on 3/21/2016.
 */
public class Wild extends Command {
    CyberCoreMain Owner;

    public Wild(CyberCoreMain server) {
        super("wild", "Teleport to Wild", "/wild");
        Owner = server;
        this.commandParameters.clear();
    }

    @Override
    public boolean execute(CommandSender s, String label, String[] args) {
        if(!(s instanceof Player)){
            s.sendMessage(TextFormat.RED+"Error You Must Be A Player To Use This");
            return true;
        }
        CorePlayer cp = (CorePlayer)s;
        NukkitRandom rand = new NukkitRandom();
        Integer X = rand.nextRange(-(5000),(5000));
        Integer Z = rand.nextRange(-(5000),(5000));
        Position pos = ((Player) s).getLevel().getSafeSpawn(new Vector3(X,50,Z));
        ((Player) s).getLevel().generateChunk(X >> 4,Z >> 4,true);
        s.sendMessage(TextFormat.GREEN+"Teleporting to Wild in 5 Secs!");
        cp.delayTeleport(20*5,pos);
//        Owner.getServer().getScheduler().scheduleDelayedTask(new ReTPTask(Owner,(Player)s,pos),20*5);

        return false;
    }

    public static void runCommand(CommandSender s, CyberCoreMain server){
        //((Player) s).teleport(pos);

    }
}
