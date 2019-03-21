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
import cn.nukkit.IPlayer;
import cn.nukkit.block.Block;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import cn.nukkit.utils.Config;
import net.yungtechboy1.CyberCore.entities.monster.walking.Zombie;

/**
 * Each entity get it's own spawner class.
 * 
 * @author <a href="mailto:kniffman@googlemail.com">Michael Gertz</a>
 */
public class ZombieSpawner extends AbstractEntitySpawner {

    /**
     * @param spawnTask
     */
    public ZombieSpawner(AutoSpawnTask spawnTask, Config pluginConfig) {
        super(spawnTask, pluginConfig);
    }

    public SpawnResult spawn(IPlayer iPlayer, Position pos, Level level) {
        SpawnResult result = SpawnResult.OK;
        
        // TODO: a zombie may also be spawned with items ... but that's to be done later

        int blockLightLevel = level.getBlockLightAt((int) pos.x, (int) pos.y, (int) pos.z);

        if (blockLightLevel > 7) {
            result = SpawnResult.WRONG_LIGHTLEVEL;
        } else if (pos.y > 256 || pos.y < 1 || level.getBlockIdAt((int) pos.x, (int) pos.y, (int) pos.z) == Block.AIR) { // cannot spawn on AIR block
            result = SpawnResult.POSITION_MISMATCH;
        } else {
            this.spawnTask.createEntity(getEntityName(), pos.add(0, 2.8, 0));
        }

        FileLogger.info(String.format("[%s] spawn for %s at %s,%s,%s with lightlevel %s, result: %s", getLogprefix(), iPlayer.getName(), pos.x, pos.y, pos.z, blockLightLevel, result));

        return result;
    }
    
    /* (@Override)
     * @see cn.nukkit.entity.ai.IEntitySpawner#getEntityNetworkId()
     */
    @Override
    public int getEntityNetworkId() {
        return Zombie.NETWORK_ID;
    }

    /* (@Override)
     * @see cn.nukkit.entity.ai.IEntitySpawner#getEntityName()
     */
    @Override
    public String getEntityName() {
        return "Zombie";
    }
    
    /* (@Override)
     * @see AbstractEntitySpawner#getLogprefix()
     */
    @Override
    protected String getLogprefix() {
        return this.getClass().getSimpleName();
    }

}
