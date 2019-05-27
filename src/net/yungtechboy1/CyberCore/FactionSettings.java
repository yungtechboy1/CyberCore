package net.yungtechboy1.CyberCore;

/**
 * Created by carlt on 4/12/2019.
 */
public class FactionSettings {

    private ChatSetting ChatSelection = ChatSetting.All;

    public ChatSetting getChatSelection() {
        return ChatSelection;
    }

    public void setChatSelection(ChatSetting chatSelection) {
        ChatSelection = chatSelection;
    }

    public enum ChatSetting {
        All,
        Ally,
        Fac
    }


}
