package net.yungtechboy1.CyberCore.Manager.Factions.Cmds;

import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;

import net.yungtechboy1.CyberCore.Manager.Factions.Faction;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;

/**
 * Created by carlt_000 on 7/9/2016.
 */
public class Join extends Commands {

    public Join(CommandSender s, String[] a, FactionsMain m){
        super(s,a,"/f join <faction>",m);
        senderMustBePlayer = true;
        senderMustBeMember = true;
        sendUsageOnFail = true;

        if(run()){
            RunCommand();
        }
    }

    @Override
    //@TODO
    public void RunCommand(){
        if(Args.length <= 1){
            Sender.sendMessage(FactionsMain.NAME+TextFormat.GRAY+"Usage /f join <faction>");
            return;
        }
        String fn = Main.FFactory.factionPartialName(Args[1]);
        Faction f = Main.FFactory.getFaction(fn);
        if(f == null){
            Sender.sendMessage(FactionsMain.NAME+TextFormat.RED+"Faction not Found!");
            return;
        }
        if(f.GetPrivacy() == 1){
            Sender.sendMessage(FactionsMain.NAME+TextFormat.RED+"Error! You can not join a private Faction!");
            return;
        }
        f.AddRecruit(Sender.getName().toLowerCase());
        Main.FFactory.FacList.put(Sender.getName().toLowerCase(), fn);

        Sender.sendMessage(FactionsMain.NAME+TextFormat.RED+"Congrads! You Just Joined "+fac.GetDisplayName()+" Faction!");
        f.BroadcastMessage(FactionsMain.NAME+TextFormat.GREEN+Sender.getName()+" Has joinded your faction!");
//        if(Main.CC != null)Main.CC.Setnametag((Player)Sender);
    }
}
