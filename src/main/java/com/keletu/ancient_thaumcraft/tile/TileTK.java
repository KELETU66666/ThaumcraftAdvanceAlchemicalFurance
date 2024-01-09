package com.keletu.ancient_thaumcraft.tile;

import net.minecraftforge.fml.common.registry.GameRegistry;

public class TileTK {
    public static void setUp(){
        GameRegistry.registerTileEntity(TileManaPod.class, "tk_mana_pod");
        GameRegistry.registerTileEntity(TileAlchemyFurnaceAdvanced.class, "tk_advanced_alchemy");
        GameRegistry.registerTileEntity(TileAlchemyFurnaceAdvancedNozzle.class, "tk_nozzle");
    }
}
