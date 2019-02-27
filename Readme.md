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
#GOOD
```
    Classes
        Miner - Mining and Digging
            TNT-Specalist
                Chance of spawning TNT that can destroy ores
                20% increase in power with TNT
            MineLife
                10% increase in mine speed
                
        enchanter - Enchanting, Producing rare Potion and Books
        scholar - Potion Making, Can create "super-food", potions, and splash-potions
        builder - Easier base building, Can create auto-gen walls,/fly within faction plot, craft TNT arrows, Cannon TNT...
        crafter - Can craft special items (TNT arrows, Cannon TNT...), Alos Farmer... can Auto plant and 
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