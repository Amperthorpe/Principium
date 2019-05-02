package com.alleluid.principium.common.items.weapons

import com.alleluid.principium.Utils
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.ActionResult
import net.minecraft.util.EnumHand
import net.minecraft.world.World

object WeaponPistol : BaseWeapon("item_weapon_pistol"){

    override fun onItemRightClick(worldIn: World, playerIn: EntityPlayer, handIn: EnumHand): ActionResult<ItemStack> {
        return super.onItemRightClick(worldIn, playerIn, handIn)
    }

    override fun onWeaponFire(worldIn: World, playerIn: EntityPlayer, handIn: EnumHand): Boolean {
        Utils.statusMessage("pew pew")
        return true
    }

}