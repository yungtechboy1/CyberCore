package net.yungtechboy1.CyberCore.Classes.Passive;

import cn.nukkit.block.Block;
import cn.nukkit.block.BlockID;
import cn.nukkit.level.Position;
import cn.nukkit.math.Vector3;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.Classes.Power.Power;
import net.yungtechboy1.CyberCore.Classes.Power.PowerEnum;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.Manager.FT.PopupFT;

import java.util.ArrayList;

public class OreKnowledge extends PassivePower {
    int BlockRange = 10;

    public OreKnowledge(BaseClass b,int lvl) {
        super(b,1, lvl);
    }

    @Override
    public void initStages() {
        super.initStages();
        switch (getStage()) {
            case STAGE_1:
                setPowerSuccessChance(2);
                break;
            case STAGE_2:
                setPowerSuccessChance(5);
                BlockRange = 13;
                break;
            case STAGE_3:
                setPowerSuccessChance(8);
                BlockRange = 17;
                break;
            case STAGE_4:
                setPowerSuccessChance(10);
                BlockRange = 20;
                break;
            case STAGE_5:
                setPowerSuccessChance(15);
                BlockRange = 20;
                break;
            default:
                setPowerSuccessChance(2);
                break;
        }
    }

    @Override
    public PowerEnum getType() {
        return PowerEnum.MinerOreKnowledge;
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
        int size = BlockRange;
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
        for(Vector3 v3: bl){
            Block b = p.getLevel().getBlock(v3);
            String bn = TextFormat.YELLOW+b.getName();
            CyberCoreMain.getInstance().FTM.AddToRemoveList(new PopupFT(new Position(v3.x,v3.y,v3.z,p.getLevel()),bn){{
                Frozen = true;
                Lifespan = 10;
            }});
        }

    }

}
