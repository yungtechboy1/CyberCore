package net.yungtechboy1.CyberCore.Abilities;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockLeaves;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemGunpowder;
import cn.nukkit.level.particle.FlameParticle;
import cn.nukkit.level.particle.Particle;
import cn.nukkit.level.sound.BlazeShootSound;
import cn.nukkit.math.NukkitRandom;
import cn.nukkit.math.Vector3;
import cn.nukkit.network.protocol.DataPacket;
import cn.nukkit.network.protocol.LevelEventPacket;
import cn.nukkit.scheduler.AsyncTask;
import net.yungtechboy1.CyberCore.Classes.BaseClass;
import net.yungtechboy1.CyberCore.CyberCoreMain;

import java.util.ArrayList;

/**
 * Created by carlt_000 on 1/27/2017.
 */
public class ForestFire extends Ability {
    public ForestFire(CyberCoreMain main,BaseClass bc){
        super(main,bc, BaseClass.TYPE_LUMBERJACK,Ability.LUMBERJACK_FORESTFIRE);
    }

    @Override
    public boolean activate() {
        ArrayList<Block> affected = new ArrayList<>();
        int size = (int) Math.ceil((BC.getLVL() / 10) + 5);
        for (int x = -size; x < size; x++) {
            for (int y = -size; y < size; y++) {
                for (int z = -size; z < size; z++) {
                    Block b = player.getLevel().getBlock(new Vector3(x,y,z));
                    if(b != null && (b.getId() == Block.WOOD||b.getId() == Block.WOOD2||b.getId() == Block.LEAVE||b.getId() == Block.LEAVE2)){
                        affected.add(b);
                    }
                }
            }
        }

        int a = 0;
        for(Block b: affected){
            int rand = new NukkitRandom(a++ + 3129330342L).nextRange(0,100);
            if(b.getId() == Block.WOOD||b.getId() == Block.WOOD2){
                if(rand > 50){//chared
                    player.getLevel().dropItem(b.add(.5,.5,.5), Item.get(Item.COAL,b.getDamage(),1));
                }else{
                    player.getLevel().dropItem(b.add(.5,.5,.5), Item.get(b.getId(),b.getDamage(),1));
                }
            }else if(b instanceof BlockLeaves){
                int[][] drops = b.getDrops(new ItemGunpowder());
                if(drops[0][0] == Item.APPLE && rand < (4+(BC.getLVL()/15))){//GAPPLE
                    drops[0][0] = Item.GOLDEN_APPLE;
                }
                player.getLevel().dropItem(b.add(.5,.5,.5), Item.get(drops[0][0],drops[0][1],1));
            }
            player.getLevel().addParticle(new FlameParticle(b));
        }
        player.getLevel().addSound(new BlazeShootSound(player));
        return true;
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
    public void BlockBreakEvent(BlockBreakEvent event) {

    }
}
