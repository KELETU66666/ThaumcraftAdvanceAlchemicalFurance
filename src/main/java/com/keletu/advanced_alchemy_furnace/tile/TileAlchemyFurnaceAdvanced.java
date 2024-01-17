package com.keletu.advanced_alchemy_furnace.tile;

import com.keletu.advanced_alchemy_furnace.AdvancedAlchemyFurnace;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.api.aspects.AspectHelper;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aura.AuraHelper;
import thaumcraft.common.lib.crafting.ThaumcraftCraftingManager;
import thaumcraft.common.tiles.TileThaumcraft;
import thecodex6824.thaumicaugmentation.ThaumicAugmentation;
import thecodex6824.thaumicaugmentation.api.impetus.node.CapabilityImpetusNode;
import thecodex6824.thaumicaugmentation.api.impetus.node.ConsumeResult;
import thecodex6824.thaumicaugmentation.api.impetus.node.IImpetusNode;
import thecodex6824.thaumicaugmentation.api.impetus.node.NodeHelper;
import thecodex6824.thaumicaugmentation.api.impetus.node.prefab.SimpleImpetusConsumer;
import thecodex6824.thaumicaugmentation.api.util.DimensionalBlockPos;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Deque;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class TileAlchemyFurnaceAdvanced
extends TileThaumcraft implements ITickable {
    public AspectList aspects = new AspectList();
    public int vis;
    public int maxVis = AdvancedAlchemyFurnace.AAFConfig.MAXIMUM;
    public int power1 = 0;
    public int maxPower = 500;
    public int heat = 0;
    public boolean destroy = false;
    int count = 0;
    int processed = 0;
    protected SimpleImpetusConsumer node = new SimpleImpetusConsumer(1, 0);
    private int ticks = ThreadLocalRandom.current().nextInt(20);

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
        nbttagcompound.setTag("node", node.serializeNBT());
        return nbttagcompound;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtCompound) {
        super.readFromNBT(nbtCompound);
        this.aspects.readFromNBT(nbtCompound);
        this.node.deserializeNBT(nbtCompound.getCompoundTag("node"));
        this.power1 = nbtCompound.getShort("power1");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbtCompound) {
        super.writeToNBT(nbtCompound);
        this.aspects.writeToNBT(nbtCompound);
        nbtCompound.setTag("node", node.serializeNBT());
        nbtCompound.setShort("power1", (short)this.power1);
        return nbtCompound;
    }

    @Override
    public void handleUpdateTag(NBTTagCompound tag) {
        super.handleUpdateTag(tag);
        node.init(world);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        super.onDataPacket(net, pkt);
        if (this.world != null) {
            this.world.checkLightFor(EnumSkyBlock.BLOCK, this.getPos());
        }
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
                if (this.power1 <= this.maxPower && ticks++ % 10 == 0) {
                    ConsumeResult result = node.consume(1, false);
                    if (result.energyConsumed == 1) {
                        this.power1 += result.energyConsumed * AdvancedAlchemyFurnace.AAFConfig.EFFICIENCY;
                        world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 6);
                        world.addBlockEvent(pos, getBlockType(), 1, 0);
                        NodeHelper.syncAllImpetusTransactions(result.paths.keySet());
                        for (Map.Entry<Deque<IImpetusNode>, Long> entry : result.paths.entrySet())
                            NodeHelper.damageEntitiesFromTransaction(entry.getKey(), entry.getValue());
                    }
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
            this.power1 -= aa;
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
        if (al.size() == 0) {
            return false;
        }
        int vs = al.visSize();
        return vs + this.aspects.visSize() <= this.maxVis;
    }

    @Override
    public void setPos(BlockPos posIn) {
        super.setPos(posIn);
        if (world != null)
            node.setLocation(new DimensionalBlockPos(pos.toImmutable(), world.provider.getDimension()));
    }

    @Override
    public void setWorld(World worldIn) {
        super.setWorld(worldIn);
        node.setLocation(new DimensionalBlockPos(pos.toImmutable(), world.provider.getDimension()));
    }

    @Override
    public void onLoad() {
        node.init(world);
        ThaumicAugmentation.proxy.registerRenderableImpetusNode(node);
    }

    @Override
    public void invalidate() {
        if (!world.isRemote)
            NodeHelper.syncDestroyedImpetusNode(node);

        node.destroy();
        ThaumicAugmentation.proxy.deregisterRenderableImpetusNode(node);
        super.invalidate();
    }

    @Override
    public void onChunkUnload() {
        node.unload();
        ThaumicAugmentation.proxy.deregisterRenderableImpetusNode(node);
    }

    @Override
    public boolean receiveClientEvent(int id, int type) {
        return true;
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
        return oldState.getBlock() != newState.getBlock();
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityImpetusNode.IMPETUS_NODE)
            return true;
        else
            return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityImpetusNode.IMPETUS_NODE)
            return CapabilityImpetusNode.IMPETUS_NODE.cast(node);
        else
            return super.getCapability(capability, facing);
    }
}
