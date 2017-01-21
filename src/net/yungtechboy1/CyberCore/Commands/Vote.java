package net.yungtechboy1.CyberCore.Commands;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.inventory.PlayerInventory;
import cn.nukkit.item.Item;
import cn.nukkit.item.ItemDiamond;
import cn.nukkit.item.ItemSteak;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.Main;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by carlt_000 on 3/21/2016.
 */

public class Vote {
    Main Owner;
    public void Vote(Main server){
        Owner = server;
    }

    public static void runCommand(CommandSender s,String[] args, Main server){
        Integer vs = GetVoteStatus1(s.getName());
        if(vs == 0){
            s.sendMessage(TextFormat.AQUA+"[Terratide]"+TextFormat.YELLOW+" You have not voted for "+TextFormat.RED+"Terratide"+TextFormat.YELLOW+" yet!");
            s.sendMessage(TextFormat.AQUA+"[Terratide]"+TextFormat.YELLOW+" Please go to 'http://minecraftpocket-servers.com/server/38216/vote/' vote for"+TextFormat.GOLD+"Terratide");
        }else if(vs == 1){
            if(SetVoted1(s.getName()) == 0) {
                s.sendMessage(TextFormat.AQUA+"[Terratide]"+TextFormat.GREEN+" Vote Claimed for "+TextFormat.RED+"Terratide"+TextFormat.GREEN+"!");
                //Give Rewards
                if(s instanceof Player){
                    Item R1 = new ItemDiamond(0,2);//2 Diamonds
                    Item R2 = new ItemSteak(0,3);//3 Steaks
                    Item R3 = new Item(Item.PLANK,0,32);//32 Wood
                    PlayerInventory pi = ((Player) s).getInventory();
                    pi.addItem(R1,R2,R3);
                }
            }else{
                s.sendMessage(TextFormat.AQUA+"[Terratide]"+TextFormat.YELLOW+" Vote Already Claimed for "+TextFormat.RED+"Terratide"+TextFormat.YELLOW+"!");
            }
        }else if(vs == 2){
            s.sendMessage(TextFormat.AQUA+"[Terratide]"+TextFormat.YELLOW+" Vote Already Claimed for "+TextFormat.RED+"Terratide"+TextFormat.YELLOW+"!");
        }
        /*
        Integer vs2 = GetVoteStatus2(s.getName());
        if(vs2 == 0){
            s.sendMessage(TextFormat.AQUA+"[CyberCore]"+TextFormat.YELLOW+" You have not voted for "+TextFormat.RED+"Arch"+TextFormat.YELLOW+" yet!");
            s.sendMessage(TextFormat.AQUA+"[CyberCore]"+TextFormat.YELLOW+" Please go to 'http://minecraftpocket-servers.com/server/48817/vote/' vote for"+TextFormat.RED+"Arch");
        }else if(vs2 == 1){
            if(SetVoted2(s.getName()) == 0) {
                s.sendMessage(TextFormat.AQUA+"[CyberCore]"+TextFormat.GREEN+" Vote Claimed for "+TextFormat.RED+"Arch"+TextFormat.GREEN+"!");
                //Give Rewards
                if(s instanceof Player){
                    Item R1 = new ItemDiamond(0,2);//2 Diamonds
                    Item R2 = new ItemSteak(0,3);//3 Steaks
                    Item R3 = new Item(Item.PLANK,0,32);//32 Wood
                    PlayerInventory pi = ((Player) s).getInventory();
                    pi.addItem(R1,R2,R3);
                }
            }else{
                s.sendMessage(TextFormat.AQUA+"[CyberCore]"+TextFormat.YELLOW+" Vote Already Claimed for "+TextFormat.RED+"Arch"+TextFormat.YELLOW+"!");
            }
        }else if(vs2 == 2){
            s.sendMessage(TextFormat.AQUA+"[CyberCore]"+TextFormat.YELLOW+" Vote Already Claimed for "+TextFormat.RED+"Arch"+TextFormat.YELLOW+"!");
        }*/
    }

    static String executePost(String targetURL){

        URL url;
        HttpURLConnection connection = null;
        try{
            //Create connection

            url = new URL(targetURL);
            connection = (HttpURLConnection)url.openConnection();

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

            while((line = br.readLine()) != null){
                response.append(line);
            }
            br.close();
            return response.toString();
        }catch(Exception e){
            e.printStackTrace();
            return "3";
        }finally {

            if(connection != null) {
                connection.disconnect();
            }
        }


    }

    //For Arch
    public static Integer GetVoteStatus2(String Username) {
        try {
            return Integer.parseInt(executePost("http://minecraftpocket-servers.com/api/?object=votes&element=claim&key=v6ex54886fje14kc39p1og0lkvslhrvq1p&username="+Username));
        }catch (Exception e){
            System.out.println( e.getClass().getName() + ": " + e.getMessage());

        }
        return 3;
    }
    //For CyberTech
    public static Integer GetVoteStatus1(String Username) {
        try {
            //Key For Terra - 3q69nwi5cjm1qoij9ggel7osnq9eqm2tst3
            return Integer.parseInt(executePost("http://minecraftpocket-servers.com/api/?object=votes&element=claim&key=3q69nwi5cjm1qoij9ggel7osnq9eqm2tst3&username="+Username));
        }catch (Exception e){
            System.out.println( e.getClass().getName() + ": " + e.getMessage());

        }
        return 3;
    }
    //For Arch
    public static Integer SetVoted2(String Username) {
        try {
            //Key For Arch - v6ex54886fje14kc39p1og0lkvslhrvq1p
            return Integer.parseInt(executePost("http://minecraftpocket-servers.com/api/?action=post&object=votes&element=claim&key=v6ex54886fje14kc39p1og0lkvslhrvq1p&username="+Username));
        }catch (Exception e){
            System.out.println( e.getClass().getName() + ": " + e.getMessage());

        }
        return 2;
    }
    //For CyberTech
    public static Integer SetVoted1(String Username) {
        try {
            //Key For Terra - 3q69nwi5cjm1qoij9ggel7osnq9eqm2tst3
            return Integer.parseInt(executePost("http://minecraftpocket-servers.com/api/?action=post&object=votes&element=claim&key=te85wcp8hbhr68oxblm2dslea5iodavsga&username="+Username));
        }catch (Exception e){
            System.out.println( e.getClass().getName() + ": " + e.getMessage());

        }
        return 2;
    }
}