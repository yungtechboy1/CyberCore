package net.yungtechboy1.CyberCore.Manager.Crate;

import cn.nukkit.block.Block;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.item.Item;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.Manager.Crate.Tasks.CloseCrate;


public class CrateListener implements Listener {
    private CyberCoreMain main;
    public CrateListener(CyberCoreMain main){
        this.main = main;
    }


//    @EventHandler
//    public void onHit(PlayerInteractEvent ev){
//        Block b = ev.getBlock();
//        if (b.getId() == 54){
//            ev.setCancelled();
//            Item i = ev.getItem();
//            if (i.getId() == 347 && ev.getPlayer().isOp()){
//
//            }
//            if (i.getNamedTag().getBoolean("ItemKey1")){
//                main.CrateMain.showCrate(b,ev.getPlayer());
//                main.getServer().getScheduler().scheduleDelayedTask(new CloseCrate(main,b,ev.getPlayer()), 40);
//            }
//        }
//    }
}


