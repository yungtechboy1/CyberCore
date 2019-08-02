package net.yungtechboy1.CyberCore.Manager.ChunkTest;

import cn.nukkit.block.*;
import cn.nukkit.level.ChunkManager;
import cn.nukkit.level.biome.Biome;
import cn.nukkit.level.biome.BiomeSelector;
import cn.nukkit.level.generator.Generator;
import cn.nukkit.level.generator.noise.vanilla.f.NoiseGeneratorOctavesF;
import cn.nukkit.level.generator.noise.vanilla.f.NoiseGeneratorPerlinF;
import cn.nukkit.level.generator.object.ore.OreType;
import cn.nukkit.level.generator.populator.impl.*;
import cn.nukkit.level.generator.populator.type.Populator;
import cn.nukkit.math.NukkitRandom;
import cn.nukkit.math.Vector3;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class CustomChunkGenerator extends Generator {
    @Override
    public int getId() {
        return TYPE_INFINITE;
    }

    private final List<Populator> populators = new ArrayList<>();
    private final List<Populator> generationPopulators = new ArrayList<>();
    public static final int seaHeight = 64;
    public NoiseGeneratorOctavesF scaleNoise;
    public NoiseGeneratorOctavesF depthNoise;
    private ChunkManager level;
    private Random random;
    private NukkitRandom nukkitRandom;
    private long localSeed1;
    private long localSeed2;
    private BiomeSelector selector;
    private ThreadLocal<Biome[]> biomes = ThreadLocal.withInitial(() -> new Biome[10 * 10]);
    private ThreadLocal<float[]> depthRegion = ThreadLocal.withInitial(() -> null);
    private ThreadLocal<float[]> mainNoiseRegion = ThreadLocal.withInitial(() -> null);
    private ThreadLocal<float[]> minLimitRegion = ThreadLocal.withInitial(() -> null);
    private ThreadLocal<float[]> maxLimitRegion = ThreadLocal.withInitial(() -> null);
    private ThreadLocal<float[]> heightMap = ThreadLocal.withInitial(() -> new float[825]);
    private NoiseGeneratorOctavesF minLimitPerlinNoise;
    private NoiseGeneratorOctavesF maxLimitPerlinNoise;
    private NoiseGeneratorOctavesF mainPerlinNoise;
    private NoiseGeneratorPerlinF surfaceNoise;

    @Override
    public void init(ChunkManager chunkManager, NukkitRandom nukkitRandom) {
        this.level = chunkManager;
        this.nukkitRandom = nukkitRandom;
        this.random = new Random();
        this.nukkitRandom.setSeed(this.level.getSeed());
        this.localSeed1 = this.random.nextLong();
        this.localSeed2 = this.random.nextLong();
        this.nukkitRandom.setSeed(this.level.getSeed());
        this.selector = new BiomeSelector(this.nukkitRandom);

        this.minLimitPerlinNoise = new NoiseGeneratorOctavesF(nukkitRandom, 16);
        this.maxLimitPerlinNoise = new NoiseGeneratorOctavesF(nukkitRandom, 16);
        this.mainPerlinNoise = new NoiseGeneratorOctavesF(nukkitRandom, 8);
        this.surfaceNoise = new NoiseGeneratorPerlinF(nukkitRandom, 4);
        this.scaleNoise = new NoiseGeneratorOctavesF(nukkitRandom, 10);
        this.depthNoise = new NoiseGeneratorOctavesF(nukkitRandom, 16);

        //this should run before all other populators so that we don't do things like generate ground cover on bedrock or something
        PopulatorGroundCover cover = new PopulatorGroundCover();
        this.generationPopulators.add(cover);

        PopulatorBedrock bedrock = new PopulatorBedrock();
        this.generationPopulators.add(bedrock);

        PopulatorOre ores = new PopulatorOre();
        ores.setOreTypes(new OreType[]{
                new OreType(new BlockOreCoal(), 20, 17, 0, 128),
                new OreType(new BlockOreIron(), 20, 9, 0, 64),
                new OreType(new BlockOreRedstone(), 8, 8, 0, 16),
                new OreType(new BlockOreLapis(), 1, 7, 0, 16),
                new OreType(new BlockOreGold(), 2, 9, 0, 32),
                new OreType(new BlockOreDiamond(), 1, 8, 0, 16),
                new OreType(new BlockDirt(), 10, 33, 0, 128),
                new OreType(new BlockGravel(), 8, 33, 0, 128),
                new OreType(new BlockStone(BlockStone.GRANITE), 10, 33, 0, 80),
                new OreType(new BlockStone(BlockStone.DIORITE), 10, 33, 0, 80),
                new OreType(new BlockStone(BlockStone.ANDESITE), 10, 33, 0, 80)
        });
        this.populators.add(ores);

        PopulatorCaves caves = new PopulatorCaves();
        this.populators.add(caves);

        PopulatorRavines ravines = new PopulatorRavines();
        this.populators.add(ravines);
    }

    @Override
    public void generateChunk(int i, int i1) {

    }

    @Override
    public void populateChunk(int i, int i1) {

    }

    @Override
    public Map<String, Object> getSettings() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public Vector3 getSpawn() {
        return null;
    }

    @Override
    public ChunkManager getChunkManager() {
        return null;
    }
}
