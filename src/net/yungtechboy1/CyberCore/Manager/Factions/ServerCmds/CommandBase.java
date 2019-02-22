package net.yungtechboy1.CyberCore.Manager.Factions.ServerCmds;


import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.lang.TranslationContainer;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;

public abstract class CommandBase extends Command {

    protected FactionsMain main;

    public CommandBase(String name, String desc, String usage, FactionsMain api) {
        super(name, desc, usage);
        main = api;
    }

}