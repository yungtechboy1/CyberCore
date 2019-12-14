package net.yungtechboy1.CyberCore.Manager.Factions;

public class FactionSettings {
    private FactionRank AllowedToViewInbox = FactionRank.Officer;
    private FactionRank AllowedToAcceptAlly = FactionRank.General;
    private FactionRank AllowedToEditSettings = FactionRank.Leader;
    private FactionRank AllowedToPromote = FactionRank.Member;
    private FactionRank AllowedToKick = FactionRank.General;
    //    private FactionRank AllowedToDemote = FactionRank.Member;
    private int MaxFactionChat = 30;
    private int MaxAllyChat = 30;
    private int WeeklyFactionTax = 0;
    private FactionRank AllowedToInvite = FactionRank.Member;
    private FactionRank DefaultJoinRank = FactionRank.Recruit;
    private FactionRank AllowedToClaim = FactionRank.General;
    private FactionRank AllowedToWinthdraw = FactionRank.General;
    private FactionRank AllowedToSetHome = FactionRank.General;

    public FactionSettings() {

    }

    public FactionSettings(String i) {
        String[] ii = i.split("\\|");
        if (ii.length != 11) {
            System.out.println("Error importing factions settings! Expected length 10, got " + ii.length);
            return;
        }
        FactionRank avi = FactionRank.getRankFromString(ii[0]);
        if (avi != null) AllowedToViewInbox = avi;

        FactionRank aaa = FactionRank.getRankFromString(ii[1]);
        if (aaa != null) AllowedToAcceptAlly = avi;

        FactionRank aes = FactionRank.getRankFromString(ii[2]);
        if (aes != null) AllowedToEditSettings = aes;

        FactionRank ap = FactionRank.getRankFromString(ii[3]);
        if (ap != null) AllowedToPromote = ap;

        FactionRank atk = FactionRank.getRankFromString(ii[4]);
        if (atk != null) AllowedToKick = avi;

        FactionRank ati = FactionRank.getRankFromString(ii[5]);
        if (ati != null) AllowedToInvite = ati;

        FactionRank djr = FactionRank.getRankFromString(ii[6]);
        if (djr != null) DefaultJoinRank = djr;

        FactionRank atc = FactionRank.getRankFromString(ii[7]);
        if (atc != null) AllowedToClaim = atc;

        FactionRank atw = FactionRank.getRankFromString(ii[8]);
        if (atw != null) AllowedToWinthdraw = atw;

        FactionRank ash = FactionRank.getRankFromString(ii[9]);
        if (avi != null) AllowedToViewInbox = ash;

        try {
            int iii = Integer.parseInt(ii[10]);
            WeeklyFactionTax = iii;
        } catch (Exception e) {
            System.out.println("Error parseing WeeklyFactionTax! from " + ii[10]);
        }


    }

    public String export() {
        String e = "";
        e += AllowedToViewInbox.getPower() + "|";
        e += AllowedToAcceptAlly.getPower() + "|";
        e += AllowedToEditSettings.getPower() + "|";
        e += AllowedToPromote.getPower() + "|";
        e += AllowedToKick.getPower() + "|";
        e += AllowedToInvite.getPower() + "|";
        e += DefaultJoinRank.getPower() + "|";
        e += AllowedToClaim.getPower() + "|";
        e += AllowedToWinthdraw.getPower() + "|";
        e += AllowedToSetHome.getPower() + "|";
        e += getWeeklyFactionTax() + "|";
        return e;
    }

    public int getWeeklyFactionTax() {
        return WeeklyFactionTax;
    }

    public void setWeeklyFactionTax(int weeklyFactionTax) {
        WeeklyFactionTax = weeklyFactionTax;
    }

    public FactionRank getAllowedToWinthdraw() {
        return AllowedToWinthdraw;
    }

    public void setAllowedToWinthdraw(FactionRank allowedToWinthdraw) {
        AllowedToWinthdraw = allowedToWinthdraw;
    }

    public FactionRank getAllowedToSetHome() {
        return AllowedToSetHome;
    }

    public void setAllowedToSetHome(FactionRank allowedToSetHome) {
        AllowedToSetHome = allowedToSetHome;
    }

    public FactionRank getAllowedToPromote() {
        return AllowedToPromote;
    }

    public void setAllowedToPromote(FactionRank allowedToPromote) {
        AllowedToPromote = allowedToPromote;
    }

//    public FactionRank getAllowedToDemote() {
//        return AllowedToDemote;
//    }
//
//    public void setAllowedToDemote(FactionRank allowedToDemote) {
//        AllowedToDemote = allowedToDemote;
//    }

    public int getMaxFactionChat() {
        return MaxFactionChat;
    }

    public void setMaxFactionChat(int maxFactionChat) {
        MaxFactionChat = maxFactionChat;
    }

    public int getMaxAllyChat() {
        return MaxAllyChat;
    }

    public void setMaxAllyChat(int maxAllyChat) {
        MaxAllyChat = maxAllyChat;
    }

    public FactionRank getAllowedToViewInbox() {
        return AllowedToViewInbox;
    }

    public void setAllowedToViewInbox(FactionRank allowedToViewInbox) {
        AllowedToViewInbox = allowedToViewInbox;
    }

    public FactionRank getAllowedToAcceptAlly() {
        return AllowedToAcceptAlly;
    }

    public void setAllowedToAcceptAlly(FactionRank allowedToAcceptAlly) {
        AllowedToAcceptAlly = allowedToAcceptAlly;
    }

    public FactionRank getAllowedToInvite() {
        return AllowedToInvite;
    }

    public void setAllowedToInvite(FactionRank allowedToInvite) {
        AllowedToInvite = allowedToInvite;
    }

    public FactionRank getDefaultJoinRank() {
        return DefaultJoinRank;
    }

    public void setDefaultJoinRank(FactionRank defaultJoinRank) {
        DefaultJoinRank = defaultJoinRank;
    }

    public FactionRank getAllowedToClaim() {
        return AllowedToClaim;
    }

    public void setAllowedToClaim(FactionRank allowedToClaim) {
        AllowedToClaim = allowedToClaim;
    }

    public FactionRank getAllowedToKick() {
        return AllowedToKick;
    }

    public void setAllowedToKick(FactionRank allowedToKick) {
        AllowedToKick = allowedToKick;
    }

    public FactionRank getAllowedToEditSettings() {
        return AllowedToEditSettings;
    }

    public void setAllowedToEditSettings(FactionRank allowedToEditSettings) {
        this.AllowedToEditSettings = allowedToEditSettings;
    }
}
