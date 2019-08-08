package net.yungtechboy1.CyberCore;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.item.Item;
import net.yungtechboy1.CyberCore.Classes.New.ClassType;
import net.yungtechboy1.CyberCore.Classes.Power.BaseClasses.PowerEnum;
import net.yungtechboy1.CyberCore.Custom.Item.CItemBook;
import net.yungtechboy1.CyberCore.Manager.Form.Windows.HowToPlay.HTP_0_Window;

public class BookCmd extends Command {
    CyberCoreMain Owner;

    public BookCmd(CyberCoreMain server) {
        super("book", "Beginner Command to get you started on the server!", "/book");
        Owner = server;
        this.commandParameters.clear();
        this.commandParameters.put("default", new CommandParameter[]{
                new CommandParameter("type", CommandParamType.INT,false)
                ,new CommandParameter("ID", CommandParamType.INT,false)
        });
        this.setPermission("CyberTech.CyberCore.player");
    }

    @Override
    public boolean execute(CommandSender s, String label, String[] args) {

        CorePlayer p = (CorePlayer) s;
        int type,id;
        try{
            type = Integer.parseInt(args[0]);
            id = Integer.parseInt(args[1]);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        Item b ;
        if(type == 0) {
            b = new CItemBook(PowerEnum.values()[id]);
        }else{
            b = new CItemBook(ClassType.values()[id]);
        }
        p.getInventory().addItem(b);
        return true;
    }
}
