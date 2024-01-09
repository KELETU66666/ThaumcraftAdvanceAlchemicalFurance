
package com.keletu.ancient_thaumcraft.tile;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.api.aspects.IEssentiaTransport;
import thaumcraft.common.tiles.TileThaumcraft;

public class TileAlchemyFurnaceAdvancedNozzle
extends TileThaumcraft
implements IAspectContainer,
IEssentiaTransport, ITickable {
    EnumFacing facing;
    public TileAlchemyFurnaceAdvanced furnace = null;

    public boolean canUpdate() {
        return this.facing != null;
    }

    public void update() {
        if (this.facing == null && this.furnace == null) {
            this.facing = null;
            for (EnumFacing dir : EnumFacing.VALUES) {
                TileEntity tile = this.world.getTileEntity(this.getPos().add(dir.getXOffset(), dir.getYOffset(), dir.getZOffset()));
                if (!(tile instanceof TileAlchemyFurnaceAdvanced)) continue;
                this.facing = dir.getOpposite();
                this.furnace = (TileAlchemyFurnaceAdvanced)tile;
                break;
            }
        }
    }

    @Override
    public AspectList getAspects() {
        return this.furnace != null ? this.furnace.aspects : null;
    }

    @Override
    public void setAspects(AspectList aspects) {
    }

    @Override
    public int addToContainer(Aspect tt, int am) {
        return am;
    }

    @Override
    public boolean takeFromContainer(Aspect tt, int am) {
        if (this.furnace == null) {
            return false;
        }
        if (this.furnace.aspects.getAmount(tt) >= am) {
            this.furnace.aspects.remove(tt, am);
            this.furnace.markDirty();
            this.furnace.vis = this.furnace.aspects.visSize();
            this.world.notifyBlockUpdate(this.furnace.getPos(), this.getWorld().getBlockState(this.furnace.getPos()), this.getWorld().getBlockState(this.furnace.getPos()), 3);
            return true;
        }
        return false;
    }

    @Override
    public boolean doesContainerContain(AspectList ot) {
        return false;
    }

    @Override
    public boolean doesContainerContainAmount(Aspect tt, int am) {
        if (this.furnace == null) {
            return false;
        }
        return this.furnace.aspects.getAmount(tt) >= am;
    }

    @Override
    public int containerContains(Aspect tt) {
        if (this.furnace == null) {
            return 0;
        }
        return this.furnace.aspects.getAmount(tt);
    }

    @Override
    public boolean doesContainerAccept(Aspect tag) {
        return false;
    }

    @Override
    public boolean takeFromContainer(AspectList ot) {
        return false;
    }

    @Override
    public boolean isConnectable(EnumFacing face) {
        return face == this.facing;
    }

    @Override
    public boolean canInputFrom(EnumFacing face) {
        return false;
    }

    @Override
    public boolean canOutputTo(EnumFacing face) {
        return face == this.facing;
    }

    @Override
    public void setSuction(Aspect aspect, int amount) {
    }

    @Override
    public int getMinimumSuction() {
        return 0;
    }

    @Override
    public Aspect getSuctionType(EnumFacing face) {
        return null;
    }

    @Override
    public int getSuctionAmount(EnumFacing face) {
        return 0;
    }

    @Override
    public Aspect getEssentiaType(EnumFacing loc) {
        return (this.furnace != null && this.furnace.aspects.getAspects().length > 0)  ? this.furnace.aspects.getAspects()[0] : null;
    }

    @Override
    public int getEssentiaAmount(EnumFacing loc) {
        return (this.furnace != null && this.furnace.aspects.getAspects().length > 0) ? this.furnace.aspects.getAmount(this.furnace.aspects.getAspects()[0]) : 0;
    }

    @Override
    public int takeEssentia(Aspect aspect, int amount, EnumFacing facing) {
        return this.canOutputTo(facing) && this.takeFromContainer(aspect, amount) ? amount : 0;
    }

    @Override
    public int addEssentia(Aspect aspect, int amount, EnumFacing facing) {
        return 0;
    }
}
