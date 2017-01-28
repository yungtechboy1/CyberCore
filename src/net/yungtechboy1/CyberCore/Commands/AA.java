package net.yungtechboy1.CyberCore.Commands;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.entity.Attribute;
import cn.nukkit.level.sound.ExperienceOrbSound;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.Classes.BaseClass;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.Utils;

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
        this.setPermission("CyberTech.CyberCore.player");
    }
    @Override
    public boolean execute(CommandSender s, String label, String[] args) {
        BaseClass bc = Owner.ClassFactory.GetClass((Player) s);
        if(bc != null) {
            bc.setPrime();
            ((Player) s).getLevel().addSound(new ExperienceOrbSound((Player)s));
            s.sendMessage("PRIMED!!!");
        }


        return true;
    }
}