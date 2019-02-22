package net.yungtechboy1.CyberCore.Manager.Factions.Listener;

import CyberTech.CyberChat.Main;
import cn.nukkit.event.player.PlayerDeathEvent;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.event.player.PlayerPreLoginEvent;
import cn.nukkit.potion.Effect;
import cn.nukkit.utils.MainLogger;
import cn.nukkit.utils.TextFormat;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by joneca04 on 12/30/2016.
 */
public class PreLoginEvent {
    FactionsMain Main;
    PlayerPreLoginEvent Event;
    public PreLoginEvent(FactionsMain main, PlayerPreLoginEvent ev){
        Main = main;
        Event = ev;
        run();
    }

    public void run(){
        if(!CheckIP(Event.getPlayer().getAddress())){
            Event.setKickMessage(TextFormat.RED+"~~~VPN's are not allowed!~~~");
            Event.setCancelled();
        }
    }

    //Check IP
    public boolean CheckIP(String ip){
        if(Main.IP.exists(ip)){
            return (boolean)Main.IP.get(ip);
        }
        String query = "http://yunc7a2id60lpalzyh6u2hsji.getipintel.net/check.php?ip="+ip+"&contact=yungtechboy1@gmail.com&flags=m";
        Float result = execute(query);
        if(result == null){
            Main.IP.set(ip,false);
            return false;
        }
        if(result < .90f){
            Main.IP.set(ip,true);
            return true;
        }
        Main.IP.set(ip,false);
        return false;
    }

    public HashMap<String, Object> FormatJSON(String json){
        if(json == null)return null;
        return new Gson().fromJson(json, new TypeToken<HashMap<String, Object>>() {}.getType());
    }

    public static Float execute(String targetURL){

        URL url;
        HttpURLConnection connection = null;
        try{
            //Create connection

            url = new URL(targetURL);
            connection = (HttpURLConnection)url.openConnection();

            connection.setRequestMethod("GET");
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
            return Float.parseFloat(response.toString());
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }finally {
            if(connection != null) {
                connection.disconnect();
            }
        }


    }
}
