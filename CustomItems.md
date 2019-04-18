351:0 - Pig Gut
---
Extremely strong and resistant to damage

    Will Be used to reinforece armor,

Item : Dye

Meta : 0

NamedTag : PigGut = 1

Name : Pig Gut

Max Stack : 16

000:0 - Wolf Claw
---
Used to Strengthen Swords

Item : Ghast Tear

Meta : 1

NamedTag :

Name : Wolf Claw

000:0 - Cannon
---
- Item: Dispenser / Dropper
- 4 Variables
    - HS - Horizontal Spread - -100 - 100
    - VS - Vertical Spread - -100 - 100
    - PW - Power - 0 - 100
- Construction:
    - Fuse
        - Cooldown time
        - Firing Mode
    - Barrel
        - Affects Spread
    - TNT / Silent TNT / Super TNTs
        - Range
    - FireStone
        - Affects horizontal Spread
            - More use in construction results in a Tighter horizontal spread for the cannon 
        - Base building item

000:0 - Offset Sheets
---
Sheet that adds or removes Spread for `HS` or `VS` but not both.

- 2 Variables
    - `HS` - Horizontal Spread - -100 - 100
    - OR
    - `VS` - Vertical Spread - -100 - 100

000:0 - Barrel
---
Blaze rod with custom namedtag

Only craftable by `Crafter` class

When crafting a barrel a the `HS` and `VS` are dependant on the level of the Crafter.

Players can obtain `Offset Sheets` that can be applied to a Cannon or Barrel

Spread Calculations : 
        
    spread = 100 - level
   
- 2 Variables
    - `HS` - Horizontal Spread - -100 - 100
    - `VS` - Vertical Spread - -100 - 100
    
000:0 - Firestone
---
- Cobble stone mixed with Blaze powder
- Ingredients
    - 4 Blaze Powder
    - 1 Cobblestone
    
000:0 - Silent TNT
---
- TNT mixed with cob webbs
- Ingredients
    - 8 Cobwebs
    - 1 TNT
    
000:0 - Super TNT
---
- Cobble stone mixed with Blaze powder
- Ingredients
    - 5 TNT
    - 4 Gunpowder

000:0 - Cobble Wall Generators
---
3 Types
- Stack - a 128 high Cobble wall
- Line - 32 Blocks long and 128 high cobble walls
- Line w/ Water - 32 Blocks long and 128 high cobble walls with water next to them

Construction
- Stack
    - 256 Cobble
    - 64 Lava
- Line
    - 32 Stack
    - 32 Lava
- Line w/ water
    - Line
    - 32 Water
    
000:0 - Custom Modified Sword
---
Data:
- Length
    - Short
    - Medium
    - Long
- Material
    - Firestone - Gives longer fire Damage with lower base damage
    - Frozen Steel - Can freeze opponent from all movement for .01 - 1 Sec
    - Reinforced Steel - Same Base Damage of a Iron Sword but with 2X the durability of Diamond
    - Saphire - Diamond Damage with 1.5X Durability of Diamond
- Speed
    - Crafters and Offense classes have a 10% Speed boost
- Damage
    -  -5% - 25% random Boost + (lvl*.8);
    ###Modified Swords
    - Shorter swings can swing faster
    - Depending on the material used to forge the Sword it can have up to 4 Base Effects
    - Made to be a very deadly weapon when customized correctly
    - Players can change every part of the Data Values below
    - Requires a `PrimeFurnace` Can be crafted by ``Crafter`` (Mayber ``Smith`` :/)

000:0 - Custom Modified Sword Charms
---
Can increase the Damage or Speed on a Custom Modified Sword.

Has `Success` and `fail` Rates