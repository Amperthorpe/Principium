package com.alleluid.principium.common.blocks.counter

import com.alleluid.principium.GeneralUtils
import com.alleluid.principium.common.blocks.BaseBlockTileEntity
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

object BlockCounter : BaseBlockTileEntity<TileEntityCounter>(Material.ROCK, null,"counter") {

    override fun onBlockActivated(worldIn: World, pos: BlockPos, state: IBlockState, playerIn: EntityPlayer, hand: EnumHand, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): Boolean {
        if (!worldIn.isRemote) {
            val tile: TileEntityCounter = this.getTileEntity(worldIn, pos)
            if (facing == EnumFacing.DOWN) {
                tile.count--
            } else if (facing == EnumFacing.UP) {
                tile.count++
            }
            GeneralUtils.statusMessage("Count: ${tile.count}")
        }
        return true
    }

    override val tileEntityClass: Class<TileEntityCounter>
        get() = TileEntityCounter::class.java

    override fun createTileEntity(world: World, state: IBlockState): TileEntityCounter? = TileEntityCounter()
}