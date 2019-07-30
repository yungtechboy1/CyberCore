package net.yungtechboy1.CyberCore.Commands;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.Manager.Form.Windows.FactionAdminPage1;
import net.yungtechboy1.CyberCore.Manager.Form.Windows.SettingsPage0;

public class Settings  extends Command {
    CyberCoreMain Owner;

    public Settings(CyberCoreMain server) {
        super("settings", "Manage your server InternalPlayerSettings", "/settings");
        Owner = server;
        this.commandParameters.clear();
        this.commandParameters.put("default", new CommandParameter[]{});
        this.setPermission("CyberTech.CyberCore.player");
    }

    @Override
    public boolean execute(CommandSender s, String label, String[] args) {
        try {
            ((CorePlayer)s).showFormWindow(new SettingsPage0());
        }catch (Exception e){
            CyberCoreMain.getInstance().getLogger().error("EEE!213111 >>",e);
        }
        return true;
    }
}