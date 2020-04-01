package net.yungtechboy1.CyberCore.Manager.Factions.Mission;

import cn.nukkit.item.Item;

public class MissionTask {
    boolean Completed = false;

    MissionTaskAction Action;
    String Syntax;
    int Amount = -1;
    Item SelectedItem = null;

    public MissionTask(MissionTaskAction type, String syntax) {
        Syntax = syntax;
        Action = type;
        if (syntax.contains(":") && syntax.split(":").length >= 2) {//ITEM
            String[] a = syntax.split(":");
            if (a.length <= 1) {
                System.out.println("ERROR!!!! CAN NOT COMPLET CREATION OF MISSIONTASK!!!!!! E33193");
                return;
            }

            try {
                int id = Integer.parseInt(a[0]);
                int meta = Integer.parseInt(a[1]);
                if (a.length == 3) {
                    int count = Integer.parseInt(a[2]);
                    SelectedItem = Item.get(id, meta);
                    Amount = count;
                } else {
                    SelectedItem = Item.get(id, meta);
                }
            } catch (Exception e) {
                throw e;
            }

        }
    }

    public enum MissionTaskAction {
        Break,//X AMOUNT TO PLACE or XXX:XX:XX FORMAT> ID:META:COUNT TO BREAK
        Place,//X AMOUNT TO PLACE or XXX:XX:XX FORMAT> ID:META:COUNT TO PLACE
        Kill,//X AMOUNT TO KILL
        Travel,//XXXXX DISTANCE IN BLOCKS
        HaveItem,//XXX:XX:XXXX format> ID:META:AMOUNT
        Other
    }

}
