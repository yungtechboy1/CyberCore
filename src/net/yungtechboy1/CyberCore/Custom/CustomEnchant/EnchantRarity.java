package net.yungtechboy1.CyberCore.Custom.CustomEnchant;

/**
 * Created by carlt on 3/21/2019.
 */
public enum EnchantRarity {
    R10(10),
    R20(20),
    R30(30),
    R40(40),
    R50(50),
    R60(60),
    R70(70),
    R80(80),
    R90(90),
    R100(100);

    int Val;

    EnchantRarity(int v) {
        Val = v;
    }
    public int GetVal(){
        return Val;
    }


}
