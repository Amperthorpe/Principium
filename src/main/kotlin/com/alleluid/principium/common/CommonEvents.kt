package com.alleluid.principium.common

import com.alleluid.principium.MOD_ID
import com.alleluid.principium.Utils
import com.alleluid.principium.common.items.ModItems.longFallBoots
import com.alleluid.principium.common.items.weapons.PrincipicSword
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.SoundEvents
import net.minecraft.item.ItemStack
import net.minecraft.util.DamageSource
import net.minecraft.util.EnumParticleTypes
import net.minecraft.util.ResourceLocation
import net.minecraft.util.SoundEvent
import net.minecraftforge.event.entity.living.LivingFallEvent
import net.minecraftforge.event.entity.player.AttackEntityEvent
import net.minecraftforge.event.entity.player.PlayerInteractEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

@Mod.EventBusSubscriber(modid = MOD_ID)
object CommonEvents {

    @JvmStatic
    @SubscribeEvent
    fun onEvent(event: PlayerInteractEvent.LeftClickBlock) {

        when (event.itemStack.item) {
/*
            LaserDrill -> if (!event.world.isRemote && event.itemStack.hasTagCompound()) {
                var didBreak = false
                if (event.itemStack.tagCompound!!.getLong("timeWhenUsable") < event.world.totalWorldTime) {
                    didBreak = event.world.destroyBlock(event.pos, true)
                }
                if (didBreak) {
                    event.itemStack.tagCompound!!.setLong("timeWhenUsable",
                            event.world.totalWorldTime + LaserDrill.breakCooldown)
                }
*/
        }
    }

    @JvmStatic
    @SubscribeEvent
    fun onEvent(event: AttackEntityEvent) {
        when (event.entityPlayer.heldItemMainhand.item) {
            PrincipicSword -> {
                val health = (event.target as EntityLivingBase).health
                val world = event.entityPlayer.world
                if (health > 1.1){
                    event.target.attackEntityFrom(DamageSource.DRAGON_BREATH, health - 0.1f)
                    if (world.isRemote){
                        Utils.particleGroup(world, EnumParticleTypes.CRIT_MAGIC, event.target)
                    }
                } else if (world.isRemote) {
                    world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, false,
                            event.target.posX,
                            event.target.posY + event.target.height - 0.3,
                            event.target.posZ,
                            0.0, 0.05, 0.0
                    )
                    event.entityPlayer.playSound(SoundEvents.BLOCK_FIRE_EXTINGUISH, 0.7f, 2f)
                }
                event.isCanceled = true
            }
        }
    }

    @JvmStatic
    @SubscribeEvent
    fun onEvent(event: LivingFallEvent) {
        val entity = event.entityLiving
        if (entity is EntityPlayer && entity.armorInventoryList.contains(ItemStack(longFallBoots))) {
//            event.distance = 0f
            event.damageMultiplier = 0f
        }
    }
}