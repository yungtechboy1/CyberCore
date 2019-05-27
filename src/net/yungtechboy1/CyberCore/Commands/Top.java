package net.yungtechboy1.CyberCore.Commands;

import cn.nukkit.command.data.CommandParamType;
import net.yungtechboy1.CyberCore.Commands.Constructors.CheckPermCommand;
import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.level.Position;
import cn.nukkit.math.Vector3;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.Messages;
import net.yungtechboy1.CyberCore.Rank.RankList;
import net.yungtechboy1.CyberCore.Utils;

import java.util.Calendar;

/**
 * Created by carlt_000 on 3/21/2016.
 */

public class Top extends CheckPermCommand {
    private Vector3 temporalVector = new Vector3();

    public Top(CyberCoreMain server) {
        super(server, "top", "Teleport to Top Block", "/top", RankList.PERM_OP);
        this.commandParameters.clear();
        this.commandParameters.put("default", new CommandParameter[]{
                new CommandParameter("player", CommandParamType.TARGET, true)
        });
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (!super.execute(commandSender, s, strings)) return SendError();
        if (commandSender instanceof Player) {
            int S = Owner.GetPlayerRankInt((Player) commandSender, true);
            if (S == 0 && !commandSender.isOp()) {
                commandSender.sendMessage(CyberCoreMain.NAME + TextFormat.RED + "Error! You don't have access to this command!");
                return true;
            }
            //Check Cooldown
            Boolean skip = (CheckPerms(commandSender) >= RankList.PERM_ADMIN_3);
            CompoundTag nt = ((Player) commandSender).namedTag;
            if (skip) {
                if (nt != null) {
                    Integer time = nt.getInt("CCTop");
                    Integer ct = (int) (Calendar.getInstance().getTime().getTime() / 1000);
                    //Check time
                    if (ct < time) {
                        String diff = Utils.getDifferenceBtwTime((long) time);
                        commandSender.sendMessage(CyberCoreMain.NAME + TextFormat.RED + "Error! You must wait " + diff);
                        return true;
                    }
                }
            }

            gototp((Player) commandSender);

            if (skip) {
                Integer q = 10;
                if (S == 1) q = 60 * 5;
                if (S == 2) q = 60 * 2;
                Integer ct = (int) (Calendar.getInstance().getTime().getTime() / 1000) + q;
                Owner.cooldowns.set("top." + commandSender.getName().toLowerCase(), ct);
            }
            if (skip) {
                Integer q = 60;
                if (S == 1) q = 60 * 60 * 12;
                if (S == 2) q = 60 * 60 * 8;
                if (S == 3) q = 60 * 60 * 4;
                if (S == 4) q = 60 * 60;
                Integer ct = (int) (Calendar.getInstance().getTime().getTime() / 1000) + q;
                if (nt != null) nt.putInt("CCTop", ct);
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

    public void gototp(Player s) {
        Position pos = getHighestStandablePositionAt(s.getPosition());
        if (pos == null) {
            s.sendMessage(CyberCoreMain.NAME + TextFormat.RED + "Error! Could not teleport to top!");
        } else {
            s.teleport(pos);
            s.sendMessage(CyberCoreMain.NAME + TextFormat.GREEN + "Teleport to top!");
        }

    }
}