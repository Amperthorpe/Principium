package com.alleluid.principium.common

import com.alleluid.principium.MOD_ID
import com.alleluid.principium.Utils
import com.alleluid.principium.common.items.weapons.BaseWeapon
import net.minecraft.client.Minecraft
import net.minecraft.init.Items
import net.minecraft.util.EnumHand
import net.minecraftforge.client.event.MouseEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(modid = MOD_ID)
object MouseHandler {

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    fun onMouseEvent(event: MouseEvent){
        val mc = Minecraft.getMinecraft()
        val heldItem = mc.player.getHeldItem(EnumHand.MAIN_HAND).item
        if (heldItem != Items.AIR && heldItem is BaseWeapon){
            if (event.button == 0 && event.isButtonstate){
                heldItem.onWeaponFire(mc.world, mc.player)
            }
        }
    }
}