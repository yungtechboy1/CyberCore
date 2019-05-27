package net.yungtechboy1.CyberCore.Manager.Form.Windows;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.response.FormResponseSimple;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.FormType;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;
import net.yungtechboy1.CyberCore.Manager.Form.CyberFormSimple;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static net.yungtechboy1.CyberCore.Manager.Factions.FactionString.Error_PlayerInFaction;
import static net.yungtechboy1.CyberCore.Manager.Factions.FactionString.Error_SA224;

public class FactionInviteChooseWindow extends CyberFormSimple {
    public FactionInviteChooseWindow(ArrayList<Player> af) {
        this(new ArrayList(), af);
    }

    public FactionInviteChooseWindow(List<ElementButton> buttons, ArrayList<Player> af) {
        super(FormType.MainForm.Faction_Invite_Choose, "CyberFactions | Invite Player", "", buttons);
        int k = 0;
        addButton(new ElementButton("Grinch!"));
        for (Player p : af) {
            k++;
            if (k > 20) break;
            addButton(new ElementButton(p.getName()));
        }
    }


    @Override
    public void onRun(CorePlayer cp) {
        super.onRun(cp);
        FormResponseSimple fic = getResponse();
        String pn = fic.getClickedButton().getText();
        CorePlayer cpp = (CorePlayer) CyberCoreMain.getInstance().getServer().getPlayerExact(pn);
        if (cpp == null) {
            cp.sendMessage("Error! The name '" + pn + "' could not be found on server!");
            return;
        } else {
            if (null != CyberCoreMain.getInstance().FM.FFactory.getPlayerFaction(cpp)) {
                //TODO Allow Setting to ignore Faction messages
                //Sounds like a lot of work lol >:(
                cp.sendMessage(Error_PlayerInFaction.getMsg());
                return;
            }
            Integer time = (int) (Calendar.getInstance().getTime().getTime() / 1000) + 60 * 5;
            if (_Fac == null) {
                cp.sendMessage(Error_SA224.getMsg());
                return;
            }
            _Fac.AddInvite(cpp.getName().toLowerCase(), time);
            CyberCoreMain.getInstance().FM.FFactory.InvList.put(cpp.getName().toLowerCase(), _Fac.GetName());

            cp.sendMessage(FactionsMain.NAME + TextFormat.GREEN + "Successfully invited " + cpp.getName() + "!");
            cpp.sendMessage(FactionsMain.NAME + TextFormat.YELLOW + "You have been invited to faction.\n" + TextFormat.GREEN + "Type '/f accept' or '/f deny' into chat to accept or deny!");
        }
    }
}