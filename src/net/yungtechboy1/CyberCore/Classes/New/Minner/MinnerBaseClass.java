package net.yungtechboy1.CyberCore.Classes.New.Minner;

import cn.nukkit.block.BlockID;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.block.BlockPlaceEvent;
import cn.nukkit.item.Item;
import cn.nukkit.utils.ConfigSection;
import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.Classes.New.ClassType;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.PowerEnum;
import net.yungtechboy1.CyberCore.Classes.PowerSource.PrimalPowerType;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.Custom.Block.CustomBlockTNT;
import net.yungtechboy1.CyberCore.CyberCoreMain;

public class MinnerBaseClass extends BaseClass {
    public static final String TNT_Specialist_Add_Tick = "TSAT";
    public static final int Power_TNT_Specialist = 0;

    public MinnerBaseClass(CyberCoreMain main, CorePlayer player, ConfigSection data) {
        super(main, player, data);
    }

    @Override
    public PrimalPowerType getPowerSourceType() {
        return null;
    }

    @Override
    public ClassType getTYPE() {
        return null;
    }

    @Override
    public void SetPowers() {

    }

    @Override
    public void initBuffs() {

    }

    @Override
    public Object RunPower(PowerEnum powerid, Object... args) {
        return null;
    }


    @Override
    public String getName() {
        return "Miner Base Class";
    }

    @Override
    public BlockBreakEvent BlockBreakEvent(BlockBreakEvent event) {
        event = super.BlockBreakEvent(event);
        Item hand = getPlayer().getInventory().getItemInHand();
        if (hand.isPickaxe()) {
            switch (event.getBlock().getId()) {
                case BlockID.COBBLESTONE:
                case BlockID.STONE:
                case BlockID.SANDSTONE:
                    addXP(1);
                    break;
                case BlockID.IRON_ORE:
                    addXP(2);
                    break;
                case BlockID.GOLD_ORE:
                    addXP(3);
                    break;
                case BlockID.DIAMOND_BLOCK:
                    addXP(4);
                    break;
                case BlockID.EMERALD_ORE:
                    addXP(5);
                    break;
            }
        }

        return event;
    }



    @Override
    public BlockPlaceEvent BlockPlaceEvent(BlockPlaceEvent event) {
        event = super.BlockPlaceEvent(event);
        switch (event.getBlock().getId()) {
            case BlockID.TNT:
                CustomBlockTNT cbt = (CustomBlockTNT) event.getBlock();
                if (cbt == null) System.out.println("EEEEEEEEEEEEE4735 NULL CustomblockTNT");
                CustomBlockTNT.TNTType l = cbt.getTNTType();
                addXP(l.ordinal()+1);
                break;
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

//    @Override
//    public void onUpdate(int tick) {
//        super.onUpdate(tick);
//        CoolDown tntaddcd = GetCooldown(TNT_Specialist_Add_Tick);
//        if (tntaddcd == null) {
//            AddCooldown(TNT_Specialist_Add_Tick, GetTNTAddWaitTime());
//        } else {
//            if (!tntaddcd.isValid()) {
//                //Add Tnt & reset
//                ActivePowers.get(Power_TNT_Specialist).AddAvailbleQuantity();
//                AddCooldown(TNT_Specialist_Add_Tick, GetTNTAddWaitTime());
//            }
//        }
//    }
//
//    @Override
//    public ConfigSection exportConfig() {
//        return new ConfigSection() {{
//            put("COOLDOWNS", COOLDOWNS);
//            put("XP", getXP());
//            put("TYPE", getTYPE().getKey());
//        }};
//    }
}