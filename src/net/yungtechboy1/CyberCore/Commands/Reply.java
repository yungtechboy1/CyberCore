package net.yungtechboy1.CyberCore.Commands;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;

/**
 * Created by carlt_000 on 3/21/2016.
 */

public class Reply extends Command{
    CyberCoreMain Owner;

    public Reply(CyberCoreMain server) {
        super("reply", "Quickly Reply to messages", "/r <msg>", new String[]{"r"});
        Owner = server;
        this.commandParameters.clear();
        this.commandParameters.put("default", new CommandParameter[]{
                new CommandParameter("message", CommandParamType.RAWTEXT, false)
        });
    }

    @Override
    public boolean execute(CommandSender s, String label, String[] args) {
        CyberCoreMain server = Owner;
        if (s instanceof CorePlayer) {
            CorePlayer p = (CorePlayer) s;
            if (args.length >= 1 && server.LastMsg.containsKey(p.getName().toLowerCase()) || p.LastMessageSentTo != null) {
                String a = server.LastMsg.get(p.getName().toLowerCase());
                CorePlayer t = (CorePlayer)server.getServer().getPlayer(a);
                if(t == null)t = (CorePlayer)server.getServer().getPlayerExact(p.LastMessageSentTo);
                if (t == null) {
                    if (a.equalsIgnoreCase("SERVER")) {
                        String msg = implode(" ", args);
                        s.sendMessage(TextFormat.YELLOW + "[You > SERVER] : " + TextFormat.AQUA + msg);
                        server.getLogger().info(TextFormat.YELLOW + "[" + p.getName() + " > You/Server ]  : " + TextFormat.AQUA + msg);
                        server.LastMsg.put("SERVER", s.getName().toLowerCase());
                        server.LastMsg.put(s.getName().toLowerCase(), "SERVER");
                        return true;
                    }
                    s.sendMessage(TextFormat.RED + "Error! Target Player Not Found!");
                    return true;
                }
                String msg = "";
                for (String aa : args) {
                    msg = msg + " " + aa;
                }
                t.sendMessage(TextFormat.YELLOW + "[" + p.getName() + " > You] : " + TextFormat.AQUA + msg);
                p.sendMessage(TextFormat.YELLOW + "[You > " + t.getName() + "] : " + TextFormat.AQUA + msg);
                server.getLogger().info(TextFormat.YELLOW + "[" + p.getName() + " > " + t.getName() + "] : " + TextFormat.AQUA + msg);
                p.LastMessageSentTo = t.getName();
                t.LastMessageSentTo = p.getName();
//                server.LastMsg.put(p.getName().toLowerCase(), t.getName().toLowerCase());
//                server.LastMsg.put(t.getName().toLowerCase(), p.getName().toLowerCase());
            } else {
                s.sendMessage(TextFormat.YELLOW + "Usage :/r <message>");
                return true;
            }
        }
        return true;
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