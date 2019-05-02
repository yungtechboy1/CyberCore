package net.yungtechboy1.CyberCore.Commands;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.item.Item;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;

/**
 * Created by carlt_000 on 2/23/2017.
 */
public class SellHand extends Command {
    CyberCoreMain Owner;

    public SellHand(CyberCoreMain server) {
        super("SellHand", "Sell hand to auction", "/sellhand [price]", new String[]{"sh"});
        Owner = server;
        this.commandParameters.clear();
        this.commandParameters.put("default", new CommandParameter[]{
                new CommandParameter("price", CommandParamType.FLOAT, false)
        });
        this.setPermission("CyberTech.CyberCore.player");
    }

    @Override
    public boolean execute(CommandSender s, String label, String[] args) {
        s.sendMessage("IMA TAKE YOUR HAND!");
        if(s instanceof Player){
            CorePlayer p = (CorePlayer) s;
            Item hand = p.getInventory().getItemInHand();
            try {
                int price = Integer.parseInt(args[0]);
                Owner.AuctionFactory.additem(hand.clone(), p, price);
                p.getInventory().remove(hand);
            }catch (Exception ex){
                ex.printStackTrace();
                s.sendMessage("ERRRRRRRR!!!!!!!");
                return false;
            }
        }
        return true;
    }
}