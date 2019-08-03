package net.yungtechboy1.CyberCore.Manager.LeaderBoard.network.packet;

import cn.nukkit.network.protocol.DataPacket;

public class SetObjectivePacket extends DataPacket {

    public static final byte NETWORK_ID = 0x6b;

    public String displaySlot;
    public String objectiveName;
    public String displayName;
    public String criteriaName;
    public int sortOrder;

    public static byte getNetworkId() {
        return NETWORK_ID;
    }

    public String getDisplaySlot() {
        return displaySlot;
    }

    public void setDisplaySlot(String displaySlot) {
        this.displaySlot = displaySlot;
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

    public String getCriteriaName() {
        return criteriaName;
    }

    public void setCriteriaName(String criteriaName) {
        this.criteriaName = criteriaName;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }

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
        this.putString(this.displaySlot);
        this.putString(this.objectiveName);
        this.putString(this.displayName);
        this.putString(this.criteriaName);
        this.putVarInt(this.sortOrder);
    }
}
