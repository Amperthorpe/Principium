package com.alleluid.principium

import com.alleluid.principium.client.gui.GuiPedestal
import com.alleluid.principium.client.gui.GuiSmelter
import com.alleluid.principium.common.blocks.pedestal.ContainerPedestal
import com.alleluid.principium.common.blocks.pedestal.TileEntityPedestal
import com.alleluid.principium.common.blocks.smelter.ContainerSmelter
import com.alleluid.principium.common.blocks.smelter.TileEntitySmelter
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.Container
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.fml.common.network.IGuiHandler

class ModGuiHandler : IGuiHandler{
    companion object {
        const val PEDESTAL = 0
        const val SMELTER = 1
    }

    override fun getClientGuiElement(ID: Int, player: EntityPlayer, world: World, x: Int, y: Int, z: Int): Any? {
        return when (ID) {
            PEDESTAL -> GuiPedestal(getServerGuiElement(ID, player, world, x, y, z)!!, player.inventory)
            SMELTER -> GuiSmelter(getServerGuiElement(ID, player, world, x, y, z)!!, player.inventory)
            else -> null
        }
    }

    override fun getServerGuiElement(ID: Int, player: EntityPlayer, world: World, x: Int, y: Int, z: Int): Container? {
        return when (ID) {
            PEDESTAL -> ContainerPedestal(player.inventory, world.getTileEntity(BlockPos(x, y, z)) as TileEntityPedestal)
            SMELTER -> ContainerSmelter(player.inventory, world.getTileEntity(BlockPos(x, y, z)) as TileEntitySmelter)
            else -> null
        }
    }

}