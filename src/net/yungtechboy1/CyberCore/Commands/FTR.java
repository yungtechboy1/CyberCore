package net.yungtechboy1.CyberCore.Commands;


import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.level.sound.ExperienceOrbSound;
import cn.nukkit.network.protocol.RemoveEntityPacket;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.Abilities.Ability;
import net.yungtechboy1.CyberCore.Classes.BaseClass;
import net.yungtechboy1.CyberCore.CyberCoreMain;

import java.io.File;
import java.security.acl.Owner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by carlt_000 on 1/30/2017.
 */
public class FTR extends Command {
    CyberCoreMain Owner;

    public FTR(CyberCoreMain server) {
        super("ftr", "Reload Floating Text", "/ftr");
        Owner = server;
        this.commandParameters.clear();
        this.setPermission("CyberTech.CyberCore.op");
    }

    @Override
    public boolean execute(CommandSender s, String label, String[] args) {
        for(Long eid: Owner.FTM.Eids){
            RemoveEntityPacket pk = new RemoveEntityPacket();
            pk.eid = eid;
            pk.encode();
            Server.broadcastPacket(Owner.getServer().getOnlinePlayers().values(),pk);
        }

        Owner.FTM.Eids = new ArrayList<>();

        for(Map.Entry<UUID,Player> eee: Owner.getServer().getOnlinePlayers().entrySet()) {
            for(Map.Entry<String,Long> e: Owner.FTM.EV3.entrySet()) {
                Long eid = e.getValue();
                RemoveEntityPacket pk = new RemoveEntityPacket();
                pk.eid = eid;
                pk.encode();
                Server.broadcastPacket(Owner.getServer().getOnlinePlayers().values(),pk);
            }
        }
        Owner.FTM.EV3 = new HashMap<>();

        for(Map.Entry<UUID,Player> eee: Owner.getServer().getOnlinePlayers().entrySet()) {
            for(Long eid: Owner.FTM.DEids){
                RemoveEntityPacket pk = new RemoveEntityPacket();
                pk.eid = eid;
                pk.encode();
                Server.broadcastPacket(Owner.getServer().getOnlinePlayers().values(),pk);
            }
        }

        Owner.FTM.DEids = new ArrayList<>();
        Owner.FTM.MainConfig = new Config(new File(Owner.getDataFolder(), "ftconfig.yml"), Config.YAML);
        Owner.FTM.ReloadDynamicText();


        return true;
    }
}