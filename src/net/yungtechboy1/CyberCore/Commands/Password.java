package net.yungtechboy1.CyberCore.Commands;

import net.yungtechboy1.CyberCore.Commands.Constructors.CheckPermCommand;
import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.RankList;

import java.sql.Statement;

/**
 * Created by carlt_000 on 2/18/2017.
 */
public class Password extends CheckPermCommand {

    public Password(CyberCoreMain server) {
        super(server, "password", "Edits Passwords", "/password", RankList.PERM_ADMIN_1);
        this.commandParameters.clear();
        this.commandParameters.put("default", new CommandParameter[]{
                new CommandParameter("player", CommandParameter.ARG_TYPE_TARGET, false),
                new CommandParameter("reset", CommandParameter.ARG_TYPE_INT, false)
        });
        this.commandParameters.put("setpassword", new CommandParameter[]{
                new CommandParameter("player", CommandParameter.ARG_TYPE_TARGET, false),
                new CommandParameter("set", CommandParameter.ARG_TYPE_INT, false),
                new CommandParameter("password", CommandParameter.ARG_TYPE_STRING, false)
        });
    }

    @Override
    public boolean execute(CommandSender s, String str, String[] args) {
        if (!super.execute(s, str, args)) return SendError();
        if (args[1].equalsIgnoreCase("reset")) {
            if (Owner.PasswordFactoy.Passwords.containsKey(args[0].toLowerCase())) {
                Owner.PasswordFactoy.Passwords.remove(args[0].toLowerCase());
            }
            try {
                Statement stmt = Owner.PasswordFactoy.getMySqlConnection().createStatement();
                stmt.executeUpdate("DELETE FROM `password` WHERE `player` = '" + args[0].toLowerCase() + "';");
            } catch (Exception ex) {
                Owner.getServer().getLogger().info(ex.getClass().getName() + "8888 " + ex.getMessage());
            }
            s.sendMessage("Password Reset!");
            Player target = Owner.getServer().getPlayerExact(args[0]);
            if (target != null)target.sendMessage(TextFormat.GREEN + "Password Reset!");
        }
        return true;
    }
}

