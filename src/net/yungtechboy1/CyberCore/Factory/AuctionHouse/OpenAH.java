package net.yungtechboy1.CyberCore.Factory.AuctionHouse;

import cn.nukkit.Player;
import cn.nukkit.inventory.Inventory;
import cn.nukkit.inventory.InventoryHolder;
import cn.nukkit.item.ItemBook;
import cn.nukkit.scheduler.Task;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.Custom.Inventory.AuctionHouse;
import net.yungtechboy1.CyberCore.CyberCoreMain;

/**
 * Created by carlt on 4/28/2019.
 */
public class OpenAH extends Task implements Runnable {
    Player P;
    Inventory B;
    public OpenAH(CorePlayer p, AuctionHouse b) {
    P = p;
    B = b;
    }

    @Override
    public void onRun(int i) {
//        B.addItem(new ItemBook());
        CyberCoreMain.getInstance().getLogger().info(B.getContents().values().size()+" < SIZZEEE"+B.getSize());
            P.addWindow(B);
        CyberCoreMain.getInstance().getLogger().info(B.getContents().values().size()+" < SIZZEEE"+B.getSize());
//            B.sendContents(P);
    }
}
