package com.keletu.ancient_thaumcraft.init;

import com.keletu.ancient_thaumcraft.AncientThaumaturgy;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.util.ResourceLocation;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.blocks.BlocksTC;
import thaumcraft.api.crafting.CrucibleRecipe;
import thaumcraft.api.crafting.IDustTrigger;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.api.crafting.Part;
import thaumcraft.api.items.ItemsTC;
import thaumcraft.common.lib.crafting.DustTriggerMultiblock;

public class KRecipes {

    public static void TKStationRecipes() {

        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation(AncientThaumaturgy.MOD_ID, "shadow_metal"), new CrucibleRecipe(
                "SHADOW_METAL",
                new ItemStack(KItems.shadow_nugget),
                "nuggetIron",
                new AspectList().add(Aspect.DARKNESS, 15).add(Aspect.METAL, 30).add(Aspect.DARKNESS, 10)
        ));

        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(AncientThaumaturgy.MOD_ID, "shadow_fortress_helm"), new InfusionRecipe(
                "SHADOW_FORTRESS_ARMOR",
                new ItemStack(KItems.shadow_helm),
                16,
                new AspectList().add(Aspect.METAL, 100).add(Aspect.PROTECT, 80).add(Aspect.MAGIC, 45).add(Aspect.DARKNESS, 120).add(Aspect.VOID, 85),
                new ItemStack(ItemsTC.voidHelm),
                new ItemStack(KItems.shadow_ingot),
                new ItemStack(KItems.shadow_ingot),
                "ingotIron",
                "ingotIron",
                "gemEmerald"));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(AncientThaumaturgy.MOD_ID, "shadow_fortress_chest"), new InfusionRecipe(
                "SHADOW_FORTRESS_ARMOR",
                new ItemStack(KItems.shadow_chest),
                16,
                new AspectList().add(Aspect.METAL, 150).add(Aspect.PROTECT, 100).add(Aspect.MAGIC, 70).add(Aspect.DARKNESS, 150).add(Aspect.VOID, 100),
                new ItemStack(ItemsTC.voidChest),
                new ItemStack(KItems.shadow_ingot),
                new ItemStack(KItems.shadow_ingot),
                new ItemStack(KItems.shadow_ingot),
                new ItemStack(KItems.shadow_ingot),
                "ingotIron",
                "leather"));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(AncientThaumaturgy.MOD_ID, "shadow_fortress_legs"), new InfusionRecipe(
                "SHADOW_FORTRESS_ARMOR",
                new ItemStack(KItems.shadow_legs),
                16,
                new AspectList().add(Aspect.METAL, 125).add(Aspect.PROTECT, 90).add(Aspect.MAGIC, 65).add(Aspect.DARKNESS, 125).add(Aspect.VOID, 90),
                new ItemStack(ItemsTC.voidLegs),
                new ItemStack(KItems.shadow_ingot),
                new ItemStack(KItems.shadow_ingot),
                new ItemStack(KItems.shadow_ingot),
                "ingotIron",
                "leather"));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(AncientThaumaturgy.MOD_ID, "shadow_helm_goggles"), new InfusionRecipe(
                "SHADOW_FORTRESS_ARMOR",
                new Object[]{"goggles", new NBTTagInt(1)},
                5,
                (new AspectList()).add(Aspect.SENSES, 40).add(Aspect.AURA, 20).add(Aspect.PROTECT, 20),
                new ItemStack(KItems.shadow_helm, 1, 32767),
                new ItemStack(Items.SLIME_BALL),
                new ItemStack(ItemsTC.goggles, 1, 32767)));

        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(AncientThaumaturgy.MOD_ID, "shadow_helm_warp"), new InfusionRecipe(
                "SHADOW_FORTRESS_ARMOR",
                new Object[]{"mask", new NBTTagInt(0)},
                8,
                new AspectList().add(Aspect.MIND, 80).add(Aspect.LIFE, 80).add(Aspect.PROTECT, 20),
                new ItemStack(KItems.shadow_helm, 1, 32767),
                "plateIron",
                "dyeBlack",
                "plateIron",
                "leather",
                BlocksTC.shimmerleaf,
                ItemsTC.brain));

        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(AncientThaumaturgy.MOD_ID, "shadow_helm_wither"), new InfusionRecipe(
                "SHADOW_FORTRESS_ARMOR",
                new Object[]{"mask", new NBTTagInt(1)},
                8,
                new AspectList().add(Aspect.ENTROPY, 80).add(Aspect.DEATH, 80).add(Aspect.PROTECT, 20),
                new ItemStack(KItems.shadow_helm, 1, 32767),
                "plateIron",
                "dyeWhite",
                "plateIron",
                "leather",
                Items.POISONOUS_POTATO,
                new ItemStack(Items.SKULL, 1, 1)));

        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(AncientThaumaturgy.MOD_ID, "shadow_helm_lifesteal"), new InfusionRecipe(
                "SHADOW_FORTRESS_ARMOR",
                new Object[]{"mask", new NBTTagInt(2)},
                8,
                new AspectList().add(Aspect.UNDEAD, 80).add(Aspect.LIFE, 80).add(Aspect.PROTECT, 20),
                new ItemStack(KItems.shadow_helm, 1, 32767),
                "plateIron",
                "dyeRed",
                "plateIron",
                "leather",
                Items.GHAST_TEAR,
                Items.MILK_BUCKET));
    }

    public static void initMultiBlock() {
        Part A = new Part(BlocksTC.alembic, new ItemStack(KBlocks.alchemyFurnace, 1, 3));
        Part N = new Part(BlocksTC.metalAlchemical, new ItemStack(KBlocks.alchemyFurnace, 1, 4));
        Part G = new Part(Blocks.GLASS, "AIR");
        Part AD = new Part(BlocksTC.metalAlchemicalAdvanced, new ItemStack(KBlocks.alchemyFurnace, 1, 1), true);
        Part S = new Part(BlocksTC.smelterBasic, new ItemStack(KBlocks.alchemyFurnace, 1, 0), true);
        Part AU = new Part(BlocksTC.metalAlchemicalAdvanced, new ItemStack(KBlocks.alchemyFurnace, 1, 2), true);
        IDustTrigger.registerDustTrigger(new DustTriggerMultiblock("ADVANCED_ALCHEMY_FURNACE",
                new Part[][][]{
                        {
                                {A, N, A},
                                {N, G, N},
                                {A, N, A}
                        },
                        {
                                {AU, AD, AU},
                                {AD, S, AD},
                                {AU, AD, AU}
                        }
                }));
        ThaumcraftApi.addMultiblockRecipeToCatalog(new ResourceLocation(AncientThaumaturgy.MOD_ID, "advanced_alchemy_furnace"), new ThaumcraftApi.BluePrint(
                "ADVANCED_ALCHEMY_FURNACE",
            new Part[][][]{
                    {
                            {A, N, A},
                            {N, G, N},
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
                new ItemStack(Blocks.GLASS),
                new ItemStack(BlocksTC.metalAlchemicalAdvanced, 4),
                new ItemStack(BlocksTC.smelterBasic)
        ));
    }
}
