package net.yungtechboy1.CyberCore.Manager.Factions;

public class FactionSettings {
    private FactionRank AllowedToViewInbox = FactionRank.Officer;
    private FactionRank AllowedToAcceptAlly = FactionRank.General;
    private FactionRank AllowedToEditSettings = FactionRank.General;
    private FactionRank AllowedToPromote = FactionRank.Member;
    private FactionRank AllowedToKick = FactionRank.General;
    private FactionRank AllowedToDemote = FactionRank.Member;
    private int MaxFactionChat = 30;
    private int MaxAllyChat = 30;
    private FactionRank AllowedToInvite = FactionRank.Member;
    private FactionRank DefaultJoinRank = FactionRank.Recruit;
    private FactionRank AllowedToClaim = FactionRank.General;

    public FactionRank getAllowedToSetHome() {
        return AllowedToSetHome;
    }

    public void setAllowedToSetHome(FactionRank allowedToSetHome) {
        AllowedToSetHome = allowedToSetHome;
    }

    private FactionRank AllowedToSetHome = FactionRank.General;

    public FactionSettings() {

    }

    public FactionRank getAllowedToPromote() {
        return AllowedToPromote;
    }

    public void setAllowedToPromote(FactionRank allowedToPromote) {
        AllowedToPromote = allowedToPromote;
    }

    public FactionRank getAllowedToDemote() {
        return AllowedToDemote;
    }

    public void setAllowedToDemote(FactionRank allowedToDemote) {
        AllowedToDemote = allowedToDemote;
    }

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
