package net.yungtechboy1.CyberCore.Custom;

import cn.nukkit.Server;
import cn.nukkit.block.Block;
import cn.nukkit.utils.BinaryStream;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.unimi.dsi.fastutil.ints.Int2IntArrayMap;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by carlt on 3/28/2019.
 */
public class CustomGlobalBlockPalette {
    private static final Int2IntArrayMap legacyToRuntimeId = new Int2IntArrayMap();
    private static final Int2IntArrayMap runtimeIdToLegacy = new Int2IntArrayMap();
    private static final AtomicInteger runtimeIdAllocator = new AtomicInteger(0);
    private static byte[] compiledTable = new byte[0];

    static {
        legacyToRuntimeId.defaultReturnValue(-1);
        runtimeIdToLegacy.defaultReturnValue(-1);
//IP = new Config(new File(Main.getDataFolder(), "IPs.yml"), Config.YAML);
        try {
//            InputStream stream = new FileInputStream(new File(CyberCoreMain.getInstance().getDataFolder(), "blocks.json"));

            InputStream stream = Server.class.getClassLoader().getResourceAsStream("runtimeid_table.json");
            if (stream == null) {
                throw new AssertionError("Unable to locate RuntimeID table");
            }
            Reader reader = new InputStreamReader(stream, StandardCharsets.UTF_8);

            Gson gson = new Gson();
            Type collectionType = new TypeToken<Collection<CustomGlobalBlockPalette.TableEntry>>() {
            }.getType();
            Collection<CustomGlobalBlockPalette.TableEntry> entries = gson.fromJson(reader, collectionType);
            BinaryStream table = new BinaryStream();

            TableEntry t1 = new TableEntry();
            t1.id = Block.ENCHANT_TABLE;
            t1.data = 1;
            t1.name = "minecraft:enchanting_table";
            TableEntry t2 = new TableEntry();
            t2.id = Block.ENCHANT_TABLE;
            t2.data = 2;
            t2.name = "minecraft:enchanting_table";
            TableEntry t3 = new TableEntry();
            t3.id = Block.ENCHANT_TABLE;
            t3.data = 3;
            t3.name = "minecraft:enchanting_table";
            TableEntry t4 = new TableEntry();
            t4.id = Block.ENCHANT_TABLE;
            t4.data = 4;
            t4.name = "minecraft:enchanting_table";
            TableEntry t5 = new TableEntry();
            t5.id = Block.ENCHANT_TABLE;
            t5.data = 5;
            t5.name = "minecraft:enchanting_table";


            entries.add(t1);
            entries.add(t2);
            entries.add(t3);
            entries.add(t4);
            entries.add(t5);


            table.putUnsignedVarInt(entries.size());

            for (CustomGlobalBlockPalette.TableEntry entry : entries) {
                registerMapping((entry.id << 4) | entry.data);
                table.putString(entry.name);
                table.putLShort(entry.data);
            }

            compiledTable = table.getBuffer();
        } catch (Exception e) {
            Server.getInstance().getLogger().alert("Error! AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        }
    }

    public static int getOrCreateRuntimeId(int id, int meta) {
        return getOrCreateRuntimeId((id << 4) | meta);
    }

    public static int getOrCreateRuntimeId(int legacyId) throws NoSuchElementException {
        int runtimeId = legacyToRuntimeId.get(legacyId);
        if (runtimeId == -1) {
            //runtimeId = registerMapping(runtimeIdAllocator.incrementAndGet(), legacyId);
            throw new NoSuchElementException("Unmapped block registered id:" + (legacyId >>> 4) + " meta:" + (legacyId & 0xf));
        }
        return runtimeId;
    }

    public static int registerMapping(int legacyId) {
        int runtimeId = runtimeIdAllocator.getAndIncrement();
        runtimeIdToLegacy.put(runtimeId, legacyId);
        legacyToRuntimeId.put(legacyId, runtimeId);
        return runtimeId;
    }

    public static byte[] getCompiledTable() {
        return compiledTable;
    }

    private static class TableEntry {
        private int id;
        private int data;
        private String name;
    }
}
