package net.yungtechboy1.CyberCore.Manager.Econ;

import cn.nukkit.Player;
import net.yungtechboy1.CyberCore.CyberCoreMain;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;

/**
 * Created by carlt on 3/1/2019.
 */
public class EconManager {
    public CyberCoreMain Main;
    public HashMap<String, PlayerEconData> PEDL;
    public ArrayList<PlayerBorrowAgreement> PBAL;

    public EconManager(CyberCoreMain m) {
        Main = m;
        Player p;

    }

    public PlayerEconData GetData(Player p) {
        return GetData(p.getName());
    }

    public PlayerEconData GetData(String n) {
        if (!PEDL.containsKey(n)) return CreateDefault(n);
        return PEDL.get(n);
    }

    public PlayerEconData CreateDefault(String n) {
        PlayerEconData a = new PlayerEconData();
        a.Cash = 1000;
        a.CreditLimit = 1000;
        a.CreditScore = 350;//Out of 1000
        a.Name = n;
        a.UsedCredit = 0;
        PEDL.put(n, a);
        return a;
    }

    public boolean MakeTransaction(String s, double price) {
    if(price > GetMoney(s))return false;
    TakeMoney(s,price);
    return true;
    }

    public void TakeMoney(String s, double price) {
        if (price <= 0) return;
        PlayerEconData ped = GetData(s);
        ped.Cash -= price;
    }

    public void AddMoney(String s, double price) {
        if (price <= 0) return;
        PlayerEconData ped = GetData(s);
        ped.Cash += price;
    }

    public double GetMoney(String s) {
        PlayerEconData ped = GetData(s);
        return ped.Cash;
    }


}
