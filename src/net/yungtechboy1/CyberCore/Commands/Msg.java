package net.yungtechboy1.CyberCore.Commands;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.Main;

/**
 * Created by carlt_000 on 3/21/2016.
 */

public class Msg extends Command {
    Main Owner;

    public Msg(Main server) {
        super("msg", "Send messages between players", "/msg <player> <msg>", new String[]{"message", "tell"});
        Owner = server;
        this.commandParameters.clear();
        this.commandParameters.put("default", new CommandParameter[]{
                new CommandParameter("player", CommandParameter.ARG_TYPE_TARGET, false),
                new CommandParameter("message", CommandParameter.ARG_TYPE_RAW_TEXT, false)
        });
    }

    //@TODO Move to Utills
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

    @Override
    public boolean execute(CommandSender s, String label, String[] args) {
        Main server = Owner;
        if (s instanceof Player) {
            Player p = (Player) s;
            if (args.length >= 2) {
                Player t = server.getServer().getPlayer(args[0]);
                if (t == null) {
                    if (args[0].equalsIgnoreCase("SERVER")) {

                        String msg = implode(" ", args);
                        s.sendMessage(TextFormat.YELLOW + "[You > SERVER] : " + TextFormat.AQUA + msg);
                        server.getLogger().info(TextFormat.YELLOW + "[" + p.getName() + " > You/Server ] : " + TextFormat.AQUA + msg);

                        server.LastMsg.put("SERVER", s.getName().toLowerCase());
                        server.LastMsg.put(s.getName().toLowerCase(), "SERVER");
                        return true;
                    }
                    s.sendMessage(TextFormat.RED + "Error! Target Player Not Found!");
                    return true;
                }
                String msg = implode(" ", args);
                t.sendMessage(TextFormat.YELLOW + "[" + p.getName() + " > You] : " + TextFormat.AQUA + msg);
                s.sendMessage(TextFormat.YELLOW + "[You > " + t.getName() + "] : " + TextFormat.AQUA + msg);
                server.getLogger().info(TextFormat.YELLOW + "[" + p.getName() + " > " + t.getName() + "] : " + TextFormat.AQUA + msg);
                server.LastMsg.put(p.getName().toLowerCase(), t.getName().toLowerCase());
                server.LastMsg.put(t.getName().toLowerCase(), p.getName().toLowerCase());
            } else {
                s.sendMessage(TextFormat.YELLOW + "Usage :/msg <Player> <message>");
                return true;
            }
        } else {
            if (args.length >= 2) {
                Player t = server.getServer().getPlayer(args[0]);
                if (t == null) {
                    s.sendMessage(TextFormat.RED + "Error! Target Player Not Found!");
                    return true;
                }
                String msg = implode(" ", args);
                t.sendMessage(TextFormat.YELLOW + "[SERVER > You] : " + TextFormat.AQUA + msg);
                s.sendMessage(TextFormat.YELLOW + "[You > " + t.getName() + "] : " + TextFormat.AQUA + msg);
                server.getLogger().info(TextFormat.YELLOW + "[SERVER > " + t.getName() + "] : " + TextFormat.AQUA + msg);
                server.LastMsg.put("SERVER", t.getName().toLowerCase());
                server.LastMsg.put(t.getName().toLowerCase(), "SERVER");
                return true;
            } else {
                s.sendMessage(TextFormat.YELLOW + "Usage :/msg <Player> <message>");
                return true;
            }
        }
        return true;
    }
}