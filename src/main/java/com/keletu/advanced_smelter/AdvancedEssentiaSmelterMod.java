package com.keletu.advanced_smelter;

import com.keletu.advanced_smelter.block.BlockAlchemicalSmelter;
import com.keletu.advanced_smelter.block.BlockChestGrate;
import com.keletu.advanced_smelter.client.render.TileAlchemicalSmelterAdvancedRenderer;
import com.keletu.advanced_smelter.gui.GuiHandler;
import com.keletu.advanced_smelter.tile.TileAlchemicalSmelterAdvanced;
import com.keletu.advanced_smelter.tile.TileAlchemicalSmelterAdvancedNozzle;
import com.keletu.advanced_smelter.tile.TileChestGrate;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.blocks.BlocksTC;
import thaumcraft.api.crafting.IDustTrigger;
import thaumcraft.api.crafting.Part;
import thaumcraft.api.crafting.ShapedArcaneRecipe;
import thaumcraft.api.items.ItemsTC;
import thaumcraft.common.lib.crafting.DustTriggerMultiblock;
import thecodex6824.thaumicaugmentation.api.TABlocks;

@Mod(
        modid = AdvancedEssentiaSmelterMod.MOD_ID,
        name = AdvancedEssentiaSmelterMod.MOD_NAME,
        version = AdvancedEssentiaSmelterMod.VERSION,
        dependencies = AdvancedEssentiaSmelterMod.dependencies
)
public class AdvancedEssentiaSmelterMod {

    public static final String MOD_ID = "advanced_smelter";
    public static final String MOD_NAME = "Advanced Essentia Smelter";
    public static final String VERSION = "1.0.0";
    public static final String dependencies = "required-after:thaumcraft@[6.1.BETA26,);required-after:thaumicaugmentation";

    public static AdvancedEssentiaSmelterMod instance;
    public static Block alchemyFurnace = new BlockAlchemicalSmelter();
    public static Block alchgrate = new BlockChestGrate();

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        instance = this;

        ForgeRegistries.BLOCKS.register(alchemyFurnace);
        ForgeRegistries.ITEMS.register(new ItemBlock(alchemyFurnace).setRegistryName("advanced_essentia_smelter"));

        ForgeRegistries.BLOCKS.register(alchgrate);
        ForgeRegistries.ITEMS.register(new ItemBlock(alchgrate).setRegistryName("alchemical_grate"));

        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        GameRegistry.registerTileEntity(TileAlchemicalSmelterAdvanced.class, "tk_advanced_smelter");
        GameRegistry.registerTileEntity(TileAlchemicalSmelterAdvancedNozzle.class, "tk_nozzle");
        GameRegistry.registerTileEntity(TileChestGrate.class, "tk_chestgrate");

        Part A = new Part(BlocksTC.alembic, new ItemStack(alchemyFurnace, 1, 3));
        Part N = new Part(BlocksTC.metalAlchemical, new ItemStack(alchemyFurnace, 1, 4));
        Part AD = new Part(BlocksTC.metalAlchemicalAdvanced, new ItemStack(alchemyFurnace, 1, 1), true);
        Part S = new Part(BlocksTC.smelterBasic, new ItemStack(alchemyFurnace, 1, 0), true);
        Part AU = new Part(BlocksTC.metalAlchemicalAdvanced, new ItemStack(alchemyFurnace, 1, 2), true);
        IDustTrigger.registerDustTrigger(new DustTriggerMultiblock("ADVANCED_SMELTER",
                new Part[][][]{
                        {
                                {A, N, A},
                                {N, null, N},
                                {A, N, A}
                        },
                        {
                                {AU, AD, AU},
                                {AD, S, AD},
                                {AU, AD, AU}
                        }
                }));
        ThaumcraftApi.addMultiblockRecipeToCatalog(new ResourceLocation(AdvancedEssentiaSmelterMod.MOD_ID, "advanced_smelter"), new ThaumcraftApi.BluePrint(
                "ADVANCED_SMELTER",
                new Part[][][]{
                        {
                                {A, N, A},
                                {N, null, N},
                                {A, N, A}
                        },
                        {
                                {AU, AD, AU},
                                {AD, S, AD},
                                {AU, AD, AU}
                        }
                },
                new ItemStack(BlocksTC.alembic, 4),
                new ItemStack(BlocksTC.metalAlchemical, 4),
                new ItemStack(BlocksTC.metalAlchemicalAdvanced, 8),
                new ItemStack(BlocksTC.smelterBasic)
        ));

        ThaumcraftApi.registerResearchLocation(new ResourceLocation("advanced_smelter", "research/research.json"));
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation(MOD_ID, "alchemical_grate"), new ShapedArcaneRecipe(
                new ResourceLocation(""),
                "ALCHEMY_GRATE",
                50,
                new AspectList().add(Aspect.ORDER, 3).add(Aspect.ENTROPY, 3),
                new ItemStack(alchgrate, 1),
                "VGV", "VSV", "VCV",
                'V', new ItemStack(ItemsTC.ingots, 1, 1),
                'G', new ItemStack(TABlocks.ITEM_GRATE),
                'S', new ItemStack(ItemsTC.mind),
                'C', new ItemStack(BlocksTC.hungryChest)));
    }

    @Mod.EventBusSubscriber
    public static class ObjectRegistryHandler {


        @SubscribeEvent
        public static void addBlocks(RegistryEvent.Register<Block> event) {

        }

        @SubscribeEvent
        public static void addItems(RegistryEvent.Register<Item> event) {

        }

        @SubscribeEvent
        @SideOnly(Side.CLIENT)
        public static void modelRegistryEvent(ModelRegistryEvent event) {
            for (int i = 0; i < 6; i++)
                ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(alchemyFurnace), i, new ModelResourceLocation(AdvancedEssentiaSmelterMod.MOD_ID + ":" + alchemyFurnace.getTranslationKey().substring(5), "inventory"));
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(alchgrate), 0, new ModelResourceLocation(AdvancedEssentiaSmelterMod.MOD_ID + ":" + alchgrate.getTranslationKey().substring(5), "inventory"));

            ClientRegistry.bindTileEntitySpecialRenderer(TileAlchemicalSmelterAdvanced.class, new TileAlchemicalSmelterAdvancedRenderer());
        }
    }

    @Config(modid = MOD_ID)
    public static class AAFConfig {

        @Config.Comment("Maximum capacity of AAF storage aspects")
        @Config.RangeInt(min = 100, max = 5000)
        public static int MAXIMUM = 2000;

        @Config.Comment("How much essentia can 1 point of impetus produce?")
        @Config.RangeInt(min = 1, max = 500)
        public static int EFFICIENCY = 50;


        @Mod.EventBusSubscriber(modid = MOD_ID)
        private static class EventHandler {
            @SubscribeEvent
            public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event) {
                if (event.getModID().equals(MOD_ID)) {
                    ConfigManager.sync(MOD_ID, Config.Type.INSTANCE);
                }
            }
        }
    }
}
