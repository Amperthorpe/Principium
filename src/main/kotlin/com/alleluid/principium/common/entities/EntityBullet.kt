package com.alleluid.principium.common.entities

import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.IProjectile
import net.minecraft.entity.projectile.EntityArrow
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumParticleTypes
import net.minecraft.util.math.MathHelper
import net.minecraft.world.World

class EntityBullet(val worldIn: World, shooter: EntityLivingBase) : EntityArrow(worldIn, shooter), IProjectile {
    override fun getArrowStack(): ItemStack {
        return ItemStack.EMPTY
    }
}