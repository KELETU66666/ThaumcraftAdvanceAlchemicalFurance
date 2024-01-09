package com.keletu.ancient_thaumcraft.proxy;

import com.keletu.ancient_thaumcraft.client.render.TileAlchemyFurnaceAdvancedRenderer;
import com.keletu.ancient_thaumcraft.client.render.TileManaPodRenderer;
import com.keletu.ancient_thaumcraft.init.KBlocks;
import com.keletu.ancient_thaumcraft.init.KItems;
import com.keletu.ancient_thaumcraft.item.ItemManaBean;
import com.keletu.ancient_thaumcraft.tile.TileAlchemyFurnaceAdvanced;
import com.keletu.ancient_thaumcraft.tile.TileManaPod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);

        BlockColors blockColors = Minecraft.getMinecraft().getBlockColors();
        ItemColors itemColors = Minecraft.getMinecraft().getItemColors();
        registerItemColourHandlers(blockColors, itemColors);
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
        KBlocks.Render();
        KItems.Render();
    }

    @Override
    public void modelRegistryEvent(ModelRegistryEvent event) {
        registerTileEntities();
     }

    public static void registerTileEntities()
    {
        ClientRegistry.bindTileEntitySpecialRenderer(TileManaPod.class, new TileManaPodRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileAlchemyFurnaceAdvanced.class, new TileAlchemyFurnaceAdvancedRenderer());

        //ForgeHooksClient.registerTESRItemStack(Item.getItemFromBlock(KBlocks.crafting_station), 0, TileTKCraftingStation.class);
    }

    private static void registerItemColourHandlers(BlockColors blockColors, ItemColors itemColors) {
        IItemColor itemCrystalPlanterColourHandler = (stack, tintIndex) -> {
            Item item = stack.getItem();
            if (item == KItems.mana_bean) {
                return ((ItemManaBean) item).getColor(stack, tintIndex);
            }
            return 16777215;
        };

        Minecraft.getMinecraft().getItemColors().registerItemColorHandler(itemCrystalPlanterColourHandler, KItems.mana_bean);

    }
}
