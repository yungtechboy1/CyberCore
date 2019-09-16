package net.yungtechboy1.CyberCore;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import net.yungtechboy1.CyberCore.Manager.Form.Windows.HowToPlay.HTP_0_Window;

public class HowToPlay extends Command {
    CyberCoreMain Owner;

    public HowToPlay(CyberCoreMain server) {
        super("howtoplay", "Beginner Command to get you started on the server!", "/howtoplay | /htp", new String[]{"htp"});
        Owner = server;
        this.commandParameters.clear();
        this.commandParameters.put("default", new CommandParameter[]{});
        this.setPermission("CyberTech.CyberCore.player");
    }

    @Override
    public boolean execute(CommandSender s, String label, String[] args) {

        CorePlayer p = (CorePlayer) s;
        if(p.getPlayerClass() == null) {
            p.showFormWindow(new HTP_0_Window());
        }
        return true;
    }
}