package net.yungtechboy1.CyberCore.Commands;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.item.EntityPrimedTNT;
import cn.nukkit.level.Position;
import cn.nukkit.level.Sound;
import cn.nukkit.math.NukkitRandom;
import cn.nukkit.math.Vector3;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.DoubleTag;
import cn.nukkit.nbt.tag.FloatTag;
import cn.nukkit.nbt.tag.ListTag;
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

            TNTSpecialist ts = (TNTSpecialist)c;
            TNTSpecialistPower tsp = (TNTSpecialistPower) ts.GetPower(Power.TNT_Specialist);
            if(tsp == null){
                commandSender.sendMessage(CyberCoreMain.NAME + TextFormat.RED + "Error! GETTING POWER");
                return true;
            }
            if(ts.TryRunPower(Power.TNT_Specialist)){
                tsp.
            }
            double mot = (double) (new NukkitRandom()).nextSignedFloat() * 3.141592653589793D * 5.0D;//Was 2
            CompoundTag nbt = (new CompoundTag()).putList((new ListTag("Pos")).add(new DoubleTag("", p.x + 0.5D)).add(new DoubleTag("", p.y)).add(new DoubleTag("", p.z + 0.5D))).putList(new ListTag<DoubleTag>("Motion")
                    .add(new DoubleTag("", -Math.sin(p.yaw / 180 * Math.PI) * Math.cos(p.pitch / 180 * Math.PI)))
                    .add(new DoubleTag("", -Math.sin(p.pitch / 180 * Math.PI)))
                    .add(new DoubleTag("", Math.cos(p.yaw / 180 * Math.PI) * Math.cos(p.pitch / 180 * Math.PI)))).putList((new ListTag("Rotation")).add(new FloatTag("", 0.0F)).add(new FloatTag("", 0.0F))).putShort("Fuse", ts.getFuse());
            Entity tnt = new EntityPrimedTNT(p.getLevel().getChunk(p.getFloorX() >> 4, p.getFloorZ() >> 4), nbt, p);
            tnt.setMotion(tnt.getMotion().multiply(4));
            tnt.spawnToAll();
            p.level.addSound(p, Sound.RANDOM_FUSE);

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
