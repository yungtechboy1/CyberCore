package net.yungtechboy1.CyberCore.Manager.Factions.BossBar;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;

    public class BossBar{

        public int eid;
        public Player owner;
        public String title;
        public int maxHealth;
        public int currentHealth;
        public boolean visible = true;

        public Long lastUpdated = System.currentTimeMillis();
        public int totalUpdate = 0;

        public int startTime = -1;
        public int endTime = -1;
        public boolean showRemainTime = true;

        public BossBar(Player owner){
            this(owner, "Undefined");
        }

        public BossBar(Player owner, String title){
            this(owner, title, 0);
        }

        public BossBar(Player owner, String title, int currentHealth){
            this(owner, title, currentHealth, 100);
        }

        public BossBar(Player owner, String title, int currentHealth, int maxHealth){
            if(maxHealth < 0){
                maxHealth = 0;
            }
            if(currentHealth > maxHealth){
                currentHealth = maxHealth;
            }
            this.owner = owner;
            this.eid = (int) Entity.entityCount++;
            this.title = title;
            this.currentHealth = currentHealth;
            this.maxHealth = maxHealth;
        }

        public void setVisible(boolean bool){
            if(bool){
                send();
            }else{
                Packet.removeBossBar(getOwner(), this.eid);
            }
            this.visible = bool;
        }

        public void setHealth(Integer health){
            if(health > this.maxHealth){
                health = this.maxHealth;
            }
            if(health < 0){
                health = 0;
            }
            currentHealth = health;

            Packet.sendPercentage(getOwner(),this.eid, this.currentHealth / (double) this.maxHealth * 100);
        }

        public int getHealth(){
            return this.currentHealth;
        }

        public void setMaxHealth(int maxHealth){
            if(maxHealth < 0){
                maxHealth = 0;
            }
            this.maxHealth = maxHealth;
            Packet.sendPercentage(getOwner(),this.eid, this.currentHealth / (double) this.maxHealth * 100);
        }

        public int getMaxHealth(){
            return this.maxHealth;
        }

        public Player getOwner(){
            return this.owner;
        }

        public void setTitle(String title){
            this.title = title;
            Packet.sendTitle(getOwner(),this.eid, this.title);
        }

        public String getTitle(){
            return this.title;
        }

        public void setTimer(int second){
            this.setTimer(second, true);
        }

        public void setTimer(int second, boolean showRemainTime){
            int currentTime = (int) (System.currentTimeMillis() / 1000l);
            this.startTime = currentTime;
            this.endTime = currentTime + second;
            this.showRemainTime = showRemainTime;
            this.onUpdate();
        }

        //update interval is 10 tick
        public void onUpdate(){

        }

        public void send(){
            if(! this.visible){
                return;
            }
            Packet.sendBossBar(getOwner(), this.eid, this.title);
            Packet.sendPercentage(getOwner(), this.eid, this.currentHealth / (double) this.maxHealth * 100);

        }

        public void sendTo(Player player){
            if(! this.visible){
                return;
            }
            Packet.sendBossBar(player, this.eid, this.title);
            Packet.sendPercentage(player, this.eid, this.currentHealth / (double) this.maxHealth * 100);
            return;
        }
    }

