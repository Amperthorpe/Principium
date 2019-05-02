package com.alleluid.principium.common.items.weapons

import com.alleluid.principium.Utils
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.projectile.EntityArrow
import net.minecraft.entity.projectile.EntityTippedArrow
import net.minecraft.util.EnumHand
import net.minecraft.world.World

object WeaponPistol : BaseWeapon("item_weapon_pistol"){

    override fun onWeaponFire(worldIn: World, playerIn: EntityPlayer, enumHand: EnumHand, damage: Float): Boolean {
        return if (!worldIn.isRemote) {
            val entityArrow = EntityTippedArrow(worldIn, playerIn!!)
            entityArrow.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0f, 4.0f, 1.0f)
            entityArrow.damage = damage.toDouble()
            entityArrow.pickupStatus = EntityArrow.PickupStatus.CREATIVE_ONLY
            worldIn.spawnEntity(entityArrow)
            true
        } else {
            false
        }
    }

}