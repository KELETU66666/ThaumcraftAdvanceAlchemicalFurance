package com.keletu.ancient_thaumcraft.init;

import net.minecraft.util.ResourceLocation;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.ScanEntity;
import thaumcraft.api.research.ScanningManager;
import thaumcraft.common.entities.projectile.EntityAlumentum;
import thaumcraft.common.entities.projectile.EntityCausalityCollapser;

public class KResearch {

    public static void registerResearch() {
        ResearchCategories.registerCategory("ANCIENT_THAUMATURGY", "HEDGEALCHEMY", null, new ResourceLocation("ancient_thaumcraft", "textures/models/research/r_pech_thaum.png"), new ResourceLocation("ancient_thaumcraft", "textures/models/research/greatwood.png"));

        ThaumcraftApi.registerResearchLocation(new ResourceLocation("ancient_thaumcraft", "research/research.json"));
    }
}
