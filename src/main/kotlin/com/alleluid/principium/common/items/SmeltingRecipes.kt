package com.alleluid.principium.common.items

import net.minecraft.item.ItemStack
import net.minecraftforge.fml.common.registry.GameRegistry.addSmelting

fun registerSmeltingRecipes(){
    addSmelting(ModItems.principitus, ItemStack(ModItems.ingotPrincipic, 1), 1f)
}