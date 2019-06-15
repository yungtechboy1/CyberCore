package net.yungtechboy1.CyberCore.Manager.Factions.Cmds;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;

import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionRank;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;

/**
 * Created by carlt_000 on 7/9/2016.
 */
public class Claim extends Commands {

    public Claim(CorePlayer s, String[] a, FactionsMain m){
        super(s,a,"/f claim [radius = 1]",m);
        senderMustBeInFaction = true;
        senderMustBePlayer = true;
        sendFailReason = true;
        sendUsageOnFail = true;

        if(run()){
           RunCommand();
        }
    }

    @Override
    public void RunCommand(){
        Integer Radius = GetIntegerAtArgs(1,1);
        FactionRank r = fac.getPlayerRank((CorePlayer) Sender);
        if(r != null){
            if(!r.HasPerm(fac.getSettings().getAllowedToClaim())){
                Sender.sendMessage("Error! You don't have permission to Claim Plots!");
                return;
            }
        }

        if(Radius > 1){
            Integer rr = Radius * Radius;
            Integer money = (5000*rr);
            Integer power = (rr);
            if(fac.GetMoney() > money){
                Sender.sendMessage(FactionsMain.NAME+TextFormat.RED+"Your Faction does not have $"+money+" in your faction account!");
                return;
            }
            if(fac.GetPower() < power){
                Sender.sendMessage(FactionsMain.NAME+TextFormat.RED+"Your Faction does not have enough power!");
                return;
            }
            for(int x = Math.negateExact(Radius); x<Radius; x++){
                for(int z = Math.negateExact(Radius); z<Radius; z++) {
                    int xx = ((int)((Player) Sender).getX() >> 4 ) + x;
                    int zz = ((int)((Player) Sender).getZ() >> 4 ) + z;
                    ClaimLand(xx,zz);
                }
            }
        }else{

            int x = (int)((Player) Sender).getX() >> 4;
            int z = (int)((Player) Sender).getZ() >> 4;
            //amount = (100) * Main.prefs["PlotPrice"];
            int money = 5000;
            int power = 1;
            if(fac.GetMoney() < money){
                Sender.sendMessage(FactionsMain.NAME+TextFormat.RED+"Your Faction does not have $"+money+" in your faction account!");
                return;
            }
            if(fac.GetPower() < power){
                Sender.sendMessage(FactionsMain.NAME+TextFormat.RED+"Your Faction does not have "+power+" PowerPublicInterface!");
                return;
            }
            if(Main.FFactory.PlotsList.containsKey(x+"|"+z)){
                Sender.sendMessage(FactionsMain.NAME+TextFormat.RED+"That land is already Claimed by"+Main.FFactory.PlotsList.get(x+"|"+z)+"'s Faction!!");
                return;
            }
            Sender.sendMessage(FactionsMain.NAME+TextFormat.GREEN+"Purchase Successful! $5000 and 1 PowerPublicInterface Withdrawn To Purchase This Chunk!");
            fac.TakeMoney(money);
            fac.AddPlots(x+"|"+z);
            fac.TakePower(power);
            Main.FFactory.PlotsList.put(x+"|"+z,fac.GetName());
        }

    }

    private void ClaimLand(Integer x,Integer z){
        int money = 5000;
        int power = 1;
        if(fac.GetMoney() < money){
            Sender.sendMessage(FactionsMain.NAME+TextFormat.RED+"Your Faction does not have $"+money+" in your faction account to claim Chunk at X:"+x+" Z:"+z+"!");
            return;
        }
        if(fac.GetPower() < power){
            Sender.sendMessage(FactionsMain.NAME+TextFormat.RED+"Your Faction does not have "+power+" PowerPublicInterface to claim Chunk at X:"+x+" Z:"+z+"!");
            return;
        }
        if(Main.FFactory.PlotsList.containsKey(x+"|"+z)){
            Sender.sendMessage(FactionsMain.NAME+TextFormat.RED+"That Chunk at X:"+x+" Z:"+z+" is already Claimed by"+Main.FFactory.PlotsList.get(x+"|"+z)+"'s Faction!!");
            return;
        }
        Sender.sendMessage(FactionsMain.NAME+TextFormat.GREEN+"Purchase Successful! $5000 and 1 PowerPublicInterface Withdrawn To Purchase Chunk at X:"+x+" Z:"+z+"!");
        fac.TakeMoney(money);
        fac.AddPlots(x+"|"+z);
        fac.TakePower(power);
        Main.FFactory.PlotsList.put(x+"|"+z,fac.GetName());
    }
}
