package net.yungtechboy1.CyberCore.Abilities;

import net.yungtechboy1.CyberCore.Classes.Old.BaseClass;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import cn.nukkit.event.block.BlockBreakEvent;

/**
 * Created by carlt_000 on 1/26/2017.
 */
public class Tree_Feller extends Ability {
    public Tree_Feller(CyberCoreMain main, BaseClass bc){
        super(main,bc, BaseClass.TYPE_LUMBERJACK, LUMBERJACK_TREE_FELLER);
    }


    //@TODO THIS!
    @Override
    public boolean activate(){
        BC.getPlayer().sendMessage("Comming Soon!");
        return false;
        //@TODO
        //CCM.getServer().getScheduler().scheduleDelayedTask(this,new Tree_Feller_Async(CCM,));
        //return true;
    }


    @Override
    public int GetCooldown() {
        return getTime() + 240;
    }

    @Override
    public void deactivate() {
        super.deactivate();
    }

    @Override
    public void onRun(int i) {

    }

    @Override
    public BlockBreakEvent BlockBreakEvent(BlockBreakEvent event) {

        return event;
    }

    @Override
    public String getName() {
        return "Tree Feller";
    }

    @Override
    public void PrimeEvent() {
        String msg = "This Ability Is Coming Soon!";
        BC.getPlayer().sendMessage(msg);
    }
}
