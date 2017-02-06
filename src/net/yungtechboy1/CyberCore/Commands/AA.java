package net.yungtechboy1.CyberCore.Commands;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.level.sound.ExperienceOrbSound;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.Abilities.Ability;
import net.yungtechboy1.CyberCore.Classes.BaseClass;
import net.yungtechboy1.CyberCore.CyberCoreMain;

/**
 * Created by carlt_000 on 1/26/2017.
 */
public class AA extends Command {
    CyberCoreMain Owner;

    public AA(CyberCoreMain server) {
        super("AbillityActivate", "Activates your Class' Special Ability!", "/aa [key]", new String[]{"aa"});
        Owner = server;
        this.commandParameters.clear();
        this.commandParameters.put("default", new CommandParameter[]{
                new CommandParameter("key", CommandParameter.ARG_TYPE_INT, false)
        });
        this.commandParameters.put("default2", new CommandParameter[]{
        });
        this.setPermission("CyberTech.CyberCore.player");
    }

    @Override
    public boolean execute(CommandSender s, String label, String[] args) {
        BaseClass bc = Owner.ClassFactory.GetClass((Player) s);
        String send = "------- ABILITIES -------\n";
        if (bc != null) {
            //@TODO Finish!
            if (args.length == 0) {
                if(bc.PossibleAbillity().size() == 0){
                    s.sendMessage(TextFormat.YELLOW+"Error! This Class doesn't provide you with a perk!");
                    return true;
                }
                //Send all possible Abillities!
                int i = 1;
                for (Ability c : bc.PossibleAbillity()) {
                    String a = TextFormat.GREEN + "";
                    if (bc.HasCooldown(c.ID)) a = TextFormat.RED + "";
                    a += "[" + i++ + "] > " + c.getName() + TextFormat.RESET + "\n";
                    send += a;
                }
                s.sendMessage(send);
            } else if (args.length == 1) {
                int key = Integer.parseInt(args[0]);
                if (bc.PossibleAbillity().size() >= key && key != 0) {
                    bc.setPrime(--key);
                    ((Player) s).getLevel().addSound(new ExperienceOrbSound((Player) s));
                    s.sendMessage("PRIMED!!!");
                } else {
                    s.sendMessage("Error! Ability Key Invalid!");
                }
            } else {
                s.sendMessage("Error! Usage /aa or /aa [key]");
            }
        }
        return true;
    }
}