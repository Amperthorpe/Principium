package com.alleluid.principium

import com.alleluid.principium.common.blocks.pedestal.ContainerPedestal
import com.alleluid.principium.common.blocks.pedestal.GuiPedestal
import com.alleluid.principium.common.blocks.pedestal.TileEntityPedestal
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.Container
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.fml.common.network.IGuiHandler

class ModGuiHandler() : IGuiHandler{
    companion object {
        val PEDESTAL = 0
    }

    override fun getClientGuiElement(ID: Int, player: EntityPlayer, world: World, x: Int, y: Int, z: Int): Any? {
        return when (ID) {
            PEDESTAL -> GuiPedestal(getServerGuiElement(ID, player, world, x, y, z)!!, player.inventory)
            else -> null
        }
    }

    override fun getServerGuiElement(ID: Int, player: EntityPlayer, world: World, x: Int, y: Int, z: Int): Container? {
        return when (ID) {
            PEDESTAL -> ContainerPedestal(player.inventory, world.getTileEntity(BlockPos(x, y, z)) as TileEntityPedestal)
            else -> null
        }
    }

}