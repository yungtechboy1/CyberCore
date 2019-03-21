package net.yungtechboy1.CyberCore.Tasks;

import cn.nukkit.Player;
import cn.nukkit.scheduler.PluginTask;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.Password;

/**
 * Created by carlt_000 on 2/4/2017.
 */
public class Notify extends PluginTask<CyberCoreMain> {
    public Notify(CyberCoreMain main){
        super(main);
    }

    @Override
    public void onRun(int i) {
        for(Player p: getOwner().getServer().getOnlinePlayers().values()){
            Password pass = getOwner().PasswordFactoy.GetPassword(p);
            if(!pass.getLoggedin()){
                //§7Please §bLogin §7with §b/login
                //@TODO
                if(!pass.IsRegistered())p.sendPopup(TextFormat.YELLOW+"You must /register <password>");
                if(pass.IsRegistered())p.sendPopup(TextFormat.YELLOW+"You must /login <password>");
            }
        }
    }
}