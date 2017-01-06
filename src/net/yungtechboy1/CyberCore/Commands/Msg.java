package net.yungtechboy1.CyberCore.Commands;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.Main;
import net.yungtechboy1.CyberCore.Msgs;

/**
 * Created by carlt_000 on 3/21/2016.
 */

public class Msg {
    Main Owner;
    public void Msg(Main server){
        Owner = server;
    }

    public static void runCommand(CommandSender s,String[] args, Main server){
        if(s instanceof Player){
            Player p = (Player)s;
            if(args.length >= 2){
                Player t = server.getServer().getPlayer(args[0]);
                if(t == null){
                    if(args[0].equalsIgnoreCase("SERVER")){

                        String msg = implode(" ", args);
                        s.sendMessage(TextFormat.YELLOW+"[You > SERVER] : "+TextFormat.AQUA+msg);
                        server.getLogger().info(TextFormat.YELLOW+"["+p.getName()+" > You/Server ] : "+TextFormat.AQUA+msg);

                        server.LastMsg.put("SERVER",s.getName().toLowerCase());
                        server.LastMsg.put(s.getName().toLowerCase(),"SERVER");
                        return;
                    }
                    s.sendMessage(TextFormat.RED+"Error! Target Player Not Found!");
                    return;
                }
                String msg = implode(" ", args);
                t.sendMessage(TextFormat.YELLOW+"["+p.getName()+" > You] : "+TextFormat.AQUA+msg);
                s.sendMessage(TextFormat.YELLOW+"[You > "+t.getName()+"] : "+TextFormat.AQUA+msg);
                server.getLogger().info(TextFormat.YELLOW+"["+p.getName()+" > "+t.getName()+"] : "+TextFormat.AQUA+msg);
                server.LastMsg.put(p.getName().toLowerCase(),t.getName().toLowerCase());
                server.LastMsg.put(t.getName().toLowerCase(),p.getName().toLowerCase());
            }else{
                s.sendMessage(TextFormat.YELLOW+"Usage :/msg <Player> <message>");
                return;
            }
        } else {
            if(args.length >= 2){
                Player t = server.getServer().getPlayer(args[0]);
                if(t == null){
                    s.sendMessage(TextFormat.RED+"Error! Target Player Not Found!");
                    return;
                }
                String msg = implode(" ", args);
                t.sendMessage(TextFormat.YELLOW+"[SERVER > You] : "+TextFormat.AQUA+msg);
                s.sendMessage(TextFormat.YELLOW+"[You > "+t.getName()+"] : "+TextFormat.AQUA+msg);
                server.getLogger().info(TextFormat.YELLOW+"[SERVER > "+t.getName()+"] : "+TextFormat.AQUA+msg);
                server.LastMsg.put("SERVER",t.getName().toLowerCase());
                server.LastMsg.put(t.getName().toLowerCase(),"SERVER");
                return;
            }else{
                s.sendMessage(TextFormat.YELLOW+"Usage :/msg <Player> <message>");
                return;
            }
        }
    }

    public static String implode(String separator, String... data) {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < data.length - 1; i++) {
            //data.length - 1 => to not add separator at the end
            if (!data[i].matches(" *")) {//empty string are ""; " "; "  "; and so on
                sb.append(data[i]);
                sb.append(separator);
            }
        }
        sb.append(data[data.length - 1].trim());
        return sb.toString();
    }
}