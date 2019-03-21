package net.yungtechboy1.CyberCore.Manager.FT;

/**
 * Created by carlt on 3/8/2019.
 */
public enum FloatingTextType {
    FT_Standard(0), FT_Popup(1);

    int k;

    FloatingTextType(int t) {
        k = t;
    }

    public FloatingTextType GetFloatingTextType(int k) {
        switch (k) {
            case 0:
                return FT_Standard;
            case 1:
                return FT_Popup;
        }
        return FT_Standard;
    }
}