package com.alleluid.principium.common.items

import com.alleluid.principium.MOD_ID
import com.alleluid.principium.PrincipiumMod
import com.alleluid.principium.util.Formatting
import net.minecraft.client.resources.I18n
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.EntityEquipmentSlot
import net.minecraft.item.ItemArmor
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation
import net.minecraft.world.World

class LongFallBootsBase(slot: EntityEquipmentSlot, val name: String) : ItemArmor(ArmorMaterial.CHAIN, 0, slot){
    init {
        translationKey = "$MOD_ID.$name"
        registryName = ResourceLocation(MOD_ID, name)
        creativeTab = PrincipiumMod.creativeTab
    }

    override fun addInformation(stack: ItemStack, worldIn: World?, tooltip: MutableList<String>, flagIn: ITooltipFlag) {
        tooltip.add(Formatting.LORE + I18n.format("info.$MOD_ID.$name.lore1")) //We do what we must because we can.
        tooltip.add(I18n.format("info.$MOD_ID.$name.info1")) //Negates fall damage.

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