package net.yungtechboy1.CyberCore.Custom.Item;

public enum ModifiedLength{
    Short(1),
    Medium(2),
    Long(3),
    Dagger(4),
    Dual_Dagger(5),
    Spear(6);
    private int i;
    ModifiedLength(int Id){
        i = Id;
    }

    public int GetId(){
        return i;
    }
}
