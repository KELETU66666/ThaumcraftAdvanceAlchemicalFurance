package com.keletu.ancient_thaumcraft.village;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;
import thaumcraft.common.world.biomes.BiomeGenMagicalForest;

import java.util.Random;


public class TKWorldGenerator implements IWorldGenerator
{
    public static TKWorldGenerator INSTANCE = new TKWorldGenerator();

    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        generateSurface(world, random, chunkX, chunkZ, true);
    }

    private void generateSurface(World world, Random random, int chunkX, int chunkZ, boolean newGen) {
        BlockPos pos =new BlockPos(chunkX * 16 + 8, world.getHeight(chunkX * 16 + 8, chunkZ * 16 + 8), chunkZ * 16 + 8);
        if(world.getBiome(pos) instanceof BiomeGenMagicalForest)
                new WorldGenManaPods().generate(world, random, pos);
    }
}