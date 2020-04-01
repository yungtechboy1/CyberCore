/**
 * CreeperSpawner.java
 * 
 * Created on 10:39:49
 */
package net.yungtechboy1.CyberCore.entities.spawners;

import cn.nukkit.IPlayer;
import cn.nukkit.block.Block;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import cn.nukkit.utils.Config;
import net.yungtechboy1.CyberCore.MobAI.AutoSpawnTask;
import net.yungtechboy1.CyberCore.MobAI.FileLogger;
import net.yungtechboy1.CyberCore.entities.animal.flying.Bat;
import net.yungtechboy1.CyberCore.entities.autospawn.AbstractEntitySpawner;
import net.yungtechboy1.CyberCore.entities.autospawn.SpawnResult;

/**
 * Each entity get it's own spawner class.
 * 
 * @author <a href="mailto:kniffman@googlemail.com">Michael Gertz</a>
 */
public class BatSpawner extends AbstractEntitySpawner {

    /**
     * @param spawnTask
     */
    public BatSpawner(AutoSpawnTask spawnTask, Config pluginConfig) {
        super(spawnTask, pluginConfig);
    }

    public SpawnResult spawn(IPlayer iPlayer, Position pos, Level level) {
        SpawnResult result = SpawnResult.OK;

        int blockId = level.getBlockIdAt((int) pos.x, (int) pos.y, (int) pos.z);
        int blockLightLevel = level.getBlockLightAt((int) pos.x, (int) pos.y, (int) pos.z);

        if (Block.transparent[blockId]) { // only spawns on opaque blocks
            result = SpawnResult.WRONG_BLOCK;
        } else if (blockLightLevel > 3) {
            result = SpawnResult.WRONG_LIGHTLEVEL;
        } else if (pos.y > 256 || pos.y < 1 || level.getBlockIdAt((int) pos.x, (int) pos.y, (int) pos.z) == Block.AIR) { // cannot spawn on AIR block
            result = SpawnResult.POSITION_MISMATCH;
        } else {
            this.spawnTask.createEntity(getEntityName(), pos.add(0, 1.3, 0));
        }

        FileLogger.info(String.format("[%s] spawn for %s at %s,%s,%s with lightlevel %s and blockId %s, result: %s", getLogprefix(), iPlayer.getName(), pos.x, pos.y, pos.z, blockLightLevel, blockId, result));

        return result;
    }
    
    /* (@Override)
     * @see cn.nukkit.entity.ai.IEntitySpawner#getEntityNetworkId()
     */
    @Override
    public int getEntityNetworkId() {
        return Bat.NETWORK_ID;
    }

    /* (@Override)
     * @see cn.nukkit.entity.ai.IEntitySpawner#getEntityName()
     */
    @Override
    public String getEntityName() {
        return "Bat";
    }

    /* (@Override)
     * @see AbstractEntitySpawner#getLogprefix()
     */
    @Override
    protected String getLogprefix() {
        return this.getClass().getSimpleName();
    }

}
