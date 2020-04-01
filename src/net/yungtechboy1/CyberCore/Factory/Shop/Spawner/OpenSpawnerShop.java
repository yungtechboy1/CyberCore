package net.yungtechboy1.CyberCore.Factory.Shop.Spawner;

import cn.nukkit.item.ItemDiamond;
import cn.nukkit.scheduler.Task;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;

public class OpenSpawnerShop extends Task implements Runnable {
    CorePlayer P;
    SpawnerShop B;
    public OpenSpawnerShop(CorePlayer p, SpawnerShop b) {
        P = p;
        B = b;
    }

    @Override
    public void onRun(int i) {
//        B.addItem(new ItemBook());
        CyberCoreMain.getInstance().getLogger().info(B.getContents().values().size()+" < SIZZEEE"+B.getSize());
        P.addWindow(B);
        P.SpawnerShop = B;
//            System.out.println("------------------------------------------------!!!!!!!!!!");
        B.sendContents(P);
        B.SendAllSlots(P);
        B.addItem(new ItemDiamond(0,10));
        CyberCoreMain.getInstance().getLogger().info(B.getContents().values().size()+" < SIZZEEE"+B.getSize());
//            B.sendContents(P);
    }
}
