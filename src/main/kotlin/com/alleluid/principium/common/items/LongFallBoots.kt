package com.alleluid.principium.common.items

import com.alleluid.principium.MOD_ID
import com.alleluid.principium.PrincipiumMod
import com.alleluid.principium.Utils
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.EntityEquipmentSlot
import net.minecraft.item.ItemArmor
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation
import net.minecraft.world.World

class LongFallBootsBase(slot: EntityEquipmentSlot, val name: String) : ItemArmor(ItemArmor.ArmorMaterial.CHAIN, 0, slot){
    init {
        translationKey = "$MOD_ID.$name"
        registryName = ResourceLocation(MOD_ID, name)
        creativeTab = PrincipiumMod.creativeTab
    }

    override fun addInformation(stack: ItemStack, worldIn: World?, tooltip: MutableList<String>, flagIn: ITooltipFlag) {
        tooltip.add("${Utils.Formatting.LORE}We asked this test subject to just try and land on her head.")
        tooltip.add("Negates fall damage")
        tooltip.add("${Utils.Formatting.LORE}Heheh. She can't do it. Good work boots.")

        super.addInformation(stack, worldIn, tooltip, flagIn)
    }

    override fun onArmorTick(world: World, player: EntityPlayer, itemStack: ItemStack) {
        player.fallDistance = 0f
        super.onArmorTick(world, player, itemStack)
    }

    fun registerItemModel() {
        PrincipiumMod.proxy.registerItemRenderer(this, 0, name)
    }

}