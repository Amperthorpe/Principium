package com.alleluid.principium.common.entities

import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.IProjectile
import net.minecraft.entity.projectile.EntityArrow
import net.minecraft.item.ItemStack
import net.minecraft.util.math.MathHelper
import net.minecraft.world.World

class EntityBullet(val worldIn: World) : EntityArrow(worldIn), IProjectile {

    init {
        this.pickupStatus = PickupStatus.DISALLOWED
        this.damage = 2.0
        this.setSize(0.5f, 0.5f)
    }

    constructor(worldIn: World, x: Double, y: Double, z: Double) : this(worldIn){
        this.posX = x
        this.posY = y
        this.posZ = z
    }

    constructor(worldIn: World, entity: EntityLivingBase)
            : this(worldIn, entity.posX, entity.posY + entity.eyeHeight.toDouble() - 0.1, entity.posZ){
        this.shootingEntity = entity
    }

    override fun getArrowStack(): ItemStack {
        return ItemStack.EMPTY
    }

    override fun shoot(x: Double, y: Double, z: Double, velocity: Float, inaccuracy: Float) {
        var x = x
        var y = y
        var z = z
        val f = MathHelper.sqrt(x * x + y * y + z * z)
        x /= f.toDouble()
        y /= f.toDouble()
        z /= f.toDouble()
//        x += this.rand.nextGaussian() * 0.0075 * inaccuracy.toDouble()
//        y += this.rand.nextGaussian() * 0.0075 * inaccuracy.toDouble()
//        z += this.rand.nextGaussian() * 0.0075 * inaccuracy.toDouble()
        x *= velocity.toDouble()
        y *= velocity.toDouble()
        z *= velocity.toDouble()
        this.motionX = x
        this.motionY = y
        this.motionZ = z
        val f1 = MathHelper.sqrt(x * x + z * z)
        this.rotationYaw = (MathHelper.atan2(x, z) * 57.3).toFloat()
//        this.rotationPitch = (MathHelper.atan2(y, f1.toDouble()) * 57.3).toFloat()
        this.prevRotationYaw = this.rotationYaw
        this.prevRotationPitch = this.rotationPitch
//        this.ticksInGround = 0 // Private in super
    }

    override fun onUpdate() {
        super.onUpdate()
        if (inGround){
            this.worldIn.removeEntity(this)
        }
    }

}