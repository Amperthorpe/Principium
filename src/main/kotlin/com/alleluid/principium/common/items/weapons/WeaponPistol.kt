package com.alleluid.principium.common.items.weapons

import com.alleluid.principium.common.entities.EntityBullet
import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.projectile.EntityArrow
import net.minecraft.entity.projectile.EntitySpectralArrow
import net.minecraft.entity.projectile.EntityTippedArrow
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumHand
import net.minecraft.util.EnumParticleTypes
import net.minecraft.world.World

object WeaponPistol : BaseWeapon("item_weapon_pistol"){
    internal var bullet: EntityBullet? = null

    override fun onWeaponFire(worldIn: World, playerIn: EntityPlayer, enumHand: EnumHand, damage: Float): Boolean {
        return if (!worldIn.isRemote) {
            val entityArrow = EntityBullet(worldIn, playerIn!!)
            bullet = entityArrow
            entityArrow.shoot(playerIn, playerIn.rotationPitch + 0.1f, playerIn.rotationYaw, 0.0f, 5.0f, 0.1f)
            entityArrow.damage = damage.toDouble()
            entityArrow.isCritical = true
            entityArrow.pickupStatus = EntityArrow.PickupStatus.CREATIVE_ONLY
            worldIn.spawnEntity(entityArrow)
            true
        } else {
            false
        }
    }

    override fun onUpdate(stack: ItemStack, worldIn: World, entityIn: Entity, itemSlot: Int, isSelected: Boolean) {
        if (bullet != null){
            worldIn.spawnParticle(
                    EnumParticleTypes.CRIT,
                    bullet!!.posX,
                    bullet!!.posY,
                    bullet!!.posZ,
                    0.0, 0.0, 0.0
            )
        }
        super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected)
    }

}