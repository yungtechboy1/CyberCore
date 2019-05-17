package net.yungtechboy1.CyberCore.Commands;

import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.level.Position;
import cn.nukkit.math.Vector3;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.Classes.New.Minner.TNTSpecialist;
import net.yungtechboy1.CyberCore.Classes.Power.Power;
import net.yungtechboy1.CyberCore.Classes.Power.TNTSpecialistPower;
import net.yungtechboy1.CyberCore.Commands.Constructors.CheckPermCommand;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.Messages;
import net.yungtechboy1.CyberCore.Rank.RankList;

public class TNT extends CheckPermCommand {
    private Vector3 temporalVector = new Vector3();

    public TNT(CyberCoreMain server) {
        super(server, "tnt", "Class command for TNT Specialists", "/tnt", RankList.PERM_GUEST);
        this.commandParameters.clear();
        this.commandParameters.put("default", new CommandParameter[]{
                new CommandParameter("player", CommandParamType.TARGET, true)
        });
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (!super.execute(commandSender, s, strings)) return SendError();
        if (commandSender instanceof CorePlayer) {
            CorePlayer p = (CorePlayer) commandSender;
            BaseClass c = p.GetPlayerClass();
            if (c == null || !(c instanceof TNTSpecialist)) {
                commandSender.sendMessage(CyberCoreMain.NAME + TextFormat.RED + "Error! You don't have access to this command!");
                return true;
            }

            TNTSpecialist ts = (TNTSpecialist) c;
            TNTSpecialistPower tsp = (TNTSpecialistPower) ts.GetPower(Power.TNT_Specialist);
            if (tsp == null) {
                commandSender.sendMessage(CyberCoreMain.NAME + TextFormat.RED + "Error! GETTING POWER");
                return true;
            }
            if (ts.TryRunPower(Power.TNT_Specialist)) {
                ts.RunPower(Power.TNT_Specialist, p);
            }


        } else {
            commandSender.sendMessage(CyberCoreMain.NAME + Messages.NEED_TO_BE_PLAYER);
        }
        return true;
    }

    public Position getHighestStandablePositionAt(Position pos) {
        int x = pos.getFloorX();
        int z = pos.getFloorZ();
        for (int y = 256; y >= 0; y--) {
            if (pos.level.getBlock(this.temporalVector.setComponents(x, y, z)).isSolid()) {
                return new Position(x + 0.5, pos.level.getBlock(this.temporalVector.setComponents(x, y, z)).getBoundingBox().getMaxY(), z + 0.5, pos.level);
            }
        }
        return null;
    }
}

