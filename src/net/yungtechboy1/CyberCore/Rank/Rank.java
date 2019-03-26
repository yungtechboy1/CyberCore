package net.yungtechboy1.CyberCore.Rank;


/**
 * Created by carlt_000 on 1/22/2017.
 */
public class Rank {

    public String display_name = "";
    public String chat_prefix = "&7";
    int id = 0;

    public ChatFormats.RankChatFormat getChat_format() {
        return chat_format;
    }

    public void setChat_format(ChatFormats.RankChatFormat chat_format) {
        this.chat_format = chat_format;
    }

    ChatFormats.RankChatFormat chat_format = ChatFormats.RankChatFormat.Default;

    public Rank(Integer id, String display_name) {
        this.id = id;
        this.display_name = display_name;
    }

    public Rank(Integer id, String display_name, String chat_prefix) {
        this.id = id;
        this.display_name = display_name;
        this.chat_prefix = chat_prefix;
    }

    public Integer getId(){
        return id;
    }

    public String getDisplayName() {
        return display_name;
    }

    public String getChat_prefix() {
        return chat_prefix;
    }
}
