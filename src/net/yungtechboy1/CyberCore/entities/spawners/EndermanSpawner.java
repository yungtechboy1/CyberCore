/**
 * CreeperSpawner.java
 * 
 * Created on 10:39:49
 */
package net.yungtechboy1.CyberCore.entities.spawners;

import net.yungtechboy1.CyberCore.MobAI.AutoSpawnTask;
import net.yungtechboy1.CyberCore.MobAI.FileLogger;
import net.yungtechboy1.CyberCore.entities.autospawn.AbstractEntitySpawner;
import net.yungtechboy1.CyberCore.entities.autospawn.SpawnResult;
import net.yungtechboy1.CyberCore.entities.monster.walking.Enderman;
import net.yungtechboy1.CyberCore.entities.utils.Utils;
import cn.nukkit.IPlayer;
import cn.nukkit.block.Block;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import cn.nukkit.utils.Config;

/**
 * Each entity get it's own spawner class.
 * 
 * @author <a href="mailto:kniffman@googlemail.com">Michael Gertz</a>
 */
public class EndermanSpawner extends AbstractEntitySpawner {

    /**
     * @param spawnTask
     */
    public EndermanSpawner(AutoSpawnTask spawnTask, Config pluginConfig) {
        super(spawnTask, pluginConfig);
    }

    public SpawnResult spawn(IPlayer iPlayer, Position pos, Level level) {
        SpawnResult result = SpawnResult.OK;
        
        // as enderman spawn very seldom, we need another random spawn here ...
        if (Utils.rand(0, 3) > 0) { // spawn with a 1/3 chance (it's possible that they also spawn in overworld!)
            return SpawnResult.SPAWN_DENIED;
        }

        int blockId = level.getBlockIdAt((int) pos.x, (int) pos.y, (int) pos.z);
        int blockLightLevel = level.getBlockLightAt((int) pos.x, (int) pos.y, (int) pos.z);

        if (!Block.solid[blockId]) { // only spawns on solid blocks
            result = SpawnResult.WRONG_BLOCK;
        } else if (blockLightLevel > 7) { // lightlevel not working for now, but as lightlevel is always zero that should work
            result = SpawnResult.WRONG_LIGHTLEVEL;
        } else if (pos.y > 256 || pos.y < 1 || level.getBlockIdAt((int) pos.x, (int) pos.y, (int) pos.z) == Block.AIR) { // cannot spawn on AIR block
            result = SpawnResult.POSITION_MISMATCH;
        } else {
            this.spawnTask.createEntity(getEntityName(), pos.add(0, 3.8, 0));
        }

        FileLogger.info(String.format("[%s] spawn for %s at %s,%s,%s with lightlevel %s and blockId %s, result: %s", getLogprefix(), iPlayer.getName(), pos.x, pos.y, pos.z, blockLightLevel, blockId, result));

        return result;
    }
    
    /* (@Override)
     * @see cn.nukkit.entity.ai.IEntitySpawner#getEntityNetworkId()
     */
    @Override
    public int getEntityNetworkId() {
        return Enderman.NETWORK_ID;
    }

    /* (@Override)
     * @see cn.nukkit.entity.ai.IEntitySpawner#getEntityName()
     */
    @Override
    public String getEntityName() {
        return "Enderman";
    }
    
    /* (@Override)
     * @see AbstractEntitySpawner#getLogprefix()
     */
    @Override
    protected String getLogprefix() {
        return this.getClass().getSimpleName();
    }

}
