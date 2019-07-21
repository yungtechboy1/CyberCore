package net.yungtechboy1.CyberCore.Commands;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.Commands.Constructors.CheckPermCommand;
import net.yungtechboy1.CyberCore.Commands.Constructors.TargetCommand;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.Manager.Form.Windows.AdminMainWindow;
import net.yungtechboy1.CyberCore.Messages;
import net.yungtechboy1.CyberCore.Rank.RankList;

public class AdminCMD extends CheckPermCommand {

    public AdminCMD(CyberCoreMain server) {
        super(server,"admin","Open Admin Panel","/admin", RankList.PERM_GUEST);
        this.commandParameters.clear();
        this.commandParameters.put("default", new CommandParameter[]{
//                new CommandParameter("player", CommandParamType.TARGET, true)
        });
    }

    @Override
    public boolean execute(CommandSender s, String str, String[] args) {
        if (!super.execute(s, str, args)) return SendError();
        Error = null;
        if (s instanceof Player) {
            CorePlayer p = (CorePlayer) s;
            p.showFormWindow(new AdminMainWindow());
        }
        return true;
    }
}