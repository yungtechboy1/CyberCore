package net.yungtechboy1.CyberCore.Manager.Factions.Cmds;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;

import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;

/**
 * Created by carlt_000 on 7/9/2016.
 */
public class Unclaim extends Commands {

    public Unclaim(CorePlayer s, String[] a, FactionsMain m){
        super(s,a,"/f unclaim [radius = 1]",m);
        senderMustBeInFaction = true;
        senderMustBeGeneral = true;
        sendFailReason = true;
        sendUsageOnFail = true;

        if(run()){
            RunCommand();
        }
    }

    @Override
    public void RunCommand(){
        Integer Radius = GetIntegerAtArgs(1,1);
        if(Radius > 1){
            Integer rr = Radius * Radius;
            for(int x = Math.negateExact(Radius); x<Radius; x++){
                for(int z = Math.negateExact(Radius); z<Radius; z++) {
                    int xx = ((int)((Player) Sender).getX() >> 4 ) + x;
                    int zz = ((int)((Player) Sender).getZ() >> 4 ) + z;
                    UnClaimLand(xx,zz);
                }
            }
        }else{

            int x = (int)((Player) Sender).getX() >> 4;
            int z = (int)((Player) Sender).getZ() >> 4;
            //amount = (100) * Main.prefs["PlotPrice"];


            if(!Main.FFactory.PlotsList.containsKey(x+"|"+z)){
                Sender.sendMessage(FactionsMain.NAME+TextFormat.RED+"Chunk Not Claimed!");
                return;
            }
            if(!Main.FFactory.GetPlotStatus(x,z).GetName().equalsIgnoreCase(fac.GetName())){
                Sender.sendMessage(FactionsMain.NAME+TextFormat.RED+"Your Faction Dose not owne this Chunk!");
                return;
            }
            Sender.sendMessage(FactionsMain.NAME+TextFormat.GREEN+"Plot Removed!");
            fac.unregisterPlot(x+"|"+z);
            Main.FFactory.PlotsList.remove(x+"|"+z);
        }

    }

    private void UnClaimLand(Integer x,Integer z){
        if(!Main.FFactory.PlotsList.containsKey(x+"|"+z)){
            Sender.sendMessage(FactionsMain.NAME+TextFormat.RED+"Chunk Not Claimed!");
            return;
            //Sender.sendMessage(TextFormat.RED+"That Chunk at X:"+x+" Z:"+z+" is already Claimed by"+Main.FFactory.PlotsList.get(x+"|"+z)+"'s Faction!!");
        }
        if(!Main.FFactory.GetPlotStatus(x,z).GetName().equalsIgnoreCase(fac.GetName())){
            Sender.sendMessage(FactionsMain.NAME+TextFormat.RED+"Your Faction Dose not own this Chunk!");
            return;
        }
        Sender.sendMessage(FactionsMain.NAME+TextFormat.GREEN+"Plot Removed!");
        fac.unregisterPlot(x+"|"+z);
        Main.FFactory.PlotsList.remove(x+"|"+z);
    }
}
