package com.alleluid.principium

import com.alleluid.principium.common.blocks.box.GuiBox
import com.alleluid.principium.common.blocks.pedestal.GuiPedestal
import com.alleluid.principium.common.blocks.smelter.GuiSmelter
import com.alleluid.principium.common.blocks.box.ContainerBox
import com.alleluid.principium.common.blocks.box.TileEntityBox
import com.alleluid.principium.common.blocks.pedestal.ContainerPedestal
import com.alleluid.principium.common.blocks.pedestal.TileEntityPedestal
import com.alleluid.principium.common.blocks.smelter.ContainerSmelter
import com.alleluid.principium.common.blocks.smelter.TileEntitySmelter
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.Container
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.fml.common.network.IGuiHandler
enum class ModGuiID{
    PEDESTAL,
    SMELTER,
    BOX
}

class ModGuiHandler : IGuiHandler{

    override fun getClientGuiElement(ID: Int, player: EntityPlayer, world: World, x: Int, y: Int, z: Int): Any? {
        return when (ModGuiID.values()[ID]) {
            ModGuiID.PEDESTAL -> GuiPedestal(getServerGuiElement(ID, player, world, x, y, z)!!, player.inventory)
            ModGuiID.SMELTER -> GuiSmelter(getServerGuiElement(ID, player, world, x, y, z)!!, player.inventory)
            ModGuiID.BOX -> GuiBox(getServerGuiElement(ID, player, world, x, y, z)!!, player.inventory)
        }
    }

    override fun getServerGuiElement(ID: Int, player: EntityPlayer, world: World, x: Int, y: Int, z: Int): Container? {
        return when (ModGuiID.values()[ID]) {
            ModGuiID.PEDESTAL -> ContainerPedestal(player.inventory, world.getTileEntity(BlockPos(x, y, z)) as TileEntityPedestal)
            ModGuiID.SMELTER -> ContainerSmelter(player, world.getTileEntity(BlockPos(x, y, z)) as TileEntitySmelter)
            ModGuiID.BOX -> ContainerBox(player.inventory, world.getTileEntity(BlockPos(x, y, z)) as TileEntityBox)
        }
    }

}