package net.yungtechboy1.CyberCore.Manager.Form.Windows.Faction;

import cn.nukkit.form.element.ElementButton;
import cn.nukkit.level.Position;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.FormType;
import net.yungtechboy1.CyberCore.Manager.Factions.Faction;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionRank;
import net.yungtechboy1.CyberCore.Manager.Form.CyberFormSimple;

import java.util.ArrayList;

public class FactionHomeWindow extends CyberFormSimple {
    Faction _F;
    ArrayList<Faction> _fl = new ArrayList<>();
    public FactionHomeWindow(Faction f) {
        super(FormType.MainForm.Faction_Home, "Faction Home Menu");
        _F = f;
        FactionRank r = f.getSettings().getAllowedToSetHome();
        addButton(new ElementButton("Teleport to Faction Home"));
        for(String fan:f.getFAlly()){
            Faction fa = _plugin.FM.FFactory.getFaction(fan);
            if(fa == null)continue;
            addButton(new ElementButton("Ally Faction Teleport > " + fa.getDisplayName()));
            _fl.add(fa);
        }
    }

    @Override
    public boolean onRun(CorePlayer p) {
        int k = getResponse().getClickedButtonId();
        if(k == 0){
            Position pos = Position.fromObject(_F.GetHome(),p.getLevel());
            p.delayTeleport(20*5,pos,true,3);
        }else{
            int kk = k-1;
            if(kk < 0)return true;
            Faction f = _fl.get(kk);
            Position pos = Position.fromObject(f.GetHome(),p.getLevel());
            p.delayTeleport(20*5,pos,true,3);
        }
        return true;
    }
}
