package net.yungtechboy1.CyberCore.Manager.Factions.Cmds;

import cn.nukkit.utils.TextFormat;

import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.Manager.Factions.Cmds.Base.Commands;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;

import java.util.*;

/**
 * Created by carlt_000 on 7/9/2016.
 */
public class Perk extends Commands {

    public Perk(CorePlayer s, String[] a, FactionsMain m){
        super(s,a,"/f perk",m);
        senderMustBePlayer = true;
        senderMustBeMember = true;
        sendUsageOnFail = true;

        if(run()){
            RunCommand();
        }
    }

    @Override
    public void RunCommand(){
        if(Args.length == 1){
            Sender.sendMessage(TextFormat.YELLOW+
                    "--------------------------\n" +
                    " - /f perk list [page]\n"+
                    " - /f perk claim <id>\n"+
                    "--------------------------"
            );
        }else if(Args.length == 2){
            if(Args[1].equalsIgnoreCase("list")){
                SendList(1);
            }
        }else if(Args.length == 3){
            if(Args[1].equalsIgnoreCase("claim")){
                Integer id = Integer.parseInt(Args[2]);
//                ClaimPerk(id);
            }
        }
    }

//    //Todo
//    public void ClaimPerk(Integer id){
//        for(Map.Entry<String, Object> a: Main.Perks.getAll().entrySet()){
//            ConfigSection b = (ConfigSection) a.getValue();
//            String iidd = ((Integer)b.get("id"))+"";
//            if(!iidd.equalsIgnoreCase(id+""))continue;
//            ConfigSection cost = b.getSection("cost");
//            Integer cxp = (Integer) cost.get("xp");
//            Integer clevel = (Integer) cost.get("level");
//            Integer cmoney = (Integer) cost.get("money");
//            if(cxp != 0 && !fac.TakeXP(cxp)){
//                Sender.sendMessage(FactionsMain.NAME+TextFormat.RED+"Error! You don't have enough faction XP! "+cxp+" XP is required for this perk!");
//                return;
//            }
//            fac.TakeXP(cxp);
//            if(clevel != 0 && fac.GetLevel() < clevel){
//                Sender.sendMessage(FactionsMain.NAME+TextFormat.RED+"Error! You'ur faction is not high enough in level! "+clevel+" Levels is required for this perk!");
//                return;
//            }
//            fac.TakeLevel(clevel);
//            if(cmoney != 0 && fac.getMoney() < cmoney){
//                Sender.sendMessage(FactionsMain.NAME+TextFormat.RED+"Error! You don't have enough faction money! $"+cmoney+" is required for this perk!");
//                return;
//            }
//            String name = (String)b.get("name");
//            String desc = (String)b.get("desc");
//            fac.BroadcastMessage(FactionsMain.NAME+TextFormat.GREEN+name+" Perk Claimed!");
//            ConfigSection reward = b.getSection("reward");
//            /*
//        *       cmds: []
//                effects:
//                 3:1: 6000
//                items: []
//                level: 0
//                xp: 0
//                money: 0
//            * */
//            ArrayList<String> cmds = (ArrayList<String>)reward.get("cmds");
//            ConfigSection effect = (ConfigSection)reward.get("effects");
//            ConfigSection items = (ConfigSection)reward.get("items");
//            Integer level = (Integer)reward.get("level");
//            Integer xp = (Integer)reward.get("xp");
//            Integer money = (Integer)reward.get("money");
//            if(cmds.size() != 0){
//                for(String cmd: cmds){
//                    cmd = cmd.replace("{faction}",fac.GetName());
//                    if(cmd.contains("{player}")){
//                        for(Player player: fac.GetOnlinePlayers()){
//                            Main.getServer().dispatchCommand(new ConsoleCommandSender(), cmd.replace("{player}",player.getName()));
//                        }
//                    }else {
//                        Main.getServer().dispatchCommand(new ConsoleCommandSender(), cmd);
//                    }
//                }
//            }
//            if(effect.size() != 0){
//                for(Player player: fac.GetOnlinePlayers()){
//                    for(Map.Entry<String, Object> c: effect.entrySet()) {
//                        String key = c.getKey();
//                        Integer length = (Integer) c.getValue();
//                        Integer eid;
//                        Integer lvl = 1;
//                        if (key.contains("|")) {
//                            eid = Integer.parseInt(key.split("\\|")[0]);
//                            lvl = Integer.parseInt(key.split("\\|")[1]);
//                        } else {
//                            eid = Integer.parseInt(key);
//                        }
//                        Effect e = Effect.getEffect(eid);
//                        e.setDuration(length);
//                        e.setAmplifier(lvl);
//                        player.addEffect(e);
//                    }
//                }
//                fac.BroadcastMessage(FactionsMain.NAME+TextFormat.GREEN+effect.size()+" Effects added to everyone!");
//            }
//
//            if(items.size() != 0){
//                for(Player player: fac.GetOnlinePlayers()){
//                    for(Map.Entry<String, Object> c: effect.entrySet()) {
//                        String key = c.getKey();
//                        Integer bid;
//                        Integer bmeta = 0;
//                        Integer bcount = 0;
//                        if (key.contains("|")) {
//                            bid = Integer.parseInt(key.split("\\|")[0]);
//                            bmeta = Integer.parseInt(key.split("\\|")[1]);
//                        } else {
//                            bid = Integer.parseInt(key);
//                        }
//                        Item i = Item.get(bid,bmeta,bcount);
//                        player.getInventory().addItem(i);
//                    }
//                }
//                fac.BroadcastMessage(FactionsMain.NAME+TextFormat.GREEN+items.size()+" Items added to everyone's Inventories!");
//            }
//
//            if(level != 0){
//                fac.AddLevel(level);
//                fac.BroadcastMessage(FactionsMain.NAME+TextFormat.GREEN+level+" Levels added to Faction Level!");
//            }
//            if(xp != 0){
//                fac.AddXP(xp);
//                fac.BroadcastMessage(FactionsMain.NAME+TextFormat.GREEN+xp+" XP added to Faction Experience!");
//            }
//            if(money != 0){
//                fac.AddMoney(money);
//                fac.BroadcastMessage(FactionsMain.NAME+TextFormat.GREEN+"$"+money+" added to Faction Balance!");
//            }
//            return;
//        }
//        Sender.sendMessage(FactionsMain.NAME+TextFormat.RED+"Error that id is invalid!");
//    }

    public void SendList(Integer p){
        ArrayList<String> a = new ArrayList<>();
        //TODO
//        for(Map.Entry<String, Object> c: Main.Perks.getAll().entrySet()){
//            ConfigSection b = (ConfigSection) c.getValue();
//            String iidd = ((Integer)b.get("id"))+"";
//            String name = (String)b.get("name");
//            String desc = (String)b.get("desc");
//            a.add(TextFormat.GRAY+"["+TextFormat.GREEN+iidd+TextFormat.GRAY+"]"+TextFormat.AQUA+" "+name+TextFormat.YELLOW+" > "+TextFormat.GRAY+ desc);
//        }
//
//        Integer to = p * 5;
//        Integer from = to - 5;
//        // 5 -> 0 ||| 10 -> 5
//        Integer x = 0;
//        String t = "";
//
//        t += TextFormat.GRAY+"-----"+TextFormat.GOLD+".<[Faction Perk List]>."+TextFormat.GRAY+"-----\n";
//        for(String vvalue : a){
//            if(!(x < to && x >= from)){
//                x++;
//                continue;
//            }
//            if(x > to)break;
//            x++;
//            t += vvalue + " \n";
//        }
//        t += "------------------------------";
//        Sender.sendMessage(t);
    }
}
