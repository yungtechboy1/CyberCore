package net.yungtechboy1.CyberCore.Commands;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.item.Item;
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
if(p.GetPlayerClass() == null) {
    p.showFormWindow(new Class0Window());
}else{
    p.showFormWindow(p.GetPlayerClass().getHowToUseClassWindow());
}
        return true;
    }

}


