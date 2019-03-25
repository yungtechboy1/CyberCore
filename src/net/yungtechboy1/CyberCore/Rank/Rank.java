package net.yungtechboy1.CyberCore.Rank;


/**
 * Created by carlt_000 on 1/22/2017.
 */
public class Rank {

    RankList rank;

    String display_name = "[]";
    String chat_prefix = "&7";
    public int Ranking = 0;

    public ChatFormats.RankChatFormat getChat_format() {
        return chat_format;
    }

    public void setChat_format(ChatFormats.RankChatFormat chat_format) {
        this.chat_format = chat_format;
    }

    ChatFormats.RankChatFormat chat_format = ChatFormats.RankChatFormat.Default;
    RankList R;

    public Rank(RankList rank, String display_name) {
        this.rank = rank;
        this.display_name = display_name;
    }

    public Rank(RankList rank, String display_name, String chat_prefix) {
        this.rank = rank;
        this.display_name = display_name;
        this.chat_prefix = chat_prefix;
    }

    public RankList getRank() {
        return rank;
    }

    public String getDisplayName() {
        return display_name;
    }

    public String getChat_prefix() {
        return chat_prefix;
    }
}
