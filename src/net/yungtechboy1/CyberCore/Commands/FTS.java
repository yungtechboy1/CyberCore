package net.yungtechboy1.CyberCore.Commands;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.CyberCoreMain;

/**
 * Created by carlt_000 on 1/30/2017.
 */
public class FTS extends Command {
    CyberCoreMain Owner;

    public FTS(CyberCoreMain server) {
        super("fts", "Save Floating Text", "/ft");
        Owner = server;
        this.commandParameters.clear();
        this.setPermission("CyberTech.CyberCore.op");
    }

    @Override
    public boolean execute(CommandSender s, String label, String[] args) {
        //TODO
        s.sendMessage(TextFormat.GREEN+"Config Saved!");
        return true;
    }
}