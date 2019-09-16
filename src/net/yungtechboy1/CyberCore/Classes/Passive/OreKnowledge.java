package net.yungtechboy1.CyberCore.Classes.Passive;

import cn.nukkit.block.Block;
import cn.nukkit.block.BlockID;
import cn.nukkit.level.Position;
import cn.nukkit.math.Vector3;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Base.StagePowerAbstract;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.PowerEnum;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.Custom.Events.CustomEntityDamageByEntityEvent;
import net.yungtechboy1.CyberCore.Manager.FT.FloatingTextFactory;
import net.yungtechboy1.CyberCore.Manager.FT.PopupFT;

import java.util.ArrayList;

//PowerAbstract
public class OreKnowledge extends StagePowerAbstract {
//    int BlockRange = 10;

    public OreKnowledge(BaseClass b) {
        super(b);
        setMaxStage(StageEnum.STAGE_1);
    }

    @Override
    public StageEnum getMaxStage() {
        return StageEnum.STAGE_5;
    }

    @Override
    public ArrayList<Class> getAllowedClasses() {
        return null;
    }

    @Override
    public double getPowerSourceCost() {
        return 1;
    }

    @Override
    public CustomEntityDamageByEntityEvent CustomEntityDamageByEntityEvent(CustomEntityDamageByEntityEvent e) {
        return e;
    }

    @Override
    public int getPowerSuccessChance() {
        switch (getStage()) {
            default:
            case STAGE_1:
                return 2;
            case STAGE_2:
                return 5;
            case STAGE_3:
                return 8;
            case STAGE_4:
                return 10;
            case STAGE_5:
                return 15;
        }
    }

    public int getBlockRange() {
        super.initStages();
        switch (getStage()) {
            default:
            case STAGE_1:
                return 10;
            case STAGE_2:
                return 13;
            case STAGE_3:

                return 17;
            case STAGE_4:
            case STAGE_5:
                return 20;
        }
    }

    @Override
    public PowerEnum getType() {
        return PowerEnum.MinerOreKnowledge;
    }

    @Override
    public Object usePower(Object... args) {
        return null;
    }

    @Override
    public String getName() {
        return "Ore Knowledge";
    }

    @Override
    public String getDispalyName() {
        return getName();
    }

    public void dispalyPreciousOres(CorePlayer p) {
        int size = getBlockRange();
        ArrayList<Vector3> bl = new ArrayList<>();
        for (int x = -size; x < size; x++) {
            for (int y = -size; y < size; y++) {
                for (int z = -size; z < size; z++) {
                    Vector3 pos = p.add(x, y, z);
                    Block b = p.getLevel().getBlock(pos);
                    int bid = b.getId();
                    if (bid == BlockID.AIR || bid == BlockID.STONE || bid == BlockID.COBBLESTONE || bid == BlockID.GRASS || bid == BlockID.DIRT)
                        continue;
                    switch (bid) {
                        case BlockID.GOLD_ORE:
                        case BlockID.IRON_ORE:
                        case BlockID.COAL_ORE:
                        case BlockID.DIAMOND_BLOCK:
                        case BlockID.EMERALD_ORE:
                            bl.add(pos.clone());
                            break;
                    }
                }
            }
        }
        if (bl.size() == 0) return;
        for (Vector3 v3 : bl) {
            Block b = p.getLevel().getBlock(v3);
            String bn = TextFormat.YELLOW + b.getName();
            FloatingTextFactory.AddToRemoveList(new PopupFT(new Position(v3.x, v3.y, v3.z, p.getLevel()), bn) {{
                Frozen = true;
                Lifespan = 10;
            }});
        }

    }

}
