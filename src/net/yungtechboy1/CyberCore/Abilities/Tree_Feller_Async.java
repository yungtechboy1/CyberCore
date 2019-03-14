package net.yungtechboy1.CyberCore.Abilities;

import cn.nukkit.Server;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockAir;
import cn.nukkit.block.BlockLeaves;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemShears;
import cn.nukkit.level.Level;
import cn.nukkit.scheduler.AsyncTask;
import net.yungtechboy1.CyberCore.CyberCoreMain;

import java.util.ArrayList;

/**
 * Created by carlt_000 on 1/26/2017.
 */
public class Tree_Feller_Async extends AsyncTask {
    public int check = 0;
    ArrayList<String> visited = new ArrayList<>();
    ArrayList<Block>  BlockList = new ArrayList<>();
    CyberCoreMain CCM;
    Block POS;
    public Tree_Feller_Async(CyberCoreMain ccm, Block pos) {
        CCM = ccm;
        POS = pos;
    }

    @Override
    public void onRun() {
        findLog(POS,0,0);
    }

    @Override
    public void onCompletion(Server server) {
        super.onCompletion(server);
        Level world = server.getLevelByName(POS.getLevel().getName());
        if(world != null){
            for(Block block: BlockList){
                if(block instanceof BlockLeaves){
                    Item[] d = block.getDrops(new ItemShears());
                    for (int i = 0; i < d.length; i++) {
                        Item drop = d[i];
                        world.setBlock(block, new BlockAir(),true,true);
                        world.dropItem(block.add(0.5,0.5,0.5),drop);
                        try {
                            wait(50);
                        }catch (Exception ex){
                        }
                    }
                }
            }
        }

    }

    private void findLog(Block pos, Integer distance, Integer check) {
        findLog(pos, distance, check, null);
    }

    private void findLog(Block pos, Integer distance, Integer check, Integer fromSide) {
        ++check;
        String index = pos.x + "." + pos.y + "." + pos.z;
        if (visited.contains(index)) return;
        if ((pos.getId() == Block.WOOD || pos.getId() == Block.LEAVES) && distance < 10) {
            visited.add(index);
            BlockList.add(pos);
            //No more loops
            this.findLog(pos.up(), distance + 1, check, fromSide);
            this.findLog(pos.down(), distance + 1, check, fromSide);
            this.findLog(pos.north(), distance + 1, check, fromSide);
            this.findLog(pos.south(), distance + 1, check, fromSide);
            this.findLog(pos.east(), distance + 1, check, fromSide);
            this.findLog(pos.west(), distance + 1, check, fromSide);
            this.findLog(pos.down(), distance + 1, check, fromSide);
        }
    }
}
