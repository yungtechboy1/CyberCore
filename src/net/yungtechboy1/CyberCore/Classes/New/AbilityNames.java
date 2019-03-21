package net.yungtechboy1.CyberCore.Classes.New;

public enum AbilityNames {
    Raider_RaidMode(0);

    protected int K;
    AbilityNames(int k){
        K = k;
    }

    public int GetKey(){
        return K;
    }
}
