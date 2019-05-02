package com.alleluid.principium.common.items.weapons

import com.alleluid.principium.Utils
import com.alleluid.principium.common.items.BaseItem
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.ActionResult
import net.minecraft.util.EnumHand
import net.minecraft.world.World

abstract class BaseWeapon(name: String) : BaseItem(name){
    override fun onItemRightClick(worldIn: World, playerIn: EntityPlayer, handIn: EnumHand): ActionResult<ItemStack> {
        Utils.statusMessage("Pew pew")
        return super.onItemRightClick(worldIn, playerIn, handIn)
    }

    abstract fun onWeaponFire(worldIn: World, playerIn: EntityPlayer, handIn: EnumHand): Boolean
}