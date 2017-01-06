package net.yungtechboy1.CyberCore;

import cn.nukkit.utils.TextFormat;

import javax.xml.soap.Text;

/**
 * Created by carlt_000 on 3/21/2016.
 */
public class Msgs {

    //Genral Strings
    public static String NO_PERM = TextFormat.RED+"You do not have enough permissionsn to do so.";
    public static String NEED_TO_BE_PLAYER = TextFormat.RED+"You have to be a player to use this command!";
    public static String PLAYER_NOT_FOUND = TextFormat.RED+"Player not Found! Please Try Again!";

    //setspawn commands
    public static String SETSPAWN_SUCCESS = "§aSuccessfully setted spawn point to current location!";

    //spawn commands
    public static String SPAWN_CONSOLE = "§eSpawnPoint location: §6{loc}";
    public static String SPAWN_TELEPORTING = "§eTeleporting to Spawn....";

    //TP commands
    public static String TPA_SENT = "§aSent request to §6{0}";
    public static String TPACCEPT_NO_REQUEST = "§cYou do not have any pending requests";
    public static String TPING = "§e{0} §6accepted your request, teleporting...";
    public static String TPACCEPTED = "§6You accepted §e{0}'s §6teleport request";
    public static String TPDENYED = "§aYou denied §e{0}'s §ateleport request";
    public static String TPDENYED_SENDER = "§cYour request has been denied";
    public static String TPREQUEST_SENT = "§aRequest Sent!";
    public static String TP_INCOME_FIRST_LINE = "§e{0} §6Wants to teleport to you";
    public static String TPA_REQUEST =
            "§e{0} §6Wants to teleport to you\n"+
            "§6Use: §a/tpaccept §6To accept request\n"+
            "§dOr\n"+
            "§c/tpdeny §6to deny the request\n";

    //helpop
    public static String HELPOP = "§c[helpop] §6{0}: §c{1}";

    //ness
    public static String NESS_HELP = "§c/ness <reload>";
}
