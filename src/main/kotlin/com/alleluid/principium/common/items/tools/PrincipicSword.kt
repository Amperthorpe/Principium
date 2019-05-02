package com.alleluid.principium.common.items.tools

import com.alleluid.principium.Utils
import com.alleluid.principium.common.items.BaseItem
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumHand
import com.alleluid.principium.Utils.Formatting as ufm


object PrincipicSword : BaseItem("sword_principic") {
    init {
        loreText.add("The strong will be made weak, and the weak shall bow before me.")
        infoText.add("Damages to half a heart, but won't kill.")

    }

    override fun onLeftClickEntity(stack: ItemStack, player: EntityPlayer, entity: Entity): Boolean {
        return false //logic moved to CommonEvents.kt
    }

    override fun itemInteractionForEntity(stack: ItemStack, playerIn: EntityPlayer, target: EntityLivingBase, hand: EnumHand): Boolean {
        if (!playerIn.world.isRemote) Utils.statusMessage(target.health.toString())
        return super.itemInteractionForEntity(stack, playerIn, target, hand)
    }
}