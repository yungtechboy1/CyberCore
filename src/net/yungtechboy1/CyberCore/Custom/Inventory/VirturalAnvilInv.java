package net.yungtechboy1.CyberCore.Custom.Inventory;

import cn.nukkit.Player;
import cn.nukkit.block.BlockAir;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemBlock;
import net.yungtechboy1.CyberCore.CyberCoreMain;

import java.util.ArrayList;

/**
 * Created by carlt_000 on 1/18/2017.
 */
public class VirturalAnvilInv {

    public static final int TARGET = 0;
    public static final int SACRIFICE = 1;
    public static final int RESULT = 2;

    CyberCoreMain m;
    Player p;
    Item i1;
    Item i2;

    public VirturalAnvilInv(CyberCoreMain main, Player player, Item firstitem) {
        this.m = main;
        this.p = player;
        this.i1 = firstitem.clone();
        ArrayList<Integer> air = new ArrayList<>();
        p.getInventory().setItemInHand(new ItemBlock(new BlockAir(), (Integer)null, 0));
    }
/*
    public void AddItem(){
        Item item = p.getInventory().getItemInHand();
        if(!(item instanceof ItemTool) && !(item instanceof ItemArmor) && !(item instanceof CItemBookEnchanted)){
            //Not a Valid Item!
        }
        i2 = item.clone();
        p.getInventory().setItemInHand(new ItemBlock(new BlockAir(), (Integer)null, 0));
    }

    public boolean onRename(Player player, Item resultItem) {
        Item local = getItem(TARGET);
        Item second = getItem(SACRIFICE);

        if (!resultItem.deepEquals(local, true, false) || resultItem.getCount() != local.getCount()) {
            //Item does not match target item. Everything must match except the tags.
            return false;
        }

        if (local.deepEquals(resultItem)) {
            //just item transaction
            return true;
        }

        if (local.getId() != 0 && second.getId() == 0) { //only rename
            local.setCustomName(resultItem.getCustomName());
            setItem(RESULT, local);
            player.getInventory().addItem(local);
            clearAll();
            player.getInventory().sendContents(player);
            sendContents(player);

            player.getLevel().addSound(new AnvilUseSound(player));
            return true;
        } else if (local.getId() != 0 && second.getId() != 0) { //enchants combining
            if (!local.equals(second, true, false)) {
                return false;
            }

            if (local.getId() != 0 && second.getId() != 0) {
                Item result = local.clone();
                int enchants = 0;

                ArrayList<Enchantment> enchantments = new ArrayList<>(Arrays.asList(second.getEnchantments()));

                ArrayList<Enchantment> baseEnchants = new ArrayList<>();

                for (Enchantment ench : local.getEnchantments()) {
                    if (ench.isMajor()) {
                        baseEnchants.add(ench);
                    }
                }

                for (Enchantment enchantment : enchantments) {
                    if (enchantment.getLevel() < 0 || enchantment.getId() < 0) {
                        continue;
                    }

                    if (enchantment.isMajor()) {
                        boolean same = false;
                        boolean another = false;

                        for (Enchantment baseEnchant : baseEnchants) {
                            if (baseEnchant.getId() == enchantment.getId())
                                same = true;
                            else {
                                another = true;
                            }
                        }

                        if (!same && another) {
                            continue;
                        }
                    }

                    Enchantment localEnchantment = local.getEnchantment(enchantment.getId());

                    if (localEnchantment != null) {
                        int level = Math.max(localEnchantment.getLevel(), enchantment.getLevel());

                        if (localEnchantment.getLevel() == enchantment.getLevel())
                            level++;

                        enchantment.setLevel(level);
                        result.addEnchantment(enchantment);
                        continue;
                    }

                    result.addEnchantment(enchantment);
                    enchants++;
                }

                result.setCustomName(resultItem.getCustomName());

                player.getInventory().addItem(result);
                player.getInventory().sendContents(player);
                clearAll();
                sendContents(player);

                player.getLevel().addSound(new AnvilUseSound(player));
                return true;
            }
        }

        return false;
    }

    @Override
    public void onClose(Player who) {
        super.onClose(who);
        who.craftingType = Player.CRAFTING_SMALL;

        for (int i = 0; i < 2; ++i) {
            this.getHolder().getLevel().dropItem(this.getHolder().add(0.5, 0.5, 0.5), this.getItem(i));
            this.clear(i);
        }
    }

    @Override
    public void onOpen(Player who) {
        super.onOpen(who);
        who.craftingType = Player.CRAFTING_ANVIL;
    }*/
}