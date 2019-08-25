package net.yungtechboy1.CyberCore.Manager.Crate.Tasks;

import cn.nukkit.InterruptibleThread;
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
import cn.nukkit.network.protocol.RemoveEntityPacket;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.Manager.Crate.Utils.CenterText;
import net.yungtechboy1.CyberCore.Manager.Factions.CustomFloatingTextParticle;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class CrateTickThread extends Thread implements InterruptibleThread {


    public final Integer MAX = 800 / 3;
    //    public static CyberCoreMain CCM;
    private String player;
    private ArrayList<Item> items;
    private String name;
    //    private HashMap<String, Long> eids = new HashMap<>();
    private Vector3 asVector3;

    public CrateTickThread(String player, ArrayList<Item> items, String name, Vector3 asVector3) {
//        CCM = ccm;
        this.player = player;
        this.items = items;
        this.name = name;
        this.asVector3 = asVector3;
        start();
    }

    public void CTstop() {
        if (isAlive()) interrupt();
    }

    public void run() {
        int life = 0;
        boolean ff = false;
        long eid2 = -1l;
        long eid = -1l;
        int slot = -1;
        int lasttick = -1;
//        int lt = -1;
        int tick = 0;
        int t = -1;
        int waittotick = -1;
//        System.out.println("11111111111111111111");
        while (Server.getInstance().isRunning()) {
            life++;
            int tt = Server.getInstance().getTick();
//            if(lt +2 > t)System.out.println("W|"+life);
            System.out.println("W|" + life + "||" + tt);
            if (tt > lasttick) {
//                lt = tt;
                System.out.println("1|" + tick);
//                System.out.println("||||||||======");
                lasttick = tick;//TODO BIGGGGGGGG LOOK HERERE FIX E!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!


                String pn = player;
                Vector3 pos = asVector3;
                ArrayList<Item> pis = items;
                if (pis == null || pis.size() == 0) {
                    Server.getInstance().getLogger().info("CRATE TICK ERROR!!!! AT TICK NUMBER " + tick + " | No Items Sent!");
                    CTstop();
                    return;
                }
                System.out.println("2||");
                NukkitRandom nr = new NukkitRandom();
                if (slot == -1) slot = nr.nextRange(0, pis.size() - 1);
                slot += nr.nextRange(0, pis.size());
                System.out.println("3|||");
                while (slot >= pis.size()) {
                    slot -= nr.nextRange(1, pis.size() - 1);
                }
                if (slot < 0) {
                    slot = Math.abs(slot);
                }
                System.out.println("4||||" + pis.size() + "|" + slot);


                //Check to see if Item Exists
                if (eid == -1) {
                    eid = Entity.entityCount++;
                }
                //roll slots and Add 1 to it
//                while (slot >= pis.size()) {
//                    slot -= pis.size();
//                }

                System.out.println("5|||||");
                //Send Items
                AddItemEntityPacket pk = new AddItemEntityPacket();
                pk.entityUniqueId = eid;
                pk.entityRuntimeId = eid;
                //CompoundTag ct1 = new CompoundTag();

                System.out.println("6||||||");
                try {
                    pk.item = pis.get(slot);
                    pis.get(slot).getName();
                    System.out.println("In TRy!");
                } catch (Exception e) {
                    e.printStackTrace();
                    pk.item = Item.get(Item.AIR);
                }

                System.out.println("7|||||||");
                pk.x = pos.getFloorX() + 0.5f;
                pk.y = pos.getFloorY() + 0.5f;
                pk.z = pos.getFloorZ() + 0.5f;
                pk.speedX = (float) 0;
                pk.speedY = (float) 0;
                pk.speedZ = (float) 0;
                String i;
                String ll = "";
                i = pk.item.getName();
                //Text Above Item
//                l = String.join("\n",pk.item.getLore());
                System.out.println("8||||||||");
                boolean first = true;
                for (String s : pk.item.getLore()) {
                    if (first) {
                        first = false;
                        ll += "§r§c|==§bItem Lore§r§c==\n\n";
                    }
                    ll += "§r§c|==>" + TextFormat.GREEN + s + "\n\n";
                }
                String f1 = CenterText.sendCenteredMessage("§r§c|---->§b" + i + "§c<----|\n\n" + ll, 246);
                String f2 = CenterText.sendCenteredMessage(TextFormat.OBFUSCATED + "§b|||||||||§r" + TextFormat.RED + "ROLLING Item" + TextFormat.OBFUSCATED + "§b|||||||||§r", 246);
                CustomFloatingTextParticle ft = new CustomFloatingTextParticle(new Vector3(pos.getFloorX() + .5, pos.getFloorY() + 3, pos.getFloorZ() + 1.5), f1, f2);

                System.out.println("9|||||||||");
//        FloatingTextFactory.AddFloatingText(new CyberFloatingTextContainer(FTM, getServer().getLevelByName("world").getSafeSpawn().add(0, 5, 0), TextFormat.GREEN + "This is Spawn!"));
//                Long e1d = main.cratetxt.getLong(pn);
                if (eid2 == -1) eid2 = 1095216660480L + ThreadLocalRandom.current().nextLong(0L, 2147483647L);
                ft.entityId = eid2;
                DataPacket[] packets = ft.encode();

                System.out.println("10|||||||||");
                Level l = Server.getInstance().getLevelByName("world");
                System.out.println("11||||||||||");
                Player p = Server.getInstance().getPlayerExact(pn);
                System.out.println("12|||||||||||");
                if (p != null) {
                    l.addSound(pos, Sound.RANDOM_ORB);
                    p.batchDataPacket(pk);
                    for (DataPacket packet : packets) {
                        p.batchDataPacket(packet);
                    }

                }
                System.out.println("13||||||||||||");
                //l.addParticle(new RedstoneParticle(pos.add(.5,1,.5),2));
                //This one
                //l.addParticle(new DestroyBlockParticle(pos.add(.5,1,.5), Block.get(new NukkitRandom(tick).nextRange(0,100))));
                //Or this one
                //@TODO allow Config to choose break particle!
                l.addParticle(new DestroyBlockParticle(pos.add(.5, 1, .5), Block.get(Block.COBWEB)));

                System.out.println("14|||||||||||||");

                if (!ff) {
                    //Schedule Next
                    int k = getDelayFromTick(tick);
                    System.out.println("15A|||||||||||||" + k + "|" + tick);
                    if (tick >= MAX) {
                        ff = true;
                    }
                    tick += k;
                    System.out.println("16A|||||||||||||" + tick);
                    lasttick = tt + k;
                    continue;
                } else {
                    System.out.println("15B|||||||||||||");
                    //Last Tick for Roll... Send Item!
                    //l.addParticle(new HugeExplodeParticle(pos));
                    l.addParticle(new HugeExplodeSeedParticle(pos.add(.5, 1, .5)));
                    l.addParticle(new HugeExplodeSeedParticle(pos.add(.5, 1, 1.5)));
                    l.addParticle(new HugeExplodeSeedParticle(pos.add(1.5, 1, 1.5)));
                    l.addParticle(new HugeExplodeSeedParticle(pos.add(1.5, 1, .5)));
                    if (p != null) l.addParticle(new HugeExplodeSeedParticle(p));
                    System.out.println("16B|||||||||||||");
                    Server.getInstance().getScheduler().scheduleDelayedTask(new Explosion(pos.add(.5, 1, .5), l), 5);
                    Server.getInstance().getScheduler().scheduleDelayedTask(new Explosion(pos.add(.5, 1, .5), l), 10);
                    Server.getInstance().getScheduler().scheduleDelayedTask(new Explosion(pos.add(.5, 1, .5), l), 15);
                    Server.getInstance().getScheduler().scheduleDelayedTask(new Explosion(pos.add(.5, 1, .5), l), 20);
                    Server.getInstance().getScheduler().scheduleDelayedTask(new Explosion(pos.add(.5, 1, .5), l), 25);
                    Server.getInstance().getScheduler().scheduleDelayedTask(new Explosion(pos.add(.5, 1, .5), l), 30);
                    Server.getInstance().getScheduler().scheduleDelayedTask(new Explosion(pos.add(.5, 1, .5), l), 35);
                    Server.getInstance().getScheduler().scheduleDelayedTask(new Explosion(pos.add(.5, 1, .5), l), 40);
                    Server.getInstance().getScheduler().scheduleDelayedTask(new Explosion(pos.add(.5, 1, .5), l), 45);
                    Server.getInstance().getScheduler().scheduleDelayedTask(new Explosion(pos.add(.5, 1, .5), l), 50);
                    Server.getInstance().getScheduler().scheduleDelayedTask(new Explosion(pos.add(.5, 1, .5), l), 55);
                    Server.getInstance().getScheduler().scheduleDelayedTask(new Explosion(pos.add(.5, 1, .5), l), 60);
                    Server.getInstance().getScheduler().scheduleDelayedTask(new Explosion(pos.add(.5, 1, .5), l), 65);
                    Server.getInstance().getScheduler().scheduleDelayedTask(new Explosion(pos.add(.5, 1, .5), l), 70);
                    Server.getInstance().getScheduler().scheduleDelayedTask(new Explosion(pos.add(.5, 1, .5), l), 75);
                    Server.getInstance().getScheduler().scheduleDelayedTask(new Explosion(pos.add(.5, 1, .5), l), 80);
                    Server.getInstance().getScheduler().scheduleDelayedTask(new Explosion(pos.add(.5, 1, .5), l), 85);
                    Server.getInstance().getScheduler().scheduleDelayedTask(new Explosion(pos.add(.5, 1, .5), l), 90);
                    Server.getInstance().getScheduler().scheduleDelayedTask(new Explosion(pos.add(.5, 1, .5), l), 95);
                    Server.getInstance().getScheduler().scheduleDelayedTask(new Explosion(pos.add(.5, 1, .5), l), 100);
                    Server.getInstance().getScheduler().scheduleDelayedTask(new Explosion(pos.add(.5, 1, 1.5), l), 100);
                    Server.getInstance().getScheduler().scheduleDelayedTask(new Explosion(pos.add(1.5, 1, 1.5), l), 100);
                    Server.getInstance().getScheduler().scheduleDelayedTask(new Explosion(pos.add(1.5, 1, .5), l), 100);
                    System.out.println("17B|||||||||||||");
//                    Server.getInstance().getScheduler().scheduleDelayedTask(new SendItem(main, data), 20 * 5);
                    //Send items
                    //i
                    if (p == null) {
                        Server.getInstance().getLogger().error("Error Could not find player to give crate reward to!!!");
                    } else {
                        p.getInventory().addItem(pk.item);
                        p.sendMessage("Item added!!!!");
                    }
                    RemoveEntityPacket pkk = new RemoveEntityPacket();
                    pkk.eid = eid2;
                    p.batchDataPacket(pkk);

                    System.out.println("18B|||||||||||||");
                    CTstop();
                    return;
                }
            } else {
                try {
                    Thread.sleep(80);//4 Ticks*2//200
                } catch (InterruptedException e) {
                    //ignore
                }
            }
        }
    }

    public int getDelayFromTick(int t) {
        int f = 0;
        if (t < (MAX / 16)) {
            f = 2;
        } else if (t < (2 * MAX / 16)) {
            f = 3;
        } else if (t < (3 * MAX / 16)) {
            f = 3;
        } else if (t < (4 * MAX / 16)) {
            f = 4;
        } else if (t < (5 * MAX / 16)) {
            f = 4;
        } else if (t < (6 * MAX / 16)) {
            f = 5;
        } else if (t < (7 * MAX / 16)) {
            f = 6;
        } else if (t < (8 * MAX / 16)) {
            f = 7;
        } else if (t < (30 * MAX / 64)) {//9/16 > 18/32 > 36/64
            f = 9;
        } else if (t < (35 * MAX / 64)) {
            f = 12;
        } else if (t < (45 * MAX / 64)) {//12/16 >
            f = 15;
        } else if (t < (55 * MAX / 64)) {//13/16 > 56/
            f = 17;
        } else if (t < (60 * MAX / 64)) {
            f = 18;
        } else if (t < (62 * MAX / 64)) {//15/16 > 30/32 > 45/48 > 60/64
            f = 19;
        } else {
            f = 20;
        }
//        f += 3;
        return f;
    }


}
