package net.yungtechboy1.CyberCore.Commands;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.inventory.PlayerInventory;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemDiamond;
import cn.nukkit.item.ItemSteak;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.Custom.CustomEnchant.CrateKey;
import net.yungtechboy1.CyberCore.CyberCoreMain;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by carlt_000 on 3/21/2016.
 */

public class Vote extends Command {
    CyberCoreMain Owner;

    public Vote(CyberCoreMain server) {
        super("vote", "Vote for server", "/vote");
        Owner = server;
        this.commandParameters.clear();
    }

    static String executePost(String targetURL) {

        URL url;
        HttpURLConnection connection = null;
        try {
            //Create connection

            url = new URL(targetURL);
            connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Language", "en-US");
            connection.setRequestProperty("User-Agent",
                    "Mozilla/5.0 (Windows NT 5.1) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.56 Safari/535.11");

            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            //send Request
           /* DataOutputStream dataout = new DataOutputStream(connection.getOutputStream());
            dataout.writeBytes(urlParameters);
            dataout.flush();
            dataout.close();*/

            //get response
            InputStream is = connection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer response = new StringBuffer();

            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            br.close();
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "3";
        } finally {

            if (connection != null) {
                connection.disconnect();
            }
        }


    }

    //For CyberTech
    public static Integer GetVoteStatus1(String Username) {
        try {
            //Key For Terra - 3q69nwi5cjm1qoij9ggel7osnq9eqm2tst3
            return Integer.parseInt(executePost("http://minecraftpocket-servers.com/api/?object=votes&element=claim&key=3q69nwi5cjm1qoij9ggel7osnq9eqm2tst3&username=" + Username));
        } catch (Exception e) {
            System.out.println(e.getClass().getName() + ": " + e.getMessage());

        }
        return 3;
    }

    //For CyberTech
    public static Integer SetVoted1(String Username) {
        try {
            //Key For Terra - 3q69nwi5cjm1qoij9ggel7osnq9eqm2tst3
            return Integer.parseInt(executePost("http://minecraftpocket-servers.com/api/?action=post&object=votes&element=claim&key=3q69nwi5cjm1qoij9ggel7osnq9eqm2tst3&username=" + Username));
        } catch (Exception e) {
            System.out.println(e.getClass().getName() + ": " + e.getMessage());

        }
        return 2;
    }

    @Override
    public boolean execute(CommandSender s, String label, String[] args) {
        CyberCoreMain server = Owner;
        Integer vs = GetVoteStatus1(s.getName());
        if (vs == 0) {
            //s.sendMessage(TextFormat.AQUA + "[Terratide]" + TextFormat.YELLOW + " You have not voted for " + TextFormat.RED + "Terratide" + TextFormat.YELLOW + " yet!");
            s.sendMessage("§l§eTERRA§6TIDE> §r§7You Have not voted yet!");
            s.sendMessage(TextFormat.AQUA + "[Terratide]" + TextFormat.YELLOW + " Please go to 'vote.terratide.net' to vote for" + TextFormat.GOLD + "Terratide");
            return true;
        }
        if (vs == 1) {
            SetVoted1(s.getName());
            //§l§cVOTE> §r§7Username Voted and Received a §b§lCRATE KEY! §r§7To vote, visit: §bvote.terratide.net
            s.sendMessage("§l§cVOTE> §r§7"+s.getName()+" Voted and Received a §b§lCRATE KEY and DIAMONDS! §r§7To vote, visit: §bvote.terratide.net");
            //Give Rewards
            if (s instanceof Player) {
                //REWARD ITEMS
                Item R1 = new ItemDiamond(0, 2);//2 Diamonds
                Item R2 = new ItemSteak(0, 3);//3 Steaks
                Item R3 = new Item(Item.PLANK, 0, 32);//32 Wood
                Item R4 = new Item(Item.MAGMA_CREAM,1);//1 January Crate
                //R4.addEnchantment(new CrateKey());
                R4.setCustomName("January Crate Key");
                R4.getNamedTag().putBoolean("JanuaryKey", true  );
                PlayerInventory pi = ((Player) s).getInventory();
                pi.addItem(R1, R2, R3, R4);
                s.sendMessage("§l§eTERRA§6TIDE> §r§7Thanks For Voting!");
            }
            return true;
        }
        if (vs == 2) {
            //s.sendMessage(TextFormat.AQUA + "[Terratide]" + TextFormat.YELLOW + " Vote Already Claimed for " + TextFormat.RED + "Terratide" + TextFormat.YELLOW + "!");
            s.sendMessage("§l§eTERRA§6TIDE> §r§7You have already claimed your reward!");
            return true;
        }
        return false;
    }
}