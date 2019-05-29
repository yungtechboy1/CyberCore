package net.yungtechboy1.CyberCore.Commands;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import net.yungtechboy1.CyberCore.Classes.New.BaseClass;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.Manager.Form.Windows.FactionAdminPage1;

public class AdminCMD extends Command {
    CyberCoreMain Owner;

    public AdminCMD(CyberCoreMain server) {
        super("admin", "View your Class info!", "/aclass [key]");
        Owner = server;
        this.commandParameters.clear();
        this.commandParameters.put("default", new CommandParameter[]{
                new CommandParameter("del", CommandParamType.STRING, true)
        });
        this.commandParameters.put("classes", new CommandParameter[]{
                new CommandParameter("setclass", CommandParamType.STRING, false),
                new CommandParameter("class", CommandParamType.STRING, false)
        });
        this.commandParameters.put("setxp", new CommandParameter[]{
                new CommandParameter("setclass", CommandParamType.STRING, false),
                new CommandParameter("class", CommandParamType.INT, false)
        });
//        this.setPermission("CyberTech.CyberCore.op");
    }

    @Override
    public boolean execute(CommandSender s, String label, String[] args) {
        if(!(s instanceof CorePlayer))return false;
        try {
            ((CorePlayer)s).showFormWindow(new FactionAdminPage1());
        }catch (Exception e){
            CyberCoreMain.getInstance().getLogger().error("EEE!213111 >>",e);
        }
        return true;
    }
}
