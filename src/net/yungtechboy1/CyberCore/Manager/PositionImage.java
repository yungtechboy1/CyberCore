package net.yungtechboy1.CyberCore.Manager;

import cn.nukkit.block.*;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.level.format.generic.BaseFullChunk;
import cn.nukkit.math.NukkitMath;
import cn.nukkit.math.Vector3;
import org.omg.CORBA.PUBLIC_MEMBER;

import java.awt.image.BufferedImage;

/**
 * Created by carlt on 5/15/2019.
 */
public class PositionImage {
    int Size = 64;
    int x, y, z;
    Level Level;


    public PositionImage(int size, int x, int y, int z, cn.nukkit.level.Level level) {
        Size = size;
        this.x = x;
        this.y = y;
        this.z = z;
        Level = level;
    }

    public BufferedImage CreateImg() {
        //image dimension
        int width = Size;
        int height = Size;
//create buffered image object img
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);//PNG
        for (int zz = 0; zz < height; zz++) {
            for (int xx = 0; xx < width; xx++) {
                img.setRGB(xx,zz,RGBit(125, 233, 58));
            }
        }

        for (int zz = 0; zz < height; zz++) {
            for (int xx = 0; xx < width; xx++) {
                Vector3 b = GetTopBlock(new Vector3(this.x+xx,0,this.z+zz));
                int k = GetBlockColor(Level.getBlock(b));
                img.setRGB(xx,zz,k);
            }
        }
        return img;
    }

    private int RGBit(int r){
        return RGBit(r,r,r,100);
    }
    private int RGBit(int r,int g){
        return RGBit(r,g,g,100);
    }
    private int RGBit(int r,int g, int b){
        return RGBit(r,g,b,100);
    }
    private int RGBit(int r,int g, int b, int a){
        return (a << 24) | (r << 16) | (g << 8) | b;
    }

    //https://minecraft-el.gamepedia.com/Map_item_format
    public int GetBlockColor(Block b) {
        switch (b.getId()) {
            case BlockID.STONE:
                return RGBit(112 );
            case BlockID.GRASS:
            case BlockID.SLIME_BLOCK:
                return RGBit(127, 178, 56);
            case BlockID.SAND:
            case BlockID.SANDSTONE:
            case BlockID.SANDSTONE_STAIRS:
            case BlockID.END_STONE:
            case BlockID.WOOD:
                if(b.getId() == BlockID.WOOD) {
                    BlockWood bw = (BlockWood) b;
                    if (bw.getDamage() != bw.BIRCH) break;
                }
            case BlockID.FENCE:
                if(b.getId() == BlockID.FENCE) {
                    BlockFence bw = (BlockFence) b;
                    if (bw.getDamage() != bw.FENCE_BIRCH) break;
                }
            case BlockID.FENCE_GATE:
            case BlockID.FENCE_GATE_BIRCH:
            case BlockID.BIRCH_WOODEN_STAIRS:
            case BlockID.WOODEN_SLAB:
            case BlockID.DOUBLE_WOOD_SLAB:
                if(b.getId() == BlockID.WOODEN_SLAB ) {
                    if (b.getDamage() != 2&&b.getDamage() != 10) break;
                }else if( b.getId() == BlockID.DOUBLE_WOOD_SLAB){
                    if (b.getDamage() != 2) break;
                }
            case BlockID.BONE_BLOCK:
            case BlockID.END_BRICKS:
            case BlockID.BROWN_MUSHROOM:
            case BlockID.BROWN_MUSHROOM_BLOCK:
            case BlockID.RED_MUSHROOM:
            case BlockID.RED_MUSHROOM_BLOCK:
                return RGBit(247, 233, 163);//2
            case BlockID.BED_BLOCK:
            case BlockID.COBWEB:
                return RGBit(199);//3
            case BlockID.LAVA:
            case BlockID.STILL_LAVA:
            case BlockID.TNT:
            case BlockID.REDSTONE_BLOCK:
                return RGBit(255,0);//4
            case BlockID.ICE:
            case BlockID.ICE_FROSTED:
            case BlockID.PACKED_ICE:
                return RGBit(160,160,255);//5
            case BlockID.IRON_BLOCK:
            case BlockID.IRON_DOOR_BLOCK:
            case BlockID.IRON_TRAPDOOR:
            case BlockID.IRON_BAR:
            case BlockID.BREWING_BLOCK:
            case BlockID.ANVIL:
            case BlockID.HEAVY_WEIGHTED_PRESSURE_PLATE:
                return RGBit(167);//6
            case BlockID.SAPLING:
            case BlockID.LEAVE:
            case BlockID.LEAVE2:
            case BlockID.TALL_GRASS:
            case BlockID.DEAD_BUSH:
            case BlockID.FLOWER:
            case BlockID.SUGARCANE_BLOCK:
            case BlockID.PUMPKIN_STEM:
            case BlockID.MELON_STEM:
            case BlockID.VINE:
            case BlockID.LILY_PAD:
                return RGBit(0,124,0);//7


            case BlockID.GLASS:
            case BlockID.GLASS_PANE:
                break;
            case BlockID.AIR:
            default:
                return Integer.MIN_VALUE;
        }

        return Integer.MIN_VALUE;
    }

    public Vector3 GetTopBlock(Vector3 spawn) {
        Vector3 v = spawn.floor();
        FullChunk chunk = Level.getChunk((int) v.x >> 4, (int) v.z >> 4, false);
        int xx = (int) v.x & 15;
        int zz = (int) v.z & 15;
        if (chunk != null) {
            int y = 254;

            int b;
            Block block;
            for (int i = 254; i > 0; i--) {
//                System.out.println(x+' '+i+" "+z+' ');
                int bid = chunk.getBlockId(xx, i, zz);
                boolean wasAir = bid == 0;
                if (wasAir || GetBlockColor(Block.get(bid)) == Integer.MIN_VALUE) continue;
//                System.out.println(xx+" "+i+" "+zz+" >>> "+bid);
//                System.out.println("PASS");
                b = chunk.getFullBlock(xx, i, zz);
                block = Block.get(b >> 4, b & 15);
                if(!block.isSolid())continue;
//                System.out.println("PASS!");
                return new Vector3(spawn.getFloorX(), i, spawn.getFloorZ());
            }
            v.y = 200;
        }
        return spawn;
    }
}
