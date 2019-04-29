package net.yungtechboy1.CyberCore.Factory.AuctionHouse;

import cn.nukkit.item.Item;
import net.yungtechboy1.CyberCore.CorePlayer;

/**
 * Created by carlt on 4/28/2019.
 */
public class AuctionItemData {
    Item item;
    double Cost;
    CorePlayer Seller;
    long SellBy;
    public AuctionItemData(Item i, double cost, CorePlayer seller){
        item = i;
        Cost = cost;
        Seller = seller;
    }
}
