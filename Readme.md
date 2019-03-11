# CyberCore
### Combined the Following Plugins:
- CyberTP
- CyberFactions
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
- [x] Add CyberChat
- [x] Copy Warp over from CyberChat
- [ ] Add /sudo
- [x] Pass Events To Abillity
- [ ] Fix Absourption on Nukkit
- [ ] Add Reset Password
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
    Offense
        Raider
            Able to enter raid more and toggle Nametag
            Take 10% less damage in enemy plot
        Theif
            Can Pickpocket
            10% damage buff when using the Dagger/Short blade
        Assassin
            More Powerfull Bows
            10% Damage buff with Automatic Bow & Bows
            Can go invisable using ability while they have "Invisbility Cloak" Enchant on their Chestplate
        Kngith
            Reduces All damage by 10%
            Slower swing time and Longer Attack Cooldown (27 Ticks vs 20)
        Tank
            Gain Upto 50% more health
            Deal upto 10% more damage
            Deal 10% more damage when still (No movement in past 10 Ticks)
    Crafting
        Mad Scientist - Potion Maker
            Able to craft special potions
            Potons have Buffs that are assocated with Potion Stand Quality and lvl
            Potons have Inpurities taht are assocated with players Ability lvl
                KEY = Impurtiy % | Max Possible Buff from Stand
                1-10 - 100% | 10%
                11-20 - 90% | 15%
                21-30 - 80% | 20%
                31-40 - 70% | 30%
                41-50 - 60% | 45%
                51-60 - 50% | 60%
                61-70 - 45% | 75%
                71-80 - 40% | 85%
                81-90 - 35% | 95%
                91-100 - 30% | 100%
        Enchater
            Each Custom enchant has a chance of Failing and Succeding depending on Enchanters ability lvl
                KEY = Max Success % | Max Fail %
                    1-10| 30% | 100%
                    11-20|40% | 90%
                    21-30|45% | 80%
                    31-40|50% | 70%
                    41-50|60% | 60%
                    51-60|75% | 50%
                    61-70|85% | 45%
                    71-80|100% | 40%
                    81-90|100% | 35%
                    91-100|100% | 30%
            
        Smith - Weapon Specalizatioin
            Can craft Modified Sword
        Crafter - Block and Item Customization
            Can craft Custom items
    Farming
        Farmer
            Auto harvest - Each time you havest a crop there is a chance that a fully grown crop directly next can be harvested too. This effect has a max
                Key = Auto Havest % | Max Blocks
                01 - 10 = 5% | 3
                11 - 20 = 10% | 3
                21 - 30 = 15% | 4
                31 - 40 = 20% | 5
                41 - 50 = 25% | 6
                51 - 60 = 30% | 7
                61 - 70 = 35% | 8
                71 - 80 = 45% | 8
                81 - 90 = 55% | 9
                91 - 100 = 65% | 10
        LumberJack
        Miner
            TNT-Specalist
                Chance of spawning TNT that can destroy ores
                20% increase in power with TNT
            MineLife
                10% increase in mine speed
    
#Avatar?
Water
Fire
Ice
Air
#Other Class
    Digger
        @ Lvl 10 Can specalize Minner
    Farmer
        @ Lvl 10 Can specalize Enchanter & Scholar
    LumberJack
        @ Lvl 10 Can specalize 
    Miner
    Tank
    
#GOOD
Stay with other clases for now

Or Maybe make these into abillities that can be gained when the Repestive class is leveled up...

^^

Good Idea!!!
```
    Specalizations
        Miner - Mining and Digging
            TNT-Specalist
                Chance of spawning TNT that can destroy ores
                20% increase in power with TNT
            MineLife
                10% increase in mine speed
                
        enchanter - Enchanting, Producing rare Potion and Books
        scholar - Potion Making, Can create "super-food", potions, and splash-potions, BAREL,
            Extensive knowledge of Potions
                Fire Splash Potion
                Ice Splash Potion
                Nerve Gas Splash potion
                
        builder - Easier base building, Can create auto-gen walls,/fly within faction plot, craft TNT arrows, Cannon TNT...
            Snow Hoe Enchant:
                Only can be used by builder    
                Can errect Snow Barriers with bow
                LVL 1 - 5:
                    9 Snow blocks Carrying Capacity
                    1 Snow block regin every 5/lvl Secs
                    Place 1 Snow block every 1 Sec 
                LVL 6 - 10:
                    9 Snow blocks Carrying Capacity + Level/2
                    1 Snow block regin every 4/lvl Secs
                    Place 1 Snow block every 1 Sec 
                LVL 11 - 15:
                    9 Snow blocks Carrying Capacity + level - 10
                    1 Snow block regin every 3/lvl Secs
                    Place 1 Snow block every 1 Sec 
        crafter - Can craft special items (TNT arrows, Cannon TNT...), Alos Farmer... can Auto plant and 
            Power Stomp
                Creats a Shockwave around player
                LVL 1 - 5 
                    Shockwave 3 block splash damage @ Level*.5+1
                    7 Block Plant Shockwave
                    30/lvl cooldown
                LVL 5 - 10
                    Shockwave 5 block splash damage @ Level*.6+1
                    12 Block Plant Shockwave
                    30/lvl cooldown
                LVL 10 - 15
                    Shockwave 8 block splash damage @ Level*.7+1
                    15 Block Plant Shockwave
                    30/lvl cooldown
        smith - Can combine Enchanted Books with items, craft enhanced weapons
        Assasin - 
            Silent movement
                        Trained Killer and does 1.2X Damage (EG 5 * 1.2 = 6, 2.5 Hearts -> 3 Hearts attack)
                        Get paid by server to kill players...The server helps you successed
                        Can Pick-pocket players
                            Using a fishing lure or tapping player a small inv will appear with 9 Random items for the player to attempt to grab
                            Only 4 of the Items are actually stealable.
                            Cooldown
        Knight - 
            Can hold Modified Sword
            Trained Killers with 1.2X Better damage asorbtion.
                    Protector
                
```

###Modifed Swords
- 3 Length Types: Short,Medium,Long
- Shorter swings can swing faster
- Depending on the material used to forge the Sword it can have up to 4 Base Effects
- Made to be a very deadly weapon when customized correctly
- Players can change every part of the Data Values below
- Requires a `PrimeFurnace` Can be crafted by ``Crafter`` (Mayber ``Smith`` :/)
- DATA VALUES
    - Length
        - Short
        - Medium
        - Long
    - Material
    - Effects
        - 4 Max
    - Speed - Swing speed
    - Damage - Base damage/Min Damage


#Custom Items
##Pocket Lure
    Used by an Assassin to pick pocket players
    Depends on Assassin Pick pocket lvl and Tool Lvl
        LVL:
            KEY = Max Success Change | Inpurities (Reduces Players Ability by This Percent) | Cooldown | Modifier (+/- Percent to Final Chance & Can override Max)
            1 = 40 | 0-40 | 120 | -100-100
            2 = 50 | 0-40 | 90 | -100-100
            3 = 60 | 0-30 | 70 | -100-100
            4 = 70 | 0-20 | 50 | -100-100
            5 = 80 | 0-50 | 30 | -100-100
            6 = 90 | 0-80 | 20 | -100-100
##Knockback Hoe
    Affects X Distance and knocks opponents back with cooldown
    LVL:
        KEY = Knockback Lvl | Distance |Cooldown | Damage/20
        1 = 1 | 2 | 5 | 1
        2 = 1 | 4 | 4 | 1
        3 = 2 | 5 | 4 | 2
        4 = 2 | 7 | 3 | 2
        5 = 3 | 7 | 2 | 3
        6 = 4 | 8 | 1 | 3
##Snow Hoe
    Item crafted by Crafter
    Creates snow blocks Barriers when tapped on floor
    LVL:
        KEY = Blocks Regenerated per 20 Ticks | Snow Capacity
        1 = .7 | 3
        2 = 1 | 5
        3 = 1.5 | 7
        4 = 1.7 | 9
        5 = 2 | 11
        6 = 2.5 | 15
##Cords Maps

Has 2 States
 - blank
 - written (X,Y)

Recipe
 - Can be crafted by crafter
        
        |--R-|-EM-|--R-|
        |-EM-|-CP-|-EM-|
        |--R-|-EM-|-R--|
        
        - R = Redston Dust
        - EM = Empty Map
        - CP = Compass
  Can be put into a `Teleporter` and teleport individuals to the Chunk Cords
        
##Teleporter(BlockStructure)
        ------------------------------------
        |                   Y
        |          |--|--|--|--|--|
        |          D--D--D--D--D--D
        |          D--D--G--G--D--D
        |     X    D--G--D--D--G--D
        |          D--G--D--D--G--D
        |          D--D--G--G--D--D
        |          D--D--D--D--D--D
            D = Diamond block
            G = Gold block
            
        
        |          D--D--D--D--D--D
        |          D--|--|--|--|--D
        |          D--|--|--|--|--D
        |          D--|--|--|--|--D
        |          D--|--|--|--|--D
        |          D--D--D--D--D--D
        |
        |
        |
        |
        |
Default settings:
    Drops randomly in Chunk
    Once at Level 2 the Teleporter would now be able to 
Can add upgrades:
    
##Cannon(Dispenser)
##Cannon Barrel(Blaze Rod)

Custom Meta data : 1

Custom item that can not be crafted but only obtained by CratesExtends fireing range of TNT Cannon

##Cannon TNT(TNT)
- Constructed by Builder & Crafter
- Fuel for TNT Cannon.
- Construction
```
        |-T-|-G-|-T-|
        |-G-|-T-|-G-|
        |-T-|-G-|-T-|
        T = 5 = TNT
        G = 4 = Gun Powder
```
##Cannon(Dispenser)
- Turns the Dispenser into a cannon. Needs to be connected to a Chest with a hopper.
- Takes `CannonTNT`(Only pushes entities and does no block damage) and Regular TNT or any other type
- Will not drop when broken unless it was with a Pickaxe with `CannonSaver`
###Crafting Process
- Needs:
    ...
###Chest Inv

- 1 `Cannon TNT` - Fuel used to shoot shots
- 2 TNT - Any TNT can go here and will be shot out
- 3 `Cannon Barrel` - Can Increase/Decrease the accuracy/range/power/fire rate
- 4 Gunpowder - Can increase the power, evenly divides amoung TNT
- 5 `Cannon Blueprint` - Is Paper, defines the Damage Value, Ergonomic Value, TNT Amount, and Max Range
- 6 `Offset Sheet` - allows the cannon to shoots shots slightly off center
### Enchanted Book
2 Varriables
- Chance of Success
- Level Required
### Custom Enchant
- ID
- Lvl
- Power - Another value for the Life of the enchnat (0-100)
- Percent - an average number of how successful the enchant is (0-100)





















---
#Old Ideal
```
    Crafter - Better at making weapons
    Farmers - Better at planting and farming crops
    Fighters - Increased Range,Damage, and/or armor
    Enchanters - Makes potions
    -
    -
    Merchant
        Can get your own shop space near spawn
        Can get TP sign from Spawn to Shop
        Shop is seperate island 1 Chunk Wide
        Shop is premade and editable
        Shop Signs will work on chests
    Fighter
        Assasin
            Silent movement
            Trained Killer and does 1.2X Damage (EG 5 * 1.2 = 6, 2.5 Hearts -> 3 Hearts attack)
            Get paid by server to kill players...The server helps you successed
            Can Pick-pocket players
                Using a fishing lure or tapping player a small inv will appear with 9 Random items for the player to attempt to grab
                Only 4 of the Items are actually stealable.
                Cooldown
        Knight
            Trained Killers with 1.2X Better damage asorbtion.
            Protector
    Crafter
        Enchanter
            Creates enchated items for Knights,Assasin, and potions
            Can create "planting stick" which allows farmers to auto plant seeds, by enchanting a book
            
        Farmer
            Sells goods to Merchants
            Able to auto plant seeds 2 * Level blocks away as an ability or with hoe only farmers can use.
        IronSmith
            Create weapons for Enchanter or Merchant or Knight
                These weapons can then be enchanted by an Enchanter and increase the value and power of the sword.
        LandOwner
            Can Claim land for Bussiness ventures
                - Mine
                - Bar
                - Lounge
                - Arena
                - Farm
                - Fee'd Entrance
            
            

#Classes
##Farmer
    Passive Abilities
        - Items planted can grow up to 50% Faster depending on your level
        - Animals killed have a chance of dropping more loot
    Active Ability
        - Able to craft 
        
    Perfect for those who would rather craft
##Crafter
    Items
        Super TNT - 1.5 Times more powerful than regualar TNT
        Cannon TNT (Super Cannon Fuel) - TNT used to shoot TNT Cannon
        TNT Cannon - Shoots TNT Straight... Uses TNT and Cannon TNT
        Craft better swords
    Passive
    Active
##Fighters
    Items
        Ablet to find special items that Farmers, Enchanters and Crafters need
    Passive
    Active
##Enchantser
## Item Customization
### Swords
#### Handle
##### Short
[ x ] Quicker swing with lighter damage
##### Medium
##### Long Handle
[ x ] More Damage at the cost of more swing time and more hunger used
#### Blade
[ x ] Dagger - Quick  attack time but short distance needed

[ x ] Short Blade - Shorter reach distance and Faster swing

[ x ] Long Blade - Longer reach distance and slower swing

[ 1/2 ] Lance - Tap the ground to deal 1/2 Damage to everyone within .5 blocks

#### Enchants
[ x ] Heart Stealer - Used to steals a hearts while battling

[ x ] Venom Blade - Causes Nausea for a brief period

[ x ] Frozen Ice - Freezes enemies for a brief period

Pepper Spray - Blinds enemies for a brief period

Swappa - Swap Location with an Enemy

[X] Hardened  - Double Damage chance with each hit

[X] Kiss of Thunder - Chance of lightning striking your opponent with each hit

Drip - Chance of spawning water to slow opponent

Rocket/AirBag/Spring - Shoots player 10 Blocks into the sky when hit from behind

#### Health
More enchants the more health it takes, Each Enchant level for each enchant adds 50% of the base damage taken to item when used.

### Axe
#### Blade
Wood Cutter -Improves wood harvesting with axe

Armor Cutter - Improves armor damage with weapon

#### Enchants

Chainsaw - Chance of instantly breaking all tree blocks

### Bow
#### Enchants
Machine Gun - Increases bow shooting rate

Spray N Pray - Chance of shooting from 3-10 Arrows in rapid succession
### Arrow
#### Enchants
Heatseaker - Tracks for 10 Blocks then Flys Straight

Boomerang - Chance of arrow(Ammo)  Returning to your inv

Explosion - Explodes 1/2 TNT when after landing

### Potions

Frozone-Throw pot to Freeze Opponent for Level X 1.5

### Dispenser
#### Enchants
##### Cannon
---
Turns the Dispenser into a cannon. Needs to be connected to a Chest with a hopper.
Takes CannonTNT(Only pushes entities and does no block damage) and Regular TNT or any other type
Inv:
Cannon TNT|(Any)TNT|Barrel(Blaze Rod)|Gunpowder(Increases Power) / Slime Block (increases height) | Blueprint (Paper) | Offest (Paper)

- 1 Cannon TNT - Fule used to shoot shots
- 2 TNT - Any TNT can go here and will be shot out
- 3 Barrel - Can Increase/Decrease the accuracy/range/power/fire rate
- 4 Gunpowder - Can increase the power, evenly divides amoung TNT
- 5 Blueprint - Is Paper, defines the Damage Value, Ergonomic Value, TNT Amount, and Max Range
- 6 Offset - allows the cannon to shoots shots slightly off center
### Enchanted Book
2 Varriables
- Chance of Success
- Level Required
### Custom Enchant
- ID
- Lvl
- Power - Another value for the Life of the enchnat (0-100)
- Percent - an average number of how successful the enchant is (0-100)











#CyberTech Faction API
Server now has a Custom Player Class, Server, Servermanager



---
---
---
Traits
	Ninja
Nation
	Fire
	Water/Ice
	Air
	Earth
Custom Enchants
	Offense
		Heart Stealer - Used to steals a hearts while battling
		Venom Blade - Causes Nausea for a brief period
		Frozen Ice - Freezes enemies for a brief period
		Pepper Spray - Blinds enemies for a brief period
		Swappa - Swap Location with an Enemy
		Hardened  - Double Damage chance with each hit
		Kiss of Thunder - Chance of lightning striking your opponent with each hit
	Axe:
		Chunker - On a pickaxe, when breaking faction Nervous systems it causes the claim to go to that player and a new Nervous system is spawned on the surface.
	Compass:
		Tracker - Points you in the nearest opponent within Level * 250, Add a Delay for the compass depending on how far away, how much resources is used for calculations, so that if people spam the server wont slow.
		Base Tracker - Same a Tracker but just with plot claims
Items
	Maps
		When with-in 300 Block of a Claimed Base you can write a base location to it then use it in a teleporter.
			On the map it shows the Faction Name and Cords
	Teleporter
		Layout:
			X-e	X-g	X	X-g	X-e
			X-g	g	g	g	X-g
			X	g	g	g	X
			X	g	g-d-d	g	X
			X	g	g	g	X
			x-g	g	g	g	X-g
			x-e	x-g	x	x-g	x-e
			X = Obsidian - 18
			G = Gold - 23
			D = Diamond - 2
			E = Emerald - 4
		Hit the Diamond blocks with a map to start the teleporter and teleports All Entites within its Construction to the Cords within 500 Blocks with all players not guaranteed to make it together. Also runs on Fuel…. What type? TBD….
Faction Plot Chunk Nervous System.
	When a faction claims a chunk it is treated as a an additional extremity to the current nervous system, if any. After claiming a Redstone Block would appear on the surface containing Chunk Cords and Faction owner. Factions can reposition the block using the /movenervous or /mn. Then tap on the block that you want, Next walk to the desired location and place the block in your inv. That block is the Nervous system for that faction now. If there are other adjacent conjoined  faction plots then the Block can be moved across. But if the Nervous system block for Chunk A is located in Chunk C , and another faction claims Chunk B, which is in-between A and C, Then Chunk A would be unclaimed. Whereas if an enemy player were to attach and find All 3 Chunk Nervous Systems for Chunks A,B,C and break them then the plots would be unclaimed, with each disconnect checking that there is still a chunk connecting the Nervous System to its chunk. If a Nervous System is no longer connected to its chunk then the chunk will be unclaimed and  the Nervous System block will be destroyed.
	
		Examples:
				If E5 Were to be Disconnected and All Nervous systems are located at E1 then as a result E5:B8 in addition to E5 will be unclaimed.
				--------	A	B	C	D	E
				1	X	X	X	X	X
				2			X	X	x
				3				X	X
				4					x
				5					x
				6					x
				7					x
				8		x	x	x	x
				9					
									
				
Speciality
	Tank - Keep more health at the cost of damage and Hunger
	Warrior - Deal more damage at the cost of Defense and Hunger
	Crafter - Boosts crafting speed
	Enchanter - Access to special enchants
	Farmer - Speeds up harvesting and Growing
	Pyro - Allows for hand held TNT Cannon avoid TNT Damage
	
Chunk Info: 
    Give detail of how the Chunk looks