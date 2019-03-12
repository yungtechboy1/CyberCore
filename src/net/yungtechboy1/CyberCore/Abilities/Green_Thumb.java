package net.yungtechboy1.CyberCore.Abilities;

import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.inventory.PlayerInventory;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemTool;
import cn.nukkit.item.enchantment.Enchantment;
import net.yungtechboy1.CyberCore.Classes.Old.BaseClass;
import net.yungtechboy1.CyberCore.CyberCoreMain;

/**
 * Created by carlt_000 on 1/27/2017.
 */
public class Green_Thumb extends Ability {
    public Green_Thumb(CyberCoreMain main, BaseClass bc){
        super(main,bc, BaseClass.TYPE_FARMER,Ability.FARMER_GREEN_THUMB);
    }
    //@TODO not doing this???

    @Override
    public boolean activate(){
        Item hand = player.getInventory().getItemInHand();
        if(!(hand instanceof ItemTool))return false;
        afterhand = hand.clone();
        firsthand = hand.clone();
        Enchantment e = Enchantment.get(Enchantment.ID_EFFICIENCY);
        e.setLevel(5);
        afterhand.addEnchantment(e);
        player.getInventory().setItemInHand(afterhand);
        CCM.getServer().getScheduler().scheduleDelayedTask(this,LevelToSec()*20);
        player.sendMessage("Ability activated for "+LevelToSec());
        return true;
    }



    @Override
    public int GetCooldown() {
        return getTime() + 240;
    }

    @Override
    public void deactivate() {
        super.deactivate();
        player.sendMessage("ON DISS!!!!! ");
        PlayerInventory pi = BC.getPlayer().getInventory();
        for(int x = 0; x < pi.getSize()-1; x++){
            Item i = pi.getItem(x);
            if (i.deepEquals(afterhand,true,true)){
                pi.setItem(x,firsthand);
                BC.setActiveAbility(null);
                return;
            }
        }
    }

    @Override
    public void onRun(int i) {
        deactivate();
    }

    @Override
    public void BlockBreakEvent(BlockBreakEvent event) {

    }

    @Override
    public void PrimeEvent() {
        String msg = "Use an Axe and tap on a block to activate this ability!";
        BC.getPlayer().sendMessage(msg);
    }
}