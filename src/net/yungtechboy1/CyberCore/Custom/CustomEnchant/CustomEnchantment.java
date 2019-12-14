package net.yungtechboy1.CyberCore.Custom.CustomEnchant;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.block.BlockPlaceEvent;
import cn.nukkit.form.element.ElementLabel;
import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.item.enchantment.EnchantmentType;
import cn.nukkit.math.NukkitRandom;
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
    public static final int CLIMBER = 39;
    public static final int BURNSHILED = 40;
    public static final int KINGOFHEARTS = 40;
    public static final int RESTORATION = 40;
    public static final int CrateKey = 55;
    public static final int MAX = 55;//ALWAYS THE MAX ENCHANT ID
    public static final int VANILLA = 32;//ALWAYS 32


    public enum Tier {

        Unknown(0),
        Basic(1),
        Standard(2),
        Upgraded(3),
        Eternal(4),
        Legendary(5),
        Rare(6),
        Untold(7),
        Unheard(8);

        private int Rank;

        Tier(int rank) {
            this.Rank = rank;
        }

        public static Tier GetTier(int t) {
            switch (t) {
                case 0:
                default:
                    return Unknown;
                case 1:
                    return Basic;
                case 2:
                    return Standard;
                case 3:
                    return Upgraded;
                case 4:
                    return Eternal;
                case 5:
                    return Legendary;
                case 6:
                    return Rare;
                case 7:
                    return Untold;
                case 8:
                    return Unheard;
            }
        }


        public boolean isGreaterThanEq(Tier t) {
            return this.Rank >= t.Rank;
        }

        public boolean isGreaterThan(Tier t) {
            return this.Rank > t.Rank;
        }

        public boolean isLessThan(Tier t) {
            return this.Rank < t.Rank;
        }

        public String getString() {
            switch (GetTier(this.Rank)) {
                case Basic:
                    return "Basic";
                case Standard:
                    return "Standard";
                case Upgraded:
                    return "Lvl_3";
                case Eternal:
                    return "Eternal";
                case Legendary:
                    return "Legendary";
                case Unknown:
                default:
                    return "Unknown";
            }
        }
    }

    boolean CheckCustomName = true;
    private int cooldown;
    String lastplayer = "";
    public EnchantRarity ER;
    public Tier TTier;

    public CustomEnchantment(int id, String name, int weight, EnchantmentType type) {
        this(id, name, weight, type, Tier.Basic);
    }

    public CustomEnchantment(int id, String name, int weight, EnchantmentType type, Tier t) {
        this(id, name, weight, type, t, EnchantRarity.R50);
    }

    public CustomEnchantment(int id, String name, int weight, EnchantmentType type, Tier t, EnchantRarity er) {
        super(id, name, weight, type);
        TTier = t;
        ER = er;
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

    public static Enchantment CreateEnchant(int id) {
        return CreateEnchant(id, 1);
    }

    public static Enchantment CreateEnchant(int id, int lvl) {
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
                    return null;
                case HASTE:
                    return new HasteCE(lvl);
                //TODO need to load cools downs too!
                case CrateKey:
                    return null;
            }
        }

        return null;
    }

    public static Enchantment getEnchantFromIDFromItem(Item i, int id) {
        return getEnchantFromIDFromItem(i, (short) id);
    }

    public static Enchantment getEnchantFromIDFromItem(Item i, short id) {
        if (!i.hasEnchantments()) {
            return null;
        }

        for (CompoundTag entry : i.getNamedTag().getList("ench", CompoundTag.class).getAll()) {
            if (entry.getShort("id") == id) {
                return CreateEnchant(entry.getShort("id"), entry.getShort("lvl"));
            }
        }

        return null;
    }
    public static Enchantment[] getAllEnchantFromItem(Item i) {
        if (!i.hasEnchantments()) {
            return null;
        }

        ArrayList<Enchantment> el = new ArrayList<>();
        for (CompoundTag entry : i.getNamedTag().getList("ench", CompoundTag.class).getAll()) {
                el.add(CreateEnchant(entry.getShort("id"), entry.getShort("lvl")));
        }

        return (Enchantment[])el.toArray();
    }

    public void RunPassive() {

    }

    public static ArrayList<Enchantment> GetRandomEnchant(Tier t, int count, Item itm) {
        ArrayList<Enchantment> el = new ArrayList<>();
        ArrayList<Enchantment> fel = new ArrayList<>();
        for (int i = 0; i <= MAX; i++) {
            Enchantment e = CreateEnchant(i);
            if (e == null) continue;
            if (i > VANILLA) {//Custom Enchant
                CustomEnchantment ce = (CustomEnchantment) e;
                if (ce.TTier.isGreaterThan(t) || !ce.canEnchant(itm)) continue;
                int c = ce.ER.GetVal();
                NukkitRandom nnr = new NukkitRandom();
                NukkitRandom nr = new NukkitRandom((System.currentTimeMillis() / 1000L) / i * c + nnr.nextRange(0, 10000));
                if (c < nr.nextRange(0, 100)) continue;
            }
            el.add(e);
        }
        NukkitRandom nr2 = new NukkitRandom((System.currentTimeMillis() / 1000L)+el.size());

        for (int i = 0; i < count; i++) {
            if (el.size() == 0) {
                fel.add(null);
                continue;
            }
            int key = nr2.nextRange(0, el.size() - 1);
            fel.add(el.get(key));
            el.remove(key);
        }
        return fel;
    }
    public static ArrayList<Enchantment> GetRandomEnchant(Tier t, int count) {
        ArrayList<Enchantment> el = new ArrayList<>();
        ArrayList<Enchantment> fel = new ArrayList<>();
        for (int i = 0; i <= MAX; i++) {
            Enchantment e = CreateEnchant(i);
            if (e == null) continue;
            if (i > VANILLA) {//Custom Enchant
                CustomEnchantment ce = (CustomEnchantment) e;
                if (ce.TTier.isGreaterThan(t)) continue;
                int c = ce.ER.GetVal();
                NukkitRandom nnr = new NukkitRandom();
                NukkitRandom nr = new NukkitRandom((System.currentTimeMillis() / 1000L) / i * c + nnr.nextRange(0, 10000));
                if (c < nr.nextRange(0, 100)) continue;
            }
            el.add(e);
        }
        NukkitRandom nr2 = new NukkitRandom((System.currentTimeMillis() / 1000L));

        for (int i = 0; i < count; i++) {
            if (el.size() == 0) {
                fel.add(null);
                continue;
            }
            int key = nr2.nextRange(0, el.size() - 1);
            fel.add(el.get(key));
            el.remove(key);
        }
        return fel;
    }

    public static ArrayList<ElementLabel> PrepareEnchantList(ArrayList<Enchantment> al) {
        ArrayList<ElementLabel> el = new ArrayList<>();
        for (Enchantment e : al) {
            if(e == null){
                el.add(new ElementLabel("---------------------"));
            }else el.add(new ElementLabel(e.getName() + " | " + e.getWeight()));
        }
        return el;
    }
}
