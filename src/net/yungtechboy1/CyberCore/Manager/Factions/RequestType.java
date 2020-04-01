package net.yungtechboy1.CyberCore.Manager.Factions;

public enum RequestType {
    Ally(0),
    Faction_Invite(1);

    private int Key = -1;

    public int getKey() {
        return Key;
    }

    RequestType(int key) {
        Key = key;
    }
}
