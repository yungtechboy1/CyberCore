package net.yungtechboy1.CyberCore.Manager.Factions;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.command.CommandSender;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.block.BlockPlaceEvent;
import cn.nukkit.event.player.PlayerDeathEvent;
import cn.nukkit.item.ItemBlock;
import cn.nukkit.level.Level;
import cn.nukkit.math.Vector3;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.ConfigSection;
import cn.nukkit.utils.TextFormat;
import com.sun.xml.internal.ws.client.SenderException;
import main.java.CyberFactions.Mission.ActiveMission;
import main.java.CyberFactions.Mission.Mission;
import main.java.CyberFactions.Tasks.FactionRichAsyncSingle;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;

/**
 * Created by carlt_000 on 5/13/2016.
 */
////@TODO Make this so I can Call a Faction Class....
public class Faction {
    private FactionsMain Main;
    private String Name;
    private String DisplayName;
    public String Leader;
    public ArrayList<String> Recruits;
    public ArrayList<String> Members;
    public ArrayList<String> Officers;
    public ArrayList<String> Generals;
    private String MOTD;
    private String Desc;
    private ArrayList<String> FChat = new ArrayList<>();
    private ArrayList<String> FAlly = new ArrayList<>();
    private ArrayList<String> Plots = new ArrayList<>();
    private ArrayList<String> Allies = new ArrayList<>();
    private ArrayList<String> Enemies = new ArrayList<>();
    private String War = null;
    private Map<String, Integer> Invites = new HashMap<>();
    private Integer MaxPlayers = 15;
    private Integer PowerBonus = 1;
    private Integer Privacy = 0;
    private Integer Perms = 0;
    private Integer Power = 0;
    private Integer Rich = 0;

    private Integer Points = 0;
    private Integer XP = 0;
    private Integer Level = 0;

    private ArrayList<Integer> CompletedMissionIDs = new ArrayList<>();

    public ActiveMission AM = null;

    private Integer Money = 0;
    private Vector3 Home = new Vector3(0,0,0);

    public Faction(FactionsMain main, String name, String displayname,String leader, ArrayList<String> members,ArrayList<String> generals,ArrayList<String> officers,ArrayList<String> recruits){
        Main = main;
        Name = name;
        DisplayName = displayname;
        Leader = leader;
        Members = members;
        Recruits = recruits;
        Generals = generals;
        Officers = officers;
        for(String m: Members){
            Player p = Main.getServer().getPlayerExact(m);
            if(p != null){
                Main.FFactory.FacList.put(p.getName().toLowerCase(),Name);
            }
        }
        for(String m: Officers){
            Player p = Main.getServer().getPlayerExact(m);
            if(p != null){
                Main.FFactory.FacList.put(p.getName().toLowerCase(),Name);
            }
        }
        for(String m: Generals){
            Player p = Main.getServer().getPlayerExact(m);
            if(p != null){
                Main.FFactory.FacList.put(p.getName().toLowerCase(),Name);
            }
        }

        for(String m: Recruits){
            Player p = Main.getServer().getPlayerExact(m);
            if(p != null){
                Main.FFactory.FacList.put(p.getName().toLowerCase(),Name);
            }
        }
        Player p = Main.getServer().getPlayerExact(Leader);
        if(p != null)Main.FFactory.FacList.put(p.getName().toLowerCase(),Name);
    }

    //@todo LMAO THIS IS NOT EVEN CLOSE TO being correcty XD
    public String toString(){
        return GetName();
    }

    public void SetPlots(ArrayList<String> value){Plots = value;}
    public ArrayList<String> GetPlots(){return Plots;}
    public void AddPlots(String plot){Plots.add(plot);}
    public void DelPlots(String plot){Plots.remove(plot);}

    public void SetMaxPlayers(Integer value){MaxPlayers = value;}
    public Integer GetMaxPlayers(){return MaxPlayers;}

    public void SetPowerBonus(Integer value){PowerBonus = value;}
    public Integer GetPowerBonus(){return PowerBonus;}
    public Integer CalculateMaxPower(){
        Integer TP = GetNumberOfPlayers();
        return TP*10;
        //Lets do 20 Instead of 10
    }

    public Integer GetNumberOfPlayers(){
        return GetGenerals().size() + GetOfficers().size() + GetMembers().size() + 1;
    }

    public void SetPrivacy(Integer value){Privacy = value;}
    public Integer GetPrivacy(){return Privacy;}

    public String GetName(){return Name;}

    public void SetDesc(String value){Desc = value;}
    public String GetDesc(){return Desc;}


    public void SetMOTD(String value){MOTD = value;}
    public String GetMOTD(){return MOTD;}

    public void SetPerm(Integer value){
        if(value == null)value = 0;
        Perms = value;
    }
    public Integer GetPerm(){return Perms;}
    public Integer GetPerm(Integer key){
        try {
            return Integer.parseInt(Perms.toString().substring(key));
        }catch (Exception ignore){
            return null;
        }
    }

    public void SetPoints(Integer value){Points = value;}
    public Integer GetPoints(){return Points;}
    public void AddPoints(Integer pts){
        SetPoints(GetPoints() + Math.abs(pts));
    }
    public void TakePoints(Integer pts){
        Integer a = GetPoints() - pts;
        if(a < 0)SetPoints(0);
        SetPoints(a);
    }

    public void SetLevel(Integer value){
        Level = value;
        UpdateBossBar();
    }
    public Integer GetLevel(){return Level;}
    public void AddLevel(Integer lvl){
        SetLevel(GetLevel() + Math.abs(lvl));
    }
    public void TakeLevel(Integer lvl){
        Integer a = GetLevel() - lvl;
        if(a < 0)SetLevel(0);
        SetLevel(a);
    }

    public int calculateRequireExperience(int level) {
        if (level >= 30) {
            return 112 + (level - 30) * 9* 100;
        } else if (level >= 15) {
            return 37 + (level - 15) * 5* 100;
        } else {
            return 7 + level * 2 * 100;
        }
    }
    public void CalculateXP(){
        int xp = GetXP();
        int lvl = GetLevel();
        while(xp >= calculateRequireExperience(lvl)){
            xp = xp - calculateRequireExperience(lvl);
            lvl++;
        }
        SetXP(xp);
        SetLevel(lvl);
    }
    public void AddXP (int add){
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
    public void SetXP(Integer value){
        XP = value;
        UpdateBossBar();
     }
    public void SetXPCalculate(Integer value){
        int level = GetLevel();
        int most = calculateRequireExperience(level);
        while (value >= most) {  //Level Up!
            value = value - most;
            most = calculateRequireExperience(++level);
        }
        SetXP(value);
        SetLevel(level);
     }
    public Integer GetXPPercent(){
        Double d = ((XP/(double)calculateRequireExperience(GetLevel()))*100);
        return d.intValue();}
    public Integer GetXP(){return XP;}
    public boolean TakeXP(Integer xp){
        int x = GetXP();
        int lvl = GetLevel();
        while(x < xp){
            if (lvl == 0)return false;
            xp += calculateRequireExperience(--lvl);
        }
        Integer a = x - xp;
        SetXP(a);
        SetLevel(lvl);
        return true;
    }


    public void HandleKillEvent(PlayerDeathEvent event){
        if(GetActiveMission() != null){
            GetActiveMission().AddKill();
        }
    }
    public void HandleBreakEvent(BlockBreakEvent event){
        if(GetActiveMission() != null){
            GetActiveMission().BreakBlock(event);
        }
    }
    public void HandlePlaceEvent(BlockPlaceEvent event){
        if(GetActiveMission() != null){
            GetActiveMission().PlaceBlock(event);
        }
    }
    public void SetActiveMission(String id){
        if(id == null || id.equalsIgnoreCase("")){
            SetActiveMission();
        }else {
            SetActiveMission(Integer.parseInt(id));
        }
    }
    public void AcceptNewMission(Integer id, CommandSender Sender){
        if(GetActiveMission() != null){
            Sender.sendMessage(FactionsMain.NAME+TextFormat.RED+"Error you already have a mission!!");
            return;
        }
        if(CompletedMissionIDs.contains(id)){
            Sender.sendMessage(FactionsMain.NAME+TextFormat.RED+"Error you have already completed this mission!!");
            return;
        }
        SetActiveMission(id);
    }
    public void SetActiveMission(Integer id){
        for(Mission mission: Main.Missions){
            if(mission.id.equals(id)) {
                SetActiveMission(new ActiveMission(Main,this,mission));
                BroadcastMessage(FactionsMain.NAME+TextFormat.AQUA+mission.name+TextFormat.GREEN+" Faction mission accepted!");
            }
        }
    }
    public void RetrieveActiveMission(String id){
        if(id == null || id.equalsIgnoreCase("")){
            SetActiveMission();
        }else {
            RetrieveActiveMission(Integer.parseInt(id));
        }
    }
    public void RetrieveActiveMission(Integer id){
        if(Main.AM.exists(GetName())){
            for(Mission mission: Main.Missions){
                if(mission.id.equals(id)){
                    try {
                        /*YamlConfig yamlConfig = new YamlConfig();
                        yamlConfig.setClassTag("ActiveMission",ActiveMission.class);
                        yamlConfig.setClassTag("tag:yaml.org,2002:cn.nukkit.item.ItemBlock",ItemBlock.class);
                        YamlReader reader = new YamlReader(new FileReader(Main.getDataFolder().toString()+"/missions/"+GetName()+".yml"),yamlConfig);
                        ActiveMission activeMission = reader.read(ActiveMission.class);
                        System.out.println(activeMission.name)*/;
                        ActiveMission activeMission = new ActiveMission(Main, this,(ConfigSection) Main.AM.get(GetName()));
                        SetActiveMission(activeMission);
                        //SetActiveMission(new ActiveMission(Main,this,mission));
                        BroadcastMessage(FactionsMain.NAME+TextFormat.AQUA+mission.name+TextFormat.GREEN+" Faction mission accepted!");
                        return;
                    }catch(Exception ex){
                        ex.printStackTrace();
                    }
                }
            }
        }
        SetActiveMission();
    }
    public void SetActiveMission(){
        AM = null;
    }
    public void SetActiveMission(ActiveMission mission){
        AM = mission;
    }
    public ActiveMission GetActiveMission(){
        return AM;
    }
    public void CompleteMission(ActiveMission mission){
        CompletedMissionIDs.add(mission.id);
        AM = null;
    }
    public void SetCompletedMissisons(ArrayList<Integer> value){CompletedMissionIDs = value;}
    public ArrayList<Integer> GetCompletedMissions(){return CompletedMissionIDs;}
    public void AddCompletedMission(Integer mission){
        CompletedMissionIDs.add(mission);
    }

    public void SetMoney(Integer value){Money = value;UpdateTopResults();}
    public Integer GetMoney(){return Money;}
    public void AddMoney(Integer money){
        SetMoney(GetMoney() + Math.abs(money));
    }
    public void TakeMoney( Integer money){
        Integer a = GetMoney() - money;
        if(a < 0)SetMoney(0);
        SetMoney(a);
    }

    public Integer GetRich(){
        return Rich+GetMoney();
    }
    public void SetRich(Integer rich){
        Rich = rich;
    }
    public void CalcualteRich(){
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

    public Integer GetMaxPower(){return CalculateMaxPower();}
    public void SetPower(Integer value){
        Integer dif = value - GetPower();
        String t = "";
        if(dif > 0){
            t = TextFormat.GREEN+"Gained +"+dif;
        }else{
            t = TextFormat.RED+"Lost -"+ Math.abs(dif);
        }
        BroadcastPopUp(TextFormat.GRAY+"Faction now has "+TextFormat.GREEN+value+TextFormat.GRAY+" Power!"+t);
        Power = value;
    }
    public Integer GetPower(){return Power;}
    public void AddPower(Integer power){
        Integer t = GetPower() + Math.abs(power);
        if(t > CalculateMaxPower()){
            SetPower(CalculateMaxPower());
        }else {
            SetPower(t);
        }
    }
    public void TakePower( Integer power){
        Integer a = GetPower() - power;
        if(a < 0){
            SetPower(0);
        }else {
            SetPower(a);
        }
    }

    public Vector3 GetHome(){return Home;}
    public void SetHome(Integer x, Integer y, Integer z){SetHome(new Vector3(x,y,z));}
    public void SetHome(Vector3 pos){Home = pos;}

    public void StartWar(String key) {
        War = key;
    }
    public void EndWar(){War = null;}
    public ConfigSection GetWarData(){
        if (War != null && Main.War.containsKey(War)){
            return (ConfigSection)Main.War.get(War);
        }
        return null;
    }
    public Boolean AtWar(){
        if(War != null)return true;
        return false;
    }
    public Boolean AtWar(String fac){
        if(War != null){
            if(((ConfigSection)Main.War.get(War)).getString("defenders").equalsIgnoreCase(fac)){
                return true;
            }
        }
        return false;
    }

    public void AddCooldown(Integer secs){
        Map<String, Object> cd = Main.CD.getAll();
        int time = (int)(Calendar.getInstance().getTime().getTime()/1000);
        cd.put(GetName(),time+secs);
    }
    public boolean HasWarCooldown(){
        Map<String, Object> cd = Main.CD.getAll();
        int time = (int)(Calendar.getInstance().getTime().getTime()/1000);
        if (cd.containsKey(GetName())){
            if (time >= (int)cd.get(GetName())){
                cd.remove(GetName());
                return false;
            }
            return true;
        }
        return false;
    }

    public void SetEnemies(ArrayList<String> list){Enemies = list;}
    public void AddEnemy(String fac){Enemies.add(fac);}
    public void RemoveEnemy(String fac){Enemies.remove(fac);}
    public ArrayList<String> GetEnemies(){return Enemies;}
    public Boolean isEnemy(String fac){
        if(Enemies.contains(fac.toLowerCase()))return true;
        return false;
    }

    public void SetAllies(ArrayList<String> list){Allies = list;}
    public void AddAlly(String fac){Allies.add(fac);}
    public void RemoveAlly(String fac){Allies.remove(fac);}
    public ArrayList<String> GetAllies(){return Allies;}
    public Boolean isAllied(String fac){
        if(Allies.contains(fac.toLowerCase()))return true;
        return false;
    }

    public void SetInvite(Map<String, Integer> Invs){Invites = Invs;}
    public Map<String, Integer> GetInvite(){return Invites;}
    public void AddInvite(String Key, Integer Value ){Invites.put(Key,Value);}
    public void DelInvite(String Key){Invites.remove(Key);}
    public Boolean AcceptInvite(String name){
        if(Invites.get(name) > (int)(Calendar.getInstance().getTime().getTime()/1000)){
            Members.add(name.toLowerCase());
            DelInvite(name);
            return true;
        }
        DelInvite(name);
        return false;
    }
    public void DenyInvite(String name){
        DelInvite(name);
    }
    public boolean HasInvite(String name){
        return Invites.containsKey(name);
    }

    public void SetMembers(ArrayList<String> members){Members = members;}
    public void SetOfficers(ArrayList<String> members){Officers = members;}
    public void SetGenerals(ArrayList<String> members){Generals = members;}
    public void SetRecruits(ArrayList<String> members){Recruits = members;}
    public void SetLeader(String leader){Leader = leader;}
    public ArrayList<String> GetMembers(){return Members;}
    public ArrayList<String> GetOfficers(){return Officers;}
    public ArrayList<String> GetGenerals(){return Generals;}
    public ArrayList<String> GetRecruits(){return Recruits;}
    public String GetLeader(){return Leader;}
    public void AddMember(String name){Members.add(name);}
    public void AddOfficer(String name){Officers.add(name);}
    public void AddGeneral(String name){Generals.add(name);}
    public void AddRecruit(String name){Recruits.add(name);}
    public void DelMember(String name){Members.remove(name);}
    public void DelOfficer(String name){Officers.remove(name);}
    public void DelGeneral(String name){Generals.remove(name);}
    public void DelRecruit(String name){Recruits.remove(name);}


    public boolean IsMember(Player p){return IsMember(p.getName());}
    public boolean IsOfficer(Player p){return IsOfficer(p.getName());}
    public boolean IsGeneral(Player p){return IsGeneral(p.getName());}
    public boolean IsRecruit(Player p){return IsRecruit(p.getName());}
    public boolean IsRecruit(String n){
        for(String m: GetRecruits()){
            if(n.equalsIgnoreCase(m))return true;
        }
        return false;
    }
    public boolean IsMember(String n){
        for(String m: GetMembers()){
            if(n.equalsIgnoreCase(m))return true;
        }
        return false;
    }
    public boolean IsOfficer(String n){
        for(String m: GetOfficers()){
            if(n.equalsIgnoreCase(m))return true;
        }
        return false;
    }
    public boolean IsGeneral(String n){
        for(String m: GetGenerals()){
            if(n.equalsIgnoreCase(m))return true;
        }
        return false;
    }

    public boolean IsInFaction(Player player){
        return IsInFaction(player.getName());
    }
    public boolean IsInFaction(String n){
        for(String m: GetRecruits()){
            if(n.equalsIgnoreCase(m))return true;
        }
        for(String m: GetMembers()){
            if(n.equalsIgnoreCase(m))return true;
        }
        for(String m: GetOfficers()){
            if(n.equalsIgnoreCase(m))return true;
        }
        for(String m: GetGenerals()){
            if(n.equalsIgnoreCase(m))return true;
        }
        return n.equalsIgnoreCase(GetLeader());
    }


    public void SetDisplayName(String val){DisplayName = val;}
    public String GetDisplayName(){return DisplayName;}

    public void MessageAllys(String message){
        BroadcastMessage(message);
        for(String ally: GetAllies()){
            Faction af = Main.FFactory.getFaction(ally);
            if(af != null)af.BroadcastMessage(message);
        }
    }


    public String GetFactionNameTag(String p){
        String prefix = "R";
        if(IsMember(p))prefix = "M";
        if(IsOfficer(p))prefix = "O";
        if(IsGeneral(p))prefix = "G";
        if(Leader.equalsIgnoreCase(p))prefix = "L";
        return prefix+"-"+DisplayName;
    }
    public String GetFactionNameTag(Player p){
        String prefix = "R";
        if(IsMember(p))prefix = "M";
        if(IsOfficer(p))prefix = "O";
        if(IsGeneral(p))prefix = "G";
        if(Leader.equalsIgnoreCase(p.getName()))prefix = "L";
        return prefix+"-"+DisplayName;
    }

    public void BroadcastMessage(String message){
        for(String m: Members){
            Player p = Main.getServer().getPlayerExact(m);
            if(p != null){
                p.sendMessage(message);
            }
        }
        for(String m: Officers){
            Player p = Main.getServer().getPlayerExact(m);
            if(p != null){
                p.sendMessage(message);
            }
        }
        for(String m: Generals){
            Player p = Main.getServer().getPlayerExact(m);
            if(p != null){
                p.sendMessage(message);
            }
        }

        for(String m: Recruits){
            Player p = Main.getServer().getPlayerExact(m);
            if(p != null){
                p.sendMessage(message);
            }
        }
        Player p = Main.getServer().getPlayerExact(Leader);
        if(p != null)p.sendMessage(message);
    }

    public void ResetNameTag(){
        for(String m: Members){
            Player p = Main.getServer().getPlayerExact(m);
            if(p != null){
                if(Main.CC != null)Main.CC.Setnametag(p);
            }
        }
        for(String m: Officers){
            Player p = Main.getServer().getPlayerExact(m);
            if(p != null){
                if(Main.CC != null)Main.CC.Setnametag(p);
            }
        }
        for(String m: Generals){
            Player p = Main.getServer().getPlayerExact(m);
            if(p != null){
                if(Main.CC != null)Main.CC.Setnametag(p);
            }
        }

        for(String m: Recruits){
            Player p = Main.getServer().getPlayerExact(m);
            if(p != null){
                if(Main.CC != null)Main.CC.Setnametag(p);
            }
        }
        Player p = Main.getServer().getPlayerExact(Leader);
        if(p != null && Main.CC != null)Main.CC.Setnametag(p);
    }

    public void BroadcastPopUp(String message){
        BroadcastPopUp(message, "");
    }
    public void BroadcastPopUp(String message, String subtitle){
        for(String m: Members){
            Player p = Main.getServer().getPlayerExact(m);
            if(p != null){
                p.sendPopup(message,subtitle);
            }
        }
        for(String m: Officers){
            Player p = Main.getServer().getPlayerExact(m);
            if(p != null){
                p.sendPopup(message,subtitle);
            }
        }
        for(String m: Generals){
            Player p = Main.getServer().getPlayerExact(m);
            if(p != null){
                p.sendPopup(message,subtitle);
            }
        }
        for(String m: Recruits){
            Player p = Main.getServer().getPlayerExact(m);
            if(p != null){
                p.sendPopup(message,subtitle);
            }
        }
        Player p = Main.getServer().getPlayerExact(Leader);
        if(p != null)p.sendPopup(message,subtitle);
    }

    public Integer GetPlayerPerm(String name){
        for(String player: GetRecruits())if(player.equalsIgnoreCase(name))return 1;
        for(String player: GetMembers())if(player.equalsIgnoreCase(name))return 2;
        for(String player: GetOfficers())if(player.equalsIgnoreCase(name))return 3;
        for(String player: GetGenerals())if(player.equalsIgnoreCase(name))return 4;
        if(GetLeader().equalsIgnoreCase(name))return 5;
        return 0;
    }

    public ArrayList<Player> GetOnlinePlayers() {
        ArrayList<Player> a = new ArrayList();
        for(String m: Members){
            Player p = Main.getServer().getPlayerExact(m);
            if(p != null)a.add(p);
        }
        for(String m: Officers){
            Player p = Main.getServer().getPlayerExact(m);
            if(p != null)a.add(p);
        }
        for(String m: Generals){
            Player p = Main.getServer().getPlayerExact(m);
            if(p != null)a.add(p);
        }

        for(String m: Recruits){
            Player p = Main.getServer().getPlayerExact(m);
            if(p != null)a.add(p);
        }
        Player p = Main.getServer().getPlayerExact(Leader);
        if(p != null)a.add(p);
        return a;
    }

    public String BossBarText(){
       /*return TextFormat.GOLD+""+TextFormat.BOLD+"====§eTERRA§6TIDE===="+TextFormat.RESET+"\n\n"+
               "§6"+GetDisplayName()+" §b: §aLEVEL §b: §3"+GetLevel()+"\n"+
                "§eXP §b: §6"+GetXP()+" §a/ §b"+calculateRequireExperience(GetLevel());*/
       return TextFormat.GOLD+""+TextFormat.BOLD+"====§eTERRA§6TIDE===="+TextFormat.RESET+"\n\n"+
               "§e"+GetDisplayName()+" §b: §aLEVEL §b: §3"+GetLevel()+"\n"+
                "§eXP §b: §a"+GetXP()+" §a/ §3"+calculateRequireExperience(GetLevel());
    }

    public void UpdateBossBar(){
        for(Player player: GetOnlinePlayers()){
            Main.sendBossBar(player,this);
        }
    }

    public ArrayList<String> getFAlly() {
        return FAlly;
    }
    public ArrayList<String> getFChat() {
        return FChat;
    }
    public void setFAlly(ArrayList<String> FAlly) {
        this.FAlly = FAlly;
    }
    public void setFChat(ArrayList<String> FChat) {
        this.FChat = FChat;
    }

    public void UpdateTopResults(){
        Main.FFactory.Top.put(GetName(),GetMoney());
    }
    public void UpdateRichResults(){
        Main.FFactory.Rich.put(GetName(),GetRich());
    }
}
