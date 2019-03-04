package net.yungtechboy1.CyberCore.Manager.Factions.Cmds;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.event.entity.ExplosionPrimeEvent;
import cn.nukkit.utils.TextFormat;

import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;

import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

/**
 * Created by carlt_000 on 7/9/2016.
 */
public class Mission extends Commands {

    public Mission(CommandSender s, String[] a, FactionsMain m){
        super(s,a,"/f mission [radius = 1]",m);
        senderMustBeInFaction = true;
        senderMustBePlayer = true;
        senderMustBeGeneral = true;
        sendFailReason = true;
        sendUsageOnFail = true;

        if(run()){
            RunCommand();
        }
    }

    @Override
    public void RunCommand(){
        if(Args.length == 1){
            Sender.sendMessage(TextFormat.YELLOW+"--------------------------\n" +
                    " - /f mission cancel\n"+
                    " - /f mission accept <id>\n"+
                    " - /f mission list [page]\n"+
                    " - /f mission status\n"+
                    " - /f mission claim\n"+
                    "--------------------------"
            );
        }else if(Args.length == 2){
            if(Args[1].equalsIgnoreCase("cancel")){
                fac.SetActiveMission();
                fac.BroadcastMessage(FactionsMain.NAME+TextFormat.YELLOW+"Faction mission canceled!");
            }else if(Args[1].equalsIgnoreCase("list")){
                SendList(1);
            }else if(Args[1].equalsIgnoreCase("status")){
                if(fac.GetActiveMission() != null){
                        Sender.sendMessage(fac.GetActiveMission().BreakBlockStatus()+"\n"+fac.GetActiveMission().PlaceBlockStatus()+"\n"+fac.GetActiveMission().ItemStatus());
                }else{
                    Sender.sendMessage(FactionsMain.NAME+TextFormat.RED+"Your faction doesn't have an active mission!");
                }
            }else if(Args[1].equalsIgnoreCase("claim")){
                if(fac.GetActiveMission() != null){
                        if(fac.GetActiveMission().CheckCompletion(true) != 0) {
                            Sender.sendMessage(FactionsMain.NAME + TextFormat.RED + "Error! You have not completed all requirements of the Mission");
                        }
                }else{
                    Sender.sendMessage(FactionsMain.NAME+TextFormat.RED+"Your faction doesn't have an active mission!");
                }
            }
        }else if(Args.length == 3){
            if(Args[1].equalsIgnoreCase("accept")){
                try {
                    Integer id = Integer.parseInt(Args[2]);
                    fac.AcceptNewMission(id, Sender);
                }catch (Exception ex){
                    Sender.sendMessage(FactionsMain.NAME+TextFormat.RED+"Error! Usage: /f mission accept <id>");
                }
            }else if(Args[1].equalsIgnoreCase("list")){
                SendList(Integer.parseInt(Args[2]));
            }
        }
    }

    public void SendList(Integer p){
        ArrayList<String> a = new ArrayList<>();
        for(main.java.CyberFactions.Mission.Mission mission: Main.Missions){
            if(fac.GetCompletedMissions().contains(mission.id)){
                //@TODO Decide weather to show this or just show the red...
                a.add(TextFormat.GRAY+"["+TextFormat.RED+mission.id+TextFormat.GRAY+"]"+TextFormat.AQUA+" "+mission.name+TextFormat.YELLOW+" > "+TextFormat.GRAY+ mission.desc);
            }else{
                a.add(TextFormat.GRAY+"["+TextFormat.GREEN+mission.id+TextFormat.GRAY+"]"+TextFormat.AQUA+" "+mission.name+TextFormat.YELLOW+" > "+TextFormat.GRAY+ mission.desc);
            }
     }

        Integer to = p * 5;
        Integer from = to - 5;
        // 5 -> 0 ||| 10 -> 5
        Integer x = 0;
        String t = "";

        t += TextFormat.GRAY+"-----"+TextFormat.GOLD+".<[Faction Mission List]>."+TextFormat.GRAY+"-----\n";
        for(String vvalue : a){
            if(!(x < to && x >= from)){
                x++;
                continue;
            }
            if(x > to)break;
            x++;
            t += vvalue + " \n";
        }
        t += "------------------------------";
        Sender.sendMessage(t);
    }
}
