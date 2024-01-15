package com.keletu.advanced_alchemy_furnace.tile;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.api.aspects.AspectHelper;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aura.AuraHelper;
import thaumcraft.common.lib.crafting.ThaumcraftCraftingManager;
import thaumcraft.common.tiles.TileThaumcraft;

import javax.annotation.Nonnull;

public class TileAlchemyFurnaceAdvanced
extends TileThaumcraft implements ITickable {
    public AspectList aspects = new AspectList();
    public int vis;
    public int maxVis = 500;
    public int power1 = 500;
    public int maxPower = 500;
    public int heat = 0;
    public boolean destroy = false;
    int count = 0;
    int processed = 0;

    @SideOnly(value= Side.CLIENT)
    @Nonnull
    public AxisAlignedBB getRenderBoundingBox() {
        return new AxisAlignedBB(this.getPos().getX() - 1, this.getPos().getY(), this.getPos().getZ() - 1, this.getPos().getX() + 2, this.getPos().getY() + 2, this.getPos().getZ() + 2);
    }

    @Override
    public void readSyncNBT(NBTTagCompound nbttagcompound) {
        this.vis = nbttagcompound.getShort("vis");
        this.heat = nbttagcompound.getShort("heat");
    }

    @Override
    public NBTTagCompound writeSyncNBT(NBTTagCompound nbttagcompound) {
        nbttagcompound.setShort("vis", (short)this.vis);
        nbttagcompound.setShort("heat", (short)this.heat);
        return nbttagcompound;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtCompound) {
        super.readFromNBT(nbtCompound);
        this.aspects.readFromNBT(nbtCompound);
        this.power1 = nbtCompound.getShort("power1");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbtCompound) {
        super.writeToNBT(nbtCompound);
        this.aspects.writeToNBT(nbtCompound);
        nbtCompound.setShort("power1", (short)this.power1);
        return nbtCompound;
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        super.onDataPacket(net, pkt);
        if (this.world != null) {
            this.world.checkLightFor(EnumSkyBlock.BLOCK, this.getPos());
        }
    }

    public boolean canUpdate() {
        return true;
    }

    public void update() {
        ++this.count;
        if (!this.world.isRemote) {
            if (this.destroy) {
                for (int a = -1; a <= 1; ++a) {
                    for (int b = 0; b <= 1; ++b) {
                        for (int c = -1; c <= 1; ++c) {
                            if (a == 0 && b == 0 && c == 0 || this.world.getBlockState(new BlockPos(this.getPos().getX() + a, this.getPos().getY() + b, this.getPos().getZ() + c)).getBlock() != this.getBlockType()) continue;
                            IBlockState m = this.world.getBlockState(new BlockPos(this.getPos().getX() + a, this.getPos().getY() + b, this.getPos().getZ() + c));
                            this.world.setBlockState(new BlockPos(this.getPos().add(a, b, c)), Block.getBlockFromItem(this.getBlockType().getItemDropped(m, this.world.rand, 0)).getStateFromMeta(this.getBlockType().damageDropped(m)), 3);
                        }
                    }
                }
                this.world.setBlockState(this.getPos(), Block.getBlockFromItem(this.getBlockType().getItemDropped(getBlockType().getDefaultState(), this.world.rand, 0)).getDefaultState(), 3);
                return;
            }
            if (this.processed > 0) {
                --this.processed;
            }
            if (this.count % 5 == 0) {
                int pt = this.heat--;
                if (this.heat <= this.maxPower) {
                    this.heat += AuraHelper.drainVis(getWorld(), getPos(), 0.5f, false) * 20;
                }
                if (pt / 50 != this.heat / 50) {
                    IBlockState block = this.getWorld().getBlockState(this.getPos());
                    this.world.notifyBlockUpdate(this.getPos(), block, block, 3);
                }
            }
        }
    }

    public boolean process(ItemStack stack) {
        if (this.processed == 0 && this.canSmelt(stack)) {
            AspectList al = ThaumcraftCraftingManager.getObjectTags(stack);
            int aa = (al = AspectHelper.getObjectAspects(stack)).visSize();
            if (aa * 2 > this.heat || aa > this.power1) {
                return false;
            }
            this.heat -= aa * 2;
            //this.power1 -= aa;
            this.processed = (int)((float)this.processed + (5.0f + Math.max(0.0f, (1.0f - (float)this.heat / (float)this.maxPower) * 100.0f)));
            this.aspects.add(al);
            this.vis = this.aspects.visSize();
            this.markDirty();
            this.world.getTileEntity(this.getPos());
            return true;
        }
        return false;
    }

    private boolean canSmelt(ItemStack stack) {
        if (stack == null) {
            return false;
        }
        AspectList al = ThaumcraftCraftingManager.getObjectTags(stack);
        if (/*(al = ThaumcraftCraftingManager.getBonusTags(stack, al)) == null || */al.size() == 0) {
            return false;
        }
        int vs = al.visSize();
        return vs + this.aspects.visSize() <= this.maxVis;
    }

}
