package net.yungtechboy1.CyberCore.Manager.Factions.Cmds;

import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;

import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.Manager.Factions.Faction;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;

import java.security.acl.Owner;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by carlt_000 on 7/9/2016.
 */
public class Help extends Commands {

    public Help(CorePlayer s, String[] a, FactionsMain m) {
        super(s, a, "/f help [page]", m);
        senderMustBePlayer = true;
        sendFailReason = true;
        sendUsageOnFail = true;

        if (run()) {
            RunCommand();
        }
    }

    @Override
    public void RunCommand() {
        ArrayList<String> a = new ArrayList<>();
        a.add("/f accept - Accept Faction Invite");
        a.add("/f admin - OP Only");
        a.add("/f balance - Faction Balance");
        a.add("/f chat [Message] | /f c [Message] - Send message to faction only");
        a.add("/f claim [radius] - Claim Land");
        a.add("/f create <name> - Create a Faction");
        a.add("/f del - Delete Faction");
        a.add("/f demote <player> - Demote player in faction");
        a.add("/f deny - Deny faction invite");
        a.add("/f deposit <amount> - Add money to faction ballance");
        a.add("/f desc [Description] - Set description for Faction");
        a.add("/f help [page] - View All Commands");
        a.add("/f home - Teleport to faction home");
        a.add("/f info <faction> - View faction's info");
        a.add("/f invite <player> - Invite player to join your faction");
        a.add("/f join <faction> - Join an open faction");
        a.add("/f kick <player> - Kick player from faction");
        a.add("/f kits - Coming Soons");
        a.add("/f leader <player> - Transfer leadership to another player");
        a.add("/f leave [Leave message]- Leave faction");
        a.add("/f leader <player> - Give another player leadership of faction");
        a.add("/f list [page] - List all factions");
        a.add("/f map - Show map of area");
        a.add("/f mission - Show all mission commands");
        a.add("/f motd <Settings> - Set faction MOTD ");
        a.add("/f overclaim [radius] - Overclaim land ");
        a.add("/f perk - View All Faction Perks ");
        a.add("/f power - View faction's power");
        a.add("/f privacy - Change faction privacy between Open and Closed");
        a.add("/f Promote <player> - Promote a player");
        a.add("/f sethome - Set faction home");
        a.add("/f unclaim [radius] - Unclaim faction chunks");
        a.add("/f war <faction> - Declare War against faction");
        a.add("/f wartp - Teleport to the war zone");
        a.add("/f withdraw - Take money from faction's balance");

        Integer p = GetIntegerAtArgs(0,1);
        if(p > a.size() / 5)return;
        Integer to = p * 5;
        Integer from = to - 5;
        // 5 -> 0 ||| 10 -> 5
        Integer x = 0;
        String t = "";

        DecimalFormat format = new DecimalFormat("0.#");

        t += TextFormat.GRAY+"Page " + p + " of "  + format.format(Math.ceil(Double.parseDouble(Integer.toString(a.size() / 5)))) ;
        t += TextFormat.GRAY+"-----"+TextFormat.GOLD+".<[Faction Command List]>."+TextFormat.GRAY+"-----\n";

        for(String value : a){
            // 0 < 5 && 0 >= 0
            //   YES     YES
            //
            //0
            //1 2 3 4 5
            //0 < 10 && 0 >= 5
            if(!(x < to && x >= from)){
                x++;
                continue;
            }
            if(x > to)break;
            x++;
            t += value + "\n";

        }
        t += "------------------------------";
        Sender.sendMessage(t);
    }
}