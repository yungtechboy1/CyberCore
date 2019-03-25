package net.yungtechboy1.CyberCore.Rank;

import cn.nukkit.Player;
import cn.nukkit.utils.TextFormat;

/**
 * Created by carlt on 3/21/2019.
 */
public class ChatFormats {


    public enum RankChatFormat {
        Default("%r%f:%p > %t");

        private String Syntax;

        RankChatFormat(String syn) {
            Syntax = syn;
        }

        public String GetSyntax() {
            return Syntax;
        }

        public String format(String fac, String rank, Player pp, String t) {
            String a = GetSyntax();
            a = a.replaceAll("%f",fac).replaceAll("%r",rank).replaceAll("%p",pp.getName()).replaceAll("%t",t);
            return a;
        }
    }
    public enum RankDisplayNameFormat {
        Default("%f\n%r "+ TextFormat.GRAY +" %p");

        private String Syntax;

        RankDisplayNameFormat(String syn) {
            Syntax = syn;
        }

        public String GetSyntax() {
            return Syntax;
        }

        public String format(String fac, String rank, Player pp) {
            String a = GetSyntax();
            a= a.replaceAll("%f",fac).replaceAll("%r",rank).replaceAll("%p",pp.getName());
            return a;
        }
    }
}
