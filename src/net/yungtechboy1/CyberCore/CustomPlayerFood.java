package net.yungtechboy1.CyberCore;

import cn.nukkit.PlayerFood;
import cn.nukkit.Server;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.event.entity.EntityRegainHealthEvent;
import cn.nukkit.potion.Effect;

public class CustomPlayerFood extends PlayerFood {


    private final CorePlayer corePlayer;
    public int foodTickTimer = 0;


    public CustomPlayerFood(CorePlayer corePlayer, int level, float foodSaturationLevel) {
        super(corePlayer, level, foodSaturationLevel);
        this.corePlayer = corePlayer;
    }


    public CorePlayer getPlayer() {
        return corePlayer;
    }

    @Override
    public void update(int tickDiff) {
        if (!this.getPlayer().isFoodEnabled()) return;
        if (this.getPlayer().isAlive()) {
            int diff = Server.getInstance().getDifficulty();
//            System.out.println("MAY NOT BE UNEDER 20"+getPlayer().getHealth());
            if(getPlayer().getHealth() >= 20)return;//No More Natural Healing!
//            System.out.println("IM SCARED! THIS IS CALLED "+tickDiff);
            if (this.getLevel() > 17) {
                this.foodTickTimer += tickDiff;
                if (this.foodTickTimer >= 80) {
                    if (this.getPlayer().getHealth() < this.getPlayer().getMaxHealth()) {
                        EntityRegainHealthEvent ev = new EntityRegainHealthEvent(this.getPlayer(), 1, EntityRegainHealthEvent.CAUSE_EATING);
                        this.getPlayer().heal(ev);
                        this.updateFoodExpLevel(3);
                    }
                    this.foodTickTimer = 0;
                }
            } else if (this.getLevel() == 13) {
                this.foodTickTimer += tickDiff;
                if (this.foodTickTimer >= 80) {
                    if (this.getPlayer().getHealth() < this.getPlayer().getMaxHealth()) {
                        EntityRegainHealthEvent ev = new EntityRegainHealthEvent(this.getPlayer(), .7f, EntityRegainHealthEvent.CAUSE_EATING);
                        this.getPlayer().heal(ev);
                        this.updateFoodExpLevel(2.5f);
                    }
                    this.foodTickTimer = 0;
                }
            } else if (this.getLevel() == 8) {
                this.foodTickTimer += tickDiff;
                if (this.foodTickTimer >= 80) {
                    if (this.getPlayer().getHealth() < this.getPlayer().getMaxHealth()) {
                        EntityRegainHealthEvent ev = new EntityRegainHealthEvent(this.getPlayer(), .4f, EntityRegainHealthEvent.CAUSE_EATING);
                        this.getPlayer().heal(ev);
                        this.updateFoodExpLevel(1.5f);
                    }
                    this.foodTickTimer = 0;
                }
            } else if (this.getLevel() == 5) {
                this.foodTickTimer += tickDiff;
                if (this.foodTickTimer >= 80) {
                    if (this.getPlayer().getHealth() < this.getPlayer().getMaxHealth()) {
                        EntityRegainHealthEvent ev = new EntityRegainHealthEvent(this.getPlayer(), .2f, EntityRegainHealthEvent.CAUSE_EATING);
                        this.getPlayer().heal(ev);
                        this.updateFoodExpLevel(.75f);
                    }
                    this.foodTickTimer = 0;
                }
            } else if (this.getLevel() == 0) {
                this.foodTickTimer += tickDiff;
                if (this.foodTickTimer >= 80) {
                    EntityDamageEvent ev = new EntityDamageEvent(this.getPlayer(), EntityDamageEvent.DamageCause.HUNGER, 1);
                    float now = this.getPlayer().getHealth();
                    if (diff == 1) {
                        if (now > 10) this.getPlayer().attack(ev);
                    } else if (diff == 2) {
                        if (now > 1) this.getPlayer().attack(ev);
                    } else {
                        this.getPlayer().attack(ev);
                    }

                    this.foodTickTimer = 0;
                }
            }
            if (this.getPlayer().hasEffect(Effect.HUNGER)) {
                this.updateFoodExpLevel(0.025);
            }
        }
    }


}
