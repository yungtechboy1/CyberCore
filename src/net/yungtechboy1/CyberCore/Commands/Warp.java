package net.yungtechboy1.CyberCore.Commands;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.CyberCoreMain;

/**
 * Created by carlt_000 on 2/1/2017.
 */
public class Warp extends Command {
    private String Prefix = TextFormat.AQUA + "[TerraTP]";
    CyberCoreMain Owner;

    public Warp(CyberCoreMain server) {
        super("warp", "Warps you to a warp!", "/warp [key]");
        Owner = server;
        this.commandParameters.clear();
        this.commandParameters.put("default", new CommandParameter[]{
                new CommandParameter("key", CommandParamType.RAWTEXT, true)
        });
        this.commandParameters.put("2nd", new CommandParameter[]{});
    }

    @Override
    public boolean execute(CommandSender s, String label, String[] args) {
        Player p = (Player) s;
        if (args.length != 1 || p == null){
            /*
            ConfigSection list = Owner.MainConfig.getSections("warp");

            ArrayList<String> ll = new ArrayList<>();
            for(String a: list.keySet()){
                ll.add()
            }*/
            //FUck warp Listing
            return true;
        }
        String warpkey = args[0].toLowerCase();
        if(Owner.WarpManager.GetWarp(warpkey) != null){

        }else{
            s.sendMessage("Error! Warp not found!");
        }
        return true;
    }
}