package net.yungtechboy1.CyberCore.Custom.Item;

import cn.nukkit.block.Block;
import cn.nukkit.item.ItemChickenCooked;
import cn.nukkit.item.ItemEnderPearl;
import cn.nukkit.item.ItemMagmaCream;
import cn.nukkit.item.ItemMuttonCooked;
import cn.nukkit.item.ItemMuttonRaw;
import cn.nukkit.item.ItemPorkchopCooked;
import cn.nukkit.item.*;
import cn.nukkit.utils.Utils;

import java.util.Map;
import java.util.regex.Pattern;

public class CustomItemManager {
    public static Class[] list = null;
    public static void init() {
        if (list == null) {
            list = new Class['\uffff'];
            list[256] = ItemShovelIron.class;
            list[257] = ItemPickaxeIron.class;
            list[258] = ItemAxeIron.class;
            list[259] = ItemFlintSteel.class;
            list[260] = ItemApple.class;
            list[261] = ItemBow.class;
            list[262] = ItemArrow.class;
            list[263] = ItemCoal.class;
            list[264] = ItemDiamond.class;
            list[265] = ItemIngotIron.class;
            list[266] = ItemIngotGold.class;
            list[267] = ItemSwordIron.class;
            list[268] = ItemSwordWood.class;
            list[269] = ItemShovelWood.class;
            list[270] = ItemPickaxeWood.class;
            list[271] = ItemAxeWood.class;
            list[272] = ItemSwordStone.class;
            list[273] = ItemShovelStone.class;
            list[274] = ItemPickaxeStone.class;
            list[275] = ItemAxeStone.class;
            list[276] = ItemSwordDiamond.class;
            list[277] = ItemShovelDiamond.class;
            list[278] = ItemPickaxeDiamond.class;
            list[279] = ItemAxeDiamond.class;
            list[280] = ItemStick.class;
            list[281] = ItemBowl.class;
            list[282] = ItemMushroomStew.class;
            list[283] = ItemSwordGold.class;
            list[284] = ItemShovelGold.class;
            list[285] = ItemPickaxeGold.class;
            list[286] = ItemAxeGold.class;
            list[287] = ItemString.class;
            list[288] = ItemFeather.class;
            list[289] = ItemGunpowder.class;
            list[290] = ItemHoeWood.class;
            list[291] = ItemHoeStone.class;
            list[292] = ItemHoeIron.class;
            list[293] = ItemHoeDiamond.class;
            list[294] = ItemHoeGold.class;
            list[295] = ItemSeedsWheat.class;
            list[296] = ItemWheat.class;
            list[297] = ItemBread.class;
            list[298] = ItemHelmetLeather.class;
            list[299] = ItemChestplateLeather.class;
            list[300] = ItemLeggingsLeather.class;
            list[301] = ItemBootsLeather.class;
            list[302] = ItemHelmetChain.class;
            list[303] = ItemChestplateChain.class;
            list[304] = ItemLeggingsChain.class;
            list[305] = ItemBootsChain.class;
            list[306] = ItemHelmetIron.class;
            list[307] = ItemChestplateIron.class;
            list[308] = ItemLeggingsIron.class;
            list[309] = ItemBootsIron.class;
            list[310] = ItemHelmetDiamond.class;
            list[311] = ItemChestplateDiamond.class;
            list[312] = ItemLeggingsDiamond.class;
            list[313] = ItemBootsDiamond.class;
            list[314] = ItemHelmetGold.class;
            list[315] = ItemChestplateGold.class;
            list[316] = ItemLeggingsGold.class;
            list[317] = ItemBootsGold.class;
            list[318] = ItemFlint.class;
            list[319] = ItemPorkchopRaw.class;
            list[320] = ItemPorkchopCooked.class;
            list[321] = ItemPainting.class;
            list[322] = ItemAppleGold.class;
            list[323] = ItemSign.class;
            list[324] = ItemDoorWood.class;
            list[325] = ItemBucket.class;
            list[328] = ItemMinecart.class;
            list[329] = ItemSaddle.class;
            list[330] = ItemDoorIron.class;
            list[331] = ItemRedstone.class;
            list[332] = ItemSnowball.class;
            list[333] = ItemBoat.class;
            list[334] = ItemLeather.class;
            list[336] = ItemBrick.class;
            list[337] = ItemClay.class;
            list[338] = ItemSugarcane.class;
            list[339] = ItemPaper.class;
            list[340] = ItemBook.class;
            list[341] = ItemSlimeball.class;
            list[342] = ItemMinecartChest.class;
            list[344] = ItemEgg.class;
            list[345] = ItemCompass.class;
            list[346] = ItemFishingRod.class;
            list[347] = ItemClock.class;
            list[348] = ItemGlowstoneDust.class;
            list[349] = ItemFish.class;
            list[350] = ItemFishCooked.class;
            list[351] = ItemDye.class;
            list[352] = ItemBone.class;
            list[353] = ItemSugar.class;
            list[354] = ItemCake.class;
            list[355] = ItemBed.class;
            list[356] = ItemRedstoneRepeater.class;
            list[357] = ItemCookie.class;
            list[358] = ItemMap.class;
            list[359] = ItemShears.class;
            list[360] = ItemMelon.class;
            list[361] = ItemSeedsPumpkin.class;
            list[362] = ItemSeedsMelon.class;
            list[363] = ItemBeefRaw.class;
            list[364] = ItemSteak.class;
            list[365] = ItemChickenRaw.class;
            list[366] = ItemChickenCooked.class;
            list[367] = ItemRottenFlesh.class;
            list[368] = ItemEnderPearl.class;
            list[369] = ItemBlazeRod.class;
            list[370] = ItemGhastTear.class;
            list[371] = ItemNuggetGold.class;
            list[372] = ItemNetherWart.class;
            list[373] = ItemPotion.class;
            list[374] = ItemGlassBottle.class;
            list[375] = ItemSpiderEye.class;
            list[376] = ItemSpiderEyeFermented.class;
            list[377] = ItemBlazePowder.class;
            list[378] = ItemMagmaCream.class;
            list[379] = ItemBrewingStand.class;
            list[380] = ItemCauldron.class;
            list[381] = ItemEnderEye.class;
            list[382] = ItemMelonGlistering.class;
            list[383] = ItemSpawnEgg.class;
            list[384] = ItemExpBottle.class;
            list[385] = ItemFireCharge.class;
            list[387] = ItemBookWritten.class;
            list[388] = ItemEmerald.class;
            list[389] = ItemItemFrame.class;
            list[390] = ItemFlowerPot.class;
            list[391] = ItemCarrot.class;
            list[392] = ItemPotato.class;
            list[393] = ItemPotatoBaked.class;
            list[394] = ItemPotatoPoisonous.class;
            list[397] = ItemSkull.class;
            list[398] = ItemCarrotOnAStick.class;
            list[399] = ItemNetherStar.class;
            list[400] = ItemPumpkinPie.class;
            list[401] = ItemFirework.class;
            list[403] = ItemBookEnchanted.class;
            list[404] = ItemRedstoneComparator.class;
            list[405] = ItemNetherBrick.class;
            list[406] = ItemQuartz.class;
            list[407] = ItemMinecartTNT.class;
            list[408] = ItemMinecartHopper.class;
            list[409] = ItemPrismarineShard.class;
            list[410] = ItemHopper.class;
            list[411] = ItemRabbitRaw.class;
            list[412] = ItemRabbitCooked.class;
            list[413] = ItemRabbitStew.class;
            list[414] = ItemRabbitFoot.class;
            list[416] = ItemHorseArmorLeather.class;
            list[417] = ItemHorseArmorIron.class;
            list[418] = ItemHorseArmorGold.class;
            list[419] = ItemHorseArmorDiamond.class;
            list[422] = ItemPrismarineCrystals.class;
            list[423] = ItemMuttonRaw.class;
            list[424] = ItemMuttonCooked.class;
            list[426] = ItemEndCrystal.class;
            list[427] = ItemDoorSpruce.class;
            list[428] = ItemDoorBirch.class;
            list[429] = ItemDoorJungle.class;
            list[430] = ItemDoorAcacia.class;
            list[431] = ItemDoorDarkOak.class;
            list[432] = ItemChorusFruit.class;
            list[438] = ItemPotionSplash.class;
            list[441] = ItemPotionLingering.class;
            list[444] = ItemElytra.class;
            list[446] = ItemBanner.class;
            list[455] = ItemTrident.class;
            list[457] = ItemBeetroot.class;
            list[458] = ItemSeedsBeetroot.class;
            list[459] = ItemBeetrootSoup.class;
            list[460] = ItemSalmon.class;
            list[461] = ItemClownfish.class;
            list[462] = ItemPufferfish.class;
            list[463] = ItemSalmonCooked.class;
            list[464] = ItemDriedKelp.class;
            list[466] = ItemAppleGoldEnchanted.class;
            list[469] = ItemTurtleShell.class;
            list[510] = ItemRecord11.class;
            list[501] = ItemRecordCat.class;
            list[500] = ItemRecord13.class;
            list[502] = ItemRecordBlocks.class;
            list[503] = ItemRecordChirp.class;
            list[504] = ItemRecordFar.class;
            list[509] = ItemRecordWard.class;
            list[505] = ItemRecordMall.class;
            list[506] = ItemRecordMellohi.class;
            list[507] = ItemRecordStal.class;
            list[508] = ItemRecordStrad.class;
            list[511] = ItemRecordWait.class;

            for(int i = 0; i < 256; ++i) {
                if (Block.list[i] != null) {
                    list[i] = Block.list[i];
                }
            }
        }
    }


    public static Item get(int id) {
        return get(id, 0);
    }

    public static Item get(int id, Integer meta) {
        return get(id, meta, 1);
    }

    public static Item get(int id, Integer meta, int count) {
        return get(id, meta, count, new byte[0]);
    }

    public static Item get(int id, Integer meta, int count, byte[] tags) {
        try {
            Class c = list[id];
            Object item;
            if (c == null) {
                item = new Item(id, meta, count);
            } else if (id < 256) {
                if (meta >= 0) {
                    item = new ItemBlock(Block.get(id, meta), meta, count);
                } else {
                    item = new ItemBlock(Block.get(id), meta, count);
                }
            } else {
                item = (Item)c.getConstructor(Integer.class, Integer.TYPE).newInstance(meta, count);
            }

            if (tags.length != 0) {
                ((Item)item).setCompoundTag(tags);
            }

            return (Item)item;
        } catch (Exception var6) {
            return (new Item(id, meta, count)).setCompoundTag(tags);
        }
    }

    public static Item fromString(String str) {
        String[] b = str.trim().replace(' ', '_').replace("minecraft:", "").split(":");
        int id = 0;
        int meta = 0;
        Pattern integerPattern = Pattern.compile("^[1-9]\\d*$");
        if (integerPattern.matcher(b[0]).matches()) {
            id = Integer.valueOf(b[0]);
        } else {
            try {
                id = Item.class.getField(b[0].toUpperCase()).getInt((Object)null);
            } catch (Exception var6) {
            }
        }

        id &= 65535;
        if (b.length != 1) {
            meta = Integer.valueOf(b[1]) & '\uffff';
        }

        return get(id, meta);
    }

    public static Item fromJson(Map<String, Object> data) {
        String nbt = (String)data.getOrDefault("nbt_hex", "");
        return get(Utils.toInt(data.get("id")), Utils.toInt(data.getOrDefault("damage", 0)), Utils.toInt(data.getOrDefault("count", 1)), nbt.isEmpty() ? new byte[0] : Utils.parseHexBinary(nbt));
    }

    public static Item[] fromStringMultiple(String str) {
        String[] b = str.split(",");
        Item[] items = new Item[b.length - 1];

        for(int i = 0; i < b.length; ++i) {
            items[i] = fromString(b[i]);
        }

        return items;
    }
}
