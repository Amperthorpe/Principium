package com.alleluid.principium.common.blocks

import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Blocks
import net.minecraft.init.SoundEvents
import net.minecraft.item.Item
import net.minecraft.item.ItemBlock
import net.minecraft.util.*
import net.minecraft.util.math.AxisAlignedBB
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockAccess
import net.minecraft.world.World
import java.util.*

object BlockEgg : BaseBlock(Material.DRAGON_EGG, "block_egg") {
    // TODO: Make this fall
    private val WEIRD_EGG_AABB = AxisAlignedBB(0.0625, 0.0, 0.0625, 0.9375, 1.0, 0.9375)
    init {
        loreText.add("¯\\_(ツ)_/¯")
        // Shrugs need no translation
    }

    override fun causesSuffocation(state: IBlockState) = false
    override fun isOpaqueCube(state: IBlockState) = false
    override fun isFullBlock(state: IBlockState): Boolean = false
    override fun getBoundingBox(state: IBlockState, source: IBlockAccess, pos: BlockPos) = WEIRD_EGG_AABB

    override fun getItemDropped(state: IBlockState, rand: Random, fortune: Int): Item {
        return ItemBlock.getItemFromBlock(Blocks.COBBLESTONE)
    }

    override fun onBlockActivated(worldIn: World, pos: BlockPos, state: IBlockState, playerIn: EntityPlayer, hand: EnumHand, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): Boolean {
        val directions = listOf<BlockPos>(pos.up(), pos.down(), pos.north(), pos.south(), pos.east(), pos.west())
        directions.forEach {
            if (worldIn.isAirBlock(it)){
                worldIn.setBlockState(it, this.defaultState)

                if (worldIn.isRemote) {
                    worldIn.spawnParticle(EnumParticleTypes.HEART, false, it.x.toDouble(), it.y.toDouble(), it.z.toDouble(), 0.0, 0.0, 0.0)
                    playerIn.playSound(SoundEvents.BLOCK_STONE_PLACE, 1f, 1f)
                }
            }
        }

        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ)
    }
}
