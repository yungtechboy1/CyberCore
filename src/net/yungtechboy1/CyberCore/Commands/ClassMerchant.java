package net.yungtechboy1.CyberCore.Commands;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;

public class ClassMerchant extends Command {
    CyberCoreMain Owner;

    public ClassMerchant(CyberCoreMain server) {
        super("classmerchant", "Turn your HUD on or off!", "hud <on / class / fac / pos / off>", new String[]{"cm"});
        Owner = server;
        this.commandParameters.clear();
        this.commandParameters.put("default", new CommandParameter[]{
                new CommandParameter("on / class / fac / pos / off", CommandParamType.RAWTEXT, false)
        });
        this.setPermission("CyberTech.CyberCore.player");
    }

    @Override
    public boolean execute(CommandSender s, String label, String[] args) {
        Owner.Shop.OpenShop((CorePlayer) s,1);
        return true;
    }
}