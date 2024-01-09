package com.keletu.ancient_thaumcraft.village;

import com.keletu.ancient_thaumcraft.init.KBlocks;
import com.keletu.ancient_thaumcraft.tile.TileManaPod;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WorldGenManaPods extends WorldGenerator {
  public boolean generate(World par1World, Random par2Random, BlockPos blockPos) {
    int x = blockPos.getX();
    int y = blockPos.getY();
    int z = blockPos.getZ();
    int l = x;
    for (int i1 = z; y < Math.min(128, par1World.getHeight(x, z)); y++) {
      if (par1World.isAirBlock(blockPos) && par1World.isAirBlock(new BlockPos(x, y - 1, z))) {
        if (KBlocks.mana_pod.canPlaceBlockOnSide(par1World, blockPos, EnumFacing.byIndex(0))) {
          par1World.setBlockState(blockPos, KBlocks.mana_pod.getStateFromMeta(2 + par2Random.nextInt(5)));
          TileEntity tile = par1World.getTileEntity(blockPos);
          if (tile instanceof TileManaPod)
            ((TileManaPod)tile).checkGrowth(); 
          break;
        } 
      } else {
        x = l + par2Random.nextInt(4) - par2Random.nextInt(4);
        z = i1 + par2Random.nextInt(4) - par2Random.nextInt(4);
      } 
    } 
    return true;
  }
}