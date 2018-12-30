package com.alleluid.principium.common.items

import com.alleluid.principium.MOD_ID
import com.alleluid.principium.PrincipiumMod
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.EntityEquipmentSlot
import net.minecraft.item.ItemArmor
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation
import net.minecraft.world.World

class LongFallBootsBase(slot: EntityEquipmentSlot, val name: String) : ItemArmor(ItemArmor.ArmorMaterial.CHAIN, 0, slot){
    init {
        unlocalizedName = "$MOD_ID.$name"
        registryName = ResourceLocation(MOD_ID, name)
        creativeTab = PrincipiumMod.creativeTab
    }

    override fun onArmorTick(world: World, player: EntityPlayer, itemStack: ItemStack) {
        player.fallDistance = 0f
        super.onArmorTick(world, player, itemStack)
    }

    fun registerItemModel() {
        PrincipiumMod.proxy.registerItemRenderer(this, 0, name)
    }

}