package com.alleluid.principium.client

import com.alleluid.principium.MOD_ID
import com.alleluid.principium.common.items.ModItems
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.ItemStack

object PrincipiumTab : CreativeTabs(MOD_ID){
    override fun getTabIconItem(): ItemStack {
        return ItemStack(ModItems.principitus)
    }
}