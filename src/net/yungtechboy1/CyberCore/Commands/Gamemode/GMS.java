package net.yungtechboy1.CyberCore.Commands.Gamemode;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.CyberCoreMain;

/**
 * Created by carlt_000 on 3/21/2016.
 */
public class GMS {
    public static void runCommand(CommandSender s,String[] args, CyberCoreMain server){
        if(!(s instanceof Player)){
            s.sendMessage(TextFormat.RED+"Must be player to use /GMS");
            return;
        }
        ((Player) s).setGamemode(Player.SURVIVAL);
        s.sendMessage(TextFormat.GREEN+"Gamemode Changed to Survival!");
    }
}
