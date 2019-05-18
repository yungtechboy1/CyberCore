package net.yungtechboy1.CyberCore.Manager.Factions.Cmds;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.form.element.*;
import cn.nukkit.form.window.FormWindowCustom;
import cn.nukkit.form.window.FormWindowModal;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.inventory.PlayerInventory;
import cn.nukkit.utils.TextFormat;

import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.FormType;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;
import net.yungtechboy1.CyberCore.Manager.Form.Windows.FactionCreate0;
import org.apache.logging.log4j.core.Core;

import java.util.ArrayList;

/**
 * Created by carlt_000 on 7/9/2016.
 */
public class Create extends Commands {

    private ArrayList<String> bannednames = new ArrayList<String>() {{
        add("wilderness");
        add("safezone");
        add("peace");
    }};

//    public Create(FactionsMain main, String name, String description, String usageMessage, String[] aliases) {
//        super(main, name, description, usageMessage, aliases);
//    }
//

    public Create(CorePlayer s, String[] a, FactionsMain m) {
        super(s, a, "/f create", m);
        senderMustBePlayer = true;
        sendUsageOnFail = true;
        Sender = s;
        if (run()) {
            RunCommand();
        }
    }

    @Override
    public void RunCommand() {
        FormWindowCustom FWM = new FormWindowCustom("CyberFactions | Create Faction (1/2)");
//        Element e = null;
        FWM.addElement(new ElementInput("Desired Faction Name"));
        FWM.addElement(new ElementInput("MOTD", "A CyberTech Faction"));
        FWM.addElement(new ElementLabel("Enabeling Faction Privacy will require a player to have an invite to join your faction."));
        FWM.addElement(new ElementToggle("Faction Privacy", false));

        CorePlayer cp = (CorePlayer) CyberCoreMain.getInstance().getServer().getPlayerExact(Sender.getName());
        cp.showFormWindow(new FactionCreate0());
//        cp.setNewWindow();


//        if(Args.length <= 1){
//            Sender.sendMessage(FactionsMain.NAME+TextFormat.GRAY+"Usage /f create <name>");
//            return;
//        }
//        if(!Args[1].matches("^[a-zA-Z0-9]*")) {
//            Sender.sendMessage(FactionsMain.NAME+TextFormat.RED+"You may only use letters and numbers!");
//            return;
//        }
//        if(bannednames.contains(Args[1].toLowerCase())){
//            Sender.sendMessage(FactionsMain.NAME+TextFormat.RED+"That is a Banned faction Name!");
//            return;
//        }
//        if(Main.factionExists(Args[1])) {
//            Sender.sendMessage(FactionsMain.NAME+TextFormat.RED+"Faction already exists");
//            return;
//        }
//        if(Args[1].length() > 20) {
//            Sender.sendMessage(FactionsMain.NAME+TextFormat.RED+"Faction name is too long. Please try again!");
//            return;
//        }
//        if(Main.isInFaction(Sender.getName())) {
//            Sender.sendMessage(FactionsMain.NAME+TextFormat.RED+"You must leave your faction first");
//            return;
//        } else {
//            Main.FFactory.CreateFaction(Args[1],(Player) Sender);
//            Sender.sendMessage(FactionsMain.NAME+TextFormat.GRAY+"Your Faction has 2 power!");
////            Main.CC.Setnametag((Player) Sender);
////            Main.sendBossBar((Player) Sender);
//            return;
//        }
    }
}
