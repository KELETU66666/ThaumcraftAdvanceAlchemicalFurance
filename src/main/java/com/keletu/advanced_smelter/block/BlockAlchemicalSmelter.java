package com.keletu.advanced_smelter.block;

import com.keletu.advanced_smelter.tile.TileAlchemicalSmelterAdvanced;
import com.keletu.advanced_smelter.tile.TileAlchemicalSmelterAdvancedNozzle;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.api.blocks.BlocksTC;
import thaumcraft.client.fx.ParticleEngine;
import thaumcraft.client.fx.particles.FXSlimyBubble;

import java.util.Random;

public class BlockAlchemicalSmelter extends BlockContainer {

    public static final PropertyInteger METADATA = PropertyInteger.create("meta", 0, 5);

    public BlockAlchemicalSmelter() {
        super(Material.IRON);
        this.setTranslationKey("advanced_smelter");
        this.setRegistryName("advanced_smelter");
        this.setHardness(3.0f);
        this.setResistance(17.0f);
        this.setSoundType(SoundType.METAL);
        this.setDefaultState(this.blockState.getBaseState().withProperty(METADATA, 0));
        //this.getBoundingBox(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
    }

    public int getMetaFromState(IBlockState state) {
        return state.getValue(METADATA);
    }

    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(METADATA, meta % 5);
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, METADATA);
    }

    //@SideOnly(value=Side.CLIENT)
    //public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
    //    par3List.add(new ItemStack(par1, 1, 0));
    //}

    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.INVISIBLE;
    }

    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    public boolean isFullCube(IBlockState state) {
        return false;
    }

    public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        /*if(getMetaFromState(state) == 4)
            return new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.25, 1.0);
        else */if(getMetaFromState(state) == 0)
            return new AxisAlignedBB(0.0625, 0.0, 0.0625, 0.9375, 0.875, 0.9375);
        else if(getMetaFromState(state) == 3)
            return new AxisAlignedBB(0.125, 0.0, 0.125, 0.875, 1.0, 0.875);

        return FULL_BLOCK_AABB;
    }


    public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World worldIn, BlockPos pos) {
        return this.getCollisionBoundingBox(state, worldIn, pos);
    }

    //public void addCollisionBoxToList(IBlockState state, World world, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entity, boolean isActualState) {
    //    int md = getMetaFromState(world.getBlockState(pos));
    //    if (md == 0 && !(entity instanceof EntityLivingBase)) {
    //        this.getBoundingBox(0.0f, 0.0f, 0.0f, 1.0f, 0.7f, 1.0f);
    //        super.addCollisionBoxToList(state, world, pos, entityBox, collidingBoxes, entity, isActualState);
    //    } else {
    //        this.getBoundingBox(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
    //        super.addCollisionBoxToList(state, world, pos, entityBox, collidingBoxes, entity, isActualState);
    //    }
    //}

   //public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
   //    TileAlchemyFurnaceAdvanced tile;
   //    int metadata = getMetaFromState(world.getBlockState(pos));
   //    if (metadata == 0 && (tile = (TileAlchemyFurnaceAdvanced)world.getTileEntity(pos)) != null && tile.heat > 100) {
   //        return (int)((float)tile.heat / (float)tile.maxPower * 12.0f);
   //    }
   //    return super.getLightValue(state, world, pos);
   //}

    public void onEntityCollision(World world, BlockPos pos, IBlockState state, Entity entity) {
        TileAlchemicalSmelterAdvanced tile;
        int metadata;
        if (!world.isRemote && getMetaFromState(state) == 0 && (tile = (TileAlchemicalSmelterAdvanced)world.getTileEntity(pos)) != null && entity instanceof EntityItem && tile.process(((EntityItem)entity).getItem())) {
            ItemStack s = ((EntityItem)entity).getItem();
            s.setCount(s.getCount() - 1);
           // world.func_72956_a(entity, "thaumcraft:bubble", 0.2f, 1.0f + world.rand.nextFloat() * 0.4f);
            if (s.getCount() <= 0) {
                entity.setDead();
            } else {
                ((EntityItem)entity).setItem(s);
            }
        }
    }

    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        int md = getMetaFromState(state);
        if(md == 0)
            return  Item.getItemFromBlock(BlocksTC.smelterBasic);
        else if (md == 1)
            return Item.getItemFromBlock(BlocksTC.metalAlchemicalAdvanced);
        else if (md == 2)
            return Item.getItemFromBlock(BlocksTC.metalAlchemicalAdvanced);
        else if (md == 3)
            return Item.getItemFromBlock(BlocksTC.alembic);
        else if (md == 4)
            return Item.getItemFromBlock(BlocksTC.metalAlchemical);
        else return Item.getItemFromBlock(Blocks.AIR);
    }

    public int damageDropped(IBlockState state) {
        int metadata = getMetaFromState(state);
        if (metadata == 1 || metadata == 4) {
            return 3;
        }
        if (metadata == 3) {
            return 9;
        }
        if (metadata == 2) {
            return 1;
        }
        return 0;
    }

    public TileEntity createTileEntity(World world, IBlockState state) {
        int metadata = getMetaFromState(state);
        if (metadata == 0) {
            return new TileAlchemicalSmelterAdvanced();
        }
        if (metadata == 1) {
            return new TileAlchemicalSmelterAdvancedNozzle();
        }
        return super.createTileEntity(world, state);
    }

    public boolean hasComparatorInputOverride(IBlockState state) {
        return true;
    }

    @Override
    public int getComparatorInputOverride(IBlockState blockState, World world, BlockPos pos) {
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof TileAlchemicalSmelterAdvancedNozzle) {
            if (((TileAlchemicalSmelterAdvancedNozzle)te).furnace != null) {
                float r = (float)((TileAlchemicalSmelterAdvancedNozzle)te).furnace.vis / (float)((TileAlchemicalSmelterAdvancedNozzle)te).furnace.maxVis;
                return MathHelper.floor(r * 14.0f) + ((TileAlchemicalSmelterAdvancedNozzle)te).furnace.vis > 0 ? 1 : 0;
            }
            return 0;
        }
        return 0;
    }

    public TileEntity createNewTileEntity(World var1, int md) {
        return null;
    }

    public void breakBlock(World world, BlockPos pos, IBlockState bl) {
        int md = getMetaFromState(bl);
        block8: {
            if (world.isRemote) break block8;
            if (md != 0) {
                for (int a = -1; a <= 1; ++a) {
                    for (int b = -1; b <= 1; ++b) {
                        for (int c = -1; c <= 1; ++c) {
                            TileAlchemicalSmelterAdvanced tile;
                            if (world.getBlockState(pos.add(a, b, c)).getBlock() != this || getMetaFromState(world.getBlockState(pos.add(a, b, c))) != 0 || (tile = (TileAlchemicalSmelterAdvanced)world.getTileEntity(pos.add(a, b, c))) == null) continue;
                            tile.destroy = true;
                            break block8;
                        }
                    }
                }
            } else {
                for (int a = -1; a <= 1; ++a) {
                    for (int b = 0; b <= 1; ++b) {
                        for (int c = -1; c <= 1; ++c) {
                            if (a == 0 && b == 0 && c == 0 || world.getBlockState(pos.add(a, b, c)).getBlock() != this) continue;
                            IBlockState m = world.getBlockState(pos.add(a, b, c));
                            world.setBlockState(pos.add(a, b, c), Block.getBlockFromItem(this.getItemDropped(m, world.rand, 0)).getStateFromMeta(this.damageDropped(m)), 3);
                        }
                    }
                }
            }
        }
    }

    @SideOnly(value= Side.CLIENT)
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
        TileAlchemicalSmelterAdvanced tile;
        int meta = getMetaFromState(world.getBlockState(pos));
        if (meta == 0 && (tile = (TileAlchemicalSmelterAdvanced)world.getTileEntity(pos)) != null && tile.vis > 0) {
            FXSlimyBubble ef = new FXSlimyBubble(world, (float)pos.getX() + rand.nextFloat(), pos.getY() + 1, (float)pos.getZ() + rand.nextFloat(), 0.06f + rand.nextFloat() * 0.06f);
            ef.setAlphaF(0.8f);
            ef.setRBGColorF(0.6f - rand.nextFloat() * 0.2f, 0.0f, 0.6f + rand.nextFloat() * 0.2f);
            ParticleEngine.addEffect(world, ef);
            if (rand.nextInt(50) == 0) {
                double var21 = (float)pos.getX() + rand.nextFloat();
                double var22 = pos.getY();
                double var23 = (float)pos.getZ() + rand.nextFloat();
                world.playSound(var21, var22, var23, SoundEvents.BLOCK_LAVA_POP, SoundCategory.BLOCKS, 0.1f + rand.nextFloat() * 0.1f, 0.9f + rand.nextFloat() * 0.15f, false);
            }
            int q = rand.nextInt(2);
            int w = rand.nextInt(2);
            FXSlimyBubble ef2 = new FXSlimyBubble(world, (double)pos.getX() - 0.6 + (double)rand.nextFloat() * 0.2 + (double)(q * 2), pos.getY() + 2, (double)pos.getZ() - 0.6 + (double)rand.nextFloat() * 0.2 + (double)(w * 2), 0.06f + rand.nextFloat() * 0.06f);
            ef2.setAlphaF(0.8f);
            ef2.setRBGColorF(0.6f - rand.nextFloat() * 0.2f, 0.0f, 0.6f + rand.nextFloat() * 0.2f);
            ParticleEngine.addEffect(world, ef2);
        }
        super.randomDisplayTick(state, world, pos, rand);
    }
}
