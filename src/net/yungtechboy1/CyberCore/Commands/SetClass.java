package net.yungtechboy1.CyberCore.Commands;

import net.yungtechboy1.CyberCore.Commands.Constructors.CheckPermCommand;
import cn.nukkit.command.CommandSender;
import cn.nukkit.math.Vector3;
import cn.nukkit.utils.ConfigSection;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.RankList;

/**
 * Created by carlt_000 on 1/28/2017.
 */
public class SetClass extends CheckPermCommand {
    private Vector3 temporalVector = new Vector3();

    public SetClass(CyberCoreMain server) {
        super(server, "setclass", "[OP] Set Player's Class for Server", "/setclass <Player-Name-Exact> <type> <xp>", RankList.PERM_OP);
        this.commandParameters.clear();
        this.setPermission("CyberTech.CyberCore.op");
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (!super.execute(commandSender, s, strings)) return SendError();

        String name = strings[0];
        int type = Integer.parseInt(strings[1]);
        int xp = Integer.parseInt(strings[2]);

        ConfigSection data =  new ConfigSection() {{
            put("COOLDOWNS", new ConfigSection());
            put("XP", xp);
            put("TYPE", type);
        }};
        Owner.ClassFactory.MMOSave.set(name.toLowerCase(),data);
        Owner.ClassFactory.AddToClassListAfterSave(name);

        return true;
    }
}