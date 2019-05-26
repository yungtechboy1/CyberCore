package net.yungtechboy1.CyberCore.Custom;

import cn.nukkit.block.BlockID;
import cn.nukkit.inventory.transaction.data.ReleaseItemData;
import cn.nukkit.inventory.transaction.data.TransactionData;
import cn.nukkit.inventory.transaction.data.UseItemData;
import cn.nukkit.inventory.transaction.data.UseItemOnEntityData;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemBlock;
import cn.nukkit.nbt.NBTIO;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.network.protocol.DataPacket;
import cn.nukkit.network.protocol.InventoryTransactionPacket;
import cn.nukkit.network.protocol.types.NetworkInventoryAction;
import it.unimi.dsi.fastutil.io.FastByteArrayInputStream;
import net.yungtechboy1.CyberCore.Custom.Block.CustomBlockTNT;
import net.yungtechboy1.CyberCore.Custom.Block.CustomElementBlock;

import java.io.IOException;
import java.nio.ByteOrder;
import java.util.Arrays;

public class CustomInventoryTransactionPacket extends DataPacket {
    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_MISMATCH = 1;
    public static final int TYPE_USE_ITEM = 2;
    public static final int TYPE_USE_ITEM_ON_ENTITY = 3;
    public static final int TYPE_RELEASE_ITEM = 4;
    public static final int USE_ITEM_ACTION_CLICK_BLOCK = 0;
    public static final int USE_ITEM_ACTION_CLICK_AIR = 1;
    public static final int USE_ITEM_ACTION_BREAK_BLOCK = 2;
    public static final int RELEASE_ITEM_ACTION_RELEASE = 0;
    public static final int RELEASE_ITEM_ACTION_CONSUME = 1;
    public static final int USE_ITEM_ON_ENTITY_ACTION_INTERACT = 0;
    public static final int USE_ITEM_ON_ENTITY_ACTION_ATTACK = 1;
    public static final int ACTION_MAGIC_SLOT_DROP_ITEM = 0;
    public static final int ACTION_MAGIC_SLOT_PICKUP_ITEM = 1;
    public static final int ACTION_MAGIC_SLOT_CREATIVE_DELETE_ITEM = 0;
    public static final int ACTION_MAGIC_SLOT_CREATIVE_CREATE_ITEM = 1;
    public int transactionType;
    public CustomNetworkInventoryAction[] actions;
    public TransactionData transactionData;
    public boolean isCraftingPart = false;

    public CustomInventoryTransactionPacket() {
    }

    @Override
    public byte pid() {
        return 30;
    }

    @Override
    public void decode() {
        this.transactionType = (int) this.getUnsignedVarInt();

        System.out.println("QQQQQQQQQQ Starting to Decode Inv Packet, Type > "+transactionType);
        this.actions = new CustomNetworkInventoryAction[(int) this.getUnsignedVarInt()];
        for (int i = 0; i < this.actions.length; i++) {
            this.actions[i] = new CustomNetworkInventoryAction().read(this);
        }

        switch (this.transactionType) {
            case TYPE_NORMAL:
            case TYPE_MISMATCH:
                //Regular ComplexInventoryTransaction doesn't read any extra data
                break;
            case TYPE_USE_ITEM:
                UseItemData itemData = new UseItemData();

                itemData.actionType = (int) this.getUnsignedVarInt();
                itemData.blockPos = this.getBlockVector3();
                itemData.face = this.getBlockFace();
                itemData.hotbarSlot = this.getVarInt();
                System.out.println("ABOUT TO READDDDDD 44444444444");
                itemData.itemInHand = this.getSlot();
                itemData.playerPos = this.getVector3f().asVector3();
                itemData.clickPos = this.getVector3f();

                this.transactionData = itemData;
                break;
            case TYPE_USE_ITEM_ON_ENTITY:
                UseItemOnEntityData useItemOnEntityData = new UseItemOnEntityData();

                useItemOnEntityData.entityRuntimeId = this.getEntityRuntimeId();
                useItemOnEntityData.actionType = (int) this.getUnsignedVarInt();
                useItemOnEntityData.hotbarSlot = this.getVarInt();
                System.out.println("ABOUT TO READDDDDD 555555555");
                useItemOnEntityData.itemInHand = this.getSlot();
                useItemOnEntityData.playerPos = this.getVector3f().asVector3();
                useItemOnEntityData.clickPos = this.getVector3f().asVector3();

                this.transactionData = useItemOnEntityData;
                break;
            case TYPE_RELEASE_ITEM:
                ReleaseItemData releaseItemData = new ReleaseItemData();

                releaseItemData.actionType = (int) getUnsignedVarInt();
                releaseItemData.hotbarSlot = getVarInt();
                System.out.println("ABOUT TO READDDDDD 66666666");
                releaseItemData.itemInHand = getSlot();
                releaseItemData.headRot = this.getVector3f().asVector3();

                this.transactionData = releaseItemData;
                break;
            default:
                throw new RuntimeException("Unknown transaction type " + this.transactionType);
        }
    }

    @Override
    public void encode() {
        this.reset();
        this.putUnsignedVarInt(this.transactionType);

        this.putUnsignedVarInt(this.actions.length);
        for (CustomNetworkInventoryAction action : this.actions) {
            action.write(this);
        }

        switch (this.transactionType) {
            case TYPE_NORMAL:
            case TYPE_MISMATCH:
                break;
            case TYPE_USE_ITEM:
                UseItemData useItemData = (UseItemData) this.transactionData;

                this.putUnsignedVarInt(useItemData.actionType);
                this.putBlockVector3(useItemData.blockPos);
                this.putBlockFace(useItemData.face);
                this.putVarInt(useItemData.hotbarSlot);
                this.putSlot(useItemData.itemInHand);
                this.putVector3f(useItemData.playerPos.asVector3f());
                this.putVector3f(useItemData.clickPos);
                break;
            case TYPE_USE_ITEM_ON_ENTITY:
                UseItemOnEntityData useItemOnEntityData = (UseItemOnEntityData) this.transactionData;

                this.putEntityRuntimeId(useItemOnEntityData.entityRuntimeId);
                this.putUnsignedVarInt(useItemOnEntityData.actionType);
                this.putVarInt(useItemOnEntityData.hotbarSlot);
                this.putSlot(useItemOnEntityData.itemInHand);
                this.putVector3f(useItemOnEntityData.playerPos.asVector3f());
                this.putVector3f(useItemOnEntityData.clickPos.asVector3f());
                break;
            case TYPE_RELEASE_ITEM:
                ReleaseItemData releaseItemData = (ReleaseItemData) this.transactionData;

                this.putUnsignedVarInt(releaseItemData.actionType);
                this.putVarInt(releaseItemData.hotbarSlot);
                this.putSlot(releaseItemData.itemInHand);
                this.putVector3f(releaseItemData.headRot.asVector3f());
                break;
            default:
                throw new RuntimeException("Unknown transaction type " + this.transactionType);
        }
    }

    @Override
    public Item getSlot() {
        System.out.println("+++++++++++++++++++++++++++");
        int id = this.getVarInt();
        System.out.println("ID !!!!!!>>> "+id);
//    if(id < 0){
//            System.out.println("===============================b "+id);
//            id = 255 +
//        }
        if (id == 0) {
            return Item.get(0, 0, 0);
        } else {
            int auxValue = this.getVarInt();
            int data = auxValue >> 8;
            if (data == 32767) {
                data = -1;
            }
            System.out.println("Data >>> "+data);

            int cnt = auxValue & 255;
            System.out.println("CNT >>> "+cnt);
            int nbtLen = this.getLShort();
            byte[] nbt = new byte[0];
            int canPlaceOn;
            int canDestroy;
            if (nbtLen < 32767) {
                nbt = this.get(nbtLen);
            } else if (nbtLen == 65535) {
                canPlaceOn = (int)this.getUnsignedVarInt();
                canDestroy = this.getOffset();
                FastByteArrayInputStream stream = new FastByteArrayInputStream(this.get());

                for(int i = 0; i < canPlaceOn; ++i) {
                    try {
                        CompoundTag tag = NBTIO.read(stream, ByteOrder.LITTLE_ENDIAN, true);
                        nbt = NBTIO.write(tag, ByteOrder.LITTLE_ENDIAN, false);
                    } catch (IOException var12) {
                        throw new RuntimeException(var12);
                    }
                }

                this.setOffset(canDestroy + (int)stream.position());
            }

            canPlaceOn = this.getVarInt();
            if (canPlaceOn > 0) {
                for(canDestroy = 0; canDestroy < canPlaceOn; ++canDestroy) {
                    this.getString();
                }
            }

            canDestroy = this.getVarInt();
            if (canDestroy > 0) {
                for(int i = 0; i < canDestroy; ++i) {
                    this.getString();
                }
            }
//            if (id == BlockID.TNT && Item.get(id, data, cnt, nbt).getCustomName().equalsIgnoreCase("Experimental TNT ")) {
//                data = CustomBlockTNT.TNTType.Experimental.ordinal();
//                System.out.println("PRINTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT");
//            }
            return Item.get(id, data, cnt, nbt);
        }
    }

    public String toString() {
        return "InventoryTransactionPacket(transactionType=" + this.transactionType + ", actions=" + Arrays.deepToString(this.actions) + ", transactionData=" + this.transactionData + ", isCraftingPart=" + this.isCraftingPart + ")";
    }
}
