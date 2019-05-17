package net.yungtechboy1.CyberCore.Commands.Constructors;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.Messages;
import net.yungtechboy1.CyberCore.Rank.Rank;
import net.yungtechboy1.CyberCore.Rank.RankList;

/**
 * Created by carlt_000 on 1/20/2017.
 */
public class CheckPermCommand extends Command {

    public CyberCoreMain Owner;
    public RankList MinRank;
    public String Error = null;
    public CommandSender CS;
    public CheckPermCommand(CyberCoreMain server, String cmd, String desc, String usage, RankList minrank){
        super(cmd,desc,usage);
        Owner = server;
        MinRank = minrank;
        this.setPermission("CyberTech.CyberCore.player");
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        CS = commandSender;
        if(MinRank.getID() > CheckPerms(commandSender))return !SendError(Messages.NO_PERM);
        Error = null;
        return true;
    }

    public int CheckPerms(CommandSender s) {
        if (s instanceof ConsoleCommandSender) {
            return RankList.PERM_SERVER;
        } else if (s instanceof CorePlayer) {
            Rank r = ((CorePlayer)s).GetRank();
            if(r != null)return r.getId();
            return Owner.GetPlayerRankInt((Player) s, true);
        }
        return RankList.PERM_GUEST.getID();
    }

    public boolean SendError(){
        return SendError(null);
    }

    public boolean SendError(String error){
        if(error != null){
            Error = error;
            CS.sendMessage(CyberCoreMain.NAME +" "+error);
            return true;
        }else if(Error != null){
            CS.sendMessage(CyberCoreMain.NAME +" "+Error);
            return true;
        }
        return false;
    }
}
