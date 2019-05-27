package net.yungtechboy1.CyberCore.Manager.Factions.Cmds;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;

import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;

/**
 * Created by carlt_000 on 7/9/2016.
 */
public class Map extends Commands {

    public Map(CorePlayer s, String[] a, FactionsMain m) {
        super(s, a, "/f map", m);
        senderMustBeInFaction = false;
        senderMustBePlayer = true;
        sendFailReason = true;
        sendUsageOnFail = true;

        if (run()) {
            RunCommand();
        }
    }

    @Override
    public void RunCommand() {
        Integer x = (int)((Player)Sender).getX() >> 4;
        Integer z = (int)((Player)Sender).getZ() >> 4;
        String PF = TextFormat.AQUA+"-<{"+TextFormat.YELLOW+"F Map"+TextFormat.AQUA+"}>-"+TextFormat.BLUE;
        String text=TextFormat.BLUE+"|-----------"+PF+"----------|\n";


        for (int i = -4; i <= 4; i++) {//Z or Y
            text = text +TextFormat.BLUE+ "|";
            for (int o = -15; o <= 15; o++) {//X
                //Main.getLogger().info("DOING X:"+(x+o)+" AND Z:"+(z+i));
                String stat = Main.FFactory.GetPlotStatus(x+o,z+i);
                if(i == 0 && o == 0){
                    if(stat != null && fac != null && stat.toLowerCase().equalsIgnoreCase(fac.GetName().toLowerCase())) {
                        text = text + TextFormat.GREEN + "&";
                    }else if(stat != null && stat.equalsIgnoreCase("PEACE")){
                        text = text+TextFormat.AQUA+"&";
                    }else{
                        text = text + TextFormat.GRAY + "&";
                    }
                    continue;
                }
                if(stat == null){
                    text = text+TextFormat.GRAY+"-";
                }else if(fac != null && stat.toLowerCase().equalsIgnoreCase(fac.GetName().toLowerCase())){
                    text = text+TextFormat.GREEN+"\\";
                }else if(stat.toLowerCase().equalsIgnoreCase("PEACE")){
                    text = text+TextFormat.AQUA+"P";
                }else{
                    text = text+TextFormat.RED+"X";
                }
            }
            text = text +TextFormat.BLUE+ "|";
            text = text+"\n";
        }
        text=text+TextFormat.BLUE+"|-------------------------------|\n";
        text=text+TextFormat.BLUE+"|"+TextFormat.GRAY+"----------   "+TextFormat.AQUA+"Legend"+TextFormat.GRAY+"   -----------"+TextFormat.BLUE+"|\n";//6 - 4
        text = text + TextFormat.BLUE+"|"+TextFormat.GRAY+" ----- [ "+TextFormat.GREEN+"/"+TextFormat.GRAY+" ] : Your Faction --------"+TextFormat.BLUE+"|\n";//10-3
        text = text + TextFormat.BLUE+"|"+TextFormat.GRAY+" ----- [ "+TextFormat.RED+"X"+TextFormat.GRAY+" ] : Enemy Faction -------"+TextFormat.BLUE+"|\n";//10-
        text = text + TextFormat.BLUE+"|"+TextFormat.GRAY+" ----- [ "+TextFormat.AQUA+"P"+TextFormat.GRAY+" ] : Peaceful Land  ------"+TextFormat.BLUE+"|\n";
        text=text+TextFormat.BLUE+"|-------------------------------|\n";
        Sender.sendMessage(text);
    }
}