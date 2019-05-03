package com.alleluid.principium.common.blocks.smelter

import com.alleluid.principium.MachineSyncMessage
import com.alleluid.principium.ModGuiID
import com.alleluid.principium.PacketHandler
import com.alleluid.principium.Utils
import com.alleluid.principium.Utils.ifServer
import com.alleluid.principium.common.blocks.BaseBlockTileEntity
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.item.EntityItem
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.fml.common.network.NetworkRegistry
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.IItemHandler

object BlockSmelter : BaseBlockTileEntity<TileEntitySmelter>(Material.ROCK, ModGuiID.SMELTER, "block_smelter") {
    init {
        blockHardness = 3f
    }

    override val tileEntityClass: Class<TileEntitySmelter>
        get() = TileEntitySmelter::class.java

    override fun isToolEffective(type: String, state: IBlockState) = type == "pickaxe"

    override fun createTileEntity(world: World, state: IBlockState) = TileEntitySmelter()

    override fun breakBlock(worldIn: World, pos: BlockPos, state: IBlockState) {
        val tile: TileEntitySmelter = getTileEntity(worldIn, pos)
        val itemHandler: IItemHandler? = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH)
        if (itemHandler != null){
            for (slot in 0 until itemHandler.slots) {
                val stack = itemHandler.getStackInSlot(slot)
                if (!stack.isEmpty) {
                    val item = EntityItem(worldIn, pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble(), stack)
                    worldIn.spawnEntity(item)
                }
            }

        }
        super.breakBlock(worldIn, pos, state)
    }

    override fun onBlockActivated(worldIn: World, pos: BlockPos, state: IBlockState, playerIn: EntityPlayer, hand: EnumHand, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): Boolean {
        Utils.chatMessage("Rem: ${worldIn.isRemote} | Tile energy: ${getTileEntity(worldIn, pos).energy}")
//        worldIn.ifServer {PacketHandler.INSTANCE.sendToAllAround(MachineSyncMessage(getTileEntity(worldIn, pos).energy, pos),
//                NetworkRegistry.TargetPoint(playerIn.dimension, pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble(), 2.0))}
        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ)
    }


}