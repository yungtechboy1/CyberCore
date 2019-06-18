package net.yungtechboy1.CyberCore.Events.Custom;

import net.yungtechboy1.CyberCore.CorePlayer;

public class CyberPlayerEvent extends CyberEvent {
    CorePlayer CP;

    public CyberPlayerEvent(CorePlayer CP) {
        this.CP = CP;
    }

    public CorePlayer getPlayer() {
        return getCP();
    }
    public CorePlayer getCP() {
        return CP;
    }
}
