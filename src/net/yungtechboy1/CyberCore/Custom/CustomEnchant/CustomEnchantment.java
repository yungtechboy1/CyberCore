package net.yungtechboy1.CyberCore.Custom.CustomEnchant;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.item.enchantment.EnchantmentType;
import cn.nukkit.utils.TextFormat;

import java.util.ArrayList;

/**
 * Created by carlt_000 on 1/14/2017.
 */
public class CustomEnchantment extends Enchantment {

    public static int BLIND = 25;
    public static int CRIPPLING = 26;
    public static int DEATHBRINGER = 27;
    public static int GOOEY = 28;
    public static int ICEASPECT = 29;
    public static int LIFESTEALER = 30;
    public static int POISON = 31;
    public static int THUNDER = 32;
    public static int VIPER = 33;
    public static int CrateKey = 255;

    boolean CheckCustomName = true;
    String lastplayer = "";

    public CustomEnchantment(int id, String name, int weight, EnchantmentType type){
        super(id, name, weight, type);
    }

    public void CheckCustomName(Entity attacker) {
        if(attacker == null)return;
        if (!(attacker instanceof Player)) return;
        Item hand = ((Player) attacker).getInventory().getItemInHand();
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

            //hand.setCustomName(CT);
            ((Player) attacker).getInventory().setItemInHand(Item.get(0));
            ((Player) attacker).getInventory().sendHeldItem((Player) attacker);
            ((Player) attacker).getInventory().setItemInHand(hand);
            ((Player) attacker).getInventory().sendHeldItem((Player) attacker);;
        }
        lastplayer = attacker.getName();

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

    public static Item SetCustomName(Item i) {
        if(i == null)return null;
        Item hand = i;
        boolean custom = false;
        ArrayList<String> CE = new ArrayList<>();
        for (Enchantment enchantment : hand.getEnchantments()) {
            if (enchantment.getId() > 24) {
                CE.add(enchantment.getName() + " " + IntToRoman(enchantment.getLevel()));
                custom = true;
            }
        }
        if (custom) {
            String CT = TextFormat.RESET + "" + TextFormat.AQUA + Item.get(hand.getId(),hand.getDamage()).getName() + TextFormat.RESET;
            for (String text : CE) {
                CT += "\n" + TextFormat.GRAY + text + TextFormat.RESET;
            }

            hand.setCustomName(CT);
        }
        return hand;
    }
}
