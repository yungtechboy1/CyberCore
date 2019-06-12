package net.yungtechboy1.CyberCore.Classes.Power.Attack.Mercenary;

import cn.nukkit.block.BlockSand;
import cn.nukkit.level.Level;
import cn.nukkit.level.Location;
import cn.nukkit.math.BlockFace;
import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.Classes.Power.Power;
import net.yungtechboy1.CyberCore.Classes.Power.PowerEnum;
import net.yungtechboy1.CyberCore.Classes.Power.PowerHotBar;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.Custom.Events.CustomEntityDamageByEntityEvent;

public class KnightSandShieldPower extends PowerHotBar {
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
    public Object usePower(CorePlayer cp, Object... args) {
        BlockFace d = cp.getDirection();
        Location sp = cp.add(cp.getDirection().getUnitVector().multiply(2));//2 Forward
        Level l = cp.getLevel();
//        if (d == BlockFace.NORTH) {
        for (int width = -3; width < 3; width++) {
            int d1 = d.getIndex()+3;
            if(d1 > 5)d1 = BlockFace.NORTH.getIndex();
            BlockFace nd = BlockFace.fromIndex(d1);
            Location al = sp.add(nd.getUnitVector().multiply(width)).add(0,10,0);
            for (int height = 0; height < 3; height++) {
//                l.setBlock(al.add(), new BlockSand());
                l.setBlock(al.add(0,height,0),new BlockSand());
            }
        }
//        }
        return null;
    }

    @Override
    public String getName() {
        return "Sand Shield";
    }
}
