package net.yungtechboy1.CyberCore.Manager.Factions.Cmds;

import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.Manager.Factions.Cmds.Base.Commands;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;
import net.yungtechboy1.CyberCore.Manager.Form.Windows.FactionAdminPage1;

import java.util.ArrayList;

/**
 * Created by carlt_000 on 7/9/2016.
 */
public class Admin extends Commands {

    public Admin(CorePlayer s, String[] a, FactionsMain m) {
        super(s, a, "/f Admin", m);
        senderMustBePlayer = true;
//        senderMustBeInFaction = true;
//        senderMustBeAdmin = true;
        sendUsageOnFail = true;

        if (run()) {
            RunCommand();
        }
    }

    @Override
    public void RunCommand() {
        try {
            Sender.showFormWindow(new FactionAdminPage1());
        }catch (Exception e){
            CyberCoreMain.getInstance().getLogger().error("EEE!213111 >>",e);
        }





        //TODO MAKE NEAT
//        if (Args.length == 1) {
//            //Send Help
//            SendHelp(1);
//        } else if (Args.length == 2) {
//            if (Args[1].equalsIgnoreCase("reload")) {
//                Main.FFactory = new FactionFactory(Main);
//                for (String fn : Main.FFactory.GetAllFactions()) {
//                    Faction f = Main.FFactory.getFaction(fn);
//                    if (f != null) {
//                        Main.FFactory.List.put(fn.toLowerCase(), f);
//                    }
//                }
//                Sender.sendMessage(FactionsMain.NAME+TextFormat.GREEN + "Faction Reloaded!");
//            } else if (Args[1].equalsIgnoreCase("test2")) {
//                Sender.sendMessage(Main.getServer().getCommandMap().getCommand("msg").getClass().getCanonicalName());
//            } else if (Args[1].equalsIgnoreCase("save")) {
//                Main.FFactory.SaveAllFactions();
////            } else if (Args[1].equalsIgnoreCase("test")) {
////                String t = TextFormat.LIGHT_PURPLE+""+TextFormat.BOLD+"====TEST "+TextFormat.GOLD+"WARNING====";
////                String m = "TT will be starting soon!s";
////                BossBarNotification b = new BossBarNotification((Player)Sender,t,m,20*10,Main);
////                b.send();
////                Main.AddBBN((Player)Sender,b);
//            } else if (Args[1].equalsIgnoreCase("help")) {
//                SendHelp(1);
//            } else if (Args[1].equalsIgnoreCase("test2s")) {
//                fac.AddXP(10);
//                fac.BroadcastMessage("XP GIVEN!!!!!! > "+fac.GetXP());
//            } else if (Args[1].equalsIgnoreCase("unclaim")) {
//                if (!(Sender instanceof Player)) {
//                    Sender.sendMessage(FactionsMain.NAME+TextFormat.RED + "Error Must Be Player!");
//                    return;
//                }
//                int x = (int) ((Player) Sender).getX() >> 4;
//                int z = (int) ((Player) Sender).getZ() >> 4;
//                if (!Main.FFactory.PlotsList.containsKey(x + "|" + z)) {
//                    Sender.sendMessage(FactionsMain.NAME+TextFormat.RED + "Chunk Not Claimed!");
//                    return;
//                }
//                Faction tf = Main.FFactory.getFaction(Main.FFactory.PlotsList.get(x + "|" + z));
//                if (tf == null) {
//                    Sender.sendMessage(FactionsMain.NAME+TextFormat.RED + "Error Unclaiming Chunk!?!?!?!?");
//                    return;
//                }
//                Sender.sendMessage(FactionsMain.NAME+TextFormat.GREEN + "Plot Removed!");
//                tf.DelPlots(x + "|" + z);
//                Main.FFactory.PlotsList.remove(x + "|" + z);
//            }
//        } else if (Args.length == 3) {
//            if (Args[1].equalsIgnoreCase("delete")) {
//                Faction tf = Main.FFactory.getFaction(Args[2]);
//                if (tf == null) {
//                    Sender.sendMessage(FactionsMain.NAME+TextFormat.RED + "Target Faction Not Found!");
//                    return;
//                }
//                ;
//                Sender.sendMessage(FactionsMain.NAME+TextFormat.GREEN + "Faction Deleted!");
//                tf.BroadcastMessage(FactionsMain.NAME+TextFormat.YELLOW + "!!~~!!Faction has been Deleted by " + TextFormat.AQUA + "[ADMIN]" + Sender.getName());
//                Main.FFactory.RemoveFaction(tf);
//            } else if (Args[1].equalsIgnoreCase("help")) {
//                SendHelp(Integer.parseInt(Args[2]));
//            } else if (Args[1].equalsIgnoreCase("claim")) {
//                if (!(Sender instanceof Player)) {
//                    Sender.sendMessage(FactionsMain.NAME+TextFormat.RED + "Error Must Be Player!");
//                    return;
//                }
//                Faction tf = Main.FFactory.getFaction(Args[2]);
//                if (tf == null) {
//                    Sender.sendMessage(FactionsMain.NAME+TextFormat.RED + "Error Faction Not Found!");
//                    return;
//                }
//                ;
//                int x = (int) ((Player) Sender).getX() >> 4;
//                int z = (int) ((Player) Sender).getZ() >> 4;
//                if (Main.FFactory.PlotsList.containsKey(x + "|" + z)) {
//                    String tfs = Main.FFactory.PlotsList.getOrDefault(x + "|" + z, null);
//                    if (tfs != null) {
//                        Faction ntf = Main.FFactory.getFaction(tfs);
//                        if (ntf != null) {
//                            if (ntf.GetPlots().contains(x + "|" + z)) {
//                                ntf.DelPlots(x + "|" + z);
//                            }
//                        }
//                    }
//                }
//                tf.AddPlots(x + "|" + z);
//                Main.FFactory.PlotsList.put(x + "|" + z, tf.GetName());
//                Sender.sendMessage(FactionsMain.NAME+TextFormat.GREEN + "Plot Claimed!");
//            }else if (Args[1].equalsIgnoreCase("unclaim")) {
//                if (!(Sender instanceof Player)) {
//                    Sender.sendMessage(FactionsMain.NAME+TextFormat.RED + "Error Must Be Player!");
//                    return;
//                }
//                Integer radius = GetIntegerAtArgs(2, 1);
//
//                for (int x = Math.negateExact(radius); x < radius; x++) {
//                    for (int z = Math.negateExact(radius); z < radius; z++) {
//                        int xx = (int) ((Player) Sender).getX() >> 4;
//                        int zz = (int) ((Player) Sender).getZ() >> 4;
//                        if (Main.FFactory.PlotsList.containsKey((x + xx) + "|" + (z + zz))) {
//                            String tfs = Main.FFactory.PlotsList.getOrDefault(((x + xx) + "|" + (z + zz)), null);
//                            if (tfs != null) {
//                                Faction ntf = Main.FFactory.getFaction(tfs);
//                                if (ntf != null && ntf.GetPlots().contains((x + xx) + "|" + (z + zz)))ntf.DelPlots((x + xx) + "|" + (z + zz));
//                            }
//                        }
//                        Main.FFactory.PlotsList.remove((x + xx) + "|" + (z + zz));
//                    }
//                }
//
//            }
//        } else if (Args.length == 4) {
//            if (Args[1].equalsIgnoreCase("setxp")) {
//                Faction tf = Main.FFactory.getFaction(Args[2]);
//                if (tf == null) {
//                    Sender.sendMessage(FactionsMain.NAME + TextFormat.RED + "Targeted Faction '" + Args[2] + "' Not Found!");
//                    return;
//                }
//                Integer xp = GetIntegerAtArgs(3, 0);
//                tf.SetXPCalculate(xp);
//                Sender.sendMessage(FactionsMain.NAME+TextFormat.GREEN + "XP Set!");
//            } else if (Args[1].equalsIgnoreCase("setpower")) {
//                Faction tf = Main.FFactory.getFaction(Args[2]);
//                if (tf == null) {
//                    Sender.sendMessage(FactionsMain.NAME+TextFormat.RED + "Targeted Faction '" + Args[2] + "' Not Found!");
//                    return;
//                }
//                Integer pwr = GetIntegerAtArgs(3, 2);
//                tf.SetPower(pwr);
//                Sender.sendMessage(FactionsMain.NAME+TextFormat.GREEN + "PowerAbstract Set!");
//            } else if (Args[1].equalsIgnoreCase("setdisplayname")) {
//                Faction tf = Main.FFactory.getFaction(Args[2]);
//                if (tf == null) {
//                    Sender.sendMessage(FactionsMain.NAME+TextFormat.RED + "Targeted Faction '" + Args[2] + "' Not Found!");
//                    return;
//                }
//                tf.SetDisplayName(Args[3]);
//                Sender.sendMessage(FactionsMain.NAME+TextFormat.GREEN + "Display name Set!");
//            } else if (Args[1].equalsIgnoreCase("setleader")) {
//                Faction tf = Main.FFactory.getFaction(Args[2]);
//                if (tf == null) {
//                    Sender.sendMessage(FactionsMain.NAME+TextFormat.RED + "Targeted Faction '" + Args[2] + "' Not Found!");
//                    return;
//                }
//
//                Player pp = Main.getServer().getPlayer(Args[3]);
//                if (pp != null) {
//                    String ppn = pp.getName();
//                    if (tf.GetName().equalsIgnoreCase(Main.getPlayerFaction(ppn))) {
//                        Integer r = tf.GetPlayerPerm(ppn);
//                        if (r == 0) tf.DelRecruit(ppn);
//                        if (r == 1) tf.DelMember(ppn);
//                        if (r == 2) tf.DelOfficer(ppn);
//                        if (r == 3) tf.DelGeneral(ppn);
//                        tf.AddMember(tf.GetLeader());
//                        tf.SetLeader(ppn.toLowerCase());
//                        tf.BroadcastMessage(TextFormat.YELLOW + "" + ppn + " Is your New Leader!");
//                        pp.sendMessage(FactionsMain.NAME+TextFormat.YELLOW + "You are now leader of factionName!");
//                    } else {
//                        Sender.sendMessage(FactionsMain.NAME+TextFormat.RED + "Player Is Not in Same Faction!");
//                    }
//                } else {
//                    Sender.sendMessage(FactionsMain.NAME+TextFormat.RED + "Player Not Online or Found!");
//                }
//
//                Sender.sendMessage(FactionsMain.NAME+TextFormat.GREEN + "Leader Set!");
//            } else if (Args[1].equalsIgnoreCase("setmaxplayers")) {
//                Faction tf = Main.FFactory.getFaction(Args[2]);
//                if (tf == null) {
//                    Sender.sendMessage(FactionsMain.NAME+TextFormat.RED + "Targeted Faction '" + Args[2] + "' Not Found!");
//                    return;
//                }
//                Integer pwr = Integer.parseInt(Args[3]);
//                tf.SetMaxPlayers(pwr);
//                Sender.sendMessage(FactionsMain.NAME+TextFormat.GREEN + "Max Players Set!");
//            } else if (Args[1].equalsIgnoreCase("claim")) {
//                if (!(Sender instanceof Player)) {
//                    Sender.sendMessage(FactionsMain.NAME+TextFormat.RED + "Error Must Be Player!");
//                    return;
//                }
//                Faction tf = Main.FFactory.getFaction(Args[2]);
//                if (tf == null) {
//                    Sender.sendMessage(FactionsMain.NAME+TextFormat.RED + "Error Faction Not Found!");
//                    return;
//                }
//                Integer radius = GetIntegerAtArgs(3, 1);
//
//                for (int x = Math.negateExact(radius); x < radius; x++) {
//                    for (int z = Math.negateExact(radius); z < radius; z++) {
//                        int xx = (int) ((Player) Sender).getX() >> 4;
//                        int zz = (int) ((Player) Sender).getZ() >> 4;
//                        if (Main.FFactory.PlotsList.containsKey((x + xx) + "|" + (z + zz))) {
//                            String tfs = Main.FFactory.PlotsList.getOrDefault(((x + xx) + "|" + (z + zz)), null);
//                            if (tfs != null) {
//                                Faction ntf = Main.FFactory.getFaction(tfs);
//                                if (ntf != null) {
//                                    if (ntf.GetPlots().contains((x + xx) + "|" + (z + zz))) {
//                                        ntf.DelPlots((x + xx) + "|" + (z + zz));
//                                    }
//                                }
//                            }
//                        }
//                        tf.AddPlots((x + xx) + "|" + (z + zz));
//                        Main.FFactory.PlotsList.put((x + xx) + "|" + (z + zz), tf.GetName());
//                    }
//                }
//            }
//        }
    }

    public void SendHelp(Integer page) {
        ArrayList<String> a = new ArrayList<>();
        a.add("/f admin reload");
        a.add("/f admin test");
        a.add("/f admin test2s");
        a.add("/f admin unclaim");
        a.add("/f admin delete <fac>");
        a.add("/f admin claim <fac>");
        a.add("/f admin unclaim <fac>");
        a.add("/f admin setxp <fac> <amt>");
        a.add("/f admin setpower <fac> <power>");
        a.add("/f admin setdisplayname <fac> <name>");
        a.add("/f admin setleader <fac> <leader>");
        a.add("/f admin setmaxplayers <fac> <max>");
        a.add("/f admin claim <fac> <radius>");


        Integer p = page;
        Integer to = p * 5;
        Integer from = to - 5;
        // 5 -> 0 ||| 10 -> 5
        Integer x = 0;
        String t = "";

        t += TextFormat.GRAY+"-----"+TextFormat.GOLD+".<[Faction Admin Command List]>."+TextFormat.GRAY+"-----\n";
        for(String value : a){
            // 0 < 5 && 0 >= 0
            //   YES     YES
            //
            //0
            //1 2 3 4 5
            //0 < 10 && 0 >= 5
            if(!(x < to && x >= from)){
                x++;
                continue;
            }
            if(x > to)break;
            x++;
            t += value + "\n";

        }
        t += "------------------------------";
        Sender.sendMessage(t);
    }
}
