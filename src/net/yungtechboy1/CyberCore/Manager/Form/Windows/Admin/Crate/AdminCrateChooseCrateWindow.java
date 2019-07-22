package net.yungtechboy1.CyberCore.Manager.Form.Windows.Admin.Crate;

import cn.nukkit.form.element.ElementButton;
import cn.nukkit.level.Position;
import cn.nukkit.math.Vector3;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.FormType;
import net.yungtechboy1.CyberCore.Manager.Crate.CrateData;
import net.yungtechboy1.CyberCore.Manager.Crate.CrateMain;
import net.yungtechboy1.CyberCore.Manager.Crate.CrateObject;
import net.yungtechboy1.CyberCore.Manager.Form.CyberFormSimple;

public class AdminCrateChooseCrateWindow extends CyberFormSimple {
    private Vector3 _V;
    private CrateMain _C;

    public AdminCrateChooseCrateWindow(Vector3 v, CrateMain c) {
        super(FormType.MainForm.Crate_Admin_ChooseCrate, "Choose Crate");
        _C = c;
        _V = v;
        for(CrateData cd: _C.getCrateMap().values()){
            addButton(new ElementButton(cd.Name));
        }
    }

    @Override
    public boolean onRun(CorePlayer p) {
        super.onRun(p);
        int k = getResponse().getClickedButtonId();
        if(k > _C.getCrateMap().size()){
            p.sendMessage("Invalid Selection!");return true;
        }
        CrateData cd = (CrateData) _C.getCrateMap().values().toArray()[k];
        if(cd == null){
            System.out.println("Errrororororo!!!!!");
            return true;
        }
        _C.CrateChests.put(_V,new CrateObject(Position.fromObject(_V,p.getLevel()),cd));
        return true;
    }
}
