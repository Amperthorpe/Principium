package com.alleluid.principium.common.items.weapons

import com.alleluid.principium.Utils
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.projectile.EntityTippedArrow
import net.minecraft.world.World

object WeaponPistol : BaseWeapon("item_weapon_pistol"){

    override fun onWeaponFire(worldIn: World, playerIn: EntityPlayer): Boolean {
        Utils.statusMessage("pew pew")
        val entityarrow = EntityTippedArrow(worldIn, playerIn)
        entityarrow.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0f, 3.0f, 1.0f)
        worldIn.spawnEntity(entityarrow)
        return true
    }

}