package net.yungtechboy1.CyberCore.Classes;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.entity.EntityRegainHealthEvent;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.event.potion.PotionEvent;
import cn.nukkit.item.Item;
import cn.nukkit.math.NukkitRandom;
import cn.nukkit.potion.Potion;
import cn.nukkit.utils.ConfigSection;
import net.yungtechboy1.CyberCore.Abilities.Ability;
import net.yungtechboy1.CyberCore.Abilities.Double_Hearts;
import net.yungtechboy1.CyberCore.Abilities.Super_Breaker;
import net.yungtechboy1.CyberCore.CyberCoreMain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by carlt_000 on 1/26/2017.
 */
public class Class_Tank extends BaseClass {


    public Class_Tank(CyberCoreMain main, Player player, int rank, int xp, ConfigSection cooldowns) {
        super(main, player, rank, xp, cooldowns);
    }

    public Class_Tank (CyberCoreMain main, Player player, ConfigSection cs){
        super(main,player,cs);
    }

    @Override
    public void EntityRegainHealthEvent(EntityRegainHealthEvent event){
        int xp = (int)Math.ceil(event.getAmount());
        addXP(xp*10);
    }

    @Override
    public String getName() {
        return "Tanks";
    }

    @Override
    public void PlayerInteractEvent(PlayerInteractEvent event) {
        Item hand = event.getItem();
        if (hand.getId() == Item.SPLASH_POTION && (hand.getDamage() == Potion.REGENERATION || hand.getDamage() == Potion.REGENERATION_LONG || hand.getDamage() == Potion.REGENERATION_II)) {
            addXP(100);
        } else if (isPrime() && !HasCooldown(PrimeKey)) {
            if (hand.isSword()) {
                setPrime(false);
                activateAbility();
            }
        }
    }

    @Override
    public ArrayList<Ability> PossibleAbillity() {
        ArrayList<Ability> a = new ArrayList<Ability>();
        a.add(new Double_Hearts(CCM,this));
        return a;
    }
}
