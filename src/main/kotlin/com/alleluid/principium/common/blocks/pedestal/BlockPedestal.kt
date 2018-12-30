package com.alleluid.principium.common.blocks.pedestal

import com.alleluid.principium.PrincipiumMod
import com.alleluid.principium.Utils
import com.alleluid.principium.common.blocks.BaseTileEntity
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.item.EntityItem
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.IItemHandler

object BlockPedestal : BaseTileEntity<TileEntityPedestal>(Material.ROCK, "block_pedestal") {
    init {
        blockHardness = 3f
    }

    override fun isOpaqueCube(state: IBlockState): Boolean = false
    override fun isFullBlock(state: IBlockState): Boolean = false

    override val tileEntityClass: Class<TileEntityPedestal>
        get() = TileEntityPedestal::class.java

    override fun createTileEntity(world: World, state: IBlockState): TileEntityPedestal? = TileEntityPedestal()

    override fun onBlockActivated(worldIn: World, pos: BlockPos, state: IBlockState, playerIn: EntityPlayer, hand: EnumHand, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): Boolean {
        val tile: TileEntityPedestal = getTileEntity(worldIn, pos)
        val itemHandler: IItemHandler? = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing)
        if (!worldIn.isRemote) {
            val heldItem = playerIn.getHeldItem(hand)
            if (itemHandler != null) {
                if (!playerIn.isSneaking) {
                    if (heldItem.isEmpty) {
                        playerIn.setHeldItem(hand, itemHandler.extractItem(0, 64, false))
                    } else {
                        playerIn.setHeldItem(hand, itemHandler.insertItem(0, heldItem, false))
                    }
                    tile.markDirty()
                }
            }
        } else {
            if (itemHandler != null) {
                val stack: ItemStack = itemHandler.getStackInSlot(0)
                if (!stack.isEmpty) {
                    Utils.statusMessage("${stack.count}x ${stack.displayName}")
                }
            }
        }
        return true
    }

    override fun breakBlock(worldIn: World, pos: BlockPos, state: IBlockState) {
        val tile: TileEntityPedestal = getTileEntity(worldIn, pos)
        val itemHandler: IItemHandler? = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH)
        if (itemHandler != null){
            val stack = itemHandler.getStackInSlot(0)
            if (!stack.isEmpty) {
                val item = EntityItem(worldIn, pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble(), stack)
                worldIn.spawnEntity(item)
            }

        }
        super.breakBlock(worldIn, pos, state)
    }
}