package com.keletu.ancient_thaumcraft.init;

import com.google.common.base.Strings;
import com.keletu.ancient_thaumcraft.AncientThaumaturgy;
import com.keletu.ancient_thaumcraft.item.ItemManaBean;
import com.keletu.ancient_thaumcraft.item.ShadowArmor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class KItems {
    public static Item shadow_ingot;
    public static Item shadow_nugget;
    public static Item shadow_helm;
    public static Item shadow_chest;
    public static Item shadow_legs;
    public static Item mana_bean;

    public static ItemArmor.ArmorMaterial SHADOW_FORTRESS = EnumHelper.addArmorMaterial("SHADOW_FORTRESS", "shadow", 300, new int[]{0, 6, 10, 4}, 15, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 4F);
    public static void preInit() {
        shadow_ingot = new Item().setTranslationKey("shadow_ingot").setCreativeTab(AncientThaumaturgy.tab);
        shadow_nugget = new Item().setTranslationKey("shadow_nugget").setCreativeTab(AncientThaumaturgy.tab);
        shadow_helm = new ShadowArmor(EntityEquipmentSlot.HEAD).setTranslationKey("shadow_fortress_helm").setCreativeTab(AncientThaumaturgy.tab);
        shadow_chest = new ShadowArmor(EntityEquipmentSlot.CHEST).setTranslationKey("shadow_fortress_chest").setCreativeTab(AncientThaumaturgy.tab);
        shadow_legs = new ShadowArmor(EntityEquipmentSlot.LEGS).setTranslationKey("shadow_fortress_legs").setCreativeTab(AncientThaumaturgy.tab);
        mana_bean = new ItemManaBean().setTranslationKey("mana_bean").setCreativeTab(AncientThaumaturgy.tab);
       }

    public static void registerItem() {
        registerItem(shadow_helm, shadow_helm.getTranslationKey().substring(5));
        registerItem(shadow_chest, shadow_chest.getTranslationKey().substring(5));
        registerItem(shadow_legs, shadow_legs.getTranslationKey().substring(5));
        registerItem(shadow_ingot, shadow_ingot.getTranslationKey().substring(5));
        registerItem(shadow_nugget, shadow_nugget.getTranslationKey().substring(5));
        registerItem(mana_bean, mana_bean.getTranslationKey().substring(5));
    }

    public static void registerItem(Item item, String name)
    {
        if (item.getRegistryName() == null && Strings.isNullOrEmpty(name))
            throw new IllegalArgumentException("Attempted to register a item with no name: " + item);
        if (item.getRegistryName() != null && !item.getRegistryName().toString().equals(name))
            throw new IllegalArgumentException("Attempted to register a item with conflicting names. Old: " + item.getRegistryName() + " New: " + name);
        ForgeRegistries.ITEMS.register(item.getRegistryName() == null ? item.setRegistryName(name) : item);
    }

    public static void Render(){
        registerRender();
    }

    public static void registerRender() {
        renderItems(shadow_helm);
        renderItems(shadow_chest);
        renderItems(shadow_legs);
        renderItems(shadow_ingot);
        renderItems(shadow_nugget);
        renderItems(mana_bean);
    }

    public static void renderItems(Item i){

        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(i, 0, new ModelResourceLocation(
                AncientThaumaturgy.MOD_ID + ":" + i.getTranslationKey().substring(5), "inventory"));

    }

}
