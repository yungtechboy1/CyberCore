package net.yungtechboy1.CyberCore.Commands;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.Utils;

/**
 * Created by carlt_000 on 3/21/2016.
 */

public class Msg extends Command {
    CyberCoreMain Owner;

    public Msg(CyberCoreMain server) {
        super("msg", "Send messages between players", "/msg <player> <msg>", new String[]{"message", "tell"});
        Owner = server;
        this.commandParameters.clear();
        this.commandParameters.put("default", new CommandParameter[]{
                new CommandParameter("player", CommandParamType.TARGET, false),
                new CommandParameter("message", CommandParamType.RAWTEXT, false)
        });
    }
    @Override
    public boolean execute(CommandSender s, String label, String[] args) {
        CyberCoreMain server = Owner;
        if (s instanceof CorePlayer) {
            CorePlayer p = (CorePlayer) s;
            if (args.length >= 2) {
                CorePlayer t = (CorePlayer)server.getServer().getPlayer(args[0]);
                if (t == null) {
                    if (args[0].equalsIgnoreCase("SERVER")) {

                        String msg = Utils.implode(" ", args);
                        s.sendMessage(TextFormat.YELLOW + "[You > SERVER] : " + TextFormat.AQUA + msg);
                        server.getLogger().info(TextFormat.YELLOW + "[" + p.getName() + " > You/Server ] : " + TextFormat.AQUA + msg);

                        server.LastMsg.put( s.getName().toLowerCase(),"SERVER");
                        p.LastMessageSentTo = "SERVER";
                        return true;
                    }
                    s.sendMessage(TextFormat.RED + "Error! Target Player Not Found!");
                    return true;
                }
                if(t.MuteMessage){
                    s.sendMessage(TextFormat.RED + "Error! "+t.getName()+" is not accepting Messages right now!");
                    return true;
                }
                String msg = Utils.implode(" ", args);
                t.sendMessage(TextFormat.YELLOW + "[" + p.getName() + " > You] : " + TextFormat.AQUA + msg);
                s.sendMessage(TextFormat.YELLOW + "[You > " + t.getName() + "] : " + TextFormat.AQUA + msg);
                server.getLogger().info(TextFormat.YELLOW + "[" + p.getName() + " > " + t.getName() + "] : " + TextFormat.AQUA + msg);
                p.LastMessageSentTo = t.getName();
                t.LastMessageSentTo = p.getName();
//                server.LastMsg.put(p.getName().toLowerCase(), t.getName().toLowerCase());
//                server.LastMsg.put(t.getName().toLowerCase(), p.getName().toLowerCase());
            } else {
                s.sendMessage(TextFormat.YELLOW + "Usage :/msg <Player> <message>");
                return true;
            }
        } else {
            if (args.length >= 2) {
                CorePlayer t = (CorePlayer)server.getServer().getPlayer(args[0]);
                if (t == null) {
                    s.sendMessage(TextFormat.RED + "Error! Target Player Not Found!");
                    return true;
                }
                String msg = Utils.implode(" ", args);
                t.sendMessage(TextFormat.YELLOW + "[SERVER > You] : " + TextFormat.AQUA + msg);
//                s.sendMessage(TextFormat.YELLOW + "[You > " + t.getName() + "] : " + TextFormat.AQUA + msg);
                server.getLogger().info(TextFormat.YELLOW + "[SERVER > " + t.getName() + "] : " + TextFormat.AQUA + msg);
                server.LastMsg.put( t.getName().toLowerCase(),"SERVER");
                t.LastMessageSentTo = "SERVER";
                return true;
            } else {
                s.sendMessage(TextFormat.YELLOW + "Usage :/msg <Player> <message>");
                return true;
            }
        }
        return true;
    }
}