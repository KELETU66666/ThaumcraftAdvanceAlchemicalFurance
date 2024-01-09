package com.keletu.ancient_thaumcraft;

import com.keletu.ancient_thaumcraft.init.KBlocks;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class KTab extends CreativeTabs
{
    public KTab(String label)
    {
        super(label);
    }

    @Override
    public ItemStack createIcon()
    {
        return Item.getItemFromBlock(KBlocks.pechHead_normal).getDefaultInstance();
    }
}