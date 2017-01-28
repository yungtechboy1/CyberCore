# CyberCore
### Combined the Following Plugins:
- CyberTP
- CyberChat

### Notes
To activate '_Active_' Abilities the player must do /aa or /abilityactive
Once command is ran the the server will start a timer of 5sec that will allow the player to tap the floor with an item to activate the appropiate Ability.

Example:
- /aa is ran
- Player pulls out pick axe and taps the ground
- **_BOW_**
- Player now has an Efficency V Pickaxe for 2 Secs!
- After wards then the Enchant will be removed from the item.

### EXP
424,024,412 is what it takes to Get to the max Level of 1000 in a single Ablility!

Math ![Math](http://puu.sh/tza9v/780d8234bb.png)

-----------
Miner - Gets Boots
Lumberjack
Farmer
Warrior

### TODO
- [ ] Add CyberChat
- [ ] Copy Warp over from CyberChat
- [ ] Add /sudo
- [ ] Pass Events To Abillity
- [ ] Fix Absourption on Nukkit
- [ ] TODO
- [ ] TODO
- [ ] TODO
- [ ] TODO

Blast Mining > When you exploed TNT you have a 30% chance + Levels to get
asd

```
public void PlayerInteractEvent(PlayerInteractEvent event){
    Item hand = event.getItem();
        if(event.getBlock().getId() == Block.TNT && hand.getId() == Item.AIR){
            event.getBlock().getLevel().setBlock(event.getBlock(), new BlockAir(), true);
            double mot = (new NukkitRandom()).nextSignedFloat() * Math.PI * 2;

            CompoundTag nbt = new CompoundTag()
                    .putList(new ListTag<DoubleTag>("Pos")
                            .add(new DoubleTag("", event.getBlock().x + 0.5))
                            .add(new DoubleTag("", event.getBlock().y))
                            .add(new DoubleTag("", event.getBlock().z + 0.5)))
                    .putList(new ListTag<DoubleTag>("Motion")
                            .add(new DoubleTag("", -Math.sin(mot) * 0.02))
                            .add(new DoubleTag("", 0.2))
                            .add(new DoubleTag("", -Math.cos(mot) * 0.02)))
                    .putList(new ListTag<FloatTag>("Rotation")
                            .add(new FloatTag("", 0))
                            .add(new FloatTag("", 0)))
                    .putByte("Fuse", 80)
                    .putInt("force",LVL)
                    .putInt("size",Math.max(LVL/50,1));
            Entity tnt = new EntityPrimedTNT(
                    event.getBlock().getLevel().getChunk(event.getBlock().getFloorX() >> 4, event.getBlock().getFloorZ() >> 4),
                    nbt
            );
            tnt.spawnToAll();
            event.getBlock().getLevel().addSound(new TNTPrimeSound(event.getBlock()));
        }*/
    }
```

```

```

```
 public void BlockBreakEvent(BlockBreakEvent event) {
        /*ExcavationBreakEvent(event);
        int id = event.getBlock().getId();
        int obxp = OreBreak.getOrDefault(id, 0);
        int wcxp = WoodCutting.getOrDefault(id, 0);
        int hxp = Herbal.getOrDefault(id, 0);
        int exp = Excavation.getOrDefault(id, 0);
        if (obxp > 0) XP_MINING += obxp;
        if (wcxp > 0) XP_WOOD_CUTTING += wcxp;
        if (hxp > 0) XP_HERBALISM += hxp;
        if (exp > 0) XP_EXCAVATION += exp;*/
    }
```

```

```