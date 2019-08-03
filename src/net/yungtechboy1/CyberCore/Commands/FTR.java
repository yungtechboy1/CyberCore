package net.yungtechboy1.CyberCore.Commands;


import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.Manager.FT.FloatingTextFactory;

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
        FloatingTextFactory.killall();
        Owner.loadFloatingText();
//        for(Long eid: Owner.FTM.Eids){
//            RemoveEntityPacket pk = new RemoveEntityPacket();
//            pk.eid = eid;
//            pk.encode();
//            Server.broadcastPacket(Owner.getServer().getOnlinePlayers().values(),pk);
//        }
//
//        Owner.FTM.Eids = new ArrayList<>();
//
//        for(Map.Entry<UUID,Player> eee: Owner.getServer().getOnlinePlayers().entrySet()) {
//            for(Map.Entry<String,Long> e: Owner.FTM.EV3.entrySet()) {
//                Long eid = e.getValue();
//                RemoveEntityPacket pk = new RemoveEntityPacket();
//                pk.eid = eid;
//                pk.encode();
//                Server.broadcastPacket(Owner.getServer().getOnlinePlayers().values(),pk);
//            }
//        }
//        Owner.FTM.EV3 = new HashMap<>();
//
//        for(Map.Entry<UUID,Player> eee: Owner.getServer().getOnlinePlayers().entrySet()) {
//            for(Long eid: Owner.FTM.DEids){
//                RemoveEntityPacket pk = new RemoveEntityPacket();
//                pk.eid = eid;
//                pk.encode();
//                Server.broadcastPacket(Owner.getServer().getOnlinePlayers().values(),pk);
//            }
//        }
//
//        Owner.FTM.DEids = new ArrayList<>();
//        Owner.FTM.MainConfig = new Config(new File(Owner.getDataFolder(), "ftconfig.yml"), Config.YAML);
//        Owner.FTM.ReloadDynamicText();


        return true;
    }
}