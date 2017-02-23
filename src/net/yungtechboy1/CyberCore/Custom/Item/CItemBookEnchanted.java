package net.yungtechboy1.CyberCore.Custom.Item;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.Custom.CustomEnchant.CustomEnchantment;

import java.util.ArrayList;

/**
 * Created by carlt_000 on 1/18/2017.
 */
public class CItemBookEnchanted extends Item {
    public CItemBookEnchanted() {
        this(0, 1);
    }

    public CItemBookEnchanted(Integer meta) {
        this(meta, 1);
    }

    public CItemBookEnchanted(Integer meta, int count) {
        super(403, meta, count, "Enchanted Book");
    }

    public Enchantment getEnchantment(){
        return Enchantment.get(getDamage());
    }

    @Override
    public void addEnchantment(Enchantment... enchantments) {
        super.addEnchantment(enchantments);
        for(Enchantment e : getEnchantments()){
            if(e instanceof CustomEnchantment){
                CheckCustomName();
                return;
            }
        }
    }

    public void CheckCustomName() {
        Item hand = this;
        boolean custom = false;
        ArrayList<String> CE = new ArrayList<>();
        for (Enchantment enchantment : hand.getEnchantments()) {
            if (enchantment.getId() > 24) {
                CE.add(enchantment.getName() + " " + IntToRoman(enchantment.getLevel()));
                custom = true;
            }
            System.out.println(enchantment.getName() + " " + IntToRoman(enchantment.getLevel()));
        }
        if (custom) {
            String CT = TextFormat.RESET + "" + TextFormat.AQUA + Item.get(hand.getId(),hand.getDamage()).getName() + TextFormat.RESET;
            for (String text : CE) {
                CT += "\n" + TextFormat.GRAY + text + TextFormat.RESET;
            }
            hand.setCustomName(CT);
        }
    }

    private static String IntToRoman(Integer integer) {
        int a = 1;
        if (integer == a++) return "I";
        if (integer == a++) return "II";
        if (integer == a++) return "III";
        if (integer == a++) return "IV";
        if (integer == a++) return "V";
        if (integer == a++) return "VI";
        if (integer == a++) return "VII";
        if (integer == a++) return "VIII";
        if (integer == a++) return "IX";
        if (integer == a) return "X";
        return "-";
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }
}
