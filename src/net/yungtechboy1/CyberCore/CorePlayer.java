package net.yungtechboy1.CyberCore;

import cn.nukkit.Player;
import cn.nukkit.PlayerFood;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockAir;
import cn.nukkit.block.BlockDragonEgg;
import cn.nukkit.block.BlockNoteblock;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.event.server.DataPacketReceiveEvent;
import cn.nukkit.form.window.FormWindow;
import cn.nukkit.inventory.Inventory;
import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.level.Location;
import cn.nukkit.level.Position;
import cn.nukkit.level.Sound;
import cn.nukkit.math.*;
import cn.nukkit.network.SourceInterface;
import cn.nukkit.network.protocol.*;
import cn.nukkit.potion.Effect;
import cn.nukkit.utils.TextFormat;
import co.aikar.timings.Timing;
import co.aikar.timings.Timings;
import javafx.geometry.Pos;
import net.yungtechboy1.CyberCore.Custom.CustomEnchant.BurnShield;
import net.yungtechboy1.CyberCore.Custom.CustomEnchant.Climber;
import net.yungtechboy1.CyberCore.Custom.CustomEnchant.CustomEnchantment;
import net.yungtechboy1.CyberCore.Custom.CustomEnchant.Spring;
import net.yungtechboy1.CyberCore.Custom.Inventory.AuctionHouse;
import net.yungtechboy1.CyberCore.Data.HomeData;
import net.yungtechboy1.CyberCore.Manager.Econ.PlayerEconData;
import net.yungtechboy1.CyberCore.Manager.Factions.Faction;
import net.yungtechboy1.CyberCore.Rank.Rank;
import net.yungtechboy1.CyberCore.Rank.RankList;
import net.yungtechboy1.CyberCore.Tasks.TPToHome;
import net.yungtechboy1.CyberCore.Tasks.TeleportEvent;

import java.util.ArrayList;
import java.util.HashMap;

public class CorePlayer extends Player {


    public String TPR = null;
    public FormType.MainForm LastSentFormType = FormType.MainForm.NULL;
    public FormType.SubMenu LastSentSubMenu = FormType.SubMenu.MainMenu;
    public boolean MuteMessage = false;
    public String LastMessageSentTo = null;
    public String Faction = null;
    public FactionSettings fsettings = new FactionSettings();
    public CoreSettings settings = new CoreSettings();
    /**
     * @@deprecated
     */
    public Integer money = 0;
    public Integer kills = 0;
    public Integer deaths = 0;
    public Integer fixcoins = 0;
    public Integer banned = 0;
    public String faction_id = null;
    public HashMap<String, Object> extraData = new HashMap<>();
    public ArrayList<Enchantment> MasterEnchantigList = null;
    public ArrayList<HomeData> HD = new ArrayList<>();
    public int MaxHomes = 5;
    public int TPRTimeout;
    long uct = 0;
    boolean uw = false;
    private FormWindow nw;
    private Item ItemBeingEnchanted = null;
    private boolean ItemBeingEnchantedLock = false;
    private PlayerSettingsData SettingsData = null;
    private Rank rank = RankList.PERM_GUEST.getRank();
    private BlockVector3 lastBreakPosition1 = new BlockVector3();
    private Position CTLastPos = null;
    private int TeleportTick = 0;
    private boolean isTeleporting = false;
    private boolean isInTeleportingProcess = false;
    private CorePlayer TargetTeleporting = null;
    private Position TargetTeleportingLoc;
    public AuctionHouse AH = null;

    public CorePlayer(SourceInterface interfaz, Long clientID, String ip, int port) {
        super(interfaz, clientID, ip, port);
    }

    public boolean isInTeleportingProcess() {
        return isInTeleportingProcess;
    }

    public void setInTeleportingProcess(boolean inTeleportingProcess) {
        isInTeleportingProcess = inTeleportingProcess;
    }

    public boolean IsItemBeingEnchanted() {
        return getItemBeingEnchanted() != null;
    }

    public void clearItemBeingEnchanted() {
        setItemBeingEnchanted(null);
    }

    public Item getItemBeingEnchanted() {
        return ItemBeingEnchanted;
    }

    public void setItemBeingEnchanted(Item itemBeingEnchanted) {
        if (IsItemBeingEnchanted()) {

        }
        ItemBeingEnchanted = itemBeingEnchanted;
    }

    public boolean isItemBeingEnchantedLock() {
        return ItemBeingEnchantedLock;
    }

    public void setItemBeingEnchantedLock(boolean itemBeingEnchantedLock) {
        ItemBeingEnchantedLock = itemBeingEnchantedLock;
    }

    public void removeItemBeingEnchantedLock() {
        setItemBeingEnchantedLock(false);
    }

    public void setItemBeingEnchantedLock() {
        setItemBeingEnchantedLock(true);
    }

    public void ReturnItemBeingEnchanted() {
        if (IsItemBeingEnchanted() && !isItemBeingEnchantedLock()) {
            Item i = getItemBeingEnchanted();
            getInventory().addItem(i);
            clearItemBeingEnchanted();
            removeItemBeingEnchantedLock();
        }
    }

    public PlayerEconData GetData() {
        if (getSettingsData() == null) CreateDefaultSettingsData();
        if (getSettingsData() == null) return null;
        return getSettingsData();
    }

    public void CreateDefaultSettingsData() {
        PlayerSettingsData a = new PlayerSettingsData();
        a.Cash = 1000;
        a.CreditLimit = 1000;
        a.CreditScore = 350;//Out of 1000
        a.Name = getName();
        a.UsedCredit = 0;
        setSettingsData(a);
    }

    @Override
    public int addWindow(Inventory inventory, Integer forceId, boolean isPermanent) {
        if (this.windows.containsKey(inventory)) {
            return this.windows.get(inventory);
        }
        int cnt;
        if (forceId == null) {
            this.windowCnt = cnt = Math.max(4, ++this.windowCnt % 99);
        } else {
            cnt = forceId;
        }
        this.windows.put(inventory, cnt);

        if (isPermanent) {
            this.permanentWindows.add(cnt);
        }

        if (inventory.open(this)) {
            return cnt;
        } else {
            this.removeWindow(inventory);
            sendMessage("ERROR!!!!!!! I FUCKKKKED UUUUPPP");
            return -1;
        }
    }

    public boolean MakeTransaction(double price) {
        if (price > GetMoney()) return false;
        TakeMoney(price);
        return true;
    }

    public void TakeMoney(double price) {
        if (price <= 0) return;
        PlayerEconData ped = GetData();
        ped.Cash -= price;
    }

    public void AddMoney(double price) {
        if (price <= 0) return;
        PlayerEconData ped = GetData();
        ped.Cash += price;
    }

    public double GetMoney() {
        PlayerEconData ped = GetData();
        return ped.Cash;
    }

    public void SetRank(RankList r) {
        SetRank(r.getRank());
    }

    public void SetRank(Rank r) {
        rank = r;
    }

    public Rank GetRank() {
        return rank;
    }

    public Integer addDeath() {
        return deaths += 1;
    }

    public Integer addDeaths(Integer amount) {
        return deaths += amount;
    }

    public Integer addKill() {
        return kills += 1;
    }

    public Integer addKills(Integer amount) {
        return kills += amount;
    }

    public double calculateKD() {
        return kills / deaths;
    }

    public boolean hasNewWindow() {
        return nw != null;
    }

    public FormWindow getNewWindow() {
        return nw;
    }

    public void setNewWindow(FormWindow nw) {
        this.nw = nw;
    }

    public void clearNewWindow() {
        this.nw = null;
    }

    @Override
    public void fall(float fallDistance) {
        if (!uw) super.fall(fallDistance);
    }

    @Override
    public boolean attack(EntityDamageEvent source) {
        ArrayList<EntityDamageEvent.DamageCause> da = new ArrayList<>();
        da.add(EntityDamageEvent.DamageCause.FIRE);
        da.add(EntityDamageEvent.DamageCause.FIRE_TICK);
        da.add(EntityDamageEvent.DamageCause.LAVA);
        if (uw && source.getCause() == EntityDamageEvent.DamageCause.FALL) return false;
        if (da.contains(source.getCause())) {
            Player defender = (Player) source.getEntity();
            if (defender == null) return super.attack(source);//Defender not player
            Item cp = defender.getInventory().getChestplate();
            if (cp == null) return super.attack(source);//No Chestplate on
            EntityDamageEvent.DamageCause cause = source.getCause();
            //Check if defender has BurnShield
            BurnShield bs = (BurnShield) CustomEnchantment.getEnchantFromIDFromItem(cp, CustomEnchantment.BURNSHILED);
            if (bs == null) return super.attack(source);
            int bsl = bs.getLevel();
            switch (cause) {
                case FIRE_TICK:
                    if (bsl >= 1) return false;
                case FIRE:
                    if (bsl >= 2) return false;
                case LAVA:
                    if (bsl >= 3) return false;
            }
        }
        return super.attack(source);
    }

    public boolean CheckGround() {
        AxisAlignedBB bb = this.boundingBox.clone();
        bb.setMinY(bb.getMinY() - 0.75);

        this.onGround = this.level.getCollisionBlocks(bb).length > 0;
        this.isCollided = this.onGround;
        return onGround;
    }

    @Override
    public void handleDataPacket(DataPacket packet) {
        if (!connected) {
            return;
        }

        try (Timing timing = Timings.getReceiveDataPacketTiming(packet)) {
            DataPacketReceiveEvent ev = new DataPacketReceiveEvent(this, packet);
            this.server.getPluginManager().callEvent(ev);
            if (ev.isCancelled()) {
                return;
            }

            if (packet.pid() == ProtocolInfo.BATCH_PACKET) {
                this.server.getNetwork().processBatch((BatchPacket) packet, this);
                return;
            }

            packetswitch:
            switch (packet.pid()) {

                case ProtocolInfo.MOVE_PLAYER_PACKET:
                    super.handleDataPacket(packet);
                    if (this.teleportPosition != null) {
                        break;
                    }
                    Item boots = getInventory().getBoots();
                    if (boots == null) break;
                    Spring se = (Spring) CustomEnchantment.getEnchantFromIDFromItem(boots, (short) CustomEnchantment.SPRING);
                    Climber ce = (Climber) CustomEnchantment.getEnchantFromIDFromItem(boots, (short) CustomEnchantment.CLIMBER);
                    if (se == null && ce == null) break;
                    Vector3 nm = new Vector3();
                    if (se != null) {

                        MovePlayerPacket movePlayerPacket = (MovePlayerPacket) packet;
                        Vector3 newPos = new Vector3(movePlayerPacket.x, movePlayerPacket.y - this.getEyeHeight(), movePlayerPacket.z);


                        Vector3 dif = newPos.subtract(this);
                        CheckGround();
                        if (dif.getY() > 0 && !uw) {

                            //No Super Boost!
                            if (0 < dif.x && dif.x > DEFAULT_SPEED) dif.x = DEFAULT_SPEED;
                            if (0 > dif.x && dif.x < (-1 * DEFAULT_SPEED)) dif.x = (-1 * DEFAULT_SPEED);
                            if (0 > dif.z && dif.z > DEFAULT_SPEED) dif.z = DEFAULT_SPEED;
                            if (0 > dif.z && dif.z < (-1 * DEFAULT_SPEED)) dif.z = (-1 * DEFAULT_SPEED);

                            sendMessage(dif.getY() + "!");

                            nm.add(dif.x * .25, DEFAULT_SPEED * se.GetLevelEffect(), dif.z * .25);
                            resetFallDistance();
                            uct = lastUpdate + 20;
                            uw = true;
//                        upp++;
//                        if (upp > 3) uw = true;
                        } else if (onGround && uct > lastUpdate) {
//                        if (upp > 0) upp--;
                            uw = false;
                        }
                        if (uw) inAirTicks = 0;
                    } else if (ce != null) {

                        MovePlayerPacket movePlayerPacket = (MovePlayerPacket) packet;
                        Vector3 newPos = new Vector3(movePlayerPacket.x, movePlayerPacket.y - this.getEyeHeight(), movePlayerPacket.z);


                        Vector3 dif = newPos.subtract(this);
                        CheckGround();
                        if (dif.getY() > 0 && !uw) {

                            //No Super Boost!
                            if (0 < dif.x && dif.x > DEFAULT_SPEED) dif.x = DEFAULT_SPEED;
                            if (0 > dif.x && dif.x < (-1 * DEFAULT_SPEED)) dif.x = (-1 * DEFAULT_SPEED);
                            if (0 > dif.z && dif.z > DEFAULT_SPEED) dif.z = DEFAULT_SPEED;
                            if (0 > dif.z && dif.z < (-1 * DEFAULT_SPEED)) dif.z = (-1 * DEFAULT_SPEED);

                            sendMessage(dif.getY() + "!");

                            nm.add(dif.x, DEFAULT_SPEED * ce.GetLevelEffect(), dif.z);
                            resetFallDistance();
                            uct = lastUpdate + 20;
                            uw = true;
//                        upp++;
//                        if (upp > 3) uw = true;
                        } else if (onGround && uct > lastUpdate) {
//                        if (upp > 0) upp--;
                            uw = false;
                        }
                        if (uw) inAirTicks = 0;
                    }
//                    if (upp == 0) uw = false;
                    if (nm != null && !(nm.x != 0 && nm.y != 0 && nm.z != 0)) addMotion(nm.x, nm.y, nm.z);

                    break;
                case ProtocolInfo.PLAYER_ACTION_PACKET:
                    PlayerActionPacket playerActionPacket = (PlayerActionPacket) packet;
                    if (!this.spawned || (!this.isAlive() && playerActionPacket.action != PlayerActionPacket.ACTION_RESPAWN && playerActionPacket.action != PlayerActionPacket.ACTION_DIMENSION_CHANGE_REQUEST)) {
                        return;
                    }

                    playerActionPacket.entityId = this.id;
                    Vector3 pos = new Vector3(playerActionPacket.x, playerActionPacket.y, playerActionPacket.z);
                    BlockFace face = BlockFace.fromIndex(playerActionPacket.face);

                    actionswitch:
                    switch (playerActionPacket.action) {
                        case PlayerActionPacket.ACTION_START_BREAK:
                            long currentBreak = System.currentTimeMillis();
                            BlockVector3 currentBreakPosition = new BlockVector3(playerActionPacket.x, playerActionPacket.y, playerActionPacket.z);
                            // HACK: Client spams multiple left clicks so we need to skip them.
                            if ((lastBreakPosition1.equals(currentBreakPosition) && (currentBreak - this.lastBreak) < 10) || pos.distanceSquared(this) > 100) {
                                return;
                            }
                            Block target = this.level.getBlock(pos);
                            PlayerInteractEvent playerInteractEvent = new PlayerInteractEvent(this, this.inventory.getItemInHand(), target, face, target.getId() == 0 ? PlayerInteractEvent.Action.LEFT_CLICK_AIR : PlayerInteractEvent.Action.LEFT_CLICK_BLOCK);
                            this.getServer().getPluginManager().callEvent(playerInteractEvent);
                            if (playerInteractEvent.isCancelled()) {
                                this.inventory.sendHeldItem(this);
                                return;
                            }
                            switch (target.getId()) {
                                case Block.NOTEBLOCK:
                                    ((BlockNoteblock) target).emitSound();
                                    break actionswitch;
                                case Block.DRAGON_EGG:
                                    ((BlockDragonEgg) target).teleport();
                                    break actionswitch;
                            }
                            Block block = target.getSide(face);
                            if (block.getId() == Block.FIRE) {
                                //Chance of getting caught on fire!
                                RandomChanceOfFire(77);
                                this.level.setBlock(block, new BlockAir(), true);
                                return;
                            }
                            if (!this.isCreative()) {
                                //improved this to take stuff like swimming, ladders, enchanted tools into account, fix wrong tool break time calculations for bad tools (pmmp/PocketMine-MP#211)
                                //Done by lmlstarqaq
                                double breakTime = Math.ceil(target.getBreakTime(this.inventory.getItemInHand(), this) * 20);
                                if (breakTime > 0) {
                                    LevelEventPacket pk = new LevelEventPacket();
                                    pk.evid = LevelEventPacket.EVENT_BLOCK_START_BREAK;
                                    pk.x = (float) pos.x;
                                    pk.y = (float) pos.y;
                                    pk.z = (float) pos.z;
                                    pk.data = (int) (65535 / breakTime);
                                    this.getLevel().addChunkPacket(pos.getFloorX() >> 4, pos.getFloorZ() >> 4, pk);
                                }
                            }

                            this.breakingBlock = target;
                            this.lastBreak = currentBreak;
                            this.lastBreakPosition1 = currentBreakPosition;
                            break;
                        case PlayerActionPacket.ACTION_JUMP:
                            sendMessage("JUMMMPPPPP!!!" + getDirection());
//                            addMovement(0,2.5,0,0,0,0);
//                            switch (getDirection()) {
//                                case NORTH:
//                                    addMotion(motionX * 2, 3, 0);
//                                    break;
//                                case EAST:
//                                    addMotion(0, 3, motionZ * 2);
//                                    break;
//                                case WEST:
//                                    addMotion(motionX * -2, 3, 0);
//                                    break;
//                                case SOUTH:
//                                    addMotion(0, 3, motionZ * -2);
//                                    break;
//
//                            }
                            break;
                    }
            }
        }
        super.handleDataPacket(packet);
    }

    public void RandomChanceOfFire(int max) {
        NukkitRandom nr = new NukkitRandom(entityCount * max);
        int f = nr.nextRange(0, 100);
        if (f < max) setOnFire(nr.nextRange(1, 4));
    }

    @Override
    public boolean onUpdate(int currentTick) {
        //Check to see if Player as medic or Restoration
        PlayerFood pf = getFoodData();
        if (TPR != null && TPRTimeout != 0 && TPRTimeout < currentTick && !isInTeleportingProcess) {
            TPRTimeout = 0;
            CorePlayer cp  = CyberCoreMain.getInstance().getCorePlayer(TPR);
            if(cp != null)cp.sendPopup(TextFormat.YELLOW+"Teleport request expired");
            sendPopup(TextFormat.YELLOW+"Teleport request expired");
            TPR = null;
        }

        if (isInTeleportingProcess) {
            sendPopup(TeleportTick + "|" + isTeleporting);
            if (TeleportTick != 0 && isTeleporting) {
                if (CTLastPos == null) CTLastPos = getPosition();
                else {
                    sendMessage(CTLastPos.distance(getPosition()) + "");
                    if (CTLastPos.distance(getPosition()) > 3) {
                        isTeleporting = false;
                    }
//            CTLastPos = getPosition();
                }
                if (TeleportTick <= currentTick && isTeleporting) {
                    System.out.println("AAAAAA");
                    if (isTeleporting) {
                        removeAllEffects();
                        if ((TargetTeleporting != null && !TargetTeleporting.isAlive())) {
                            sendMessage("Error! Player Not found!!");
                            isInTeleportingProcess = false;
                            TeleportTick = 0;
                            CTLastPos = null;
                            return super.onUpdate(currentTick);
                        } else if (TargetTeleporting == null && TargetTeleportingLoc == null) {
                            sendMessage("Error! No Teleport data found!!!");
                            isInTeleportingProcess = false;
                            TeleportTick = 0;
                            CTLastPos = null;
                            return super.onUpdate(currentTick);
                        } else if (TargetTeleportingLoc != null) {
                            getLevel().addSound(getPosition(), Sound.MOB_ENDERMEN_PORTAL);
                            teleport(TargetTeleportingLoc);
                            TargetTeleportingLoc = null;
                            TargetTeleporting = null;
                        } else {
                            getLevel().addSound(getPosition(), Sound.MOB_ENDERMEN_PORTAL);
                            teleport(TargetTeleporting);
                            TargetTeleportingLoc = null;
                            TargetTeleporting = null;
                        }
                        isInTeleportingProcess = false;
                        TeleportTick = 0;
                        CTLastPos = null;
                    }
                } else if (isInTeleportingProcess && !isTeleporting) {
                    removeAllEffects();
                    sendMessage("Error! you moved too much!");
                    isInTeleportingProcess = false;
                    TeleportTick = 0;
                    CTLastPos = null;
                    TargetTeleportingLoc = null;
                    TargetTeleporting = null;
                }

            }
        }


        return super.onUpdate(currentTick);
    }

    public void ReloadMasterEnchatingList() {
        MasterEnchantigList = null;
    }

    public ArrayList<Enchantment> GetStoredEnchants() {
        return MasterEnchantigList;
    }

    public ArrayList<Enchantment> GetStoredEnchants(CustomEnchantment.Tier tier, int i, Item item) {
        if (MasterEnchantigList == null) MasterEnchantigList = CustomEnchantment.GetRandomEnchant(tier, 3, item);
        return MasterEnchantigList;
    }

    public void LoadHomes(ArrayList<HomeData> hd) {
        HD.addAll(hd);
    }

    public boolean CheckHomeKey(String key) {
        for (HomeData h : HD) {
            if (h.getName().equalsIgnoreCase(key)) return true;
        }
        return false;
    }

    public void TeleportToHome(String key) {
        TeleportToHome(key, false);
    }

    public void TeleportToHome(String key, int delay) {
        TeleportToHome(key, false, delay);
    }

    public void TeleportToHome(String key, boolean instant) {
        TeleportToHome(key, instant, 3);
    }

    public void TeleportToHome(String key, boolean instant, int delay) {
        for (HomeData h : HD) {
            if (h.getName().equalsIgnoreCase(key)) {
                Vector3 v3 = h.toVector3();
                if (instant) teleport(v3);
                else
                    StartTeleport(h.toPosition(getLevel()), 7);
            }
        }

    }

    public boolean CanAddHome() {
        return HD.size() < MaxHomes;
    }

    public void DelHome(String name) {
        int k = 0;
        int kk = 0;
        for (HomeData h : HD) {
            k++;
            if (h.getName().equalsIgnoreCase(name)) {
                kk = 1;
                break;
            }
        }
        if (kk == 1) HD.remove(k);
    }

    public void AddHome(String name) {
        Vector3 v = (Vector3) getPosition();
        HD.add(new HomeData(name, this));
    }


    public void AddHome(HomeData homeData) {
        HD.add(homeData);
    }

    public PlayerSettingsData getSettingsData() {
        return SettingsData;
    }

    public void setSettingsData(PlayerSettingsData settingsData) {
        SettingsData = settingsData;
    }

    public void StartTeleport(CorePlayer pl, int delay) {

        pl.BeginTeleportEffects(this, delay);
    }

    public void StartTeleport(CorePlayer pl) {
        StartTeleport(pl, 3);
    }

    public void StartTeleport(Position pl, int delay) {

        BeginTeleportEffects(pl, delay);
    }

    public void StartTeleport(Position pl) {
        StartTeleport(pl, 3);
    }

    private void BeginTeleportEffects(CorePlayer corePlayer) {
        BeginTeleportEffects(corePlayer, 3);
    }

    private void BeginTeleportEffects(CorePlayer corePlayer, int delay) {
        Effect e1 = Effect.getEffect(9);
        Effect e2 = Effect.getEffect(2);
        e1.setAmplifier(2);
        e2.setAmplifier(2);
        e1.setDuration(20 * 600);
        e2.setDuration(20 * 600);
        addEffect(e1);
        addEffect(e2);
        isTeleporting = true;
        isInTeleportingProcess = true;
        TeleportTick = getServer().getTick() + 20 * delay;
        TargetTeleporting = corePlayer;
        TargetTeleportingLoc = null;
    }

    private void BeginTeleportEffects(Position pos, int delay) {
        Effect e1 = Effect.getEffect(9);
        Effect e2 = Effect.getEffect(2);
        e1.setAmplifier(2);
        e2.setAmplifier(2);
        e1.setDuration(20 * 600);
        e2.setDuration(20 * 600);
        addEffect(e1);
        addEffect(e2);
        isTeleporting = true;
        isInTeleportingProcess = true;
        TeleportTick = getServer().getTick() + 20 * delay;
        TargetTeleporting = null;
        TargetTeleportingLoc = pos;
    }


//
//        int tickDiff = currentTick - this.lastUpdate;
//
//        if (tickDiff <= 0) {
//            return true;
//        }
//
//        this.messageCounter = 2;
//
//        this.lastUpdate = currentTick;
//
//        if (!this.isAlive() && this.spawned) {
//            ++this.deadTicks;
//            if (this.deadTicks >= 10) {
//                this.despawnFromAll();
//            }
//            return true;
//        }
//
//        if (this.spawned) {
//            this.processMovement(tickDiff);
//
//            this.entityBaseTick(tickDiff);
//
//            if (this.getServer().getDifficulty() == 0 && this.level.getGameRules().getBoolean(GameRule.NATURAL_REGENERATION)) {
//                if (this.getHealth() < this.getMaxHealth() && this.ticksLived % 20 == 0) {
//                    this.heal(1);
//                }
//
//                PlayerFood foodData = this.getFoodData();
//
//                if (foodData.getLevel() < 20 && this.ticksLived % 10 == 0) {
//                    foodData.addFoodLevel(1, 0);
//                }
//            }
//
//            if (this.isOnFire() && this.lastUpdate % 10 == 0) {
//                if (this.isCreative() && !this.isInsideOfFire()) {
//                    this.extinguish();
//                } else if (this.getLevel().isRaining()) {
//                    if (this.getLevel().canBlockSeeSky(this)) {
//                        this.extinguish();
//                    }
//                }
//            }
//
//            if (!this.isSpectator() && this.speed != null) {
//                if (this.onGround) {
//                    if (this.inAirTicks != 0) {
//                        this.startAirTicks = 5;
//                    }
//                    this.inAirTicks = 0;
//                    this.highestPosition = this.y;
//                } else {
//                    if (!this.isGliding() && !server.getAllowFlight() && !this.getAdventureSettings().get(AdventureSettings.Type.ALLOW_FLIGHT) && this.inAirTicks > 10 && !this.isSleeping() && !this.isImmobile() && uw) {
//                        double expectedVelocity = (-this.getGravity()) / ((double) this.getDrag()) - ((-this.getGravity()) / ((double) this.getDrag())) * Math.exp(-((double) this.getDrag()) * ((double) (this.inAirTicks - this.startAirTicks)));
//                        double diff = (this.speed.y - expectedVelocity) * (this.speed.y - expectedVelocity);
//
//                        Block block = level.getBlock(this);
//                        boolean onLadder = block.getId() == BlockID.LADDER;
//
//                        if (!this.hasEffect(Effect.JUMP) && diff > 0.6 && expectedVelocity < this.speed.y && !onLadder) {
//                            if (this.inAirTicks < 100) {
//                                //this.sendSettings();
//                                this.setMotion(new Vector3(0, expectedVelocity, 0));
//                            } else if (this.kick(PlayerKickEvent.Reason.FLYING_DISABLED, "Flying is not enabled on this server")) {
//                                return false;
//                            }
//                        }
//                        if (onLadder) {
//                            this.resetFallDistance();
//                        }
//                    }
//
//                    if (this.y > highestPosition) {
//                        this.highestPosition = this.y;
//                    }
//
//                    if (this.isGliding()) this.resetFallDistance();
//
//                    ++this.inAirTicks;
//
//                }
//
//                if (this.isSurvival() || this.isAdventure()) {
//                    if (this.getFoodData() != null) this.getFoodData().update(tickDiff);
//                }
//            }
//        }
//
//        this.checkTeleportPosition();
//        this.checkInteractNearby();
//
//        if (this.spawned && this.dummyBossBars.size() > 0 && currentTick % 100 == 0) {
//            this.dummyBossBars.values().forEach(DummyBossBar::updateBossEntityPosition);
//        }
//
//        return true;
//    }


//    @Override
//    protected void processLogin() {
//        if (!this.server.isWhitelisted((this.getName()).toLowerCase())) {
//            this.kick(PlayerKickEvent.Reason.NOT_WHITELISTED, "Server is white-listed");
//
//            return;
//        } else if (this.isBanned()) {
//            this.kick(PlayerKickEvent.Reason.NAME_BANNED, "You are banned");
//            return;
//        } else if (this.server.getIPBans().isBanned(this.getAddress())) {
//            this.kick(PlayerKickEvent.Reason.IP_BANNED, "You are banned");
//            return;
//        }
//
//        if (this.hasPermission(Server.BROADCAST_CHANNEL_USERS)) {
//            this.server.getPluginManager().subscribeToPermission(Server.BROADCAST_CHANNEL_USERS, this);
//        }
//        if (this.hasPermission(Server.BROADCAST_CHANNEL_ADMINISTRATIVE)) {
//            this.server.getPluginManager().subscribeToPermission(Server.BROADCAST_CHANNEL_ADMINISTRATIVE, this);
//        }
//
//        for (Player p : new ArrayList<>(this.server.getOnlinePlayers().values())) {
//            if (p != this && p.getName() != null && p.getName().equalsIgnoreCase(this.getName())) {
//                if (!p.kick(PlayerKickEvent.Reason.NEW_CONNECTION, "logged in from another location")) {
//                    this.close(this.getLeaveMessage(), "Already connected");
//                    return;
//                }
//            } else if (p.loggedIn && this.getUniqueId().equals(p.getUniqueId())) {
//                if (!p.kick(PlayerKickEvent.Reason.NEW_CONNECTION, "logged in from another location")) {
//                    this.close(this.getLeaveMessage(), "Already connected");
//                    return;
//                }
//            }
//        }
//
//        CompoundTag nbt;
//        File legacyDataFile = new File(server.getDataPath() + "players/" + this.username.toLowerCase() + ".dat");
//        File dataFile = new File(server.getDataPath() + "players/" + this.uuid.toString() + ".dat");
//        if (legacyDataFile.exists() && !dataFile.exists()) {
//            nbt = this.server.getOfflinePlayerData(this.username);
//
//            if (!legacyDataFile.delete()) {
//                log.warn("Could not delete legacy player data for {}", this.username);
//            }
//        } else {
//            nbt = this.server.getOfflinePlayerData(this.uuid);
//        }
//
//        if (nbt == null) {
//            this.close(this.getLeaveMessage(), "Invalid data");
//            return;
//        }
//
//        if (loginChainData.isXboxAuthed() && server.getPropertyBoolean("xbox-auth") || !server.getPropertyBoolean("xbox-auth")) {
//            server.updateName(this.uuid, this.username);
//        }
//
//        this.playedBefore = (nbt.getLong("lastPlayed") - nbt.getLong("firstPlayed")) > 1;
//
//        boolean alive = true;
//
//        nbt.putString("NameTag", this.username);
//
//        if (0 >= nbt.getShort("Health")) {
//            alive = false;
//        }
//
//        int exp = nbt.getInt("EXP");
//        int expLevel = nbt.getInt("expLevel");
//        this.setExperience(exp, expLevel);
//
//        this.gamemode = nbt.getInt("playerGameType") & 0x03;
//        if (this.server.getForceGamemode()) {
//            this.gamemode = this.server.getGamemode();
//            nbt.putInt("playerGameType", this.gamemode);
//        }
//
//        this.adventureSettings = new AdventureSettings(this)
//                .set(AdventureSettings.Type.WORLD_IMMUTABLE, isAdventure())
//                .set(AdventureSettings.Type.WORLD_BUILDER, !isAdventure())
//                .set(AdventureSettings.Type.AUTO_JUMP, true)
//                .set(AdventureSettings.Type.ALLOW_FLIGHT, isCreative())
//                .set(AdventureSettings.Type.NO_CLIP, isSpectator());
//
//        Level level;
//        if ((level = this.server.getLevelByName(nbt.getString("Level"))) == null || !alive) {
//            this.setLevel(this.server.getDefaultLevel());
//            nbt.putString("Level", this.level.getName());
//            nbt.getList("Pos", DoubleTag.class)
//                    .add(new DoubleTag("0", this.level.getSpawnLocation().x))
//                    .add(new DoubleTag("1", this.level.getSpawnLocation().y))
//                    .add(new DoubleTag("2", this.level.getSpawnLocation().z));
//        } else {
//            this.setLevel(level);
//        }
//
//        for (Tag achievement : nbt.getCompound("Achievements").getAllTags()) {
//            if (!(achievement instanceof ByteTag)) {
//                continue;
//            }
//
//            if (((ByteTag) achievement).getData() > 0) {
//                this.achievements.add(achievement.getName());
//            }
//        }
//
//        nbt.putLong("lastPlayed", System.currentTimeMillis() / 1000);
//
//        UUID uuid = getUniqueId();
//        nbt.putLong("UUIDLeast", uuid.getLeastSignificantBits());
//        nbt.putLong("UUIDMost", uuid.getMostSignificantBits());
//
//        if (this.server.getAutoSave()) {
//            this.server.saveOfflinePlayerData(this.uuid, nbt, true);
//        }
//
//        this.sendPlayStatus(PlayStatusPacket.LOGIN_SUCCESS);
//        this.server.onPlayerLogin(this);
//
//        ListTag<DoubleTag> posList = nbt.getList("Pos", DoubleTag.class);
//
//        super.init(this.level.getChunk((int) posList.get(0).data >> 4, (int) posList.get(2).data >> 4, true), nbt);
//
//        if (!this.namedTag.contains("foodLevel")) {
//            this.namedTag.putInt("foodLevel", 20);
//        }
//        int foodLevel = this.namedTag.getInt("foodLevel");
//        if (!this.namedTag.contains("FoodSaturationLevel")) {
//            this.namedTag.putFloat("FoodSaturationLevel", 20);
//        }
//        float foodSaturationLevel = this.namedTag.getFloat("foodSaturationLevel");
//        this.foodData = new PlayerFood(this, foodLevel, foodSaturationLevel);
//
//        if (this.isSpectator()) this.keepMovement = true;
//
//        this.forceMovement = this.teleportPosition = this.getPosition();
//
//        ResourcePacksInfoPacket infoPacket = new ResourcePacksInfoPacket();
//        infoPacket.resourcePackEntries = this.server.getResourcePackManager().getResourceStack();
//        infoPacket.mustAccept = this.server.getForceResources();
//        this.dataPacket(infoPacket);
//    }

//    @Override
//    public void completeLoginSequence() {
//        PlayerLoginEvent ev;
//        this.server.getPluginManager().callEvent(ev = new PlayerLoginEvent(this, "Plugin reason"));
//        if (ev.isCancelled()) {
//            this.close(this.getLeaveMessage(), ev.getKickMessage());
//            return;
//        }
//
//        CustomStartGamePacket startGamePacket = new CustomStartGamePacket();
//        startGamePacket.entityUniqueId = this.id;
//        startGamePacket.entityRuntimeId = this.id;
//        startGamePacket.playerGamemode = (this.gamemode);
//        startGamePacket.x = (float) this.x;
//        startGamePacket.y = (float) this.y;
//        startGamePacket.z = (float) this.z;
//        startGamePacket.yaw = (float) this.yaw;
//        startGamePacket.pitch = (float) this.pitch;
//        startGamePacket.seed = -1;
//        startGamePacket.dimension = (byte) (this.level.getDimension() & 0xff);
//        startGamePacket.worldGamemode = (this.gamemode);
//        startGamePacket.difficulty = this.server.getDifficulty();
//        startGamePacket.spawnX = (int) this.x;
//        startGamePacket.spawnY = (int) this.y;
//        startGamePacket.spawnZ = (int) this.z;
//        startGamePacket.hasAchievementsDisabled = true;
//        startGamePacket.dayCycleStopTime = -1;
//        startGamePacket.eduMode = false;
//        startGamePacket.rainLevel = 0;
//        startGamePacket.lightningLevel = 0;
//        startGamePacket.commandsEnabled = this.isEnableClientCommand();
//        startGamePacket.gameRules = getLevel().getGameRules();
//        startGamePacket.levelId = "";
//        startGamePacket.worldName = this.getServer().getNetwork().getName();
//        startGamePacket.generator = 1; //0 old, 1 infinite, 2 flat
//        this.dataPacket(startGamePacket);
//
//        this.dataPacket(new AvailableEntityIdentifiersPacket());
//
//        this.loggedIn = true;
//
//        this.level.sendTime(this);
//
//        this.setMovementSpeed(DEFAULT_SPEED);
//        this.sendAttributes();
//        this.setNameTagVisible(true);
//        this.setNameTagAlwaysVisible(true);
//        this.setCanClimb(true);
//
//        this.server.getLogger().info(this.getServer().getLanguage().translateString("nukkit.player.logIn",
//                TextFormat.AQUA + this.username + TextFormat.WHITE,
//                this.ip,
//                String.valueOf(this.port),
//                String.valueOf(this.id),
//                this.level.getName(),
//                String.valueOf(NukkitMath.round(this.x, 4)),
//                String.valueOf(NukkitMath.round(this.y, 4)),
//                String.valueOf(NukkitMath.round(this.z, 4))));
//
//        if (this.isOp() || this.hasPermission("nukkit.textcolor")) {
//            this.setRemoveFormat(false);
//        }
//
//        this.server.addOnlinePlayer(this);
//        this.server.onPlayerCompleteLoginSequence(this);
//    }
}
