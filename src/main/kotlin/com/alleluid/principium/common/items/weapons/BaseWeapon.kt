package com.alleluid.principium.common.items.weapons

import com.alleluid.principium.common.items.BaseItem
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.EnumHand
import net.minecraft.world.World

abstract class BaseWeapon(name: String) : BaseItem(name){
    abstract fun onWeaponFire(worldIn: World, playerIn: EntityPlayer, enumHand: EnumHand, damage: Float): Boolean
}