package net.yungtechboy1.CyberCore.Manager.Factions.Cmds;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;
import main.java.CyberFactions.Faction;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;

import java.util.Calendar;

/**
 * Created by carlt_000 on 7/9/2016.
 */
public class War extends Commands {

    public War(CommandSender s, String[] a, FactionsMain m){
        super(s,a,"/f war <Faction>",m);
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
        if (!(Sender instanceof Player)) {
            return;
        }
        Faction target = GetFactionAtArgs(1);
        if(target == null){
            Sender.sendMessage(FactionsMain.NAME+"Error the faction containing '"+GetStringAtArgs(1)+"' could not be found!");
        }else{
            if (target.AtWar()){
                Sender.sendMessage(FactionsMain.NAME+TextFormat.RED+"Error, That target faction is currently at war!");
                return;
            }
            if (fac.AtWar()){
                Sender.sendMessage(FactionsMain.NAME+TextFormat.RED+"Error, you are already in a war!");
                return;
            }
            if (target.HasWarCooldown()){
                Sender.sendMessage(FactionsMain.NAME+TextFormat.RED+"Error, that faction has a war cooldown and cannot bet attacked right now... Try again later!");
                return;
            }

            Float tp = (float) target.GetMaxPower();
            Float fp = (float) fac.GetMaxPower();
            Float ratio = tp / fp;//40/50 = 4/5th = .8
            if(.5f > ratio){
                Sender.sendMessage(FactionsMain.NAME+TextFormat.RED+"Error, That target faction is not powerful enough for your faction to attack. The Power Ration is "+ratio+" and it must be above .5 for you to attack!");
                return;
            }else if(ratio > 2f){
                Sender.sendMessage(FactionsMain.NAME+TextFormat.RED+"Error, That target faction is too powerful for your faction to attack. The Power Ration is "+ratio+" and must be below 2 for you to attack!");
                return;
            }

            //ENEMY 40/100 FRIENDLY 50/70
            //      .40             .714
            //MIN 16 Power for Attack
            // ROUND(50*(((40/100)+1)/2))
            // ROUND(50*(((.4)+1)/2))
            // ROUND(50*((1.4)/2))
            // ROUND(50*( .7 ))
            // ROUND(50*( .7 ))
            // ROUND( 35 )


            Integer takepower = Math.round(fac.GetPower()*(((target.GetPower()/target.GetMaxPower())+1)/2));
            if (takepower < Math.floor(target.GetPower()*.4))takepower = (int) Math.floor(target.GetPower()*.2);

            if (fac.GetPower() < takepower){
                Sender.sendMessage(FactionsMain.NAME+TextFormat.RED+"Error, Your faction does not have "+takepower+" Power needed for this attack!");
                return;
            }

            fac.TakePower(takepower);
            Integer time = (int)(Calendar.getInstance().getTime().getTime()/1000);
            Main.DeclareWar(target,fac);




        }

    }
}
