package net.yungtechboy1.CyberCore.Manager.Form;

import cn.nukkit.utils.Hash;
import net.yungtechboy1.CyberCore.CyberCoreMain;

import java.util.HashMap;

public class FormManager {
    CyberCoreMain CCM;
    HashMap<String, HashMap<String,String>> L = new HashMap<>();


    public FormManager(CyberCoreMain CCM) {
        this.CCM = CCM;
    }


}
