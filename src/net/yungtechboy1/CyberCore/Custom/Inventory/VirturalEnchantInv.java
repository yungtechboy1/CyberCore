package net.yungtechboy1.CyberCore.Custom.Inventory;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockAir;
import cn.nukkit.item.*;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.item.enchantment.EnchantmentEntry;
import cn.nukkit.item.enchantment.EnchantmentList;
import cn.nukkit.math.NukkitRandom;
import cn.nukkit.math.Vector3;
import cn.nukkit.utils.DyeColor;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.Custom.CustomEnchant.CustomEnchantment;
import net.yungtechboy1.CyberCore.Custom.Item.CItemBookEnchanted;
import net.yungtechboy1.CyberCore.CyberCoreMain;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by carlt_000 on 1/5/2017.
 */
public class VirturalEnchantInv {
    private final Random random = new Random();
    CyberCoreMain m;
    Player p;
    Item i;
    public Integer lapiscount = 0;
    private int bookshelfAmount = 0;

    private int[] levels = null;
    private EnchantmentEntry[] entries = null;
    ArrayList<Item> BookArray = new ArrayList<Item>(){{
        add(new ItemSwordDiamond());
        add(new ItemShovelDiamond());
        add(new ItemPickaxeDiamond());
        add(new ItemHoeDiamond());
        add(new ItemHelmetDiamond());
        add(new ItemChestplateDiamond());
        add(new ItemLeggingsDiamond());
        add(new ItemBootsDiamond());
    }};

    public VirturalEnchantInv(CyberCoreMain main, Player player, Item hand) {
        this.m = main;
        this.p = player;
        this.i = hand;
        ArrayList<Integer> air = new ArrayList<>();
        for (Map.Entry <Integer,Item> a : p.getInventory().getContents().entrySet()) {
            Item item = a.getValue();
            if (item.getId() == Item.DYE && item.getDamage() == DyeColor.BLUE.getDyeData()) {
                lapiscount += item.getCount();
                air.add(a.getKey());
            }
        }
        for(Integer die: air){
            p.getInventory().clear(die);
        }
        p.getInventory().setItemInHand(new ItemBlock(new BlockAir(), (Integer)null, 0));
    }

    public void cancel(){
        Item dye = new ItemDye(ItemDye.LIGHT_BLUE,lapiscount);
        Item hand = i;
        p.getInventory().addItem(dye,hand);
    }

    public void init() {
        this.levels = new int[3];
        this.bookshelfAmount = this.countBookshelf();

        if (this.bookshelfAmount < 0) {
            this.bookshelfAmount = 0;
        }

        if (this.bookshelfAmount > 15) {
            this.bookshelfAmount = 15;
        }

        NukkitRandom random = new NukkitRandom();

        double base = (double) random.nextRange(1, 8) + (bookshelfAmount / 2d) + (double) random.nextRange(0, bookshelfAmount);
        this.levels[0] = (int) Math.max(base / 3, 1);
        this.levels[1] = (int) ((base * 2) / 3 + 1);
        this.levels[2] = (int) Math.max(base, bookshelfAmount * 2);
    }

    public Integer countBookshelf() {
        Integer count = 0;
        for (int x = -2; x < 2; x++) {
            for (int y = -3; y < 3; y++) {
                for (int z = -2; z < 2; z++) {
                    Block b = p.getLevel().getBlock(new Vector3(p.getFloorX() + x, p.getFloorY() + y, p.getFloorZ() + z));
                    if (b.getId() == Block.BOOKSHELF) count++;
                }
            }
        }
        return count;
    }

    public void Calculate() {
        init();
        int enchantAbility = i.getEnchantAbility();
        this.entries = new EnchantmentEntry[3];
        for (int ii = 0; ii < 3; ii++) {
            Item bi = BookArray.get(new NukkitRandom().nextRange(0,7));
            ArrayList<Enchantment> result = new ArrayList<>();

            int level = this.levels[ii];
            int k = level;
            k += ThreadLocalRandom.current().nextInt(0, Math.round(enchantAbility / 2f));
            k += ThreadLocalRandom.current().nextInt(0, Math.round(enchantAbility / 2f));
            k++;
            float bonus = (ThreadLocalRandom.current().nextFloat() + ThreadLocalRandom.current().nextFloat() - 1) * 0.15f + 1;
            int modifiedLevel = (int) (k * (1 + bonus) + 0.5f);

            ArrayList<Enchantment> possible = new ArrayList<>();
            for (Enchantment enchantment : Enchantment.getEnchantments()) {
                if (enchantment.canEnchant(i) || (i.getId() == Item.BOOK && enchantment.canEnchant(bi))) {
                    for (int enchLevel = enchantment.getMinLevel(); enchLevel < enchantment.getMaxLevel(); enchLevel++) {
                        if (modifiedLevel >= enchantment.getMinEnchantAbility(enchLevel) && modifiedLevel <= enchantment.getMaxEnchantAbility(enchLevel)) {
                            enchantment.setLevel(enchLevel);
                            possible.add(enchantment);
                        }
                    }

                }
            }

            int[] weights = new int[possible.size()];
            int total = 0;

            for (int j = 0; j < weights.length; j++) {
                int weight = possible.get(j).getWeight();
                weights[j] = weight;
                total += weight;
            }

            int v = ThreadLocalRandom.current().nextInt(total + 1);

            int sum = 0;
            int key;
            for (key = 0; key < weights.length; ++key) {
                sum += weights[key];
                if (sum >= v) {
                    key++;
                    break;
                }
            }
            key--;

            Enchantment enchantment = possible.get(key);
            result.add(enchantment);
            possible.remove(key);

            //Extra enchantment
            while (!possible.isEmpty()) {
                modifiedLevel = Math.round(modifiedLevel / 2f);
                v = ThreadLocalRandom.current().nextInt(0, 51);
                if (v <= (modifiedLevel + 1)) {

                    for (Enchantment e : new ArrayList<>(possible)) {
                        if (!e.isCompatibleWith(enchantment)) {
                            possible.remove(e);
                        }
                    }

                    weights = new int[possible.size()];
                    total = 0;

                    for (int j = 0; j < weights.length; j++) {
                        int weight = possible.get(j).getWeight();
                        weights[j] = weight;
                        total += weight;
                    }

                    v = ThreadLocalRandom.current().nextInt(total + 1);
                    sum = 0;
                    for (key = 0; key < weights.length; ++key) {
                        sum += weights[key];
                        if (sum >= v) {
                            key++;
                            break;
                        }
                    }
                    key--;

                    if(key > possible.size()-1 || key > possible.size()){
                        cancel();
                        return;
                    }
                    enchantment = possible.get(key);
                    result.add(enchantment);
                    possible.remove(key);
                } else {
                    break;
                }
            }

            this.entries[ii] = new EnchantmentEntry(result.stream().toArray(Enchantment[]::new), level, Enchantment.getRandomName());
        }
        this.sendEnchantmentList();
    }


    public void sendEnchantmentList() {
        String message = TextFormat.GRAY + "=========[" + TextFormat.BOLD + "§eTERRA§6TIDE" + TextFormat.RESET + "" + TextFormat.GRAY + "]=========";
        EnchantmentList list = new EnchantmentList(this.entries.length);
        for (int i = 0; i < list.getSize(); i++) {
            list.setSlot(i, this.entries[i]);
            message += TextFormat.RESET + "\n" + TextFormat.GRAY + " [" + (i+1) + "] > ";
            for (Enchantment e : entries[i].getEnchantments()) {
                message += "[" + FormatName(e.getName()) + " Lvl " + e.getLevel() + "] ";
            }
            message += "for "+entries[i].getCost()+" Levels";
        }
        message += TextFormat.GRAY + "\n============================";
        p.sendMessage(message);
    }

    public boolean onEnchant(Integer id) {
        int xp = p.getExperience();
        int xpl = p.getExperienceLevel();
        Enchantment[] es = entries[id-1].getEnchantments();
        Integer cost = entries[id-1].getCost();
        Integer lcost = id;
        //Check Lapis
        if(lcost > lapiscount){
            p.sendMessage(TextFormat.RED+"Error! This enchant requires "+id+" Lapis!");
            return false;
        }
        //Check Level Cost
        if(cost > p.getExperienceLevel()){
            p.sendMessage(TextFormat.RED+"Error! This enchant requires "+cost+" Exp Levels!");
            return false;
        }
        if(i.getId() == Item.BOOK){
            i = new CItemBookEnchanted(i.getDamage(),i.count);
        }

        i.addEnchantment(es);
        lapiscount -= lcost;
        p.setExperience(0, xpl - cost);
        p.addExperience(xp);


        if(lapiscount != 0){
            p.getInventory().addItem(new ItemDye(DyeColor.BLUE,lapiscount));
        }
        for(Enchantment e: es){
            if(e instanceof CustomEnchantment){
                i = CustomEnchantment.SetCustomName(i);
                break;
            }
        }
        p.getInventory().addItem(i);
        p.sendMessage(TextFormat.GREEN+"Item Enchanted!");
        return true;
    }

    public String FormatName(String name){
        switch (name){
            case "%enchantment.protect.all":return "Protection";
            case "%enchantment.protect.fire":return "Fire Protection";
            case "%enchantment.protect.fall":return "Feather Falling";
            case "%enchantment.protect.explosion":return "Blast Protection";
            case "%enchantment.protect.projectile":return "Projectile Protection";
            case "%enchantment.thorns":return "Thorns";
            case "%enchantment.oxygen":return "Respiration";
            case "%enchantment.waterWorker":return "Water Worker";
            case "%enchantment.waterWalker":return "Depth Strider";
            case "%enchantment.damage.all":return "Sharpness";
            case "%enchantment.damage.undead":return "Smite";
            case "%enchantment.damage.arthropods":return "Arthropods";
            case "%enchantment.knockback":return "Knockback";
            case "%enchantment.fire":return "Fire Aspect";
            case "%enchantment.lootBonus":return "Loot Bonus";
            case "%enchantment.digging":return "Efficiency";
            case "%enchantment.untouching":return "Silk Touch";
            case "%enchantment.durability":return "Durability";
            case "%enchantment.lootBonusDigger":return "Loot Digging";
            case "%enchantment.arrowDamage":return "Bow PowerAbstract";
            case "%enchantment.arrowKnockback":return "Bow Knockback";
            case "%enchantment.arrowFire":return "Bow Flame";
            case "%enchantment.arrowInfinite":return "Arrow Infinity";
            case "%enchantment.lootBonusFishing":return "Looting Fishing";
            case "%enchantment.fishingSpeed":return "Lure";
            default:return "~UNKNOWN~";
        }
    }

}

