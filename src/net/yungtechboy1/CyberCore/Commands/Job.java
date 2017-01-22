package net.yungtechboy1.CyberCore.Commands;

import cn.nukkit.command.CommandSender;
import net.yungtechboy1.CyberCore.CyberCoreMain;

/**
 * Created by carlt_000 on 3/21/2016.
 */

public class Job {
    CyberCoreMain Owner;
    public void Job(CyberCoreMain server){
        Owner = server;
    }

    public static void runCommand(CommandSender s,String[] args, CyberCoreMain server){

        /*if(s instanceof Player){
            Player p = (Player)s;
            if(args.length >= 1){
                if(args[0] == "list"){
                    s.sendMessage("Possible Jobs: Lumberjack, Planter, Warrior");
                }else if(args[0] == "join" && args.length == 2){
                    String job = args[1];
                    if(job.equalsIgnoreCase("Lumberjack")){
                        SetJob(p.getName().toLowerCase(),"LumberJack|0|0|50",server);
                    }else if(job.equalsIgnoreCase("Planter")){
                        SetJob(p.getName().toLowerCase(),"Planter|0|0|50",server);
                    }else if(job.equalsIgnoreCase("Warrior")){
                        SetJob(p.getName().toLowerCase(),"Warrior|0|0|50",server);
                    }
                    s.sendMessage(TextFormat.GREEN+"Job Set!");
                }else if(args[0] == "quit"){
                    server.job.remove(s.getName().toLowerCase());
                    s.sendMessage(TextFormat.GREEN+"You quit your Job!");
                }else if(args[0] == "info" && args.length == 2){
                    String job = ((String)server.job.get(s.getName().toLowerCase())).split("|")[0];
                    String lvl = ((String)server.job.get(s.getName().toLowerCase())).split("|")[1];
                    s.sendMessage(TextFormat.GREEN+"You quit your Job is "+job+" And are at level: "+lvl+"!");
                }else if(args[0] == "level" && args.length == 2){
                    String lvl = ((String)server.job.get(s.getName().toLowerCase())).split("|")[2];
                    String mxlvl = ((String)server.job.get(s.getName().toLowerCase())).split("|")[3];
                    s.sendMessage(TextFormat.GREEN+"You Job Level "+lvl+" Of "+mxlvl+"!");
                    s.sendMessage(TextFormat.YELLOW+"Once your level have reached the max level do /f levelup");
                }else if(args[0] == "levelup" && args.length == 2){
                    Integer lvl = Integer.parseInt(((String)server.job.get(s.getName().toLowerCase())).split("|")[2]);
                    Integer mxlvl = Integer.parseInt(((String)server.job.get(s.getName().toLowerCase())).split("|")[3]);
                    float percent = ((float) lvl) / mxlvl;
                    if(lvl >= mxlvl){
                        String job = ((String)server.job.get(s.getName().toLowerCase())).split("|")[0];
                        Integer llvl = Integer.parseInt(((String)server.job.get(s.getName().toLowerCase())).split("|")[1]);
                        llvl++;
                        Integer mp = (llvl*50) + 50;
                        SetJob(p.getName().toLowerCase(),job+"|"+llvl+"|0|"+mp,server);
                        s.sendMessage(TextFormat.GREEN+"Congradulations! Your job "+job+" has been upgraded to Level "+llvl+"!");
                    }else{
                        s.sendMessage(TextFormat.RED+"Error! Your only at "+percent+"% our of 100%!");
                    }
                }else{
                    s.sendMessage(TextFormat.GRAY+"----------------------------------------\n"+
                            TextFormat.AQUA+"--/job list "+TextFormat.GRAY+"-"+TextFormat.GREEN+" Show All Job List\n"+
                            TextFormat.AQUA+"--/job join "+TextFormat.GRAY+"-"+TextFormat.GREEN+" Join Job By Name\n"+
                            TextFormat.AQUA+"--/job quit "+TextFormat.GRAY+"-"+TextFormat.GREEN+" Quit Current Job\n"+
                            TextFormat.AQUA+"--/job info "+TextFormat.GRAY+"-"+TextFormat.GREEN+" Show Job Info\n"+
                            TextFormat.AQUA+"--/job level "+TextFormat.GRAY+"-"+TextFormat.GREEN+" Show Job Level\n"+
                            TextFormat.AQUA+"--/job levelup "+TextFormat.GRAY+"-"+TextFormat.GREEN+" Show Job Level\n"+
                            TextFormat.GRAY+"----------------------------------------"
                    );
                }
            }else{
                s.sendMessage(TextFormat.GRAY+"----------------------------------------\n"+
                              TextFormat.AQUA+"--/job list "+TextFormat.GRAY+"-"+TextFormat.GREEN+" Show All Job List\n"+
                              TextFormat.AQUA+"--/job join "+TextFormat.GRAY+"-"+TextFormat.GREEN+" Join Job By Name\n"+
                              TextFormat.AQUA+"--/job quit "+TextFormat.GRAY+"-"+TextFormat.GREEN+" Quit Current Job\n"+
                              TextFormat.AQUA+"--/job info "+TextFormat.GRAY+"-"+TextFormat.GREEN+" Show Job Info\n"+
                              TextFormat.AQUA+"--/job level "+TextFormat.GRAY+"-"+TextFormat.GREEN+" Show Job Level\n"+
                              TextFormat.AQUA+"--/job levelup "+TextFormat.GRAY+"-"+TextFormat.GREEN+" Show Job Level\n"+
                              TextFormat.GRAY+"----------------------------------------"
                );
            }
        } else {
            s.sendMessage(Messages.NEED_TO_BE_PLAYER);
        }*/
    }

    public String GetJob(String name,CyberCoreMain Server){
        if(Server.job.exists(name.toLowerCase())){
            return (String)Server.job.get(name.toLowerCase());
        }else{
            return null;
        }
    }

    public static void SetJob(String name,String Key,CyberCoreMain Server){
        Server.job.set(name.toLowerCase(),Key);
    }
}