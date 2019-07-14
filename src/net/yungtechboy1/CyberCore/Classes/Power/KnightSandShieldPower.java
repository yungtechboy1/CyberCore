package net.yungtechboy1.CyberCore.Classes.Power;

import cn.nukkit.block.BlockSand;
import cn.nukkit.level.Level;
import cn.nukkit.level.Location;
import cn.nukkit.math.BlockFace;
import cn.nukkit.math.Vector3;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Base.StagePowerAbstract;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.PowerEnum;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.Custom.Events.CustomEntityDamageByEntityEvent;

public class KnightSandShieldPower extends StagePowerAbstract {
    public KnightSandShieldPower(BaseClass b) {
        super(b,80,10);
        setPowerSettings(false,false,true,false);
        setMaxStage(StageEnum.STAGE_5);
    }


//    @Override
//    public ClassLevelingManager getLM() {
//        return (ClassLevelingManagerStage)super.getLM();
//    }

    @Override
    public CustomEntityDamageByEntityEvent CustomEntityDamageByEntityEvent(CustomEntityDamageByEntityEvent e) {
        return e;
    }

    @Override
    public PowerEnum getType() {
        return PowerEnum.KnightSandShield;
    }

    public Vector3 getSizes() {
        switch (getStage()) {
            default:
            case STAGE_1:
                return new Vector3(3, 3, 1);
            case STAGE_2:
                return new Vector3(4, 3, 1);
            case STAGE_3:
                return new Vector3(4, 4, 2);
            case STAGE_4:
                return new Vector3(5, 4, 2);
            case STAGE_5:
                return new Vector3(6, 5, 2);
        }
    }

    private int getWidth() {
        return getSizes().getFloorX();
    }

    private int getLength() {
        return getSizes().getFloorZ();
    }

    private int getHeight() {
        return getSizes().getFloorY();
    }

    @Override
    public Object usePower(Object... args) {
        CorePlayer cp = getPlayer();
        BlockFace d = cp.getDirection();
        Location sp = cp.add(cp.getDirection().getUnitVector().multiply(4));//2 Forward
        Level l = cp.getLevel();
//        if (d == BlockFace.NORTH) {
        boolean topoff = false;
//
        for (int length = 0; length < getLength(); length++) {
            for (int width = -(getWidth()); width < getWidth(); width++) {
                topoff = !topoff;
                BlockFace nd = getLeft(d);
                Location al = sp.add(nd.getUnitVector().multiply(width)).add(0, 20, 0);
                for (int height = 0; height < getHeight(); height++) {

//                l.setBlock(al.add(), new BlockSand());
                    Vector3 p = al.add(0, height, 0).add(d.getUnitVector().multiply(length));
                    if (l.getBlock(p).getId() == 0) l.setBlock(p, new BlockSand());
                }

                if (topoff && l.getBlock(al.add(0, 3, 0)).getId() == 0) l.setBlock(al.add(0, 3, 0), new BlockSand());
            }
//        }
        }
        return null;
    }

    public BlockFace getLeft(BlockFace bf) {
        switch (bf) {
            case NORTH:
                return BlockFace.WEST;
            case SOUTH:
                return BlockFace.EAST;
            case EAST:
                return BlockFace.NORTH;
            case WEST:
                return BlockFace.SOUTH;
            default:
                return BlockFace.NORTH;
        }

    }

    @Override
    public String getName() {
        return "Sand Shield";
    }


    @Override
    public String getDispalyName() {
        return TextFormat.YELLOW + getName();
    }
}