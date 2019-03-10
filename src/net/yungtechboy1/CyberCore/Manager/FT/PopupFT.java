package net.yungtechboy1.CyberCore.Manager.FT;

import cn.nukkit.level.Position;
import cn.nukkit.math.Vector3;

/**
 * Created by carlt on 3/8/2019.
 */
public class PopupFT extends FloatingTextContainer {
    FloatingTextType TYPE = FloatingTextType.FT_Popup;
    int Lifespan = 150;// 7.5 secs
    long Created = -1;
    int Updates = -1;

    public PopupFT(FloatingTextFactory ftf, Position pos, String syntax) {
        super(ftf, pos, syntax);
        Created = ftf.CCM.FM.Main.getServer().getTick();
    }

    public boolean CheckKill(int t) {
        return (t > Created + Lifespan) || _CE_Done;
    }

    @Override
    public void OnUpdate(int tick) {
        super.OnUpdate(tick);
        _CE_Done = CheckKill(tick);
        if(_CE_Done)return;
        Updates++;
        if(Updates >= 1){
            Position op  = Pos.clone();//Old Position
            op.add(new Vector3(0,.7,0));//Raise .7 height
        }
        if(Updates >= 5)kill();
    }
}
