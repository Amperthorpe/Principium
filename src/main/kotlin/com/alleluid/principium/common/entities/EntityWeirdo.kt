package com.alleluid.principium.common.entities

import net.minecraft.entity.monster.EntityCreeper
import net.minecraft.util.DamageSource
import net.minecraft.util.SoundEvent
import net.minecraft.world.World

class EntityWeirdo(worldIn: World) : EntityCreeper(worldIn){

    override fun getAmbientSound(): SoundEvent? {
        return super.getAmbientSound()
    }

    override fun getHurtSound(damageSourceIn: DamageSource): SoundEvent? {
        return super.getHurtSound(damageSourceIn)
    }

    override fun getDeathSound(): SoundEvent? {
        return super.getDeathSound()
    }
}
