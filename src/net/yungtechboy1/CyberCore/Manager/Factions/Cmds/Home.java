package net.yungtechboy1.CyberCore.Manager.Factions.Cmds;

import cn.nukkit.Player;
import cn.nukkit.math.Vector3;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.Manager.Factions.Cmds.Base.Commands;
import net.yungtechboy1.CyberCore.Manager.Factions.Faction;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;

/**
 * Created by carlt_000 on 7/9/2016.
 */
public class Home extends Commands {


    public Home(CorePlayer s, String[] a, FactionsMain m) {
        super(s, a, "/f Home [faction]", m);
        senderMustBeInFaction = true;
        senderMustBePlayer = true;
        sendFailReason = true;
        sendUsageOnFail = true;

        if (run()) {
            RunCommand();
        }
    }

    @Override
    public void RunCommand() {
        if(Args.length == 2){
            Faction ofaction;
//            = Main.FFactory.getFaction(Main.FFactory.factionPartialName(Args[1]));
            ofaction = fac.GetAllyFromName(Args[1]);
            if(ofaction == null){
                Sender.sendMessage(FactionsMain.NAME+TextFormat.RED+"No Faction Found By That Name!");
                return;
            }
            if(!fac.isAllied(ofaction) || !ofaction.isAllied(fac)){
                fac.RemoveAlly(ofaction);
                ofaction.RemoveAlly(fac);
                Sender.sendMessage(FactionsMain.NAME+TextFormat.RED+"You Are Not Allied With that Faction!!!");
                return;
            }
            Vector3 home = ofaction.GetHome();
            if(home.y == 0 && home.x == 0 && home.z == 0){
                Sender.sendMessage(FactionsMain.NAME+TextFormat.GOLD+""+ofaction+"'s does not have a Home is not set!");
                return;
            }
            ((CorePlayer) Sender).StartTeleport(home,(Player)Sender,10);
            Sender.sendMessage(FactionsMain.NAME + TextFormat.GREEN + "Teleporting to " + ofaction.getName() + "'s home in 10 secs!");
        }else{
            if(fac == null){
                Sender.sendMessage(FactionsMain.NAME+TextFormat.RED+"Your Not In a Faction!");
                return;
            }
            Vector3 home = fac.GetHome();
            if(home.y == 0){
                Sender.sendMessage(FactionsMain.NAME+TextFormat.GOLD+"Your faction dose not have a Home is not set!");
                return;
            }
            ((CorePlayer) Sender).StartTeleport(home,(Player)Sender,7);
            Sender.sendMessage(FactionsMain.NAME+TextFormat.GREEN+"Teleporting you to your faction home in 7 Secs!");
        }
    }
}