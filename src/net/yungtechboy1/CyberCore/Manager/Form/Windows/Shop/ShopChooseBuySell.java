package net.yungtechboy1.CyberCore.Manager.Form.Windows.Shop;

import cn.nukkit.form.element.ElementButton;
import cn.nukkit.item.Item;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.Factory.Shop.ShopMysqlData;
import net.yungtechboy1.CyberCore.FormType;
import net.yungtechboy1.CyberCore.Manager.Form.CyberFormSimple;

public class ShopChooseBuySell extends CyberFormSimple {
    private ShopMysqlData _SMD;
    private CorePlayer _CP;
    private int _bsi = -1;
    private int _ssi = -1;
    public ShopChooseBuySell(ShopMysqlData smd, CorePlayer cp) {
        super(FormType.MainForm.Shop_Choose_BuySell, "~~| Shop > Buy / Sell |~~");
        _SMD = smd;
        Item ip = smd.getItem(true);
        setContent("Selected Item Name : " + ip.getName() + " | ID: " + ip.getId() + " | Meta: " + ip.getDamage());
        Item i = smd.getItem();
        int sq = 0;
        for (Item ii : cp.getInventory().getContents().values()) {
            if (ii.equals(ip, true, true)) {
                sq += ii.getCount();
            }
        }
        int mb = (int) Math.floor(cp.getMoney() / smd.getPrice());
        addButton(new ElementButton(TextFormat.YELLOW+"Buy up to "+TextFormat.GREEN+mb+TextFormat.YELLOW+" "+ip.getName()+"(s)"));
        addButton(new ElementButton(TextFormat.YELLOW+"Sell up to "+TextFormat.GREEN+sq+TextFormat.YELLOW+" "+ip.getName()+"(s)"));
        addButton(new ElementButton(TextFormat.RED+"Return to Shop GUI"));

//
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

    @Override
    public boolean onRun(CorePlayer p) {
        super.onRun(p);
        int k = getResponse().getClickedButtonId();
        if(k == 0){
            //Buy
            p.showFormWindow(new ShopConfirmItemPurchase(_SMD,_CP, CyberCoreMain.getInstance().Shop,true));
        }else if(k == 1){
            //Sell
            p.showFormWindow(new ShopConfirmItemPurchase(_SMD,_CP,CyberCoreMain.getInstance().Shop,false));
        }else{
            //TODO Re-Open Shop GUI
        }
        return true;
    }
}
