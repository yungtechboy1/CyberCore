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
    int interval = 10;
    int _nu = -1;//Next Update!

    public PopupFT(FloatingTextFactory ftf, Position pos, String syntax) {
        super(ftf, pos, syntax);
        Created = FTF.CCM.getServer().getTick();
    }

    public boolean CheckKill(int t) {
        System.out.println(t+"|"+(Created + Lifespan));
        return (t > Created + Lifespan) || _CE_Done;
    }

    @Override
    public void OnUpdate(int tick) {
        super.OnUpdate(tick);
        if (tick >= _nu) {
            _nu = tick + interval;
            _CE_Done = CheckKill(tick);
            if (_CE_Done) return;
            Updates++;
            if (Updates >= 1) {
                Position op = Pos.clone();//Old Position
                Pos = op.add(new Vector3(0, .7, 0));//Raise .7 height
            }
            if (Updates >= 5) kill();
        }
    }
}
