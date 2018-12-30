package com.alleluid.principium.common

import com.alleluid.principium.MOD_ID
import com.alleluid.principium.Utils
import com.alleluid.principium.common.items.tools.LaserDrill
import com.sun.org.apache.xpath.internal.operations.Bool
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.nbt.NBTTagLong
import net.minecraftforge.event.entity.living.LivingFallEvent
import net.minecraftforge.event.entity.player.PlayerFlyableFallEvent
import net.minecraftforge.event.entity.player.PlayerInteractEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.relauncher.Side

@Mod.EventBusSubscriber(modid = MOD_ID)
object CommonEvents {

    @JvmStatic
    @SubscribeEvent
    fun onPlayerLeftClick(event: PlayerInteractEvent.LeftClickBlock) {
/*
        when (event.itemStack.item) {
            LaserDrill -> if (!event.world.isRemote && event.itemStack.hasTagCompound()) {
                var didBreak = false
                if (event.itemStack.tagCompound!!.getLong("timeWhenUsable") < event.world.totalWorldTime) {
                    didBreak = event.world.destroyBlock(event.pos, true)
                }
                if (didBreak) {
                    event.itemStack.tagCompound!!.setLong("timeWhenUsable",
                            event.world.totalWorldTime + LaserDrill.breakCooldown)
                }
            }
        }
*/
    }

    @JvmStatic
    @SubscribeEvent
    fun thing(event: LivingFallEvent){
        val entity = event.entityLiving
        if (entity is EntityPlayer){
//            event.distance = 0f
            event.damageMultiplier = 0f
        }
    }
}