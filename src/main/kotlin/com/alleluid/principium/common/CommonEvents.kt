package com.alleluid.principium.common

import com.alleluid.principium.MOD_ID
import com.alleluid.principium.common.items.ModItems.longFallBoots
import com.alleluid.principium.common.items.tools.PrincipicSword
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.DamageSource
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
                if (health > 1.1){
                    event.target.attackEntityFrom(DamageSource.MAGIC, health - 0.1f)
                } else {
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