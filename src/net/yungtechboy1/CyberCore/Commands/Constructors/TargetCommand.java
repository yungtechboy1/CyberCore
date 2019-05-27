package net.yungtechboy1.CyberCore.Commands.Constructors;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.Messages;
import net.yungtechboy1.CyberCore.Rank.RankList;

/**
 * Created by carlt_000 on 1/21/2017.
 */
public class TargetCommand extends CheckPermCommand {
    public int TargetKey;
    public Player Target;
    public boolean CBE = true;
    public boolean CT = true;//Check Target
    public boolean OT = true;//Optional Target

    public TargetCommand(CyberCoreMain server, String cmd, String desc, String usage, int minrank, int targetKey, boolean canbeequal, boolean checktarget) {
        super(server, cmd, desc,usage, RankList.GetRankFromInt(minrank));
        TargetKey = targetKey;
        CBE = canbeequal;
        CT = checktarget;
    }
    public TargetCommand(CyberCoreMain server, String cmd, String desc, String usage, RankList minrank, int targetKey, boolean canbeequal, boolean checktarget) {
        super(server, cmd, desc,usage, minrank);
        TargetKey = targetKey;
        CBE = canbeequal;
        CT = checktarget;
    }

    public TargetCommand(CyberCoreMain server, String cmd, String desc, String usage, int minrank, int targetKey, boolean canbeequal) {
        this(server, cmd, desc,usage,minrank, targetKey, canbeequal, true);
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        //DO THIS AT THE BEGINGING!
        Error = null;
        if (!super.execute(commandSender, s, strings)) return SendError();
        if (CT) {
            Target = GetTarget(strings);
            if (Target == null){
                //No Target! No Target to find cuz no target args was there
                if(strings.length - 1 < TargetKey && OT)return true;
                return SendError();
            }
            int tr = CheckPerms(Target);
            int sr = CheckPerms(commandSender);
            if (tr > sr) return SendError(Messages.TARGET_TOO_HIGH);
            if (tr == sr && !CBE) return SendError(Messages.RANK_CAN_NOT_BE_EQUAL);
        }
        return true;
    }

    public Player GetTarget(String[] args) {
        Error = Messages.INVALID_COMMAND_SYNTAX;
        if (args.length - 1 < TargetKey) return null;
        String t = args[TargetKey];
        Error = Messages.TARGET_NOT_FOUND;
        return Owner.getServer().getPlayer(t);
    }
}
