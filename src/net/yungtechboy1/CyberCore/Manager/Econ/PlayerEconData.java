package net.yungtechboy1.CyberCore.Manager.Econ;

import net.yungtechboy1.CyberCore.PlayerSettingsData;

/**
 * Created by carlt on 3/1/2019.
 */
public class PlayerEconData {
    public String Name;
    public double Cash;
    public int CreditScore;
    public int CreditLimit;
    public int UsedCredit;

    public PlayerEconData(PlayerSettingsData psd){
        Name = psd.Name;
        Cash = psd.Cash;
        CreditScore = psd.CreditScore;
        CreditLimit = psd.CreditLimit;
        UsedCredit = psd.UsedCredit;
    }
}
