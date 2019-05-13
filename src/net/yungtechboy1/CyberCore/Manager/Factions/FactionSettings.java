package net.yungtechboy1.CyberCore.Manager.Factions;

public class FactionSettings {
    private FactionRank AllowedToViewInbox = FactionRank.Officer;
    private FactionRank AllowedToAcceptAlly = FactionRank.General;

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

    private FactionRank AllowedToPromote = FactionRank.Member;
    private FactionRank AllowedToDemote = FactionRank.Member;

    private int MaxFactionChat = 30;

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

    private int MaxAllyChat = 30;

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

    private FactionRank AllowedToInvite = FactionRank.Member;
    private FactionRank DefaultJoinRank = FactionRank.Recruit;

    public FactionRank getAllowedToClaim() {
        return AllowedToClaim;
    }

    public void setAllowedToClaim(FactionRank allowedToClaim) {
        AllowedToClaim = allowedToClaim;
    }

    private FactionRank AllowedToClaim = FactionRank.General;

    public FactionSettings() {

    }

}
