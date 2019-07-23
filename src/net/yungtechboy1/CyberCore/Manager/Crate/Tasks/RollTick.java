package net.yungtechboy1.CyberCore.Manager.Crate.Tasks;

import Crate.CenterText;
import Crate.CustomFloatingText;
import Crate.MainClass;
import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.block.Block;
import cn.nukkit.entity.Entity;
import cn.nukkit.item.Item;
import cn.nukkit.level.Level;
import cn.nukkit.level.particle.DestroyBlockParticle;
import cn.nukkit.level.particle.HugeExplodeSeedParticle;
import cn.nukkit.level.sound.ExperienceOrbSound;
import cn.nukkit.level.sound.ExplodeSound;
import cn.nukkit.level.sound.Sound;
import cn.nukkit.math.NukkitRandom;
import cn.nukkit.math.Vector3;
import cn.nukkit.network.protocol.AddItemEntityPacket;
import cn.nukkit.network.protocol.DataPacket;
import cn.nukkit.scheduler.PluginTask;
import cn.nukkit.scheduler.ServerScheduler;
import cn.nukkit.utils.ConfigSection;
import cn.nukkit.utils.TextFormat;

import java.util.ArrayList;

public class RollTick extends PluginTask<MainClass> {
    private MainClass main;
    private ConfigSection data;
    private int tick;
    private boolean ff;
    private ConfigSection packets = new ConfigSection();

    public RollTick(MainClass main, ConfigSection data) {
        this(main, data, 0);
    }

    public RollTick(MainClass main, ConfigSection data, int tick) {
        this(main, data, tick, false);
    }

    public RollTick(MainClass main, ConfigSection data, int tick, boolean ff) {
        super(main);
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
        //Check for 0 Ticks and -1 Slot, then start Recursive Ticking
        if (tick == 0) {
            ServerScheduler SS = main.getServer().getScheduler();
            int a = 1;
            SS.scheduleDelayedTask(new RollTick(main, data, ++tick), (a += 3));
            SS.scheduleDelayedTask(new RollTick(main, data, ++tick), (a += 3));
            SS.scheduleDelayedTask(new RollTick(main, data, ++tick), (a += 3));
            SS.scheduleDelayedTask(new RollTick(main, data, ++tick), (a += 3));
            SS.scheduleDelayedTask(new RollTick(main, data, ++tick), (a += 3));
            SS.scheduleDelayedTask(new RollTick(main, data, ++tick), (a += 3));
            SS.scheduleDelayedTask(new RollTick(main, data, ++tick), (a += 3));
            SS.scheduleDelayedTask(new RollTick(main, data, ++tick), (a += 3));
            SS.scheduleDelayedTask(new RollTick(main, data, ++tick), (a += 3));
            SS.scheduleDelayedTask(new RollTick(main, data, ++tick), (a += 3));
            SS.scheduleDelayedTask(new RollTick(main, data, ++tick), (a += 3));
            SS.scheduleDelayedTask(new RollTick(main, data, ++tick), (a += 3));
            SS.scheduleDelayedTask(new RollTick(main, data, ++tick), (a += 3));
            SS.scheduleDelayedTask(new RollTick(main, data, ++tick), (a += 3));
            SS.scheduleDelayedTask(new RollTick(main, data, ++tick), (a += 3));
            SS.scheduleDelayedTask(new RollTick(main, data, ++tick), (a += 3));
            SS.scheduleDelayedTask(new RollTick(main, data, ++tick), (a += 3));
            SS.scheduleDelayedTask(new RollTick(main, data, ++tick), (a += 3));
            SS.scheduleDelayedTask(new RollTick(main, data, ++tick), (a += 3));
            SS.scheduleDelayedTask(new RollTick(main, data, ++tick), (a += 3));

            SS.scheduleDelayedTask(new RollTick(main, data, ++tick), (a += 4));
            SS.scheduleDelayedTask(new RollTick(main, data, ++tick), (a += 4));
            SS.scheduleDelayedTask(new RollTick(main, data, ++tick), (a += 4));
            SS.scheduleDelayedTask(new RollTick(main, data, ++tick), (a += 4));
            SS.scheduleDelayedTask(new RollTick(main, data, ++tick), (a += 4));
            SS.scheduleDelayedTask(new RollTick(main, data, ++tick), (a += 4));
            SS.scheduleDelayedTask(new RollTick(main, data, ++tick), (a += 4));
            SS.scheduleDelayedTask(new RollTick(main, data, ++tick), (a += 4));
            SS.scheduleDelayedTask(new RollTick(main, data, ++tick), (a += 4));

            SS.scheduleDelayedTask(new RollTick(main, data, ++tick), (a += 5));
            SS.scheduleDelayedTask(new RollTick(main, data, ++tick), (a += 5));
            SS.scheduleDelayedTask(new RollTick(main, data, ++tick), (a += 5));
            SS.scheduleDelayedTask(new RollTick(main, data, ++tick), (a += 5));
            SS.scheduleDelayedTask(new RollTick(main, data, ++tick), (a += 5));

            SS.scheduleDelayedTask(new RollTick(main, data, ++tick), (a += 6));
            SS.scheduleDelayedTask(new RollTick(main, data, ++tick), (a += 6));
            SS.scheduleDelayedTask(new RollTick(main, data, ++tick), (a += 6));

            SS.scheduleDelayedTask(new RollTick(main, data, ++tick), (a += 7));

            SS.scheduleDelayedTask(new RollTick(main, data, ++tick), (a += 8));

            SS.scheduleDelayedTask(new RollTick(main, data, ++tick), (a += 9));

            SS.scheduleDelayedTask(new RollTick(main, data, ++tick), (a += 10));

            SS.scheduleDelayedTask(new RollTick(main, data, ++tick), (a += 12));

            SS.scheduleDelayedTask(new RollTick(main, data, ++tick), (a += 15));

            /*SS.scheduleDelayedTask(new RollTick(main, data, ++tick), (a += 16));
            SS.scheduleDelayedTask(new RollTick(main, data, ++tick), (a += 16));
            SS.scheduleDelayedTask(new RollTick(main, data, ++tick), (a += 16));*/

            SS.scheduleDelayedTask(new RollTick(main, data, ++tick), (a += 17));

            SS.scheduleDelayedTask(new RollTick(main, data, ++tick), (a += 18));

            SS.scheduleDelayedTask(new RollTick(main, data, ++tick,true), (a += 20));




            /*
            SS.scheduleDelayedTask(new RollTick(main, data, ++tick), a++ * 3);//3
            SS.scheduleDelayedTask(new RollTick(main, data, ++tick), a++ * 3);//6
            SS.scheduleDelayedTask(new RollTick(main, data, ++tick), a++ * 3);//9
            SS.scheduleDelayedTask(new RollTick(main, data, ++tick), a++ * 3);//12
            SS.scheduleDelayedTask(new RollTick(main, data, ++tick), a++ * 3);//15
            SS.scheduleDelayedTask(new RollTick(main, data, ++tick), a++ * 3);//18
            SS.scheduleDelayedTask(new RollTick(main, data, ++tick), a++ * 3);//21
            SS.scheduleDelayedTask(new RollTick(main, data, ++tick), a++ * 3);//24
            SS.scheduleDelayedTask(new RollTick(main, data, ++tick), a++ * 3);//27
            SS.scheduleDelayedTask(new RollTick(main, data, ++tick), a++ * 3);//30
            SS.scheduleDelayedTask(new RollTick(main, data, ++tick), a++ * 3);//33
            SS.scheduleDelayedTask(new RollTick(main, data, ++tick), a++ * 3);//36
            SS.scheduleDelayedTask(new RollTick(main, data, ++tick), a++ * 3);//39
            SS.scheduleDelayedTask(new RollTick(main, data, ++tick), a++ * 3);//42
            SS.scheduleDelayedTask(new RollTick(main, data, ++tick), a++ * 3);//45
            SS.scheduleDelayedTask(new RollTick(main, data, ++tick), a++ * 3);//48
            SS.scheduleDelayedTask(new RollTick(main, data, ++tick), a++ * 3);//51
            SS.scheduleDelayedTask(new RollTick(main, data, ++tick), a++ * 3);//54
            SS.scheduleDelayedTask(new RollTick(main, data, ++tick), a++ * 3);//57
            SS.scheduleDelayedTask(new RollTick(main, data, ++tick), a++ * 3);//60 - 3 Secs

            SS.scheduleDelayedTask(new RollTick(main, data, ++tick), (a += 2) * 3);//66

            SS.scheduleDelayedTask(new RollTick(main, data, ++tick), (a += 2) * 3);//66
            SS.scheduleDelayedTask(new RollTick(main, data, ++tick), (a += 2) * 3);//72
            SS.scheduleDelayedTask(new RollTick(main, data, ++tick), (a += 2) * 3);//78
            SS.scheduleDelayedTask(new RollTick(main, data, ++tick), (a += 2) * 3);//84 - 4 Secs
            SS.scheduleDelayedTask(new RollTick(main, data, ++tick), (a += 2) * 3);//90
            SS.scheduleDelayedTask(new RollTick(main, data, ++tick), (a += 2) * 3);//96
            SS.scheduleDelayedTask(new RollTick(main, data, ++tick), (a += 2) * 3);//102
            SS.scheduleDelayedTask(new RollTick(main, data, ++tick), (a += 2) * 3);//108
            SS.scheduleDelayedTask(new RollTick(main, data, ++tick), (a += 2) * 3);//114
            SS.scheduleDelayedTask(new RollTick(main, data, ++tick), (a += 2) * 3);//120 - 6 Secs


            SS.scheduleDelayedTask(new RollTick(main, data, ++tick), (a += 3) * 3);//130
            SS.scheduleDelayedTask(new RollTick(main, data, ++tick), (a += 3) * 3);//130
            SS.scheduleDelayedTask(new RollTick(main, data, ++tick), (a += 3) * 3);//130
            SS.scheduleDelayedTask(new RollTick(main, data, ++tick), (a += 3) * 3);//130
            SS.scheduleDelayedTask(new RollTick(main, data, ++tick), (a += 3) * 3);//130
            SS.scheduleDelayedTask(new RollTick(main, data, ++tick), (a += 3) * 3);//130

            SS.scheduleDelayedTask(new RollTick(main, data, ++tick), (a += 4) * 3);//130
            SS.scheduleDelayedTask(new RollTick(main, data, ++tick), (a += 4) * 3);//130
            SS.scheduleDelayedTask(new RollTick(main, data, ++tick), (a += 4) * 3);//130

            SS.scheduleDelayedTask(new RollTick(main, data, ++tick), (a += 5) * 3);//130

            SS.scheduleDelayedTask(new RollTick(main, data, ++tick), (a += 6) * 3);//130
            */
            return;
        }
        int slot = b.getInt("slot");
        String pn = b.getString("PlayerName").toLowerCase();
        Vector3 pos = (Vector3) b.get("pos");
        ArrayList<Item> pis = (ArrayList<Item>) b.get("possible-items");
        if (pis == null || pis.size() == 0) {
            Server.getInstance().getLogger().info("ERROR!!!! AT TICK NUMBER " + tick);
            return;
        }
        if (slot == -1) slot = new NukkitRandom().nextRange(0, pis.size() - 1);
        slot += tick;

        long eid;
        //Check to see if Item Exists
        if (this.main.eids.containsKey(pn)) {
            eid = this.main.eids.getLong(pn);
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
        String f1 = CenterText.sendCenteredMessage("§r§c|---->§b"+ pis.get(slot).getName()+"§c<----|",246);
        String f2 = CenterText.sendCenteredMessage(TextFormat.OBFUSCATED + "§b|||||||||§r" + TextFormat.RED + "ROLLING Item" + TextFormat.OBFUSCATED + "§b|||||||||§r",246);
        CustomFloatingText ft = new CustomFloatingText(new Vector3(pos.getFloorX() + .5, pos.getFloorY() + 1, pos.getFloorZ() + .5), f1, f2);
        Long e1d = main.cratetxt.getLong(pn);
        if(e1d != 0)ft.entityId = e1d;
        DataPacket[] packets = ft.encode();

        Level l = main.getServer().getLevelByName("world");

        main.eids.put(pn, eid);
        Player p = main.getServer().getPlayerExact(pn);
        if (p != null) {
            l.addSound(new ExperienceOrbSound(pos),p);

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

        //Last Tick for Roll... Send Item!
        if (ff) {
            data.put("slot", slot);
            //l.addParticle(new HugeExplodeParticle(pos));
            l.addParticle(new HugeExplodeSeedParticle(pos.add(.5, 1, .5)));
            l.addParticle(new HugeExplodeSeedParticle(pos.add(.5, 1, 1.5)));
            l.addParticle(new HugeExplodeSeedParticle(pos.add(1.5, 1, 1.5)));
            l.addParticle(new HugeExplodeSeedParticle(pos.add(1.5, 1, .5)));
            if (p != null) l.addParticle(new HugeExplodeSeedParticle(p));
            main.getServer().getScheduler().scheduleDelayedTask(new Explosion(main, pos.add(.5, 1, .5), l), 5);
            main.getServer().getScheduler().scheduleDelayedTask(new Explosion(main, pos.add(.5, 1, .5), l), 10);
            main.getServer().getScheduler().scheduleDelayedTask(new Explosion(main, pos.add(.5, 1, .5), l), 15);
            main.getServer().getScheduler().scheduleDelayedTask(new Explosion(main, pos.add(.5, 1, .5), l), 20);
            main.getServer().getScheduler().scheduleDelayedTask(new Explosion(main, pos.add(.5, 1, .5), l), 25);
            main.getServer().getScheduler().scheduleDelayedTask(new Explosion(main, pos.add(.5, 1, .5), l), 30);
            main.getServer().getScheduler().scheduleDelayedTask(new Explosion(main, pos.add(.5, 1, .5), l), 35);
            main.getServer().getScheduler().scheduleDelayedTask(new Explosion(main, pos.add(.5, 1, .5), l), 40);
            main.getServer().getScheduler().scheduleDelayedTask(new Explosion(main, pos.add(.5, 1, .5), l), 45);
            main.getServer().getScheduler().scheduleDelayedTask(new Explosion(main, pos.add(.5, 1, .5), l), 50);
            main.getServer().getScheduler().scheduleDelayedTask(new Explosion(main, pos.add(.5, 1, .5), l), 55);
            main.getServer().getScheduler().scheduleDelayedTask(new Explosion(main, pos.add(.5, 1, .5), l), 60);
            main.getServer().getScheduler().scheduleDelayedTask(new Explosion(main, pos.add(.5, 1, .5), l), 65);
            main.getServer().getScheduler().scheduleDelayedTask(new Explosion(main, pos.add(.5, 1, .5), l), 70);
            main.getServer().getScheduler().scheduleDelayedTask(new Explosion(main, pos.add(.5, 1, .5), l), 75);
            main.getServer().getScheduler().scheduleDelayedTask(new Explosion(main, pos.add(.5, 1, .5), l), 80);
            main.getServer().getScheduler().scheduleDelayedTask(new Explosion(main, pos.add(.5, 1, .5), l), 85);
            main.getServer().getScheduler().scheduleDelayedTask(new Explosion(main, pos.add(.5, 1, .5), l), 90);
            main.getServer().getScheduler().scheduleDelayedTask(new Explosion(main, pos.add(.5, 1, .5), l), 95);
            main.getServer().getScheduler().scheduleDelayedTask(new Explosion(main, pos.add(.5, 1, .5), l), 100);
            main.getServer().getScheduler().scheduleDelayedTask(new Explosion(main, pos.add(.5, 1, 1.5), l), 100);
            main.getServer().getScheduler().scheduleDelayedTask(new Explosion(main, pos.add(1.5, 1, 1.5), l), 100);
            main.getServer().getScheduler().scheduleDelayedTask(new Explosion(main, pos.add(1.5, 1, .5), l), 100);
            main.getServer().getScheduler().scheduleDelayedTask(new SendItem(main, data), 20 * 5);
        }
        //}
    }
}
