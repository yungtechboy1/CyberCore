package net.yungtechboy1.CyberCore.Classes.GUI;

public enum GUI {
    Offense,
    Defense,
    Crafting;

    String name = "N/A";
    GUI() {

    }

    public String GetName() {
        return name;
    }
}
