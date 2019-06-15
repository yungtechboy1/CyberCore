package net.yungtechboy1.CyberCore.Manager.Form.Windows;

import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementLabel;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.FormType;
import net.yungtechboy1.CyberCore.Manager.Form.CyberFormCustom;
import net.yungtechboy1.CyberCore.Manager.Form.CyberFormSimple;

public class ClassHowToUseTNT extends CyberFormSimple {
    String _tt;
    public ClassHowToUseTNT( String title) {
        super(FormType.MainForm.Class_HowToUse_TNT, "CyberTech++ | How To Use "+title,"");
        _tt = title;
        addButton(new ElementButton("===Available Commands=="));
        addButton(new ElementButton("===Powers / Abilities==="));
        addButton(new ElementButton("===Upgrades / Unlocks==="));
        addButton(new ElementButton("===  How to Earn XP  ==="));
    }

    @Override
    public boolean onRun(CorePlayer p) {
        super.onRun(p);
        int k = getResponse().getClickedButtonId();
        if(k == 0){
            p.showFormWindow(new ClassHowToUseTNTCommands(_tt));
        }else if(k == 1){
            p.showFormWindow(new ClassHowToUseTNTPA(_tt));

        }else if(k == 2){
            p.showFormWindow(new ClassHowToUseTNTUnlocks(_tt));

        }else if(k == 3){
            p.showFormWindow(new ClassHowToUseTNTEXP(_tt));

        }
        return false;
    }
}

class ClassHowToUseTNTCommands extends CyberFormCustom{

    String _tt;
    public ClassHowToUseTNTCommands(String title) {
        super(FormType.MainForm.Class_HowToUse_TNT_Commands,title);
        _tt = title;
        addElement(new ElementLabel("===Available Commands==="));
        addElement(new ElementLabel("/tnt - Use the TNT you regenerate to mine"));
        System.out.println("TTTT >>>> "+title);
    }

    @Override
    public boolean onRun(CorePlayer p) {
        p.showFormWindow(new ClassHowToUseTNT(_tt));
        return false;
    }
}

class ClassHowToUseTNTPA extends CyberFormCustom{

    String _tt;
    public ClassHowToUseTNTPA(String title) {
        super(FormType.MainForm.Class_HowToUse_TNT_PA,title);
        _tt = title;
        addElement(new ElementLabel("===Powers / Abilities=="));
        addElement(new ElementLabel("- TNT Regen"));
        addElement(new ElementLabel("   - Default TNT Bag Size = 10"));
        addElement(new ElementLabel("   - Regenerate 1 TNT every 160 secs - Level"));
        addElement(new ElementLabel("- TNT Resistance"));
        addElement(new ElementLabel("   - Take no TNT Damage (Lvl_5 Excluded)"));
    }

    @Override
    public boolean onRun(CorePlayer p) {
        p.showFormWindow(new ClassHowToUseTNT(_tt));
        return false;
    }
}
class ClassHowToUseTNTUnlocks extends CyberFormCustom{

    String _tt;
    public ClassHowToUseTNTUnlocks(String title) {
        super(FormType.MainForm.Class_HowToUse_TNT_Unlocks,title);
        _tt = title;
        addElement(new ElementLabel("=== Upgrades / Unlocks ==="));
        addElement(new ElementLabel("- Level 1"));
        addElement(new ElementLabel("   - Spawn Basic TNT (Level 1)"));
        addElement(new ElementLabel("- Level 10"));
        addElement(new ElementLabel("   - Add 5 extra slots to TNT Storage"));
        addElement(new ElementLabel("- Level 20"));
        addElement(new ElementLabel("   - Purchase of TNT-Wand Available"));
        addElement(new ElementLabel("       - Allows you to tap to spawn TNT instead of using the /TNT command"));
        addElement(new ElementLabel("   - Class Merchant Unlocked"));
        addElement(new ElementLabel("- Level 30"));
        addElement(new ElementLabel("   - Add 5 extra slots to TNT Storage"));
        addElement(new ElementLabel("- Level 40"));
        addElement(new ElementLabel("   - Mining with any pickaxe will reduce your TNT spawn time by 1 Sec"));
        addElement(new ElementLabel("- Level 50"));
        addElement(new ElementLabel("   - Spawns Charged TNT (Level 2), Which Expands the range of TNT by 5 Blocks"));
        addElement(new ElementLabel("- Level 60"));
        addElement(new ElementLabel("   - Add 5 extra slots to TNT Storage"));
        addElement(new ElementLabel("- Level 70"));
        addElement(new ElementLabel("   - Player can now trade for TNT Snowball"));
        addElement(new ElementLabel("   -   Whereever the snowball lands will spawn a 3 X 3 grid of 9 TNT in the middle of the snowball with a 40-120 Tick fuse"));
        addElement(new ElementLabel("- Level 80"));
        addElement(new ElementLabel("   - Spawns Lvl_4 TNT Level 3, Which Expands the range of TNT by 10 Blocks"));
        addElement(new ElementLabel("- Level 90"));
        addElement(new ElementLabel("   - Spawns Lvl_5 TNT Level 4, Which Expands the range of TNT by 15 Blocks and can break Obsidian"));
        addElement(new ElementLabel("- Level 100"));
        addElement(new ElementLabel("   - Gain Lvl_5 TNT Damage Resistance"));
    }

    @Override
    public boolean onRun(CorePlayer p) {
        p.showFormWindow(new ClassHowToUseTNT(_tt));
        return false;
    }
}

class ClassHowToUseTNTEXP extends CyberFormCustom{

    String _tt;
    public ClassHowToUseTNTEXP(String title) {
        super(FormType.MainForm.Class_HowToUse_TNT_EXP,title);
        _tt = title;
        addElement(new ElementLabel("=== How to Earn XP ==="));
        addElement(new ElementLabel("- Using TNT Regen PowerPublicInterface: 2 XP"));
        addElement(new ElementLabel("- Using TNT :"));
        addElement(new ElementLabel("   - Basic: 5 XP"));
        addElement(new ElementLabel("   - Charged: 10 XP"));
        addElement(new ElementLabel("   - Lvl_4: 20 XP"));
        addElement(new ElementLabel("   - Lvl_5: 30 XP"));
        addElement(new ElementLabel("- Breaking Blocks:"));
        addElement(new ElementLabel("   - Stone & CobbleStone: 1 XP"));
        addElement(new ElementLabel("   - Iron Ore: 2 XP"));
        addElement(new ElementLabel("   - Gold Ore: 3 XP"));
        addElement(new ElementLabel("   - Diamond Ore: 5 XP"));
    }

    @Override
    public boolean onRun(CorePlayer p) {
        p.showFormWindow(new ClassHowToUseTNT(_tt));
        return false;
    }
}
