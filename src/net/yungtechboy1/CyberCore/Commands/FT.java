package net.yungtechboy1.CyberCore.Commands;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.network.protocol.RemoveEntityPacket;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.CyberCoreMain;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by carlt_000 on 1/30/2017.
 */
public class FT extends Command {
    CyberCoreMain Owner;

    public FT(CyberCoreMain server) {
        super("ft", "Create Floating Text", "/ft");
        Owner = server;
        this.commandParameters.clear();
        this.setPermission("CyberTech.CyberCore.op");
    }

    @Override
    public boolean execute(CommandSender s, String label, String[] args) {

        Owner.FTM.AddFloatingText((Player) s);
        s.sendMessage(TextFormat.GREEN+"Floating Text Created!");


        return true;
    }
}