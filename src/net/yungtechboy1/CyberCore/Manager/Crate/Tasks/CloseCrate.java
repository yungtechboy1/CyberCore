package net.yungtechboy1.CyberCore.Manager.Crate.Tasks;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.scheduler.PluginTask;
import net.yungtechboy1.CyberCore.CyberCoreMain;

public class CloseCrate extends PluginTask {
    private CyberCoreMain main;
    private Player p;
    private Block b;


    public CloseCrate(CyberCoreMain main, Block b, Player p ){
        super(main);
        this.main = main;
        this.p = p;
        this.b = b;
    }

    public void onRun(int currentTick){
        main.CrateMain.hideCrate(b,p);
    }

}
