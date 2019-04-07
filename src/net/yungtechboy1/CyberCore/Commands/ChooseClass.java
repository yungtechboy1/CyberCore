package net.yungtechboy1.CyberCore.Commands;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindowModal;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.item.Item;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.FormType;

import java.util.ArrayList;

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

            FormWindowModal FWM = new FormWindowModal("Welcome to the Class Picker!",
                    "Please Choose a class, but please not that after choosing a class you can not change it!\n" +
                            "Well.. There is one way but its not cheep or easy!\n" +
                            "I know one of the bosses drops a potion that can allow you to change!\n" +
                            "But that was only rumors! Good Luck Choose wise!"
                    , "Go Back", "Learn More >");
            p.showFormWindow(FWM);
            cp.LastSentFormType = FormType.MainForm.Class_0;
            cp.setNewWindow(new FormWindowSimple("Choose your Class Catagory!", "Visit Cybertechpp.com for more info on classes!",
                    new ArrayList<ElementButton>() {{
                        add(new ElementButton("Offense"));
                        add(new ElementButton("Defense"));
                        add(new ElementButton("Minning"));
                        add(new ElementButton("Intelligence"));
                        add(new ElementButton("TEMP-GIVE SPAWNER"));
                    }}));
        }
        return true;
    }
}
