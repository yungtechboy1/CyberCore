package net.yungtechboy1.CyberCore.Manager.FT;

import cn.nukkit.level.Position;

/**
 * Created by carlt on 3/8/2019.
 */
public class PopupFT extends FloatingTextContainer {
    FloatingTextType TYPE = FloatingTextType.FT_Popup;
    int Lifespan = 60;// 3  Secs
    long Created = -1;

    public PopupFT(FloatingTextFactory ftf, Position pos, String syntax) {
        super(ftf, pos, syntax);
        Created = ftf.CCM.FM.Main.getServer().getTick();
    }

    public boolean CheckKill(int t){
        return (t > Created + Lifespan);
    }

    @Override
    public void OnUpdate(int tick) {
        super.OnUpdate(tick);
        if(CheckKill(tick))
    }
}
