package net.yungtechboy1.CyberCore.Custom.Item;

/**
 * Created by carlt on 3/13/2019.
 */
public enum Rarity {
    Common(0),
    Ordinary(1),
    Un_Common(2),
    Rare(3),
    Scarce(4),
    Untold(5),
    Legendary(6),
    Mastery(7);
    int i;

    Rarity(int Id){
        i = Id;
    }
}


