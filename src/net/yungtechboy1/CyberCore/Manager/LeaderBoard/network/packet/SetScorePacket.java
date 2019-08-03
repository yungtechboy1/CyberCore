package net.yungtechboy1.CyberCore.Manager.LeaderBoard.network.packet;

import cn.nukkit.network.protocol.DataPacket;

import java.util.List;

public class SetScorePacket extends DataPacket {
    public static byte getNetworkId() {
        return NETWORK_ID;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public List<ScoreEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<ScoreEntry> entries) {
        this.entries = entries;
    }

    public static final byte NETWORK_ID = 0x6c;

    private byte type;
    private List<ScoreEntry> entries;

    @Override
    public byte pid() {
        return NETWORK_ID;
    }

    @Override
    public void decode() {
        //Ignore
    }

    @Override
    public void encode() {
        this.reset();
        this.putByte( this.type );
        this.putUnsignedVarInt( this.entries.size() );

        for ( ScoreEntry entry : this.entries ) {
            this.putVarLong( entry.scoreId );
            this.putString( entry.objective );
            this.putLInt( entry.score );

            if(this.type == 0){
                this.putByte( entry.entityType );

                switch ( entry.entityType ) {
                    case 3: // Fake entity
                        this.putString( entry.fakeEntity );
                        break;
                    case 1:
                    case 2:
                        this.putUnsignedVarLong( entry.entityId );
                        break;
                }

            }
        }
    }

    public static class ScoreEntry {
        public long getScoreId() {
            return scoreId;
        }

        public String getObjective() {
            return objective;
        }

        public int getScore() {
            return score;
        }

        public byte getEntityType() {
            return entityType;
        }

        public void setEntityType(byte entityType) {
            this.entityType = entityType;
        }

        public String getFakeEntity() {
            return fakeEntity;
        }

        public void setFakeEntity(String fakeEntity) {
            this.fakeEntity = fakeEntity;
        }

        public long getEntityId() {
            return entityId;
        }

        public void setEntityId(long entityId) {
            this.entityId = entityId;
        }

        public ScoreEntry(long scoreId, String objective, int score, byte entityType, String fakeEntity, long entityId) {
            this.scoreId = scoreId;
            this.objective = objective;
            this.score = score;
            this.entityType = entityType;
            this.fakeEntity = fakeEntity;
            this.entityId = entityId;
        }

        public ScoreEntry(long scoreId, String objective, int score) {
            this.scoreId = scoreId;
            this.objective = objective;
            this.score = score;
        }

        private final long scoreId;
        private final String objective;
        private final int score;

        // Add entity type
        private byte entityType;
        private String fakeEntity;
        private long entityId;
    }
}
