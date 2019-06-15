package net.yungtechboy1.CyberCore.Commands;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.Base.PowerPublicInterface;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.PowerEnum;
import net.yungtechboy1.CyberCore.CorePlayer;
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
                new CommandParameter("key", CommandParamType.INT, false)
        });
        this.commandParameters.put("default2", new CommandParameter[]{
        });
        this.setPermission("CyberTech.CyberCore.player");
    }

    @Override
    public boolean execute(CommandSender s, String label, String[] args) {
        BaseClass bc = Owner.ClassFactory.GetClass((CorePlayer) s);
        String send = "------- ABILITIES -------\n";
        if (bc != null) {
            //@TODO Finish!
            if (args.length == 0) {
                if(bc.getPowers().size() == 0){
                    s.sendMessage(TextFormat.YELLOW+"Error! This Class doesn't provide you with a perk!");
                    return true;
                }
                //Send all possible Abillities!
                int i = 1;
                for (PowerPublicInterface c : bc.getPowers()) {
                    String a = TextFormat.GREEN + "";
                    if(c.Cooldown != null && c.Cooldown.isValid())a = TextFormat.RED + "";

                    a += "[" + i++ + "] > " + c.getDispalyName() + TextFormat.RESET + "\n";
                    send += a;
                }
                s.sendMessage(send);
            } else if (args.length == 1) {
                int key = Integer.parseInt(args[0]);
                if (bc.getPowers().size() > key && key != 0) {
                    bc.CmdRunPower(PowerEnum.fromint(key));
                    bc.setPrime(--key);
//                    ((Player) s).getLevel().addSound(new ExperienceOrbSound((Player) s));
                    s.sendMessage(TextFormat.GRAY+"You ready your self for an ability!");
                } else {
                    s.sendMessage(TextFormat.RED+"Error! Ability Key Invalid!");
                }
            } else {
                s.sendMessage(TextFormat.RED+"Error! Usage /aa or /aa [key]");
            }
        }
        return true;
    }
}