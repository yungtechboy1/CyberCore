package net.yungtechboy1.CyberCore.Manager.Crate.Tasks;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.block.Block;
import cn.nukkit.entity.Entity;
import cn.nukkit.item.Item;
import cn.nukkit.level.Level;
import cn.nukkit.level.Sound;
import cn.nukkit.level.particle.DestroyBlockParticle;
import cn.nukkit.level.particle.HugeExplodeSeedParticle;
import cn.nukkit.math.NukkitRandom;
import cn.nukkit.math.Vector3;
import cn.nukkit.network.protocol.AddItemEntityPacket;
import cn.nukkit.network.protocol.DataPacket;
import cn.nukkit.network.protocol.ExplodePacket;
import cn.nukkit.scheduler.Task;
import cn.nukkit.utils.ConfigSection;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.Manager.Crate.CrateMain;
import net.yungtechboy1.CyberCore.Manager.Crate.Utils.CenterText;
import net.yungtechboy1.CyberCore.Manager.Factions.CustomFloatingTextParticle;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class RollTick extends Task {

    public final Integer MAX =266;
    private CrateMain main;
    private ConfigSection data;
    private int tick;
    private boolean ff;
    private ConfigSection packets = new ConfigSection();

    public RollTick(CrateMain main, ConfigSection data) {
        this(main, data, 0);
    }

    public RollTick(CrateMain main, ConfigSection data, int tick) {
        this(main, data, tick, false);
    }

    public RollTick(CrateMain main, ConfigSection data, int tick, boolean ff) {
        this.main = main;
        this.data = data;
        this.tick = tick;
        this.ff = ff;
    }

    /*
    *
    * ConfigSection data = new ConfigSection() {{
            put("PlayerName", player.getName());
            put("slot", -1);
            put("possible-items", items);
            put("crate-name", cn);
        }};
    * */
    @Override
    public void onRun(int servertick) {
        //for(Map.Entry<String,Object> a: main.rolls.entrySet()){
        ConfigSection b = data;
        int slot = b.getInt("slot");
        String pn = b.getString("PlayerName").toLowerCase();
        Vector3 pos = (Vector3) b.get("pos");
        ArrayList<Item> pis = (ArrayList<Item>) b.get("possible-items");
        if (pis == null || pis.size() == 0) {
            Server.getInstance().getLogger().info("ERROR!!!! AT TICK NUMBER " + tick);
            return;
        }
        if (slot == -1) slot = new NukkitRandom().nextRange(0, pis.size() - 1);
        slot += new NukkitRandom().nextRange(0, 10);
        data.set("slot", slot);

        long eid;
        //Check to see if Item Exists
        if (this.main.eids.containsKey(pn)) {
            eid = this.main.eids.get(pn);
        } else {
            eid = Entity.entityCount++;
        }
        //roll slots and Add 1 to it
        while (slot >= pis.size()) {
            slot -= pis.size();
        }

        //Send Items
        AddItemEntityPacket pk = new AddItemEntityPacket();
        pk.entityUniqueId = eid;
        pk.entityRuntimeId = eid;
        //CompoundTag ct1 = new CompoundTag();
        pk.item = pis.get(slot);
        pk.x = pos.getFloorX() + 0.5f;
        pk.y = pos.getFloorY() + 0.5f;
        pk.z = pos.getFloorZ() + 0.5f;
        pk.speedX = (float) 0;
        pk.speedY = (float) 0;
        pk.speedZ = (float) 0;

        //Text Above Item
        String f1 = CenterText.sendCenteredMessage("§r§c|---->§b" + pis.get(slot).getName() + "§c<----|", 246);
        String f2 = CenterText.sendCenteredMessage(TextFormat.OBFUSCATED + "§b|||||||||§r" + TextFormat.RED + "ROLLING Item" + TextFormat.OBFUSCATED + "§b|||||||||§r", 246);
        CustomFloatingTextParticle ft = new CustomFloatingTextParticle(new Vector3(pos.getFloorX() + .5, pos.getFloorY() + 1, pos.getFloorZ() + .5), f1, f2);
        Long e1d = main.cratetxt.getLong(pn);
        if (e1d != 0) ft.entityId = e1d;
        DataPacket[] packets = ft.encode();

        Level l = main.CCM.getServer().getLevelByName("world");

        main.eids.put(pn, eid);
        Player p = main.CCM.getServer().getPlayerExact(pn);
        if (p != null) {
            l.addSound(pos, Sound.RANDOM_ORB);
            p.batchDataPacket(pk);
            for (DataPacket packet : packets) {
                p.dataPacket(packet);
            }

        }
        //l.addParticle(new RedstoneParticle(pos.add(.5,1,.5),2));
        //This one
        //l.addParticle(new DestroyBlockParticle(pos.add(.5,1,.5), Block.get(new NukkitRandom(tick).nextRange(0,100))));
        //Or this one
        //@TODO allow Config to choose break particle!
        l.addParticle(new DestroyBlockParticle(pos.add(.5, 1, .5), Block.get(Block.COBWEB)));


        if (!ff) {
            //Schedule Next
            int k = getDelayFromTick(tick);
            if(tick >= MAX){
                main.CCM.getServer().getScheduler().scheduleDelayedTask(new RollTick(main, data, tick + k,true), k);
            }else main.CCM.getServer().getScheduler().scheduleDelayedTask(new RollTick(main, data, tick + k), k);
        } else {
            //Last Tick for Roll... Send Item!
            data.put("slot", slot);
            //l.addParticle(new HugeExplodeParticle(pos));
            l.addParticle(new HugeExplodeSeedParticle(pos.add(.5, 1, .5)));
            l.addParticle(new HugeExplodeSeedParticle(pos.add(.5, 1, 1.5)));
            l.addParticle(new HugeExplodeSeedParticle(pos.add(1.5, 1, 1.5)));
            l.addParticle(new HugeExplodeSeedParticle(pos.add(1.5, 1, .5)));
            if (p != null) l.addParticle(new HugeExplodeSeedParticle(p));
            main.CCM.getServer().getScheduler().scheduleDelayedTask(new Explosion(main, pos.add(.5, 1, .5), l), 5);
            main.CCM.getServer().getScheduler().scheduleDelayedTask(new Explosion(main, pos.add(.5, 1, .5), l), 10);
            main.CCM.getServer().getScheduler().scheduleDelayedTask(new Explosion(main, pos.add(.5, 1, .5), l), 15);
            main.CCM.getServer().getScheduler().scheduleDelayedTask(new Explosion(main, pos.add(.5, 1, .5), l), 20);
            main.CCM.getServer().getScheduler().scheduleDelayedTask(new Explosion(main, pos.add(.5, 1, .5), l), 25);
            main.CCM.getServer().getScheduler().scheduleDelayedTask(new Explosion(main, pos.add(.5, 1, .5), l), 30);
            main.CCM.getServer().getScheduler().scheduleDelayedTask(new Explosion(main, pos.add(.5, 1, .5), l), 35);
            main.CCM.getServer().getScheduler().scheduleDelayedTask(new Explosion(main, pos.add(.5, 1, .5), l), 40);
            main.CCM.getServer().getScheduler().scheduleDelayedTask(new Explosion(main, pos.add(.5, 1, .5), l), 45);
            main.CCM.getServer().getScheduler().scheduleDelayedTask(new Explosion(main, pos.add(.5, 1, .5), l), 50);
            main.CCM.getServer().getScheduler().scheduleDelayedTask(new Explosion(main, pos.add(.5, 1, .5), l), 55);
            main.CCM.getServer().getScheduler().scheduleDelayedTask(new Explosion(main, pos.add(.5, 1, .5), l), 60);
            main.CCM.getServer().getScheduler().scheduleDelayedTask(new Explosion(main, pos.add(.5, 1, .5), l), 65);
            main.CCM.getServer().getScheduler().scheduleDelayedTask(new Explosion(main, pos.add(.5, 1, .5), l), 70);
            main.CCM.getServer().getScheduler().scheduleDelayedTask(new Explosion(main, pos.add(.5, 1, .5), l), 75);
            main.CCM.getServer().getScheduler().scheduleDelayedTask(new Explosion(main, pos.add(.5, 1, .5), l), 80);
            main.CCM.getServer().getScheduler().scheduleDelayedTask(new Explosion(main, pos.add(.5, 1, .5), l), 85);
            main.CCM.getServer().getScheduler().scheduleDelayedTask(new Explosion(main, pos.add(.5, 1, .5), l), 90);
            main.CCM.getServer().getScheduler().scheduleDelayedTask(new Explosion(main, pos.add(.5, 1, .5), l), 95);
            main.CCM.getServer().getScheduler().scheduleDelayedTask(new Explosion(main, pos.add(.5, 1, .5), l), 100);
            main.CCM.getServer().getScheduler().scheduleDelayedTask(new Explosion(main, pos.add(.5, 1, 1.5), l), 100);
            main.CCM.getServer().getScheduler().scheduleDelayedTask(new Explosion(main, pos.add(1.5, 1, 1.5), l), 100);
            main.CCM.getServer().getScheduler().scheduleDelayedTask(new Explosion(main, pos.add(1.5, 1, .5), l), 100);
            main.CCM.getServer().getScheduler().scheduleDelayedTask(new SendItem(main, data), 20 * 5);

        }
        //}
    }

    public int getDelayFromTick(int t) {
        if (t < 60) {
            return 3;
        } else if (t < 96) {
            return 4;
        } else if (t < 121) {
            return 5;
        } else if (t < 139) {
            return 6;
        } else if (t < 146) {
            return 7;
        } else if (t < 154) {
            return 8;
        } else if (t < 163) {
            return 9;
        } else if (t < 173) {
            return 10;
        } else if (t < 184) {
            return 11;
        } else if (t < 196) {
            return 12;
        } else if (t < 211) {
            return 15;
        } else if (t < 228) {
            return 17;
        } else if (t < 246) {
            return 18;
        } else if (t < 266) {
            return 20;
        } else {
            return 20;
        }

    }


}