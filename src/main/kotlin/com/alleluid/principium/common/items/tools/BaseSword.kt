package com.alleluid.principium.common.items.tools

import com.alleluid.principium.MOD_ID
import com.alleluid.principium.PrincipiumMod
import com.alleluid.principium.Utils
import com.alleluid.principium.common.items.BaseItem
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.ItemSword
import net.minecraft.util.EnumHand
import net.minecraft.util.ResourceLocation
import com.alleluid.principium.Utils.Formatting as ufm


open class BaseSword(material: Item.ToolMaterial, val name: String) : ItemSword(material) {
    init {
        translationKey = "$MOD_ID.$name"
        registryName = ResourceLocation(MOD_ID, name)
        creativeTab = PrincipiumMod.creativeTab
    }

    fun registerItemModel() {
        PrincipiumMod.proxy.registerItemRenderer(this, 0, name)
    }
}

object PrincipicSword : BaseItem("sword_principic") {
    init {
        loreText.add("The strong will be made weak, and the weak shall bow before me.")
        infoText.add("Attacks lower to half a heart, but won't kill.")
//        infoText.add("Right click to teleport, sneak click to set position.")

    }

    override fun onLeftClickEntity(stack: ItemStack, player: EntityPlayer, entity: Entity): Boolean {
        return false //logic moved to CommonEvents.kt
    }

    override fun itemInteractionForEntity(stack: ItemStack, playerIn: EntityPlayer, target: EntityLivingBase, hand: EnumHand): Boolean {
        if (!playerIn.world.isRemote) Utils.statusMessage(target.health.toString())
        return super.itemInteractionForEntity(stack, playerIn, target, hand)
    }
}