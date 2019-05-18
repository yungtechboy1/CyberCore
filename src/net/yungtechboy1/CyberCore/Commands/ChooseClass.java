package net.yungtechboy1.CyberCore.Commands;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.item.Item;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.Manager.Form.Windows.Class0Window;
import net.yungtechboy1.CyberCore.Manager.Form.Windows.Class1Window;

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
        if (sender instanceof Player) {
            Player p = (Player) sender;
            CorePlayer cp = Owner.getCorePlayer(p);

            if (cp.getInventory().getItemInHand().getId() == Item.MONSTER_SPAWNER) {
                Server.getInstance().broadcastMessage(cp.getInventory().getItemInHand().getNamedTag().toString() + "");
                Server.getInstance().broadcastMessage("Level: " + cp.getInventory().getItemInHand().getNamedTag().getInt("Level") + "");
                return true;
            }


            p.showFormWindow(new Class0Window());
        }
        return true;
    }

}


