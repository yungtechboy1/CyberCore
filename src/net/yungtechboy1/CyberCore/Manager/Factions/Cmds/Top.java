package net.yungtechboy1.CyberCore.Manager.Factions.Cmds;


import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;

import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;

import java.util.*;
import java.util.Map;

/**
 * Created by carlt_000 on 7/9/2016.
 */
public class Top extends Commands {

    public Top(CorePlayer s, String[] a, FactionsMain m){
        super(s,a,"/f Top",m);
        sendUsageOnFail = true;

        if(run()){
            RunCommand();
        }
    }

    @Override
    public void RunCommand() {
        String s = "";
        s = s + TextFormat.YELLOW+"-------{[Faction Top]}-------\n";
        int x = 0;
        for(Map.Entry<Integer,String> b: GetTopFacs(10).entrySet()) {
            String[] c = b.getValue().split(",");
            String name = c[0];
            String money = c[1];
            String t = TextFormat.GRAY+"["+TextFormat.GREEN+x+TextFormat.GRAY+"]";
            s = s + t + TextFormat.YELLOW + " | " + TextFormat.AQUA + " "+name + TextFormat.YELLOW+ " "+money+"\n";
            x++;
        }
        Sender.sendMessage(s + TextFormat.YELLOW+ "-------------------------");

    }

    private HashMap<Integer,String> GetTopFacs(Integer i){
        HashMap<Integer,String> map = new HashMap<>();
        HashMap<String,Integer> Top = new HashMap<>();
        for(Map.Entry<String,Integer> a : Main.FFactory.Top.entrySet()){
            Top.put(a.getKey(),a.getValue());
        }
        for (int x = 0;x < i; x++){
            int maxValue = Integer.MIN_VALUE;
            String mvk = null;
            for (Map.Entry<String,Integer> a : Top.entrySet()) {
                if (map.containsValue(a.getKey()))continue;
                if (a.getValue() > maxValue) {
                    maxValue = a.getValue();
                    mvk = a.getKey();
                }
            }
            if(mvk == null){
                map.put(x,"--------------------");
            }else {
                map.put(x, mvk + "," + maxValue);
                Top.remove(mvk);
            }
        }
        return map;
    }
}

