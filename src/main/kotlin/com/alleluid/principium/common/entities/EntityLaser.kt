package com.alleluid.principium.common.entities

import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.IProjectile
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.network.datasync.DataParameter
import net.minecraft.world.World

class EntityLaser(val damage: Double, worldIn: World) : Entity(worldIn), IProjectile {
    val xTile = -1
    val yTile = -1
    val zTile = -1
    private val CRITICAL: DataParameter<Byte>? = null
    var shootingEntity: Entity? = null

    constructor(damage:Double, worldIn:World, x: Double, y: Double, z:Double) : this(damage, worldIn) {
        this.setPosition(x, y, z)
    }

    constructor(damage: Double, worldIn: World, shooter: EntityLivingBase): this(damage, worldIn, shooter.posX, shooter.eyeHeight - 0.1, shooter.posZ){
         shootingEntity = shooter
    }

    override fun writeEntityToNBT(p0: NBTTagCompound) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun readEntityFromNBT(p0: NBTTagCompound) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun entityInit() {
        dataManager.register(CRITICAL!!, 0.toByte())
    }

    override fun shoot(p0: Double, p1: Double, p2: Double, p3: Float, p4: Float) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}