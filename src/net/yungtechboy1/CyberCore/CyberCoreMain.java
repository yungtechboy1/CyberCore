package net.yungtechboy1.CyberCore;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.block.*;
import cn.nukkit.blockentity.BlockEntity;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandExecutor;
import cn.nukkit.command.CommandSender;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.passive.EntityPig;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.event.player.PlayerMoveEvent;
import cn.nukkit.item.Item;
import cn.nukkit.level.Level;
import cn.nukkit.math.Vector3;
import cn.nukkit.network.protocol.ProtocolInfo;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.ConfigSection;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.Bans.Ban;
import net.yungtechboy1.CyberCore.Classes.PowerSource.PowerSourceTaskAsync;
import net.yungtechboy1.CyberCore.Commands.*;
import net.yungtechboy1.CyberCore.Commands.Gamemode.GMC;
import net.yungtechboy1.CyberCore.Commands.Gamemode.GMS;
import net.yungtechboy1.CyberCore.Commands.Homes.DelHome;
import net.yungtechboy1.CyberCore.Commands.Homes.HomeManager;
import net.yungtechboy1.CyberCore.Commands.Homes.SetHome;
import net.yungtechboy1.CyberCore.Custom.Block.BlockEnchantingTable;
import net.yungtechboy1.CyberCore.Custom.Block.CustomBlockTNT;
import net.yungtechboy1.CyberCore.Custom.Block.SpawnerWithLevelBlock;
import net.yungtechboy1.CyberCore.Custom.BlockEntity.SpawnerWithLevelBlockEntity;
import net.yungtechboy1.CyberCore.Custom.Crafting.CustomRecipeCraftingManager;
import net.yungtechboy1.CyberCore.Custom.CustomInventoryTransactionPacket;
import net.yungtechboy1.CyberCore.Custom.Item.*;
import net.yungtechboy1.CyberCore.Data.ServerSqlite;
import net.yungtechboy1.CyberCore.Data.UserSQL;
import net.yungtechboy1.CyberCore.Factory.AuctionHouse.AuctionFactory;
import net.yungtechboy1.CyberCore.Factory.ClassFactory;
import net.yungtechboy1.CyberCore.Factory.Shop.ShopFactory;
import net.yungtechboy1.CyberCore.Factory.Shop.Spawner.SpawnShopFactory;
import net.yungtechboy1.CyberCore.Manager.BossBar.BossBarManager;
import net.yungtechboy1.CyberCore.Manager.BossBar.BossBarNotification;
import net.yungtechboy1.CyberCore.Manager.CustomCraftingManager;
import net.yungtechboy1.CyberCore.Manager.FT.FloatingTextContainer;
import net.yungtechboy1.CyberCore.Manager.FT.FloatingTextFactory;
import net.yungtechboy1.CyberCore.Manager.FT.PopupFT;
import net.yungtechboy1.CyberCore.Manager.Factions.Data.FactionSQL;
import net.yungtechboy1.CyberCore.Manager.Factions.Faction;
import net.yungtechboy1.CyberCore.Manager.Factions.FactionsMain;
import net.yungtechboy1.CyberCore.Manager.Purge.PurgeManager;
import net.yungtechboy1.CyberCore.Manager.SQLManager;
import net.yungtechboy1.CyberCore.Manager.Warp.WarpManager;
import net.yungtechboy1.CyberCore.MobAI.AutoSpawnTask;
import net.yungtechboy1.CyberCore.MobAI.MobPlugin;
import net.yungtechboy1.CyberCore.Rank.ChatFormats;
import net.yungtechboy1.CyberCore.Rank.Rank;
import net.yungtechboy1.CyberCore.Rank.RankFactory;
import net.yungtechboy1.CyberCore.Tasks.SendHUD;
import net.yungtechboy1.CyberCore.entities.animal.walking.Pig;

import java.io.File;
import java.lang.reflect.Constructor;
import java.sql.Connection;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static cn.nukkit.item.Item.addCreativeItem;

/**
 * Created by carlt_000 on 3/21/2016.
 */
public class CyberCoreMain extends PluginBase implements CommandExecutor, Listener {

    public static final String NAME = TextFormat.GOLD + "" + TextFormat.BOLD + "§eTERRA§6CORE " + TextFormat.RESET + TextFormat.GOLD + "» " + TextFormat.RESET;
    //CyberChat
    public static Connection Connect = null;
    public static Connection Connect2 = null;
    public static String Prefix = TextFormat.AQUA + "[TerraTP]";
    private static CyberCoreMain instance;
    public SendHUD SH;
    public BossBarManager BBM;
    public Config job;
    public Config Homes;
    public Config ban;
    public Config tban;
    public Config tcban;
    public Config tipban;
    public Config cooldowns;
    //HUD Off
    //TODO add PlayerSetting Object to allow players to save
    //TODO add /Settings commands that adds GUI
    public Config RankChatColor;
    public Config RankConfig;
    public Config MainConfig;
    public Config RankListConfig;
    public FactionsMain FM;
    public Boolean nf = true;
    public Config MuteConfig;
    public List<String> PlayerMuted = new ArrayList<>();
    public Boolean MuteChat = false;
    public Map<String, Integer> Spam = new HashMap<>();
    public Map<String, String> LM = new HashMap<>();
    //Floating Text
    public FloatingTextFactory FTM;
    //Mob Plugin and AI
    public MobPlugin MP;
    //KDR
    //Classes / MMO
    public net.yungtechboy1.CyberCore.Factory.ClassFactory ClassFactory = null;
    //PasswordFactoy
    public net.yungtechboy1.CyberCore.Factory.PasswordFactoy PasswordFactoy;
    //CustomFactory
    public net.yungtechboy1.CyberCore.Factory.CustomFactory CustomFactory;
    //FactoriesA
    public HomeManager HomeFactory;
    public net.yungtechboy1.CyberCore.Rank.RankFactory RF;
    public net.yungtechboy1.CyberCore.Factory.AuctionHouse.AuctionFactory AF;
    public ShopFactory Shop;
    public SpawnShopFactory SpawnShop;
    public net.yungtechboy1.CyberCore.Manager.Purge.PurgeManager PM;
    public List<String> Final = new ArrayList<>();
    public List<String> TPING = new ArrayList<>();
    public HashMap<String, HashMap<String, Object>> cache = new HashMap<>();
    public HashMap<String, String> LastMsg = new HashMap<>();
    //public CyberTech.CyberChat.Main CC;
    public Map<String, String> tpr = new HashMap<>();
    public ArrayList<Ban> bans = new ArrayList<>();
    public SQLManager SQLSaveManager;
    /**
     * DATA: UUID, GAMERTAG, CREATED(TIMESTAMP), LAST_LOGIN(TIMESTAMP), RANK(INT), LAST_IP
     */
//    public CoreSQL CoreSQL = null;
    /**
     * DATA: ECON, K/D,
     */
    public net.yungtechboy1.CyberCore.Data.UserSQL UserSQL;
    public net.yungtechboy1.CyberCore.Manager.Warp.WarpManager WarpManager;
    public ServerSqlite ServerSQL;
    public CustomCraftingManager CraftingManager;
    Vector3 p1;
    Vector3 p2;
    CustomRecipeCraftingManager CRM;
//    private EconManager ECON;
    private PowerSourceTaskAsync PowerSourceTask;

    public static CyberCoreMain getInstance() {
        return CyberCoreMain.instance;
    }

    public PowerSourceTaskAsync getPowerSourceTask() {
        return PowerSourceTask;
    }

    public void setPowerSourceTask(PowerSourceTaskAsync powerSourceTask) {
        PowerSourceTask = powerSourceTask;
    }

    public void HandleCustomAttack() {

    }

    public void ReloadBlockList(int id, Class c) {
        if (c != null) {
            Block block;
            try {
                block = (Block) c.newInstance();
                try {
                    Constructor constructor = c.getDeclaredConstructor(int.class);
                    constructor.setAccessible(true);
                    for (int data = 0; data < 16; ++data) {
                        Block.fullList[(id << 4) | data] = (Block) constructor.newInstance(data);
                    }
                    Block.hasMeta[id] = true;
                } catch (NoSuchMethodException ignore) {
                    for (int data = 0; data < 16; ++data) {
                        Block.fullList[(id << 4) | data] = block;
                    }
                }
            } catch (Exception e) {
                Server.getInstance().getLogger().error("Error while registering " + c.getName(), e);
                for (int data = 0; data < 16; ++data) {
                    Block.fullList[(id << 4) | data] = new BlockUnknown(id, data);
                }
                return;
            }

            Block.solid[id] = block.isSolid();
            Block.transparent[id] = block.isTransparent();
            Block.hardness[id] = block.getHardness();
            Block.light[id] = block.getLightLevel();

            if (block.isSolid()) {
                if (block.isTransparent()) {
                    if (block instanceof BlockLiquid || block instanceof BlockIce) {
                        Block.lightFilter[id] = 2;
                    } else {
                        Block.lightFilter[id] = 1;
                    }
                } else {
                    Block.lightFilter[id] = 15;
                }
            } else {
                Block.lightFilter[id] = 1;
            }
        }
    }

    @Override
    public void onEnable() {
        if(!getDataFolder().exists()) {
            getDataFolder().mkdirs();
            saveResource("config.yml");
            saveResource("ranks.yml");
        }
        MainConfig = getConfig();
//        CustomGlobalBlockPalette.registerMapping((entry.id << 4) | entry.data);


//        CustomGlobalBlockPalette.getOrCreateRuntimeId(0,0);
//        getServer().getNetwork().registerPacket(ProtocolInfo.START_GAME_PACKET, CustomStartGamePacket.class);

        Block.list[Block.ENCHANTING_TABLE] = BlockEnchantingTable.class;
        Block.list[Block.TNT] = CustomBlockTNT.class;
//        Block.list[267] = CustomElemebntBlock.class;
//        Block.list[Block.PURPLE_GLAZED_TERRACOTTA] = CustomBlockPurpleGlazedTerraCotta.class;
//        ReloadBlockList(267, CustomElementBlock.class);
        ReloadBlockList(Block.TNT, CustomBlockTNT.class);
//        ReloadBlockList(Block.PURPLE_GLAZED_TERRACOTTA, CustomBlockPurpleGlazedTerraCotta.class);
//        Item.list[Block.ENCHANTING_TABLE] = BlockEnchantingTable.class;
        addCreativeItem(Item.get(Block.ENCHANT_TABLE, 5, 1).setCustomName("TTTTTTTTTTTTTT"));
        addCreativeItem(new CustomItemElement());
        ReloadBlockList(Block.ENCHANTING_TABLE, BlockEnchantingTable.class);

//        Item.customblocklist[Item.MONSTER_SPAWNER] = CustomItemBlockSpawnerWithLevelBlock.class;
        Block.list[Block.MONSTER_SPAWNER] = SpawnerWithLevelBlock.class;
        BlockEntity.registerBlockEntity(BlockEntity.MOB_SPAWNER,SpawnerWithLevelBlockEntity.class);
        //Must be registered after custom block
//        Item.registerCustomItemBlock(Item.MONSTER_SPAWNER,CustomItemBlockSpawnerWithLevelBlock.class);

        ReloadBlockList(Block.MONSTER_SPAWNER, SpawnerWithLevelBlock.class);
        Item.list[Item.BOOK] = CItemBook.class;
        Item.list[Item.ENCHANT_BOOK] = CItemBookEnchanted.class;
        Item.list[BlockID.TNT] = CustomItemTNT.class;
        Item.list[Item.GUNPOWDER] = CustomItemGunpowder.class;
        Item.list[Item.PURPLE_GLAZED_TERRACOTTA] = CustomItemPurpleGlazedTerraCotta.class;
        Item.list[Item.STRING] = CustomItemString.class;
//        Item.list[Item.Element_1] = CustomItemElement.class;
//        Item.init();

//        System.out.println(">>>>>>>>>>0x" + Binary.bytesToHexString(new CustomItemGunpowder(CustomItemGunpowder.GunpowderType.Lvl_1).getCompoundTag()));

        PowerSourceTask = new PowerSourceTaskAsync(this);
        CraftingManager = new CustomCraftingManager();
        CRM = new CustomRecipeCraftingManager(this);

//        CustomItemTNT
//        ShapedRecipe nsr = new ShapedRecipe(Item.get(46), new String[]{"AAA", "BBB", "AAA"}, new CharObjectHashMap<Item>() {{
//            put("A".charAt(0), new CustomItemGunpowder(CustomItemGunpowder.GunpowderType.Lvl_2));
//            put("B".charAt(0), Item.get(12));
//        }}, new ArrayList<Item>() {{
//            add(new CustomItemTNT());
//        }});


//        CraftingManager.registerShapedRecipe(nsr);

        System.out.println("EEEE >>>>> " + CraftingManager.shapedRecipes.size());


//        getServer().getCraftingManager().registerShapelessRecipe();=null;

//        getServer().getCraftingManager().get()
//
//        getServer().getCraftingManager().registerShapelessRecipe();

        ClassFactory = new ClassFactory(this);
        WarpManager = new WarpManager(this);
        //Save = new SaveMain(this);
        SQLSaveManager = new SQLManager(this);

//        CoreSQL = new CoreSQL(this,"Core");
        ServerSQL = new ServerSqlite(this);
        ServerSQL.LoadAllWarps();
        UserSQL = new UserSQL(this);


        PM = new PurgeManager(this);
        //KDR Manager - All Good
        //GOOD
        //Floating Text
        //Threaded ONLY RUN FOR TESTING
        //TESTING
        FTM = new FloatingTextFactory(this);
        FloatingTextFactory.AddFloatingText(new FloatingTextContainer(FTM, getServer().getLevelByName("world").getSafeSpawn().add(0, 5, 0), TextFormat.GREEN + "This is Spawn!"));


        //Mob Plugin
        //GOOD - Should all be ready! Just add modifications for custom Entity drops and etc
        MP = new MobPlugin(this);

//        GOOD
//        ECON = new EconManager(this);
        FM = new FactionsMain(this, new FactionSQL(this));
//        getServer().getScheduler().scheduleRepeatingTask(new UnMuteTask(this), 20 * 15);
//        getServer().getScheduler().scheduleRepeatingTask(new ClearSpamTick(this), 20 * 5);
//        getServer().getScheduler().scheduleRepeatingTask(new CheckOP(this), 20 * 60);//1 Min

        Homes = new Config(new File(this.getDataFolder(), "homes.yml"), Config.YAML, new ConfigSection());

        HomeFactory = new HomeManager(this);
        RF = new RankFactory(this);
        Shop = new ShopFactory(this);
        SpawnShop = new SpawnShopFactory(this);
        //TODO
        AF = new AuctionFactory(this);

        MuteConfig = new Config(new File(getDataFolder(), "Mute.yml"), Config.YAML);

//        ban = new Config(new File(this.getDataFolder(), "ban.yml"), Config.YAML);
//        if (ban.getAll().size() > 0) {
//            for (Object c : ban.getAll().values().toArray()) {
//                if(c instanceof ConfigSection)bans.add(new Ban((ConfigSection)c));
//            }
//        }
        tban = new Config(new File(this.getDataFolder(), "tban.yml"), Config.YAML);
        tcban = new Config(new File(this.getDataFolder(), "tcban.yml"), Config.YAML);
        tipban = new Config(new File(this.getDataFolder(), "tipban.yml"), Config.YAML);
        job = new Config(new File(this.getDataFolder(), "job.yml"), Config.YAML);
        cooldowns = new Config(new File(this.getDataFolder(), "cooldowns.yml"), Config.YAML);
        getLogger().info(TextFormat.GREEN + "Initializing Cyber Essentials");

        BBM = new BossBarManager(this);

//        PasswordFactoy = new PasswordFactoy(this);
//
//
//        CustomFactory = new CustomFactory(this);

        getServer().getPluginManager().registerEvents(new MasterListener(this), this);
        getServer().getPluginManager().registerEvents(ClassFactory, this);
        getServer().getPluginManager().registerEvents(AF, this);
        getServer().getPluginManager().registerEvents(Shop, this);
        getServer().getPluginManager().registerEvents(SpawnShop, this);
        getServer().getPluginManager().registerEvents(this, this);
//        getServer().getPluginManager().registerEvents(new FactionListener(this, FM), this);

//        getServer().getScheduler().scheduleDelayedTask(new Restart(this), 20 * 60 * 60 * 2);//EVERY 2 Hours
//        getServer().getScheduler().scheduleRepeatingTask(new SendHUD(this), 50);//EVERY Sec
        SH = new SendHUD();

        //COMMANDS
        getServer().getCommandMap().register("net/yungtechboy1/CyberCore", new ChooseClass(this));
        getServer().getCommandMap().register("net/yungtechboy1/CyberCore", new BanCmd(this));
        getServer().getCommandMap().register("net/yungtechboy1/CyberCore", new Ci(this));
        getServer().getCommandMap().register("net/yungtechboy1/CyberCore", new Fix(this));
        getServer().getCommandMap().register("net/yungtechboy1/CyberCore", new IPBan(this));
        getServer().getCommandMap().register("net/yungtechboy1/CyberCore", new Msg(this));
        getServer().getCommandMap().register("net/yungtechboy1/CyberCore", new Reply(this));
        getServer().getCommandMap().register("net/yungtechboy1/CyberCore", new Spawn(this));
        getServer().getCommandMap().register("net/yungtechboy1/CyberCore", new TNT(this));
        getServer().getCommandMap().register("net/yungtechboy1/CyberCore", new Shop(this));
        //All Commands Up to this point are Updated
        ///TODO FIX REST OF COMMANDS!
        //getServer().getCommandMap().register("CyberCore", new Tban(this));
//        getServer().getCommandMap().register("net/yungtechboy1/CyberCore", new Top(this));
//        getServer().getCommandMap().register("net/yungtechboy1/CyberCore", new Vote(this));
        getServer().getCommandMap().register("net/yungtechboy1/CyberCore/Commands", new Wild(this));
//
        getServer().getCommandMap().register("net/yungtechboy1/CyberCore/Commands/Home", new net.yungtechboy1.CyberCore.Commands.Homes.Home(this));
        getServer().getCommandMap().register("net/yungtechboy1/CyberCore/Commands/Home", new SetHome(this));
        getServer().getCommandMap().register("net/yungtechboy1/CyberCore/Commands/Home", new DelHome(this));
//
//        getServer().getCommandMap().register("net/yungtechboy1/CyberCore", new AA(this));
//        getServer().getCommandMap().register("net/yungtechboy1/CyberCore", new ClassCmd(this));
//        getServer().getCommandMap().register("net/yungtechboy1/CyberCore", new SetClass(this));
//
        getServer().getCommandMap().register("net/yungtechboy1/CyberCore/Commands", new Hud(this));
//
//        getServer().getCommandMap().register("net/yungtechboy1/CyberCore", new _FT(this));
//        getServer().getCommandMap().register("net/yungtechboy1/CyberCore", new FTS(this));
//        getServer().getCommandMap().register("net/yungtechboy1/CyberCore", new FTR(this));
//
        getServer().getCommandMap().register("net/yungtechboy1/CyberCore/Commands", new TPR(this));
//        getServer().getCommandMap().register("net/yungtechboy1/CyberCore", new TPD(this));
//        getServer().getCommandMap().register("net/yungtechboy1/CyberCore", new TPA(this));
//
        getServer().getCommandMap().register("net/yungtechboy1/CyberCore/Commands", new Warp(this));
        getServer().getCommandMap().register("net/yungtechboy1/CyberCore/Commands", new SetWarp(this));
//
//        getServer().getCommandMap().register("net/yungtechboy1/CyberCore", new ClassCmd(this));
//        getServer().getCommandMap().register("net/yungtechboy1/CyberCore", new AClassCmd(this));
//
//        getServer().getCommandMap().register("net/yungtechboy1/CyberCore", new Sync(this));
//
//        getServer().getCommandMap().register("net/yungtechboy1/CyberCore", new Email(this));
//        getServer().getCommandMap().register("net/yungtechboy1/CyberCore", new Login(this));
//        getServer().getCommandMap().register("net/yungtechboy1/CyberCore", new Register(this));
//
//        getServer().getCommandMap().register("net/yungtechboy1/CyberCore", new ChatEnchant(this));
//
        getServer().getCommandMap().register("net/yungtechboy1/CyberCore/Commands", new AuctionHouseCmd(this));
        getServer().getCommandMap().register("net/yungtechboy1/CyberCore/Commands", new SellHand(this));
    }

    public void onLoad() {

        getServer().getNetwork().registerPacket(ProtocolInfo.INVENTORY_TRANSACTION_PACKET, CustomInventoryTransactionPacket.class);

        Entity.registerEntity(EntityPig.NETWORK_ID + "", Pig.class);

//        BlockEntity.registerBlockEntity("MonsterSpawner", SpawnerWithLevelBlockEntity.class);

        CyberCoreMain.instance = this;

//        MobPlugin.registerEntities();
//        MobPlugin.registerItems();
        //getServer().getScheduler().scheduleRepeatingTask(new AutoSpawnTask(this), 5, true);
    }


    public void log(String string) {
        getLogger().info(colorize(string));
    }

    public String colorize(String str) {
        return str.replace('&', '§');
    }

    public Map<TimeUnit, Long> computeDiff(Date date1, Date date2) {
        long diffInMillies = date2.getTime() - date1.getTime();
        List<TimeUnit> units = new ArrayList<>(EnumSet.allOf(TimeUnit.class));
        Collections.reverse(units);

        Map<TimeUnit, Long> result = new LinkedHashMap<>();
        long milliesRest = diffInMillies;
        for (TimeUnit unit : units) {
            long diff = unit.convert(milliesRest, TimeUnit.MILLISECONDS);
            long diffInMilliesForUnit = unit.toMillis(diff);
            milliesRest = milliesRest - diffInMilliesForUnit;
            result.put(unit, diff);
        }
        return result;
    }

    public ArrayList<Player> getAllPlayerNamesCloseTo(String name) {
        ArrayList<Player> found = new ArrayList<>();
        name = name.toLowerCase();
        int delta = 2147483647;
        Iterator var4 = getServer().getOnlinePlayers().values().iterator();

        while (var4.hasNext()) {
            Player player = (Player) var4.next();
            if (player.getName().toLowerCase().startsWith(name) || player.getName().toLowerCase().contains(name)) {
                int curDelta = player.getName().length() - name.length();
                if (curDelta < delta) {
                    found.add(player);
                    delta = curDelta;
                }

                if (curDelta == 0) {
                    found.clear();
                    found.add(player);
                    return found;
                }
            }
        }

        return found;
    }

    @Override
    public void onDisable() {
//        ConfigSection bc = new ConfigSection();
//        for (Ban b : bans) {
//            bc.put(b.name, b.toconfig());
//        }
//        ban.setAll(bc);
//        ban.save();
//        tipban.save();
//        tban.save();
//        tcban.save();
//        cooldowns.save();
//
//        Homes.save();
//
//        //CyberChat
//        MainConfig.save();
//        MuteConfig.save();
//        RankListConfig.save();

//        PasswordFactoy.onDisable();

        //Classes
        PowerSourceTask.CTstop();
        ClassFactory.Saveall();
        FM.FFactory.SaveAllFactions();
    }


    public void setMute(Player target, Integer time) {
        Integer finaltime = (int) (Calendar.getInstance().getTime().getTime() / 1000) + (time);
        MuteConfig.set(target.getName().toLowerCase(), finaltime);
        target.sendMessage(TextFormat.YELLOW + "You are now muted for " + time + " Secs");
    }


    public void removeMute(Player target) {
        MuteConfig.remove(target.getName().toLowerCase().toLowerCase());
    }

    public boolean isMuted(Player player) {
        return isMuted(player.getName());
    }

    public boolean isMuted(String player) {
        player = player.toLowerCase();
        if (MuteConfig.exists(player)) {
            if ((Calendar.getInstance().getTime().getTime() / 1000) > (int) MuteConfig.get(player)) {
                MuteConfig.remove(player);
                return false;
            }
            return true;
        }
        return false;
    }

    public CorePlayer getCorePlayer(String p) {
        return getCorePlayer(getPlayer(p));
    }

    public CorePlayer getCorePlayer(Player p) {
        if (p instanceof CorePlayer) {
            return (CorePlayer) p;
        }
        return (CorePlayer) getPlayer(p.getName());
    }

    public Integer GetIntTime() {
        return (int) (Calendar.getInstance().getTime().getTime() / 1000);
    }

    public Player getPlayer(String p) {
        Player player;
        if ((player = getServer().getPlayer(p)) != null) {
            return player;
        } else {
            if (getServer().getOnlinePlayers().containsKey(UUID.fromString(p))) {
                return getServer().getOnlinePlayers().get(UUID.fromString(p));
            }
            return null;
        }
    }

    /**
     * Returns Player Faction
     *
     * @param p
     * @return String
     */
    public Faction getPlayerFaction(Player p) {
        if (FM != null) return FM.FFactory.getPlayerFaction(p);
        return null;
    }

    public Rank getPlayerRank(String p) {
        return getPlayerRank(getPlayer(p));
    }


    public Rank getPlayerRank(Player p) {
        return RF.getPlayerRank(p);
    }

    public boolean checkSpam(Player p) {
        if (Spam.containsKey(p.getName().toLowerCase())) {
            int count = Spam.get(p.getName().toLowerCase());
            count++;
            Spam.put(p.getName().toLowerCase(), count);
            if (count >= 4) {
                p.sendMessage(TextFormat.YELLOW + "Slow Down Typing!");
                setMute(p, 10);
                return false;
            }
        } else {
            Spam.put(p.getName().toLowerCase(), 1);
        }
        return true;
    }

    public void Setnametag(Player p) {

        ChatFormats.RankChatFormat RankFormat;
        Rank r = RF.getPlayerRank(p);
        String a = r.getDisplayName();
        RankFormat = r.getChat_format();
        String pn = p.getName();

        String f1 = "";//Factioin
        String f2 = "";//Rank

        if (FM != null) {
            Faction f = FM.FFactory.getPlayerFaction(p);
            if (f != null) {
                f1 = TextFormat.GRAY + "[" + f.GetFactionNameTag(p) + TextFormat.GRAY + "]\n";
            } else {
                f1 = TextFormat.GRAY + "[NF]";
            }
        } else {
            f1 = TextFormat.GRAY + "[NF]";
        }
        p.setNameTag(ChatFormats.RankDisplayNameFormat.Default.format(f1, a, p));
    }

    public Integer GetPlayerRankInt(Player p) {
        return GetPlayerRankInt(p, false);
    }

    public Integer GetPlayerRankInt(Player p, Boolean all) {
        return RF.getPlayerRank(p).getId();
    }

//    public Integer GetPlayerRankInt(String p, Boolean all) {
//        return RF.getPlayerRank(p).getRank();
//    }

//    @EventHandler(ignoreCancelled = true)
//    public void PlayerLoginEvent(PlayerLoginEvent event) {
//        Player p = event.getPlayer();
//        p.getName();
//        for (Ban b : bans) {
//            if (b.checkbanned(p, event)){
//                event.setCancelled();
//                return;
//            }
//        }
//
//    }

    public String getDifferenceBtwTime(Object dateTime) {
        return getDifferenceBtwTime(Long.parseLong(dateTime + ""));
    }

    public String getDifferenceBtwTime(Long dateTime) {

        long timeDifferenceMilliseconds = dateTime - new Date().getTime();
        long diffSeconds = timeDifferenceMilliseconds / 1000;
        long diffMinutes = timeDifferenceMilliseconds / (60 * 1000);
        long diffHours = timeDifferenceMilliseconds / (60 * 60 * 1000);
        long diffDays = timeDifferenceMilliseconds / (60 * 60 * 1000 * 24);
        long diffWeeks = timeDifferenceMilliseconds / (60 * 60 * 1000 * 24 * 7);
        long diffMonths = (long) (timeDifferenceMilliseconds / (60 * 60 * 1000 * 24 * 30.41666666));
        long diffYears = timeDifferenceMilliseconds / (1000 * 60 * 60 * 24 * 365);

        if (diffSeconds < 1) {
            return "one sec";
        } else if (diffMinutes < 1) {
            return diffSeconds + " seconds";
        } else if (diffHours < 1) {
            return diffMinutes + " minutes";
        } else if (diffDays < 1) {
            return diffHours + " hours";
        } else if (diffWeeks < 1) {
            return diffDays + " days";
        } else if (diffMonths < 1) {
            return diffWeeks + " weeks";
        } else if (diffYears < 12) {
            return diffMonths + " months";
        } else {
            return diffYears + " years";
        }
    }

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        String cmdd = cmd.getName().toLowerCase();
//        if (FM != null && FM.FC != null) {
//            if (FM.FC.onCommand(FM, s, cmd, label, args)) {
//                return true;
//            }
//        }
//        factions.FC.onCommand(this,pcppe.getPlayer(),pcppe.getMessage(),label,args);
        switch (cmdd) {
            case "wild":
                Wild.runCommand(s, this);
                return true;
            case "cl":
                if (s.isOp()) {
                    for (Map.Entry<Integer, Level> level : getServer().getLevels().entrySet()) {
                        for (Entity e : level.getValue().getEntities()) {
                            if (e instanceof Player) continue;
                            e.kill();
                        }
                    }
                    return true;
                }
                return false;
            case "gms":
                if (s.isOp()) {
                    GMS.runCommand(s, args, this);
                    return true;
                }
                return false;
            case "gmc":
                if (s.isOp()) {
                    GMC.runCommand(s, args, this);
                    return true;
                }
                return false;
            case "chat":
                if (args.length == 1) {
                    String pnl = s.getName().toLowerCase();
                    if (args[0].equalsIgnoreCase("on")) {
                        if (PlayerMuted.contains(pnl)) {
                            s.sendMessage(TextFormat.YELLOW + "Chat Already Muted!");
                        } else {
                            PlayerMuted.add(pnl);
                            s.sendMessage(TextFormat.GREEN + "Chat Is Now Muted!");
                        }
                        return true;
                    } else if (args[0].equalsIgnoreCase("off")) {
                        if (PlayerMuted.contains(pnl)) {
                            s.sendMessage(TextFormat.GREEN + "Chat Is Now Un-Muted!");
                            PlayerMuted.remove(pnl);
                        } else {
                            s.sendMessage(TextFormat.YELLOW + "Chat Is Not Muted!");
                        }
                        return true;
                    }
                }
                return false;
            case "rank":
                RF.RankCache.remove(s.getName());
                if (RF.GARC.containsKey(s.getName())) {
                    RF.GARC.remove(s.getName());
                }
                if (RF.MRC.containsKey(s.getName())) {
                    RF.MRC.remove(s.getName());
                }
                if (RF.SRC.containsKey(s.getName())) {
                    RF.SRC.remove(s.getName());
                }
                String a = "";
//                a = RF.getR;
//                if (a == null) a = RF.GetMasterRank(s.getName());
//                if (a == null) a = RF.GetSecondaryRank(s.getName());
//                if (a != null && a.equalsIgnoreCase("op")) s.setOp(true);
//                s.sendMessage(TextFormat.GREEN + "[TerraTide] Your Rank is: " + a);
                break;
            case "link":
                /*
                if (args.length == 2) {
                    String username = args[0];
                    String pin = args[1];
                    if (CheckUserPin(username, pin, s)) {
                        s.sendMessage(TextFormat.GREEN + "Accounts Linked!");
                    }
                } else {
                    s.sendMessage(TextFormat.RED + "Usage: /link <forum username> <Forum PIN>");
                }*/
                return true;
            case "chmute":
                if (MuteChat) {
                    getServer().broadcastMessage(TextFormat.YELLOW + "All Chat Un-Muted!");
                    MuteChat = false;
                } else {
                    getServer().broadcastMessage(TextFormat.YELLOW + "All Chat Muted!");
                    MuteChat = true;
                }
                return true;
            case "mute":
                if (args.length == 0) {
                    return false;
                } else if (args.length == 1) {
                    Integer time = 5 * 60;//5 Mins
                    Player Target = getServer().getPlayer(args[0]);
                    if (Target == null) {
                        s.sendMessage(TextFormat.RED + "[TerraTide] Error, Player " + args[0] + " not found!");
                        return true;
                    }
                    setMute(Target, time);
                    return true;
                } else if (args.length == 2) {
                    Integer time;
                    try {
                        time = Integer.parseInt(args[1]);
                    } catch (Exception e) {
                        s.sendMessage(TextFormat.RED + "[TerraTide] Usage /mute <Player> [seconds]");
                        return true;
                    }
                    Player Target = getServer().getPlayer(args[0]);
                    if (Target == null) {
                        s.sendMessage(TextFormat.RED + "[TerraTide] Error, Player " + args[0] + " not found!");
                        return true;
                    }
                    setMute(Target, time);
                    return true;
                }
                return false;
            case "unmute":
                if (args.length == 0) {
                    return false;
                } else if (args.length == 1) {
                    Player Target = getServer().getPlayer(args[0]);
                    if (Target == null) {
                        s.sendMessage(TextFormat.RED + "[TerraTide] Error, Player " + args[0] + " not found!");
                        return true;
                    }
                    removeMute(Target);
                    return true;
                }
        }
        return false;
    }


//    public void initiatePlayer(Player p) {
//        try {
//            CoreSQL.loadUser((CorePlayer) p);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }


    @EventHandler(priority = EventPriority.HIGHEST)
    public void PMJ(PlayerJoinEvent me) {
        BossBarManager.AddBossBar(me.getPlayer(), new BossBarNotification(me.getPlayer(), "TEST TITLE", "TEST MESSAGE", 20 * 60, this));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void PM(PlayerMoveEvent me) {

        //TODO Implement \/
//        cp.LastSentFormType = CorePlayer.FormType.Class_0;
        if (me.getFrom().distance(me.getTo()) > .1)
            FloatingTextFactory.AddFloatingText(new PopupFT(FTM, me.getPlayer().add(0, 1.5, 0), TextFormat.AQUA + me.getPlayer().getName() + " was Here!"));
    }

    public ArrayList<Faction> getAllFactionNamesCloseTo(String arg) {
        ArrayList<Faction> found = new ArrayList<>();
        arg = arg.toLowerCase();
        int delta = 2147483647;
        Iterator var4 = FM.FFactory.List.values().iterator();

        while (var4.hasNext()) {
            Faction player = (Faction) var4.next();
            if (player.GetName().toLowerCase().startsWith(arg) || player.GetName().toLowerCase().contains(arg)) {
                int curDelta = player.GetName().length() - arg.length();
                if (curDelta < delta) {
                    found.add(player);
                    delta = curDelta;
                }

                if (curDelta == 0) {
                    found.clear();
                    found.add(player);
                    return found;
                }
            }
        }

        return found;
    }
}
