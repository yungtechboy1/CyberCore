package net.yungtechboy1.CyberCore.Custom.Item;

import cn.nukkit.item.ItemBookEnchanted;
import cn.nukkit.math.NukkitRandom;
import cn.nukkit.nbt.tag.CompoundTag;
import net.yungtechboy1.CyberCore.Custom.CustomEnchant.CustomEnchantment;
import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.utils.TextFormat;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by carlt_000 on 1/18/2017.
 */
public class CItemBookEnchanted extends Item {

    public Enchantment E;
    private int SuccessRate;
    private int FailRate;

    public CItemBookEnchanted(Integer meta,int s,int f) {
        this(meta,1,null,s,f);
    }
    public CItemBookEnchanted(Integer meta) {
        this(meta, 1, null);
    }
    public CItemBookEnchanted(Integer meta, int count, int s,int f) {
        this(meta,count,null,s,f);
    }
    public CItemBookEnchanted(Integer meta, int count) {
        this(meta, count, null);
    }

    public CItemBookEnchanted(Integer meta, int count, CompoundTag namedtag) {
        super(Item.ENCHANT_BOOK, meta, count, "Custom Enchanted Book");
        E = CustomEnchantment.getEnchant(meta);
        if (namedtag != null && namedtag.contains("rates")) {
            SuccessRate = namedtag.getCompound("rates").getInt("s");
            FailRate = namedtag.getCompound("rates").getInt("f");
            setCompoundTag(namedtag);
        } else {
            SuccessRate = percentPick(50, 0);
            FailRate = percentPick(100, 75);
        }
    }

    public CItemBookEnchanted(Integer meta, int count, CompoundTag namedtag, int s, int f) {
        super(Item.ENCHANT_BOOK, meta, count, "Custom Enchanted Book");
        E = CustomEnchantment.getEnchant(meta);
        if(namedtag != null)setCompoundTag(namedtag);
        SuccessRate = s;
        FailRate = f;
    }

    public Enchantment getEnchantment() {
        return Enchantment.get(getDamage());
    }

    @Override
    public void addEnchantment(Enchantment... enchantments) {
        super.addEnchantment(enchantments);
        for (Enchantment e : getEnchantments()) {
            if (e instanceof CustomEnchantment) {
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
            String CT = TextFormat.RESET + "" + TextFormat.AQUA + Item.get(hand.getId(), hand.getDamage()).getName() + TextFormat.RESET;
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

    private Integer percentPick(int max, int min) {
        NukkitRandom i = new NukkitRandom();
        if (max == min) {
            return max;
        } else {
            return min + i.nextRange(max - min);
        }
    }
}
