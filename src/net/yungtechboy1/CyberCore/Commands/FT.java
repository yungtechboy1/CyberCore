package net.yungtechboy1.CyberCore.Commands;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.Manager.FT.CyberFloatingTextContainer;
import net.yungtechboy1.CyberCore.Manager.FT.FloatingTextFactory;

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

        //TODO
        FloatingTextFactory.AddFloatingText(new CyberFloatingTextContainer(Owner.FTM,(CorePlayer)s,"TEMP TEXT!~"));
        s.sendMessage(TextFormat.GREEN+"Floating Text Created!");


        return true;
    }
}