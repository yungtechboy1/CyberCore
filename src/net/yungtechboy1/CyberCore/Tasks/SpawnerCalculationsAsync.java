package net.yungtechboy1.CyberCore.Tasks;

import net.yungtechboy1.CyberCore.entities.block.BlockEntitySpawner;
import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.blockentity.BlockEntity;
import cn.nukkit.entity.Entity;
import cn.nukkit.level.Level;
import cn.nukkit.scheduler.AsyncTask;

import java.util.ArrayList;

/**
 * Created by carlt_000 on 2/8/2017.
 */
public class SpawnerCalculationsAsync extends AsyncTask{
    Entity[] Entities;
    BlockEntitySpawner Me;
    int requiredPlayerRange = 32;
    int maxNearbyEntities = 6;
    ArrayList<Entity> list = new ArrayList<>();
    public SpawnerCalculationsAsync(Entity[] entities, BlockEntitySpawner me, int requiredPlayerRange, int maxNearbyEntities){
        Entities = entities;
        Me = me;
        requiredPlayerRange = 32;
    }

    @Override
    public void onRun() {
        boolean isValid = false;
        for(Entity entity : Entities){
            if(entity.distance(Me) <= this.requiredPlayerRange){
                if(entity instanceof Player){
                    isValid = true;
                }
                list.add(entity);
            }
        }
        if(!isValid)list.clear();
    }

    @Override
    public void onCompletion(Server server) {
        Level lvl = server.getLevel(Me.getLevel().getId());
        if(lvl != null)return;
        BlockEntity be = lvl.getBlockEntity(Me);
        if(be != null && be instanceof BlockEntitySpawner){
            ((BlockEntitySpawner) be).afterUpdate(list);
            ((BlockEntitySpawner) be).wait = false;
        }
    }
}
