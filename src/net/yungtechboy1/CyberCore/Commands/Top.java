package net.yungtechboy1.CyberCore.Commands;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.block.Block;
import cn.nukkit.command.CommandSender;
import cn.nukkit.math.Vector3;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.Main;
import net.yungtechboy1.CyberCore.Msgs;

/**
 * Created by carlt_000 on 3/21/2016.
 */

public class Top {
    Main Owner;
    public Top(Main server){
        Owner = server;
    }

    public static void runCommand(CommandSender s,String[] args, Main server){
        if(s instanceof Player){
            int y;
            for (y = 256; y >= 0; --y) {
                int b = ((Player) s).getLevel().getBlockIdAt(((Player) s).getFloorX(), y, ((Player) s).getFloorZ());
                if (b != Block.AIR && b != Block.LEAVES && b != Block.LEAVES2 && b != Block.SNOW_LAYER)break;
            }
            if(y == 0){
                s.sendMessage(TextFormat.RED+"Error! Could not teleport to top!");
            }else{
                ((Player) s).teleport(new Vector3(((Player) s).getFloorX(),++y,((Player) s).getFloorZ()));
                s.sendMessage(TextFormat.GREEN+"Teleport to top!");
            }
        } else {
            s.sendMessage(Msgs.NEED_TO_BE_PLAYER);
        }
    }
}