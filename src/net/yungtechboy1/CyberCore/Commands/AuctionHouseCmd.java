package net.yungtechboy1.CyberCore.Commands;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;

/**
 * Created by carlt_000 on 2/26/2017.
 */
public class AuctionHouseCmd extends Command {
    CyberCoreMain Owner;

    public AuctionHouseCmd(CyberCoreMain server) {
        super("auctionhouse", "Open auction", "/AuctionHouseCmd", new String[]{"ah"});
        Owner = server;
        this.commandParameters.clear();
        this.setPermission("CyberTech.CyberCore.player");
    }

    @Override
    public boolean execute(CommandSender s, String label, String[] args) {
        Owner.AF.OpenAH((CorePlayer) s,1);
        return true;
    }
}