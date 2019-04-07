package net.yungtechboy1.CyberCore.entities;

/**
 * Created by carlt on 4/7/2019.
 */
public interface EntityStackable {
    public boolean Stackable = false;
    int StackCount = 1;
    boolean IsStackable();
    int GetStackCount();
    void AddStackCount(int amount);
    void RemoveStackCount(int amount);
    void SetStackCount(int amount);

}
