package net.yungtechboy1.CyberCore.Manager.Form.Windows.Shop;

import cn.nukkit.form.element.ElementButton;
import cn.nukkit.item.Item;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.Factory.Shop.ShopFactory;
import net.yungtechboy1.CyberCore.Factory.Shop.ShopMysqlData;
import net.yungtechboy1.CyberCore.FormType;
import net.yungtechboy1.CyberCore.Manager.Form.CyberFormSimple;

public class ShopConfirmItemPurchase extends CyberFormSimple {
    private ShopMysqlData _SMD;
    private CorePlayer _CP;
    //    private String _M;
    private ShopFactory _SF;
    private boolean buy = false;

    public ShopConfirmItemPurchase(ShopMysqlData smd, CorePlayer cp, ShopFactory af, boolean buy) {
        this(smd,cp,af,buy,"");
    }
    public ShopConfirmItemPurchase(ShopMysqlData smd, CorePlayer cp, ShopFactory af, boolean buy, String msg) {
        super(FormType.MainForm.Shop_ConfirmPurchase, "~~| Shop Confirm Purchase |~~");
        _SMD = smd;
        _SF = af;
        this.buy = buy;
        //        _M = msg;
        if (buy) setupBuy();
        else setupSell();
        handleMsg(msg);
        addButton(new ElementButton("Return to Buy/Sell"));
        Item ip = smd.getItem(true);
        Item i = smd.getItem();

//        addElement(new ElementLabel(TextFormat.GOLD + "You have " + TextFormat.GREEN + cp.getMoney() + TextFormat.GOLD + " Money!"));//0
//        addElement(new ElementLabel("---------- Buy Section ----------"));//1
//        if (mb == 0) {
//            addElement(new ElementLabel(TextFormat.RED + "Error! You do not have enough money to purchase this for " + TextFormat.YELLOW + smd.getPrice() + TextFormat.RED + " Money!"));
//        } else {
//            addElement(new ElementLabel(TextFormat.YELLOW + "You Can Buy " + TextFormat.GREEN + mb + TextFormat.YELLOW + " for " + TextFormat.GREEN + (mb * smd.getPrice()) + TextFormat.YELLOW + "Money!"));
//            addElement(new ElementSlider("Select Quantity at $" + smd.getPrice(), 0, mb));
//            _bsi = getElements().size() - 1;
//        }
//        addElement(new ElementLabel("---------- Sell Section ----------"));
//        if (sq == 0) {
//            addElement(new ElementLabel(TextFormat.RED + "Error! You do not have any of this itme to sell!"));
//        } else {
//            addElement(new ElementLabel(TextFormat.YELLOW + "You Can Sell " + TextFormat.GREEN + sq + TextFormat.YELLOW + " for " + TextFormat.GREEN + (smd.getSellPrice(sq)) + TextFormat.YELLOW + "Money!"));
//            addElement(new ElementSlider("Select Quantity at $" + smd.getSellPrice(), 0, sq));
//            _ssi = getElements().size() - 1;
//        }
//        addElement(new Ele("Test"));

    }

    public void handleMsg(String m) {
        setContent(getContent() + "\n " + TextFormat.RED + m);
    }

    public void setupBuy() {
        int mb = (int) Math.floor(_CP.getMoney() / _SMD.getPrice());
        if (mb > 1) addButton(new ElementButton("Buy 1 @ " + _SMD.getPrice()));
        if (mb > 5) addButton(new ElementButton("Buy 5 @ " + _SMD.getPrice(5)));
        if (mb > 16) addButton(new ElementButton("Buy 16 @ " + _SMD.getPrice(16)));
        if (mb > 32) addButton(new ElementButton("Buy 32 @ " + _SMD.getPrice(32)));
        if (mb > 64) addButton(new ElementButton("Buy 64 @ " + _SMD.getPrice(64)));
        if (mb > 128) addButton(new ElementButton("Buy 128 @ " + _SMD.getPrice(128)));
        if (mb > 256) addButton(new ElementButton("Buy 256 @ " + _SMD.getPrice(256)));
        if (mb <= 0)
            setContent(getContent() + "\n" + TextFormat.RED + " Error! You do not have enough money to buy " + _SMD.getItem(true).getName());
    }

    public void setupSell() {
        int sq = 0;
        for (Item ii : _CP.getInventory().getContents().values()) {
            if (ii.equals(_SMD.getItem(true), true, true)) {
                sq += ii.getCount();
            }
        }
        if (sq > 1) addButton(new ElementButton("Sell 1 @ " + _SMD.getSellPrice()));
        if (sq > 5) addButton(new ElementButton("Sell 5 @ " + _SMD.getSellPrice(5)));
        if (sq > 16) addButton(new ElementButton("Sell 16 @ " + _SMD.getSellPrice(16)));
        if (sq > 32) addButton(new ElementButton("Sell 32 @ " + _SMD.getSellPrice(32)));
        if (sq > 64) addButton(new ElementButton("Sell 64 @ " + _SMD.getSellPrice(64)));
        if (sq > 128) addButton(new ElementButton("Sell 128 @ " + _SMD.getSellPrice(128)));
        if (sq > 256) addButton(new ElementButton("Sell 256 @ " + _SMD.getSellPrice(256)));
        if (sq <= 0)
            setContent(getContent() + "\n" + TextFormat.RED + " Error! You do not have any " + _SMD.getItem(true).getName() + " to sell!");
    }


    @Override
    public boolean onRun(CorePlayer p) {
        super.onRun(p);
        int k = getResponse().getClickedButtonId();
        if (k == 0) {
            //Return to ChooseBuySell
            p.showFormWindow(new ShopChooseBuySell(_SMD, _CP));
        } else {
            switch (k) {
                case 1:
                    _SF.PurchaseItem(p, _SMD, 1, buy);
                    break;
                case 2:
                    _SF.PurchaseItem(p, _SMD, 5, buy);
                    break;
                case 3:
                    _SF.PurchaseItem(p, _SMD, 16, buy);
                    break;
                case 4:
                    _SF.PurchaseItem(p, _SMD, 32, buy);
                    break;
                case 5:
                    _SF.PurchaseItem(p, _SMD, 64, buy);
                    break;
                case 6:
                    _SF.PurchaseItem(p, _SMD, 128, buy);
                    break;
                case 7:
                    _SF.PurchaseItem(p, _SMD, 256, buy);
                    break;
                default:
                    p.showFormWindow(new ShopChooseBuySell(_SMD, _CP));
            }
        }
        return true;
    }
}
