package net.yungtechboy1.CyberCore.Manager.AnitCheat;

import cn.nukkit.Player;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.inventory.InventoryTransactionEvent;
import cn.nukkit.network.protocol.*;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.network.protocol.DataPacket;
import cn.nukkit.network.protocol.ProtocolInfo;
import net.yungtechboy1.CyberCore.CyberCoreMain;

public class AntiCheatManger {
    public CyberCoreMain Main;

    public AntiCheatManger(CyberCoreMain main) {
        Main = main;
    }

    public void OnPacket(DataPacket packet){

        if (packet.pid() == ProtocolInfo.INVENTORY_ACTION_PACKET){

        Player p = null;
            InventoryTransactionPacket transactionPacket = (InventoryTransactionPacket) packet;
        }
                switch (transactionPacket.transactionType) {
                    case InventoryTransactionPacket.TYPE_USE_ITEM_ON_ENTITY:
                        type = useItemOnEntityData.actionType;
                        switch (type) {
                            case InventoryTransactionPacket.USE_ITEM_ON_ENTITY_ACTION_ATTACK:
    }

    //Check Kill Aura and Class Attack Debuffs
    public void OnInteractEvent(PlayerInteractEvent e){
        if(e.getAction() == cn.nukkit.network.protocol.InventoryTransactionPacket.USE_ITEM_ON_ENTITY_ACTION_ATTACK){
            EntityDamageByEntityEvent ee = (EntityDamageByEntityEvent) e;
        }
                //EntityDamageByEntityEvent
    }
}
