package com.keletu.ancient_thaumcraft.init;

import com.google.common.base.Strings;
import com.keletu.ancient_thaumcraft.AncientThaumaturgy;
import com.keletu.ancient_thaumcraft.block.BlockAlchemyFurnace;
import com.keletu.ancient_thaumcraft.block.BlockManaPod;
import com.keletu.ancient_thaumcraft.block.BlockPechhead;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class KBlocks {
    public static Block pechHead_normal;
    public static Block pechHead_hunter;
    public static Block pechHead_thaumaturge;
    public static Block mana_pod;
    public static Block alchemyFurnace;

    public static void preInit() {
        pechHead_normal = new BlockPechhead().setTranslationKey("pech_skull_forage").setCreativeTab(AncientThaumaturgy.tab);
        pechHead_hunter = new BlockPechhead().setTranslationKey("pech_skull_stalker").setCreativeTab(AncientThaumaturgy.tab);
        pechHead_thaumaturge = new BlockPechhead().setTranslationKey("pech_skull_thaum").setCreativeTab(AncientThaumaturgy.tab);
        alchemyFurnace = new BlockAlchemyFurnace().setTranslationKey("advanced_alchemy_furnace");
        mana_pod = new BlockManaPod().setTranslationKey("mana_pod").setCreativeTab(AncientThaumaturgy.tab);
    }

    public static void registerBlocks() {
        KBlocks.registerBlock(pechHead_normal, pechHead_normal.getTranslationKey().substring(5));
        KBlocks.registerBlock(pechHead_hunter, pechHead_hunter.getTranslationKey().substring(5));
        KBlocks.registerBlock(pechHead_thaumaturge, pechHead_thaumaturge.getTranslationKey().substring(5));
        KBlocks.registerBlock(alchemyFurnace, alchemyFurnace.getTranslationKey().substring(5));
        KBlocks.registerBlockWithoutItem(mana_pod, mana_pod.getTranslationKey().substring(5));
    }

    public static Block registerBlock(Block block) {
        ForgeRegistries.BLOCKS.register(block);
        ForgeRegistries.ITEMS.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
        return block;
    }
    public static Block registerBlockWithoutItem(Block block) {
        ForgeRegistries.BLOCKS.register(block);
        return block;
    }
    public static Block registerBlock(Block block, String name) {
        if (block.getRegistryName() == null && Strings.isNullOrEmpty(name))
            throw new IllegalArgumentException("Attempted to register a Block with no name: " + block);

        return registerBlock(block.getRegistryName() != null ? block : block.setRegistryName(name));
    }

    public static Block registerBlockWithoutItem(Block block, String name) {
        if (block.getRegistryName() == null && Strings.isNullOrEmpty(name))
            throw new IllegalArgumentException("Attempted to register a Block with no name: " + block);

        return registerBlockWithoutItem(block.getRegistryName() != null ? block : block.setRegistryName(name));
    }

    public static void Render(){
        registerRender(alchemyFurnace);
        registerRender(pechHead_normal);
        registerRender(pechHead_hunter);
        registerRender(pechHead_thaumaturge);
        registerRender(mana_pod);
    }

    public static void registerRender(Block block) {
        Item item = Item.getItemFromBlock(block);
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(AncientThaumaturgy.MOD_ID + ":" + item.getTranslationKey().substring(5), "inventory"));
    }

}
