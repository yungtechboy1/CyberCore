package net.yungtechboy1.CyberCore.Classes.Power.Attack.Mercenary;

import cn.nukkit.block.BlockSand;
import cn.nukkit.item.Item;
import cn.nukkit.level.Level;
import cn.nukkit.level.Location;
import cn.nukkit.math.BlockFace;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.Classes.Power.PowerEnum;
import net.yungtechboy1.CyberCore.Classes.Power.PowerHotBarInt;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.Custom.Events.CustomEntityDamageByEntityEvent;

public class KnightSandShieldPower extends PowerHotBarInt {
    public KnightSandShieldPower(BaseClass b) {
        super(b, 80);
    }

    @Override
    public CustomEntityDamageByEntityEvent CustomEntityDamageByEntityEvent(CustomEntityDamageByEntityEvent e) {
        return e;
    }

    @Override
    public PowerEnum getType() {
        return PowerEnum.KnightSandShield;
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
        for (int width = -3; width < 3; width++) {
            topoff = !topoff;
            BlockFace nd = getLeft(d);
            Location al = sp.add(nd.getUnitVector().multiply(width)).add(0,15,0);
            for (int height = 0; height < 3; height++) {

//                l.setBlock(al.add(), new BlockSand());
                l.setBlock(al.add(0,height,0),new BlockSand());
            }

            if(topoff)l.setBlock(al.add(0,3,0),new BlockSand());
        }
//        }
        return null;
    }

    public BlockFace getLeft(BlockFace bf){
        switch (bf){
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
        return TextFormat.YELLOW+getName();
    }
}
