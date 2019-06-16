package net.yungtechboy1.CyberCore.Commands;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.Manager.Form.Windows.Class0Window;

/**
 * Created by carlt on 3/22/2019.
 */
public class ChooseClass extends Command {
    CyberCoreMain Owner;

    public ChooseClass(CyberCoreMain server) {
        super("class", "Choose Class", "/class");
        Owner = server;
        this.commandParameters.clear();
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {

        CorePlayer p = (CorePlayer) sender;
if(p.getPlayerClass() == null) {
    p.showFormWindow(new Class0Window());
}else{
    p.showFormWindow(p.getPlayerClass().GetSettingsWindow());
}
        return true;
    }

}


