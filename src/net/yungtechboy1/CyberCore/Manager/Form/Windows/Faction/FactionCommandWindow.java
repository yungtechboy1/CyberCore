package net.yungtechboy1.CyberCore.Manager.Form.Windows.Faction;

import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.response.FormResponseSimple;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.FormType;
import net.yungtechboy1.CyberCore.Manager.Factions.Cmds.Chat;
import net.yungtechboy1.CyberCore.Manager.Factions.Cmds.Leave;
import net.yungtechboy1.CyberCore.Manager.Factions.Faction;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionPermSettings;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionRank;
import net.yungtechboy1.CyberCore.Manager.Form.CyberFormSimple;

public class FactionCommandWindow extends CyberFormSimple {
    public FactionCommandWindow(String title, String content, CorePlayer cp) {
        super(FormType.MainForm.Faction_CMD_Window, "Faction Command Window", "Below you will find the list of all commands you have access to. You can coose to tap on the command to learn more about it or just exit when done :)");
        setContent(getContent() + "\n" +
                "Current Faction: " + cp.getFactionName() + "\n" +
                "");

        Faction f = cp.getFaction();
        addButton(new ElementButton("Leave Faction"));
        FactionRank fr = cp.getFaction().getPlayerRank(cp);
        FactionPermSettings fs = f.getPermSettings();
        addButton(new ElementButton("Faction/Ally Chat Menu"));
        addButton(new ElementButton("Faction Home"));
        addButton(new ElementButton("Faction Info"));
        addButton(new ElementButton("Faction Map"));
        addButton(new ElementButton("Faction Perks"));
        addButton(new ElementButton("Faction Missions"));
        addButton(new ElementButton("-------------"));

        //Inbox
        FactionRank fr_m = fs.getAllowedToViewInbox();
        if (fr_m.hasPerm(fr)) addButton(new ElementButton("Faction Inbox"));

        //Invite
        FactionRank fr_i = fs.getAllowedToInvite();
        if (fr_i.hasPerm(fr)) addButton(new ElementButton("Invite Player"));

        //Claim Land
        FactionRank fr_cl = fs.getAllowedToClaim();
        if (fr_cl.hasPerm(fr)) addButton(new ElementButton("Claim Land"));

        //Demote
        FactionRank fr_d = fs.getAllowedToPromote();
        if (fr_d.hasPerm(fr)) addButton(new ElementButton("Demote Player"));

        //Promote
        FactionRank fr_p = fs.getAllowedToPromote();
        if (fr_p.hasPerm(fr)) addButton(new ElementButton("Promote Player"));

//        //Promote
//        FactionRank fr_p = fs.get();
//        if (fr_p.HasPerm(fr)) addButton(new ElementButton("Promote Player"));
//
//        //Promote
//        FactionRank fr_p = fs.getAllowedToPromote();
//        if (fr_p.HasPerm(fr)) addButton(new ElementButton("Promote Player"));

}

    @Override
    public boolean onRun(CorePlayer p) {
        super.onRun(p);
        FormResponseSimple s = getResponse();
        if (s.getClickedButton().getText().equalsIgnoreCase("Leave Faction"))
            new Leave(p, new String[0], CyberCoreMain.getInstance().FM);
        if (s.getClickedButton().getText().equalsIgnoreCase("Faction/Ally Chat Menu"))
            new Chat(p, new String[0], CyberCoreMain.getInstance().FM);
        if (s.getClickedButton().getText().equalsIgnoreCase("Faction Home"))
            p.showFormWindow(new FactionHomeWindow(p.getFaction()));
        if (s.getClickedButton().getText().equalsIgnoreCase("/f Chat"))
            new Chat(p, new String[0], CyberCoreMain.getInstance().FM);
        if (s.getClickedButton().getText().equalsIgnoreCase("/f Chat"))
            new Chat(p, new String[0], CyberCoreMain.getInstance().FM);
        if (s.getClickedButton().getText().equalsIgnoreCase("/f Chat"))
            new Chat(p, new String[0], CyberCoreMain.getInstance().FM);
        return true;
    }

    //    HashMap<String,>
}
