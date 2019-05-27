package net.yungtechboy1.CyberCore.Commands;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.item.Item;
import cn.nukkit.math.Vector3;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.Custom.Inventory.VirturalEnchantInv;
import net.yungtechboy1.CyberCore.CyberCoreMain;

/**
 * Created by carlt_000 on 2/6/2017.
 */
public class ChatEnchant extends Command {
    CyberCoreMain Owner;

    public ChatEnchant(CyberCoreMain server) {
        super("ChatEnchant", "Activates Chat Enchant Feature", "/ce [key]", new String[]{"ce"});
        Owner = server;
        this.commandParameters.clear();
        this.commandParameters.put("default", new CommandParameter[]{
                new CommandParameter("key", CommandParamType.INT, true),
        });
    }
    @Override
    public boolean execute(CommandSender s, String label, String[] args) {
        CyberCoreMain server = Owner;
        if (!(s instanceof Player)) {
            s.sendMessage(TextFormat.RED + "Error! You must be a player!");
            return true;
        }
        Player sp = (Player) s;
        if (args.length == 0) {

            if (!CheckForEtable(sp)) {
                s.sendMessage(TextFormat.RED + "Error! An Enchantment Table must be 5 Blocks close to you!");
                return true;
            }

            Item hand = sp.getInventory().getItemInHand();
            if (hand.getEnchantAbility() == 0) {
                s.sendMessage(TextFormat.RED + "Error! You can't Enchant that item!");
                return true;
            }
            if (hand.hasEnchantments()) {
                s.sendMessage(TextFormat.RED + "Error! You can't Enchant an already enchanted item!");
                return true;
            }

            VirturalEnchantInv VEI = new VirturalEnchantInv(server, sp, hand);
            if (VEI.lapiscount == 0) {
                s.sendMessage(TextFormat.RED + "Error! You need at least 1 lapis!");
                VEI.cancel();
                return true;
            }
            VEI.Calculate();
            server.CustomFactory.VEIList.set(sp.getName().toLowerCase(), VEI);
        } else if (args.length == 1) {
            if (server.CustomFactory.VEIList.containsKey(sp.getName().toLowerCase())) {
                VirturalEnchantInv VEI = (VirturalEnchantInv) server.CustomFactory.VEIList.get(sp.getName().toLowerCase());
                if (args[0].equalsIgnoreCase("cancel")) {
                    VEI.cancel();
                    server.CustomFactory.VEIList.remove(sp.getName().toLowerCase());
                    return true;
                } else if (args[0].equalsIgnoreCase("help")) {
                    s.sendMessage(TextFormat.GRAY + "/ce usage:\n" +
                            "/ce help\n" +
                            "/ce cancel - Cancel Current Transaction and get items back\n" +
                            "/ce [id] - Accept Enchant\n" +
                            "/ce - Enchant item in hand");
                    return true;
                }
                Integer i = Integer.parseInt(args[0]);
                if (i == null || i <= 0 || i > 3) {
                    s.sendMessage(TextFormat.RED + "Error! Enchant ID Must be between 1 and 3!");
                    return true;
                }else if (VEI.onEnchant(i)) {
                    server.CustomFactory.VEIList.remove(sp.getName().toLowerCase());
                }
            } else {
                s.sendMessage(TextFormat.RED + "Error! You have no item tring to be enchanted! Use /ce first!");
            }
        }
        return true;
    }

    private boolean CheckForEtable(Player player) {
        for (int x = -5; x < 5; x++) {
            for (int y = -5; y < 5; y++) {
                for (int z = -5; z < 5; z++) {
                    Block b = player.getLevel().getBlock(new Vector3(player.getFloorX() + x, player.getFloorY() + y, player.getFloorZ() + z));
                    if (b.getId() == Block.ENCHANT_TABLE) return true;
                }
            }
        }
        return false;
    }

}