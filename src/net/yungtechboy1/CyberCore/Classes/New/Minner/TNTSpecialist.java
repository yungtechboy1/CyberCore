package net.yungtechboy1.CyberCore.Classes.New.Minner;

import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.item.Item;
import cn.nukkit.utils.ConfigSection;
import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.Classes.New.TNTSpecialistPower;
import net.yungtechboy1.CyberCore.CoolDown;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;

/**
 * Created by carlt on 5/16/2019.
 */
public class TNTSpecialist extends BaseClass {
    public static final String TNT_Specialist_Add_Tick = "TSAT";
    public static final int Power_TNT_Specialist = 0;

    public TNTSpecialist(CyberCoreMain main, CorePlayer player, ConfigSection data) {
        super(main, player, ClassType.Class_Miner_TNT_Specialist, data);
        SetPowers();
    }


    public TNTSpecialist(CyberCoreMain main, CorePlayer player) {
        this(main, player, null);
    }

    public int GetMaxTNTPower() {
        double mi = getLVL() / 10;
        int m = (int) Math.round(mi);
        switch (m) {
            case 0:
            case 1:
            case 2:
                return 10;
            default:
                return 10;
        }
    }

    public void SetPowers() {
        Powers.add(Power_TNT_Specialist, new TNTSpecialistPower(0, GetMaxTNTPower()));
    }

    @Override
    public BlockBreakEvent BlockBreakEvent(BlockBreakEvent event) {
        event = super.BlockBreakEvent(event);
        Item hand = getPlayer().getInventory().getItemInHand();
        if (hand.isPickaxe()) {

        }


        return event;
    }

    public int GetTNTAddWaitTime() {
        return 160 - getLVL();
    }

//    @Override
//    public ArrayList<Ability> PossibleAbillity() {
//        ArrayList<Ability> a = new ArrayList<Ability>();
//        a.add(new)
//        return a;
//    }

    @Override
    public void onUpdate(int tick) {
        super.onUpdate(tick);
        CoolDown tntaddcd = GetCooldown(TNT_Specialist_Add_Tick);
        if (tntaddcd == null) {
            AddCooldown(TNT_Specialist_Add_Tick, GetTNTAddWaitTime());
        } else {
            if (!tntaddcd.isValid()) {
                //Add Tnt & reset
                Powers.get(Power_TNT_Specialist).AddAvailbleQuantity();
                AddCooldown(TNT_Specialist_Add_Tick, GetTNTAddWaitTime());
            }
        }
    }

    @Override
    public ConfigSection export() {
        return new ConfigSection() {{
            put("COOLDOWNS", COOLDOWNS);
            put("XP", getXP());
            put("TYPE", getTYPE().getKey());
        }};
    }
}