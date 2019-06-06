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
import net.yungtechboy1.CyberCore.Classes.Power.PowerEnum;
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
        System.out.println(CheckPerms(commandSender));
        if (!super.execute(commandSender, s, strings)) {
            System.out.println("EEE112312 1233312 122");
            return SendError();
        }
        if (commandSender instanceof CorePlayer) {
            System.out.println("CCCCCCCC");
            CorePlayer p = (CorePlayer) commandSender;
            BaseClass c = p.GetPlayerClass();
            if (!(c instanceof TNTSpecialist)) {
                commandSender.sendMessage(CyberCoreMain.NAME + TextFormat.RED + "Error! You don't have access to this command!");
                return true;
            }
            System.out.println("CCCCCCCC");

            TNTSpecialist ts = (TNTSpecialist) c;
            TNTSpecialistPower tsp = (TNTSpecialistPower) ts.getPower(PowerEnum.TNTSpecalist);
            if (tsp == null) {
                commandSender.sendMessage(CyberCoreMain.NAME + TextFormat.RED + "Error! GETTING POWER");
                return true;
            }
            if (ts.TryRunPower(PowerEnum.TNTSpecalist)) {
                System.out.println("CCCCCCCC");
                ts.RunPower(PowerEnum.TNTSpecalist, p);
                System.out.println("CCCCCCCC------");
            }else{
                commandSender.sendMessage("Error! Could not run power!");
            }


        } else {
            commandSender.sendMessage(CyberCoreMain.NAME + Messages.NEED_TO_BE_PLAYER);
        }
        return true;
    }
}

