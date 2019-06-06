package net.yungtechboy1.CyberCore.Classes.New;

public class Buff {
    private final BuffType bt;
    private final float amount;
    private boolean debuff = false;

    public Buff(BuffType bt, float amount) {
        this.bt = bt;
        this.amount = amount;
    }
    protected Buff(BuffType bt, float amount, boolean dbuff) {
        this.bt = bt;
        this.amount = amount;
        debuff = dbuff;
    }

    public BuffType getBt() {
        return bt;
    }

    public float getAmount() {
        return amount;
    }

    public boolean isDebuff(){
        return debuff;
    }

    public enum BuffType {
        NULL,
        Health,
        Armor,
        DamageFromPlayer,
        DamageToPlayer,
        DamageToEntity,
        DamageFromEntity,
        //All damage
        Damage,
        Movement,
        SwingSpeed,
        Reach,
        Healing, SuperFoodHeartRegin, Magic, Jump,
    }
}
