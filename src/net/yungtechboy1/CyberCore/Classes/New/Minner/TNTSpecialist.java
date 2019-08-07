package net.yungtechboy1.CyberCore.Classes.New.Minner;

import cn.nukkit.utils.ConfigSection;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.Classes.New.ClassType;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.PowerEnum;
import net.yungtechboy1.CyberCore.Classes.Power.TNTSpecialistPower;
import net.yungtechboy1.CyberCore.CoolDown;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.Manager.Form.CyberForm;
import net.yungtechboy1.CyberCore.Manager.Form.Windows.ClassHowToUseTNT;
import net.yungtechboy1.CyberCore.Manager.Form.Windows.MainClassSettingsTNTWindow;

import java.util.ArrayList;

/**
 * Created by carlt on 5/16/2019.
 */
public class TNTSpecialist extends MinnerBaseClass {
    public static final String TNT_Specialist_Add_Tick = "TSAT";
    public static final int Power_TNT_Specialist = 0;

    public TNTSpecialist(CyberCoreMain main, CorePlayer player, ConfigSection data) {
        super(main, player, ClassType.Class_Miner_TNT_Specialist, data);
    }


    public TNTSpecialist(CyberCoreMain main, CorePlayer player) {
        this(main, player, null);
    }

    public int GetMaxTNTPower() {
        double mi = getLVL() / 10;
        int m = (int) Math.round(mi);
        int t = 10;
        switch (m) {
            case 0:
                break;
            case 1:
                t += 5;
                break;
            case 2:
                return 10;
            default:
                return 10;
        }
        return t;
    }

    @Override
    public void SetPowers() {
//        addPossiblePower(new TNTSpecialistPower(this,  3, GetMaxTNTPower()));
    }

    @Override
    public Object RunPower(PowerEnum powerid, Object... args) {
        if (powerid == PowerEnum.TNTSpecalist && args.length == 1) {
            System.out.println("GGGGGG");
            CorePlayer p = (CorePlayer) args[0];
            TNTSpecialistPower tsp = (TNTSpecialistPower) getPossiblePower(PowerEnum.TNTSpecalist);
            tsp.UsePower(p, getFuse());
            System.out.println("aaaaaa" + p.getClass().getName());
        } else {
            System.out.println("ERRRRRRRRRRRRRRRR");
        }
        return null;
    }

    @Override
    public String getName() {
        return "TNT Specialist";
    }

//        @Override
//        public BlockBreakEvent BlockBreakEvent(BlockBreakEvent event) {
//            event = super.BlockBreakEvent(event);
//            Item hand = getPlayer().getInventory().getItemInHand();
//            if (hand.isPickaxe()) {
//                switch (event.getBlock().getId()) {
//                    case BlockID.COBBLESTONE:
//                    case BlockID.STONE:
//                    case BlockID.SANDSTONE:
//                        addXP(1);
//                        break;
//                    case BlockID.IRON_ORE:
//                        addXP(2);
//                        break;
//                    case BlockID.GOLD_ORE:
//                        addXP(3);
//                        break;
//                    case BlockID.DIAMOND_BLOCK:
//                        addXP(4);
//                        break;
//                    case BlockID.EMERALD_ORE:
//                        addXP(5);
//                        break;
//                }
//            }
//
//            return event;
//        }


//        @Override
//        public BlockPlaceEvent BlockPlaceEvent(BlockPlaceEvent event) {
//            event = super.BlockPlaceEvent(event);
//            switch (event.getBlock().getId()) {
//                case BlockID.TNT:
//                    CustomBlockTNT cbt = (CustomBlockTNT) event.getBlock();
//                    if (cbt == null) System.out.println("EEEEEEEEEEEEE4735 NULL CustomblockTNT");
//                    int l = cbt.getTNTType();
//                    addXP(l);
//                    break;
//            }
//            return event;
//        }

    public int GetTNTAddWaitTime() {
        double t = Math.sqrt(40 * getLVL()) * 13 * getLVL();
        double b = 8 * getLVL();
        return (int) (240 - (t / b));
//        return 10;
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
            //Add Tnt
            ((TNTSpecialistPower) getPossiblePower(PowerEnum.TNTSpecalist)).AddAvailbleQuantity();
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

    public int getFuse() {
        return 120;
    }

    @Override
    public ArrayList<String> FormatHudText() {
        ArrayList<String> f = super.FormatHudText();
        //Show TNT PowerAbstract
        TNTSpecialistPower p = (TNTSpecialistPower) getPossiblePower(PowerEnum.TNTSpecalist);
        if (p == null) return f;
        int q = p.getAvailbleQuantity();
        int m = p.getMaxAvailbleQuantity();
        String n = TextFormat.GRAY + " | " + TextFormat.RED + " TNT: " + q;
        f.add(n);
        return f;
    }

    @Override
    public CyberForm getSettingsWindow() {
        return new MainClassSettingsTNTWindow( this);
    }

    @Override
    public CyberForm getHowToUseClassWindow() {
        return new ClassHowToUseTNT(getName());
    }
}