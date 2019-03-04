package net.yungtechboy1.CyberCore.Manager.Factions.Cmds;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;

import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;

/**
 * Created by carlt_000 on 7/9/2016.
 */
public class AllyChat extends Commands {

    public AllyChat(CommandSender s, String[] a, FactionsMain m){
        super(s,a,"/f allychat [Text]",m);
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
        String chat = "";
        int a = 0;
        if(Args.length == 2){
            String name = Sender.getName().toLowerCase();
            if(Args[1].equalsIgnoreCase("on")){
                if(fac.getFChat().contains(name))fac.getFChat().remove(name);
                fac.getFAlly().add(name);
                Sender.sendMessage(TextFormat.GREEN+"Ally Chat Activated!");
                return;
            }else if(Args[1].equalsIgnoreCase("off")){
                if(fac.getFAlly().contains(name)){
                    fac.getFAlly().remove(name);
                }else{
                    Sender.sendMessage(TextFormat.RED+"Error! Ally Chat was not on!");
                }
                return;
            }
        }
        for(String c : Args){
            a++;
            if(a == 1)continue;
            chat += c+" ";
        }
        String n = Sender.getName();
        if(fac.Leader.equalsIgnoreCase(Sender.getName())){
            fac.MessageAllys(TextFormat.YELLOW+"~***["+n+"]***~: "+chat);
        }else if(fac.IsGeneral(Sender.getName())){
            fac.MessageAllys(TextFormat.YELLOW+"~**["+n+"]**~: "+chat);
        }else if(fac.IsOfficer(Sender.getName())){
            fac.MessageAllys(TextFormat.YELLOW+"~*["+n+"]*~: "+chat);
        }else if(fac.IsMember(Sender.getName())){
            fac.MessageAllys(TextFormat.YELLOW+"~["+n+"]~: "+chat);
        }else{
            fac.MessageAllys(TextFormat.YELLOW+"-["+n+"]-: "+chat);
        }
    }
}
