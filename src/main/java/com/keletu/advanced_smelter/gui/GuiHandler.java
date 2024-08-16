package com.keletu.advanced_smelter.gui;

import com.keletu.advanced_smelter.container.ContainerChestGrate;
import com.keletu.advanced_smelter.tile.TileChestGrate;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

    public static final int CONTAINER = 0;

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if(ID == CONTAINER)
            return new ContainerChestGrate(player.inventory, (TileChestGrate) world.getTileEntity(new BlockPos(x,y,z)), player);
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if(ID == CONTAINER)
            return new GuiChestGrate(player.inventory, (TileChestGrate) world.getTileEntity(new BlockPos(x,y,z)));
        return null;
    }
}