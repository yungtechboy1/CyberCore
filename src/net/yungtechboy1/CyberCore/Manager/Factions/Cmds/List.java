package net.yungtechboy1.CyberCore.Manager.Factions.Cmds;

import cn.nukkit.utils.TextFormat;

import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.Manager.Factions.Cmds.Base.Commands;
import net.yungtechboy1.CyberCore.Manager.Factions.Faction;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;

/**
 * Created by carlt_000 on 7/9/2016.
 */
public class List extends Commands {

    public List(CorePlayer s, String[] a, FactionsMain m) {
        super(s, a, "/f List [Page]", m);
        sendFailReason = true;
        sendUsageOnFail = true;

        if (run()) {
            RunCommand();
        }
    }

    @Override
    public void RunCommand() {
        Integer p = GetIntegerAtArgs(1,1);
        Integer to = p * 5;
        Integer from = to - 5;
        // 5 -> 0 ||| 10 -> 5
        Integer x = 0;

        String t = "";
        String a = "";

        t += TextFormat.GRAY+"-------------"+TextFormat.GOLD+".<[List]>."+TextFormat.GRAY+"-------------\n";
        t += TextFormat.GRAY+"------------Land-Pwr-Max---------\n";
        for(java.util.Map.Entry<String, Faction> e : Main.FFactory.List.entrySet()){
            Faction f = e.getValue();
            a = "";
            // 0 < 5 && 0 >= 0
            //   YES     NO
            //0
            //1 2 3 4 5
            //0 < 10 && 0 >= 5
            if(!(x < to && x >= from)){
                x++;
                continue;
            }
            if(x > to)break;

            x++;
            //Privacy
            a += TextFormat.DARK_GREEN+""+x+" > ";
            if (f.GetPrivacy() == 1) {
                a += TextFormat.RED + "[P]";
            } else {
                a += TextFormat.GREEN + "[O]";
            }
            a += TextFormat.WHITE + "---[ ";
            a += TextFormat.DARK_AQUA+"" + f.GetPlots().size();
            a += TextFormat.WHITE + " / ";
            a += TextFormat.DARK_AQUA +""+ f.GetPower();
            a += TextFormat.WHITE + " / ";
            a += TextFormat.DARK_AQUA+"" + f.CalculateMaxPower();
            a += TextFormat.WHITE + " ]--- " + TextFormat.YELLOW + f.GetDisplayName();
            a += TextFormat.GRAY + "[" + f.GetNumberOfPlayers() + "]";
            t += a + "\n";

        }
        t += "------------------------------";
        Sender.sendMessage(t);
    }
}