package net.yungtechboy1.CyberCore.Manager.LeaderBoard.network;

import cn.nukkit.entity.Entity;

public class ScoreboardDisplay {

    /**
     * The api is from the server software GoMint
     */

    private Scoreboard scoreboard;
    private String objectiveName;
    private String displayName;
    private SortOrder sortOrder;

    public ScoreboardDisplay(Scoreboard scoreboard, String objectiveName, String displayName, SortOrder sortOrder) {
        this.scoreboard = scoreboard;
        this.objectiveName = objectiveName;
        this.displayName = displayName;
        this.sortOrder = sortOrder;
    }

    public DisplayEntry addEntity(Entity entity, int score ) {
        long scoreId = scoreboard.addOrUpdateEntity( entity, this.objectiveName, score );
        return new DisplayEntry( scoreboard, scoreId );
    }

    public net.yungtechboy1.CyberCore.Manager.LeaderBoard.network.Scoreboard getScoreboard() {
        return scoreboard;
    }

    public void setScoreboard(net.yungtechboy1.CyberCore.Manager.LeaderBoard.network.Scoreboard scoreboard) {
        this.scoreboard = scoreboard;
    }

    public String getObjectiveName() {
        return objectiveName;
    }

    public void setObjectiveName(String objectiveName) {
        this.objectiveName = objectiveName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public net.yungtechboy1.CyberCore.Manager.LeaderBoard.network.SortOrder getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(net.yungtechboy1.CyberCore.Manager.LeaderBoard.network.SortOrder sortOrder) {
        this.sortOrder = sortOrder;
    }

    public DisplayEntry addLine(String line, int score ) {
        long scoreId = scoreboard.addOrUpdateLine( line, this.objectiveName, score );
        return new DisplayEntry( scoreboard, scoreId );
    }

    public void removeEntry( DisplayEntry entry ) {
        scoreboard.removeScoreEntry( entry.getScoreId() );
    }

}