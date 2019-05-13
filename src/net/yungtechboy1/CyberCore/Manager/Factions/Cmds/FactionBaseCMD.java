package net.yungtechboy1.CyberCore.Manager.Factions.Cmds;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.Messages;

import java.util.ArrayList;

/**
 * Created by carlt on 5/11/2019.
 */
public class FactionBaseCMD extends Command {
    CyberCoreMain Owner;

    public FactionBaseCMD(CyberCoreMain main) {
        super("faction", "Base Faction command", "/faction /f", new String[]{"f"});
        Owner = main;
        commandParameters.clear();
        this.commandParameters.put("create", new CommandParameter[]{
                new CommandParameter("create", false),
        });

    }

    private String[] PushOne(String[] list) {
        ArrayList<String> ss = new ArrayList<String>();
        for (String s : list) ss.add(s);
        ss.remove(0);
        return ss.toArray(new String[ss.size()]);
    }

    @Override
    public boolean execute(CommandSender commandSender, String command, String[] strings) {
        System.out.println("CALLL BBBBB333");
        if (!(commandSender instanceof CorePlayer)) return false;
        System.out.println("CALLL FFFF" + command);
        CorePlayer cp = (CorePlayer) commandSender;
        String key;
        if (strings.length == 0) key = null;
        else key = strings[0];
        String[] args = PushOne(strings);
        if (key != null) {
            switch (key.toLowerCase()) {
                case "accept":
                    new Accept(commandSender,args,Owner.FM);
                    break;
                case "create":
                    new Create(commandSender, args, Owner.FM);
                    break;
                case "admin":
                case "a":
                    new Admin(commandSender, args, Owner.FM);
                    break;
                case "inv":
                case "invite":
                    new Invite(commandSender, args, Owner.FM);
                    break;
            }
        }else{

        }
        return true;
    }
}