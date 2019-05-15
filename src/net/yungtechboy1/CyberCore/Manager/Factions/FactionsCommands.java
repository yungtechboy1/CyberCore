package net.yungtechboy1.CyberCore.Manager.Factions;

import net.yungtechboy1.CyberCore.CorePlayer;
import net.yungtechboy1.CyberCore.Manager.Factions.Cmds.*;
import cn.nukkit.utils.TextFormat;

/**
 * Created by carlt_000 on 4/10/2016.
 */
public class FactionsCommands {
    public FactionsMain Main;


    public FactionsCommands(FactionsMain main){
        Main = main;
    }


    public boolean onCommand(FactionsMain main, CorePlayer sender, String command, String[] args) {
        if(command == null){
            main.getServer().getLogger().error("HUGE ERROR ON COMMAND!!!!!");
            return false;
        }
        main.getServer().getLogger().error("COMMAND "+command);
        Faction faction = Main.FFactory.getPlayerFaction(sender);

                //if(!(sender instanceof Player))return false;
                //if(args.length > 0)sender.sendMessage("Count"+args.length+" 1:"+args[0]);
//                if(args.length == 0) {
//                    sender.sendMessage(FactionsMain.NAME+TextFormat.RED+"Please use /f help for a list of commands");
//                    return true;
//                }

                //Useage: /f c <Text>
                //Example /f c Hey Who wants to go to war!!!!
                if (((command.equalsIgnoreCase("chat") || command.equalsIgnoreCase("c")))){
                    new Chat(sender,args,main);return true;
                }

                //Clear Lag TODO FIX
                //Usage: /f cc
//                if(args.length > 0 && command.equalsIgnoreCase("cc")){
//                    if(!sender.isOp())return false;
//                    int a = 0;
//                    for (Map.Entry<Integer, Level> level : Main.getServer().getLevels().entrySet()) {
//                        for (Entity e : level.getValue().getEntities()) {
//                            if(e instanceof Player)continue;
//                            e.kill();
//                            a++;
//                        }
//                    }
//                    sender.sendMessage(a+" Entities were Killed!");
//                    //@Todo Maybe Later
//                    //Main.prefs = (new Config(Main.getDataFolder() . "Prefs.yml", CONFIG::YAML, array())).getAll();
//                    return true;
//                }

                //Usage: /f ac
                //Example: /f ac Who has diamonds?
                if (args.length > 0 && ((command.equalsIgnoreCase("allychat") || command.equalsIgnoreCase("ac")))){
                    new AllyChat(sender,args,main);return true;
                }

//TODO
//                //Wartp
//                //Usage: /f wartp
//                //Example: /f wartp
//                if(command.equalsIgnoreCase("wartp")) {
//                    new Wartp(sender,args,main);return true;
//                }
//TODO
//                //Perk
//                //Usage: /f Perk
//                //Example: /f Perk
//                if(command.equalsIgnoreCase("Perk")) {
//                    new Perk(sender,args,main);return true;
//                }
//TODO
//                //War
//                //Usage: /f war <fac>
//                //Example: /f war CyberFaction
//                if(command.equalsIgnoreCase("war")) {
//                    new War(sender, args, main);
//                    return true;
//                }
//TODO
//                //Mission
//                //Usage: /f mission
//                //Example: /f mission
//                if(command.equalsIgnoreCase("mission")) {
//                    new Mission(sender,args,main);return true;
//                }
//TODO
//                //Top
//                //Usage: /f top
//                //Example: /f top
//                if(command.equalsIgnoreCase("top")) {
//                    new Top(sender,args,main);return true;
//                }
//todo
//                //Rich
//                //Usage: /f Rich
//                //Example: /f Rich
//                if(command.equalsIgnoreCase("rich")) {
//                    new Rich(sender,args,main);return true;
//                }

                //Balance
                //Usage: /f Balance
                //Example: /f Balance
                if(command.equalsIgnoreCase("balance")) {
                    new Balance(sender,args,main);return true;
                }

                //F Ally
                if(command.equalsIgnoreCase("ally")) {
                    new Ally(sender,args,main);return true;
                }

                //Join
                //Usage: /f Join <fac>
                //Example: /f Join CyberFaction
                if(command.equalsIgnoreCase("join")) {
                    new Join(sender,args,main);return true;
                }
                //Settings
                //Usage: /f motd <Desc>
                //Example: /f motd CyberFaction Is Awesome
                if(command.equalsIgnoreCase("motd")) {
                    new Settings(sender,args,main);return true;
                }

                //Desc
                //Usage: /f desc <Desc>
                //Example: /f desc CyberFaction Is Awesome
                if(command.equalsIgnoreCase("desc")) {
                    new Desc(sender,args,main);return true;
                }

                //Create
                //Usage: /f create <Faction Name>
                //Example: /f create CyberFaction
                if(command.equalsIgnoreCase("create")) {
                    new Create(sender,args,main);return true;
                }

                //TODO
                //Map
                //Usage: /f Map
                //Example: /f Map
//                if(command.equalsIgnoreCase("map")) {
//                    new net.yungtechboy1.CyberCore.Manager.Factions.Cmds.Map(sender,args,main);return true;
//                }

                //Privacy
                //Usage: /f privacy <on\off>
                //Example: /f privacy on
                if(command.equalsIgnoreCase("privacy")){
                    new Privacy(sender,args,main);return true;
                }
                //List
                //Usage: /f privacy <on\off>
                //Example: /f privacy on
                if(command.equalsIgnoreCase("list")){
                    new List(sender,args,main);return true;
                }
                //Deposit
                //Usage: /f privacy <on\off>
                //Example: /f privacy on
                if(command.equalsIgnoreCase("deposit")){
                    new Deposit(sender,args,main);return true;
                }
                //Withdraw
                //Usage: /f privacy <on\off>
                //Example: /f privacy on
                if(command.equalsIgnoreCase("withdraw")){
                    new Withdraw(sender,args,main);return true;
                }
                //Invite
                //Usage: /f invite <player>
                //Example: /f invite yungt
                if(command.equalsIgnoreCase("invite") || command.equalsIgnoreCase("inv")) {
                    new Invite(sender,args,main);return true;
                }

                //Leader
                if(command.equalsIgnoreCase("leader")) {
                    new Leader(sender,args,main);return true;
                }

                //Promote
                //Usage: /f leader <player>
                //Example: /f leader yungt
                if(command.equalsIgnoreCase("promote")) {
                    new Promote(sender,args,main);return true;
                }

                //Demote
                //Usage: /f demote <player>
                //Example: /f demote yungt
                if(command.equalsIgnoreCase("demote")) {
                    new Demote(sender,args,main);return true;
                }

                //Kick
                //Usage: /f kick <player>
                //Example: /f kick yungt
                if(command.equalsIgnoreCase("kick")) {
                    new Kick(sender,args,main);return true;
                }

                //Home
                //Usage: /f home [Ally Faction]
                //Example: /f home
                if(command.equalsIgnoreCase("home")) {
                    new Home(sender,args,main);return true;
                }

                //Info
                //Usage: /f info [Faction]
                //Example: /f info
                if(command.equalsIgnoreCase("info")) {
                    new Info(sender,args,main);return true;
                }

                //@todo War TP! Fix
                /*
                if (command.equalsIgnoreCase("wartp")){
                    String faction = Main.getPlayerFaction(sender.getName());
                    if (faction == null){
                        sender.sendMessage(TextFormat.RED+"You must be in faction to use this command!!!");
                        return true;
                    }
                    if (Main.war.containsKey(faction)){
                        sender.sendMessage(TextFormat.RED+"You are Not At War or Attacking!!!");
                        return true;
                    }
                    Vector3 pos = Main.GetRandomTPArea(Main.war.get(faction), 7, ((Player) sender).getLevel());
                    if (pos != null){
                        ((Player) sender).teleport(pos);
                        sender.sendMessage(TextFormat.GREEN+"Teleported To War Zone!");
                    }else{
                        String ef = Main.war.get(faction);
                        Player tp = Main.GetRandomFactionPlayer(ef);
                        if(tp == null){
                            sender.sendMessage(TextFormat.GRAY+"Error With TPing! "+ef+" Has No Plot and No One Is Online!");
                            return true;
                        }
                        ((Player) sender).teleport(tp);
                        sender.sendMessage(TextFormat.GREEN+"Teleported To War Zone!");
                    }
                    return true;
                }*/

                if (command.equalsIgnoreCase("power")){
                    new Power(sender,args,main);return true;
                }

                //claim
                //Usage: /f claim
                //Example: /f claim
                //@todo Add Auto Claim and Claim Radius
                if(command.equalsIgnoreCase("claim")) {
                    new Claim(sender,args,main);return true;
                }

                //unclaim
                //Usage: /f unclaim
                //Example: /f unclaim
                //@todo Add Auto unclaim and Claim Radius
                if(command.equalsIgnoreCase("unclaim")) {
                    new Unclaim(sender,args,main);return true;
                }

                //overclaim
                //Usage: /f overclaim
                //Example: /f overclaim
                //@todo Add Auto unclaim and Claim Radius
                if(command.equalsIgnoreCase("overclaim")) {
                    new OverClaim(sender,args,main);return true;
                }

                //admin
                //Usage: /f admin
                //Example: /f admin
                //@todo Add Auto unclaim and Claim Radius
                if(command.equalsIgnoreCase("admin")) {
                    String gar = null;
                    if(sender.isOp()) {
                        new Admin(sender, args, main);
                        return true;
                    }else{
                        sender.sendMessage(FactionsMain.NAME+TextFormat.RED+"Error! You dont have access to this command!");
                        return true;
                    }
                }

                //Accept
                //Usage: /f accept
                if(command.equalsIgnoreCase("accept")) {
                    new Accept(sender,args,main);return true;
                }

                //Deny
                //Usage: /f Deny
                if(command.equalsIgnoreCase("deny")) {
                    new Deny(sender,args,main);return true;
                }

                //Delete
                //Usage: /f del
                if(command.equalsIgnoreCase("del") || command.equalsIgnoreCase("delete")) {
                    new Delete(sender,args,main);return true;
                }

                //Leave
                //Usage: /f del
                //@todo Add Reasons.... IE /f leave Bye Bitches!
                if(command.equalsIgnoreCase("leave")) {
                    new Leave(sender,args,main);return true;
                }

                //sethome
                //Usage: /f sethome
                if(command.equalsIgnoreCase("sethome")) {
                    new Sethome(sender,args,main);return true;
                }
/*
                if(command.equalsIgnoreCase("unsethome")) {
                    new uns(sender,args,main);return true;
                }*/

                if(command.equalsIgnoreCase("help")) {
                    new Help(sender,args,main);return true;
                }
                sender.sendMessage(FactionsMain.NAME+TextFormat.GRAY+"Please use /f help for a list of commands");
                return true;
    }
}
