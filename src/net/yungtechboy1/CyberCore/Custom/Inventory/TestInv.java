package net.yungtechboy1.CyberCore.Custom.Inventory;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockAir;
import cn.nukkit.entity.EntityHuman;
import cn.nukkit.inventory.Inventory;
import cn.nukkit.inventory.InventoryType;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemBlock;
import cn.nukkit.item.ItemBookEnchanted;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.level.sound.AnvilUseSound;
import cn.nukkit.math.Vector3;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.network.protocol.*;
import cn.nukkit.utils.TextFormat;

import java.util.*;

/**
 * Created by carlt_000 on 1/19/2017.
 */
public class TestInv implements Inventory {

    protected final String name;
    protected final String title;
    protected final Map<Integer, Item> slots = new HashMap<>();
    protected final Set<Player> viewers = new HashSet<>();
    protected int maxStackSize = Inventory.MAX_STACK;
    protected int size;
    EntityHuman holder;
    Vector3 BA;
    Block OB;

    public TestInv(EntityHuman Holder, Vector3 ba, Block ob) {
        holder = Holder;
        this.size = 5;

        BA = ba;
        OB = ob;

        this.title = "TEST INV";

        this.name = title;
        ReloadInv();
    }


    public void Take(Player player) {
        onRename(player);//One Last Check!
        if (getItem(2).getId() != Item.ANVIL && getItem(2).getId() != 0) {
            Server.getInstance().getLogger().info("COMMMBBBIIINNNEEE@!!!!");
            setItem2(0, Item.get(0));
            setItem2(4, Item.get(0));
            player.getInventory().addItem(getItem(2).clone());
            player.getInventory().sendContents(player);
            setItem2(2, Item.get(Item.ANVIL));
        }
    }

    public boolean onRename(Player player) {
        int testing = 0;
        Item local = getItem(0);
        Item second = getItem(4);
        Item resultItem;
        Boolean book = false;
        //@TODO allow for Diamonds to repair Diamond Item
        Boolean mineral = false;

        int key = -1;
        if (local.getId() == Item.ENCHANT_BOOK || second.getId() == Item.ENCHANT_BOOK) {
            book = true;
            if (local.getId() == Item.ENCHANT_BOOK) key = 4;
            if (second.getId() == Item.ENCHANT_BOOK) key = 0;
        } else if (!local.equals(second, false, false)) {
            //ITEMS NOT EQUAL!
            Server.getInstance().getLogger().info("TESTING WITH ID 0");
            return false;
        }

        //Check Penalty - Max can change :) - 63 is after 6 Workins
        int sp = 0;
        int lp = 0;
        if (second.hasCompoundTag()) sp = second.getNamedTag().getInt("penalty");
        if (local.hasCompoundTag()) lp = local.getNamedTag().getInt("penalty");
        if (Math.max(sp, lp) > 63) {
            //Cant do it!
            Server.getInstance().getLogger().info("TESTING WITH ID 1");
            return false;
        }

        if (local.getId() == Item.ENCHANT_BOOK && second.getId() == Item.ENCHANT_BOOK) {
            resultItem = new ItemBookEnchanted();
        } else if (key == -1) {
            resultItem = Item.get(getItem(0).getId());
        } else {
            resultItem = Item.get(getItem(key).getId());
        }

        //BEGIN COMBINEING!
        if (local.getId() != 0 && second.getId() != 0) {
            //enchants combining
            if (true) {
                int enchants = 0;

                ArrayList<Enchantment> enchantments = new ArrayList<>(Arrays.asList(second.getEnchantments()));
                ArrayList<Enchantment> enchantments2 = new ArrayList<>(Arrays.asList(local.getEnchantments()));

                ArrayList<Enchantment> baseEnchants = new ArrayList<>();

                for (Enchantment ench : local.getEnchantments()) {
                    if (ench.isMajor()) {
                        baseEnchants.add(ench);
                    }
                }

                //FIRST LOOP
                for (Enchantment enchantment : enchantments) {
                    if (enchantment.getLevel() < 0 || enchantment.getId() < 0) {
                        continue;
                    }

                    Boolean major = false;
                    //Major Means that if 2 Enchants are the Same then only the Higest level is applied
                    //And the levels are not combined!
                    //But if you have the Same level Enchants on both and the enchant is major then 1 level is added!\
                    //Example SMite 2 & Smite 2 would combine to be Smite 3
                    //Example Smite 3 & Smike 2 Would combine to be Smike 3
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

                        if (!same && another) major = true;
                    }


                    Enchantment localEnchantment = local.getEnchantment(enchantment.getId());
                    if (localEnchantment != null) {
                        int level = 0;
                        if (major) {
                            level = Math.max(localEnchantment.getLevel(), enchantment.getLevel());
                            if (localEnchantment.getLevel() == enchantment.getLevel()) level++;
                        } else {
                            level = Math.min(localEnchantment.getLevel() + enchantment.getLevel(), localEnchantment.getMaxLevel());
                        }
                        enchantment.setLevel(level);
                        resultItem.addEnchantment(enchantment);
                    } else {
                        resultItem.addEnchantment(enchantment);
                    }
                    enchants++;
                }

                //SECCOND LOOP
                for (Enchantment enchantment : enchantments2) {
                    boolean b = false;
                    for (Enchantment e : enchantments) {
                        if (e.getId() == enchantment.getId()) {
                            b = true;
                            break;
                        }
                    }
                    if (b) continue;
                    if (enchantment.getLevel() < 0 || enchantment.getId() < 0) {
                        continue;
                    }

                    Boolean major = false;
                    //Major Means that if 2 Enchants are the Same then only the Higest level is applied
                    //And the levels are not combined!
                    //But if you have the Same level Enchants on both and the enchant is major then 1 level is added!\
                    //Example SMite 2 & Smite 2 would combine to be Smite 3
                    //Example Smite 3 & Smike 2 Would combine to be Smike 3
                    if (enchantment.isMajor()) {
                        boolean same = false;
                        boolean another = false;

                        for (Enchantment baseEnchant : second.getEnchantments()) {
                            if (!baseEnchant.isMajor()) continue;
                            if (baseEnchant.getId() == enchantment.getId())
                                same = true;
                            else {
                                another = true;
                            }
                        }
                        if (!same && another) major = true;
                    }
                    resultItem.addEnchantment(enchantment);
                    enchants++;
                }
            }

            //Damage Combining!
            if (!book) {
                //Books Don't reduce Damage!
                if (local.getTier() == second.getTier()) {//Fast and easy way to make sure items are in same type
                    int ld = second.getMaxDurability() - local.getDamage();//8 -> 18
                    int sd = second.getMaxDurability() - second.getDamage();//25 -> 1
                    //IDK About the minecraft Bonus.....
                    int d1 = (resultItem.getMaxDurability() - (ld + sd)) - (int) Math.floor(resultItem.getMaxDurability() / 20d);
                    int d2 = Math.max(0, d1);
                    resultItem.setDamage(d2);
                }

            }

            //FUCK CUSTOM NAMES!

            int pl = sp + lp + 1;
            if (player.getExperienceLevel() < pl) {
                //Not Enough EXP!
                Server.getInstance().getLogger().info("TESTING WITH ID 3");
                return false;
            }

            //@TODO NOT Yet
            //player.setExperience(0,player.getExperienceLevel() - pl);
            if (resultItem.hasCompoundTag()) {
                resultItem.getNamedTag().putInt("penalty", Math.max(sp, lp) * 2 + 1);
            } else {
                CompoundTag tag = new CompoundTag();
                tag.putInt("penalty", Math.max(sp, lp) * 2 + 1);
                resultItem.setNamedTag(tag);
            }
            setItem(2, resultItem);
            Item t = getItem(1);
            t.setCustomName(TextFormat.GREEN+"CLICK ME TO COMBINE"+TextFormat.RESET+"\n"+TextFormat.GREEN+"EXP Required: " + pl);
            setItem(2, resultItem);
            setItem(1, t);
            setItem(3, t);
            sendContents(player);
            player.getLevel().addSound(new AnvilUseSound(player));
            return true;
        }
        Server.getInstance().getLogger().info("TESTING WITH ID>>> 5");
        return false;
    }

    @Override
    public void onOpen(Player who) {

        UpdateBlockPacket fullBlock1 = new UpdateBlockPacket();
        fullBlock1.x = (int) BA.x;
        fullBlock1.y = (int) BA.y - 2;
        fullBlock1.z = (int) BA.z;
        fullBlock1.blockId = Block.CHEST;
        fullBlock1.blockData = 0;
        fullBlock1.flags = 0;
        who.dataPacket(fullBlock1);

        this.viewers.add(who);
        ContainerOpenPacket pk = new ContainerOpenPacket();
        pk.windowid = (byte) who.getWindowId(this);
        pk.type = (byte) this.getType().getNetworkType();
        //pk.type = 9;
        pk.slots = 9;
        pk.x = BA.getFloorX();
        pk.y = BA.getFloorY() - 2;
        pk.z = BA.getFloorZ();

        Server.getInstance().broadcastMessage(BA.getX() + "|{2}" + BA.getY() + "|" + BA.getZ());
        /*pk.x = 85;
        pk.y = 77;
        pk.z = 323;*/
        //57.0|83.0|336.0

        who.batchDataPacket(pk);
        this.sendContents(who);
    }

    @Override
    public void onClose(Player who) {
        Server.getInstance().broadcastMessage("CLOSE ER");
        UpdateBlockPacket fullBlock1 = new UpdateBlockPacket();
        fullBlock1.x = (int) BA.x;
        fullBlock1.y = BA.getFloorY() - 2;
        fullBlock1.z = (int) BA.z;
        fullBlock1.blockId = OB.getId();
        fullBlock1.blockData = OB.getDamage();
        ;
        fullBlock1.flags = 0;
        who.dataPacket(fullBlock1);
        Server.getInstance().broadcastMessage(BA.getX() + "|{2}" + BA.getY() + "|" + BA.getZ());
        ContainerClosePacket pk = new ContainerClosePacket();
        pk.windowid = (byte) who.getWindowId(this);
        who.dataPacket(pk);
        this.viewers.remove(who);
        who.getInventory().sendContents(who);
        who.getInventory().sendHeldItem(who);
        if (getItem(0).getId() != 0) this.getHolder().getLevel().dropItem(BA.add(0.5, 0.5, 0.5), this.getItem(0));
        if (getItem(4).getId() != 0) this.getHolder().getLevel().dropItem(BA.add(0.5, 0.5, 0.5), this.getItem(4));
    }

    @Override
    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public int getMaxStackSize() {
        return maxStackSize;
    }

    @Override
    public void setMaxStackSize(int maxStackSize) {
        this.maxStackSize = maxStackSize;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public Item getItem(int index) {
        return this.slots.containsKey(index) ? this.slots.get(index).clone() : new ItemBlock(new BlockAir(), null, 0);
    }

    @Override
    public Map<Integer, Item> getContents() {
        return new HashMap<>(this.slots);
    }

    @Override
    public void setContents(Map<Integer, Item> items) {

        Server.getInstance().broadcastMessage("REMOVE SETTTCCC!1");
        ReloadInv();
    }

    public void ReloadInv() {
        this.slots.clear();
        this.slots.put(0, Item.get(0));
        this.slots.put(1, Item.get(Item.SLIME_BLOCK));
        this.slots.put(2, Item.get(Item.ANVIL));
        this.slots.put(3, Item.get(Item.SLIME_BLOCK));
        this.slots.put(4, Item.get(0));
        onSlotChange(0, null);
        onSlotChange(1, null);
        onSlotChange(2, null);
        onSlotChange(3, null);
        onSlotChange(4, null);
    }

    public void setItem2(int index, Item item) {
        this.slots.put(index, item.clone());
        this.onSlotChange(index, null);
    }


    public int i0 = 0;
    public int i4 = 0;
    @Override
    public boolean setItem(int index, Item item) {
        Server.getInstance().broadcastMessage("SET IMTE!");
        item = item.clone();
        if (index < 0 || index >= this.size) {
            return false;
        } else if (item.getId() == 0 || item.getCount() <= 0) {
            return this.clear(index);
        }


        Item old = this.getItem(index);
        this.slots.put(index, item.clone());
        this.onSlotChange(index, old);
        //if (getItem(0).getId() == 0 || getItem(4).getId() == 0) setItem2(2, Item.get(Item.ANVIL));
        if (getItem(0).getId() != 0 && getItem(4).getId() != 0 && getItem(0).getId() != i0 && getItem(4).getId() != i4 ) {
            i0 = getItem(0).getId();
            i4 = getItem(4).getId();
            onRename((Player) holder);
        }

        return true;
    }

    @Override
    public boolean contains(Item item) {
        int count = Math.max(1, item.getCount());
        boolean checkDamage = item.hasMeta();
        boolean checkTag = item.getCompoundTag() != null;
        for (Item i : this.getContents().values()) {
            if (item.equals(i, checkDamage, checkTag)) {
                count -= i.getCount();
                if (count <= 0) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public Map<Integer, Item> all(Item item) {
        Map<Integer, Item> slots = new HashMap<>();
        boolean checkDamage = item.hasMeta();
        boolean checkTag = item.getCompoundTag() != null;
        for (Map.Entry<Integer, Item> entry : this.getContents().entrySet()) {
            if (item.equals(entry.getValue(), checkDamage, checkTag)) {
                slots.put(entry.getKey(), entry.getValue());
            }
        }

        return slots;
    }

    @Override
    public void remove(Item item) {
        Server.getInstance().broadcastMessage("REMOVE IMTE!1");
        boolean checkDamage = item.hasMeta();
        boolean checkTag = item.getCompoundTag() != null;
        for (Map.Entry<Integer, Item> entry : this.getContents().entrySet()) {
            if (item.equals(entry.getValue(), checkDamage, checkTag)) {
                this.clear(entry.getKey());
            }
        }
    }

    @Override
    public int first(Item item) {
        int count = Math.max(1, item.getCount());
        boolean checkDamage = item.hasMeta();
        boolean checkTag = item.getCompoundTag() != null;
        for (Map.Entry<Integer, Item> entry : this.getContents().entrySet()) {
            if (item.equals(entry.getValue(), checkDamage, checkTag) && entry.getValue().getCount() >= count) {
                return entry.getKey();
            }
        }

        return -1;
    }

    @Override
    public int firstEmpty(Item item) {
        for (int i = 0; i < this.size; ++i) {
            if (this.getItem(i).getId() == Item.AIR) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public boolean canAddItem(Item item) {
        Server.getInstance().broadcastMessage("CAN ADD IMTE!");
        item = item.clone();
        boolean checkDamage = item.hasMeta();
        boolean checkTag = item.getCompoundTag() != null;
        for (int i = 0; i < this.getSize(); ++i) {
            Item slot = this.getItem(i);
            if (item.equals(slot, checkDamage, checkTag)) {
                int diff;
                if ((diff = slot.getMaxStackSize() - slot.getCount()) > 0) {
                    item.setCount(item.getCount() - diff);
                }
            } else if (slot.getId() == Item.AIR) {
                item.setCount(item.getCount() - this.getMaxStackSize());
            }

            if (item.getCount() <= 0) {
                return true;
            }
        }

        return false;
    }

    @Override
    public Item[] addItem(Item... slots) {
        Server.getInstance().broadcastMessage("ADD IMTE!");
        List<Item> itemSlots = new ArrayList<>();
        for (Item slot : slots) {
            if (slot.getId() != 0 && slot.getCount() > 0) {
                itemSlots.add(slot.clone());
            }
        }

        List<Integer> emptySlots = new ArrayList<>();

        for (int i = 0; i < this.getSize(); ++i) {
            //Only 0 and 4 Slot are editable!
            if (i != 0 && i != 4) continue;
            Item item = this.getItem(i);
            if (item.getId() == Item.AIR || item.getCount() <= 0) {
                emptySlots.add(i);
            }

            for (Item slot : new ArrayList<>(itemSlots)) {
                if (slot.equals(item) && item.getCount() < item.getMaxStackSize()) {
                    int amount = Math.min(item.getMaxStackSize() - item.getCount(), slot.getCount());
                    amount = Math.min(amount, this.getMaxStackSize());
                    if (amount > 0) {
                        slot.setCount(slot.getCount() - amount);
                        item.setCount(item.getCount() + amount);
                        this.setItem(i, item);
                        if (slot.getCount() <= 0) {
                            itemSlots.remove(slot);
                        }
                    }
                }
            }
            if (itemSlots.isEmpty()) {
                break;
            }
        }

        if (!itemSlots.isEmpty() && !emptySlots.isEmpty()) {
            for (int slotIndex : emptySlots) {
                if (!itemSlots.isEmpty()) {
                    Item slot = itemSlots.get(0);
                    int amount = Math.min(slot.getMaxStackSize(), slot.getCount());
                    amount = Math.min(amount, this.getMaxStackSize());
                    slot.setCount(slot.getCount() - amount);
                    Item item = slot.clone();
                    item.setCount(amount);
                    this.setItem(slotIndex, item);
                    if (slot.getCount() <= 0) {
                        itemSlots.remove(slot);
                    }
                }
            }
        }

        return itemSlots.stream().toArray(Item[]::new);
    }

    @Override
    public Item[] removeItem(Item... slots) {
        Server.getInstance().broadcastMessage("REMOVE IMTE!");
        List<Item> itemSlots = new ArrayList<>();
        for (Item slot : slots) {
            if (slot.getId() != 0 && slot.getCount() > 0) {
                itemSlots.add(slot.clone());
            }
        }

        for (int i = 0; i < this.getSize(); ++i) {
            if (i != 0 && i != 4) continue;
            Item item = this.getItem(i);
            if (item.getId() == Item.AIR || item.getCount() <= 0) {
                continue;
            }

            for (Item slot : new ArrayList<>(itemSlots)) {
                if (slot.equals(item, item.hasMeta(), item.getCompoundTag() != null)) {
                    int amount = Math.min(item.getCount(), slot.getCount());
                    slot.setCount(slot.getCount() - amount);
                    item.setCount(item.getCount() - amount);
                    this.setItem(i, item);
                    if (slot.getCount() <= 0) {
                        itemSlots.remove(slot);
                    }

                }
            }

            if (itemSlots.size() == 0) {
                break;
            }
        }

        return itemSlots.stream().toArray(Item[]::new);
    }

    @Override
    public boolean clear(int index) {
        Server.getInstance().broadcastMessage("CLR IMTE!");
        if (this.slots.containsKey(index)) {
            Item item = new ItemBlock(new BlockAir(), null, 0);
            Item old = this.slots.get(index);

            if (item.getId() != Item.AIR) {
                this.slots.put(index, item.clone());
            } else {
                this.slots.remove(index);
            }
            //if(index == 0 ||index == 4)setItem2(2, Item.get(Item.ANVIL));
            this.onSlotChange(index, old);
        }

        return true;
    }

    @Override
    public void clearAll() {
        for (Integer index : this.getContents().keySet()) {
            this.clear(index);
        }
    }

    @Override
    public Set<Player> getViewers() {
        return viewers;
    }

    @Override
    public EntityHuman getHolder() {
        return (EntityHuman) this.holder;
    }

    @Override
    public boolean open(Player who) {
        this.onOpen(who);

        return true;
    }

    @Override
    public void close(Player who) {
        this.onClose(who);
    }

    @Override
    public void onSlotChange(int index, Item before) {
        this.sendSlot(index, this.getViewers());
    }

    @Override
    public void sendContents(Player player) {
        this.sendContents(new Player[]{player});
    }

    @Override
    public void sendContents(Player[] players) {
        ContainerSetContentPacket pk = new ContainerSetContentPacket();
        pk.slots = new Item[this.getSize()];
        for (int i = 0; i < this.getSize(); ++i) {
            pk.slots[i] = this.getItem(i);
        }

        for (Player player : players) {
            int id = player.getWindowId(this);
            if (id == -1 || !player.spawned) {
                this.close(player);
                continue;
            }
            pk.windowid = (byte) id;
            player.batchDataPacket(pk);
        }
    }

    @Override
    public void sendContents(Collection<Player> players) {
        this.sendContents(players.stream().toArray(Player[]::new));
    }

    @Override
    public void sendSlot(int index, Player player) {
        this.sendSlot(index, new Player[]{player});
    }

    @Override
    public void sendSlot(int index, Player[] players) {
        ContainerSetSlotPacket pk = new ContainerSetSlotPacket();
        pk.slot = index;
        pk.item = this.getItem(index).clone();

        for (Player player : players) {
            int id = player.getWindowId(this);
            if (id == -1) {
                this.close(player);
                continue;
            }
            pk.windowid = (byte) id;
            player.dataPacket(pk);
        }
    }

    @Override
    public void sendSlot(int index, Collection<Player> players) {
        this.sendSlot(index, players.stream().toArray(Player[]::new));
    }

    @Override
    public InventoryType getType() {
        return InventoryType.CHEST;
    }
}
