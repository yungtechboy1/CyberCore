package net.yungtechboy1.CyberCore.Manager.Factions;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.block.BlockPlaceEvent;
import cn.nukkit.event.player.PlayerDeathEvent;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.math.Vector3;
import cn.nukkit.utils.ConfigSection;
import cn.nukkit.utils.TextFormat;
import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.CyberCoreMain;
import net.yungtechboy1.CyberCore.Manager.Factions.Mission.ActiveMission;
import net.yungtechboy1.CyberCore.Manager.Form.Windows.FactionChatFactionWindow;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

/**
 * Created by carlt_000 on 5/13/2016.
 */
////@TODO Make this so I can Call a Faction Class....
public class Faction {
    public String Leader;
    public ArrayList<String> Recruits = new ArrayList<>();
    public ArrayList<String> Members = new ArrayList<>();
    public ArrayList<String> Officers = new ArrayList<>();
    public ArrayList<String> Generals = new ArrayList<>();
    public ActiveMission AM = null;
    public ArrayList<AllyRequest> AR = new ArrayList<>();
    protected boolean Clean = true;
    protected int lastupload = 0;
    LinkedList<String> LastFactionChat = new LinkedList<>();
    LinkedList<String> LastAllyChat = new LinkedList<>();
    private FactionsMain Main;
    private String Name;
    private String DisplayName;
    private String MOTD;
    private String Desc;
    private ArrayList<String> FChat = new ArrayList<>();
    private ArrayList<String> FAlly = new ArrayList<>();
    private ArrayList<String> Plots = new ArrayList<>();
    private ArrayList<String> Allies = new ArrayList<>();
    private ArrayList<String> Enemies = new ArrayList<>();
    private String War = null;
    private Map<String, Invitation> Invites = new HashMap<>();
    private Integer MaxPlayers = 15;
    private Integer PowerBonus = 1;
    private Integer Privacy = 0;
    private Integer Perms = 0;
    private Integer Power = 0;
    private Integer Rich = 0;
    private int Points = 0;
    private Integer XP = 0;
    private Integer Level = 0;
    //todo Save faction Settings
    private FactionSettings Settings = new FactionSettings();
    private ArrayList<Integer> CompletedMissionIDs = new ArrayList<>();
    //    private Integer Money = 0;
    private Vector3 Home = new Vector3(0, 0, 0);

    public Faction(FactionsMain main, String name, String displayname, String leader) {
        Main = main;
        Name = name;
        DisplayName = displayname;
        Leader = leader;
        onCreation();
    }

    public Faction(FactionsMain main, String name, String displayname, String leader, ArrayList<String> members, ArrayList<String> generals, ArrayList<String> officers, ArrayList<String> recruits) {
        Main = main;
        Name = name;
        DisplayName = displayname;
        Leader = leader;
        Members = members;
        Recruits = recruits;
        Generals = generals;
        Officers = officers;
        for (String m : Members) {
            Player p = Main.getServer().getPlayerExact(m);
            if (p != null) {
                Main.FFactory.FacList.put(p.getName().toLowerCase(), Name);
            }
        }
        for (String m : Officers) {
            Player p = Main.getServer().getPlayerExact(m);
            if (p != null) {
                Main.FFactory.FacList.put(p.getName().toLowerCase(), Name);
            }
        }
        for (String m : Generals) {
            Player p = Main.getServer().getPlayerExact(m);
            if (p != null) {
                Main.FFactory.FacList.put(p.getName().toLowerCase(), Name);
            }
        }

        for (String m : Recruits) {
            Player p = Main.getServer().getPlayerExact(m);
            if (p != null) {
                Main.FFactory.FacList.put(p.getName().toLowerCase(), Name);
            }
        }
        Player p = Main.getServer().getPlayerExact(Leader);
        if (p != null) Main.FFactory.FacList.put(p.getName().toLowerCase(), Name);
        onCreation();
    }

    private void onCreation() {

    }


    public FactionSettings getSettings() {
        return Settings;
    }

    public void PromotePlayer(Player pp) {
        PromotePlayer(pp, false);
    }

    public void PromotePlayer(Player pp, boolean leaderConfirm) {
        FactionRank cr = getPlayerRank(pp);
        String pn = pp.getName().toLowerCase();
        switch (cr) {
            case Recruit:
                Recruits.remove(pn);
                Members.add(pn);
                break;
            case Member:
                Members.remove(pn);
                Officers.add(pn);
                break;
            case Officer:
                Officers.remove(pn);
                Generals.add(pn);
                break;
            case General:
                if (!leaderConfirm) break;
                Generals.remove(pn);
                Generals.add(Leader);
                Leader = (pn);
                break;
        }
    }

    public void DemotePlayer(Player pp) {
        FactionRank cr = getPlayerRank(pp);
        String pn = pp.getName().toLowerCase();
        switch (cr) {
            case Member:
                Members.remove(pn);
                Recruits.add(pn);
                break;
            case Officer:
                Officers.remove(pn);
                Members.add(pn);
                break;
            case General:
                Generals.remove(pn);
                Officers.add(pn);
                break;
        }
    }

    public Faction GetAllyFromName(String name) {
        String found = null;
        name = name.toLowerCase();
        int delta = 2147483647;
        Iterator<String> var4 = getFAlly().iterator();

        while (var4.hasNext()) {
            String fn = var4.next();
            if (fn.toLowerCase().startsWith(name) || fn.toLowerCase().contains(name)) {
                int curDelta = fn.length() - name.length();
                if (curDelta < delta) {
                    found = fn;
                    delta = curDelta;
                }

                if (curDelta == 0) {
                    found = fn;
                }
            }
        }

        return Main.FFactory.getFaction(found);
    }

    public int GetPlayerCount() {
        int c = 0;
        c += GetMembers().size();
        c += GetRecruits().size();
        c += GetOfficers().size();
        c += GetGenerals().size();
        if (GetLeader() != null) c++;
        return c;
    }

    public void KickPlayer(String pn) {
        FactionRank r = getPlayerRank(pn);
        switch (r) {
            case Recruit:
                DelRecruit(pn);
                break;
            case Member:
                DelMember(pn);
                break;
            case Officer:
                DelOfficer(pn);
                break;
            case General:
                DelGeneral(pn);
                break;
        }


        BroadcastMessage(FactionsMain.NAME + TextFormat.YELLOW + pn + " has been  kicked from the faction!");
//        Main.FFactory.FacList.remove(pn);
        TakePower(2);
    }

    public void KickPlayer(Player p) {
        FactionRank r = getPlayerRank(p);
        String pn = p.getName();
        switch (r) {
            case Recruit:
                DelRecruit(pn);
                break;
            case Member:
                DelMember(pn);
                break;
            case Officer:
                DelOfficer(pn);
                break;
            case General:
                DelGeneral(pn);
                break;
        }


        BroadcastMessage(FactionsMain.NAME + TextFormat.YELLOW + p.getName() + " has been  kicked from the faction!");
        p.sendMessage(FactionsMain.NAME + TextFormat.GREEN + "You Have Been Kicked From factionName!!!");
//        Main.FFactory.FacList.remove(pn);
        TakePower(2);
    }

    public void SendFactionChatWindow(CorePlayer cp) {
        cp.showFormWindow(new FactionChatFactionWindow((LinkedList<String>) LastFactionChat.clone()));
    }

    public void HandleFactionChatWindow(FormResponseCustom frc, CorePlayer cp) {
        if (frc == null) {
            System.out.println("Error @ 12255");
            return;
        }
        String msg = frc.getInputResponse(0);
        if (msg == null) {
            //No Message Send?
            //CLose windows
            return;
        }
        AddFactionChatMessage(msg, cp);
        SendFactionChatWindow(cp);
    }

    //@todo LMAO THIS IS NOT EVEN CLOSE TO being correcty XD
    public String toString() {
        return getName();
    }

    public void SetPlots(ArrayList<String> value) {
        Plots = value;
    }

    public ArrayList<String> GetPlots() {
        return Plots;
    }

    public void AddPlots(int chunkx, int chunkz, CorePlayer player) {
        String plot = chunkx + "|" + chunkz;
        Plots.add(plot);
        Connection c = CyberCoreMain.getInstance().FM.FFactory.getMySqlConnection();
        try {
            Statement s = c.createStatement();
            //1 = Ally Request
            //0 = Friend Requestw
            //2 = ?????
            //CyberCoreMain.getInstance().getIntTime
            s.executeQuery(String.format("INSERT INTO `plots` VALUES ('%s','%s',2)", plot, getName()));
            c.close();
//        Main.FFactory.allyrequest.put(getName(), fac.getName());
        } catch (Exception e) {
            e.printStackTrace();
            player.sendMessage("Error sending Ally Request! Please report Error 'E332R' to an admin");
            return;
        }
    }

    public void DelPlots(String plot) {
        Plots.remove(plot);
    }

    public void SetMaxPlayers(Integer value) {
        MaxPlayers = value;
    }

    public Integer GetMaxPlayers() {
        return MaxPlayers;
    }

    public void SetPowerBonus(Integer value) {
        PowerBonus = value;
    }

    public Integer GetPowerBonus() {
        return PowerBonus;
    }

    public Integer CalculateMaxPower() {
        Integer TP = GetNumberOfPlayers();
        return TP * 10;
        //Lets do 20 Instead of 10
    }

    public Integer GetNumberOfPlayers() {
        return GetGenerals().size() + GetOfficers().size() + GetMembers().size() + 1;
    }

    public void SetPrivacy(Integer value) {
        Privacy = value;
    }

    public Integer GetPrivacy() {
        return Privacy;
    }

    public String getName() {
        return Name;
    }

    public void SetDesc(String value) {
        Desc = value;
    }

    public String GetDesc() {
        return Desc;
    }

    public void SetMOTD(String value) {
        MOTD = value;
    }

    public String GetMOTD() {
        return MOTD;
    }

    public void SetPerm(String value) {
        Settings = new FactionSettings(value);
    }

    public FactionSettings GetPerm() {
        return getSettings();
    }

    public Integer GetPerm(Integer key) {
        try {
            return Integer.parseInt(Perms.toString().substring(key));
        } catch (Exception ignore) {
            return null;
        }
    }

    public void SetPoints(Integer value) {
        if (value == null) value = 0;
        Points = value;
    }

    public Integer GetPoints() {
        return Points;
    }

    public void AddPoints(Integer pts) {
        SetPoints(GetPoints() + Math.abs(pts));
    }

    public void TakePoints(Integer pts) {
        Integer a = GetPoints() - pts;
        if (a < 0) SetPoints(0);
        SetPoints(a);
    }

    public void SetLevel(Integer value) {
        Level = value;
        UpdateBossBar();
    }

    public Integer GetLevel() {
        return Level;
    }

    public void AddLevel(Integer lvl) {
        SetLevel(GetLevel() + Math.abs(lvl));
    }

    public void TakeLevel(Integer lvl) {
        Integer a = GetLevel() - lvl;
        if (a < 0) SetLevel(0);
        SetLevel(a);
    }

    public int calculateRequireExperience() {
        return calculateRequireExperience(GetLevel());
    }

    public int calculateRequireExperience(int level) {
        if (level >= 30) {
            return 112 + (level - 30) * 9 * 100;
        } else if (level >= 15) {
            return 37 + (level - 15) * 5 * 100;
        } else {
            return 7 + level * 2 * 100;
        }
    }

    public void CalculateXP() {
        int xp = GetXP();
        int lvl = GetLevel();
        while (xp >= calculateRequireExperience(lvl)) {
            xp = xp - calculateRequireExperience(lvl);
            lvl++;
        }
        SetXP(xp);
        SetLevel(lvl);
    }

    public void AddXP(int add) {
        if (add == 0) return;
        int now = GetXP();
        int added = now + add;
        int level = GetLevel();
        int most = calculateRequireExperience(level);
        while (added >= most) {  //Level Up!
            added = added - most;
            most = calculateRequireExperience(++level);
        }
        SetXP(added);
        SetLevel(level);
    }

    public void SetXP(Integer value) {
        XP = value;
        UpdateBossBar();
    }

    public void SetXPCalculate(Integer value) {
        int level = GetLevel();
        int most = calculateRequireExperience(level);
        while (value >= most) {  //Level Up!
            value = value - most;
            most = calculateRequireExperience(++level);
        }
        SetXP(value);
        SetLevel(level);
    }

    public Integer GetXPPercent() {
        Double d = ((XP / (double) calculateRequireExperience(GetLevel())) * 100);
        return d.intValue();
    }

    public Integer GetXP() {
        return XP;
    }

    public boolean TakeXP(Integer xp) {
        int x = GetXP();
        int lvl = GetLevel();
        while (x < xp) {
            if (lvl == 0) return false;
            xp += calculateRequireExperience(--lvl);
        }
        Integer a = x - xp;
        SetXP(a);
        SetLevel(lvl);
        return true;
    }

    public void HandleKillEvent(PlayerDeathEvent event) {
        if (GetActiveMission() != null) {
            GetActiveMission().AddKill();
        }
    }

    public void HandleBreakEvent(BlockBreakEvent event) {
        if (GetActiveMission() != null) {
            GetActiveMission().BreakBlock(event);
        }
    }

    public void HandlePlaceEvent(BlockPlaceEvent event) {
        if (GetActiveMission() != null) {
            GetActiveMission().PlaceBlock(event);
        }
    }

    public void SetActiveMission(String id) {
        if (id == null || id.equalsIgnoreCase("")) {
            SetActiveMission();
        } else {
            SetActiveMission(Integer.parseInt(id));
        }
    }

    public void AcceptNewMission(Integer id, CommandSender Sender) {
        if (GetActiveMission() != null) {
            Sender.sendMessage(FactionsMain.NAME + TextFormat.RED + "Error you already have a mission!!");
            return;
        }
        if (CompletedMissionIDs.contains(id)) {
            Sender.sendMessage(FactionsMain.NAME + TextFormat.RED + "Error you have already completed this mission!!");
            return;
        }
        SetActiveMission(id);
    }

    public void SetActiveMission(Integer id) {
//        for(Mission mission: Main.Missions){
//            if(mission.id.equals(id)) {
//                SetActiveMission(new ActiveMission(Main,this,mission));
//                BroadcastMessage(FactionsMain.NAME+TextFormat.AQUA+mission.name+TextFormat.GREEN+" Faction mission accepted!");
//            }
//        }
    }

    public void RetrieveActiveMission(String id) {
        if (id == null || id.equalsIgnoreCase("")) {
            SetActiveMission();
        } else {
            RetrieveActiveMission(Integer.parseInt(id));
        }
    }

    public void RetrieveActiveMission(Integer id) {
//        if(Main.AM.exists(getName())){
//            for(Mission mission: Main.Missions){
//                if(mission.id.equals(id)){
//                    try {
//                        /*YamlConfig yamlConfig = new YamlConfig();
//                        yamlConfig.setClassTag("ActiveMission",ActiveMission.class);
//                        yamlConfig.setClassTag("tag:yaml.org,2002:cn.nukkit.item.ItemBlock",ItemBlock.class);
//                        YamlReader reader = new YamlReader(new FileReader(Main.getDataFolder().toString()+"/missions/"+getName()+".yml"),yamlConfig);
//                        ActiveMission activeMission = reader.read(ActiveMission.class);
//                        System.out.println(activeMission.name)*/;
//                        ActiveMission activeMission = new ActiveMission(Main, this,(ConfigSection) Main.AM.get(getName()));
//                        SetActiveMission(activeMission);
//                        //SetActiveMission(new ActiveMission(Main,this,mission));
//                        BroadcastMessage(FactionsMain.NAME+TextFormat.AQUA+mission.name+TextFormat.GREEN+" Faction mission accepted!");
//                        return;
//                    }catch(Exception ex){
//                        ex.printStackTrace();
//                    }
//                }
//            }
//        }
//        SetActiveMission();
    }

    public void SetActiveMission() {
        AM = null;
    }

    public void SetActiveMission(ActiveMission mission) {
        AM = mission;
    }

    public ActiveMission GetActiveMission() {
        return AM;
    }

    public void CompleteMission(ActiveMission mission) {
        CompletedMissionIDs.add(mission.id);
        AM = null;
    }

    public void SetCompletedMissisons(ArrayList<Integer> value) {
        CompletedMissionIDs = value;
    }

    public ArrayList<Integer> GetCompletedMissions() {
        return CompletedMissionIDs;
    }

    public void AddCompletedMission(Integer mission) {
        CompletedMissionIDs.add(mission);
    }

    public void SetMoney(Integer value) {
//        Money = value;
        Connection c = CyberCoreMain.getInstance().FM.FFactory.getMySqlConnection();
        try {
            Statement s = c.createStatement();
            s.executeUpdate("UPDATE Settings SET Money = " + value + " WHERE Name LIKE '" + getName() + "' VALUES ");
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        return;
//        UpdateTopResults();
    }

    /**
     * @return null | Int
     */
    public Integer GetMoney() {
        Connection c = CyberCoreMain.getInstance().FM.FFactory.getMySqlConnection();
        try {
            Statement s = c.createStatement();
            ResultSet r = s.executeQuery("SELECT Money FROM Settings WHERE Name LIKE '" + getName() + "'");
            ArrayList<AllyRequest> list = new ArrayList<>();
            if (r.next()) {
                Integer m = r.getInt("Money");
                c.close();
                return m;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    public void AddMoney(Integer money) {
        SetMoney(GetMoney() + Math.abs(money));
    }

    public void TakeMoney(Integer money) {
        Integer a = GetMoney() - money;
        if (a < 0) SetMoney(0);
        SetMoney(a);
    }

    public Integer GetRich() {
        return Rich + GetMoney();
    }

    public void SetRich(Integer rich) {
        Rich = rich;
    }

    public void CalcualteRich() {
        //Level lvl = Main.getServer().getLevelByName("world");

        //Main.getServer().getScheduler().scheduleAsyncTask(new FactionRichAsyncSingle(Main,lvl,this));
        /*Integer value = 0;
        if(lvl == null)return value;
        for(String plot: GetPlots()){
        String key = plot.split("\\|")[0] + "|" + plot.split("\\|")[1];
            int sx = Integer.parseInt(plot.split("\\|")[0]) << 4;
            int sz = Integer.parseInt(plot.split("\\|")[1]) << 4;
            for (int x = 0; x < 64; x++) {
                for (int y = 0; y < 128; y++) {
                    for (int z = 0; z < 64; z++) {
                        int fx = x + sx;
                        int fz = z + sz;
                        Block b = lvl.getBlock(new Vector3(fx,y,fz));
                        String kkey = "";
                        if(b.getDamage() != 0){
                            kkey = b.getId() + "|" + b.getDamage();
                        }else{
                            kkey = b.getId()+"";
                        }
                        if(Main.BV.exists(kkey))value += (Integer) Main.BV.get(kkey);
                    }
                }
            }
        }*/
        //return value;
    }

    public Integer GetMaxPower() {
        return CalculateMaxPower();
    }

    public void SetPower(Integer value) {
        Integer dif = value - GetPower();
        String t = "";
        if (dif > 0) {
            t = TextFormat.GREEN + "Gained +" + dif;
        } else {
            t = TextFormat.RED + "Lost -" + Math.abs(dif);
        }
        BroadcastPopUp(TextFormat.GRAY + "Faction now has " + TextFormat.GREEN + value + TextFormat.GRAY + " PowerAbstract!" + t);
        Power = value;
    }

    public Integer GetPower() {
        return Power;
    }

    public void AddPower(Integer power) {
        Integer t = GetPower() + Math.abs(power);
        if (t > CalculateMaxPower()) {
            SetPower(CalculateMaxPower());
        } else {
            SetPower(t);
        }
    }

    public void TakePower(Integer power) {
        Integer a = GetPower() - power;
        if (a < 0) {
            SetPower(0);
        } else {
            SetPower(a);
        }
    }

    public Vector3 GetHome() {
        return Home;
    }

    public void SetHome(Integer x, Integer y, Integer z) {
        SetHome(new Vector3(x, y, z));
    }

    public void SetHome(Vector3 pos) {
        Home = pos;

        saveHome();

    }


    public void saveHome() {
        Connection c = CyberCoreMain.getInstance().FM.FFactory.getMySqlConnection();
        try {
            Statement s = c.createStatement();
            //CyberCoreMain.getInstance().getIntTime
            s.executeUpdate(String.format("INSERT INTO `home` VALUES ('" + getName() + "','%s','%s','%s') ;", Home.getX(), Home.getY(), Home.getZ()));
            c.close();
//        Main.FFactory.allyrequest.put(getName(), fac.getName());
        } catch (Exception e) {
            e.printStackTrace();
            BroadcastMessage("Error sending Ally Request! Please report Error 'E30DR' to an admin");
            return;
        }
    }

    public void StartWar(String key) {
        War = key;
    }

    public void EndWar() {
        War = null;
    }

    public ConfigSection GetWarData() {
        if (War != null && Main.War.containsKey(War)) {
            return (ConfigSection) Main.War.get(War);
        }
        return null;
    }

    public Boolean AtWar() {
        return War != null;
    }

    public Boolean AtWar(String fac) {
        if (War != null) {
            return ((ConfigSection) Main.War.get(War)).getString("defenders").equalsIgnoreCase(fac);
        }
        return false;
    }

    public void SetEnemies(ArrayList<String> list) {
        Enemies = list;
    }

//    public void AddCooldown(Integer secs){
//        Map<String, Object> cd = Main.CD.getAll();
//        int time = (int)(Calendar.getInstance().getTime().getTime()/1000);
//        cd.put(getName(),time+secs);
//    }
//    public boolean HasWarCooldown(){
//        Map<String, Object> cd = Main.CD.getAll();
//        int time = (int)(Calendar.getInstance().getTime().getTime()/1000);
//        if (cd.containsKey(getName())){
//            if (time >= (int)cd.get(getName())){
//                cd.remove(getName());
//                return false;
//            }
//            return true;
//        }
//        return false;
//    }

    public void AddEnemy(Faction fac, CorePlayer player) {
        Enemies.add(fac.getName());
        Connection c = CyberCoreMain.getInstance().FM.FFactory.getMySqlConnection();
        try {
            Statement s = c.createStatement();
            //1 = Ally Request
            //0 = Friend Request
            //2 = ?????
            //CyberCoreMain.getInstance().getIntTime
            s.executeQuery(String.format("INSERT INTO `relationship` VALUES (null,'%s','%s','Enemy'," + CyberCoreMain.getInstance().getIntTime() + ")", getName(), fac.getName()));
            c.close();
//        Main.FFactory.allyrequest.put(getName(), fac.getName());
        } catch (Exception e) {
            e.printStackTrace();
            player.sendMessage("Error sending Ally Request! Please report Error 'E332R' to an admin");
            return;
        }
    }

    public void RemoveEnemy(String fac) {
        Enemies.remove(fac);
    }

    public ArrayList<String> GetEnemies() {
        return Enemies;
    }

    public Boolean isEnemy(String fac) {
        return Enemies.contains(fac.toLowerCase());
    }

    public void SetAllies(ArrayList<String> list) {
        Allies = list;
    }

    public void AddAlly(String fac) {
        Allies.add(fac);
    }

    public void RemoveAlly(Faction fac) {
        if (fac == null) return;
        RemoveAlly(fac.getName());
    }

    public void RemoveAlly(String fac) {
        Allies.remove(fac);
    }

    public ArrayList<String> GetAllies() {
        return Allies;
    }

    //Can Attack Nuetral but can not Attack Allys!
    public Boolean isNeutral(CorePlayer player) {
        Faction f = player.getFaction();
        if (f != null) return isNeutral(f);
        return true;
    }

    public Boolean isNeutral(String name) {
        return !isAllied(name) && !isEnemy(name);
    }

    public Boolean isNeutral(Faction fac) {
        return isNeutral(fac.getName());
    }

    public Boolean isAllied(CorePlayer player) {
        Faction f = player.getFaction();
        if (f != null) return isAllied(f);
        return false;
    }

    public Boolean isAllied(Faction fac) {
        return isAllied(fac.getName());
    }

    public Boolean isAllied(String fac) {
        return Allies.contains(fac.toLowerCase());
    }

//    public void SetInvite(Map<String, Integer> Invs) {
//        Invites = Invs;
//    }

//    public Map<String, Integer> GetInvite() {
//        return Invites;
//    }

    public void AddInvite(CorePlayer player, Integer value, CorePlayer sender, FactionRank fr) {

        if (!addRequest(RequestType.Faction_Invite, null, player, value, sender)) {
            player.sendMessage("Error sending Faction Invite Request! Please report Error 'E332FI' to an admin");
            return;
        }

        Invites.put(player.getName().toLowerCase(), new Invitation(this, sender, value, fr));
    }

    public void DelInvite(String name) {
        Connection c = CyberCoreMain.getInstance().FM.FFactory.getMySqlConnection();
        try {
            Statement stmt = c.createStatement();
            stmt.executeUpdate("DELETE * from `Requests` where `faction` LIKE '" + getName() + "' AND `player` LIKE '" + name + "' AND `TYPE` =  " + RequestType.Faction_Invite.Key + ";");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        Invites.remove(name);
    }

    public Boolean AcceptInvite(CorePlayer p) {
        String name = p.getName();
        Invitation i = HasInvite(name);
        if (i == null) {
            //No Invite
            p.sendMessage("Error! You are not invited to join '" + getDisplayName() + "'!");
            return false;
        }
        if (!i.isValid()) {
            //Invite Timed out
            p.sendMessage("Error! You're invite has expired!");
            DelInvite(name);
            return false;
        }
        DelInvite(name);
        FactionRank r = i.getRank();
        switch (r) {
            case General:
                AddGeneral(p);
                break;
            case Member:
                AddMember(p);
                break;
            case Officer:
                AddOfficer(p);
                break;
            default:
            case Recruit:
                AddRecruit(p);
                break;
        }
        BroadcastMessage(FactionsMain.NAME + TextFormat.GREEN + name + " Has joined your faction!");
        return true;
    }

    public void DenyInvite(String name) {
        DelInvite(name);
    }

    public Invitation HasInvite(CorePlayer name) {
        return HasInvite(name.getName());
    }

    public Invitation HasInvite(String name) {

        Connection c = CyberCoreMain.getInstance().FM.FFactory.getMySqlConnection();
        try {
            Statement stmt = c.createStatement();
            ResultSet r = stmt.executeQuery("select * from `Requests` where `faction` LIKE '" + getName() + "' AND `player` LIKE '" + name + "' AND `TYPE` =  " + RequestType.Faction_Invite.Key + ";");
            if (r == null) return null;
            if (r.next()) {
                return new Invitation(getName(), name, r.getInt("expires"), FactionRank.getRankFromString(r.getString("data")));
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

//        return Invites.containsKey(name.toLowerCase());
    }

    public void SetMembers(ArrayList<String> members) {
        Members = members;
    }

    public void SetOfficers(ArrayList<String> members) {
        Officers = members;
    }

    public void SetGenerals(ArrayList<String> members) {
        Generals = members;
    }

    public void SetRecruits(ArrayList<String> members) {
        Recruits = members;
    }

    public void SetLeader(String leader) {
        Leader = leader;
    }

    public ArrayList<String> GetMembers() {
        return Members;
    }

    public ArrayList<String> GetOfficers() {
        return Officers;
    }

    public ArrayList<String> GetGenerals() {
        return Generals;
    }

    public ArrayList<String> GetRecruits() {
        return Recruits;
    }

    public String GetLeader() {
        return Leader;
    }

    public void AddMember(Player p) {
        AddMember(p.getName());
    }

    public void AddMember(CorePlayer p) {
        AddMember(p.getName());
    }

    public void AddMember(String name) {
        Members.add(name);
        Main.FFactory.FacList.put(name.toLowerCase(), getName());
    }

    public void AddOfficer(Player p) {
        AddOfficer(p.getName());
    }

    public void AddOfficer(CorePlayer p) {
        AddOfficer(p.getName());
    }

    public void AddOfficer(String name) {
        Officers.add(name);
        Main.FFactory.FacList.put(name.toLowerCase(), getName());
    }

    public void AddGeneral(Player p) {
        AddGeneral(p.getName());
    }

    public void AddGeneral(CorePlayer p) {
        AddGeneral(p.getName());
    }

    public void AddGeneral(String name) {
        Generals.add(name);
        Main.FFactory.FacList.put(name.toLowerCase(), getName());
    }

    public void AddRecruit(Player p) {
        AddRecruit(p.getName());
    }

    public void AddRecruit(CorePlayer p) {
        AddRecruit(p.getName());
    }

    public void AddRecruit(String name) {
        Recruits.add(name);
        Main.FFactory.FacList.put(name.toLowerCase(), getName());
    }

    public void DelMember(String name) {
        Members.remove(name);

        Main.FFactory.FacList.remove(name.toLowerCase());
    }

    public void DelOfficer(String name) {
        Officers.remove(name);
        Main.FFactory.FacList.remove(name.toLowerCase());
    }

    public void DelGeneral(String name) {
        Generals.remove(name);
        Main.FFactory.FacList.remove(name.toLowerCase());
    }

    public void DelRecruit(String name) {
        Recruits.remove(name);
        Main.FFactory.FacList.remove(name.toLowerCase());
    }

    public boolean IsMember(Player p) {
        return IsMember(p.getName());
    }

    public boolean IsOfficer(Player p) {
        return IsOfficer(p.getName());
    }

    public boolean IsGeneral(Player p) {
        return IsGeneral(p.getName());
    }

    public boolean IsRecruit(Player p) {
        return IsRecruit(p.getName());
    }

    public boolean IsRecruit(String n) {
        for (String m : GetRecruits()) {
            if (n.equalsIgnoreCase(m)) return true;
        }
        return false;
    }

    public boolean IsMember(String n) {
        for (String m : GetMembers()) {
            if (n.equalsIgnoreCase(m)) return true;
        }
        return false;
    }

    public boolean IsOfficer(String n) {
        for (String m : GetOfficers()) {
            if (n.equalsIgnoreCase(m)) return true;
        }
        return false;
    }

    public boolean IsGeneral(String n) {
        for (String m : GetGenerals()) {
            if (n.equalsIgnoreCase(m)) return true;
        }
        return false;
    }

    public boolean IsInFaction(Player player) {
        return IsInFaction(player.getName());
    }

    public boolean IsInFaction(String n) {
        for (String m : GetRecruits()) {
            if (n.equalsIgnoreCase(m)) return true;
        }
        for (String m : GetMembers()) {
            if (n.equalsIgnoreCase(m)) return true;
        }
        for (String m : GetOfficers()) {
            if (n.equalsIgnoreCase(m)) return true;
        }
        for (String m : GetGenerals()) {
            if (n.equalsIgnoreCase(m)) return true;
        }
        return n.equalsIgnoreCase(GetLeader());
    }

    public void SetDisplayName(String val) {
        DisplayName = val;
    }

    public String getDisplayName() {
        return DisplayName;
    }

    public void MessageAllys(String message) {
        BroadcastMessage(message);
        for (String ally : GetAllies()) {
            Faction af = Main.FFactory.getFaction(ally);
            if (af != null) af.BroadcastMessage(message);
        }
    }

    public String GetFactionNameTag(String p) {
        FactionRank fr = getPlayerRank(p);
        return fr.GetChatPrefix() + TextFormat.RESET + " - " + DisplayName;
    }

    public String GetFactionNameTag(Player p) {
        FactionRank fr = getPlayerRank(p);
        return fr.GetChatPrefix() + TextFormat.RESET + " - " + DisplayName;
    }

    public void BroadcastMessage(String message) {
        BroadcastMessage(message, FactionRank.All);
    }

    public FactionRank getPlayerRank(String p) {
        FactionRank rank = FactionRank.Recruit;
        if (IsMember(p)) rank = FactionRank.Member;
        if (IsOfficer(p)) rank = FactionRank.Officer;
        if (IsGeneral(p)) rank = FactionRank.General;
        if (Leader.equalsIgnoreCase(p)) rank = FactionRank.Leader;
        return rank;
    }

    public FactionRank getPlayerRank(Player p) {
        return getPlayerRank(p.getName().toLowerCase());
    }

    public FactionRank getPlayerRank(CorePlayer p) {
        return getPlayerRank((Player) p);
    }

    public void BroadcastMessage(String message, FactionRank rank) {
        switch (rank) {
            case All:
            case Recruit:
                for (String m : Recruits) {
                    Player p = Main.getServer().getPlayerExact(m);
                    if (p != null) {
                        p.sendMessage(message);
                    }
                }
//                if (rank != FactionRank.All && rank == FactionRank.Recruit) break;
            case Member:
                for (String m : Members) {
                    Player p = Main.getServer().getPlayerExact(m);
                    if (p != null) {
                        p.sendMessage(message);
                    }
                }
//                if (rank != FactionRank.All&& rank == FactionRank.Member) break;
            case Officer:
                for (String m : Officers) {
                    Player p = Main.getServer().getPlayerExact(m);
                    if (p != null) {
                        p.sendMessage(message);
                    }
                }
//                if (rank != FactionRank.All&& rank == FactionRank.Officer) break;
            case General:
                for (String m : Generals) {
                    Player p = Main.getServer().getPlayerExact(m);
                    if (p != null) {
                        p.sendMessage(message);
                    }
                }
//                if (rank != FactionRank.All&& rank == FactionRank.General) break;
            case Leader:
                Player p = Main.getServer().getPlayerExact(Leader);
                if (p != null) p.sendMessage(message);
        }
    }

    public void BroadcastPopUp(String message) {
        BroadcastPopUp(message, "");
    }
//
//    public void ResetNameTag(){
//        for(String m: Members){
//            Player p = Main.getServer().getPlayerExact(m);
//            if(p != null){
//                if(Main.CC != null)Main.CC.Setnametag(p);
//            }
//        }
//        for(String m: Officers){
//            Player p = Main.getServer().getPlayerExact(m);
//            if(p != null){
//                if(Main.CC != null)Main.CC.Setnametag(p);
//            }
//        }
//        for(String m: Generals){
//            Player p = Main.getServer().getPlayerExact(m);
//            if(p != null){
//                if(Main.CC != null)Main.CC.Setnametag(p);
//            }
//        }
//
//        for(String m: Recruits){
//            Player p = Main.getServer().getPlayerExact(m);
//            if(p != null){
//                if(Main.CC != null)Main.CC.Setnametag(p);
//            }
//        }
//        Player p = Main.getServer().getPlayerExact(Leader);
//        if(p != null && Main.CC != null)Main.CC.Setnametag(p);
//    }

    public void BroadcastPopUp(String message, String subtitle) {
        for (String m : Members) {
            Player p = Main.getServer().getPlayerExact(m);
            if (p != null) {
                p.sendPopup(message, subtitle);
            }
        }
        for (String m : Officers) {
            Player p = Main.getServer().getPlayerExact(m);
            if (p != null) {
                p.sendPopup(message, subtitle);
            }
        }
        for (String m : Generals) {
            Player p = Main.getServer().getPlayerExact(m);
            if (p != null) {
                p.sendPopup(message, subtitle);
            }
        }
        for (String m : Recruits) {
            Player p = Main.getServer().getPlayerExact(m);
            if (p != null) {
                p.sendPopup(message, subtitle);
            }
        }
        Player p = Main.getServer().getPlayerExact(Leader);
        if (p != null) p.sendPopup(message, subtitle);
    }

    public Integer GetPlayerPerm(String name) {
        FactionRank fr = getPlayerRank(name);
        return fr.getPower();
    }

    public ArrayList<Player> GetOnlinePlayers() {
        ArrayList<Player> a = new ArrayList();
        for (String m : Members) {
            Player p = Main.getServer().getPlayerExact(m);
            if (p != null) a.add(p);
        }
        for (String m : Officers) {
            Player p = Main.getServer().getPlayerExact(m);
            if (p != null) a.add(p);
        }
        for (String m : Generals) {
            Player p = Main.getServer().getPlayerExact(m);
            if (p != null) a.add(p);
        }

        for (String m : Recruits) {
            Player p = Main.getServer().getPlayerExact(m);
            if (p != null) a.add(p);
        }
        Player p = Main.getServer().getPlayerExact(Leader);
        if (p != null) a.add(p);
        return a;
    }

    public String BossBarText() {
       /*return TextFormat.GOLD+""+TextFormat.BOLD+"====§eTERRA§6TIDE===="+TextFormat.RESET+"\n\n"+
               "§6"+GetDisplayName()+" §b: §aLEVEL §b: §3"+GetLevel()+"\n"+
                "§eXP §b: §6"+GetXP()+" §a/ §b"+calculateRequireExperience(GetLevel());*/
        return TextFormat.GOLD + "" + TextFormat.BOLD + "====§eTERRA§6TIDE====" + TextFormat.RESET + "\n\n" +
                "§e" + getDisplayName() + " §b: §aLEVEL §b: §3" + GetLevel() + "\n" +
                "§eXP §b: §a" + GetXP() + " §a/ §3" + calculateRequireExperience(GetLevel());
    }

    public void UpdateBossBar() {
//        for(Player player: GetOnlinePlayers()){
//            Main.sendBossBar(player,this);
//        }
    }


    public ArrayList<String> getFChat() {
        return FChat;
    }

    public void setFChat(ArrayList<String> FChat) {
        this.FChat = FChat;
    }

    public void UpdateTopResults() {
        Main.FFactory.Top.put(getName(), GetMoney());
    }

    public void UpdateRichResults() {
        Main.FFactory.Rich.put(getName(), GetRich());
    }

    public void AddAllyRequest(Faction fac) {
        AddAllyRequest(fac, null, -1);
    }

    public void AddAllyRequest(Faction fac, CorePlayer cp) {
        AddAllyRequest(fac, cp, -1);
    }

    public ArrayList<AllyRequest> getAllyRequests() {
        Connection c = CyberCoreMain.getInstance().FM.FFactory.getMySqlConnection();
        try {
            Statement s = c.createStatement();

            ResultSet r = s.executeQuery("SELECT * FROM Requestes WHERE type LIKE '" + RequestType.Ally.Key + "' AND target = '" + getName() + "'");

            ArrayList<Integer> dellist = new ArrayList<>();
            ArrayList<AllyRequest> list = new ArrayList<>();
            while (r.next()) {
                String fn = r.getString("faction");
                Faction f = FactionsMain.getInstance().FFactory.getFaction(fn);
                if (f == null) {
                    dellist.add(r.getInt("id"));
                }
                AllyRequest ar = new AllyRequest(f, r.getInt("expires"));
                list.add(ar);

            }
            c.close();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public boolean addRequest(RequestType rt, Faction fac, CorePlayer player, int timeout, CorePlayer sender) {
        Connection c = CyberCoreMain.getInstance().FM.FFactory.getMySqlConnection();
        String sn = null;
        String pn = null;
        if (sender != null) sn = sender.getName();
        if (player != null) pn = player.getName();

        try {
            Statement s = c.createStatement();
            //1 = Ally Request
            //0 = Friend Request
            //2 = ?????
            //CyberCoreMain.getInstance().getIntTime
            switch (rt) {
                case Ally:
                    //Null,0,getname(),fac.getname(),timeout,inviter
                    s.executeQuery("INSERT INTO `Requests` VALUES (null," + rt.Key + ",'" + fac.getName() + "','" + getName() + "'," + timeout + ",'" + sn + "')");
                case Faction_Invite:
                    if (pn == null) return false;
                    //Null,1,getname(),player.getname(),timeout,inviter
                    s.executeQuery("INSERT INTO `Requests` VALUES (null," + rt.Key + ",'" + getName() + "','" + pn + "'," + timeout + ",'" + sn + "')");

                default:
            }
            c.close();
//        Main.FFactory.allyrequest.put(getName(), fac.getName());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Adds ally request to this faction
     *
     * @param fac     Faction Requesting to be an Ally
     * @param player  The Player who snet the Invite
     * @param timeout DateTimem to String when request expires
     */
    public void AddAllyRequest(Faction fac, CorePlayer player, int timeout) {
        if (!addRequest(RequestType.Ally, fac, null, timeout, player)) {
            player.sendMessage("Error sending Ally Request! Please report Error 'E332RA' to an admin");
            return;
        }

        //        Connection c = CyberCoreMain.getInstance().FM.FFactory.getMySqlConnection();
//        try {
//            Statement s = c.createStatement();
//            //1 = Ally Request
//            //0 = Friend Request
//            //2 = ?????
//            //CyberCoreMain.getInstance().getIntTime
//            s.executeQuery(String.format("INSERT INTO `Requests` VALUES (null,1,'%s',null,'%s'," + timeout + ")", fac.getName(), getName(), timeout));
//            c.close();
////        Main.FFactory.allyrequest.put(getName(), fac.getName());
//        } catch (Exception e) {
//            e.printStackTrace();
//            player.sendMessage("Error sending Ally Request! Please report Error 'E332R' to an admin");
//            return;
//        }


        BroadcastMessage(TextFormat.AQUA + "[ArchFactions] " + fac.getDisplayName() + " wants to be Ally's with you!");
//        BroadcastMessage(TextFormat.AQUA + "[ArchFactions] Respond to the request using `/f inbox`");
        player.sendMessage(TextFormat.AQUA + "[ArchFactions] Ally request sent to " + getDisplayName());

        AR.add(new AllyRequest(fac, timeout));
        FactionRank r = Settings.getAllowedToAcceptAlly();
        switch (r) {
            case Recruit:
                BroadcastMessage(fac.getDisplayName() + "'s Faction would like to become your ally! View the offer in /f inbox", FactionRank.All);
                break;
            case Member:
                BroadcastMessage(fac.getDisplayName() + "'s Faction would like to become your ally! View the offer in /f inbox", FactionRank.Member);
                break;
            case Officer:
                BroadcastMessage(fac.getDisplayName() + "'s Faction would like to become your ally! View the offer in /f inbox", FactionRank.Officer);
                break;
            case General:
                BroadcastMessage(fac.getDisplayName() + "'s Faction would like to become your ally! View the offer in /f inbox", FactionRank.General);
                break;
            case Leader:
                BroadcastMessage(fac.getDisplayName() + "'s Faction would like to become your ally! View the offer in /f inbox", FactionRank.Leader);
                break;
        }


    }

    public void AddFactionChatMessage(String message, CorePlayer p) {
        FactionRank r = getPlayerRank(p);
        message = TextFormat.GRAY + "[" + r.GetChatPrefix() + TextFormat.GRAY + "] - " + r.getChatColor() + p.getDisplayName() + TextFormat.GRAY + " > " + TextFormat.WHITE + message;
        BroadcastMessage("Faction> " + message);
        LastFactionChat.addFirst(message);
        if (LastFactionChat.size() > Settings.getMaxFactionChat()) {
            LastFactionChat.removeLast();
        }
    }

    public void AddAllyChatMessage(String message, CorePlayer p) {
        FactionRank r = getPlayerRank(p);
        message = TextFormat.GRAY + "[" + r.GetChatPrefix() + TextFormat.GRAY + "] - " + r.getChatColor() + p.getDisplayName() + TextFormat.GRAY + " > " + TextFormat.WHITE + message;
        BroadcastMessage("Ally> " + message);
        LastAllyChat.addFirst(message);
        if (LastAllyChat.size() > Settings.getMaxAllyChat()) {
            LastAllyChat.removeLast();
        }
    }

    @Deprecated
    public void save() {
        Connection c = CyberCoreMain.getInstance().FM.FFactory.getMySqlConnection();
        try {
            getServer().getLogger().error("DELETEING Faction " + fn + "!");
            Statement stmt = c.createStatement();
            stmt.executeUpdate(String.format("DELETE FROM `allies` WHERE `factiona` LIKE '%s' OR `factionb` LIKE '%s';", fn, fn));
            stmt.executeUpdate(String.format("DELETE FROM `plots` WHERE `faction` LIKE '%s';", fn));
            stmt.executeUpdate(String.format("DELETE FROM `confirm` WHERE `faction` LIKE '%s';", fn));
            stmt.executeUpdate(String.format("DELETE FROM `home` WHERE `faction` LIKE '%s';", fn));
            stmt.executeUpdate(String.format("DELETE FROM `Master` WHERE `faction` LIKE '%s';", fn));
            stmt.executeUpdate(String.format("DELETE FROM `master` WHERE `faction` LIKE '%s';", fn));
            stmt.close();
        } catch (Exception ex) {
            getServer().getLogger().info(ex.getClass().getName() + ":9 " + ex.getMessage() + " > " + ex.getStackTrace()[0].getLineNumber() + " ? " + ex.getCause());
        }


        //TODO
//        Connection c = CyberCoreMain.getInstance().FM.FFactory.getMySqlConnection();
//        try {
//            Statement s = c.createStatement();
//            //1 = Ally Request
//            //0 = Friend Request
//            //2 = ?????
//            //CyberCoreMain.getInstance().getIntTime
//            s.executeQuery(String.format("INSERT INTO `Requests` VALUES (null,1,'%s',null,'%s'," + timeout + ")", fac.getName(), getName(), timeout));
//            c.close();
////        Main.FFactory.allyrequest.put(getName(), fac.getName());
//        } catch (Exception e) {
//            e.printStackTrace();
//            player.sendMessage("Error sending Ally Request! Please report Error 'E332R' to an admin");
//            return;
//        }


    }

    public enum RequestType {
        Ally(0),
        Faction_Invite(1);

        private int Key = -1;

        RequestType(int key) {
            Key = key;
        }
    }

    public class AllyRequest {
        int Timeout = -1;
        Faction F;

        public AllyRequest(Faction f) {
            this(f, -1);
        }

        public AllyRequest(Faction f, int timeout) {
            F = f;
            Timeout = timeout;
        }
    }
}

