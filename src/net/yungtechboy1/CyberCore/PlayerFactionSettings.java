package net.yungtechboy1.CyberCore;

public class PlayerFactionSettings {
    private ChatSetting ChatSelection = ChatSetting.Global;

    public ChatSetting getChatSelection() {
        return ChatSelection;
    }

    public void setChatSelection(ChatSetting chatSelection) {
        ChatSelection = chatSelection;
    }

    public enum ChatSetting {
        Ally,
        Global,
        Faction
    }
}
