package net.yungtechboy1.CyberCore.Manager.LeaderBoard.network;

public class DisplayEntry {

    /**
     * The api is from the server software GoMint
     */

    private Scoreboard scoreboard;

    private long scoreId;

    public DisplayEntry(Scoreboard sb, long sid) {
        scoreboard = sb;
        scoreId = sid;
    }

    public void setScore(int score ) {
        scoreboard.updateScore( this.scoreId, score );
    }

    public long getScoreId() {
        return scoreId;
    }

    public int getScore() {
        return scoreboard.getScore( this.scoreId );
    }

}