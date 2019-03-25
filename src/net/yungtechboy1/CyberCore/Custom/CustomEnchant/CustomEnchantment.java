package net.yungtechboy1.CyberCore.Custom.CustomEnchant;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.block.BlockPlaceEvent;
import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.item.enchantment.EnchantmentType;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.ListTag;
import cn.nukkit.utils.TextFormat;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by carlt_000 on 1/14/2017.
 */
public class CustomEnchantment extends Enchantment {

    public static final int BLIND = 25;
    public static final int CRIPPLING = 26;
    public static final int DEATHBRINGER = 27;
    public static final int GOOEY = 28;
    public static final int ICEASPECT = 29;
    public static final int LIFESTEALER = 30;
    public static final int POISON = 31;
    public static final int THUNDER = 32;
    public static final int VIPER = 33;
    public static final int HASTE = 34;
    public static final int NIGHTVISION = 35;
    public static final int GILLS = 36;
    public static final int SNACKPACK = 37;
    public static final int SPRING = 38;
    public static final int CrateKey = 55;

    boolean CheckCustomName = true;
    private int cooldown;
    String lastplayer = "";
    public EnchantRarity ER;

    public CustomEnchantment(int id, String name, int weight, EnchantmentType type) {
        super(id, name, weight, type);
    }

    public long GetWorldTick() {
        return new Date().getTime() / 1000;
    }

    /**
     * Check to see if Item and key has cooldown
     *
     * @param i
     * @return
     */
    public boolean CheckCooldown(Item i) {
        return CheckCooldown(i, CooldownKey.Default);
    }

    /**
     * Check to see if Item and key has cooldown
     *
     * @param i
     * @param key
     * @return
     */
    public boolean CheckCooldown(Item i, int key) {
        for (CompoundTag entry : i.getNamedTag().getList("cooldown", CompoundTag.class).getAll()) {
            if (entry.getShort("id") == key && entry.getShort("ench") == getId())
                return entry.getLong("time") > GetWorldTick();
        }
        return false;
    }

    public int GetCooldown() {
        return cooldown;
    }

    public void SetCooldown(int secs, Item i) {
        SetCooldown(secs, i, CooldownKey.Default);
    }

    public void SetCooldown(int secs, Item i, int key) {
        long cc = GetWorldTick() + (long) secs;
        CompoundTag nt;
        if (!i.hasCompoundTag()) {
            nt = new CompoundTag();
        } else {
            nt = i.getNamedTag();
        }
        ListTag<CompoundTag> coold;
        if (!nt.contains("cooldown")) {
            coold = new ListTag<>("cooldown");
            nt.putList(coold);
        } else {
            coold = nt.getList("cooldown", CompoundTag.class);
        }
        coold.add(new CompoundTag().putShort("id", key).putLong("time", cc).putShort("ench", getId()));
//        for (CompoundTag entry : i.getNamedTag().getList("cooldown", CompoundTag.class).getAll()) {
//            if (entry.getShort("id") == key) entry.getLong("time") > GetWorldTick();
//        }
        i.setNamedTag(nt);
    }

    public void SetCooldown(int l) {
        cooldown = l;
    }


    public class CooldownKey {
        public static final int Default = 0;
    }

    public void CheckCustomName(Entity attacker) {
        if (attacker == null) return;
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
            String CT = TextFormat.RESET + "" + TextFormat.AQUA + Item.get(hand.getId(), hand.getDamage()).getName() + TextFormat.RESET;
            for (String text : CE) {
                CT += "\n" + TextFormat.GRAY + text + TextFormat.RESET;
            }

            hand.setCustomName(CT);

            //hand.setCustomName(CT);
            ((Player) attacker).getInventory().setItemInHand(Item.get(0));
            ((Player) attacker).getInventory().sendHeldItem((Player) attacker);
            ((Player) attacker).getInventory().setItemInHand(hand);
            ((Player) attacker).getInventory().sendHeldItem((Player) attacker);
            ;
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
        if (i == null) return null;
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
            String CT = TextFormat.RESET + "" + TextFormat.AQUA + Item.get(hand.getId(), hand.getDamage()).getName() + TextFormat.RESET;
            for (String text : CE) {
                CT += "\n" + TextFormat.GRAY + text + TextFormat.RESET;
            }

            hand.setCustomName(CT);
        }
        return hand;
    }

    public void BlockPlace(BlockPlaceEvent event) {

    }

    public void BlockBreak(BlockBreakEvent event) {

    }

    public static Enchantment getEnchantFromIDFromItem(int id) {
       return getEnchantFromIDFromItem(id,1);
    }
    public static Enchantment getEnchantFromIDFromItem(int id, int lvl) {
        if (id >= Enchantment.ID_TRIDENT_CHANNELING) {
            return Enchantment.get(id);
        } else {
            switch (id) {
                case BLIND:
                    return new Blind();
                case CRIPPLING:
                case DEATHBRINGER:
                case GOOEY:
                case ICEASPECT:
                case LIFESTEALER:
                case POISON:
                case THUNDER:
                case VIPER:
                case HASTE:
                    return new HasteCE(lvl);
                //TODO need to load cools downs too!
                case CrateKey:
                    return null;
            }
        }

        return null;
    }

    public static Enchantment getEnchantFromIDFromItem(Item i, short id) {
        if (!i.hasEnchantments()) {
            return null;
        }

        for (CompoundTag entry : i.getNamedTag().getList("ench", CompoundTag.class).getAll()) {
            if (entry.getShort("id") == id) {
                Enchantment e = getEnchantFromIDFromItem(entry.getShort("id"), entry.getShort("lvl"));
            }
        }

        return null;
    }

    public void RunPassive(){

    }
}
